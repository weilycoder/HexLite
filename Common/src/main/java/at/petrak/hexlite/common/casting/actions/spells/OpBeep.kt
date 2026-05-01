package at.petrak.hexlite.common.casting.actions.spells

import at.petrak.hexlite.api.casting.*
import at.petrak.hexlite.api.casting.castables.SpellAction
import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.iota.Iota
import at.petrak.hexlite.api.misc.MediaConstants
import at.petrak.hexlite.common.msgs.MsgBeepS2C
import at.petrak.hexlite.xplat.IXplatAbstractions
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument
import net.minecraft.world.level.gameevent.GameEvent
import net.minecraft.world.phys.Vec3

object OpBeep : SpellAction {
    override val argc = 3

    override fun execute(
            args: List<Iota>,
            env: CastingEnvironment
    ): SpellAction.Result {
        val target = args.getVec3(0, argc)
        val instrument = args.getPositiveIntUnder(1, NoteBlockInstrument.values().size, argc)
        val note = args.getPositiveIntUnderInclusive(2, 24, argc) // mojang don't have magic numbers challenge
        env.assertVecInRange(target)

        return SpellAction.Result(
            Spell(target, note, NoteBlockInstrument.values()[instrument]),
            MediaConstants.DUST_UNIT / 10,
            listOf(ParticleSpray.cloud(target, 1.0))
        )
    }

    override fun hasCastingSound(ctx: CastingEnvironment) = false

    private data class Spell(val target: Vec3, val note: Int, val instrument: NoteBlockInstrument) : RenderedSpell {
        override fun cast(env: CastingEnvironment) {
            IXplatAbstractions.INSTANCE.sendPacketNear(target, 128.0, env.world, MsgBeepS2C(target, note, instrument))
            env.world.gameEvent(null, GameEvent.NOTE_BLOCK_PLAY, target)
        }
    }
}
