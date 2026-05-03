package virtuoel.pehkui.server.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.function.Consumer;

import org.spongepowered.asm.mixin.MixinEnvironment;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import io.netty.buffer.Unpooled;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import virtuoel.pehkui.Pehkui;
import virtuoel.pehkui.api.PehkuiConfig;
import virtuoel.pehkui.util.CommandUtils;
import virtuoel.pehkui.util.ConfigSyncUtils;
import virtuoel.pehkui.util.I18nUtils;
import virtuoel.pehkui.util.MixinTargetClasses;
import virtuoel.pehkui.util.NbtCompoundExtensions;

public class DebugCommand
{
	public static void register(final CommandDispatcher<CommandSourceStack> commandDispatcher)
	{
		final LiteralArgumentBuilder<CommandSourceStack> builder =
			Commands.literal("scale")
			.requires(source -> source.hasPermission(2));
		
		builder.then(Commands.literal("debug")
			.then(ConfigSyncUtils.registerConfigCommands())
		);
		
		if (FabricLoader.getInstance().isDevelopmentEnvironment() || PehkuiConfig.COMMON.enableCommands.get())
		{
			builder
				.then(Commands.literal("debug")
					.then(Commands.literal("delete_scale_data")
						.then(Commands.literal("uuid")
							.then(Commands.argument("uuid", StringArgumentType.string())
								.executes(context ->
								{
									final String uuidString = StringArgumentType.getString(context, "uuid");
									
									try
									{
										MARKED_UUIDS.add(UUID.fromString(uuidString));
									}
									catch (IllegalArgumentException e)
									{
										context.getSource().sendFailure(I18nUtils.translate("commands.pehkui.debug.delete.uuid.invalid", "Invalid UUID \"%s\".", uuidString));
										return 0;
									}
									
									return 1;
								})
							)
						)
						.then(Commands.literal("username")
							.then(Commands.argument("username", StringArgumentType.string())
								.executes(context ->
								{
									MARKED_USERNAMES.add(StringArgumentType.getString(context, "username").toLowerCase(Locale.ROOT));
									
									return 1;
								})
							)
						)
					)
					.then(Commands.literal("garbage_collect")
						.executes(context ->
						{
							context.getSource().getPlayerOrException().connection.send(
								new ClientboundCustomPayloadPacket(Pehkui.DEBUG_PACKET,
									new FriendlyByteBuf(Unpooled.buffer())
									.writeEnum(DebugPacketType.GARBAGE_COLLECT)
								)
							);
							
							System.gc();
							
							return 1;
						})
					)
				);
		}
		
		if (FabricLoader.getInstance().isDevelopmentEnvironment() || PehkuiConfig.COMMON.enableDebugCommands.get())
		{
			builder
				.then(Commands.literal("debug")
					.then(Commands.literal("run_mixin_tests")
						.executes(DebugCommand::runMixinTests)
					)
					.then(Commands.literal("run_tests")
						.executes(DebugCommand::runTests)
					)
				);
		}
		
		commandDispatcher.register(builder);
	}
	
	private static final Collection<UUID> MARKED_UUIDS = new HashSet<>();
	private static final Collection<String> MARKED_USERNAMES = new HashSet<>();
	
	public static boolean unmarkEntityForScaleReset(final Entity entity, final CompoundTag nbt)
	{
		if (entity instanceof Player && MARKED_USERNAMES.remove(((Player) entity).getGameProfile().getName().toLowerCase(Locale.ROOT)))
		{
			return true;
		}
		
		final NbtCompoundExtensions compound = ((NbtCompoundExtensions) nbt);
		
		return compound.pehkui_containsUuid("UUID") && MARKED_UUIDS.remove(compound.pehkui_getUuid("UUID"));
	}
	
	private static final List<EntityType<? extends Entity>> TYPES = Arrays.asList(
		EntityType.ZOMBIE,
		EntityType.CREEPER,
		EntityType.END_CRYSTAL,
		EntityType.BLAZE
	);
	
	private static int runTests(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
	{
		Entity entity = context.getSource().getEntityOrException();
		
		Direction dir = entity.getDirection();
		Direction opposite = dir.getOpposite();
		
		Direction left = dir.getCounterClockWise();
		Direction right = dir.getClockWise();
		
		int distance = 4;
		int spacing = 2;
		
		int width = ((TYPES.size() - 1) * (spacing + 1)) + 1;
		
		Vec3 pos = entity.position();
		BlockPos.MutableBlockPos mut = new BlockPos.MutableBlockPos(pos.x, pos.y, pos.z).move(dir, distance).move(left, width / 2);
		
		Level w = entity.getCommandSenderWorld();
		
		for (EntityType<?> t : TYPES)
		{
			w.setBlockAndUpdate(mut, Blocks.POLISHED_ANDESITE.defaultBlockState());
			final Entity e = t.create(w);
			
			e.absMoveTo(mut.getX() + 0.5, mut.getY() + 1, mut.getZ() + 0.5, opposite.toYRot(), 0);
			e.moveTo(mut.getX() + 0.5, mut.getY() + 1, mut.getZ() + 0.5, opposite.toYRot(), 0);
			e.setYHeadRot(opposite.toYRot());
			
			e.addTag("pehkui");
			
			w.addFreshEntity(e);
			
			mut.move(right, spacing + 1);
		}
		
		// TODO set command block w/ entity to void and block destroy under player pos
		
		int successes = -1;
		int total = -1;
		
		CommandUtils.sendFeedback(context.getSource(), () -> I18nUtils.translate("commands.pehkui.debug.test.success", "Tests succeeded: %d/%d", successes, total), false);
		
		return 1;
	}
	
	public static enum DebugPacketType
	{
		MIXIN_AUDIT,
		GARBAGE_COLLECT
		;
	}
	
	private static int runMixinTests(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
	{
		runMixinClassloadTests(
			t -> CommandUtils.sendFeedback(context.getSource(), () -> t, false),
			false,
			false,
			MixinTargetClasses.Common.CLASSES,
			MixinTargetClasses.Server.CLASSES
		);
		
		runMixinClassloadTests(
			t -> CommandUtils.sendFeedback(context.getSource(), () -> t, false),
			false,
			true,
			MixinTargetClasses.Common.INTERMEDIARY_CLASSES,
			MixinTargetClasses.Server.INTERMEDIARY_CLASSES
		);
		
		final Entity executor = context.getSource().getEntity();
		if (executor instanceof ServerPlayer)
		{
			((ServerPlayer) executor).connection.send(
				new ClientboundCustomPayloadPacket(Pehkui.DEBUG_PACKET,
					new FriendlyByteBuf(Unpooled.buffer())
					.writeEnum(DebugPacketType.MIXIN_AUDIT)
				)
			);
		}
		
		CommandUtils.sendFeedback(context.getSource(), () -> I18nUtils.translate("commands.pehkui.debug.audit.start", "Starting Mixin environment audit..."), false);
		MixinEnvironment.getCurrentEnvironment().audit();
		CommandUtils.sendFeedback(context.getSource(), () -> I18nUtils.translate("commands.pehkui.debug.audit.end", "Mixin environment audit complete!"), false);
		
		return 1;
	}
	
	public static void runMixinClassloadTests(final Consumer<Component> response, final boolean client, final boolean resolveMappings, final String[]... classes)
	{
		final Collection<String> succeeded = new ArrayList<String>();
		final Collection<String> failed = new ArrayList<String>();
		
		for (final String[] c : classes)
		{
			DebugCommand.classloadMixinTargets(c, resolveMappings, succeeded, failed);
		}
		
		final int successes = succeeded.size();
		final int fails = failed.size();
		final int total = successes + fails;
		
		if (fails > 0)
		{
			response.accept(I18nUtils.translate("commands.pehkui.debug.test.mixin.failed", "Failed classes: %s", "\"" + String.join("\", \"", failed) + "\""));
		}
		
		final String lang = "commands.pehkui.debug.test.mixin.results." + (resolveMappings ? "intermediary" : "named") + (client ? ".client" : ".server");
		final String defaultStr = "%d successes and %d fails out of %d mixined " + (resolveMappings ? "intermediary " : "") + (client ? "client" : "server") + " classes";
		response.accept(I18nUtils.translate(lang, defaultStr, successes, fails, total));
	}
	
	public static void classloadMixinTargets(final String[] classes, final boolean resolveMappings, final Collection<String> succeeded, final Collection<String> failed)
	{
		for (String name : classes)
		{
			name = name.replace('/', '.');
			
			if (resolveMappings)
			{
				name = FabricLoader.getInstance().getMappingResolver().mapClassName("intermediary", name);
			}
			
			try
			{
				Class.forName(name);
				succeeded.add(name);
			}
			catch (Exception e)
			{
				failed.add(name);
			}
		}
	}
}
