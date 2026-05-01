package at.petrak.hexlite.api.casting.mishaps

import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.iota.Iota
import at.petrak.hexlite.api.pigment.FrozenPigment
import at.petrak.hexlite.api.utils.aqua
import at.petrak.hexlite.api.utils.asTranslatedComponent
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.item.DyeColor

class MishapBadEntity(val entity: Entity, val wanted: Component) : Mishap() {
    override fun accentColor(ctx: CastingEnvironment, errorCtx: Context): FrozenPigment =
        dyeColor(DyeColor.BROWN)

    override fun execute(env: CastingEnvironment, errorCtx: Context, stack: MutableList<Iota>) {
        env.mishapEnvironment.yeetHeldItemsTowards(entity.position())
    }

    override fun errorMessage(ctx: CastingEnvironment, errorCtx: Context) =
        error("bad_entity", wanted, entity.displayName.plainCopy().aqua)

    companion object {
        @JvmStatic
        fun of(entity: Entity, stub: String): Mishap {
            val component = "hexlite.mishap.bad_item.$stub".asTranslatedComponent
            if (entity is ItemEntity)
                return MishapBadItem(entity, component)
            return MishapBadEntity(entity, component)
        }
    }
}
