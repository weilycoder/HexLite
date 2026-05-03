package virtuoel.pehkui.mixin.reach.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(GameRenderer.class)
public class GameRendererMixin
{
	@Shadow @Final @Mutable
	Minecraft minecraft;
	
	@ModifyVariable(method = "pick", ordinal = 0, at = @At(value = "INVOKE", shift = Shift.BEFORE, target = "Lnet/minecraft/world/entity/Entity;getEyePosition(F)Lnet/minecraft/world/phys/Vec3;"))
	private double pehkui$updateTargetedEntity$setDistance(double value, float tickDelta)
	{
		final Entity entity = minecraft.getCameraEntity();
		
		if (entity != null)
		{
			final float scale = ScaleUtils.getEntityReachScale(entity, tickDelta);
			
			if (scale != 1.0F)
			{
				return scale * value;
			}
		}
		
		return value;
	}
	
	@ModifyVariable(method = "pick", ordinal = 1, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getViewVector(F)Lnet/minecraft/world/phys/Vec3;"))
	private double pehkui$updateTargetedEntity$squaredDistance(double value, float tickDelta)
	{
		final Entity entity = minecraft.getCameraEntity();
		
		if (entity != null)
		{
			if (this.minecraft.hitResult == null || this.minecraft.hitResult.getType() == HitResult.Type.MISS)
			{
				final float scale = ScaleUtils.getEntityReachScale(entity, tickDelta);
				final double baseEntityReach = minecraft.gameMode.hasFarPickRange() ? 6.0D : minecraft.gameMode.getPlayerMode().isCreative() ? 5.0F : 4.5F;
				final double entityReach = scale * baseEntityReach;
				
				return entityReach * entityReach;
			}
		}
		
		return value;
	}
	
	@ModifyConstant(method = "pick", constant = @Constant(doubleValue = 3.0D))
	private double pehkui$updateTargetedEntity$distance(double value, float tickDelta)
	{
		final Entity entity = minecraft.getCameraEntity();
		
		if (entity != null)
		{
			final float scale = ScaleUtils.getEntityReachScale(entity, tickDelta);
			
			if (scale != 1.0F)
			{
				return scale * value;
			}
		}
		
		return value;
	}
	
	@ModifyConstant(method = "pick", constant = @Constant(doubleValue = 6.0D))
	private double pehkui$updateTargetedEntity$extendedDistance(double value, float tickDelta)
	{
		final Entity entity = minecraft.getCameraEntity();
		
		if (entity != null)
		{
			final float scale = ScaleUtils.getEntityReachScale(entity, tickDelta);
			
			if (scale != 1.0F)
			{
				return scale * value;
			}
		}
		
		return value;
	}
	
	@ModifyConstant(method = "pick", constant = @Constant(doubleValue = 9.0D))
	private double pehkui$updateTargetedEntity$squaredMaxDistance(double value, float tickDelta)
	{
		final Entity entity = minecraft.getCameraEntity();
		
		if (entity != null)
		{
			final float scale = ScaleUtils.getEntityReachScale(entity, tickDelta);
			
			if (scale != 1.0F)
			{
				return scale * scale * value;
			}
		}
		
		return value;
	}
}
