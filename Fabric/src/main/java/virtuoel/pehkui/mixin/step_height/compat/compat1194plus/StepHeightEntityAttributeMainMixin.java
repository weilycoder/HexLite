package virtuoel.pehkui.mixin.step_height.compat.compat1194plus;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import virtuoel.pehkui.util.ScaleUtils;

@Pseudo
@Mixin(targets = "dev.emi.stepheightentityattribute.StepHeightEntityAttributeMain", remap = false)
public class StepHeightEntityAttributeMainMixin
{
	@Inject(method = "getStepHeight", at = @At(value = "RETURN"), cancellable = true, remap = false)
	private static void pehkui$getStepHeight(float baseStepHeight, LivingEntity entity, CallbackInfoReturnable<Float> info)
	{
		final float scale = ScaleUtils.getStepHeightScale(entity);
		
		if (scale != 1.0F)
		{
			info.setReturnValue(scale * info.getReturnValueF());
		}
	}
}
