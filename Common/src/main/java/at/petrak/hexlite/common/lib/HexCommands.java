package at.petrak.hexlite.common.lib;

import at.petrak.hexlite.common.command.BrainsweepCommand;
import at.petrak.hexlite.common.command.ListPerWorldPatternsCommand;
import at.petrak.hexlite.common.command.PatternTexturesCommand;
import at.petrak.hexlite.common.command.RecalcPatternsCommand;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class HexCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        var mainCmd = Commands.literal("hexlite");

        BrainsweepCommand.add(mainCmd);
        ListPerWorldPatternsCommand.add(mainCmd);
        RecalcPatternsCommand.add(mainCmd);
        PatternTexturesCommand.add(mainCmd);

        dispatcher.register(mainCmd);
    }
}
