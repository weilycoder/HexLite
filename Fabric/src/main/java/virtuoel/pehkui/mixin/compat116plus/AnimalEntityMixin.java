package virtuoel.pehkui.mixin.compat116plus;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.animal.Animal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(Animal.class)
public class AnimalEntityMixin
{
	@Inject(method = "spawnChildFromBreeding(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/animal/Animal;)V", locals = LocalCapture.CAPTURE_FAILHARD, at = @At(value = "INVOKE", shift = Shift.BEFORE, target = "Lnet/minecraft/server/level/ServerLevel;spawnEntityAndPassengers(Lnet/minecraft/world/entity/Entity;)V"))
	private void pehkui$breed(ServerLevel serverWorld, Animal other, CallbackInfo info, AgeableMob passiveEntity)
	{
		ScaleUtils.loadAverageScales(passiveEntity, (Animal) (Object) this, other);
	}
}
