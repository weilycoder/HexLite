package at.petrak.hexlite.common.casting.actions.math.bit

import at.petrak.hexlite.api.casting.asActionResult
import at.petrak.hexlite.api.casting.castables.ConstMediaAction
import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.getLong
import at.petrak.hexlite.api.casting.iota.Iota

object OpNot : ConstMediaAction {
    override val argc = 1

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val num = args.getLong(0, argc)
        return num.inv().asActionResult
    }
}
