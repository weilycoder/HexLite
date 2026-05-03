package virtuoel.pehkui.mixin.reach.compat116plus;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(Item.class)
public class ItemMixin
{
	@ModifyConstant(method = "getPlayerPOVHitResult", constant = @Constant(doubleValue = 5.0D))
	private static double pehkui$raycast$multiplier(double value, Level world, Player player, ClipContext.Fluid fluidHandling)
	{
		final float scale = ScaleUtils.getBlockReachScale(player);
		
		if (scale != 1.0F)
		{
			return value * scale;
		}
		
		return value;
	}
}
