package virtuoel.pehkui.mixin.client;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(Camera.class)
public abstract class CameraMixin
{
	@Shadow Entity entity;
	
	@ModifyVariable(method = "getMaxZoom", at = @At(value = "HEAD"), argsOnly = true)
	private double pehkui$clipToSpace(double desiredCameraDistance)
	{
		return desiredCameraDistance * ScaleUtils.getThirdPersonScale(entity, Minecraft.getInstance().getFrameTime());
	}
	
	@ModifyConstant(method = "getMaxZoom", constant = @Constant(floatValue = 0.1F))
	private float pehkui$clipToSpace$offset(float value)
	{
		final float scale = ScaleUtils.getBoundingBoxWidthScale(entity);
		
		return scale < 1.0F ? scale * value : value;
	}
}
