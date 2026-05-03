package virtuoel.pehkui.mixin.compat117plus;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(ContainerOpenersCounter.class)
public class ViewerCountManagerMixin
{
	@Shadow
	int openCount;
	
	@Unique
	float viewerSearchRange = 5.0F;
	
	@Inject(at = @At("HEAD"), method = "incrementOpeners(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V")
	private void pehkui$openContainer(Player player, Level world, BlockPos pos, BlockState state, CallbackInfo info)
	{
		if (openCount < 0)
		{
			openCount = 0;
			
			viewerSearchRange = 5.0F;
		}
		
		final float scale = ScaleUtils.getBlockReachScale(player);
		
		if (scale != 1.0F)
		{
			final float nextRange = 5.0F * scale;
			
			if (nextRange > viewerSearchRange)
			{
				viewerSearchRange = nextRange;
			}
		}
	}
	
	@Inject(at = @At("HEAD"), method = "decrementOpeners(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V")
	private void pehkui$closeContainer(Player player, Level world, BlockPos pos, BlockState state, CallbackInfo info)
	{
		if (openCount <= 1)
		{
			openCount = 1;
			
			viewerSearchRange = 5.0F;
		}
	}
	
	@ModifyConstant(method = "getOpenCount", constant = @Constant(floatValue = 5.0F))
	private float pehkui$getInRangeViewerCount$range(float value)
	{
		return viewerSearchRange;
	}
}
