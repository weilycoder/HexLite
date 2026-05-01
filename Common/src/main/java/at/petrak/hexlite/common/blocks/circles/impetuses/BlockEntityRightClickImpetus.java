package at.petrak.hexlite.common.blocks.circles.impetuses;

import at.petrak.hexlite.api.casting.circles.BlockEntityAbstractImpetus;
import at.petrak.hexlite.common.lib.HexBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class BlockEntityRightClickImpetus extends BlockEntityAbstractImpetus {
    public BlockEntityRightClickImpetus(BlockPos pWorldPosition, BlockState pBlockState) {
        super(HexBlockEntities.IMPETUS_RIGHTCLICK_TILE, pWorldPosition, pBlockState);
    }
}
