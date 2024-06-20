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
  override fun addAnnotation(annotationSpec: AnnotationSpec) = invoke { addAnnotation(annotationSpec) }
  override fun addAnnotations(annotationSpecs: Iterable<AnnotationSpec>) = invoke { addAnnotations(annotationSpecs) }

  // MemberSpecHolderBuilder
  override fun addFunction(funSpec: FunSpec) = invoke { addFunction(funSpec) }
  override fun addFunctions(funSpecs: Iterable<FunSpec>) = invoke { addFunctions(funSpecs) }
  override fun addProperty(propertySpec: PropertySpec) = invoke { addProperty(propertySpec) }
  override fun addProperties(propertySpecs: Iterable<PropertySpec>) = invoke { addProperties(propertySpecs) }

  // TypeSpecHolderBuilder
  override fun addType(typeSpec: TypeSpec) = invoke { addType(typeSpec) }
  override fun addTypes(typeSpecs: Iterable<TypeSpec>) = invoke { addTypes(typeSpecs) }


  fun addFileComment(format: String, vararg args: Any) = invoke { addFileComment(format, *args) }
  fun clearComment(): FileSpecBuilder = invoke { clearComment() }
  fun addTypeAlias(typeAliasSpec: TypeAliasSpec): FileSpecBuilder = invoke { addTypeAlias(typeAliasSpec) }
  fun addImport(constant: Enum<*>): FileSpecBuilder = invoke { addImport(constant) }
  fun addImport(kclass: KClass<*>, vararg names: String): FileSpecBuilder = invoke { addImport(kclass, *names) }
  fun addImport(className: ClassName, vararg names: String): FileSpecBuilder = invoke { addImport(className, *names) }
  fun addImport(kclass: KClass<*>, names: Iterable<String>): FileSpecBuilder = invoke { addImport(kclass, names) }
  fun addImport(className: ClassName, names: Iterable<String>): FileSpecBuilder = invoke { addImport(className, names) }
  fun addImport(packageName: String, vararg names: String): FileSpecBuilder = invoke { addImport(packageName, *names) }
  fun addImport(packageName: String, names: Iterable<String>): FileSpecBuilder = invoke { addImport(packageName, names) }
  fun addImport(import: Import): FileSpecBuilder = invoke { addImport(import) }
  fun clearImports(): FileSpecBuilder = invoke { clearImports() }
  fun addAliasedImport(kclass: KClass<*>, alias: String): FileSpecBuilder = invoke { addAliasedImport(kclass, alias) }
  fun addAliasedImport(className: ClassName, alias: String): FileSpecBuilder = invoke { addAliasedImport(className, alias) }
  fun addAliasedImport(className: ClassName, memberName: String, alias: String): FileSpecBuilder = invoke { addAliasedImport(className, memberName, alias) }
  fun addAliasedImport(memberName: MemberName, alias: String): FileSpecBuilder = invoke { addAliasedImport(memberName, alias) }
  fun addDefaultPackageImport(packageName: String): FileSpecBuilder = invoke { addDefaultPackageImport(packageName) }
  fun addKotlinDefaultImports(includeJvm: Boolean = true, includeJs: Boolean = true): FileSpecBuilder = invoke { addKotlinDefaultImports(includeJvm, includeJs) }
  fun indent(indent: String): FileSpecBuilder = invoke { indent(indent) }
  fun addCode(format: String, vararg args: Any?): FileSpecBuilder = invoke { addCode(format, *args) }
  fun addNamedCode(format: String, args: Map<String, *>): FileSpecBuilder = invoke { addNamedCode(format, args) }
  fun addCode(codeBlock: CodeBlock): FileSpecBuilder = invoke { addCode(codeBlock) }
  fun addBodyComment(format: String, vararg args: Any): FileSpecBuilder = invoke { addBodyComment(format, *args) }
  fun beginControlFlow(controlFlow: String, vararg args: Any): FileSpecBuilder = invoke { beginControlFlow(controlFlow, *args) }
  fun nextControlFlow(controlFlow: String, vararg args: Any): FileSpecBuilder = invoke { nextControlFlow(controlFlow, *args) }
  fun endControlFlow(): FileSpecBuilder = invoke { endControlFlow() }
  fun addStatement(format: String, vararg args: Any): FileSpecBuilder = invoke { addStatement(format, *args) }
  fun clearBody(): FileSpecBuilder = invoke { clearBody() }

  override fun invoke(block: FileSpecBuilderReceiver): FileSpecBuilder = apply {
    builder.block()
  }

  override fun build(): FileSpec = builder.build()

}

interface FileSpecSupplier : PoetSpecSupplier<FileSpec>
typealias FileSpecBuilderReceiver = FileSpec.Builder.() -> Unit
