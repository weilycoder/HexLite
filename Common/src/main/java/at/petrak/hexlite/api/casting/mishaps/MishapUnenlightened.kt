package at.petrak.hexlite.api.casting.mishaps

import at.petrak.hexlite.api.advancements.HexAdvancementTriggers
import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.eval.ResolvedPatternType
import at.petrak.hexlite.api.casting.iota.Iota
import at.petrak.hexlite.api.pigment.FrozenPigment
import at.petrak.hexlite.api.utils.asTranslatedComponent
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.item.DyeColor

class MishapUnenlightened : Mishap() {
    override fun accentColor(ctx: CastingEnvironment, errorCtx: Context): FrozenPigment =
        dyeColor(DyeColor.RED)

    override fun resolutionType(ctx: CastingEnvironment) = ResolvedPatternType.INVALID

    override fun execute(env: CastingEnvironment, errorCtx: Context, stack: MutableList<Iota>) {
        env.mishapEnvironment.dropHeldItems()
        env.castingEntity?.sendSystemMessage("hexlite.message.cant_great_spell".asTranslatedComponent)

        // add some non-zero level of juice I guess
        val pos = env.mishapSprayPos()
        env.world.playSound(null, pos.x, pos.y, pos.z, SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 0.5f, 0.7f)

        val castingPlayer = env.castingEntity as? ServerPlayer
        if (castingPlayer != null) {
            HexAdvancementTriggers.FAIL_GREAT_SPELL_TRIGGER.trigger(castingPlayer)
        }
    }

    override fun errorMessage(ctx: CastingEnvironment, errorCtx: Context) = null
}
