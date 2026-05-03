package virtuoel.pehkui.mixin.client.compat1193plus;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import virtuoel.pehkui.util.ScaleRenderUtils;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(GameRenderer.class)
public class GameRendererMixin
{
	@Shadow @Final @Mutable
	Minecraft minecraft;
	
	@ModifyConstant(method = "getProjectionMatrix(D)Lorg/joml/Matrix4f;", constant = @Constant(floatValue = 0.05F))
	private float pehkui$getBasicProjectionMatrix$depth(float value)
	{
		return ScaleRenderUtils.modifyProjectionMatrixDepth(value, minecraft.getCameraEntity(), minecraft.getFrameTime());
	}
	
	@ModifyArg(method = "bobView", index = 0, at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V"))
	private float pehkui$bobView$translate$x(float value)
	{
		final float scale = ScaleUtils.getViewBobbingScale(minecraft.getCameraEntity(), minecraft.getFrameTime());
		return scale != 1.0F ? value * scale : value;
	}
	
	@ModifyArg(method = "bobView", index = 1, at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V"))
	private float pehkui$bobView$translate$y(float value)
	{
		final float scale = ScaleUtils.getViewBobbingScale(minecraft.getCameraEntity(), minecraft.getFrameTime());
		return scale != 1.0F ? value * scale : value;
	}
}
