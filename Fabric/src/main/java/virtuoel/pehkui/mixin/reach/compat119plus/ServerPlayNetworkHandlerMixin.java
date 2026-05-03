package virtuoel.pehkui.mixin.reach.compat119plus;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerPlayNetworkHandlerMixin
{
	@Shadow ServerPlayer player;
	
	@Redirect(method = "handleUseItemOn", at = @At(value = "FIELD", opcode = Opcodes.GETSTATIC, target = "Lnet/minecraft/server/network/ServerGamePacketListenerImpl;MAX_INTERACTION_DISTANCE:D"))
	private double pehkui$onPlayerInteractBlock$distance()
	{
		final float scale = ScaleUtils.getBlockReachScale(player);
		return scale <= 1.0F ? ServerGamePacketListenerImpl.MAX_INTERACTION_DISTANCE : ServerGamePacketListenerImpl.MAX_INTERACTION_DISTANCE * scale * scale;
	}
	
	@Redirect(method = "handleInteract", at = @At(value = "FIELD", opcode = Opcodes.GETSTATIC, target = "Lnet/minecraft/server/network/ServerGamePacketListenerImpl;MAX_INTERACTION_DISTANCE:D"))
	private double pehkui$onPlayerInteractEntity$distance()
	{
		final float scale = ScaleUtils.getEntityReachScale(player);
		return scale <= 1.0F ? ServerGamePacketListenerImpl.MAX_INTERACTION_DISTANCE : ServerGamePacketListenerImpl.MAX_INTERACTION_DISTANCE * scale * scale;
	}
}
