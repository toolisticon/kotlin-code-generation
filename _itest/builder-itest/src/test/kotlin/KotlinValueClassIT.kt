package io.toolisticon.kotlin.generation.itest

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.jvm.jvmInline
import org.junit.jupiter.api.Test

internal class KotlinValueClassIT {

  @Test
  fun `poet only`() {
    val name = ClassName("test", "Foo")

    val builder = TypeSpec.classBuilder(name)
      .addModifiers(KModifier.VALUE)
      .jvmInline()

    val property = PropertySpec.builder("bar", String::class)
      .addModifiers(KModifier.PRIVATE)
      .build()

    val parameter = ParameterSpec.builder(property.name, property.type)

    println(parameter.build())

    val constructor = FunSpec.constructorBuilder()
      .addParameter(parameter.build())
    builder.addProperty(
      property.toBuilder()
        .initializer("%N", parameter.build())
        .build()
    )

    builder.primaryConstructor(constructor.build())

    //println(FileSpec.builder(name).addType(builder.build()).build())
    println(builder.build())

//    val parameters = parameterSpecs.map(ParameterSpecSupplier::get)
//
//    val constructor = FunSpec.constructorBuilder()
//      .addParameters(parameters)
//      .build()
//    val properties = parameters.map {
//      PropertySpec.builder(it.name, it.type)
//        .initializer("%N", it)
//        .build()
//    }

//    delegate.primaryConstructor(constructor)
//      .addProperties(properties)
//
//    return KotlinDataClassSpec(className = className, spec = delegate.build())
  }

  @Test
  fun `generate value class wrapping string`() {
    TODO()
  }
}
