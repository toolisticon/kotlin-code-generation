# kotlin-code-generation

[![incubating](https://img.shields.io/badge/lifecycle-INCUBATING-orange.svg)](https://github.com/holisticon#open-source-lifecycle)
[![Build Status](https://github.com/toolisticon/kotlin-code-generation/workflows/Development%20branches/badge.svg)](https://github.com/toolisticon/kotlin-code-generation/actions)
[![sponsored](https://img.shields.io/badge/sponsoredBy-Holisticon-RED.svg)](https://holisticon.de/)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.toolisticon.kotlin.generation/kotlin-code-generation/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.toolisticon.kotlin.generation/kotlin-code-generation)

**Usage:**

```xml
<dependencyManagement>
    <dependencies>
      <dependency>
        <groupId>io.toolisticon.kotlin.generation</groupId>
        <artifactId>kotlin-code-generation-bom</artifactId>
        <version>LATEST_VERSION</version>
        <scope>import</scope>
        <type>pom</type>
      </dependency>
    </dependencies>
  </dependencyManagement>
```


## Roadmap

### Specs to support

* com/squareup/kotlinpoet/AnnotationSpec
* com/squareup/kotlinpoet/FileSpec
* com/squareup/kotlinpoet/FunSpec
* com/squareup/kotlinpoet/ParameterSpec
* com/squareup/kotlinpoet/PropertySpec
* com/squareup/kotlinpoet/TypeAliasSpec
* com/squareup/kotlinpoet/TypeSpec

### Builders to implement

* com/squareup/kotlinpoet/AnnotationSpec$Builder
  * Taggable.Builder<Builder>
* com/squareup/kotlinpoet/CodeBlock$Builder
  * None
* com/squareup/kotlinpoet/FileSpec$Builder
  * Annotatable.Builder<Builder>
  * Taggable.Builder<Builder>
  * TypeSpecHolder.Builder<Builder>
* com/squareup/kotlinpoet/FunSpec$Builder
  * Annotatable.Builder<Builder>
  * ContextReceivable.Builder<Builder>
  * Documentable.Builder<Builder>
  * Taggable.Builder<Builder>
  * OriginatingElementsHolder.Builder<Builder>
* com/squareup/kotlinpoet/ParameterSpec$Builder
  * Annotatable.Builder<Builder>
  * Documentable.Builder<Builder>
  * Taggable.Builder<Builder>
* com/squareup/kotlinpoet/PropertySpec$Builder
  * Annotatable.Builder<Builder>
  * ContextReceivable.Builder<Builder>
  * Documentable.Builder<Builder>
  * OriginatingElementsHolder.Builder<Builder>
  * Taggable.Builder<Builder>
* com/squareup/kotlinpoet/TypeAliasSpec$Builder
  * Annotatable.Builder<Builder>
  * Documentable.Builder<Builder>
  * Taggable.Builder<Builder>
