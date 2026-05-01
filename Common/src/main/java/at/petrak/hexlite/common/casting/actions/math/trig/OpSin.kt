package at.petrak.hexlite.common.casting.actions.math.trig

import at.petrak.hexlite.api.casting.asActionResult
import at.petrak.hexlite.api.casting.castables.ConstMediaAction
import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.getDouble
import at.petrak.hexlite.api.casting.iota.Iota
import kotlin.math.sin

object OpSin : ConstMediaAction {
    override val argc: Int
        get() = 1

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val angle = args.getDouble(0, argc)
        return sin(angle).asActionResult
    }
}
