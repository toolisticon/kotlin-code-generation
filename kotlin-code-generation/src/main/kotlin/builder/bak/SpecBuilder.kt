package io.toolisticon.kotlin.generation.builder.bak

import io.toolisticon.kotlin.generation.Builder
import io.toolisticon.kotlin.generation.spec.SpecSupplier

@Deprecated("remove")
sealed interface SpecBuilder<SELF : SpecBuilder<SELF, PRODUCT, SPEC, SPEC_BUILDER>, PRODUCT : SpecSupplier<SPEC>, SPEC, SPEC_BUILDER> :
    Builder<PRODUCT>
