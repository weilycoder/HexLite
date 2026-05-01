package at.petrak.hexlite.common.casting.actions.math

import at.petrak.hexlite.api.casting.aplKinnie
import at.petrak.hexlite.api.casting.castables.ConstMediaAction
import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.getNumOrVec
import at.petrak.hexlite.api.casting.iota.Iota
import kotlin.math.floor

object OpFloor : ConstMediaAction {
    override val argc: Int
        get() = 1

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val value = args.getNumOrVec(0, argc)
        return listOf(aplKinnie(value, ::floor))
    }
}
