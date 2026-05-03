package virtuoel.pehkui.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(TargetGoal.class)
public class TrackTargetGoalMixin
{
	@Shadow LivingEntity targetMob;
	@Shadow @Final @Mutable Mob mob;
	
	@Inject(method = "getFollowDistance", at = @At("RETURN"), cancellable = true)
	private void pehkui$getFollowRange(CallbackInfoReturnable<Double> info)
	{
		LivingEntity target = this.mob.getTarget();
		if (target == null && (target = this.targetMob) == null)
		{
			return;
		}
		
		final float scale = ScaleUtils.getVisibilityScale(target);
		
		if (scale != 1.0F)
		{
			info.setReturnValue(info.getReturnValueD() * scale);
		}
	}
}
