package virtuoel.pehkui.mixin.identity.compat;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import virtuoel.pehkui.util.ScaleUtils;

@Pseudo
@Mixin(targets = "draylar.identity.cca.IdentityComponent", remap = false)
public class IdentityComponentMixin
{
	@Shadow(remap = false) Player player;
	@Shadow(remap = false) LivingEntity identity;
	
	@Inject(at = @At("TAIL"), method = "setIdentity", remap = false)
	private void pehkui$setIdentity(LivingEntity identity, CallbackInfoReturnable<Boolean> info)
	{
		if (identity != null)
		{
			ScaleUtils.loadScale(identity, player);
		}
	}
}
