package virtuoel.pehkui.mixin.compat116plus;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.animal.Fox;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(targets = "net.minecraft.world.entity.animal.Fox$FoxBreedGoal")
public abstract class FoxEntityMateGoalMixin extends BreedGoal
{
	private FoxEntityMateGoalMixin()
	{
		super(null, 0);
	}
	
	@Inject(method = "breed", locals = LocalCapture.CAPTURE_FAILHARD, at = @At(value = "INVOKE", shift = Shift.BEFORE, target = "Lnet/minecraft/server/level/ServerLevel;spawnEntityAndPassengers(Lnet/minecraft/world/entity/Entity;)V"))
	private void pehkui$breed(CallbackInfo info, ServerLevel serverWorld, Fox foxEntity)
	{
		ScaleUtils.loadAverageScales(foxEntity, this.animal, this.partner);
	}
}
