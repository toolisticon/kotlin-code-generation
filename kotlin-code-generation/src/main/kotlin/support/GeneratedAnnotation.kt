package io.toolisticon.kotlin.generation.support

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.asTypeName
import io.toolisticon.kotlin.generation.KotlinCodeGeneration
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationSpec
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationSpecSupplier
import jakarta.annotation.Generated
import java.time.Instant
import kotlin.reflect.KClass

@ExperimentalKotlinPoetApi
@Suppress(SUPPRESS_UNUSED)
data class GeneratedAnnotation(
    val value: String = KotlinCodeGeneration::class.asTypeName().toString(),
    val date: Instant = Instant.now(),
    val comments: List<String> = emptyList()
) : KotlinAnnotationSpecSupplier {

  fun generator(type: KClass<*>) = copy(value = type.asTypeName().toString())
  fun date(instant: Instant) = copy(date = instant)
  fun comment(comment: Pair<String, String>) = copy(comments = this.comments + "${comment.first} = ${comment.second}")

  override fun spec(): KotlinAnnotationSpec = KotlinCodeGeneration.buildAnnotation(Generated::class) {
      addStringMembers("value", value)
      addStringMember("date", date.toString())

      if (comments.isNotEmpty()) {
          addStringMember("comments", comments.joinToString(separator = "; "))
      }
  }
}
