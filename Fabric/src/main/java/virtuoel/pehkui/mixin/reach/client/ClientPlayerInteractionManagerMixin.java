package virtuoel.pehkui.mixin.reach.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(MultiPlayerGameMode.class)
public class ClientPlayerInteractionManagerMixin
{
	@Shadow @Final Minecraft minecraft;
	
	@Inject(at = @At("RETURN"), method = "getPickRange", cancellable = true)
	private void pehkui$getReachDistance(CallbackInfoReturnable<Float> info)
	{
		if (minecraft.player != null)
		{
			final float scale = ScaleUtils.getBlockReachScale(minecraft.player);
			
			if (scale != 1.0F)
			{
				info.setReturnValue(info.getReturnValue() * scale);
			}
		}
	}
}
