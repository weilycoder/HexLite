package virtuoel.pehkui.mixin.compat119plus;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(BehaviorUtils.class)
public class LookTargetUtilMixin
{
	@ModifyVariable(method = "throwItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;F)V", at = @At("HEAD"))
	private static float pehkui$give$offset(float value, LivingEntity entity, ItemStack stack, Vec3 targetLocation, Vec3 velocityFactor, float yOffset)
	{
		final float scale = ScaleUtils.getEyeHeightScale(entity);
		
		return scale != 1.0F ? scale * value : value;
	}
	
	@Inject(method = "throwItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;F)V", locals = LocalCapture.CAPTURE_FAILHARD, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/item/ItemEntity;setDefaultPickUpDelay()V"))
	private static void pehkui$give(LivingEntity entity, ItemStack stack, Vec3 targetLocation, Vec3 velocityFactor, float yOffset, CallbackInfo info, double d, ItemEntity itemEntity, Vec3 vec3d)
	{
		ScaleUtils.setScaleOfDrop(itemEntity, entity);
	}
}
