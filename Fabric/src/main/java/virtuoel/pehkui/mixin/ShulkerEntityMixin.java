package virtuoel.pehkui.mixin;

import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.monster.Shulker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(Shulker.class)
public class ShulkerEntityMixin
{
	@Inject(method = "getStandingEyeHeight", at = @At("RETURN"), cancellable = true)
	private void pehkui$getActiveEyeHeight(Pose pose, EntityDimensions dimensions, CallbackInfoReturnable<Float> info)
	{
		final Shulker entity = (Shulker) (Object) this;
		
		final Direction face = entity.getAttachFace();
		if (face != Direction.DOWN)
		{
			final float scale = ScaleUtils.getEyeHeightScale(entity);
			if (scale != 1.0F)
			{
				if (face == Direction.UP)
				{
					info.setReturnValue(ScaleUtils.divideClamped(1.0F, scale) - info.getReturnValueF());
				}
				else
				{
					info.setReturnValue(ScaleUtils.divideClamped(1.0F - info.getReturnValueF(), scale));
				}
			}
		}
	}
}
