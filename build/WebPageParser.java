// $ANTLR 3.5.2 src/WebPage.g 2025-12-01 09:48:45

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
	// src/WebPage.g:91:1: page : 'inicio' 'pagina' ( NL )? ( block )* 'fin' 'pagina' EOF ;
	public final void page() throws RecognitionException {
		try {
			// src/WebPage.g:92:5: ( 'inicio' 'pagina' ( NL )? ( block )* 'fin' 'pagina' EOF )
			// src/WebPage.g:92:7: 'inicio' 'pagina' ( NL )? ( block )* 'fin' 'pagina' EOF
			{
			match(input,18,FOLLOW_18_in_page50); 
			match(input,22,FOLLOW_22_in_page52); 
			// src/WebPage.g:92:25: ( NL )?
			int alt1=2;
			int LA1_0 = input.LA(1);
			if ( (LA1_0==NL) ) {
				alt1=1;
			}
			switch (alt1) {
				case 1 :
					// src/WebPage.g:92:25: NL
					{
					match(input,NL,FOLLOW_NL_in_page54); 
					}
					break;

			}

			// src/WebPage.g:92:29: ( block )*
			loop2:
			while (true) {
				int alt2=2;
				int LA2_0 = input.LA(1);
				if ( ((LA2_0 >= 13 && LA2_0 <= 14)||(LA2_0 >= 19 && LA2_0 <= 20)||LA2_0==23||(LA2_0 >= 26 && LA2_0 <= 27)) ) {
					alt2=1;
				}

				switch (alt2) {
				case 1 :
					// src/WebPage.g:92:29: block
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
	// src/WebPage.g:113:1: block : ( linea_simple | titulo | menu | seccion | texto | crud_block | estilos );
	public final void block() throws RecognitionException {
		try {
			// src/WebPage.g:114:5: ( linea_simple | titulo | menu | seccion | texto | crud_block | estilos )
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
					// src/WebPage.g:114:7: linea_simple
					{
					pushFollow(FOLLOW_linea_simple_in_block89);
					linea_simple();
					state._fsp--;

					}
					break;
				case 2 :
					// src/WebPage.g:115:7: titulo
					{
					pushFollow(FOLLOW_titulo_in_block97);
					titulo();
					state._fsp--;

					}
					break;
				case 3 :
					// src/WebPage.g:116:7: menu
					{
					pushFollow(FOLLOW_menu_in_block105);
					menu();
					state._fsp--;

					}
					break;
				case 4 :
					// src/WebPage.g:117:7: seccion
					{
					pushFollow(FOLLOW_seccion_in_block113);
					seccion();
					state._fsp--;

					}
					break;
				case 5 :
					// src/WebPage.g:118:7: texto
					{
					pushFollow(FOLLOW_texto_in_block121);
					texto();
					state._fsp--;

					}
					break;
				case 6 :
					// src/WebPage.g:119:7: crud_block
					{
					pushFollow(FOLLOW_crud_block_in_block129);
					crud_block();
					state._fsp--;

					}
					break;
				case 7 :
					// src/WebPage.g:120:7: estilos
					{
					pushFollow(FOLLOW_estilos_in_block137);
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
	// src/WebPage.g:127:1: linea_simple : 'linea' 'simple' ( NL )? ;
	public final void linea_simple() throws RecognitionException {
		try {
			// src/WebPage.g:128:5: ( 'linea' 'simple' ( NL )? )
			// src/WebPage.g:128:7: 'linea' 'simple' ( NL )?
			{
			match(input,19,FOLLOW_19_in_linea_simple157); 
			match(input,24,FOLLOW_24_in_linea_simple159); 
			// src/WebPage.g:128:24: ( NL )?
			int alt4=2;
			int LA4_0 = input.LA(1);
			if ( (LA4_0==NL) ) {
				alt4=1;
			}
			switch (alt4) {
				case 1 :
					// src/WebPage.g:128:24: NL
					{
					match(input,NL,FOLLOW_NL_in_linea_simple161); 
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
	// src/WebPage.g:135:1: titulo : 'titulo' s= STRING ( 'estilo' st= STRING )? ( NL )? ;
	public final WebPageParser.titulo_return titulo() throws RecognitionException {
		WebPageParser.titulo_return retval = new WebPageParser.titulo_return();
		retval.start = input.LT(1);

		Token s=null;
		Token st=null;

		try {
			// src/WebPage.g:136:5: ( 'titulo' s= STRING ( 'estilo' st= STRING )? ( NL )? )
			// src/WebPage.g:136:7: 'titulo' s= STRING ( 'estilo' st= STRING )? ( NL )?
			{
			match(input,27,FOLLOW_27_in_titulo188); 
			s=(Token)match(input,STRING,FOLLOW_STRING_in_titulo192); 
			// src/WebPage.g:136:25: ( 'estilo' st= STRING )?
			int alt5=2;
			int LA5_0 = input.LA(1);
			if ( (LA5_0==15) ) {
				alt5=1;
			}
			switch (alt5) {
				case 1 :
					// src/WebPage.g:136:27: 'estilo' st= STRING
					{
					match(input,15,FOLLOW_15_in_titulo196); 
					st=(Token)match(input,STRING,FOLLOW_STRING_in_titulo200); 
					}
					break;

			}

			// src/WebPage.g:136:49: ( NL )?
			int alt6=2;
			int LA6_0 = input.LA(1);
			if ( (LA6_0==NL) ) {
				alt6=1;
			}
			switch (alt6) {
				case 1 :
					// src/WebPage.g:136:49: NL
					{
					match(input,NL,FOLLOW_NL_in_titulo205); 
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
	// src/WebPage.g:145:1: menu : 'menu' 'opciones' ( NL )? ;
	public final void menu() throws RecognitionException {
		try {
			// src/WebPage.g:146:5: ( 'menu' 'opciones' ( NL )? )
			// src/WebPage.g:146:7: 'menu' 'opciones' ( NL )?
			{
			match(input,20,FOLLOW_20_in_menu231); 
			match(input,21,FOLLOW_21_in_menu233); 
			// src/WebPage.g:146:25: ( NL )?
			int alt7=2;
			int LA7_0 = input.LA(1);
			if ( (LA7_0==NL) ) {
				alt7=1;
			}
			switch (alt7) {
				case 1 :
					// src/WebPage.g:146:25: NL
					{
					match(input,NL,FOLLOW_NL_in_menu235); 
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
	// src/WebPage.g:155:1: texto : 'texto' s= STRING ( 'estilo' st= STRING )? ( NL )? ;
	public final WebPageParser.texto_return texto() throws RecognitionException {
		WebPageParser.texto_return retval = new WebPageParser.texto_return();
		retval.start = input.LT(1);

		Token s=null;
		Token st=null;

		try {
			// src/WebPage.g:156:5: ( 'texto' s= STRING ( 'estilo' st= STRING )? ( NL )? )
			// src/WebPage.g:156:7: 'texto' s= STRING ( 'estilo' st= STRING )? ( NL )?
			{
			match(input,26,FOLLOW_26_in_texto261); 
			s=(Token)match(input,STRING,FOLLOW_STRING_in_texto265); 
			// src/WebPage.g:156:24: ( 'estilo' st= STRING )?
			int alt8=2;
			int LA8_0 = input.LA(1);
			if ( (LA8_0==15) ) {
				alt8=1;
			}
			switch (alt8) {
				case 1 :
					// src/WebPage.g:156:26: 'estilo' st= STRING
					{
					match(input,15,FOLLOW_15_in_texto269); 
					st=(Token)match(input,STRING,FOLLOW_STRING_in_texto273); 
					}
					break;

			}

			// src/WebPage.g:156:48: ( NL )?
			int alt9=2;
			int LA9_0 = input.LA(1);
			if ( (LA9_0==NL) ) {
				alt9=1;
			}
			switch (alt9) {
				case 1 :
					// src/WebPage.g:156:48: NL
					{
					match(input,NL,FOLLOW_NL_in_texto278); 
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
	// src/WebPage.g:169:1: seccion : 'seccion' IDENT (il= imagenList )? ( atributo )* ( NL )? ;
	public final void seccion() throws RecognitionException {
		Token IDENT1=null;
		String il =null;

		try {
			// src/WebPage.g:170:5: ( 'seccion' IDENT (il= imagenList )? ( atributo )* ( NL )? )
			// src/WebPage.g:170:7: 'seccion' IDENT (il= imagenList )? ( atributo )* ( NL )?
			{
			match(input,23,FOLLOW_23_in_seccion307); 
			IDENT1=(Token)match(input,IDENT,FOLLOW_IDENT_in_seccion309); 
			// src/WebPage.g:170:23: (il= imagenList )?
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
					// src/WebPage.g:170:24: il= imagenList
					{
					pushFollow(FOLLOW_imagenList_in_seccion314);
					il=imagenList();
					state._fsp--;

					}
					break;

			}

			// src/WebPage.g:170:40: ( atributo )*
			loop11:
			while (true) {
				int alt11=2;
				int LA11_0 = input.LA(1);
				if ( (LA11_0==IDENT) ) {
					alt11=1;
				}

				switch (alt11) {
				case 1 :
					// src/WebPage.g:170:41: atributo
					{
					pushFollow(FOLLOW_atributo_in_seccion319);
					atributo();
					state._fsp--;

					}
					break;

				default :
					break loop11;
				}
			}

			// src/WebPage.g:170:52: ( NL )?
			int alt12=2;
			int LA12_0 = input.LA(1);
			if ( (LA12_0==NL) ) {
				alt12=1;
			}
			switch (alt12) {
				case 1 :
					// src/WebPage.g:170:52: NL
					{
					match(input,NL,FOLLOW_NL_in_seccion323); 
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
	// src/WebPage.g:183:1: imagenList returns [String text] : first= IDENT (rest+= IDENT )* ;
	public final String imagenList() throws RecognitionException {
		String text = null;


		Token first=null;
		Token rest=null;
		List<Object> list_rest=null;

		try {
			// src/WebPage.g:185:5: (first= IDENT (rest+= IDENT )* )
			// src/WebPage.g:185:7: first= IDENT (rest+= IDENT )*
			{
			first=(Token)match(input,IDENT,FOLLOW_IDENT_in_imagenList361); 
			// src/WebPage.g:185:19: (rest+= IDENT )*
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
					// src/WebPage.g:185:21: rest+= IDENT
					{
					rest=(Token)match(input,IDENT,FOLLOW_IDENT_in_imagenList367); 
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
	// src/WebPage.g:203:1: atributo : key= IDENT ':' val= STRING ( NL )? ;
	public final void atributo() throws RecognitionException {
		Token key=null;
		Token val=null;

		try {
			// src/WebPage.g:204:5: (key= IDENT ':' val= STRING ( NL )? )
			// src/WebPage.g:204:7: key= IDENT ':' val= STRING ( NL )?
			{
			key=(Token)match(input,IDENT,FOLLOW_IDENT_in_atributo399); 
			match(input,9,FOLLOW_9_in_atributo401); 
			val=(Token)match(input,STRING,FOLLOW_STRING_in_atributo405); 
			// src/WebPage.g:204:32: ( NL )?
			int alt14=2;
			int LA14_0 = input.LA(1);
			if ( (LA14_0==NL) ) {
				alt14=1;
			}
			switch (alt14) {
				case 1 :
					// src/WebPage.g:204:32: NL
					{
					match(input,NL,FOLLOW_NL_in_atributo407); 
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
	// src/WebPage.g:214:1: crud_block : 'crud' 'inicio' ( NL )? 'bdd' db= IDENT ( NL )? 'tabla' tab= IDENT ( NL )? ( 'campo' f= IDENT ( NL )? )+ 'fintabla' ( NL )? 'crud' 'fin' ( NL )? ;
	public final void crud_block() throws RecognitionException {
		Token db=null;
		Token tab=null;
		Token f=null;

		try {
			// src/WebPage.g:215:5: ( 'crud' 'inicio' ( NL )? 'bdd' db= IDENT ( NL )? 'tabla' tab= IDENT ( NL )? ( 'campo' f= IDENT ( NL )? )+ 'fintabla' ( NL )? 'crud' 'fin' ( NL )? )
			// src/WebPage.g:215:7: 'crud' 'inicio' ( NL )? 'bdd' db= IDENT ( NL )? 'tabla' tab= IDENT ( NL )? ( 'campo' f= IDENT ( NL )? )+ 'fintabla' ( NL )? 'crud' 'fin' ( NL )?
			{
			match(input,13,FOLLOW_13_in_crud_block436); 
			match(input,18,FOLLOW_18_in_crud_block438); 
			// src/WebPage.g:215:23: ( NL )?
			int alt15=2;
			int LA15_0 = input.LA(1);
			if ( (LA15_0==NL) ) {
				alt15=1;
			}
			switch (alt15) {
				case 1 :
					// src/WebPage.g:215:23: NL
					{
					match(input,NL,FOLLOW_NL_in_crud_block440); 
					}
					break;

			}

			match(input,11,FOLLOW_11_in_crud_block451); 
			db=(Token)match(input,IDENT,FOLLOW_IDENT_in_crud_block455); 
			// src/WebPage.g:216:24: ( NL )?
			int alt16=2;
			int LA16_0 = input.LA(1);
			if ( (LA16_0==NL) ) {
				alt16=1;
			}
			switch (alt16) {
				case 1 :
					// src/WebPage.g:216:24: NL
					{
					match(input,NL,FOLLOW_NL_in_crud_block457); 
					}
					break;

			}

			match(input,25,FOLLOW_25_in_crud_block468); 
			tab=(Token)match(input,IDENT,FOLLOW_IDENT_in_crud_block472); 
			// src/WebPage.g:217:27: ( NL )?
			int alt17=2;
			int LA17_0 = input.LA(1);
			if ( (LA17_0==NL) ) {
				alt17=1;
			}
			switch (alt17) {
				case 1 :
					// src/WebPage.g:217:27: NL
					{
					match(input,NL,FOLLOW_NL_in_crud_block474); 
					}
					break;

			}

			// src/WebPage.g:218:13: ( 'campo' f= IDENT ( NL )? )+
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
					// src/WebPage.g:218:15: 'campo' f= IDENT ( NL )?
					{
					match(input,12,FOLLOW_12_in_crud_block491); 
					f=(Token)match(input,IDENT,FOLLOW_IDENT_in_crud_block495); 
					// src/WebPage.g:218:31: ( NL )?
					int alt18=2;
					int LA18_0 = input.LA(1);
					if ( (LA18_0==NL) ) {
						alt18=1;
					}
					switch (alt18) {
						case 1 :
							// src/WebPage.g:218:31: NL
							{
							match(input,NL,FOLLOW_NL_in_crud_block497); 
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

			match(input,17,FOLLOW_17_in_crud_block511); 
			// src/WebPage.g:219:20: ( NL )?
			int alt20=2;
			int LA20_0 = input.LA(1);
			if ( (LA20_0==NL) ) {
				alt20=1;
			}
			switch (alt20) {
				case 1 :
					// src/WebPage.g:219:20: NL
					{
					match(input,NL,FOLLOW_NL_in_crud_block513); 
					}
					break;

			}

			match(input,13,FOLLOW_13_in_crud_block522); 
			match(input,16,FOLLOW_16_in_crud_block524); 
			// src/WebPage.g:220:20: ( NL )?
			int alt21=2;
			int LA21_0 = input.LA(1);
			if ( (LA21_0==NL) ) {
				alt21=1;
			}
			switch (alt21) {
				case 1 :
					// src/WebPage.g:220:20: NL
					{
					match(input,NL,FOLLOW_NL_in_crud_block526); 
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
	// src/WebPage.g:240:1: campo : 'campo' name= IDENT ( NL )? ;
	public final void campo() throws RecognitionException {
		Token name=null;

		try {
			// src/WebPage.g:241:5: ( 'campo' name= IDENT ( NL )? )
			// src/WebPage.g:241:7: 'campo' name= IDENT ( NL )?
			{
			match(input,12,FOLLOW_12_in_campo555); 
			name=(Token)match(input,IDENT,FOLLOW_IDENT_in_campo559); 
			// src/WebPage.g:241:26: ( NL )?
			int alt22=2;
			int LA22_0 = input.LA(1);
			if ( (LA22_0==NL) ) {
				alt22=1;
			}
			switch (alt22) {
				case 1 :
					// src/WebPage.g:241:26: NL
					{
					match(input,NL,FOLLOW_NL_in_campo561); 
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
	// src/WebPage.g:251:1: estilos : 'css' 'inicio' ( NL )? ( regla )+ 'css' 'fin' ( NL )? ;
	public final void estilos() throws RecognitionException {
		try {
			// src/WebPage.g:252:5: ( 'css' 'inicio' ( NL )? ( regla )+ 'css' 'fin' ( NL )? )
			// src/WebPage.g:252:7: 'css' 'inicio' ( NL )? ( regla )+ 'css' 'fin' ( NL )?
			{
			match(input,14,FOLLOW_14_in_estilos590); 
			match(input,18,FOLLOW_18_in_estilos592); 
			// src/WebPage.g:252:22: ( NL )?
			int alt23=2;
			int LA23_0 = input.LA(1);
			if ( (LA23_0==NL) ) {
				alt23=1;
			}
			switch (alt23) {
				case 1 :
					// src/WebPage.g:252:22: NL
					{
					match(input,NL,FOLLOW_NL_in_estilos594); 
					}
					break;

			}

			// src/WebPage.g:252:26: ( regla )+
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
					// src/WebPage.g:252:26: regla
					{
					pushFollow(FOLLOW_regla_in_estilos597);
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

			match(input,14,FOLLOW_14_in_estilos600); 
			match(input,16,FOLLOW_16_in_estilos602); 
			// src/WebPage.g:252:45: ( NL )?
			int alt25=2;
			int LA25_0 = input.LA(1);
			if ( (LA25_0==NL) ) {
				alt25=1;
			}
			switch (alt25) {
				case 1 :
					// src/WebPage.g:252:45: NL
					{
					match(input,NL,FOLLOW_NL_in_estilos604); 
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
	// src/WebPage.g:262:1: regla : selector= IDENT '{' ( NL )? (p1= propiedad )* '}' ( NL )? ;
	public final void regla() throws RecognitionException {
		Token selector=null;
		String p1 =null;

		try {
			// src/WebPage.g:263:5: (selector= IDENT '{' ( NL )? (p1= propiedad )* '}' ( NL )? )
			// src/WebPage.g:263:7: selector= IDENT '{' ( NL )? (p1= propiedad )* '}' ( NL )?
			{
			selector=(Token)match(input,IDENT,FOLLOW_IDENT_in_regla633); 
			match(input,28,FOLLOW_28_in_regla635); 
			// src/WebPage.g:263:26: ( NL )?
			int alt26=2;
			int LA26_0 = input.LA(1);
			if ( (LA26_0==NL) ) {
				alt26=1;
			}
			switch (alt26) {
				case 1 :
					// src/WebPage.g:263:26: NL
					{
					match(input,NL,FOLLOW_NL_in_regla637); 
					}
					break;

			}

			//if this does not work i don't know how to fix this | ayuda
			            java.util.List<String> props = new java.util.ArrayList<String>();
			        
			// src/WebPage.g:267:9: (p1= propiedad )*
			loop27:
			while (true) {
				int alt27=2;
				int LA27_0 = input.LA(1);
				if ( (LA27_0==IDENT) ) {
					alt27=1;
				}

				switch (alt27) {
				case 1 :
					// src/WebPage.g:267:10: p1= propiedad
					{
					pushFollow(FOLLOW_propiedad_in_regla661);
					p1=propiedad();
					state._fsp--;

					 props.add(p1); 
					}
					break;

				default :
					break loop27;
				}
			}

			match(input,29,FOLLOW_29_in_regla673); 
			// src/WebPage.g:268:11: ( NL )?
			int alt28=2;
			int LA28_0 = input.LA(1);
			if ( (LA28_0==NL) ) {
				alt28=1;
			}
			switch (alt28) {
				case 1 :
					// src/WebPage.g:268:11: NL
					{
					match(input,NL,FOLLOW_NL_in_regla675); 
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
	// src/WebPage.g:281:1: propiedad returns [String line] : pname= IDENT ':' pval= ( IDENT | STRING ) ';' ( NL )? ;
	public final String propiedad() throws RecognitionException {
		String line = null;


		Token pname=null;
		Token pval=null;

		try {
			// src/WebPage.g:283:5: (pname= IDENT ':' pval= ( IDENT | STRING ) ';' ( NL )? )
			// src/WebPage.g:283:7: pname= IDENT ':' pval= ( IDENT | STRING ) ';' ( NL )?
			{
			pname=(Token)match(input,IDENT,FOLLOW_IDENT_in_propiedad715); 
			match(input,9,FOLLOW_9_in_propiedad717); 
			pval=input.LT(1);
			if ( input.LA(1)==IDENT||input.LA(1)==STRING ) {
				input.consume();
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			match(input,10,FOLLOW_10_in_propiedad727); 
			// src/WebPage.g:283:47: ( NL )?
			int alt29=2;
			int LA29_0 = input.LA(1);
			if ( (LA29_0==NL) ) {
				alt29=1;
			}
			switch (alt29) {
				case 1 :
					// src/WebPage.g:283:47: NL
					{
					match(input,NL,FOLLOW_NL_in_propiedad729); 
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
	public static final BitSet FOLLOW_estilos_in_block137 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_19_in_linea_simple157 = new BitSet(new long[]{0x0000000001000000L});
	public static final BitSet FOLLOW_24_in_linea_simple159 = new BitSet(new long[]{0x0000000000000022L});
	public static final BitSet FOLLOW_NL_in_linea_simple161 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_27_in_titulo188 = new BitSet(new long[]{0x0000000000000080L});
	public static final BitSet FOLLOW_STRING_in_titulo192 = new BitSet(new long[]{0x0000000000008022L});
	public static final BitSet FOLLOW_15_in_titulo196 = new BitSet(new long[]{0x0000000000000080L});
	public static final BitSet FOLLOW_STRING_in_titulo200 = new BitSet(new long[]{0x0000000000000022L});
	public static final BitSet FOLLOW_NL_in_titulo205 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_20_in_menu231 = new BitSet(new long[]{0x0000000000200000L});
	public static final BitSet FOLLOW_21_in_menu233 = new BitSet(new long[]{0x0000000000000022L});
	public static final BitSet FOLLOW_NL_in_menu235 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_26_in_texto261 = new BitSet(new long[]{0x0000000000000080L});
	public static final BitSet FOLLOW_STRING_in_texto265 = new BitSet(new long[]{0x0000000000008022L});
	public static final BitSet FOLLOW_15_in_texto269 = new BitSet(new long[]{0x0000000000000080L});
	public static final BitSet FOLLOW_STRING_in_texto273 = new BitSet(new long[]{0x0000000000000022L});
	public static final BitSet FOLLOW_NL_in_texto278 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_23_in_seccion307 = new BitSet(new long[]{0x0000000000000010L});
	public static final BitSet FOLLOW_IDENT_in_seccion309 = new BitSet(new long[]{0x0000000000000032L});
	public static final BitSet FOLLOW_imagenList_in_seccion314 = new BitSet(new long[]{0x0000000000000032L});
	public static final BitSet FOLLOW_atributo_in_seccion319 = new BitSet(new long[]{0x0000000000000032L});
	public static final BitSet FOLLOW_NL_in_seccion323 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IDENT_in_imagenList361 = new BitSet(new long[]{0x0000000000000012L});
	public static final BitSet FOLLOW_IDENT_in_imagenList367 = new BitSet(new long[]{0x0000000000000012L});
	public static final BitSet FOLLOW_IDENT_in_atributo399 = new BitSet(new long[]{0x0000000000000200L});
	public static final BitSet FOLLOW_9_in_atributo401 = new BitSet(new long[]{0x0000000000000080L});
	public static final BitSet FOLLOW_STRING_in_atributo405 = new BitSet(new long[]{0x0000000000000022L});
	public static final BitSet FOLLOW_NL_in_atributo407 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_13_in_crud_block436 = new BitSet(new long[]{0x0000000000040000L});
	public static final BitSet FOLLOW_18_in_crud_block438 = new BitSet(new long[]{0x0000000000000820L});
	public static final BitSet FOLLOW_NL_in_crud_block440 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_11_in_crud_block451 = new BitSet(new long[]{0x0000000000000010L});
	public static final BitSet FOLLOW_IDENT_in_crud_block455 = new BitSet(new long[]{0x0000000002000020L});
	public static final BitSet FOLLOW_NL_in_crud_block457 = new BitSet(new long[]{0x0000000002000000L});
	public static final BitSet FOLLOW_25_in_crud_block468 = new BitSet(new long[]{0x0000000000000010L});
	public static final BitSet FOLLOW_IDENT_in_crud_block472 = new BitSet(new long[]{0x0000000000001020L});
	public static final BitSet FOLLOW_NL_in_crud_block474 = new BitSet(new long[]{0x0000000000001000L});
	public static final BitSet FOLLOW_12_in_crud_block491 = new BitSet(new long[]{0x0000000000000010L});
	public static final BitSet FOLLOW_IDENT_in_crud_block495 = new BitSet(new long[]{0x0000000000021020L});
	public static final BitSet FOLLOW_NL_in_crud_block497 = new BitSet(new long[]{0x0000000000021000L});
	public static final BitSet FOLLOW_17_in_crud_block511 = new BitSet(new long[]{0x0000000000002020L});
	public static final BitSet FOLLOW_NL_in_crud_block513 = new BitSet(new long[]{0x0000000000002000L});
	public static final BitSet FOLLOW_13_in_crud_block522 = new BitSet(new long[]{0x0000000000010000L});
	public static final BitSet FOLLOW_16_in_crud_block524 = new BitSet(new long[]{0x0000000000000022L});
	public static final BitSet FOLLOW_NL_in_crud_block526 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_12_in_campo555 = new BitSet(new long[]{0x0000000000000010L});
	public static final BitSet FOLLOW_IDENT_in_campo559 = new BitSet(new long[]{0x0000000000000022L});
	public static final BitSet FOLLOW_NL_in_campo561 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_14_in_estilos590 = new BitSet(new long[]{0x0000000000040000L});
	public static final BitSet FOLLOW_18_in_estilos592 = new BitSet(new long[]{0x0000000000000030L});
	public static final BitSet FOLLOW_NL_in_estilos594 = new BitSet(new long[]{0x0000000000000010L});
	public static final BitSet FOLLOW_regla_in_estilos597 = new BitSet(new long[]{0x0000000000004010L});
	public static final BitSet FOLLOW_14_in_estilos600 = new BitSet(new long[]{0x0000000000010000L});
	public static final BitSet FOLLOW_16_in_estilos602 = new BitSet(new long[]{0x0000000000000022L});
	public static final BitSet FOLLOW_NL_in_estilos604 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IDENT_in_regla633 = new BitSet(new long[]{0x0000000010000000L});
	public static final BitSet FOLLOW_28_in_regla635 = new BitSet(new long[]{0x0000000020000030L});
	public static final BitSet FOLLOW_NL_in_regla637 = new BitSet(new long[]{0x0000000020000010L});
	public static final BitSet FOLLOW_propiedad_in_regla661 = new BitSet(new long[]{0x0000000020000010L});
	public static final BitSet FOLLOW_29_in_regla673 = new BitSet(new long[]{0x0000000000000022L});
	public static final BitSet FOLLOW_NL_in_regla675 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IDENT_in_propiedad715 = new BitSet(new long[]{0x0000000000000200L});
	public static final BitSet FOLLOW_9_in_propiedad717 = new BitSet(new long[]{0x0000000000000090L});
	public static final BitSet FOLLOW_set_in_propiedad721 = new BitSet(new long[]{0x0000000000000400L});
	public static final BitSet FOLLOW_10_in_propiedad727 = new BitSet(new long[]{0x0000000000000022L});
	public static final BitSet FOLLOW_NL_in_propiedad729 = new BitSet(new long[]{0x0000000000000002L});
}
