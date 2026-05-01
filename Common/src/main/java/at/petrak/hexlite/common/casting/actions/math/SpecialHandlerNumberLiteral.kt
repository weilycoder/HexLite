package at.petrak.hexlite.common.casting.actions.math

import at.petrak.hexlite.api.HexAPI
import at.petrak.hexlite.api.casting.asActionResult
import at.petrak.hexlite.api.casting.castables.Action
import at.petrak.hexlite.api.casting.castables.ConstMediaAction
import at.petrak.hexlite.api.casting.castables.SpecialHandler
import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.iota.Iota
import at.petrak.hexlite.api.casting.math.HexPattern
import at.petrak.hexlite.api.utils.asTranslatedComponent
import at.petrak.hexlite.api.utils.lightPurple
import at.petrak.hexlite.common.lib.hex.HexSpecialHandlers
import at.petrak.hexlite.xplat.IXplatAbstractions
import net.minecraft.network.chat.Component

class SpecialHandlerNumberLiteral(val x: Double) : SpecialHandler {
    override fun act(): Action {
        return InnerAction(this.x)
    }

    override fun getName(): Component {
        val key = IXplatAbstractions.INSTANCE.specialHandlerRegistry.getResourceKey(HexSpecialHandlers.NUMBER).get()
        return HexAPI.instance().getSpecialHandlerI18nKey(key)
            .asTranslatedComponent(Action.DOUBLE_FORMATTER.format(x)).lightPurple
    }

    class InnerAction(val x: Double) : ConstMediaAction {
        override val argc = 0

        override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
            return this.x.asActionResult
        }
    }

    class Factory : SpecialHandler.Factory<SpecialHandlerNumberLiteral> {
        override fun tryMatch(pat: HexPattern, env: CastingEnvironment): SpecialHandlerNumberLiteral? {
            val sig = pat.anglesSignature()
            if (sig.startsWith("aqaa") || sig.startsWith("dedd")) {
                val negate = sig.startsWith("dedd");
                var accumulator = 0.0;
                for (ch in sig.substring(4)) {
                    when (ch) {
                        'w' -> {
                            accumulator += 1;
                        }

                        'q' -> {
                            accumulator += 5;
                        }

                        'e' -> {
                            accumulator += 10;
                        }

                        'a' -> {
                            accumulator *= 2;
                        }

                        'd' -> {
                            accumulator /= 2;
                        }
                        // ok funny man
                        's' -> {}
                        else -> throw IllegalStateException()
                    }
                }
                if (negate) {
                    accumulator = -accumulator;
                }
                return SpecialHandlerNumberLiteral(accumulator);
            } else {
                return null;
            }
        }

    }
}