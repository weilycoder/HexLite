package at.petrak.hexlite.common.casting.actions.lists

import at.petrak.hexlite.api.casting.asActionResult
import at.petrak.hexlite.api.casting.castables.ConstMediaAction
import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.getList
import at.petrak.hexlite.api.casting.iota.Iota

object OpIndexOf : ConstMediaAction {
    override val argc: Int
        get() = 2

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val list = args.getList(0, argc).toMutableList()
        val value = args[1]
        return list.indexOfFirst { Iota.tolerates(value, it) }.asActionResult
    }
}
