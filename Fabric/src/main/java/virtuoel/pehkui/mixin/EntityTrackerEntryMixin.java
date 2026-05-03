package virtuoel.pehkui.mixin;

import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(ServerEntity.class)
public abstract class EntityTrackerEntryMixin
{
	@Shadow @Final Entity entity;
	@Shadow abstract void broadcastAndSend(Packet<?> packet);
	
	@Inject(at = @At("TAIL"), method = "sendChanges")
	private void pehkui$tick(CallbackInfo info)
	{
		ScaleUtils.syncScalesIfNeeded(entity, p -> this.broadcastAndSend(p));
	}
	
	@ModifyConstant(method = "sendChanges", constant = @Constant(doubleValue = 7.62939453125E-6D))
	private double pehkui$tick$minimumSquaredDistance(double value)
	{
		final double scale = ScaleUtils.getMotionScale(entity);
		
		return scale < 1.0D ? value * scale * scale : value;
	}
	
	@Inject(at = @At("HEAD"), method = "sendDirtyEntityData")
	private void pehkui$syncEntityData(CallbackInfo info)
	{
		ScaleUtils.syncScalesIfNeeded(entity, p -> this.broadcastAndSend(p));
	}
}
