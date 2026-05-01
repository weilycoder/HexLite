package at.petrak.hexlite.common.casting.arithmetic.operator.list

import at.petrak.hexlite.api.casting.arithmetic.operator.Operator
import at.petrak.hexlite.api.casting.arithmetic.operator.OperatorBasic
import at.petrak.hexlite.api.casting.arithmetic.predicates.IotaMultiPredicate
import at.petrak.hexlite.api.casting.arithmetic.predicates.IotaPredicate
import at.petrak.hexlite.api.casting.asActionResult
import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.iota.Iota
import at.petrak.hexlite.common.casting.arithmetic.operator.nextInt
import at.petrak.hexlite.common.casting.arithmetic.operator.nextList
import at.petrak.hexlite.common.lib.hex.HexIotaTypes.*

object OperatorRemove : OperatorBasic(2, IotaMultiPredicate.pair(IotaPredicate.ofType(LIST), IotaPredicate.ofType(DOUBLE))) {
    override fun apply(iotas: Iterable<Iota>, env: CastingEnvironment): Iterable<Iota> {
        val it = iotas.iterator().withIndex()
        val list = it.nextList(arity).toMutableList()
        val index = it.nextInt(arity)
        if (index < 0 || index >= list.size)
            return list.asActionResult
        list.removeAt(index)
        return list.asActionResult
    }
}