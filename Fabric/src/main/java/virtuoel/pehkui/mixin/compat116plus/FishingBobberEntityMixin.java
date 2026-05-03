package virtuoel.pehkui.mixin.compat116plus;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(FishingHook.class)
public abstract class FishingBobberEntityMixin
{
	@Shadow
	abstract Player getPlayerOwner();
	
	@ModifyConstant(method = "shouldStopFishing", constant = @Constant(doubleValue = 1024.0D))
	private double pehkui$removeIfInvalid$distance(double value)
	{
		final float scale = ScaleUtils.getProjectileScale(getPlayerOwner());
		
		if (scale != 1.0F)
		{
			return value * scale * scale;
		}
		
		return value;
	}
}
