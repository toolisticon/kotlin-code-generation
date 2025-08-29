# kotlin-code-generation

The one-stop lib for code generation for kotlin (jvm) and code generation testing. Based on [kotlin-poet](https://square.github.io/kotlinpoet/).

[![stable](https://img.shields.io/badge/lifecycle-STABLE-green.svg)](https://github.com/holisticon#open-source-lifecycle) 
[![Maven Central Version](https://img.shields.io/maven-central/v/io.toolisticon.kotlin.generation/kotlin-code-generation-bom)](https://central.sonatype.com/namespace/io.toolisticon.kotlin.generation)
[![Build Status](https://github.com/toolisticon/kotlin-code-generation/workflows/Development%20branches/badge.svg)](https://github.com/toolisticon/kotlin-code-generation/actions)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/e493c246c3684b95a2ef097ae912d45a)](https://app.codacy.com/gh/toolisticon/kotlin-code-generation/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)
[![codecov](https://codecov.io/gh/toolisticon/kotlin-code-generation/graph/badge.svg?token=kyJ82m5DAT)](https://codecov.io/gh/toolisticon/kotlin-code-generation)
[![Kotlin](https://img.shields.io/badge/kotlin-2.2.10-blue.svg?logo=kotlin)](http://kotlinlang.org)
[![sponsored](https://img.shields.io/badge/sponsoredBy-Holisticon-RED.svg)](https://holisticon.de/)
[![javadoc](https://javadoc.io/badge2/io.toolisticon.kotlin.generation/kotlin-code-generation/javadoc.svg)](https://javadoc.io/doc/io.toolisticon.kotlin.generation/kotlin-code-generation)

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

## Documentation

This code generation lib wraps the fantastic [kotlin-poet](https://square.github.io/kotlinpoet/) framework. The documentation is based on the [kotlin-poet documentation](https://square.github.io/kotlinpoet/).

## Getting started

Core concept are [Strategies](./kotlin-code-generation/src/main/kotlin/spi/KotlinCodeGenerationStrategy.kt) and [Processors](./kotlin-code-generation/src/main/kotlin/spi/KotlinCodeGenerationProcessor.kt).

### Load SPI

* Implement and list your strategies and processors in the `META-INF/services/io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpi`. Both interfaces are loaded via the same ServiceLoader mechanism,
and later filtered for the specific type of strategy or processor.
* Load the services via `val spi = KotlinCodeGeneration.spi.load()`
* Define your context by extending the `KotlinCodeGenerationContext` class. This context is passed to the strategies and processors, so they can use more data than just the input item in the loop.
  * You probably want to filter the loaded list you provide to your specific context.
  * 



## Features

* KotlinAnnotationSpec
  * KotlinAnnotationSpecBuilder
* KotlinFileSpec
  * KotlinFileSpecBuilder
* KotlinFunSpec
  * KotlinFunSpecBuilder
* KotlinParameterSpec
  * KotlinParameterSpecBuilder
* KotlinPropertySpec
  * KotlinPropertySpecBuilder



## Roadmap

### Specs to support

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
