package at.petrak.hexlite.common.casting.actions.eval

import at.petrak.hexlite.api.casting.castables.Action
import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.eval.OperationResult
import at.petrak.hexlite.api.casting.eval.vm.CastingImage
import at.petrak.hexlite.api.casting.eval.vm.SpellContinuation
import at.petrak.hexlite.api.casting.iota.ContinuationIota
import at.petrak.hexlite.api.casting.mishaps.MishapNotEnoughArgs

object OpEvalBreakable : Action {
    override fun operate(env: CastingEnvironment,
                         image: CastingImage,
                         continuation: SpellContinuation): OperationResult {
        val stack = image.stack.toMutableList()
        val iota = stack.removeLastOrNull() ?: throw MishapNotEnoughArgs(1, 0)
        stack.add(ContinuationIota(continuation))
        return OpEval.exec(env, image, continuation, stack, iota)
    }
}
