package virtuoel.pehkui.mixin.client.compat120plus;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin
{
	@Redirect(method = "renderNameTag(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/network/chat/Component;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getNameTagOffsetY()F"))
	private float pehkui$renderLabelIfPresent$getNameLabelHeight(Entity entity)
	{
		final float delta = Minecraft.getInstance().getFrameTime();
		return (entity.getNameTagOffsetY() - entity.getBbHeight()) + (entity.getBbHeight() / ScaleUtils.getBoundingBoxHeightScale(entity, delta));
	}
}
