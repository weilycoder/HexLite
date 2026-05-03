package virtuoel.pehkui.mixin.compat116plus;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(ItemCombinerMenu.class)
public class ForgingScreenHandlerMixin
{
	@ModifyConstant(method = "method_24924", constant = @Constant(doubleValue = 0.5D, ordinal = 0))
	private double pehkui$canUse$xOffset(double value, Player player, Level world, BlockPos pos)
	{
		return ScaleUtils.getBlockXOffset(pos, player);
	}
	
	@ModifyConstant(method = "method_24924", constant = @Constant(doubleValue = 0.5D, ordinal = 1))
	private double pehkui$canUse$yOffset(double value, Player player, Level world, BlockPos pos)
	{
		return ScaleUtils.getBlockYOffset(pos, player);
	}
	
	@ModifyConstant(method = "method_24924", constant = @Constant(doubleValue = 0.5D, ordinal = 2))
	private double pehkui$canUse$zOffset(double value, Player player, Level world, BlockPos pos)
	{
		return ScaleUtils.getBlockZOffset(pos, player);
	}
}
