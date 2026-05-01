package at.petrak.hexlite.common.casting.actions.math.logic

import at.petrak.hexlite.api.casting.asActionResult
import at.petrak.hexlite.api.casting.castables.ConstMediaAction
import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.getBool
import at.petrak.hexlite.api.casting.iota.Iota

object OpBoolToNumber : ConstMediaAction {
    override val argc = 1

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val arg = args.getBool(0, argc)
        return (if (arg) 1.0 else 0.0).asActionResult
    }
}
