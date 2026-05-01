package at.petrak.hexlite.common.casting.arithmetic.operator.list

import at.petrak.hexlite.api.casting.SpellList
import at.petrak.hexlite.api.casting.arithmetic.operator.Operator
import at.petrak.hexlite.api.casting.arithmetic.operator.OperatorBasic
import at.petrak.hexlite.api.casting.arithmetic.predicates.IotaMultiPredicate
import at.petrak.hexlite.api.casting.arithmetic.predicates.IotaPredicate
import at.petrak.hexlite.api.casting.asActionResult
import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.iota.Iota
import at.petrak.hexlite.common.casting.arithmetic.operator.nextList
import at.petrak.hexlite.common.casting.arithmetic.operator.nextPositiveIntUnder
import at.petrak.hexlite.common.lib.hex.HexIotaTypes.*

object OperatorReplace : OperatorBasic(3, IotaMultiPredicate.triple(IotaPredicate.ofType(LIST), IotaPredicate.ofType(DOUBLE), IotaPredicate.TRUE)) {
    override fun apply(iotas: Iterable<Iota>, env: CastingEnvironment): Iterable<Iota> {
        val it = iotas.iterator().withIndex()
        val list = it.nextList(arity)
        val index = it.nextPositiveIntUnder(list.size(), arity)
        val iota = it.next().value
        return list.modifyAt(index) { SpellList.LPair(iota, it.cdr) }.asActionResult
    }
}