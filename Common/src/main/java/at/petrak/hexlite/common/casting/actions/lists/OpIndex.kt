package at.petrak.hexlite.common.casting.actions.lists

import at.petrak.hexlite.api.casting.castables.ConstMediaAction
import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.getDouble
import at.petrak.hexlite.api.casting.getList
import at.petrak.hexlite.api.casting.iota.Iota
import at.petrak.hexlite.api.casting.iota.NullIota
import kotlin.math.roundToInt

object OpIndex : ConstMediaAction {
    override val argc = 2
    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val list = args.getList(0, argc).toMutableList()
        val index = args.getDouble(1, argc)
        val x = list.getOrElse(index.roundToInt()) { NullIota() }
        return listOf(x)
    }
}
