package at.petrak.hexlite.common.casting.actions.math.trig

import at.petrak.hexlite.api.casting.asActionResult
import at.petrak.hexlite.api.casting.castables.ConstMediaAction
import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.getDouble
import at.petrak.hexlite.api.casting.iota.DoubleIota
import at.petrak.hexlite.api.casting.iota.Iota
import at.petrak.hexlite.api.casting.mishaps.MishapDivideByZero
import kotlin.math.cos
import kotlin.math.tan

object OpTan : ConstMediaAction {
    override val argc: Int
        get() = 1

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val angle = args.getDouble(0, argc)
        if (cos(angle) == 0.0)
            throw MishapDivideByZero.tan(args[0] as DoubleIota)
        return tan(angle).asActionResult
    }
}
