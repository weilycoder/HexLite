package at.petrak.hexlite.common.casting.actions.lists

import at.petrak.hexlite.api.casting.asActionResult
import at.petrak.hexlite.api.casting.castables.ConstMediaAction
import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.getList
import at.petrak.hexlite.api.casting.iota.Iota

// it's still called beancounter's distillation in my heart
object OpListSize : ConstMediaAction {
    override val argc = 1
    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        return args.getList(0, argc).toList().size.asActionResult // mmm one-liner
    }
}
