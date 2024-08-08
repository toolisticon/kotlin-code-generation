package io.toolisticon.kotlin.generation.spi

import kotlin.reflect.KClass

/**
 * Root interface of all processors. Used to load all implementations
 * via ServiceLoader/SPI.
 */
interface KotlinCodeGenerationProcessor<CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any, BUILDER : Any> : KotlinCodeGenerationSpi<CONTEXT, INPUT> {

  override val contextType: KClass<CONTEXT>
  override val inputType: KClass<INPUT>
  override val order: Int
  override val name: String
  val builderType: KClass<BUILDER>

  operator fun invoke(context: CONTEXT, input: INPUT?, builder: BUILDER): BUILDER
}


//
//@JvmInline
//value class FileSpecProcessorList<CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT: Any>(private val list: List<AbstractFileSpecProcessor<CONTEXT, *>>) :
//  List<AbstractFileSpecProcessor<CONTEXT, *>> by list {
//
//  /**
//   * Execute all processors if predicate allows it.
//   */
//  operator fun invoke(
//    ctx: CONTEXT,
//    input: INPUT,
//    builder: KotlinFileSpecBuilder
//  ) = filter { it.test(ctx, input) }.forEach { it.invoke(context = ctx, input = input  , builder = builder) }
//}
