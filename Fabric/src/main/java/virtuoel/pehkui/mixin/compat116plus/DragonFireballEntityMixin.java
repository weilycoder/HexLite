package virtuoel.pehkui.mixin.compat116plus;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.DragonFireball;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(DragonFireball.class)
public abstract class DragonFireballEntityMixin
{
	@ModifyConstant(method = "onHit(Lnet/minecraft/world/phys/HitResult;)V", constant = @Constant(doubleValue = 4.0D))
	private double pehkui$onCollision$width(double value)
	{
		final float scale = ScaleUtils.getBoundingBoxWidthScale((Entity) (Object) this);
		
		return scale != 1.0F ? scale * value : value;
	}
	
	@ModifyConstant(method = "onHit(Lnet/minecraft/world/phys/HitResult;)V", constant = @Constant(doubleValue = 2.0D))
	private double pehkui$onCollision$height(double value)
	{
		final float scale = ScaleUtils.getBoundingBoxHeightScale((Entity) (Object) this);
		
		return scale != 1.0F ? scale * value : value;
	}
}
