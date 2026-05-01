package at.petrak.hexlite.common.casting.actions.math.bit

import at.petrak.hexlite.api.casting.asActionResult
import at.petrak.hexlite.api.casting.castables.ConstMediaAction
import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.getList
import at.petrak.hexlite.api.casting.getLong
import at.petrak.hexlite.api.casting.getLongOrList
import at.petrak.hexlite.api.casting.iota.Iota

object OpOr : ConstMediaAction {
    override val argc = 2

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val firstParam = args.getLongOrList(0, argc)

        return firstParam.map(
            { num1 ->
                val num2 = args.getLong(1, argc)
                (num1 or num2).asActionResult
            },
            { list1 ->
                val list2 = args.getList(1, argc)
                (list1 + list2.filter { x -> list1.none { Iota.tolerates(x, it) } }).asActionResult
            }
        )
    }
}
