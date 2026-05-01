package at.petrak.hexlite.interop.pehkui;

import at.petrak.hexlite.interop.HexInterop;
import at.petrak.hexlite.xplat.IXplatAbstractions;
import net.minecraft.world.entity.Entity;

public class PehkuiInterop {
    public static void init() {
        // for future work
    }

    public static boolean isActive() {
        return IXplatAbstractions.INSTANCE.isModPresent(HexInterop.PEHKUI_ID);
    }

    /**
     * Pehkui doesn't publish an API jar so we do this BS
     */
    public interface ApiAbstraction {
        float getScale(Entity e);

        void setScale(Entity e, float scale);
    }
}
