package at.petrak.hexlite.common.casting.actions.lists

import at.petrak.hexlite.api.casting.SpellList
import at.petrak.hexlite.api.casting.asActionResult
import at.petrak.hexlite.api.casting.castables.ConstMediaAction
import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.getList
import at.petrak.hexlite.api.casting.getPositiveIntUnder
import at.petrak.hexlite.api.casting.iota.Iota

object OpModifyInPlace : ConstMediaAction {
    override val argc = 3
    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val list = args.getList(0, argc)
        val index = args.getPositiveIntUnder(1, list.size(), argc)
        val iota = args[2]
        return list.modifyAt(index) { SpellList.LPair(iota, it.cdr) }.asActionResult
    }
}
