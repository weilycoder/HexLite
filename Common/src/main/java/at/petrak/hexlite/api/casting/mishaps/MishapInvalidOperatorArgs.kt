package at.petrak.hexlite.api.casting.mishaps

import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.iota.GarbageIota
import at.petrak.hexlite.api.casting.iota.Iota
import at.petrak.hexlite.api.pigment.FrozenPigment
import at.petrak.hexlite.api.utils.asTextComponent
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.ComponentUtils
import net.minecraft.world.item.DyeColor

/**
 * The value failed some kind of predicate.
 */
class MishapInvalidOperatorArgs(val perpetrators: List<Iota>) : Mishap() {
    override fun accentColor(ctx: CastingEnvironment, errorCtx: Context): FrozenPigment =
        dyeColor(DyeColor.GRAY)

    override fun execute(env: CastingEnvironment, errorCtx: Context, stack: MutableList<Iota>) {
        for (i in perpetrators.indices) {
            stack[stack.size - 1 - i] = GarbageIota()
        }
    }

    override fun errorMessage(ctx: CastingEnvironment, errorCtx: Context): Component {
        return if (perpetrators.size == 1) {
            error(
                "invalid_operator_args.one",
                0,
                perpetrators[0].display()
            )
        } else {
            error(
                "invalid_operator_args.many",
                perpetrators.size,
                0,
                perpetrators.lastIndex,
                ComponentUtils.formatList(perpetrators.map { it.display() }, ", ".asTextComponent)
            )
        }
    }
}
