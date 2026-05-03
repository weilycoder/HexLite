package virtuoel.pehkui.mixin.compat117plus;

import net.minecraft.core.Direction;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(Shulker.class)
public class ShulkerEntityMixin
{
	@Inject(method = "makeBoundingBox", at = @At("RETURN"), cancellable = true)
	private void pehkui$calculateBoundingBox(CallbackInfoReturnable<AABB> info)
	{
		final Shulker entity = (Shulker) (Object) this;
		
		final Direction facing = entity.getAttachFace().getOpposite();
		
		AABB box = info.getReturnValue();
		
		final double xLength = box.getXsize() / -2.0D;
		final double yLength = box.getYsize() / -2.0D;
		final double zLength = box.getZsize() / -2.0D;
		
		final float widthScale = ScaleUtils.getBoundingBoxWidthScale(entity);
		final float heightScale = ScaleUtils.getBoundingBoxHeightScale(entity);
		
		if (widthScale != 1.0F || heightScale != 1.0F)
		{
			final double dX = xLength * (1.0D - widthScale); 
			final double dY = yLength * (1.0D - heightScale); 
			final double dZ = zLength * (1.0D - widthScale); 
			box = box.inflate(dX, dY, dZ);
			box = box.move(dX * facing.getStepX(), dY * facing.getStepY(), dZ * facing.getStepZ());
			
			info.setReturnValue(box);
		}
	}
}
