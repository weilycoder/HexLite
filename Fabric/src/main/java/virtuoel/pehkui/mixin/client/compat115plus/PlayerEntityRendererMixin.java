package virtuoel.pehkui.mixin.client.compat115plus;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(PlayerRenderer.class)
public abstract class PlayerEntityRendererMixin
{
	@Inject(at = @At("RETURN"), method = "getRenderOffset", cancellable = true)
	private void pehkui$getPositionOffset(AbstractClientPlayer entity, float tickDelta, CallbackInfoReturnable<Vec3> info)
	{
		final Vec3 ret = info.getReturnValue();
		if (ret != Vec3.ZERO)
		{
			info.setReturnValue(ret.scale(ScaleUtils.getModelHeightScale(entity, tickDelta)));
		}
	}
}
