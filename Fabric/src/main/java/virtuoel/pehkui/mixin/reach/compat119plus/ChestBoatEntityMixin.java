package virtuoel.pehkui.mixin.reach.compat119plus;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.entity.vehicle.ContainerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(ChestBoat.class)
public abstract class ChestBoatEntityMixin implements ContainerEntity
{
	@Inject(method = "stillValid", at = @At("RETURN"), cancellable = true)
	private void pehkui$canPlayerUse(Player playerEntity, CallbackInfoReturnable<Boolean> info)
	{
		if (!info.getReturnValueZ())
		{
			final float scale = ScaleUtils.getEntityReachScale(playerEntity);
			
			if (scale > 1.0F && !this.isRemoved() && this.position().closerThan(playerEntity.position(), 8.0 * scale))
			{
				info.setReturnValue(true);
			}
		}
	}
}
