package at.petrak.hexlite.common.casting.actions.akashic

import at.petrak.hexlite.api.casting.castables.ConstMediaAction
import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.getBlockPos
import at.petrak.hexlite.api.casting.getPattern
import at.petrak.hexlite.api.casting.iota.Iota
import at.petrak.hexlite.api.casting.iota.NullIota
import at.petrak.hexlite.api.casting.mishaps.MishapNoAkashicRecord
import at.petrak.hexlite.api.misc.MediaConstants
import at.petrak.hexlite.common.blocks.akashic.BlockAkashicRecord

object OpAkashicRead : ConstMediaAction {
    override val argc = 2
    override val mediaCost: Long = MediaConstants.DUST_UNIT

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val pos = args.getBlockPos(0, argc)
        val key = args.getPattern(1, argc)

        val record = env.world.getBlockState(pos).block
        if (record !is BlockAkashicRecord) {
            throw MishapNoAkashicRecord(pos)
        }

        val datum = record.lookupPattern(pos, key, env.world)
        return listOf(datum ?: NullIota())
    }
}
