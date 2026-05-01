package at.petrak.hexlite.common.items.magic;

import at.petrak.hexlite.api.item.VariantItem;
import at.petrak.hexlite.api.mod.HexConfig;
import net.minecraft.world.item.ItemStack;

import static at.petrak.hexlite.common.items.storage.ItemFocus.NUM_VARIANTS;

public class ItemTrinket extends ItemPackagedHex implements VariantItem {
    public ItemTrinket(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean canDrawMediaFromInventory(ItemStack stack) {
        return false;
    }

    @Override
    public boolean breakAfterDepletion() {
        return false;
    }

    @Override
    public int cooldown() {
        return HexConfig.common().trinketCooldown();
    }

    @Override
    public int numVariants() {
        return NUM_VARIANTS;
    }
}
