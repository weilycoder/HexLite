package at.petrak.hexlite.fabric.cc.adimpl;

import at.petrak.hexlite.api.addldata.ADPigment;
import at.petrak.hexlite.api.item.PigmentItem;
import at.petrak.hexlite.api.pigment.ColorProvider;
import at.petrak.hexlite.fabric.cc.HexCardinalComponents;
import dev.onyxstudios.cca.api.v3.item.ItemComponent;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

/**
 * The pigment itself
 */
public abstract class CCPigment extends ItemComponent implements ADPigment {
    public CCPigment(ItemStack stack) {
        super(stack, HexCardinalComponents.PIGMENT);
    }

    public static class ItemBased extends CCPigment {
        private final PigmentItem item;

        public ItemBased(ItemStack owner) {
            super(owner);
            var item = owner.getItem();
            if (!(item instanceof PigmentItem col)) {
                throw new IllegalStateException("item is not a pigment: " + owner);
            }
            this.item = col;
        }

        @Override
        public ColorProvider provideColor(UUID owner) {
            return item.provideColor(this.stack, owner);
        }
    }
}
