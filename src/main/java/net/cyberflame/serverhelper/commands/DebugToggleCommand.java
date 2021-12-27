package net.cyberflame.serverhelper.commands;

import net.cyberflame.serverhelper.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Objects;

import static net.cyberflame.serverhelper.Main.getInstance;

public class DebugToggleCommand implements ICommand
{
	private final Main instance = Main.getInstance();

	@Override
	public void execute(Player player, String[] args)
	{
		final String permission = "serverhelper.debug";
		String bypassToggleOn = getInstance().getConfig().getString("messages.bypass_toggle_on");
		String bypassToggleOff = getInstance().getConfig().getString("messages.bypass_toggle_off");
		if (! player.hasPermission(permission))
			{
				String noPermission = getInstance().getConfig().getString("messages.no_permission");
				assert noPermission != null;
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', noPermission));
				return;
			}
		if (args.length == 1)
			{
				if (Bukkit.getPlayer(args[0]) != null)
					{
						Player target = Bukkit.getPlayer(args[0]);
						String targetName = Objects.requireNonNull(Bukkit.getPlayer(args[0])).getName();
						instance.setReceivingDebug(Objects.requireNonNull(Bukkit.getPlayer(args[0])).getUniqueId());
						String bypassOtherToggleOn =
								getInstance().getConfig().getString("messages.bypass_other_toggle_on");
						String bypassOtherToggleOff =
								getInstance().getConfig().getString("messages" + ".bypass_other_toggle_off");
						assert target != null;
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', (instance.getReceivingDebug(
								target.getUniqueId()) ? Objects.requireNonNull(bypassOtherToggleOn)
						                                       .replaceAll("%player%", targetName) : Objects
								.requireNonNull(bypassOtherToggleOff).replaceAll("%player%", targetName))));
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', (instance.getReceivingDebug(
								target.getUniqueId()) ? Objects.requireNonNull(bypassToggleOn)
						                                       .replaceAll("%player%", targetName) : Objects
								.requireNonNull(bypassToggleOff).replaceAll("%player%", targetName))));
					}
				else
					{
						String unknownPlayer = getInstance().getConfig().getString("messages.unknown_player");
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
						                                                          Objects.requireNonNull(unknownPlayer)
						                                                                 .replaceAll("%player%",
						                                                                             args[0])));
					}
				return;
			}
		instance.setReceivingDebug(player.getUniqueId());
		player.sendMessage(ChatColor.translateAlternateColorCodes('&',
		                                                          (instance.getReceivingDebug(player.getUniqueId()) ?
				                                                           Objects.requireNonNull(bypassToggleOn)
				                                                                  .replaceAll("%player%",
				                                                                              player.getName()) :
				                                                           Objects.requireNonNull(bypassToggleOff)
				                                                                  .replaceAll("%player%",
				                                                                              player.getName()))));
	}
}
