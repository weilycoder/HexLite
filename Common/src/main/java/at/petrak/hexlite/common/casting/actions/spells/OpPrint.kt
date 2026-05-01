package at.petrak.hexlite.common.casting.actions.spells

import at.petrak.hexlite.api.casting.RenderedSpell
import at.petrak.hexlite.api.casting.castables.Action
import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.eval.OperationResult
import at.petrak.hexlite.api.casting.eval.sideeffects.OperatorSideEffect
import at.petrak.hexlite.api.casting.eval.vm.CastingImage
import at.petrak.hexlite.api.casting.eval.vm.SpellContinuation
import at.petrak.hexlite.api.casting.iota.Iota
import at.petrak.hexlite.api.casting.mishaps.MishapNotEnoughArgs
import at.petrak.hexlite.common.lib.hex.HexEvalSounds

// TODO should this dump the whole stack
object OpPrint : Action {
    override fun operate(env: CastingEnvironment, image: CastingImage, continuation: SpellContinuation): OperationResult {
        val stack = image.stack.toMutableList()

        if (stack.isEmpty()) {
            throw MishapNotEnoughArgs(1, 0)
        }
        val datum = stack[stack.lastIndex]

        val image2 = image.withUsedOp().copy(stack = stack)
        return OperationResult(
            image2,
            listOf(
                OperatorSideEffect.AttemptSpell(Spell(datum), hasCastingSound = false, awardStat = false)
            ),
            continuation,
            HexEvalSounds.SPELL,
        )
    }

    private data class Spell(val datum: Iota) : RenderedSpell {
        override fun cast(env: CastingEnvironment) {
            env.printMessage(datum.display())
        }
    }
}
