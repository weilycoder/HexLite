package at.petrak.hexlite.common.blocks.entity;

import at.petrak.hexlite.api.block.HexBlockEntity;
import at.petrak.hexlite.common.blocks.BlockQuenchedAllay;
import at.petrak.hexlite.common.lib.HexBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.BiFunction;

/**
 * No-op BE just to have a BER
 */
public class BlockEntityQuenchedAllay extends HexBlockEntity {
    public BlockEntityQuenchedAllay(BlockQuenchedAllay block, BlockPos pos, BlockState blockState) {
        super(HexBlockEntities.typeForQuenchedAllay(block), pos, blockState);
    }

    public static BiFunction<BlockPos, BlockState, BlockEntityQuenchedAllay> fromKnownBlock(BlockQuenchedAllay block) {
        return (pos, state) -> new BlockEntityQuenchedAllay(block, pos, state);
    }

    @Override
    protected void saveModData(CompoundTag tag) {

    }

    @Override
    protected void loadModData(CompoundTag tag) {

    }
}
