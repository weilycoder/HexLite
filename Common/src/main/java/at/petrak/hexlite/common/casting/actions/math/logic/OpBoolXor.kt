package at.petrak.hexlite.common.casting.actions.math.logic

import at.petrak.hexlite.api.casting.asActionResult
import at.petrak.hexlite.api.casting.castables.ConstMediaAction
import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.getBool
import at.petrak.hexlite.api.casting.iota.Iota

object OpBoolXor : ConstMediaAction {
    override val argc = 2

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val lhs = args.getBool(0, argc)
        val rhs = args.getBool(1, argc)
        return (lhs xor rhs).asActionResult
    }
}
