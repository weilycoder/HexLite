package at.petrak.hexlite.api.casting.mishaps

import at.petrak.hexlite.api.casting.ParticleSpray
import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.iota.Iota
import at.petrak.hexlite.api.pigment.FrozenPigment
import at.petrak.hexlite.common.lib.HexDamageTypes
import net.minecraft.world.entity.Mob
import net.minecraft.world.item.DyeColor

class MishapAlreadyBrainswept(val mob: Mob) : Mishap() {
    override fun accentColor(ctx: CastingEnvironment, errorCtx: Context): FrozenPigment =
        dyeColor(DyeColor.GREEN)

    override fun execute(ctx: CastingEnvironment, errorCtx: Context, stack: MutableList<Iota>) {
        mob.hurt(mob.damageSources().source(HexDamageTypes.OVERCAST, ctx.castingEntity), mob.health)
    }

    override fun particleSpray(ctx: CastingEnvironment) =
        ParticleSpray.burst(mob.eyePosition, 1.0)

    override fun errorMessage(ctx: CastingEnvironment, errorCtx: Context) =
        error("already_brainswept")

}
