package virtuoel.pehkui.mixin.reach;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerPlayNetworkHandlerMixin
{
	@Shadow ServerPlayer player;
	
	@ModifyConstant(method = "handleUseItemOn", constant = @Constant(doubleValue = 64.0D))
	private double pehkui$onPlayerInteractBlock$distance(double value)
	{
		final float scale = ScaleUtils.getBlockReachScale(player);
		
		return scale != 1.0F ? scale * scale * value : value;
	}
}
