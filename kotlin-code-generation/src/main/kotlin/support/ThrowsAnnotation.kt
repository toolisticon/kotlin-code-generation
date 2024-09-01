package io.toolisticon.kotlin.generation.support

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildAnnotation
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.format.FORMAT_KCLASS
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationSpec
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationSpecSupplier
import io.toolisticon.kotlin.generation.support.CodeBlockArray.Companion.Format
import kotlin.collections.fold
import kotlin.reflect.KClass

@ExperimentalKotlinPoetApi
data class ThrowsAnnotation(private val members: CodeBlockArray<CodeBlock>) : KotlinAnnotationSpecSupplier {
  companion object {
    val EXCEPTION = Throws::class
    val EMPTY = CodeBlockArray.codeBlockArray(Format(format = "", prefix = "", suffix = ""))
    private val TypeName.codeBlock: CodeBlock get() = CodeBlock.of(FORMAT_KCLASS, this)
  }

  constructor(exceptions: List<TypeName>) : this(members = exceptions.fold(EMPTY) { acc, cur -> acc + cur.codeBlock })
  constructor(exception: TypeName, vararg exceptions: TypeName) : this(listOf(exception, *exceptions))
  constructor(exception: KClass<out Throwable>, vararg exceptions: KClass<out Throwable>) : this(listOf(exception, *exceptions).map { it.asTypeName() })

  override fun spec(): KotlinAnnotationSpec = buildAnnotation(EXCEPTION) {
    addMember(members.build())
  }
}
