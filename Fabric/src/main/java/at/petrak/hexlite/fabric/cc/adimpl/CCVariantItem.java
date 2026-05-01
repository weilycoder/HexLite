package at.petrak.hexlite.fabric.cc.adimpl;

import at.petrak.hexlite.api.addldata.ADVariantItem;
import at.petrak.hexlite.api.item.VariantItem;
import at.petrak.hexlite.fabric.cc.HexCardinalComponents;
import dev.onyxstudios.cca.api.v3.item.ItemComponent;
import net.minecraft.world.item.ItemStack;

public abstract class CCVariantItem extends ItemComponent implements ADVariantItem {
    public CCVariantItem(ItemStack stack) {
        super(stack, HexCardinalComponents.VARIANT_ITEM);
    }

    public static class ItemBased extends CCVariantItem {
        private final VariantItem variantItem;

        public ItemBased(ItemStack owner) {
            super(owner);
            var item = owner.getItem();
            if (!(item instanceof VariantItem variantItem)) {
                throw new IllegalStateException("item is not a colorizer: " + owner);
            }
            this.variantItem = variantItem;
        }

        @Override
        public int numVariants() {
            return variantItem.numVariants();
        }

        @Override
        public int getVariant() {
            return variantItem.getVariant(this.stack);
        }

        @Override
        public void setVariant(int variant) {
            variantItem.setVariant(this.stack, variant);
        }
    }
}
