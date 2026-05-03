package virtuoel.pehkui.mixin.compat116plus;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(TargetingConditions.class)
public class TargetPredicateMixin
{
	@Shadow boolean testInvisible;
	
	@ModifyConstant(method = "test", constant = @Constant(doubleValue = 2.0D))
	private double pehkui$test$minDistance(double value, @Nullable LivingEntity baseEntity, LivingEntity targetEntity)
	{
		if (testInvisible)
		{
			final float scale = ScaleUtils.getVisibilityScale(targetEntity);
			
			return scale != 1.0F ? value * scale : value;
		}
		
		return value;
	}
}
