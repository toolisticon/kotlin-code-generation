@file:OptIn(ExperimentalKotlinPoetApi::class)

package io.toolisticon.kotlin.generation.spi

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.TestFixtures.ServiceLoaderTestClassLoader
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.ServiceLoader

class ServiceLoaderTestClassLoaderTest {

  interface MyInterface {
    fun hello(): String
  }

  class MyClass : MyInterface {
    override fun hello(): String = "hello"
  }

  @Test
  fun `load myClass via fake serviceLoader`() {
    val classLoader = ServiceLoaderTestClassLoader(MyInterface::class, MyClass::class)

    val list = ServiceLoader.load(MyInterface::class.java, classLoader).toList()

    assertThat(list).hasSize(1)
    assertThat(list[0]).isInstanceOf(MyInterface::class.java)
    assertThat(list[0].hello()).isEqualTo("hello")
  }
}
