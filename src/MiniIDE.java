package compiladorhtml;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class MiniIDE extends JFrame {
    private JTextArea editor = new JTextArea(25, 70);
    private JEditorPane preview = new JEditorPane("text/html", "");
    private File currentFile = null;

    public MiniIDE() {
        super("WebLang IDE (ANTLR 3.5.2)");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Configurar editor
        editor.setFont(new Font("Consolas", Font.PLAIN, 14));
        editor.setTabSize(4);
        editor.setText("// Escribe tu código WebLang aquí...\n");

        // Configurar vista previa
        preview.setEditable(false);
        preview.setContentType("text/html");

        // Menú superior
        JMenuBar menuBar = new JMenuBar();
        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem nuevo = new JMenuItem("Nuevo");
        JMenuItem abrir = new JMenuItem("Abrir...");
        JMenuItem guardar = new JMenuItem("Guardar");
        JMenuItem guardarComo = new JMenuItem("Guardar como...");
        JMenuItem salir = new JMenuItem("Salir");

        menuArchivo.add(nuevo);
        menuArchivo.add(abrir);
        menuArchivo.add(guardar);
        menuArchivo.add(guardarComo);
        menuArchivo.addSeparator();
        menuArchivo.add(salir);
        menuBar.add(menuArchivo);
        setJMenuBar(menuBar);

        // Botón de compilación
        JButton build = new JButton("Compilar / Generar HTML");
        build.addActionListener(e -> compilarArchivoTemporal());

        // Divisor entre editor y vista previa
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(editor), new JScrollPane(preview));
        split.setResizeWeight(0.5);

        add(split, BorderLayout.CENTER);
        add(build, BorderLayout.SOUTH);

        // Acciones de menú
        nuevo.addActionListener(e -> nuevoArchivo());
        abrir.addActionListener(e -> abrirArchivo());
        guardar.addActionListener(e -> guardarArchivo(false));
        guardarComo.addActionListener(e -> guardarArchivo(true));
        salir.addActionListener(e -> System.exit(0));

        pack();
        setLocationRelativeTo(null);
    }

    /* -----------------------------
       Lógica principal del IDE
       ----------------------------- */

private void compilarArchivoTemporal() {
    try {
        // 1) Escribir el contenido actual a un archivo temporal
        File tmp = new File("temp_input.web");
        try (FileWriter fw = new FileWriter(tmp)) {
            fw.write(editor.getText());
        }

        // 2) Ejecutar el compilador / parser (Main) que genera output.html y styles.css
        compiladorhtml.Main.main(new String[] { "temp_input.web" });

        // 3) Forzar recarga y resolver CSS relativo correctamente:
        File htmlFile = new File("output.html");
        File cssFile = new File("styles.css");

        if (!htmlFile.exists()) {
            preview.setContentType("text/html");
            preview.setText("<html><body><h2>Error: No se generó output.html</h2></body></html>");
            return;
        }

        // Leer el HTML a un String (esto evita problemas de cache del URLConnection)
        StringBuilder htmlContent = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(htmlFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                htmlContent.append(line).append("\n");
            }
        }

        // Crear un documento HTML nuevo para el JEditorPane y fijar la "base" (URL) para recursos relativos
        javax.swing.text.html.HTMLEditorKit kit = new javax.swing.text.html.HTMLEditorKit();
        javax.swing.text.Document doc = kit.createDefaultDocument();
        // Establece la base para que <link href="styles.css"> sea resuelto correctamente
        if (htmlFile.getParentFile() != null) {
            ((javax.swing.text.html.HTMLDocument) doc).setBase(htmlFile.getParentFile().toURI().toURL());
        } else {
            ((javax.swing.text.html.HTMLDocument) doc).setBase(htmlFile.toURI().toURL());
        }

        preview.setEditorKit(kit);
        preview.setDocument(doc);
        preview.setText(htmlContent.toString()); // carga el HTML leido
        preview.setCaretPosition(0); // ir al inicio
        preview.revalidate();
        preview.repaint();

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al compilar: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}


    private void nuevoArchivo() {
        editor.setText("");
        currentFile = null;
        setTitle("BalIDE - Nuevo archivo");
    }

    private void abrirArchivo() {
        JFileChooser fc = new JFileChooser(".");
        fc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos Web (*.web)", "web"));
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            currentFile = fc.getSelectedFile();
            try (BufferedReader br = new BufferedReader(new FileReader(currentFile))) {
                editor.read(br, null);
                setTitle("BalIDE - " + currentFile.getName());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al abrir archivo: " + ex.getMessage());
            }
        }
    }

    private void guardarArchivo(boolean forzarNuevo) {
        try {
            if (forzarNuevo || currentFile == null) {
                JFileChooser fc = new JFileChooser(".");
                fc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos Web (*.web)", "web"));
                if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                    currentFile = fc.getSelectedFile();
                    if (!currentFile.getName().endsWith(".web")) {
                        currentFile = new File(currentFile.getAbsolutePath() + ".web");
                    }
                } else {
                    return;
                }
            }
            try (FileWriter fw = new FileWriter(currentFile)) {
                fw.write(editor.getText());
            }
            setTitle("BalID - " + currentFile.getName());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar archivo: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MiniIDE().setVisible(true));
    }
}
