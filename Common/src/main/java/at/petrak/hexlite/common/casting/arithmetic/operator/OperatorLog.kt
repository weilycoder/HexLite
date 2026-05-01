package at.petrak.hexlite.common.casting.arithmetic.operator

import at.petrak.hexlite.api.casting.arithmetic.operator.Operator
import at.petrak.hexlite.api.casting.arithmetic.operator.OperatorBasic
import at.petrak.hexlite.api.casting.arithmetic.predicates.IotaMultiPredicate
import at.petrak.hexlite.api.casting.arithmetic.predicates.IotaPredicate
import at.petrak.hexlite.api.casting.asActionResult
import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.iota.Iota
import at.petrak.hexlite.api.casting.mishaps.MishapDivideByZero
import at.petrak.hexlite.common.lib.hex.HexIotaTypes.DOUBLE
import kotlin.math.log

object OperatorLog : OperatorBasic(2, IotaMultiPredicate.all(IotaPredicate.ofType(DOUBLE))) {
    override fun apply(iotas: Iterable<Iota>, env : CastingEnvironment): Iterable<Iota> {
        val it = iotas.iterator().withIndex()
        val value = it.nextDouble(arity)
        val base = it.nextDouble(arity)
        if (value <= 0.0 || base <= 0.0 || base == 1.0)
            throw MishapDivideByZero.of(iotas.first(), iotas.last(), "logarithm")
        return log(value, base).asActionResult
    }
}