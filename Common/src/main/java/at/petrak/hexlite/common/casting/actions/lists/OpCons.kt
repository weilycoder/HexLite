package at.petrak.hexlite.common.casting.actions.lists

import at.petrak.hexlite.api.casting.SpellList
import at.petrak.hexlite.api.casting.asActionResult
import at.petrak.hexlite.api.casting.castables.ConstMediaAction
import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.getList
import at.petrak.hexlite.api.casting.iota.Iota

object OpCons : ConstMediaAction {
    override val argc = 2
    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val bottom = args.getList(0, argc)
        val top = args[1]
        return SpellList.LPair(top, bottom).asActionResult
    }
}
