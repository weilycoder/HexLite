package at.petrak.hexlite.api.casting.eval.env;

import at.petrak.hexlite.api.casting.circles.CircleExecutionState;
import at.petrak.hexlite.api.casting.eval.MishapEnvironment;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;

public class CircleMishapEnv extends MishapEnvironment {
    protected final CircleExecutionState execState;

    protected CircleMishapEnv(ServerLevel world, CircleExecutionState execState) {
        super(world, null);
        this.execState = execState;
    }

    @Override
    public void yeetHeldItemsTowards(Vec3 targetPos) {

    }

    @Override
    public void dropHeldItems() {

    }

    @Override
    public void drown() {

    }

    @Override
    public void damage(float healthProportion) {

    }

    @Override
    public void removeXp(int amount) {

    }

    @Override
    public void blind(int ticks) {

    }
}
