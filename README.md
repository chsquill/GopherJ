# GopherJ

GopherJ idea is around generating boilerplate code for repetive tasks. It is a code generator creating Java source files using structured input such as JSON, XML as well as other Java source files as input. 

# Running GopherJ on the command line

Execute Main.java  

# Running GopherJ UI

Execute GopherJUi.java  

# Purpose

GopherJ is based around generating boilerplate Java source files for predictable input. For example, programming against third party API’s has become common and can be tedious to create the Java code structures that map to an API’s output. Using structured input such as JSON and XML, GopherJ will generate the matching Java code. GopherJ provides both a command line interface as well as a GUI. The command line interactive menu prompts the user for an input file, generating output and writing the generated file to disk. The GUI provides an interface allowing users to use a file selector to pick a JSON input file and generate the output to the UI. Once displayed on the UI the user can copy the contents to the system clip-board or save the output to disk. An option to read previously stored files from disk and display them in the UI is available along with an audit trail written to a local database.  

# Dependencies

JavaPoet - (https://github.com/square/javapoet) - (Maven - https://mvnrepository.com/artifact/com.squareup/javapoet)

  Aid in programatically assembling a Java source file. 
  
Json Organization - (http://json.org) – (Maven - https://mvnrepository.com/artifact/org.json/json)

  Aid in parsing a json string.
  
JavaFx - (Provided with Oracles Java 8 JRE/JDK or newer releases. Also availble from https://openjfx.io)

  Used for GopherJ's user interface. 
