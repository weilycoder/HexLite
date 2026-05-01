package at.petrak.hexlite.common.casting.arithmetic.operator.list

import at.petrak.hexlite.api.casting.arithmetic.operator.Operator
import at.petrak.hexlite.api.casting.arithmetic.operator.OperatorBasic
import at.petrak.hexlite.api.casting.arithmetic.predicates.IotaMultiPredicate
import at.petrak.hexlite.api.casting.arithmetic.predicates.IotaPredicate
import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.iota.Iota
import at.petrak.hexlite.api.casting.iota.ListIota
import at.petrak.hexlite.api.casting.iota.NullIota
import at.petrak.hexlite.common.casting.arithmetic.operator.nextList
import at.petrak.hexlite.common.lib.hex.HexIotaTypes.*

object OperatorUnappend : OperatorBasic(1, IotaMultiPredicate.all(IotaPredicate.ofType(LIST))) {
    override fun apply(iotas: Iterable<Iota>, env: CastingEnvironment): Iterable<Iota> {
        val it = iotas.iterator().withIndex()
        val list = it.nextList(arity).toMutableList()
        val last = list.removeLastOrNull() ?: NullIota()
        return listOf(ListIota(list), last)
    }
}