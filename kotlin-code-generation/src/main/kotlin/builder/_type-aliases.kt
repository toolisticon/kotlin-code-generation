package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeAliasSpec
import com.squareup.kotlinpoet.TypeSpec



interface DelegatingBuilder<SELF, RECEIVER> {
  fun builder(block: RECEIVER) : SELF
}
