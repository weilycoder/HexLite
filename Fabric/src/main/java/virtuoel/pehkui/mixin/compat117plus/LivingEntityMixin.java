package virtuoel.pehkui.mixin.compat117plus;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin
{
	@ModifyConstant(method = "hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z", constant = @Constant(doubleValue = 0.4000000059604645D))
	private double pehkui$damage$knockback(double value, DamageSource source, float amount)
	{
		final float scale = ScaleUtils.getKnockbackScale(source.getEntity());
		
		return scale != 1.0F ? scale * value : value;
	}
	
	@ModifyConstant(method = "blockedByShield(Lnet/minecraft/world/entity/LivingEntity;)V", constant = @Constant(doubleValue = 0.5D))
	private double pehkui$knockback$knockback(double value, LivingEntity target)
	{
		final float scale = ScaleUtils.getKnockbackScale((Entity) (Object) this);
		
		return scale != 1.0F ? scale * value : value;
	}
}
