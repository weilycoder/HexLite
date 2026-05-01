package at.petrak.hexlite.common.casting.actions.spells

import at.petrak.hexlite.api.casting.ParticleSpray
import at.petrak.hexlite.api.casting.RenderedSpell
import at.petrak.hexlite.api.casting.castables.SpellAction
import at.petrak.hexlite.api.casting.eval.CastingEnvironment
import at.petrak.hexlite.api.casting.getVec3
import at.petrak.hexlite.api.casting.iota.Iota
import at.petrak.hexlite.api.misc.MediaConstants
import at.petrak.hexlite.api.mod.HexConfig
import at.petrak.hexlite.api.mod.HexTags
import at.petrak.hexlite.xplat.IXplatAbstractions
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.phys.Vec3

object OpBreakBlock : SpellAction {
    override val argc: Int
        get() = 1

    override fun execute(
        args: List<Iota>,
        env: CastingEnvironment
    ): SpellAction.Result {
        val vecPos = args.getVec3(0, argc)
        val pos = BlockPos.containing(vecPos)
        env.assertPosInRangeForEditing(pos)

        val isCheap = env.world.getBlockState(pos).`is`(HexTags.Blocks.CHEAP_TO_BREAK_BLOCK)

        return SpellAction.Result(
            Spell(pos),
            if (isCheap) MediaConstants.DUST_UNIT / 100 else MediaConstants.DUST_UNIT / 8,
            listOf(ParticleSpray.burst(Vec3.atCenterOf(pos), 1.0))
        )
    }

    private data class Spell(val pos: BlockPos) : RenderedSpell {
        override fun cast(env: CastingEnvironment) {
            val blockstate = env.world.getBlockState(pos)
            val tier = HexConfig.server().opBreakHarvestLevel()

            if (
                !blockstate.isAir
                && blockstate.getDestroySpeed(env.world, pos) >= 0f // fix being able to break bedrock &c
                && IXplatAbstractions.INSTANCE.isCorrectTierForDrops(tier, blockstate)
                && IXplatAbstractions.INSTANCE.isBreakingAllowed(
                    env.world,
                    pos,
                    blockstate,
                    env.castingEntity as? ServerPlayer
                )
            ) {
                env.world.destroyBlock(pos, true, env.castingEntity)
            }
        }
    }
}
