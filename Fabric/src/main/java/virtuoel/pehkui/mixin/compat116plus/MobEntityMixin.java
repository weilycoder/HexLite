package virtuoel.pehkui.mixin.compat116plus;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(Mob.class)
public class MobEntityMixin
{
	@Inject(at = @At("RETURN"), method = "convertTo")
	private <T extends Mob> void pehkui$convertTo(EntityType<T> entityType, boolean bl, CallbackInfoReturnable<T> info)
	{
		final Mob e = info.getReturnValue();
		
		if (e != null)
		{
			ScaleUtils.loadScale(e, (Entity) (Object) this);
		}
	}
}
