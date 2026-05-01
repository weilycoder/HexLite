package at.petrak.hexlite.api.casting.arithmetic.engine;


import at.petrak.hexlite.api.casting.iota.IotaType;
import at.petrak.hexlite.api.casting.math.HexPattern;

// Helper type for hashing lists of types.
public sealed interface HashCons {
	public record Pattern(HexPattern operator) implements HashCons {}

	public record Pair(IotaType<?> head, HashCons tail) implements HashCons {}
}
