// $ANTLR 3.5.2 src/WebPage.g 2025-12-13 09:56:18

package compiladorhtml;
import org.antlr.runtime.*;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class WebPageParser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "IDENT", "NL", "SL_COMMENT", "STRING", 
		"WS", "':'", "';'", "'bdd'", "'campo'", "'crud'", "'css'", "'estilo'", 
		"'fin'", "'fintabla'", "'inicio'", "'linea'", "'menu'", "'opciones'", 
		"'pagina'", "'seccion'", "'simple'", "'tabla'", "'texto'", "'titulo'", 
		"'{'", "'}'"
	};
	public static final int EOF=-1;
	public static final int T__9=9;
	public static final int T__10=10;
	public static final int T__11=11;
	public static final int T__12=12;
	public static final int T__13=13;
	public static final int T__14=14;
	public static final int T__15=15;
	public static final int T__16=16;
	public static final int T__17=17;
	public static final int T__18=18;
	public static final int T__19=19;
	public static final int T__20=20;
	public static final int T__21=21;
	public static final int T__22=22;
	public static final int T__23=23;
	public static final int T__24=24;
	public static final int T__25=25;
	public static final int T__26=26;
	public static final int T__27=27;
	public static final int T__28=28;
	public static final int T__29=29;
	public static final int IDENT=4;
	public static final int NL=5;
	public static final int SL_COMMENT=6;
	public static final int STRING=7;
	public static final int WS=8;

	// delegates
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators


	public WebPageParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public WebPageParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	@Override public String[] getTokenNames() { return WebPageParser.tokenNames; }
	@Override public String getGrammarFileName() { return "src/WebPage.g"; }


	    // Salidas
	    public StringBuilder html = new StringBuilder();
	    public StringBuilder css = new StringBuilder();

	    // CRUD
	    public String crudDatabase = "";
	    public String crudTable = "";
	    public java.util.List<String> crudFields = new java.util.ArrayList<String>();

	// ==============================
	// GENERACIÓN SQL + PHP CRUD
	// ==============================

	private void generateSQLAndPHP() {
	    try {
	        // -------- SQL --------
	        java.io.FileWriter sql = new java.io.FileWriter("schema.sql");

	        sql.write("CREATE DATABASE IF NOT EXISTS " + crudDatabase + ";\n");
	        sql.write("USE " + crudDatabase + ";\n\n");

	        sql.write("CREATE TABLE " + crudTable + " (\n");
	        sql.write("  id SERIAL PRIMARY KEY,\n");

	        for (int i = 0; i < crudFields.size(); i++) {
	            sql.write("  " + crudFields.get(i) + " VARCHAR(255)");
	            if (i < crudFields.size() - 1) sql.write(",");
	            sql.write("\n");
	        }

	        sql.write(");\n");
	        sql.close();

	        // -------- PHP DB --------
	        java.io.FileWriter db = new java.io.FileWriter("db.php");
	        db.write("<?php\n");
	        db.write("$" + "host = \"localhost\";\n");
	        db.write("$" + "db   = \"" + crudDatabase + "\";\n");
	        db.write("$" + "user = \"root\";\n");
	        db.write("$" + "pass = \"\";\n");
	        db.write("$" + "pdo = new PDO(\"mysql:host="+"$"+"host;dbname="+"$"+"db\", "+"$"+"user, "+"$"+"pass);\n");
	        db.write("$" + "pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);\n");
	        db.write("?>\n");
	        db.close();

	        generateCreate();
	        generateRead();
	        generateUpdate();
	        generateDelete();

	    } catch (Exception e) {
	        System.err.println("Error generando CRUD PHP/SQL: " + e.getMessage());
	    }
	}
	//-----------------------------------
	//----------CREATE-------------------
	//---+-------------------------------

	private void generateCreate() throws Exception {
	    java.io.FileWriter fw = new java.io.FileWriter("create_" + crudTable + ".php");

	    fw.write("<?php\ninclude 'db.php';\n");

	    for (String f : crudFields) {
	        fw.write("$" + f + " = "+"$"+"_POST['" + f + "'];\n");
	    }

	    fw.write("\n"+"$"+"sql = \"INSERT INTO " + crudTable + " (");
	    fw.write(String.join(",", crudFields));
	    fw.write(") VALUES (");

	    for (int i = 0; i < crudFields.size(); i++) {
	        fw.write("?");
	        if (i < crudFields.size() - 1) fw.write(",");
	    }
	    fw.write(")\";\n");

	    fw.write("$"+"stmt = "+"$"+"pdo->prepare("+"$"+"sql);\n");
	    int idx = 1;
	    for (String f : crudFields) {
	        fw.write("$" + "stmt->bindValue(" + idx++ + ", $" + f + ");\n");

	    }

	    fw.write("$"+"stmt->execute();\n");
	    fw.write("echo \"Registro insertado\";\n");
	    fw.write("?>");
	    fw.close();
	}

	//------------------------------------
	//+-------------READ------------------
	//------------------------------------

	private void generateRead() throws Exception {
	    java.io.FileWriter fw = new java.io.FileWriter("read_" + crudTable + ".php");

	    fw.write("<?php\ninclude 'db.php';\n");
	    fw.write("$"+"stmt = "+"$"+"pdo->query(\"SELECT * FROM " + crudTable + "\");\n");
	    fw.write("while ("+"$"+"row = "+"$"+"stmt->fetch()) {\n");
	    fw.write("  print_r("+"$"+"row);\n");
	    fw.write("}\n?>");

	    fw.close();
	}

	//------------------------------------------
	//-----------UPDATE-------------------------
	//------------------------------------------

	private void generateUpdate() throws Exception {
	    java.io.FileWriter fw = new java.io.FileWriter("update_" + crudTable + ".php");

	    fw.write("<?php\ninclude 'db.php';\n");
	    fw.write("$"+"id = "+"$"+"_POST['id'];\n");

	    for (String f : crudFields) {
	        fw.write("$" + f + " = $" + "_POST['" + f + "'];\n");

	    }

	    fw.write("$"+"sql = \"UPDATE " + crudTable + " SET ");

	    for (int i = 0; i < crudFields.size(); i++) {
	        fw.write(crudFields.get(i) + "=?");
	        if (i < crudFields.size() - 1) fw.write(", ");
	    }

	    fw.write(" WHERE id=?\";\n");
	    fw.write("$"+"stmt = "+"$"+"pdo->prepare("+"$"+"sql);\n");

	    int idx = 1;
	    for (String f : crudFields) {
	        fw.write("$" + "stmt->bindValue(" + idx++ + ", $" + f + ");\n");

	    }

	    fw.write("$"+"stmt->bindValue(" + idx + ", "+"$"+"id);\n");
	    fw.write("$"+"stmt->execute();\n");
	    fw.write("echo \"Actualizado\";\n?>");

	    fw.close();
	}

	//-----------------------------------------------------------
	//---------------DELETE-------------------------------------
	//------------------------------------------------------------

	private void generateDelete() throws Exception {
	    java.io.FileWriter fw = new java.io.FileWriter("delete_" + crudTable + ".php");

	    fw.write("<?php\ninclude 'db.php';\n");
	    fw.write("$"+"id = "+"$"+"_GET['id'];\n");
	    fw.write("$"+"stmt = "+"$"+"pdo->prepare(\"DELETE FROM " + crudTable + " WHERE id=?\");\n");
	    fw.write("$"+"stmt->execute(["+"$"+"id]);\n");
	    fw.write("echo \"Eliminado\";\n?>");

	    fw.close();
	}


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



	// $ANTLR start "page"
	// src/WebPage.g:243:1: page : 'inicio' 'pagina' ( NL )? ( block )* 'fin' 'pagina' EOF ;
	public final void page() throws RecognitionException {
		try {
			// src/WebPage.g:244:5: ( 'inicio' 'pagina' ( NL )? ( block )* 'fin' 'pagina' EOF )
			// src/WebPage.g:244:7: 'inicio' 'pagina' ( NL )? ( block )* 'fin' 'pagina' EOF
			{
			match(input,18,FOLLOW_18_in_page50); 
			match(input,22,FOLLOW_22_in_page52); 
			// src/WebPage.g:244:25: ( NL )?
			int alt1=2;
			int LA1_0 = input.LA(1);
			if ( (LA1_0==NL) ) {
				alt1=1;
			}
			switch (alt1) {
				case 1 :
					// src/WebPage.g:244:25: NL
					{
					match(input,NL,FOLLOW_NL_in_page54); 
					}
					break;

			}

			// src/WebPage.g:244:29: ( block )*
			loop2:
			while (true) {
				int alt2=2;
				int LA2_0 = input.LA(1);
				if ( ((LA2_0 >= 13 && LA2_0 <= 14)||(LA2_0 >= 19 && LA2_0 <= 20)||LA2_0==23||(LA2_0 >= 26 && LA2_0 <= 27)) ) {
					alt2=1;
				}

				switch (alt2) {
				case 1 :
					// src/WebPage.g:244:29: block
					{
					pushFollow(FOLLOW_block_in_page57);
					block();
					state._fsp--;

					}
					break;

				default :
					break loop2;
				}
			}

			match(input,16,FOLLOW_16_in_page60); 
			match(input,22,FOLLOW_22_in_page62); 
			match(input,EOF,FOLLOW_EOF_in_page64); 

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

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "page"



	// $ANTLR start "block"
	// src/WebPage.g:265:1: block : ( linea_simple | titulo | menu | seccion | texto | crud_block | estilos );
	public final void block() throws RecognitionException {
		try {
			// src/WebPage.g:266:5: ( linea_simple | titulo | menu | seccion | texto | crud_block | estilos )
			int alt3=7;
			switch ( input.LA(1) ) {
			case 19:
				{
				alt3=1;
				}
				break;
			case 27:
				{
				alt3=2;
				}
				break;
			case 20:
				{
				alt3=3;
				}
				break;
			case 23:
				{
				alt3=4;
				}
				break;
			case 26:
				{
				alt3=5;
				}
				break;
			case 13:
				{
				alt3=6;
				}
				break;
			case 14:
				{
				alt3=7;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 3, 0, input);
				throw nvae;
			}
			switch (alt3) {
				case 1 :
					// src/WebPage.g:266:7: linea_simple
					{
					pushFollow(FOLLOW_linea_simple_in_block89);
					linea_simple();
					state._fsp--;

					}
					break;
				case 2 :
					// src/WebPage.g:267:7: titulo
					{
					pushFollow(FOLLOW_titulo_in_block97);
					titulo();
					state._fsp--;

					}
					break;
				case 3 :
					// src/WebPage.g:268:7: menu
					{
					pushFollow(FOLLOW_menu_in_block105);
					menu();
					state._fsp--;

					}
					break;
				case 4 :
					// src/WebPage.g:269:7: seccion
					{
					pushFollow(FOLLOW_seccion_in_block113);
					seccion();
					state._fsp--;

					}
					break;
				case 5 :
					// src/WebPage.g:270:7: texto
					{
					pushFollow(FOLLOW_texto_in_block121);
					texto();
					state._fsp--;

					}
					break;
				case 6 :
					// src/WebPage.g:271:7: crud_block
					{
					pushFollow(FOLLOW_crud_block_in_block129);
					crud_block();
					state._fsp--;


					        //evita error de duplicidad del crud al generar
					        crudFields.clear();
					        //genera el sql y php de ese bloque
					        generateSQLAndPHP();
					    
					}
					break;
				case 7 :
					// src/WebPage.g:277:7: estilos
					{
					pushFollow(FOLLOW_estilos_in_block139);
					estilos();
					state._fsp--;

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "block"



	// $ANTLR start "linea_simple"
	// src/WebPage.g:284:1: linea_simple : 'linea' 'simple' ( NL )? ;
	public final void linea_simple() throws RecognitionException {
		try {
			// src/WebPage.g:285:5: ( 'linea' 'simple' ( NL )? )
			// src/WebPage.g:285:7: 'linea' 'simple' ( NL )?
			{
			match(input,19,FOLLOW_19_in_linea_simple159); 
			match(input,24,FOLLOW_24_in_linea_simple161); 
			// src/WebPage.g:285:24: ( NL )?
			int alt4=2;
			int LA4_0 = input.LA(1);
			if ( (LA4_0==NL) ) {
				alt4=1;
			}
			switch (alt4) {
				case 1 :
					// src/WebPage.g:285:24: NL
					{
					match(input,NL,FOLLOW_NL_in_linea_simple163); 
					}
					break;

			}


			         html.append("<hr/>\n");
			      
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "linea_simple"


	public static class titulo_return extends ParserRuleReturnScope {
	};


	// $ANTLR start "titulo"
	// src/WebPage.g:292:1: titulo : 'titulo' s= STRING ( 'estilo' st= STRING )? ( NL )? ;
	public final WebPageParser.titulo_return titulo() throws RecognitionException {
		WebPageParser.titulo_return retval = new WebPageParser.titulo_return();
		retval.start = input.LT(1);

		Token s=null;
		Token st=null;

		try {
			// src/WebPage.g:293:5: ( 'titulo' s= STRING ( 'estilo' st= STRING )? ( NL )? )
			// src/WebPage.g:293:7: 'titulo' s= STRING ( 'estilo' st= STRING )? ( NL )?
			{
			match(input,27,FOLLOW_27_in_titulo190); 
			s=(Token)match(input,STRING,FOLLOW_STRING_in_titulo194); 
			// src/WebPage.g:293:25: ( 'estilo' st= STRING )?
			int alt5=2;
			int LA5_0 = input.LA(1);
			if ( (LA5_0==15) ) {
				alt5=1;
			}
			switch (alt5) {
				case 1 :
					// src/WebPage.g:293:27: 'estilo' st= STRING
					{
					match(input,15,FOLLOW_15_in_titulo198); 
					st=(Token)match(input,STRING,FOLLOW_STRING_in_titulo202); 
					}
					break;

			}

			// src/WebPage.g:293:49: ( NL )?
			int alt6=2;
			int LA6_0 = input.LA(1);
			if ( (LA6_0==NL) ) {
				alt6=1;
			}
			switch (alt6) {
				case 1 :
					// src/WebPage.g:293:49: NL
					{
					match(input,NL,FOLLOW_NL_in_titulo207); 
					}
					break;

			}


			         String content = strip(s.getText());
			         String styleAttr = "";
			         if (st != null) styleAttr = " style=\"" + strip(st.getText()) + "\"";
			         html.append("<h1" + styleAttr + ">" + escapeHtml(content) + "</h1>\n");
			      
			}

			retval.stop = input.LT(-1);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "titulo"



	// $ANTLR start "menu"
	// src/WebPage.g:302:1: menu : 'menu' 'opciones' ( NL )? ;
	public final void menu() throws RecognitionException {
		try {
			// src/WebPage.g:303:5: ( 'menu' 'opciones' ( NL )? )
			// src/WebPage.g:303:7: 'menu' 'opciones' ( NL )?
			{
			match(input,20,FOLLOW_20_in_menu233); 
			match(input,21,FOLLOW_21_in_menu235); 
			// src/WebPage.g:303:25: ( NL )?
			int alt7=2;
			int LA7_0 = input.LA(1);
			if ( (LA7_0==NL) ) {
				alt7=1;
			}
			switch (alt7) {
				case 1 :
					// src/WebPage.g:303:25: NL
					{
					match(input,NL,FOLLOW_NL_in_menu237); 
					}
					break;

			}


			         html.append("<nav><ul><li>Inicio</li><li>Productos</li><li>Contacto</li></ul></nav>\n");
			         // ejemplo css por defecto
			         css.append("nav ul { list-style:none; padding:6px; background:#f2f2f2; }\n");
			         css.append("nav li { display:inline; margin-right:10px; }\n");
			      
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "menu"


	public static class texto_return extends ParserRuleReturnScope {
	};


	// $ANTLR start "texto"
	// src/WebPage.g:312:1: texto : 'texto' s= STRING ( 'estilo' st= STRING )? ( NL )? ;
	public final WebPageParser.texto_return texto() throws RecognitionException {
		WebPageParser.texto_return retval = new WebPageParser.texto_return();
		retval.start = input.LT(1);

		Token s=null;
		Token st=null;

		try {
			// src/WebPage.g:313:5: ( 'texto' s= STRING ( 'estilo' st= STRING )? ( NL )? )
			// src/WebPage.g:313:7: 'texto' s= STRING ( 'estilo' st= STRING )? ( NL )?
			{
			match(input,26,FOLLOW_26_in_texto263); 
			s=(Token)match(input,STRING,FOLLOW_STRING_in_texto267); 
			// src/WebPage.g:313:24: ( 'estilo' st= STRING )?
			int alt8=2;
			int LA8_0 = input.LA(1);
			if ( (LA8_0==15) ) {
				alt8=1;
			}
			switch (alt8) {
				case 1 :
					// src/WebPage.g:313:26: 'estilo' st= STRING
					{
					match(input,15,FOLLOW_15_in_texto271); 
					st=(Token)match(input,STRING,FOLLOW_STRING_in_texto275); 
					}
					break;

			}

			// src/WebPage.g:313:48: ( NL )?
			int alt9=2;
			int LA9_0 = input.LA(1);
			if ( (LA9_0==NL) ) {
				alt9=1;
			}
			switch (alt9) {
				case 1 :
					// src/WebPage.g:313:48: NL
					{
					match(input,NL,FOLLOW_NL_in_texto280); 
					}
					break;

			}


			         String content = strip(s.getText());
			         String styleAttr = "";
			         if (st != null) styleAttr = " style=\"" + strip(st.getText()) + "\"";
			         html.append("<p" + styleAttr + ">" + escapeHtml(content) + "</p>\n");
			      
			}

			retval.stop = input.LT(-1);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "texto"



	// $ANTLR start "seccion"
	// src/WebPage.g:326:1: seccion : 'seccion' IDENT (il= imagenList )? ( atributo )* ( NL )? ;
	public final void seccion() throws RecognitionException {
		Token IDENT1=null;
		String il =null;

		try {
			// src/WebPage.g:327:5: ( 'seccion' IDENT (il= imagenList )? ( atributo )* ( NL )? )
			// src/WebPage.g:327:7: 'seccion' IDENT (il= imagenList )? ( atributo )* ( NL )?
			{
			match(input,23,FOLLOW_23_in_seccion309); 
			IDENT1=(Token)match(input,IDENT,FOLLOW_IDENT_in_seccion311); 
			// src/WebPage.g:327:23: (il= imagenList )?
			int alt10=2;
			int LA10_0 = input.LA(1);
			if ( (LA10_0==IDENT) ) {
				int LA10_1 = input.LA(2);
				if ( ((LA10_1 >= IDENT && LA10_1 <= NL)||(LA10_1 >= 13 && LA10_1 <= 14)||LA10_1==16||(LA10_1 >= 19 && LA10_1 <= 20)||LA10_1==23||(LA10_1 >= 26 && LA10_1 <= 27)) ) {
					alt10=1;
				}
			}
			switch (alt10) {
				case 1 :
					// src/WebPage.g:327:24: il= imagenList
					{
					pushFollow(FOLLOW_imagenList_in_seccion316);
					il=imagenList();
					state._fsp--;

					}
					break;

			}

			// src/WebPage.g:327:40: ( atributo )*
			loop11:
			while (true) {
				int alt11=2;
				int LA11_0 = input.LA(1);
				if ( (LA11_0==IDENT) ) {
					alt11=1;
				}

				switch (alt11) {
				case 1 :
					// src/WebPage.g:327:41: atributo
					{
					pushFollow(FOLLOW_atributo_in_seccion321);
					atributo();
					state._fsp--;

					}
					break;

				default :
					break loop11;
				}
			}

			// src/WebPage.g:327:52: ( NL )?
			int alt12=2;
			int LA12_0 = input.LA(1);
			if ( (LA12_0==NL) ) {
				alt12=1;
			}
			switch (alt12) {
				case 1 :
					// src/WebPage.g:327:52: NL
					{
					match(input,NL,FOLLOW_NL_in_seccion325); 
					}
					break;

			}


			         String id = IDENT1.getText();
			         html.append("<section id=\"" + escapeHtml(id) + "\">\n");
			         if (il != null) {
			             html.append(il); // usamos la variable local "il"
			         }
			         html.append("</section>\n");
			      
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "seccion"



	// $ANTLR start "imagenList"
	// src/WebPage.g:340:1: imagenList returns [String text] : first= IDENT (rest+= IDENT )* ;
	public final String imagenList() throws RecognitionException {
		String text = null;


		Token first=null;
		Token rest=null;
		List<Object> list_rest=null;

		try {
			// src/WebPage.g:342:5: (first= IDENT (rest+= IDENT )* )
			// src/WebPage.g:342:7: first= IDENT (rest+= IDENT )*
			{
			first=(Token)match(input,IDENT,FOLLOW_IDENT_in_imagenList363); 
			// src/WebPage.g:342:19: (rest+= IDENT )*
			loop13:
			while (true) {
				int alt13=2;
				int LA13_0 = input.LA(1);
				if ( (LA13_0==IDENT) ) {
					int LA13_1 = input.LA(2);
					if ( ((LA13_1 >= IDENT && LA13_1 <= NL)||(LA13_1 >= 13 && LA13_1 <= 14)||LA13_1==16||(LA13_1 >= 19 && LA13_1 <= 20)||LA13_1==23||(LA13_1 >= 26 && LA13_1 <= 27)) ) {
						alt13=1;
					}

				}

				switch (alt13) {
				case 1 :
					// src/WebPage.g:342:21: rest+= IDENT
					{
					rest=(Token)match(input,IDENT,FOLLOW_IDENT_in_imagenList369); 
					if (list_rest==null) list_rest=new ArrayList<Object>();
					list_rest.add(rest);
					}
					break;

				default :
					break loop13;
				}
			}


			          StringBuilder sb = new StringBuilder();
			          // primer ident
			          sb.append("<img src=\"" + escapeHtml(first.getText()) + "\" alt=\"" + escapeHtml(first.getText()) + "\"/>\n");
			          // resto (lista)
			          if (list_rest != null) {
			              for (int i=0;i<list_rest.size();i++) {
			                  org.antlr.runtime.Token t = (org.antlr.runtime.Token)list_rest.get(i);
			                  String name = t.getText();
			                  sb.append("<img src=\"" + escapeHtml(name) + "\" alt=\"" + escapeHtml(name) + "\"/>\n");
			              }
			          }
			          text = sb.toString();
			      
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return text;
	}
	// $ANTLR end "imagenList"



	// $ANTLR start "atributo"
	// src/WebPage.g:360:1: atributo : key= IDENT ':' val= STRING ( NL )? ;
	public final void atributo() throws RecognitionException {
		Token key=null;
		Token val=null;

		try {
			// src/WebPage.g:361:5: (key= IDENT ':' val= STRING ( NL )? )
			// src/WebPage.g:361:7: key= IDENT ':' val= STRING ( NL )?
			{
			key=(Token)match(input,IDENT,FOLLOW_IDENT_in_atributo401); 
			match(input,9,FOLLOW_9_in_atributo403); 
			val=(Token)match(input,STRING,FOLLOW_STRING_in_atributo407); 
			// src/WebPage.g:361:32: ( NL )?
			int alt14=2;
			int LA14_0 = input.LA(1);
			if ( (LA14_0==NL) ) {
				alt14=1;
			}
			switch (alt14) {
				case 1 :
					// src/WebPage.g:361:32: NL
					{
					match(input,NL,FOLLOW_NL_in_atributo409); 
					}
					break;

			}


			         html.append("<p><strong>" + escapeHtml(key.getText()) + ":</strong> " + escapeHtml(strip(val.getText())) + "</p>\n");
			      
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "atributo"



	// $ANTLR start "crud_block"
	// src/WebPage.g:371:1: crud_block : 'crud' 'inicio' ( NL )? 'bdd' db= IDENT ( NL )? 'tabla' tab= IDENT ( NL )? ( 'campo' f= IDENT ( NL )? )+ 'fintabla' ( NL )? 'crud' 'fin' ( NL )? ;
	public final void crud_block() throws RecognitionException {
		Token db=null;
		Token tab=null;
		Token f=null;

		try {
			// src/WebPage.g:372:5: ( 'crud' 'inicio' ( NL )? 'bdd' db= IDENT ( NL )? 'tabla' tab= IDENT ( NL )? ( 'campo' f= IDENT ( NL )? )+ 'fintabla' ( NL )? 'crud' 'fin' ( NL )? )
			// src/WebPage.g:372:7: 'crud' 'inicio' ( NL )? 'bdd' db= IDENT ( NL )? 'tabla' tab= IDENT ( NL )? ( 'campo' f= IDENT ( NL )? )+ 'fintabla' ( NL )? 'crud' 'fin' ( NL )?
			{
			match(input,13,FOLLOW_13_in_crud_block438); 
			match(input,18,FOLLOW_18_in_crud_block440); 
			// src/WebPage.g:372:23: ( NL )?
			int alt15=2;
			int LA15_0 = input.LA(1);
			if ( (LA15_0==NL) ) {
				alt15=1;
			}
			switch (alt15) {
				case 1 :
					// src/WebPage.g:372:23: NL
					{
					match(input,NL,FOLLOW_NL_in_crud_block442); 
					}
					break;

			}

			match(input,11,FOLLOW_11_in_crud_block453); 
			db=(Token)match(input,IDENT,FOLLOW_IDENT_in_crud_block457); 
			// src/WebPage.g:373:24: ( NL )?
			int alt16=2;
			int LA16_0 = input.LA(1);
			if ( (LA16_0==NL) ) {
				alt16=1;
			}
			switch (alt16) {
				case 1 :
					// src/WebPage.g:373:24: NL
					{
					match(input,NL,FOLLOW_NL_in_crud_block459); 
					}
					break;

			}

			match(input,25,FOLLOW_25_in_crud_block470); 
			tab=(Token)match(input,IDENT,FOLLOW_IDENT_in_crud_block474); 
			// src/WebPage.g:374:27: ( NL )?
			int alt17=2;
			int LA17_0 = input.LA(1);
			if ( (LA17_0==NL) ) {
				alt17=1;
			}
			switch (alt17) {
				case 1 :
					// src/WebPage.g:374:27: NL
					{
					match(input,NL,FOLLOW_NL_in_crud_block476); 
					}
					break;

			}

			// src/WebPage.g:375:13: ( 'campo' f= IDENT ( NL )? )+
			int cnt19=0;
			loop19:
			while (true) {
				int alt19=2;
				int LA19_0 = input.LA(1);
				if ( (LA19_0==12) ) {
					alt19=1;
				}

				switch (alt19) {
				case 1 :
					// src/WebPage.g:375:15: 'campo' f= IDENT ( NL )?
					{
					match(input,12,FOLLOW_12_in_crud_block493); 
					f=(Token)match(input,IDENT,FOLLOW_IDENT_in_crud_block497); 
					// src/WebPage.g:375:31: ( NL )?
					int alt18=2;
					int LA18_0 = input.LA(1);
					if ( (LA18_0==NL) ) {
						alt18=1;
					}
					switch (alt18) {
						case 1 :
							// src/WebPage.g:375:31: NL
							{
							match(input,NL,FOLLOW_NL_in_crud_block499); 
							}
							break;

					}

					}
					break;

				default :
					if ( cnt19 >= 1 ) break loop19;
					EarlyExitException eee = new EarlyExitException(19, input);
					throw eee;
				}
				cnt19++;
			}

			match(input,17,FOLLOW_17_in_crud_block513); 
			// src/WebPage.g:376:20: ( NL )?
			int alt20=2;
			int LA20_0 = input.LA(1);
			if ( (LA20_0==NL) ) {
				alt20=1;
			}
			switch (alt20) {
				case 1 :
					// src/WebPage.g:376:20: NL
					{
					match(input,NL,FOLLOW_NL_in_crud_block515); 
					}
					break;

			}

			match(input,13,FOLLOW_13_in_crud_block524); 
			match(input,16,FOLLOW_16_in_crud_block526); 
			// src/WebPage.g:377:20: ( NL )?
			int alt21=2;
			int LA21_0 = input.LA(1);
			if ( (LA21_0==NL) ) {
				alt21=1;
			}
			switch (alt21) {
				case 1 :
					// src/WebPage.g:377:20: NL
					{
					match(input,NL,FOLLOW_NL_in_crud_block528); 
					}
					break;

			}


			          // guardar valores en members para la escritura final
			          crudDatabase = db.getText();
			          crudTable = tab.getText();
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

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "crud_block"



	// $ANTLR start "campo"
	// src/WebPage.g:397:1: campo : 'campo' name= IDENT ( NL )? ;
	public final void campo() throws RecognitionException {
		Token name=null;

		try {
			// src/WebPage.g:398:5: ( 'campo' name= IDENT ( NL )? )
			// src/WebPage.g:398:7: 'campo' name= IDENT ( NL )?
			{
			match(input,12,FOLLOW_12_in_campo557); 
			name=(Token)match(input,IDENT,FOLLOW_IDENT_in_campo561); 
			// src/WebPage.g:398:26: ( NL )?
			int alt22=2;
			int LA22_0 = input.LA(1);
			if ( (LA22_0==NL) ) {
				alt22=1;
			}
			switch (alt22) {
				case 1 :
					// src/WebPage.g:398:26: NL
					{
					match(input,NL,FOLLOW_NL_in_campo563); 
					}
					break;

			}


			         crudFields.add(name.getText());
			      
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "campo"



	// $ANTLR start "estilos"
	// src/WebPage.g:408:1: estilos : 'css' 'inicio' ( NL )? ( regla )+ 'css' 'fin' ( NL )? ;
	public final void estilos() throws RecognitionException {
		try {
			// src/WebPage.g:409:5: ( 'css' 'inicio' ( NL )? ( regla )+ 'css' 'fin' ( NL )? )
			// src/WebPage.g:409:7: 'css' 'inicio' ( NL )? ( regla )+ 'css' 'fin' ( NL )?
			{
			match(input,14,FOLLOW_14_in_estilos592); 
			match(input,18,FOLLOW_18_in_estilos594); 
			// src/WebPage.g:409:22: ( NL )?
			int alt23=2;
			int LA23_0 = input.LA(1);
			if ( (LA23_0==NL) ) {
				alt23=1;
			}
			switch (alt23) {
				case 1 :
					// src/WebPage.g:409:22: NL
					{
					match(input,NL,FOLLOW_NL_in_estilos596); 
					}
					break;

			}

			// src/WebPage.g:409:26: ( regla )+
			int cnt24=0;
			loop24:
			while (true) {
				int alt24=2;
				int LA24_0 = input.LA(1);
				if ( (LA24_0==IDENT) ) {
					alt24=1;
				}

				switch (alt24) {
				case 1 :
					// src/WebPage.g:409:26: regla
					{
					pushFollow(FOLLOW_regla_in_estilos599);
					regla();
					state._fsp--;

					}
					break;

				default :
					if ( cnt24 >= 1 ) break loop24;
					EarlyExitException eee = new EarlyExitException(24, input);
					throw eee;
				}
				cnt24++;
			}

			match(input,14,FOLLOW_14_in_estilos602); 
			match(input,16,FOLLOW_16_in_estilos604); 
			// src/WebPage.g:409:45: ( NL )?
			int alt25=2;
			int LA25_0 = input.LA(1);
			if ( (LA25_0==NL) ) {
				alt25=1;
			}
			switch (alt25) {
				case 1 :
					// src/WebPage.g:409:45: NL
					{
					match(input,NL,FOLLOW_NL_in_estilos606); 
					}
					break;

			}


			         // link al css si no está ya agregado
			         if (html.indexOf("<link rel=\"stylesheet\"") == -1) {
			             html.insert(0, "<link rel=\"stylesheet\" href=\"styles.css\">\n");
			         }
			      
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "estilos"



	// $ANTLR start "regla"
	// src/WebPage.g:419:1: regla : selector= IDENT '{' ( NL )? (p1= propiedad )* '}' ( NL )? ;
	public final void regla() throws RecognitionException {
		Token selector=null;
		String p1 =null;

		try {
			// src/WebPage.g:420:5: (selector= IDENT '{' ( NL )? (p1= propiedad )* '}' ( NL )? )
			// src/WebPage.g:420:7: selector= IDENT '{' ( NL )? (p1= propiedad )* '}' ( NL )?
			{
			selector=(Token)match(input,IDENT,FOLLOW_IDENT_in_regla635); 
			match(input,28,FOLLOW_28_in_regla637); 
			// src/WebPage.g:420:26: ( NL )?
			int alt26=2;
			int LA26_0 = input.LA(1);
			if ( (LA26_0==NL) ) {
				alt26=1;
			}
			switch (alt26) {
				case 1 :
					// src/WebPage.g:420:26: NL
					{
					match(input,NL,FOLLOW_NL_in_regla639); 
					}
					break;

			}

			//if this does not work i don't know how to fix this | ayuda
			            java.util.List<String> props = new java.util.ArrayList<String>();
			        
			// src/WebPage.g:424:9: (p1= propiedad )*
			loop27:
			while (true) {
				int alt27=2;
				int LA27_0 = input.LA(1);
				if ( (LA27_0==IDENT) ) {
					alt27=1;
				}

				switch (alt27) {
				case 1 :
					// src/WebPage.g:424:10: p1= propiedad
					{
					pushFollow(FOLLOW_propiedad_in_regla663);
					p1=propiedad();
					state._fsp--;

					 props.add(p1); 
					}
					break;

				default :
					break loop27;
				}
			}

			match(input,29,FOLLOW_29_in_regla675); 
			// src/WebPage.g:425:11: ( NL )?
			int alt28=2;
			int LA28_0 = input.LA(1);
			if ( (LA28_0==NL) ) {
				alt28=1;
			}
			switch (alt28) {
				case 1 :
					// src/WebPage.g:425:11: NL
					{
					match(input,NL,FOLLOW_NL_in_regla677); 
					}
					break;

			}


			         css.append(selector.getText() + " {\n");
			         for (String line : props) {
			             css.append(line);
			         }
			         css.append("}\n");
			      
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "regla"



	// $ANTLR start "propiedad"
	// src/WebPage.g:438:1: propiedad returns [String line] : pname= IDENT ':' pval= ( IDENT | STRING ) ';' ( NL )? ;
	public final String propiedad() throws RecognitionException {
		String line = null;


		Token pname=null;
		Token pval=null;

		try {
			// src/WebPage.g:440:5: (pname= IDENT ':' pval= ( IDENT | STRING ) ';' ( NL )? )
			// src/WebPage.g:440:7: pname= IDENT ':' pval= ( IDENT | STRING ) ';' ( NL )?
			{
			pname=(Token)match(input,IDENT,FOLLOW_IDENT_in_propiedad717); 
			match(input,9,FOLLOW_9_in_propiedad719); 
			pval=input.LT(1);
			if ( input.LA(1)==IDENT||input.LA(1)==STRING ) {
				input.consume();
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			match(input,10,FOLLOW_10_in_propiedad729); 
			// src/WebPage.g:440:47: ( NL )?
			int alt29=2;
			int LA29_0 = input.LA(1);
			if ( (LA29_0==NL) ) {
				alt29=1;
			}
			switch (alt29) {
				case 1 :
					// src/WebPage.g:440:47: NL
					{
					match(input,NL,FOLLOW_NL_in_propiedad731); 
					}
					break;

			}


			         String v = pval.getText();
			         v = strip(v);
			         line = "  " + pname.getText() + ": " + v + ";\n";
			      
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return line;
	}
	// $ANTLR end "propiedad"

	// Delegated rules



	public static final BitSet FOLLOW_18_in_page50 = new BitSet(new long[]{0x0000000000400000L});
	public static final BitSet FOLLOW_22_in_page52 = new BitSet(new long[]{0x000000000C996020L});
	public static final BitSet FOLLOW_NL_in_page54 = new BitSet(new long[]{0x000000000C996000L});
	public static final BitSet FOLLOW_block_in_page57 = new BitSet(new long[]{0x000000000C996000L});
	public static final BitSet FOLLOW_16_in_page60 = new BitSet(new long[]{0x0000000000400000L});
	public static final BitSet FOLLOW_22_in_page62 = new BitSet(new long[]{0x0000000000000000L});
	public static final BitSet FOLLOW_EOF_in_page64 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_linea_simple_in_block89 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_titulo_in_block97 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_menu_in_block105 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_seccion_in_block113 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_texto_in_block121 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_crud_block_in_block129 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_estilos_in_block139 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_19_in_linea_simple159 = new BitSet(new long[]{0x0000000001000000L});
	public static final BitSet FOLLOW_24_in_linea_simple161 = new BitSet(new long[]{0x0000000000000022L});
	public static final BitSet FOLLOW_NL_in_linea_simple163 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_27_in_titulo190 = new BitSet(new long[]{0x0000000000000080L});
	public static final BitSet FOLLOW_STRING_in_titulo194 = new BitSet(new long[]{0x0000000000008022L});
	public static final BitSet FOLLOW_15_in_titulo198 = new BitSet(new long[]{0x0000000000000080L});
	public static final BitSet FOLLOW_STRING_in_titulo202 = new BitSet(new long[]{0x0000000000000022L});
	public static final BitSet FOLLOW_NL_in_titulo207 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_20_in_menu233 = new BitSet(new long[]{0x0000000000200000L});
	public static final BitSet FOLLOW_21_in_menu235 = new BitSet(new long[]{0x0000000000000022L});
	public static final BitSet FOLLOW_NL_in_menu237 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_26_in_texto263 = new BitSet(new long[]{0x0000000000000080L});
	public static final BitSet FOLLOW_STRING_in_texto267 = new BitSet(new long[]{0x0000000000008022L});
	public static final BitSet FOLLOW_15_in_texto271 = new BitSet(new long[]{0x0000000000000080L});
	public static final BitSet FOLLOW_STRING_in_texto275 = new BitSet(new long[]{0x0000000000000022L});
	public static final BitSet FOLLOW_NL_in_texto280 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_23_in_seccion309 = new BitSet(new long[]{0x0000000000000010L});
	public static final BitSet FOLLOW_IDENT_in_seccion311 = new BitSet(new long[]{0x0000000000000032L});
	public static final BitSet FOLLOW_imagenList_in_seccion316 = new BitSet(new long[]{0x0000000000000032L});
	public static final BitSet FOLLOW_atributo_in_seccion321 = new BitSet(new long[]{0x0000000000000032L});
	public static final BitSet FOLLOW_NL_in_seccion325 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IDENT_in_imagenList363 = new BitSet(new long[]{0x0000000000000012L});
	public static final BitSet FOLLOW_IDENT_in_imagenList369 = new BitSet(new long[]{0x0000000000000012L});
	public static final BitSet FOLLOW_IDENT_in_atributo401 = new BitSet(new long[]{0x0000000000000200L});
	public static final BitSet FOLLOW_9_in_atributo403 = new BitSet(new long[]{0x0000000000000080L});
	public static final BitSet FOLLOW_STRING_in_atributo407 = new BitSet(new long[]{0x0000000000000022L});
	public static final BitSet FOLLOW_NL_in_atributo409 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_13_in_crud_block438 = new BitSet(new long[]{0x0000000000040000L});
	public static final BitSet FOLLOW_18_in_crud_block440 = new BitSet(new long[]{0x0000000000000820L});
	public static final BitSet FOLLOW_NL_in_crud_block442 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_11_in_crud_block453 = new BitSet(new long[]{0x0000000000000010L});
	public static final BitSet FOLLOW_IDENT_in_crud_block457 = new BitSet(new long[]{0x0000000002000020L});
	public static final BitSet FOLLOW_NL_in_crud_block459 = new BitSet(new long[]{0x0000000002000000L});
	public static final BitSet FOLLOW_25_in_crud_block470 = new BitSet(new long[]{0x0000000000000010L});
	public static final BitSet FOLLOW_IDENT_in_crud_block474 = new BitSet(new long[]{0x0000000000001020L});
	public static final BitSet FOLLOW_NL_in_crud_block476 = new BitSet(new long[]{0x0000000000001000L});
	public static final BitSet FOLLOW_12_in_crud_block493 = new BitSet(new long[]{0x0000000000000010L});
	public static final BitSet FOLLOW_IDENT_in_crud_block497 = new BitSet(new long[]{0x0000000000021020L});
	public static final BitSet FOLLOW_NL_in_crud_block499 = new BitSet(new long[]{0x0000000000021000L});
	public static final BitSet FOLLOW_17_in_crud_block513 = new BitSet(new long[]{0x0000000000002020L});
	public static final BitSet FOLLOW_NL_in_crud_block515 = new BitSet(new long[]{0x0000000000002000L});
	public static final BitSet FOLLOW_13_in_crud_block524 = new BitSet(new long[]{0x0000000000010000L});
	public static final BitSet FOLLOW_16_in_crud_block526 = new BitSet(new long[]{0x0000000000000022L});
	public static final BitSet FOLLOW_NL_in_crud_block528 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_12_in_campo557 = new BitSet(new long[]{0x0000000000000010L});
	public static final BitSet FOLLOW_IDENT_in_campo561 = new BitSet(new long[]{0x0000000000000022L});
	public static final BitSet FOLLOW_NL_in_campo563 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_14_in_estilos592 = new BitSet(new long[]{0x0000000000040000L});
	public static final BitSet FOLLOW_18_in_estilos594 = new BitSet(new long[]{0x0000000000000030L});
	public static final BitSet FOLLOW_NL_in_estilos596 = new BitSet(new long[]{0x0000000000000010L});
	public static final BitSet FOLLOW_regla_in_estilos599 = new BitSet(new long[]{0x0000000000004010L});
	public static final BitSet FOLLOW_14_in_estilos602 = new BitSet(new long[]{0x0000000000010000L});
	public static final BitSet FOLLOW_16_in_estilos604 = new BitSet(new long[]{0x0000000000000022L});
	public static final BitSet FOLLOW_NL_in_estilos606 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IDENT_in_regla635 = new BitSet(new long[]{0x0000000010000000L});
	public static final BitSet FOLLOW_28_in_regla637 = new BitSet(new long[]{0x0000000020000030L});
	public static final BitSet FOLLOW_NL_in_regla639 = new BitSet(new long[]{0x0000000020000010L});
	public static final BitSet FOLLOW_propiedad_in_regla663 = new BitSet(new long[]{0x0000000020000010L});
	public static final BitSet FOLLOW_29_in_regla675 = new BitSet(new long[]{0x0000000000000022L});
	public static final BitSet FOLLOW_NL_in_regla677 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IDENT_in_propiedad717 = new BitSet(new long[]{0x0000000000000200L});
	public static final BitSet FOLLOW_9_in_propiedad719 = new BitSet(new long[]{0x0000000000000090L});
	public static final BitSet FOLLOW_set_in_propiedad723 = new BitSet(new long[]{0x0000000000000400L});
	public static final BitSet FOLLOW_10_in_propiedad729 = new BitSet(new long[]{0x0000000000000022L});
	public static final BitSet FOLLOW_NL_in_propiedad731 = new BitSet(new long[]{0x0000000000000002L});
}
