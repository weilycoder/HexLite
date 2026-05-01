package at.petrak.hexlite.api.casting.eval.vm

import at.petrak.hexlite.api.casting.iota.Iota

/**
 * A change to the data in a CastingVM after a pattern is drawn.
 */
data class FunctionalData(
    val stack: List<Iota>,
    val parenCount: Int,
    val parenthesized: List<Iota>,
    val escapeNext: Boolean,
    val ravenmind: Iota?
)

