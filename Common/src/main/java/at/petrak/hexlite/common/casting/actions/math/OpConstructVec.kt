package at.petrak.hexlite.common.casting.actions.math

import at.petrak.hexlite.api.casting.asActionResult
import at.petrak.hexlite.api.casting.castables.ConstMediaAction
import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.getDouble
import at.petrak.hexlite.api.casting.iota.Iota
import net.minecraft.world.phys.Vec3

object OpConstructVec : ConstMediaAction {
    override val argc = 3
    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val x = args.getDouble(0, argc)
        val y = args.getDouble(1, argc)
        val z = args.getDouble(2, argc)
        return Vec3(x, y, z).asActionResult
    }
}
