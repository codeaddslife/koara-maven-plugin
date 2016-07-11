[![Koara](http://www.koara.io/logo.png)](http://www.koara.io)

[![Build Status](https://img.shields.io/travis/codeaddslife/koara-maven-plugin.svg)](https://travis-ci.org/codeaddslife/koara-maven-plugin)
[![Coverage Status](https://img.shields.io/coveralls/codeaddslife/koara-maven-plugin.svg)](https://coveralls.io/github/codeaddslife/koara-maven-plugin?branch=master)
[![Latest Version](https://img.shields.io/maven-central/v/io.koara/koara-maven-plugin.svg?label=Maven Central)](http://search.maven.org/#search%7Cga%7C1%7Ckoara-maven-plugin)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/codeaddslife/koara-maven-plugin/blob/master/LICENSE)

# Koara-maven-plugin
This project is a plugin for parsing [Koara](http://www.koara.io) documents with [Apache Maven](http://maven.apache.org).

## Getting Started
Download:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
   <modelVersion>4.0.0</modelVersion>
   <groupId>io.koara</groupId>
   <artifactId>demo</artifactId>
   <version>1.0-SNAPSHOT</version>
   <build>
      <plugins>
         <plugin>
            <groupId>io.koara</groupId>
            <artifactId>koara-maven-plugin</artifactId>
            <version>0.1.0</version>
            <executions>
               <execution>
                  <id>output-html</id>
                  <phase>generate-resources</phase>
                  <goals>
                     <goal>convert</goal>
                  </goals>
               </execution>
            </executions>
         </plugin>
      </plugins>
   </build>
</project>
```

## Task attributes
- `sourceDirectory`:
  Location where the plugin should look for documents to render (default: ${basedir}/src/main/koara)

- `outputDirectory`:
  Location to which all rendered documents should be written (default: ${project.build.directory}/generated-docs)
  
- `modules`:
  Optional comma-seperated string of modules used to render the koara documents. By default, all modules will be used. Possible values: paragraphs, headings, lists, links, images, formatting, blockquote, code

- `outputFormat`:
  The format in which the koara documents should be rendered. Possible values: html5, xml