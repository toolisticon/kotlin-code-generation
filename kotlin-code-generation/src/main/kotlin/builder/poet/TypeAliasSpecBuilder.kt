package io.toolisticon.kotlin.generation.builder.poet

import com.squareup.kotlinpoet.*

@JvmInline
value class TypeAliasSpecBuilder(private val builder: TypeAliasSpec.Builder) : KotlinPoetBuilderSupplier<TypeAliasSpec, TypeAliasSpec.Builder>,
  AnnotatableBuilder<TypeAliasSpec, TypeAliasSpec.Builder>,
  DocumentableBuilder<TypeAliasSpec, TypeAliasSpec.Builder>,
  TaggableBuilder<TypeAliasSpec, TypeAliasSpec.Builder> {

  val modifiers: MutableSet<KModifier> get() = get().modifiers
  val typeVariables: MutableSet<TypeVariableName> get() = get().typeVariables

//  public fun addModifiers(vararg modifiers: KModifier): Builder = apply {
//    modifiers.forEach(this::addModifier)
//  }
//
//  public fun addModifiers(modifiers: Iterable<KModifier>): Builder = apply {
//    modifiers.forEach(this::addModifier)
//  }
//
//  private fun addModifier(modifier: KModifier) {
//    this.modifiers.add(modifier)
//  }
//
//  public fun addTypeVariables(typeVariables: Iterable<TypeVariableName>): Builder = apply {
//    this.typeVariables += typeVariables
//  }
//
//  public fun addTypeVariable(typeVariable: TypeVariableName): Builder = apply {
//    typeVariables += typeVariable
//  }
//
//  //region Overrides for binary compatibility
//  @Suppress("RedundantOverride")
//  override fun addAnnotation(annotationSpec: AnnotationSpec): Builder = super.addAnnotation(annotationSpec)
//
//  @Suppress("RedundantOverride")
//  override fun addAnnotations(annotationSpecs: Iterable<AnnotationSpec>): Builder =
//    super.addAnnotations(annotationSpecs)
//
//  @Suppress("RedundantOverride")
//  override fun addAnnotation(annotation: ClassName): Builder = super.addAnnotation(annotation)
//
//  @DelicateKotlinPoetApi(
//    message = "Java reflection APIs don't give complete information on Kotlin types. Consider " +
//      "using the kotlinpoet-metadata APIs instead.",
//  )
//  override fun addAnnotation(annotation: Class<*>): Builder = super.addAnnotation(annotation)
//
//  @Suppress("RedundantOverride")
//  override fun addAnnotation(annotation: KClass<*>): Builder = super.addAnnotation(annotation)
//
//  @Suppress("RedundantOverride")
//  override fun addKdoc(format: String, vararg args: Any): Builder = super.addKdoc(format, *args)
//
//  @Suppress("RedundantOverride")
//  override fun addKdoc(block: CodeBlock): Builder = super.addKdoc(block)
//  //endregion
//

  override fun build(): TypeAliasSpec = get().build()
  override fun get(): TypeAliasSpec.Builder = builder
}
