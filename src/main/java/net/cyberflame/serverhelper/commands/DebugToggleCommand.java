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
		String alertsToggleOn = getInstance().getConfig().getString("messages.alerts_toggle_on");
		String alertsToggleOff = getInstance().getConfig().getString("messages.alerts_toggle_off");
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
						String alertsOtherToggleOn =
								getInstance().getConfig().getString("messages.alerts_toggle_on_other");
						String alertsOtherToggleOff =
								getInstance().getConfig().getString("messages.alerts_toggle_off_other");
						assert target != null;
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', (instance.getReceivingDebug(
								target.getUniqueId()) ? Objects.requireNonNull(alertsOtherToggleOn)
						                                       .replaceAll("%player%", targetName) : Objects
								.requireNonNull(alertsOtherToggleOff).replaceAll("%player%", targetName))));
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', (instance.getReceivingDebug(
								target.getUniqueId()) ? Objects.requireNonNull(alertsToggleOn)
						                                       .replaceAll("%player%", targetName) : Objects
								.requireNonNull(alertsToggleOff).replaceAll("%player%", targetName))));
					}
				else
					{
						String unknownPlayer = getInstance().getConfig().getString("messages.unknown_player");
						assert unknownPlayer != null;
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
				                                                           Objects.requireNonNull(alertsToggleOn)
				                                                                  .replaceAll("%player%",
				                                                                              player.getName()) :
				                                                           Objects.requireNonNull(alertsToggleOff)
				                                                                  .replaceAll("%player%",
				                                                                              player.getName()))));
	}
}
