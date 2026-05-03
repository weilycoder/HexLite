package virtuoel.pehkui.mixin.magna.compat;

import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import virtuoel.pehkui.util.ScaleUtils;

@Pseudo
@Mixin(targets = "draylar.magna.api.reach.ReachDistanceHelper", remap = false)
public class ReachDistanceHelperMixin
{
	@Inject(at = @At(value = "RETURN", ordinal = 1), method = "getReachDistance", cancellable = true, remap = false)
	private static void harmful_heights$getReachDistance(Player player, CallbackInfoReturnable<Double> info)
	{
		final float scale = ScaleUtils.getBlockReachScale(player);
		
		if (scale > 1.0F)
		{
			info.setReturnValue(info.getReturnValue().doubleValue() * scale);
		}
	}
}
