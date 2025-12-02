# WebLang-Compilator
A repertory made to practice the creation of a WebLang compilator. 

# How to use

Before starting, make sure you have `Java` installed and added to your `classpath`. Open a PS Console (No admin rights needed) in the root of the downloaded contents and type the following commands, depending on the current need.

### To compilate a new grammatic

```PowerShell
java -jar lib/antlr-3.5.2-complete.jar src/WebPage.g -o build
```

### To compilate a new build

```PowerShell
javac -cp "lib/antlr-3.5.2-complete.jar;build" -d build src/*.java build/*.java
```

### To use the programm with out using the IDE

```PowerShell
java -cp "lib/antlr-3.5.2-complete.jar;build" compiladorhtml.Main src/ejemplo.web
```

If you want to use another source archive, you can change the command. Just make sure the file is inside the "src" folder, and has .web extension. 

```PowerShell
java -cp "lib/antlr-3.5.2-complete.jar;build" compiladorhtml.Main src/<your-file>.web
```

### To use the programm using the in-build IDE (early stage in dev)

```PowerShell
java -cp "lib/antlr-3.5.2-complete.jar;build" compiladorhtml.MiniIDE
```

# What can it do?


| **Element / Rule**                         | **Description**                                          | **Sample**                              |
| -------------------------------------------- | -------------------------------------------------------- | ----------------------------------------------- |
| **Inicio de página**                         | Estructura obligatoria que envuelve todo.                | `inicio pagina`...`fin pagina`                  |
| **Título** (`titulo`)                        | Inserta un `<h1>` con texto y estilo opcional.           | `titulo "Bienvenido" estilo "color:red;"`       |
| **Texto** (`texto`)                          | Inserta un párrafo `<p>` con estilo opcional.            | `texto "Hola mundo" estilo "font-weight:bold;"` |
| **Línea simple** (`linea simple`)            | Inserta `<hr/>`.                                         | `linea simple`                                  |
| **Menú** (`menu opciones`)                   | Inserta un menú fijo `<nav><ul>…</ul></nav>`.            | `menu opciones`                                 |
| **Sección** (`seccion`)                      | Crea `<section id="...">`.                               | `seccion galeria`                               |
| **Lista de imágenes** (`imagenList`)         | Lista de IDENT consecutivos crea múltiples `<img>`.      | `img1 img2 img3`                                |
| **Atributos dentro de sección** (`atributo`) | Agrega `<p><strong>clave:</strong> valor</p>`.           | `telefono: "12345"`                             |
| **Bloque CRUD** (`crud inicio ... crud fin`) | Registra base, tabla y campos, genera JSON y tabla HTML. | Ver sección CRUD abajo.                         |
| **Campo CRUD** (`campo`)                     | Se guarda en `crudFields`.                               | `campo nombre`                                  |
| **Bloque CSS** (`css inicio ... css fin`)    | Genera código para `styles.css` y agrega `<link>`.       | Ver sección CSS abajo.                          |
| **Regla CSS** (`regla`)                      | Una regla con selector y propiedades.                    | `body { margin: "10px"; }`                      |
| **Propiedad CSS** (`propiedad`)              | Línea `prop: valor;` dentro del bloque.                  | `color: "blue";`                                |
| **Strings** (`STRING`)                       | Cadena entre comillas.                                   | `"Hola"`                                        |
| **Identificadores** (`IDENT`)                | Letras, números, guiones, puntos, guion bajo.            | `foto1`                                         |
| **Comentarios** (`// ...`)                   | Comentarios de una línea.                                | `// esto es un comentario`                      |



