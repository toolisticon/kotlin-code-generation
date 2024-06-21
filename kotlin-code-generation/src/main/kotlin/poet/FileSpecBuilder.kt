package io.toolisticon.kotlin.generation.poet

import com.squareup.kotlinpoet.*
import kotlin.reflect.KClass

class FileSpecBuilder(
  override val builder: FileSpec.Builder
) : PoetSpecBuilder<FileSpecBuilder, FileSpec.Builder, FileSpec, FileSpecSupplier>,
  AnnotatableBuilder<FileSpecBuilder>,
  MemberSpecHolderBuilder<FileSpecBuilder>,
  TypeSpecHolderBuilder<FileSpecBuilder> {
  companion object {
    fun FileSpec.Builder.wrap() = FileSpecBuilder(this)

    @JvmStatic
    fun get(packageName: String, typeSpec: TypeSpec): FileSpec = FileSpec.get(packageName, typeSpec)

    @JvmStatic
    fun builder(className: ClassName): FileSpecBuilder = FileSpec.builder(className).wrap()

    @JvmStatic
    fun builder(memberName: MemberName): FileSpecBuilder = FileSpec.builder(memberName).wrap()

    @JvmStatic
    fun builder(packageName: String, fileName: String): FileSpecBuilder = FileSpec.builder(packageName, fileName).wrap()

    @JvmStatic
    fun scriptBuilder(fileName: String, packageName: String = ""): FileSpecBuilder = FileSpec.scriptBuilder(fileName, packageName).wrap()
  }

  // AnnotatableBuilder
  override fun addAnnotation(annotationSpec: AnnotationSpec) = apply { builder.addAnnotation(annotationSpec) }
  override fun addAnnotations(annotationSpecs: Iterable<AnnotationSpec>) = apply { builder.addAnnotations(annotationSpecs) }

  // MemberSpecHolderBuilder
  override fun addFunction(funSpec: FunSpec) = apply { builder.addFunction(funSpec) }
  override fun addFunctions(funSpecs: Iterable<FunSpec>) = apply { builder.addFunctions(funSpecs) }
  override fun addProperty(propertySpec: PropertySpec) = apply { builder.addProperty(propertySpec) }
  override fun addProperties(propertySpecs: Iterable<PropertySpec>) = apply { builder.addProperties(propertySpecs) }

  // TypeSpecHolderBuilder
  override fun addType(typeSpec: TypeSpec) = apply { builder.addType(typeSpec) }
  override fun addTypes(typeSpecs: Iterable<TypeSpec>) = apply { builder.addTypes(typeSpecs) }


  fun addFileComment(format: String, vararg args: Any) = apply { builder.addFileComment(format, *args) }
  fun clearComment(): FileSpecBuilder = apply { builder.clearComment() }
  fun addTypeAlias(typeAliasSpec: TypeAliasSpec): FileSpecBuilder = apply { builder.addTypeAlias(typeAliasSpec) }
  fun addImport(constant: Enum<*>): FileSpecBuilder = apply { builder.addImport(constant) }
  fun addImport(kclass: KClass<*>, vararg names: String): FileSpecBuilder = apply { builder.addImport(kclass, *names) }
  fun addImport(className: ClassName, vararg names: String): FileSpecBuilder = apply { builder.addImport(className, *names) }
  fun addImport(kclass: KClass<*>, names: Iterable<String>): FileSpecBuilder = apply { builder.addImport(kclass, names) }
  fun addImport(className: ClassName, names: Iterable<String>): FileSpecBuilder = apply { builder.addImport(className, names) }
  fun addImport(packageName: String, vararg names: String): FileSpecBuilder = apply { builder.addImport(packageName, *names) }
  fun addImport(packageName: String, names: Iterable<String>): FileSpecBuilder = apply { builder.addImport(packageName, names) }
  fun addImport(import: Import): FileSpecBuilder = apply { builder.addImport(import) }
  fun clearImports(): FileSpecBuilder = apply { builder.clearImports() }
  fun addAliasedImport(kclass: KClass<*>, alias: String): FileSpecBuilder = apply { builder.addAliasedImport(kclass, alias) }
  fun addAliasedImport(className: ClassName, alias: String): FileSpecBuilder = apply { builder.addAliasedImport(className, alias) }
  fun addAliasedImport(className: ClassName, memberName: String, alias: String): FileSpecBuilder = apply { builder.addAliasedImport(className, memberName, alias) }
  fun addAliasedImport(memberName: MemberName, alias: String): FileSpecBuilder = apply { builder.addAliasedImport(memberName, alias) }
  fun addDefaultPackageImport(packageName: String): FileSpecBuilder = apply { builder.addDefaultPackageImport(packageName) }
  fun addKotlinDefaultImports(includeJvm: Boolean = true, includeJs: Boolean = true): FileSpecBuilder = apply { builder.addKotlinDefaultImports(includeJvm, includeJs) }
  fun indent(indent: String): FileSpecBuilder = apply { builder.indent(indent) }
  fun addCode(format: String, vararg args: Any?): FileSpecBuilder = apply { builder.addCode(format, *args) }
  fun addNamedCode(format: String, args: Map<String, *>): FileSpecBuilder = apply { builder.addNamedCode(format, args) }
  fun addCode(codeBlock: CodeBlock): FileSpecBuilder = apply { builder.addCode(codeBlock) }
  fun addBodyComment(format: String, vararg args: Any): FileSpecBuilder = apply { builder.addBodyComment(format, *args) }
  fun beginControlFlow(controlFlow: String, vararg args: Any): FileSpecBuilder = apply { builder.beginControlFlow(controlFlow, *args) }
  fun nextControlFlow(controlFlow: String, vararg args: Any): FileSpecBuilder = apply { builder.nextControlFlow(controlFlow, *args) }
  fun endControlFlow(): FileSpecBuilder = apply { builder.endControlFlow() }
  fun addStatement(format: String, vararg args: Any): FileSpecBuilder = apply { builder.addStatement(format, *args) }
  fun clearBody(): FileSpecBuilder = apply { builder.clearBody() }

  override fun build(): FileSpec = builder.build()
}

interface FileSpecSupplier : PoetSpecSupplier<FileSpec>
typealias FileSpecBuilderReceiver = FileSpec.Builder.() -> Unit
