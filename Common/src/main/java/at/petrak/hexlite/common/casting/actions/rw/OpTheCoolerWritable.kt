package at.petrak.hexlite.common.casting.actions.rw

import at.petrak.hexlite.api.casting.asActionResult
import at.petrak.hexlite.api.casting.castables.ConstMediaAction
import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.getEntity
import at.petrak.hexlite.api.casting.iota.Iota
import at.petrak.hexlite.api.casting.iota.NullIota
import at.petrak.hexlite.xplat.IXplatAbstractions

object OpTheCoolerWritable : ConstMediaAction {
    override val argc = 1

    override fun execute(
        args: List<Iota>,
        env: CastingEnvironment
    ): List<Iota> {
        val target = args.getEntity(0, argc)
        env.assertEntityInRange(target)

        val datumHolder = IXplatAbstractions.INSTANCE.findDataHolder(target)
            ?: return false.asActionResult

        val success = datumHolder.writeIota(NullIota(), true)

        return success.asActionResult
    }
}
