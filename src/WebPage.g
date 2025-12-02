
grammar WebPage;

options {
  language = Java;
}

@header {
package compiladorhtml;
import org.antlr.runtime.*;
}


/* -------------------------
   Parser members (Java)
   ------------------------- */
@parser::members {
    // Salidas
    public StringBuilder html = new StringBuilder();
    public StringBuilder css = new StringBuilder();

    // CRUD
    public String crudDatabase = "";
    public String crudTable = "";
    public java.util.List<String> crudFields = new java.util.ArrayList<String>();

        public String getCrudJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("  \"database\": \"" + (crudDatabase==null?"":crudDatabase) + "\",\n");
        sb.append("  \"table\": \"" + (crudTable==null?"":crudTable) + "\",\n");
        sb.append("  \"fields\": [");
        for (int i=0;i<crudFields.size();i++) {
            sb.append("\"" + crudFields.get(i) + "\"");
            if (i < crudFields.size()-1) sb.append(", ");
        }
        sb.append("]\n}");
        return sb.toString();
    }


    // Helpers
    private String strip(String s) {
        if (s == null) return "";
        s = s.trim();
        if (s.length() >= 2 && s.startsWith("\"") && s.endsWith("\"")) return s.substring(1, s.length()-1);
        return s;
    }
    private String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    // Metodo final para escribir archivos (se puede llamar esto desde la regla page | no olvidar corregir cuando no hay css)
    private void writeFiles() {
        try {
            // output.html (si ya tiene css, link se agregó en la regla)
            java.io.FileWriter fw = new java.io.FileWriter("output.html");
            fw.write(html.toString());
            fw.close();

            // styles.css (si hay css)
            if (css.length() > 0) {
                java.io.FileWriter fw2 = new java.io.FileWriter("styles.css");
                fw2.write(css.toString());
                fw2.close();
            }

            // crud_spec.json simple
            java.io.FileWriter fw3 = new java.io.FileWriter("crud_spec.json");
            fw3.write("{\n");
            fw3.write("  \"database\": \"" + (crudDatabase==null?"":crudDatabase) + "\",\n");
            fw3.write("  \"table\": \"" + (crudTable==null?"":crudTable) + "\",\n");
            fw3.write("  \"fields\": [");
            for (int i=0;i<crudFields.size();i++) {
                fw3.write("\"" + crudFields.get(i) + "\"");
                if (i < crudFields.size()-1) fw3.write(", ");
            }
            fw3.write("]\n}\n");
            fw3.close();
        } catch(Exception e) {
            System.err.println("Error al escribir archivos: " + e.getMessage());
        }
    }
}

/* -------------------------
   Parser rules (lowercase)
   ------------------------- */

page
    : 'inicio' 'pagina' NL? block* 'fin' 'pagina' EOF
      {
          // Si hay estilos css y no se agregó el link aún, se añade al inicio del html
          if (css.length() > 0 && html.indexOf("<link rel=\"stylesheet\"") == -1) {
              html.insert(0, "<link rel=\"stylesheet\" href=\"styles.css\">\n");
          }
          // envolver con estructura HTML básica si el usuario no la agregó
          String body = html.toString();
          if (!body.trim().startsWith("<!doctype")) {
              String head = "<!doctype html>\n<html>\n<head>\n<meta charset=\"utf-8\">\n<title>Página generada</title>\n";
              if (css.length() > 0) head += "<link rel=\"stylesheet\" href=\"styles.css\">\n";
              head += "</head>\n<body>\n";
              String foot = "\n</body>\n</html>";
              html.insert(0, head);
              html.append(foot);
          }
          // escribe archivos finales
          writeFiles();
      }
    ;

block
    : linea_simple
    | titulo
    | menu
    | seccion
    | texto
    | crud_block
    | estilos
    ;

/* ---------------------------
   Elementos simples
   --------------------------- */

linea_simple
    : 'linea' 'simple' NL?
      {
         html.append("<hr/>\n");
      }
    ;

// titulo puede tener estilo inline opcional
titulo
    : 'titulo' s=STRING ( 'estilo' st=STRING )? NL?
      {
         String content = strip($s.getText());
         String styleAttr = "";
         if ($st != null) styleAttr = " style=\"" + strip($st.getText()) + "\"";
         html.append("<h1" + styleAttr + ">" + escapeHtml(content) + "</h1>\n");
      }
    ;

menu
    : 'menu' 'opciones' NL?
      {
         html.append("<nav><ul><li>Inicio</li><li>Productos</li><li>Contacto</li></ul></nav>\n");
         // ejemplo css por defecto
         css.append("nav ul { list-style:none; padding:6px; background:#f2f2f2; }\n");
         css.append("nav li { display:inline; margin-right:10px; }\n");
      }
    ;

texto
    : 'texto' s=STRING ( 'estilo' st=STRING )? NL?
      {
         String content = strip($s.getText());
         String styleAttr = "";
         if ($st != null) styleAttr = " style=\"" + strip($st.getText()) + "\"";
         html.append("<p" + styleAttr + ">" + escapeHtml(content) + "</p>\n");
      }
    ;

/* ---------------------------
   Secciones e imágenes
   --------------------------- */

seccion
    : 'seccion' IDENT (il=imagenList)? (atributo)* NL?
      {
         String id = $IDENT.getText();
         html.append("<section id=\"" + escapeHtml(id) + "\">\n");
         if (il != null) {
             html.append(il); // usamos la variable local "il"
         }
         html.append("</section>\n");
      }
    ;


// imagenList devuelve un String con tags <img>
imagenList
    returns [String text]
    : first=IDENT ( rest+=IDENT )*
      {
          StringBuilder sb = new StringBuilder();
          // primer ident
          sb.append("<img src=\"" + escapeHtml($first.getText()) + "\" alt=\"" + escapeHtml($first.getText()) + "\"/>\n");
          // resto (lista)
          if ($rest != null) {
              for (int i=0;i<$rest.size();i++) {
                  org.antlr.runtime.Token t = (org.antlr.runtime.Token)$rest.get(i);
                  String name = t.getText();
                  sb.append("<img src=\"" + escapeHtml(name) + "\" alt=\"" + escapeHtml(name) + "\"/>\n");
              }
          }
          $text = sb.toString();
      }
    ;

/* atributo clave: "domicilio: "..."", agrega HTML dentro de sección */
atributo
    : key=IDENT ':' val=STRING NL?
      {
         html.append("<p><strong>" + escapeHtml($key.getText()) + ":</strong> " + escapeHtml(strip($val.getText())) + "</p>\n");
      }
    ;

/* ---------------------------
   CRUD block
   --------------------------- */

crud_block
    : 'crud' 'inicio' NL?
        'bdd' db=IDENT NL?
        'tabla' tab=IDENT NL?
            ( 'campo' f=IDENT NL? )+
        'fintabla' NL?
      'crud' 'fin' NL?
      {
          // guardar valores en members para la escritura final
          crudDatabase = $db.getText();
          crudTable = $tab.getText();
          // recuperar campos: los tokens 'f' vienen de la última repetición; pero los hemos agregado en el loop a la lista
          // Para asegurar que crudFields tenga los campos, reconstruimos desde tokens (alternativa: añadir dentro del loop)
          // Aquí vamos a no depender de la variable loop, así: (mejor: agregarlos dentro de la regla 'campo' en vez de aquí)
          // Para simplicidad, append un encabezado visual en html:
          html.append("<section class=\"crud\">\n<h2>CRUD: " + escapeHtml(crudTable) + "</h2>\n<table border=\"1\"><tr>");
          for (int i=0;i<crudFields.size();i++) html.append("<th>" + escapeHtml(crudFields.get(i)) + "</th>");
          html.append("</tr>\n<tr>");
          for (int i=0;i<crudFields.size();i++) html.append("<td>&nbsp;</td>");
          html.append("</tr></table>\n</section>\n");
      }
    ;

// versión alternativa para capturar campos: regla campo que agrega directamente a la lista
// sin repetir aquí
// Nota: ANTLR v3 permite acciones en el bucle, así que definimos 'campo' abajo y en el bloque se invoca la producción (se usa el + anterior)
campo
    : 'campo' name=IDENT NL?
      {
         crudFields.add($name.getText());
      }
    ;

/* ---------------------------
   Bloque CSS
   --------------------------- */

estilos
    : 'css' 'inicio' NL? regla+ 'css' 'fin' NL?
      {
         // link al css si no está ya agregado
         if (html.indexOf("<link rel=\"stylesheet\"") == -1) {
             html.insert(0, "<link rel=\"stylesheet\" href=\"styles.css\">\n");
         }
      }
    ;

// regla: selector { propiedades } | No funciona de forma correcta devuelve el css sin anidar al respectivo padre, corregir.
regla
    : selector=IDENT '{' NL?
        {//if this does not work i don't know how to fix this | ayuda
            java.util.List<String> props = new java.util.ArrayList<String>();
        }
        (p1=propiedad { props.add($p1.line); })*
      '}' NL?
      {
         css.append($selector.getText() + " {\n");
         for (String line : props) {
             css.append(line);
         }
         css.append("}\n");
      }
    ;

 
// propiedad: nombre: valor;  (devuelve la línea, no escribe en css)

propiedad
    returns [String line]
    : pname=IDENT ':' pval=(IDENT|STRING) ';' NL?
      {
         String v = $pval.getText();
         v = strip(v);
         $line = "  " + $pname.getText() + ": " + v + ";\n";
      }
    ;



/* ---------------------------
   Lexer rules (mayúsculas)
   --------------------------- */

STRING
    : '"' (~'"')* '"'
    ;

IDENT
    : ('a'..'z'|'A'..'Z'|'0'..'9'|'_'|'-'|'.')+
    ;

NL
    : ('\r'? '\n')+ { $channel = HIDDEN; }
    ;

WS
    : (' '|'\t')+ { $channel = HIDDEN; }
    ;

// ignora comentarios de línea
SL_COMMENT
    : '//' ~('\n'|'\r')* { $channel = HIDDEN; }
    ;

