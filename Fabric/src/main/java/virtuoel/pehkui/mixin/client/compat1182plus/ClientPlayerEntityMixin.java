package virtuoel.pehkui.mixin.client.compat1182plus;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(LocalPlayer.class)
public class ClientPlayerEntityMixin
{
	@ModifyConstant(method = "sendPosition", constant = @Constant(doubleValue = 2.0E-4D))
	private double pehkui$sendMovementPackets$minVelocity(double value)
	{
		final float scale = ScaleUtils.getMotionScale((Entity) (Object) this);
		
		return scale < 1.0F ? scale * value : value;
	}
}
