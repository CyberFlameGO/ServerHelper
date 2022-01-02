package net.cyberflame.serverhelper.commands.debug;

import net.cyberflame.serverhelper.ServerHelperPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class LogCommand implements CommandExecutor
{
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
	                         String[] args)
	{
		if (sender.hasPermission("serverhelper.log"))
			{
				{
					String message = "Test";

					if (args.length == 0)
						{
							ServerHelperPlugin.getInstance().getLogger().info(message);
							sender.sendMessage("§aLogged: §f" + message);
							return true;
						}

					String level;

					level = args[0].strip().toUpperCase();
					if (! Arrays.asList(new String[] {"INFO", "WARN", "ERROR", "SEVERE"}).contains(level))
						{
							sender.sendMessage("Level \"" + level + "\" is invalid, falling back to info");
							level = "INFO";
						}


					if (args.length >= 2)
						{
							List<String> messageList = new LinkedList<>(Arrays.asList(args));
							messageList.remove(0);
							message = String.join(" ", messageList);
						}

					switch (level)
						{
							case "CONFIG" -> ServerHelperPlugin.getInstance().getLogger().config(message);
							case "FINE" -> ServerHelperPlugin.getInstance().getLogger().fine(message);
							case "FINER" -> ServerHelperPlugin.getInstance().getLogger().finer(message);
							case "FINEST" -> ServerHelperPlugin.getInstance().getLogger().finest(message);
							case "INFO" -> ServerHelperPlugin.getInstance().getLogger().info(message);
							case "WARN" -> ServerHelperPlugin.getInstance().getLogger().warning(message);
							case "ERROR", "SEVERE" -> ServerHelperPlugin.getInstance().getLogger().severe(message);
						}
					sender.sendMessage("§aLogged with level " + level + ": §f" + message);
					return true;
				}
			}
		return false;
	}
}

