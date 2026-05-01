package at.petrak.hexlite.common.casting.actions.eval

import at.petrak.hexlite.api.casting.castables.Action
import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.eval.OperationResult
import at.petrak.hexlite.api.casting.eval.vm.CastingImage
import at.petrak.hexlite.api.casting.eval.vm.SpellContinuation
import at.petrak.hexlite.api.casting.iota.DoubleIota
import at.petrak.hexlite.api.mod.HexConfig
import at.petrak.hexlite.common.lib.hex.HexEvalSounds

object OpThanos : Action {
    override fun operate(env: CastingEnvironment, image: CastingImage, continuation: SpellContinuation): OperationResult {
        val opsLeft = env.maxOpCount() - image.opsConsumed
        val stack = image.stack.toMutableList()
        stack.add(DoubleIota(opsLeft.toDouble()))

        val image2 = image.withUsedOp().copy(stack = stack)
        return OperationResult(image2, listOf(), continuation, HexEvalSounds.NORMAL_EXECUTE)
    }
}