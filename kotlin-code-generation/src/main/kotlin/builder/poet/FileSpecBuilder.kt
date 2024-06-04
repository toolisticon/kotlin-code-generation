package io.toolisticon.kotlin.generation.builder.poet

import com.squareup.kotlinpoet.*
import kotlin.reflect.KClass

@JvmInline
value class FileSpecBuilder(private val builder: FileSpec.Builder) : KotlinPoetBuilderSupplier<FileSpec, FileSpec.Builder>,
  AnnotatableBuilder<FileSpecBuilder, FileSpec, FileSpec.Builder>,
  MemberSpecHolderBuilder<FileSpecBuilder, FileSpec, FileSpec.Builder>,
  TaggableBuilder<FileSpecBuilder, FileSpec, FileSpec.Builder>,
  TypeSpecHolderBuilder<FileSpecBuilder, FileSpec, FileSpec.Builder> {

  val defaultImports: MutableSet<String> get() = builder.defaultImports
  val imports: List<Import> get() = builder.imports
  val members: MutableList<Any> get() = builder.members

  override fun addProperty(propertySpec: PropertySpec): FileSpecBuilder = apply {
    builder.addProperty(propertySpec)
  }

  override fun addFunction(funSpec: FunSpec): FileSpecBuilder = apply {
    builder.addFunction(funSpec)
  }

  override fun addType(typeSpec: TypeSpec): FileSpecBuilder = apply {
    builder.addType(typeSpec)
  }

  fun addFileComment(format: String, vararg args: Any): FileSpecBuilder = apply {
    builder.addFileComment(format, *args)
  }

  fun clearComment(): FileSpecBuilder = apply {
    builder.clearComment()
  }

  fun addTypeAlias(typeAliasSpec: TypeAliasSpec): FileSpecBuilder = apply {
    builder.addTypeAlias(typeAliasSpec)
  }

  fun addTypeAlias(builder: TypeAliasSpecBuilder): FileSpecBuilder = addTypeAlias(builder.build())

  fun addImport(constant: Enum<*>): FileSpecBuilder = apply {
    builder.addImport(constant)
  }

  fun addImport(type: KClass<*>, vararg names: String): FileSpecBuilder = apply {
    builder.addImport(type, *names)
  }

  fun addImport(className: ClassName, vararg names: String): FileSpecBuilder = apply {
    builder.addImport(className, *names)
  }

  fun addImport(type: KClass<*>, names: Iterable<String>): FileSpecBuilder = addImport(type.asClassName(), names)

  fun addImport(className: ClassName, names: Iterable<String>): FileSpecBuilder = apply {
    builder.addImport(className, names)
  }

  fun addImport(packageName: String, vararg names: String): FileSpecBuilder = apply {
    builder.addImport(packageName, *names)
  }

  fun addImport(packageName: String, names: Iterable<String>): FileSpecBuilder = apply {
    builder.addImport(packageName, names)
  }

  fun addImport(import: Import): FileSpecBuilder = apply {
    builder.addImport(import)
  }

  fun clearImports(): FileSpecBuilder = apply {
    builder.clearImports()
  }

  fun addAliasedImport(type: KClass<*>, alias: String): FileSpecBuilder =
    addAliasedImport(type.asClassName(), alias)

  fun addAliasedImport(className: ClassName, alias: String): FileSpecBuilder = apply {
    builder.addAliasedImport(className, alias)
  }

  fun addAliasedImport(className: ClassName, memberName: String, alias: String): FileSpecBuilder = apply {
    builder.addAliasedImport(className, memberName, alias)
  }

  fun addAliasedImport(memberName: MemberName, alias: String): FileSpecBuilder = apply {
    builder.addAliasedImport(memberName, alias)
  }

  fun addDefaultPackageImport(packageName: String): FileSpecBuilder = apply {
    builder.addDefaultPackageImport(packageName)
  }

  fun addKotlinDefaultImports(includeJvm: Boolean = true, includeJs: Boolean = true): FileSpecBuilder = apply {
    builder.addKotlinDefaultImports(includeJvm, includeJs)
  }

  fun indent(indent: String): FileSpecBuilder = apply {
    builder.indent(indent)
  }

  fun addCode(format: String, vararg args: Any?): FileSpecBuilder = apply {
    builder.addCode(format, *args)
  }

  fun addNamedCode(format: String, args: Map<String, *>): FileSpecBuilder = apply {
    builder.addNamedCode(format, args)
  }

  fun addCode(codeBlock: CodeBlock): FileSpecBuilder = apply {
    builder.addCode(codeBlock)
  }

  fun addCode(builder: CodeBlockBuilder): FileSpecBuilder = addCode(builder.build())

  fun addBodyComment(format: String, vararg args: Any): FileSpecBuilder = apply {
    builder.addBodyComment(format, *args)
  }

  fun beginControlFlow(controlFlow: String, vararg args: Any): FileSpecBuilder = apply {
    builder.beginControlFlow(controlFlow, *args)
  }

  fun nextControlFlow(controlFlow: String, vararg args: Any): FileSpecBuilder = apply {
    builder.nextControlFlow(controlFlow, *args)
  }

  fun endControlFlow(): FileSpecBuilder = apply {
    builder.endControlFlow()
  }

  fun addStatement(format: String, vararg args: Any): FileSpecBuilder = apply {
    builder.addStatement(format, *args)
  }

  fun clearBody(): FileSpecBuilder = apply {
    builder.clearBody()
  }

  override fun build(): FileSpec = builder.build()
  override fun get(): FileSpec.Builder = builder
}
