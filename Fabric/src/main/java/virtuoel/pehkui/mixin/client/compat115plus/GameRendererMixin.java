package virtuoel.pehkui.mixin.client.compat115plus;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(GameRenderer.class)
public class GameRendererMixin
{
	@Shadow @Final @Mutable
	Minecraft minecraft;
	
	@Redirect(method = "bobView", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/player/Player;walkDist:F"))
	private float pehkui$bobView$horizontalSpeed(Player obj)
	{
		final float scale = ScaleUtils.getViewBobbingScale(obj, 1.0F);
		return scale != 1.0F ? ScaleUtils.divideClamped(obj.walkDist, scale) : obj.walkDist;
	}
	
	@Redirect(method = "bobView", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/player/Player;walkDistO:F"))
	private float pehkui$bobView$prevHorizontalSpeed(Player obj)
	{
		final float scale = ScaleUtils.getViewBobbingScale(obj, 0.0F);
		return scale != 1.0F ? ScaleUtils.divideClamped(obj.walkDistO, scale) : obj.walkDistO;
	}
}
