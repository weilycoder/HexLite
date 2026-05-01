package at.petrak.hexlite.api.casting.mishaps

import at.petrak.hexlite.api.casting.ParticleSpray
import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.iota.Iota
import at.petrak.hexlite.api.pigment.FrozenPigment
import at.petrak.hexlite.api.utils.asTranslatedComponent
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.Explosion
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3

class MishapBadBlock(val pos: BlockPos, val expected: Component) : Mishap() {
    override fun accentColor(ctx: CastingEnvironment, errorCtx: Context): FrozenPigment =
        dyeColor(DyeColor.LIME)

    override fun execute(ctx: CastingEnvironment, errorCtx: Context, stack: MutableList<Iota>) {
        ctx.world.explode(null, pos.x + 0.5, pos.y + 0.5, pos.z + 0.5, 0.25f, Level.ExplosionInteraction.NONE)
    }

    override fun particleSpray(ctx: CastingEnvironment) =
        ParticleSpray.burst(Vec3.atCenterOf(pos), 1.0)

    override fun errorMessage(ctx: CastingEnvironment, errorCtx: Context) =
        error("bad_block", expected, this.pos.toShortString(), blockAtPos(ctx, this.pos))

    companion object {
        @JvmStatic
        fun of(pos: BlockPos, stub: String): MishapBadBlock {
            return MishapBadBlock(pos, "hexlite.mishap.bad_block.$stub".asTranslatedComponent)
        }
    }
}
