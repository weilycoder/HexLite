package virtuoel.pehkui.mixin.compat117plus;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(Player.class)
public abstract class PlayerEntityMixin
{
	@ModifyConstant(method = "attack(Lnet/minecraft/world/entity/Entity;)V", constant = @Constant(doubleValue = 0.4000000059604645D))
	private double pehkui$attack$knockback(double value)
	{
		final float scale = ScaleUtils.getKnockbackScale((Entity) (Object) this);
		
		return scale != 1.0F ? scale * value : value;
	}
}
