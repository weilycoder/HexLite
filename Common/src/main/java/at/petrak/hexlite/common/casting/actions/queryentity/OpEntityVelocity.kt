package at.petrak.hexlite.common.casting.actions.queryentity

import at.petrak.hexlite.api.HexAPI
import at.petrak.hexlite.api.casting.asActionResult
import at.petrak.hexlite.api.casting.castables.ConstMediaAction
import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.getEntity
import at.petrak.hexlite.api.casting.iota.Iota

object OpEntityVelocity : ConstMediaAction {
    override val argc = 1

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val e = args.getEntity(0, argc)
        env.assertEntityInRange(e)

        val vel = HexAPI.instance().getEntityVelocitySpecial(e)
        return vel.asActionResult
    }
}
