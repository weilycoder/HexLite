package at.petrak.hexlite.common.casting.actions.queryentity

import at.petrak.hexlite.api.casting.asActionResult
import at.petrak.hexlite.api.casting.castables.ConstMediaAction
import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.getPlayer
import at.petrak.hexlite.api.casting.iota.Iota
import at.petrak.hexlite.xplat.IXplatAbstractions

object OpCanEntityHexFly : ConstMediaAction {
    override val argc = 1

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val player = args.getPlayer(0, argc)
        env.assertEntityInRange(player)

        val flightAbility = IXplatAbstractions.INSTANCE.getFlight(player)
        return (flightAbility != null).asActionResult
    }
}
