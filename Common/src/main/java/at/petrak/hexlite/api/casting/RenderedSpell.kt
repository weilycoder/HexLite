package at.petrak.hexlite.api.casting

import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.eval.vm.CastingImage

interface RenderedSpell {
    fun cast(env: CastingEnvironment)

    fun cast(env: CastingEnvironment, image: CastingImage): CastingImage? {
        cast(env)
        return null
    }
}
