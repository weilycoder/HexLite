package at.petrak.hexlite.common.blocks;

import net.minecraft.world.level.block.Block;

/**
 * Does absolutely nothing on Fabric; the flammable block registry is for that.
 */
public class BlockFlammable extends Block {
    public final int burn, spread;

    public BlockFlammable(Properties $$0, int burn, int spread) {
        super($$0);
        this.burn = burn;
        this.spread = spread;
    }
}
