package at.petrak.hexlite.interop.pehkui

import at.petrak.hexlite.api.casting.asActionResult
import at.petrak.hexlite.api.casting.castables.ConstMediaAction
import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.getEntity
import at.petrak.hexlite.api.casting.iota.Iota
import at.petrak.hexlite.xplat.IXplatAbstractions

object OpGetScale : ConstMediaAction {
    override val argc = 1

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val target = args.getEntity(0, argc)
        env.assertEntityInRange(target)
        return IXplatAbstractions.INSTANCE.pehkuiApi.getScale(target).toDouble().asActionResult
    }
}
