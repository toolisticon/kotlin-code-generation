package io.toolisticon.kotlin.generation._BAK.poet

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.FileSpec.Builder
import io.toolisticon.kotlin.generation.BuilderSupplier
import kotlin.reflect.KClass

typealias FileSpecBuilderReceiver = FileSpecBuilder.() -> Unit

@JvmInline
value class FileSpecBuilder(private val builder: Builder) : BuilderSupplier<FileSpec, Builder>,
    AnnotatableBuilder<FileSpecBuilder, FileSpec, Builder>,
    MemberSpecHolderBuilder<FileSpecBuilder, FileSpec, Builder>,
    TaggableBuilder<FileSpecBuilder, FileSpec, Builder>,
    TypeSpecHolderBuilder<FileSpecBuilder, FileSpec, Builder> {
  companion object {
    private fun Builder.wrap() = FileSpecBuilder(this)
    fun get(packageName: String, typeSpec: TypeSpec): FileSpec = FileSpec.get(packageName, typeSpec)
    fun builder(className: ClassName): FileSpecBuilder = FileSpec.builder(className).wrap()
    fun builder(memberName: MemberName): FileSpecBuilder = FileSpec.builder(memberName).wrap()
    fun builder(packageName: String, fileName: String): FileSpecBuilder = FileSpec.builder(packageName, fileName).wrap()
    fun scriptBuilder(fileName: String, packageName: String = ""): FileSpecBuilder = FileSpec.scriptBuilder(fileName, packageName).wrap()
  }

  val defaultImports: MutableSet<String> get() = builder.defaultImports
  val imports: List<Import> get() = builder.imports
  val members: MutableList<Any> get() = builder.members

  fun addAliasedImport(type: KClass<*>, alias: String): FileSpecBuilder = addAliasedImport(type.asClassName(), alias)
  fun addAliasedImport(className: ClassName, alias: String): FileSpecBuilder = apply { builder.addAliasedImport(className, alias) }
  fun addAliasedImport(className: ClassName, memberName: String, alias: String): FileSpecBuilder = apply { builder.addAliasedImport(className, memberName, alias) }
  fun addAliasedImport(memberName: MemberName, alias: String): FileSpecBuilder = apply { builder.addAliasedImport(memberName, alias) }
  fun addBodyComment(format: String, vararg args: Any): FileSpecBuilder = apply { builder.addBodyComment(format, *args) }
  fun addCode(format: String, vararg args: Any?): FileSpecBuilder = apply { builder.addCode(format, *args) }
  fun addCode(codeBlock: CodeBlock): FileSpecBuilder = apply { builder.addCode(codeBlock) }
  fun addCode(builder: CodeBlockBuilder): FileSpecBuilder = addCode(builder.build())
  fun addDefaultPackageImport(packageName: String): FileSpecBuilder = apply { builder.addDefaultPackageImport(packageName) }
  fun addFileComment(format: String, vararg args: Any): FileSpecBuilder = apply { builder.addFileComment(format, *args) }
  override fun addFunction(funSpec: FunSpec): FileSpecBuilder = apply { builder.addFunction(funSpec) }
  fun addImport(constant: Enum<*>): FileSpecBuilder = apply { builder.addImport(constant) }
  fun addImport(type: KClass<*>, vararg names: String): FileSpecBuilder = apply { builder.addImport(type, *names) }
  fun addImport(className: ClassName, vararg names: String): FileSpecBuilder = apply { builder.addImport(className, *names) }
  fun addImport(type: KClass<*>, names: Iterable<String>): FileSpecBuilder = addImport(type.asClassName(), names)
  fun addImport(className: ClassName, names: Iterable<String>): FileSpecBuilder = apply { builder.addImport(className, names) }
  fun addImport(packageName: String, vararg names: String): FileSpecBuilder = apply { builder.addImport(packageName, *names) }
  fun addImport(import: Import): FileSpecBuilder = apply { builder.addImport(import) }
  fun addImport(packageName: String, names: Iterable<String>): FileSpecBuilder = apply { builder.addImport(packageName, names) }
  fun addKotlinDefaultImports(includeJvm: Boolean = true, includeJs: Boolean = true): FileSpecBuilder = apply { builder.addKotlinDefaultImports(includeJvm, includeJs) }
  fun addNamedCode(format: String, args: Map<String, *>): FileSpecBuilder = apply { builder.addNamedCode(format, args) }
  override fun addProperty(propertySpec: PropertySpec): FileSpecBuilder = apply { builder.addProperty(propertySpec) }
  fun addStatement(format: String, vararg args: Any): FileSpecBuilder = apply { builder.addStatement(format, *args) }
  override fun addType(typeSpec: TypeSpec): FileSpecBuilder = apply { builder.addType(typeSpec) }
  fun addTypeAlias(typeAliasSpec: TypeAliasSpec): FileSpecBuilder = apply { builder.addTypeAlias(typeAliasSpec) }
  fun addTypeAlias(builder: TypeAliasSpecBuilder): FileSpecBuilder = addTypeAlias(builder.build())
  fun beginControlFlow(controlFlow: String, vararg args: Any): FileSpecBuilder = apply { builder.beginControlFlow(controlFlow, *args) }
  fun clearBody(): FileSpecBuilder = apply { builder.clearBody() }
  fun clearComment(): FileSpecBuilder = apply { builder.clearComment() }
  fun clearImports(): FileSpecBuilder = apply { builder.clearImports() }
  fun endControlFlow(): FileSpecBuilder = apply { builder.endControlFlow() }
  fun indent(indent: String): FileSpecBuilder = apply { builder.indent(indent) }
  fun nextControlFlow(controlFlow: String, vararg args: Any): FileSpecBuilder = apply { builder.nextControlFlow(controlFlow, *args) }

  override fun build(): FileSpec = builder.build()
  override fun get(): Builder = builder
}
