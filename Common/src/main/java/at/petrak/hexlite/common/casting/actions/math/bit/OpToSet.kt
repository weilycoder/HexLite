package at.petrak.hexlite.common.casting.actions.math.bit

import at.petrak.hexlite.api.casting.asActionResult
import at.petrak.hexlite.api.casting.castables.ConstMediaAction
import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.getList
import at.petrak.hexlite.api.casting.iota.Iota

object OpToSet : ConstMediaAction {
    override val argc = 1

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val list = args.getList(0, argc)
        val out = mutableListOf<Iota>()

        for (subiota in list) {
            if (out.none { Iota.tolerates(it, subiota) }) {
                out.add(subiota)
            }
        }

        return out.asActionResult
    }
}
