package at.petrak.paucal.xplat;

import at.petrak.paucal.api.msg.PaucalMessage;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.core.Registry;
import net.minecraft.world.phys.Vec3;

/**
 * Small adapter to delegate PAUCAL's xplat calls to HexLite's xplat implementation.
 */
public class IXplatAbstractions {
    public Platform platform() {
        return Platform.FABRIC;
    }

    public SoundEvent getSoundByID(ResourceLocation id) {
        // Registry APIs vary across mappings; return null as a safe default.
        return null;
    }

    public void sendPacketToPlayerS2C(ServerPlayer target, PaucalMessage packet) {
        at.petrak.hexlite.xplat.IXplatAbstractions.INSTANCE.sendPacketToPlayer(target,
            new MessageWrapper(packet));
    }

    public void sendPacketNearS2C(Vec3 pos, double radius, ServerLevel dimension, PaucalMessage packet) {
        at.petrak.hexlite.xplat.IXplatAbstractions.INSTANCE.sendPacketNear(pos, radius, dimension,
            new MessageWrapper(packet));
    }

    public void init() {
        at.petrak.hexlite.xplat.IXplatAbstractions.INSTANCE.initPlatformSpecific();
    }

    public static final IXplatAbstractions INSTANCE = new IXplatAbstractions();

    private static final class MessageWrapper implements at.petrak.hexlite.common.msgs.IMessage {
        private final PaucalMessage wrapped;

        private MessageWrapper(PaucalMessage wrapped) {
            this.wrapped = wrapped;
        }

        @Override
        public void serialize(FriendlyByteBuf buf) {
            this.wrapped.serialize(buf);
        }

        @Override
        public ResourceLocation getFabricId() {
            return this.wrapped.getFabricId();
        }
    }
}
