package virtuoel.pehkui.mixin.compat116plus;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends EntityMixin
{
	@Unique BlockPos pehkui$initialClimbingPos = null;
	
	@Inject(method = "onClimbable()Z", at = @At(value = "RETURN"), cancellable = true)
	private void pehkui$isClimbing(CallbackInfoReturnable<Boolean> info)
	{
		final LivingEntity self = (LivingEntity) (Object) this;
		
		if (pehkui$initialClimbingPos != null || info.getReturnValueZ() || self.isSpectator())
		{
			return;
		}
		
		final float width = ScaleUtils.getBoundingBoxWidthScale(self);
		
		if (width > 1.0F)
		{
			final AABB bounds = self.getBoundingBox();
			
			final double halfUnscaledXLength = (bounds.getXsize() / width) / 2.0D;
			final int minX = Mth.floor(bounds.minX + halfUnscaledXLength);
			final int maxX = Mth.floor(bounds.maxX - halfUnscaledXLength);
			
			final int minY = Mth.floor(bounds.minY);
			
			final double halfUnscaledZLength = (bounds.getZsize() / width) / 2.0D;
			final int minZ = Mth.floor(bounds.minZ + halfUnscaledZLength);
			final int maxZ = Mth.floor(bounds.maxZ - halfUnscaledZLength);
			
			pehkui$initialClimbingPos = self.blockPosition();
			
			for (final BlockPos pos : BlockPos.betweenClosed(minX, minY, minZ, maxX, minY, maxZ))
			{
				setPosDirectly(pos);
				if (self.onClimbable())
				{
					info.setReturnValue(true);
					break;
				}
			}
			
			setPosDirectly(pehkui$initialClimbingPos);
			pehkui$initialClimbingPos = null;
		}
	}
}
