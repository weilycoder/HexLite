package virtuoel.pehkui.mixin.client.compat115plus.optifine.compat;

import net.minecraft.client.renderer.ScreenEffectRenderer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(ScreenEffectRenderer.class)
public abstract class InGameOverlayRendererMixin
{
	@ModifyConstant(method = "getOverlayBlock", remap = false, constant = @Constant(floatValue = 0.1F))
	private static float pehkui$getInWallBlockState$offset(float value, Player player)
	{
		final float scale = ScaleUtils.getEyeHeightScale(player);
		
		return scale != 1.0F ? value * scale : value;
	}
}
