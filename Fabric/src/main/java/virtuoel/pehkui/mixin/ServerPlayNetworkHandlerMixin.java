package virtuoel.pehkui.mixin;

import net.minecraft.network.protocol.game.ServerboundUseItemOnPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerPlayNetworkHandlerMixin
{
	@Shadow ServerPlayer player;
	
	@ModifyArg(method = "handleMoveVehicle", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/AABB;deflate(D)Lnet/minecraft/world/phys/AABB;"))
	private double pehkui$onVehicleMove$contract(double value)
	{
		final float scale = ScaleUtils.getMotionScale(player);
		return scale < 1.0F ? value * scale : value;
	}
	
	@ModifyArg(method = "handleMoveVehicle", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V"))
	private Vec3 pehkui$onVehicleMove$move(MoverType type, Vec3 movement)
	{
		final float scale = ScaleUtils.getMotionScale(player.getRootVehicle());
		return scale != 1.0F ? movement.scale(1.0F / scale) : movement;
	}
	
	@ModifyArg(method = "handleMovePlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V"))
	private Vec3 pehkui$onPlayerMove$move(MoverType type, Vec3 movement)
	{
		final float scale = ScaleUtils.getMotionScale(player);
		return scale != 1.0F ? movement.scale(1.0F / scale) : movement;
	}
	
	@ModifyConstant(method = "handleUseItemOn", constant = @Constant(doubleValue = 0.5D, ordinal = 0))
	private double pehkui$onPlayerInteractBlock$xOffset(double value, ServerboundUseItemOnPacket packet)
	{
		return ScaleUtils.getBlockXOffset(packet.getHitResult().getBlockPos(), player);
	}
	
	@ModifyConstant(method = "handleUseItemOn", constant = @Constant(doubleValue = 0.5D, ordinal = 1))
	private double pehkui$onPlayerInteractBlock$yOffset(double value, ServerboundUseItemOnPacket packet)
	{
		return ScaleUtils.getBlockYOffset(packet.getHitResult().getBlockPos(), player);
	}
	
	@ModifyConstant(method = "handleUseItemOn", constant = @Constant(doubleValue = 0.5D, ordinal = 2))
	private double pehkui$onPlayerInteractBlock$zOffset(double value, ServerboundUseItemOnPacket packet)
	{
		return ScaleUtils.getBlockZOffset(packet.getHitResult().getBlockPos(), player);
	}
}
