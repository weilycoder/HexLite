package at.petrak.hexlite.common.lib;

import at.petrak.hexlite.api.casting.ActionRegistryEntry;
import at.petrak.hexlite.api.casting.arithmetic.Arithmetic;
import at.petrak.hexlite.api.casting.castables.SpecialHandler;
import at.petrak.hexlite.api.casting.eval.sideeffects.EvalSound;
import at.petrak.hexlite.api.casting.eval.vm.ContinuationFrame;
import at.petrak.hexlite.api.casting.iota.IotaType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import static at.petrak.hexlite.api.HexAPI.modLoc;

public class HexRegistries {
    public static final ResourceKey<Registry<ActionRegistryEntry>> ACTION = ResourceKey.createRegistryKey(modLoc("action"));
    public static final ResourceKey<Registry<SpecialHandler.Factory<?>>> SPECIAL_HANDLER = ResourceKey.createRegistryKey(modLoc("special_handler"));
    public static final ResourceKey<Registry<IotaType<?>>> IOTA_TYPE = ResourceKey.createRegistryKey(modLoc("iota_type"));
    public static final ResourceKey<Registry<Arithmetic>> ARITHMETIC = ResourceKey.createRegistryKey(modLoc("arithmetic"));
    public static final ResourceKey<Registry<ContinuationFrame.Type<?>>> CONTINUATION_TYPE = ResourceKey.createRegistryKey(modLoc("continuation_type"));
    public static final ResourceKey<Registry<EvalSound>> EVAL_SOUND = ResourceKey.createRegistryKey(modLoc("eval_sound"));
}
