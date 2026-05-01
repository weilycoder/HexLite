package at.petrak.hexlite.common.casting.actions.selectors

import at.petrak.hexlite.api.casting.asActionResult
import at.petrak.hexlite.api.casting.castables.ConstMediaAction
import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.iota.Iota

object OpGetCaster : ConstMediaAction {
    override val argc = 0

    override fun execute(args: List<Iota>, ctx: CastingEnvironment): List<Iota> {
        if (ctx.castingEntity == null)
            return null.asActionResult

        ctx.assertEntityInRange(ctx.castingEntity)
        return ctx.castingEntity.asActionResult
    }
}
