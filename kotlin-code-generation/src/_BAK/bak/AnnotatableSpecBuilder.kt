package io.toolisticon.kotlin.generation._BAK.bak

import com.squareup.kotlinpoet.Annotatable
import io.toolisticon.kotlin.generation.Builder
import io.toolisticon.kotlin.generation._BAK.SpecSupplier

@Deprecated("remove")
interface AnnotatableSpecBuilder<SELF, PRODUCT : SpecSupplier<SPEC>, SPEC : Annotatable, SPEC_BUILDER : Annotatable.Builder<SPEC_BUILDER>> :
    Builder<PRODUCT>
