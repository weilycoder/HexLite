package virtuoel.pehkui.mixin.reach.compat116plus;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ItemCombinerMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(ItemCombinerMenu.class)
public class ForgingScreenHandlerMixin
{
	@ModifyConstant(method = "method_24924", constant = @Constant(doubleValue = 64.0D))
	private double pehkui$canUse$distance(double value, Player player)
	{
		final float scale = ScaleUtils.getBlockReachScale(player);
		return scale != 1.0F ? scale * scale * value : value;
	}
}
