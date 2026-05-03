package virtuoel.pehkui.mixin.compat117plus;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(Axolotl.class)
public class AxolotlEntityMixin
{
	@ModifyConstant(method = "getMeleeAttackRangeSqr", constant = @Constant(doubleValue = 1.5D))
	private double pehkui$squaredAttackRange$range(double value)
	{
		final float scale = ScaleUtils.getBoundingBoxWidthScale((Entity) (Object) this);
		
		if (scale != 1.0F)
		{
			return scale * scale * value;
		}
		
		return value;
	}
}
