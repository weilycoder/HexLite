package at.petrak.hexlite.common.casting.actions.rw

import at.petrak.hexlite.api.casting.asActionResult
import at.petrak.hexlite.api.casting.castables.ConstMediaAction
import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.iota.Iota
import at.petrak.hexlite.xplat.IXplatAbstractions

object OpReadable : ConstMediaAction {
    override val argc = 0

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val (handStack) = env.getHeldItemToOperateOn {
            IXplatAbstractions.INSTANCE.findDataHolder(it) != null
        } ?: return false.asActionResult

        val datumHolder = IXplatAbstractions.INSTANCE.findDataHolder(handStack)
            ?: return false.asActionResult

        // If the datum contains no iota, return whether it has a default empty iota.
        datumHolder.readIota(env.world)
            ?: return (datumHolder.emptyIota() != null).asActionResult

        return true.asActionResult
    }
}
