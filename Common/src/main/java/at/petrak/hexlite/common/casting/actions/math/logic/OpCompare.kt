package at.petrak.hexlite.common.casting.actions.math.logic

import at.petrak.hexlite.api.casting.asActionResult
import at.petrak.hexlite.api.casting.castables.ConstMediaAction
import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.getDouble
import at.petrak.hexlite.api.casting.iota.DoubleIota
import at.petrak.hexlite.api.casting.iota.Iota
import java.util.function.BiPredicate

class OpCompare(val acceptsEqual: Boolean, val cmp: BiPredicate<Double, Double>) : ConstMediaAction {
    override val argc = 2

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val lhs = args.getDouble(0, argc)
        val rhs = args.getDouble(1, argc)
        if (DoubleIota.tolerates(lhs, rhs))
            return acceptsEqual.asActionResult

        return cmp.test(lhs, rhs).asActionResult
    }
}
