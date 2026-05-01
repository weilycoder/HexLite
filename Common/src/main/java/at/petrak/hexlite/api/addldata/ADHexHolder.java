package at.petrak.hexlite.api.addldata;

import at.petrak.hexlite.api.casting.iota.Iota;
import at.petrak.hexlite.api.pigment.FrozenPigment;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ADHexHolder {

    boolean canDrawMediaFromInventory();

    boolean hasHex();

    @Nullable
    List<Iota> getHex(ServerLevel level);

    void writeHex(List<Iota> patterns, @Nullable FrozenPigment pigment, long media);

    void clearHex();

    @Nullable FrozenPigment getPigment();
}
