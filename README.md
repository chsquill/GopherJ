# GopherJ

GopherJ idea is around generating boilerplate code for repetive tasks. It is a code generator creating Java source files using structured input such as JSON, XML as well as other Java source files as input. 

# Running GopherJ on the command line

Execute Main.java  

# Running GopherJ UI

Execute GopherJUi.java  

# Purpose

GopherJ helps avoid the tedious task of creating boilerplate code manually. For example, programming against third party API’s has become common and it can be tedious to create the Java code structures that will map to the API’s output. The goal is to identify the structure of the input (JSON, XML) and parse it into a compilable Java code structure maintaining the inputs fields names, types and arrays. 

A second use case is to generate JUnit test boilerplate code over an existing codebase of Java services. In this scenario it would find all public methods and create @Test methods per-public method.  

# Dependencies

JavaPoet - (https://github.com/square/javapoet) - (Maven - https://mvnrepository.com/artifact/com.squareup/javapoet)

  Aid in programatically assembling a Java source file. 
  
Json Organization - (http://json.org) – (Maven - https://mvnrepository.com/artifact/org.json/json)

  Aid in parsing a json string.
  
JavaFx - (Provided with Oracles Java 8 JRE/JDK or newer releases. Also availble from https://openjfx.io)

  Used for GopherJ's user interface. 
