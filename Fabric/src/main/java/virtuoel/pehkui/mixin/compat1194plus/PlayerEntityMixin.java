package virtuoel.pehkui.mixin.compat1194plus;

import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(Player.class)
public abstract class PlayerEntityMixin
{
	@Inject(method = "getFlyingSpeed", at = @At(value = "RETURN", ordinal = 0), cancellable = true)
	private void pehkui$getOffGroundSpeed(CallbackInfoReturnable<Float> info)
	{
		final float scale = ScaleUtils.getFlightScale((Player) (Object) this);
		
		if (scale != 1.0F)
		{
			info.setReturnValue(info.getReturnValueF() * scale);
		}
	}
}
