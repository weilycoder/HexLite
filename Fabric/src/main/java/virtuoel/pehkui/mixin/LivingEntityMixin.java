package virtuoel.pehkui.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.block.ScaffoldingBlock;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import virtuoel.pehkui.api.PehkuiConfig;
import virtuoel.pehkui.util.MulticonnectCompatibility;
import virtuoel.pehkui.util.PehkuiBlockStateExtensions;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin
{
	@ModifyArg(method = "getEyeHeight", index = 1, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getStandingEyeHeight(Lnet/minecraft/world/entity/Pose;Lnet/minecraft/world/entity/EntityDimensions;)F"))
	private EntityDimensions pehkui$getEyeHeight$dimensions(EntityDimensions dimensions)
	{
		return dimensions.scale(1.0F / ScaleUtils.getEyeHeightScale((Entity) (Object) this));
	}
	
	@ModifyConstant(method = "travel", constant = @Constant(floatValue = 1.0F, ordinal = 0))
	private float pehkui$travel$fallDistance(float value)
	{
		final float scale = ScaleUtils.getFallingScale((Entity) (Object) this);
		
		if (scale != 1.0F)
		{
			if (PehkuiConfig.COMMON.scaledFallDamage.get())
			{
				return value / scale;
			}
		}
		
		return value;
	}
	
	@Inject(method = "getEyeHeight", at = @At("RETURN"), cancellable = true)
	private void pehkui$getEyeHeight(Pose pose, EntityDimensions dimensions, CallbackInfoReturnable<Float> info)
	{
		if (pose != Pose.SLEEPING)
		{
			final float scale = ScaleUtils.getEyeHeightScale((Entity) (Object) this);
			
			if (scale != 1.0F)
			{
				info.setReturnValue(info.getReturnValueF() * scale);
			}
		}
	}
	
	@Inject(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setDeltaMovement(DDD)V", shift = Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD)
	private void pehkui$tickMovement$minVelocity(CallbackInfo info, Vec3 velocity)
	{
		final LivingEntity self = (LivingEntity) (Object) this;
		
		final float scale = ScaleUtils.getMotionScale(self);
		
		if (scale < 1.0F)
		{
			final double min = scale * MulticonnectCompatibility.INSTANCE.getProtocolDependantValue(ver -> ver <= 47, 0.005D, 0.003D);
			
			double vX = velocity.x;
			double vY = velocity.y;
			double vZ = velocity.z;
			
			if (Math.abs(vX) < min)
			{
				vX = 0.0D;
			}
			
			if (Math.abs(vY) < min)
			{
				vY = 0.0D;
			}
			
			if (Math.abs(vZ) < min)
			{
				vZ = 0.0D;
			}
			
			self.setDeltaMovement(vX, vY, vZ);
		}
	}
	
	@ModifyVariable(method = "getDamageAfterArmorAbsorb(Lnet/minecraft/world/damagesource/DamageSource;F)F", at = @At("HEAD"), argsOnly = true)
	private float pehkui$applyArmorToDamage(float value, DamageSource source, float amount)
	{
		final Entity attacker = source.getEntity();
		final float attackScale = attacker == null ? 1.0F : ScaleUtils.getAttackScale(attacker);
		final float defenseScale = ScaleUtils.getDefenseScale((Entity) (Object) this);
		
		if (attackScale != 1.0F || defenseScale != 1.0F)
		{
			value = attackScale * value / defenseScale;
		}
		
		return value;
	}
	
	@Inject(method = "getMaxHealth", at = @At("RETURN"), cancellable = true)
	private void pehkui$getMaxHealth(CallbackInfoReturnable<Float> info)
	{
		final float scale = ScaleUtils.getHealthScale((Entity) (Object) this);
		
		if (scale != 1.0F)
		{
			info.setReturnValue(info.getReturnValueF() * scale);
		}
	}
	
	@Inject(method = "getVisibilityPercent", at = @At("RETURN"), cancellable = true)
	private void pehkui$getAttackDistanceScalingFactor(@Nullable Entity entity, CallbackInfoReturnable<Double> info)
	{
		final float scale = ScaleUtils.getVisibilityScale((Entity) (Object) this);
		
		if (scale != 1.0F)
		{
			info.setReturnValue(info.getReturnValueD() * scale);
		}
	}
	
	@Inject(method = "getJumpPower", at = @At("RETURN"), cancellable = true)
	private void pehkui$getJumpVelocity(CallbackInfoReturnable<Float> info)
	{
		final float scale = ScaleUtils.getJumpHeightScale((Entity) (Object) this);
		
		if (scale != 1.0F)
		{
			info.setReturnValue(info.getReturnValueF() * scale);
		}
	}
	
	@Inject(method = "handleOnClimbable(Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;", at = @At(value = "RETURN"), cancellable = true)
	private void pehkui$applyClimbingSpeed(Vec3 motion, CallbackInfoReturnable<Vec3> info)
	{
		final LivingEntity self = (LivingEntity) (Object) this;
		
		if (!self.onClimbable())
		{
			return;
		}
		
		final float width = ScaleUtils.getBoundingBoxWidthScale(self);
		
		if (width > 1.0F)
		{
			final AABB bounds = self.getBoundingBox();
			
			final double halfUnscaledXLength = (bounds.getXsize() / width) / 2.0D;
			final int minX = Mth.floor(bounds.minX + halfUnscaledXLength);
			final int maxX = Mth.floor(bounds.maxX - halfUnscaledXLength);
			
			final int minY = Mth.floor(bounds.minY);
			
			final double halfUnscaledZLength = (bounds.getZsize() / width) / 2.0D;
			final int minZ = Mth.floor(bounds.minZ + halfUnscaledZLength);
			final int maxZ = Mth.floor(bounds.maxZ - halfUnscaledZLength);
			
			for (final BlockPos pos : BlockPos.betweenClosed(minX, minY, minZ, maxX, minY, maxZ))
			{
				if (((PehkuiBlockStateExtensions) self.getCommandSenderWorld().getBlockState(pos)).pehkui_getBlock() instanceof ScaffoldingBlock)
				{
					final Vec3 prev = info.getReturnValue();
					info.setReturnValue(new Vec3(prev.x, Math.max(self.getDeltaMovement().y, -0.15D), prev.z));
					break;
				}
			}
		}
	}
	
	@Redirect(method = "pushEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getBoundingBox()Lnet/minecraft/world/phys/AABB;"))
	private AABB pehkui$tickCramming$getBoundingBox(LivingEntity obj)
	{
		final AABB bounds = obj.getBoundingBox();
		
		final float interactionWidth = ScaleUtils.getInteractionBoxWidthScale(obj);
		final float interactionHeight = ScaleUtils.getInteractionBoxHeightScale(obj);
		
		if (interactionWidth != 1.0F || interactionHeight != 1.0F)
		{
			final double scaledXLength = bounds.getXsize() * 0.5D * (interactionWidth - 1.0F);
			final double scaledYLength = bounds.getYsize() * 0.5D * (interactionHeight - 1.0F);
			final double scaledZLength = bounds.getZsize() * 0.5D * (interactionWidth - 1.0F);
			
			return bounds.inflate(scaledXLength, scaledYLength, scaledZLength);
		}
		
		return bounds;
	}
}
