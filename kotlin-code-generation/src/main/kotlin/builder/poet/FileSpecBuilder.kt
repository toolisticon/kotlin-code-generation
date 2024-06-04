package io.toolisticon.kotlin.generation.builder.poet

import com.squareup.kotlinpoet.*
import kotlin.reflect.KClass

@JvmInline
value class FileSpecBuilder(private val builder: FileSpec.Builder) : KotlinPoetBuilderSupplier<FileSpec, FileSpec.Builder>,
  AnnotatableBuilder<FileSpec, FileSpec.Builder>,
  MemberSpecHolderBuilder<FileSpec, FileSpec.Builder>,
  TaggableBuilder<FileSpec, FileSpec.Builder>,
  TypeSpecHolderBuilder<FileSpec, FileSpec.Builder> {

  val defaultImports: MutableSet<String> get() = get().defaultImports

  val imports: List<Import> get() = get().imports
  val members: MutableList<Any> get() = get().members

  override fun addProperty(propertySpec: PropertySpec): FileSpecBuilder = apply {
    get().addProperty(propertySpec)
  }

  override fun addFunction(funSpec: FunSpec): FileSpecBuilder = apply {
    get().addFunction(funSpec)
  }

  override fun addType(typeSpec: TypeSpec): FileSpecBuilder = apply {
    get().addType(typeSpec)
  }

  fun addFileComment(format: String, vararg args: Any): FileSpecBuilder = apply {
    get().addFileComment(format, *args)
  }

  fun clearComment(): FileSpecBuilder = apply {
    get().clearComment()
  }

  fun addTypeAlias(typeAliasSpec: TypeAliasSpec): FileSpecBuilder = apply {
    get().addTypeAlias(typeAliasSpec)
  }

  fun addImport(constant: Enum<*>): FileSpecBuilder = apply {
    get().addImport(constant)
  }

  fun addImport(type: KClass<*>, vararg names: String): FileSpecBuilder = apply {
    get().addImport(type, *names)
  }

  fun addImport(className: ClassName, vararg names: String): FileSpecBuilder = apply {
    get().addImport(className, *names)
  }

  fun addImport(type: KClass<*>, names: Iterable<String>): FileSpecBuilder = addImport(type.asClassName(), names)

  fun addImport(className: ClassName, names: Iterable<String>): FileSpecBuilder = apply {
    get().addImport(className, names)
  }

  fun addImport(packageName: String, vararg names: String): FileSpecBuilder = apply {
    get().addImport(packageName, *names)
  }

  fun addImport(packageName: String, names: Iterable<String>): FileSpecBuilder = apply {
    get().addImport(packageName, names)
  }

  fun addImport(import: Import): FileSpecBuilder = apply {
    get().addImport(import)
  }

  fun clearImports(): FileSpecBuilder = apply {
    get().clearImports()
  }

  fun addAliasedImport(type: KClass<*>, alias: String): FileSpecBuilder =
    addAliasedImport(type.asClassName(), alias)

  fun addAliasedImport(className: ClassName, alias: String): FileSpecBuilder = apply {
    get().addAliasedImport(className, alias)
  }

  fun addAliasedImport(className: ClassName, memberName: String, alias: String): FileSpecBuilder = apply {
    get().addAliasedImport(className, memberName, alias)
  }

  fun addAliasedImport(memberName: MemberName, alias: String): FileSpecBuilder = apply {
    get().addAliasedImport(memberName, alias)
  }

  fun addDefaultPackageImport(packageName: String): FileSpecBuilder = apply {
    get().addDefaultPackageImport(packageName)
  }

  fun addKotlinDefaultImports(
    includeJvm: Boolean = true,
    includeJs: Boolean = true,
  ): FileSpecBuilder = apply {
    get().addKotlinDefaultImports(includeJvm, includeJs)
  }

  fun indent(indent: String): FileSpecBuilder = apply {
    get().indent(indent)
  }

  fun addCode(format: String, vararg args: Any?): FileSpecBuilder = apply {
    get().addCode(format, *args)
  }

  fun addNamedCode(format: String, args: Map<String, *>): FileSpecBuilder = apply {
    get().addNamedCode(format, args)
  }

  fun addCode(codeBlock: CodeBlock): FileSpecBuilder = apply {
    get().addCode(codeBlock)
  }

  fun addBodyComment(format: String, vararg args: Any): FileSpecBuilder = apply {
    get().addBodyComment(format, *args)
  }

  fun beginControlFlow(controlFlow: String, vararg args: Any): FileSpecBuilder = apply {
    get().beginControlFlow(controlFlow, *args)
  }

  fun nextControlFlow(controlFlow: String, vararg args: Any): FileSpecBuilder = apply {
    get().nextControlFlow(controlFlow, *args)
  }

  fun endControlFlow(): FileSpecBuilder = apply {
    get().endControlFlow()
  }

  fun addStatement(format: String, vararg args: Any): FileSpecBuilder = apply {
    get().addStatement(format, *args)
  }

  fun clearBody(): FileSpecBuilder = apply {
    get().clearBody()
  }

  override fun build(): FileSpec = get().build()
  override fun get(): FileSpec.Builder = builder
}
