package virtuoel.pehkui.mixin.compat117plus;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Entity.class)
public abstract class EntityCalculateDimensionsMixin
{
	@Inject(method = "refreshDimensions", locals = LocalCapture.CAPTURE_FAILHARD, at = @At(value = "INVOKE", shift = Shift.AFTER, target = "Lnet/minecraft/world/entity/Entity;reapplyPosition()V"))
	private void pehkui$calculateDimensions(CallbackInfo info, EntityDimensions previous, Pose pose, EntityDimensions current)
	{
		final Entity self = (Entity) (Object) this;
		final Level world = self.getCommandSenderWorld();
		
		if (world.isClientSide && self.getType() == EntityType.PLAYER && current.width > previous.width)
		{
			final double prevW = Math.min(previous.width, 4.0D);
			final double prevH = Math.min(previous.height, 4.0D);
			final double currW = Math.min(current.width, 4.0D);
			final double currH = Math.min(current.height, 4.0D);
			final Vec3 lastCenter = self.position().add(0.0D, prevH / 2.0D, 0.0D);
			final double w = Math.max(0.0F, currW - prevW) + 1.0E-6D;
			final double h = Math.max(0.0F, currH - prevH) + 1.0E-6D;
			final VoxelShape voxelShape = Shapes.create(AABB.ofSize(lastCenter, w, h, w));
			world.findFreePosition(self, voxelShape, lastCenter, currW, currH, currW)
				.ifPresent(vec -> self.setPos(vec.add(0.0D, -currH / 2.0D, 0.0D)));
		}
	}
}
