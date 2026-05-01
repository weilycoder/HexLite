package at.petrak.hexlite.common.casting.actions.spells.sentinel

import at.petrak.hexlite.api.casting.asActionResult
import at.petrak.hexlite.api.casting.castables.ConstMediaAction
import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.iota.Iota
import at.petrak.hexlite.api.casting.iota.NullIota
import at.petrak.hexlite.api.casting.mishaps.MishapBadCaster
import at.petrak.hexlite.api.casting.mishaps.MishapLocationInWrongDimension
import at.petrak.hexlite.api.misc.MediaConstants
import at.petrak.hexlite.xplat.IXplatAbstractions
import net.minecraft.server.level.ServerPlayer

object OpGetSentinelPos : ConstMediaAction {
    override val argc = 0
    override val mediaCost: Long = MediaConstants.DUST_UNIT / 10
    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        if (env.castingEntity !is ServerPlayer)
            throw MishapBadCaster()

        val sentinel = IXplatAbstractions.INSTANCE.getSentinel(env.castingEntity as? ServerPlayer) ?: return listOf(NullIota())
        if (sentinel.dimension != env.world.dimension())
            throw MishapLocationInWrongDimension(sentinel.dimension.location())
        return sentinel.position.asActionResult
    }
}
