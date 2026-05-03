package virtuoel.pehkui.mixin.reach;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(AbstractContainerMenu.class)
public class ScreenHandlerMixin
{
	@ModifyConstant(method = "method_17696", constant = @Constant(doubleValue = 64.0D))
	private static double pehkui$canUse$distance(double value, Block block, Player player)
	{
		final float scale = ScaleUtils.getBlockReachScale(player);
		return scale > 1.0F ? scale * scale * value : value;
	}
}
