package at.petrak.hexlite.common.casting.actions.stack

import at.petrak.hexlite.api.casting.castables.Action
import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.eval.OperationResult
import at.petrak.hexlite.api.casting.eval.vm.CastingImage
import at.petrak.hexlite.api.casting.eval.vm.SpellContinuation
import at.petrak.hexlite.api.casting.getIntBetween
import at.petrak.hexlite.api.casting.mishaps.MishapNotEnoughArgs
import at.petrak.hexlite.common.lib.hex.HexEvalSounds

object OpFishermanButItCopies : Action {
    override fun operate(env: CastingEnvironment, image: CastingImage, continuation: SpellContinuation): OperationResult {
        val stack = image.stack.toMutableList()

        if (stack.size < 2)
            throw MishapNotEnoughArgs(2, stack.size)

        val depth = stack.getIntBetween(stack.lastIndex, -(stack.size - 2), stack.size - 2)
        stack.removeLast()

        if (depth >= 0) {
            val fish = stack[stack.size - 1 - depth]
            stack.add(fish)
        } else {
            val lure = stack.last()
            stack.add(stack.size - 1 + depth, lure)
        }

        val image2 = image.withUsedOp().copy(stack = stack)
        return OperationResult(image2, listOf(), continuation, HexEvalSounds.NORMAL_EXECUTE)
    }
}
