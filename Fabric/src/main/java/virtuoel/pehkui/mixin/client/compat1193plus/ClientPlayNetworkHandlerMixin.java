package virtuoel.pehkui.mixin.client.compat1193plus;

import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(ClientPacketListener.class)
public class ClientPlayNetworkHandlerMixin
{
	@Inject(method = "handleRespawn(Lnet/minecraft/network/protocol/game/ClientboundRespawnPacket;)V", locals = LocalCapture.CAPTURE_FAILHARD, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;resetPos()V"))
	private void pehkui$onPlayerRespawn(ClientboundRespawnPacket packet, CallbackInfo info, ResourceKey<Level> dimension, Holder<DimensionType> dimensionType, LocalPlayer oldPlayer, int id, String brand, LocalPlayer newPlayer)
	{
		ScaleUtils.loadScaleOnRespawn(newPlayer, oldPlayer, packet.shouldKeep((byte) 1));
	}
}
