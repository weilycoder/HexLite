package at.petrak.hexlite.interop.utils;

import at.petrak.hexlite.api.casting.math.HexCoord;
import at.petrak.hexlite.api.casting.math.HexPattern;
import net.minecraft.world.phys.Vec2;

import java.util.List;

public record PatternEntry(HexPattern pattern, HexCoord origin, List<Vec2> zappyPoints) {
    // NO-OP
}
