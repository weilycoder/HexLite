package at.petrak.hexlite.api.casting.arithmetic.operator

import at.petrak.hexlite.api.casting.arithmetic.predicates.IotaMultiPredicate
import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.eval.OperationResult
import at.petrak.hexlite.api.casting.eval.vm.CastingImage
import at.petrak.hexlite.api.casting.eval.vm.SpellContinuation
import at.petrak.hexlite.api.casting.iota.Iota
import at.petrak.hexlite.api.casting.mishaps.Mishap
import at.petrak.hexlite.common.lib.hex.HexEvalSounds
import java.util.function.Consumer

abstract class OperatorBasic(arity: Int, accepts: IotaMultiPredicate) : Operator(arity, accepts) {

    @Throws(Mishap::class)
    override fun operate(env: CastingEnvironment, image: CastingImage, continuation: SpellContinuation): OperationResult {
        val stack = image.stack.toMutableList()
        val args = stack.takeLast(arity)
        repeat(arity) { stack.removeLast() }

        val ret = apply(args, env)
        ret.forEach(Consumer { e: Iota -> stack.add(e) })

        val image2 = image.copy(stack = stack, opsConsumed = image.opsConsumed + 1)
        return OperationResult(image2, listOf(), continuation, HexEvalSounds.NORMAL_EXECUTE)
    }

    /**
     * / **
     * The method called when this Operator is actually acting on the stack, for real.
     * @param iotas An iterable of iotas with [Operator.arity] elements that satisfied [Operator.accepts].
     * @param env The casting environment, to make use of if this operator needs it.
     * @return the iotas that this operator will return to the stack (with the first element of the returned iterable being placed deepest into the stack, and the last element on top of the stack).
     * @throws Mishap if the Operator mishaps for any reason it will be passed up the chain.
     */
    @Throws(Mishap::class)
    abstract fun apply(iotas: Iterable<Iota>, env: CastingEnvironment): Iterable<Iota>

}