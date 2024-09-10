@file:Suppress(SUPPRESS_UNUSED)

package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.*
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.poet.*
import io.toolisticon.kotlin.generation.poet.FileSpecBuilder.Companion.wrap
import io.toolisticon.kotlin.generation.spec.*
import io.toolisticon.kotlin.generation.support.SUPPRESS_UNUSED
import kotlin.reflect.KClass

/**
 * Builder for [KotlinFileSpec].
 */
@ExperimentalKotlinPoetApi
class KotlinFileSpecBuilder internal constructor(
  private val delegate: FileSpecBuilder
) : BuilderSupplier<KotlinFileSpec, FileSpec>, KotlinFileSpecSupplier, DelegatingBuilder<KotlinFileSpecBuilder, FileSpecBuilderReceiver>,
  KotlinAnnotatableBuilder<KotlinFileSpecBuilder>,
  KotlinMemberSpecHolderBuilder<KotlinFileSpecBuilder>,
  KotlinTypeSpecHolderBuilder<KotlinFileSpecBuilder> {
  companion object {
    fun builder(className: ClassName): KotlinFileSpecBuilder {
      require(className.packageName.isNotEmpty()) { "cannot build file for empty package." }
      return KotlinFileSpecBuilder(delegate = FileSpecBuilder.builder(className))
    }

    fun builder(memberName: MemberName): KotlinFileSpecBuilder = KotlinFileSpecBuilder(
      delegate = FileSpecBuilder.builder(memberName)
    )

    fun builder(packageName: String, fileName: String): KotlinFileSpecBuilder = KotlinFileSpecBuilder(
      delegate = FileSpecBuilder.builder(packageName, fileName)
    )

    fun scriptBuilder(fileName: String, packageName: String = ""): KotlinFileSpecBuilder = KotlinFileSpecBuilder(
      delegate = FileSpecBuilder.scriptBuilder(fileName, packageName)
    )

    fun builder(spec: KotlinFileSpec) = builder(spec.get())

    fun builder(spec: FileSpec) = KotlinFileSpecBuilder(delegate = spec.toBuilder().wrap())
  }

  override fun addAnnotation(spec: KotlinAnnotationSpecSupplier) = apply { delegate.addAnnotation(spec.get()) }
  override fun addFunction(funSpec: KotlinFunSpecSupplier) = apply { delegate.addFunction(funSpec.get()) }
  override fun addProperty(propertySpec: KotlinPropertySpecSupplier) = apply { delegate.addProperty(propertySpec.get()) }
  override fun addType(typeSpec: TypeSpecSupplier) = builder { this.addType(typeSpec.get()) }
  override fun tag(type: KClass<*>, tag: Any?) = builder { this.tag(type, tag) }

  fun addAliasedImport(kclass: KClass<*>, alias: String) = builder { this.addAliasedImport(kclass, alias) }
  fun addAliasedImport(className: ClassName, alias: String) = builder { this.addAliasedImport(className, alias) }
  fun addAliasedImport(className: ClassName, memberName: String, alias: String) = builder { this.addAliasedImport(className, memberName, alias) }
  fun addAliasedImport(memberName: MemberName, alias: String) = builder { this.addAliasedImport(memberName, alias) }
  fun addBodyComment(format: String, vararg args: Any) = builder { this.addBodyComment(format, *args) }
  fun addCode(format: String, vararg args: Any?) = builder { this.addCode(format, *args) }
  fun addCode(codeBlock: CodeBlock) = builder { this.addCode(codeBlock) }
  fun addDefaultPackageImport(packageName: String) = builder { this.addDefaultPackageImport(packageName) }
  fun addFileComment(format: String, vararg args: Any) = builder { this.addFileComment(format, *args) }
  fun addImport(constant: Enum<*>) = builder { this.addImport(constant) }
  fun addImport(kclass: KClass<*>, vararg names: String) = builder { this.addImport(kclass, *names) }
  fun addImport(className: ClassName, vararg names: String) = builder { this.addImport(className, *names) }
  fun addImport(kclass: KClass<*>, names: Iterable<String>) = builder { this.addImport(kclass, names) }
  fun addImport(className: ClassName, names: Iterable<String>) = builder { this.addImport(className, names) }
  fun addImport(packageName: String, vararg names: String) = builder { this.addImport(packageName, *names) }
  fun addImport(packageName: String, names: Iterable<String>) = builder { this.addImport(packageName, names) }
  fun addImport(import: Import) = builder { this.addImport(import) }
  fun addKotlinDefaultImports(includeJvm: Boolean = true, includeJs: Boolean = true) = builder { this.addKotlinDefaultImports(includeJvm, includeJs) }
  fun addNamedCode(format: String, args: Map<String, *>) = builder { this.addNamedCode(format, args) }
  fun addStatement(format: String, vararg args: Any) = builder { this.addStatement(format, *args) }
  fun addTypeAlias(typeAliasSpec: TypeAliasSpecSupplier) = builder { this.addTypeAlias(typeAliasSpec.get()) }
  fun beginControlFlow(controlFlow: String, vararg args: Any) = builder { this.beginControlFlow(controlFlow, *args) }
  fun nextControlFlow(controlFlow: String, vararg args: Any) = builder { this.nextControlFlow(controlFlow, *args) }
  fun endControlFlow() = builder { this.endControlFlow() }

  override fun builder(block: FileSpecBuilderReceiver) = apply {
    delegate.builder.block()
  }

  override fun build(): KotlinFileSpec {
    val spec = delegate.build()
    return KotlinFileSpec(spec = spec)
  }

  override fun spec(): KotlinFileSpec = build()
  override fun get(): FileSpec = build().get()

  override val className: ClassName = delegate.className
}

@ExperimentalKotlinPoetApi
typealias KotlinFileSpecBuilderReceiver = KotlinFileSpecBuilder.() -> Unit
