package net.cyberflame.serverhelper.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandLogic implements CommandExecutor
{

	private final Map<String, ICommand> commands;

	// The plugin's plugin-specific (serverhelper:<command>) commands don't work, but I don't mind.
	public CommandLogic() {
		commands = new HashMap<>();
		registerCommand(new GCTriggerCommand(), "triggergc", "garbagecollect", "gc");
		registerCommand(new DebugToggleCommand(), "toggledebug", "debugalerts", "debugnotifications",
		                "debugalertstoggle", "dat");
	}

	private void registerCommand(ICommand iCommand, String... aliases) {
		for (String alias : aliases)
			{
				commands.put(alias.toLowerCase(), iCommand);
			}
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String str,
	                         String[] args) {
		// Player-only; we can just use proper command registration for commands needing to be executed by non-players
		// e.g. console commands.
		Player player = (sender instanceof Player) ? ((Player) sender) : null;
		if (player == null)
			{
				sender.sendMessage("Sorry, this command can only be used by a player.");
				return false;
			}

		ICommand iCommand = commands.get(str.toLowerCase());
		if (iCommand != null)
			{
				iCommand.execute(player, Arrays.copyOfRange(args, 1, args.length));
			}
		return true;
	}
}