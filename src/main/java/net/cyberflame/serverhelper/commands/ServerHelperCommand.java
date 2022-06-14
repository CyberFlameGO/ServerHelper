package net.cyberflame.serverhelper.commands;

import net.cyberflame.serverhelper.ServerHelperPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ServerHelperCommand implements CommandExecutor
{
	final String permission = "serverhelper.reload";

	// TODO: Create a debug "toolchain" which includes config reloading and GC, not limited to players (while
	//  retaining the current command structure as the primary structure). Ideally a root command with subcommands.
	@Override
	public boolean onCommand(CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args)
	{
		if (sender.hasPermission("serverhelper.main"))
			{
				if (args.length == 0)
					{
						sender.sendMessage("§aServerHelper is a plugin made for debugging and administration of this " +
						                   "Minecraft server. You can view the source code at " +
						                   "https://github.com/CyberFlameGO/ServerHelper");
						if (sender.hasPermission(permission))
							{
								sender.sendMessage("§eSub-command syntax: §6/serverhelper [subcommand]\n" +
								                   "§3Subcommands: §6reload §7- §2Reloads the plugin configuration.");
							}
					}
				else
					{
						if (args[0].equals("reload") && sender.hasPermission(permission))
							{
								ServerHelperPlugin.getInstance().reloadConfig();
								sender.sendMessage("§ePlugin configuration has been §2reloaded§c!");
							}
						else
							{
								sender.sendMessage("§cUnrecognized parameters. Type §6/serverhelper §cto view the " +
								                   "sub-command syntax and applicable arguments.");
							}
					}
				return true;
			}
		else return false;
	}
}
