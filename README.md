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
