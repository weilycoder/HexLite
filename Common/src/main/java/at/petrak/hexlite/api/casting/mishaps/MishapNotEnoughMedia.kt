package at.petrak.hexlite.api.casting.mishaps

import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.eval.ResolvedPatternType
import at.petrak.hexlite.api.casting.iota.Iota
import at.petrak.hexlite.api.pigment.FrozenPigment
import at.petrak.hexlite.api.utils.asTranslatedComponent
import net.minecraft.world.item.DyeColor

class MishapNotEnoughMedia(private val cost: Long) : Mishap() {
    override fun accentColor(ctx: CastingEnvironment, errorCtx: Context): FrozenPigment =
        dyeColor(DyeColor.RED)

    override fun resolutionType(ctx: CastingEnvironment) = ResolvedPatternType.ERRORED

    override fun execute(env: CastingEnvironment, errorCtx: Context, stack: MutableList<Iota>) {
        env.extractMedia(cost, false)
    }

    override fun errorMessage(ctx: CastingEnvironment, errorCtx: Context) = "hexlite.message.cant_overcast".asTranslatedComponent
}