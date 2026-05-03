package virtuoel.pehkui.mixin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.HorseInventoryMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(HorseInventoryMenu.class)
public class HorseScreenHandlerMixin
{
	@Unique private static final ThreadLocal<Float> pehkui$REACH_SCALE = ThreadLocal.withInitial(() -> 1.0F);
	
	@Inject(method = "stillValid", at = @At("HEAD"))
	private void pehkui$canUse(Player player, CallbackInfoReturnable<Boolean> info)
	{
		pehkui$REACH_SCALE.set(ScaleUtils.getEntityReachScale(player));
	}
	
	@ModifyConstant(method = "stillValid", constant = @Constant(floatValue = 8.0F))
	private float pehkui$canUse$distance(float value)
	{
		final float scale = pehkui$REACH_SCALE.get();
		
		return scale != 1.0F ? scale * value : value;
	}
}
