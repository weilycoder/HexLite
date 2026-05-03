package virtuoel.pehkui.mixin;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(CrossbowItem.class)
public class CrossbowItemMixin
{
	@ModifyConstant(method = "shootProjectile", constant = @Constant(doubleValue = 0.15000000596046448D))
	private static double pehkui$shoot$yOffset(double value, Level world, LivingEntity shooter, InteractionHand hand, ItemStack crossbow, ItemStack projectile, float soundPitch, boolean creative, float speed, float divergence, float simulated)
	{
		final float scale = ScaleUtils.getEyeHeightScale(shooter);
		
		return scale != 1.0F ? value * scale : value;
	}
}
