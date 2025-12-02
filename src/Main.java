package compiladorhtml;
import org.antlr.runtime.*;
import java.io.*;
import compiladorhtml.WebPageLexer;
import compiladorhtml.WebPageParser;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Uso: java Main <archivo.web>");
            return;
        }
        String infile = args[0];
        ANTLRFileStream in = new ANTLRFileStream(infile, "UTF-8");
        WebPageLexer lex = new WebPageLexer(in);
        CommonTokenStream tokens = new CommonTokenStream(lex);
        WebPageParser parser = new WebPageParser(tokens);
        parser.page();

        // Generar salida HTML
        String html = parser.html.toString();
        try (FileWriter fw = new FileWriter("output.html")) {
            fw.write(html);
        }

        // Generar JSON CRUD
        try (FileWriter fw = new FileWriter("crud_spec.json")) {
            fw.write(parser.getCrudJson());
        }

        System.out.println("✅ Generado: output.html y crud_spec.json");
    }
}
