package at.petrak.hexlite.api.casting.eval;

import at.petrak.hexlite.api.casting.math.HexDir;
import at.petrak.hexlite.api.casting.math.HexPattern;

public final class SpecialPatterns {
    public static final HexPattern INTROSPECTION = HexPattern.fromAngles("qqq", HexDir.WEST);
    public static final HexPattern RETROSPECTION = HexPattern.fromAngles("eee", HexDir.EAST);
    public static final HexPattern CONSIDERATION = HexPattern.fromAngles("qqqaw", HexDir.WEST);

    public static final HexPattern EVANITION = HexPattern.fromAngles("eeedw", HexDir.EAST);
}
