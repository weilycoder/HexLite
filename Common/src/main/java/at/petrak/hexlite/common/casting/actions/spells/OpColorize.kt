package at.petrak.hexlite.common.casting.actions.spells

import at.petrak.hexlite.api.casting.RenderedSpell
import at.petrak.hexlite.api.casting.castables.SpellAction
import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.iota.Iota
import at.petrak.hexlite.api.casting.mishaps.MishapBadOffhandItem
import at.petrak.hexlite.api.misc.MediaConstants
import at.petrak.hexlite.api.pigment.FrozenPigment
import at.petrak.hexlite.xplat.IXplatAbstractions
import net.minecraft.Util
import net.minecraft.world.item.ItemStack

object OpColorize : SpellAction {
    override val argc = 0

    override fun execute(
            args: List<Iota>,
            env: CastingEnvironment
    ): SpellAction.Result {
        val (handStack) = env.getHeldItemToOperateOn(IXplatAbstractions.INSTANCE::isPigment)
            ?: throw MishapBadOffhandItem.of(ItemStack.EMPTY, "colorizer") // TODO: hack

        if (!IXplatAbstractions.INSTANCE.isPigment(handStack)) {
            throw MishapBadOffhandItem.of(
                handStack,
                "colorizer"
            )
        }

        return SpellAction.Result(
            Spell(handStack),
            MediaConstants.DUST_UNIT,
            listOf()
        )
    }

    private data class Spell(val stack: ItemStack) : RenderedSpell {
        override fun cast(env: CastingEnvironment) {
            val copy = stack.copy()
            if (env.withdrawItem({ ItemStack.isSameItemSameTags(copy, it) }, 1, true))
                env.setPigment(FrozenPigment(copy, env.castingEntity?.uuid ?: Util.NIL_UUID))
        }
    }
}
