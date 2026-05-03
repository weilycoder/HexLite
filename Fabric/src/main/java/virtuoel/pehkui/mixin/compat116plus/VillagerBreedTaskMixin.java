package virtuoel.pehkui.mixin.compat116plus;

import java.util.Optional;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.VillagerMakeLove;
import net.minecraft.world.entity.npc.Villager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(VillagerMakeLove.class)
public class VillagerBreedTaskMixin
{
	@Inject(method = "breed(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/npc/Villager;Lnet/minecraft/world/entity/npc/Villager;)Ljava/util/Optional;", locals = LocalCapture.CAPTURE_FAILHARD, at = @At(value = "INVOKE", shift = Shift.BEFORE, target = "Lnet/minecraft/server/level/ServerLevel;spawnEntityAndPassengers(Lnet/minecraft/world/entity/Entity;)V"))
	private void pehkui$createChild(ServerLevel serverWorld, Villager villagerEntity, Villager villagerEntity2, CallbackInfoReturnable<Optional<Villager>> info, Villager child)
	{
		ScaleUtils.loadAverageScales(child, villagerEntity, villagerEntity2);
	}
}
