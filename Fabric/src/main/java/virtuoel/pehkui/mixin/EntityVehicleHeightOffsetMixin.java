package virtuoel.pehkui.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.monster.PatrollingMonster;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin({
	ArmorStand.class,
	AbstractSkeleton.class,
	Endermite.class,
	PatrollingMonster.class,
	Silverfish.class,
	Zombie.class,
	Animal.class,
	Player.class
})
public abstract class EntityVehicleHeightOffsetMixin
{
	@Inject(at = @At("RETURN"), method = "getMyRidingOffset", cancellable = true)
	private void pehkui$getHeightOffset(CallbackInfoReturnable<Double> info)
	{
		final Entity self = (Entity) (Object) this;
		final Entity vehicle = self.getVehicle();
		
		if (vehicle != null)
		{
			final float scale = ScaleUtils.getBoundingBoxHeightScale(self);
			final float vehicleScale = ScaleUtils.getBoundingBoxHeightScale(vehicle);
			
			if (scale != 1.0F || vehicleScale != 1.0F)
			{
				final double vehicleAdjustedHeight = vehicle.getBbHeight() * 0.75D;
				final double offset = info.getReturnValueD();
				
				final double adjusted = vehicleAdjustedHeight - (((vehicleAdjustedHeight / vehicleScale) - offset) * scale);
				info.setReturnValue(adjusted);
			}
		}
	}
}
