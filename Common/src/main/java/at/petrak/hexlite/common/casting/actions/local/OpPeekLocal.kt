package at.petrak.hexlite.common.casting.actions.local

import at.petrak.hexlite.api.HexAPI
import at.petrak.hexlite.api.casting.castables.Action
import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.eval.OperationResult
import at.petrak.hexlite.api.casting.eval.vm.CastingImage
import at.petrak.hexlite.api.casting.eval.vm.SpellContinuation
import at.petrak.hexlite.api.casting.iota.IotaType
import at.petrak.hexlite.api.casting.iota.NullIota
import at.petrak.hexlite.common.lib.hex.HexEvalSounds

object OpPeekLocal : Action {
    override fun operate(env: CastingEnvironment, image: CastingImage, continuation: SpellContinuation): OperationResult {
        val stack = image.stack.toMutableList()

        val rm = if (image.userData.contains(HexAPI.RAVENMIND_USERDATA)) {
            IotaType.deserialize(image.userData.getCompound(HexAPI.RAVENMIND_USERDATA), env.world)
        } else {
            NullIota()
        }
        stack.add(rm)

        // does not mutate userdata
        val image2 = image.withUsedOp().copy(stack = stack)
        return OperationResult(image2, listOf(), continuation, HexEvalSounds.NORMAL_EXECUTE)
    }
}
