package virtuoel.pehkui.mixin.client.compat115plus;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(MobRenderer.class)
public class MobEntityRendererMixin<T extends Mob>
{
	@Inject(method = "renderLeash", at = @At(value = "HEAD"))
	private <E extends Entity> void pehkui$renderLeash$head(T entity, float tickDelta, PoseStack matrices, MultiBufferSource provider, E holdingEntity, CallbackInfo info)
	{
		final Entity attached = entity.getLeashHolder();
		
		if (attached != null)
		{
			final float inverseWidthScale = 1.0F / ScaleUtils.getModelWidthScale(entity, tickDelta);
			final float inverseHeightScale = 1.0F / ScaleUtils.getModelHeightScale(entity, tickDelta);
			
			matrices.pushPose();
			matrices.scale(inverseWidthScale, inverseHeightScale, inverseWidthScale);
			matrices.pushPose();
		}
	}
	
	@Inject(method = "renderLeash", at = @At(value = "RETURN"))
	private <E extends Entity> void pehkui$renderLeash$return(T entity, float tickDelta, PoseStack matrices, MultiBufferSource provider, E holdingEntity, CallbackInfo info)
	{
		if (entity.getLeashHolder() != null)
		{
			matrices.popPose();
			matrices.popPose();
		}
	}
}
