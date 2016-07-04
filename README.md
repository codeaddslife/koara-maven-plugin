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