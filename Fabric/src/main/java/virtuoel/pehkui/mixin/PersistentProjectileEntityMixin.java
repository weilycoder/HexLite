package virtuoel.pehkui.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(AbstractArrow.class)
public abstract class PersistentProjectileEntityMixin
{
	@Inject(at = @At("RETURN"), method = "<init>(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;)V")
	private void pehkui$construct(EntityType<? extends Projectile> type, LivingEntity owner, Level world, CallbackInfo info)
	{
		final float scale = ScaleUtils.getEyeHeightScale(owner);
		
		if (scale != 1.0F)
		{
			final Entity self = ((Entity) (Object) this);
			
			final Vec3 pos = self.position();
			
			self.setPos(pos.x, pos.y + ((1.0F - scale) * 0.1D), pos.z);
		}
	}
	
	@Inject(at = @At("HEAD"), method = "setOwner")
	private void pehkui$setOwner(@Nullable Entity entity, CallbackInfo info)
	{
		if (entity != null)
		{
			ScaleUtils.setScaleOfProjectile((Entity) (Object) this, entity);
		}
	}
	
	@ModifyArg(method = "tick", index = 1, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/AbstractArrow;setDeltaMovement(DDD)V"))
	private double pehkui$tick$gravity(double value)
	{
		final Vec3 velocity = ((Entity) (Object) this).getDeltaMovement();
		final float scale = ScaleUtils.getMotionScale((Entity) (Object) this);
		
		return scale != 1.0F ? velocity.y + (scale * (value - velocity.y)) : value;
	}
}
