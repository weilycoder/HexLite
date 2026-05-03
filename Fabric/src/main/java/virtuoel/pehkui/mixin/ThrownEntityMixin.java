package virtuoel.pehkui.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(ThrowableProjectile.class)
public abstract class ThrownEntityMixin
{
	@Shadow
	protected abstract float getGravity();
	
	@Inject(at = @At("RETURN"), method = "<init>(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;)V")
	private void pehkui$construct(EntityType<? extends ThrowableProjectile> type, LivingEntity owner, Level world, CallbackInfo info)
	{
		final float heightScale = ScaleUtils.getEyeHeightScale(owner);
		if (heightScale != 1.0F)
		{
			final Entity self = ((Entity) (Object) this);
			
			final Vec3 pos = self.position();
			
			self.setPos(pos.x, pos.y + ((1.0F - heightScale) * 0.1D), pos.z);
		}
		
		ScaleUtils.setScaleOfProjectile((Entity) (Object) this, owner);
	}
	
	@ModifyArg(method = "tick", index = 1, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/ThrowableProjectile;setDeltaMovement(DDD)V"))
	private double pehkui$tick$setVelocity(double value)
	{
		final float scale = ScaleUtils.getMotionScale((Entity) (Object) this);
		
		if (scale != 1.0F)
		{
			return value + ((1.0F - scale) * getGravity());
		}
		
		return value;
	}
}
