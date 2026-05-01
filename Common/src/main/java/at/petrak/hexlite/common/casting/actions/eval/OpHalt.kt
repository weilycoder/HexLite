package at.petrak.hexlite.common.casting.actions.eval

import at.petrak.hexlite.api.casting.castables.Action
import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.eval.OperationResult
import at.petrak.hexlite.api.casting.eval.vm.CastingImage
import at.petrak.hexlite.api.casting.eval.vm.SpellContinuation
import at.petrak.hexlite.common.lib.hex.HexEvalSounds

object OpHalt : Action {
    override fun operate(env: CastingEnvironment, image: CastingImage, continuation: SpellContinuation): OperationResult {
        var newStack = image.stack.toList()

        var done = false
        var newCont = continuation
        while (!done && newCont is SpellContinuation.NotDone) {
            // Kotlin Y U NO destructuring assignment
            val newInfo = newCont.frame.breakDownwards(newStack)
            done = newInfo.first
            newStack = newInfo.second
            newCont = newCont.next
        }
        // if we hit no continuation boundaries (i.e. thoth/hermes exits), we've TOTALLY cleared the itinerary...
        if (!done) {
            // bomb the stack so we exit
            newStack = listOf()
        }

        val image2 = image.withUsedOp().copy(stack = newStack)
        return OperationResult(image2, listOf(), newCont, HexEvalSounds.SPELL)
    }
}
