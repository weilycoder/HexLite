package at.petrak.hexlite.common.casting.actions.math

import at.petrak.hexlite.api.casting.castables.ConstMediaAction
import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.getVec3
import at.petrak.hexlite.api.casting.iota.DoubleIota
import at.petrak.hexlite.api.casting.iota.Iota

object OpDeconstructVec : ConstMediaAction {
    override val argc = 1
    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val v = args.getVec3(0, argc)
        return listOf(DoubleIota(v.x), DoubleIota(v.y), DoubleIota(v.z))
    }
}
