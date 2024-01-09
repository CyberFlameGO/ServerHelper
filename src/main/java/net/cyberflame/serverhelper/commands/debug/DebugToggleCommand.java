package net.cyberflame.serverhelper.commands.debug;

import static net.cyberflame.serverhelper.ServerHelperPlugin.getInstance;

import java.util.Objects;
import net.cyberflame.serverhelper.ServerHelperPlugin;
import net.cyberflame.serverhelper.commands.ICommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DebugToggleCommand implements ICommand {
  @Override
  public void execute(@NotNull Player player, String[] args) {
    final String permission = "serverhelper.debug";
    String alertsToggleOn = getInstance().getConfig().getString("messages.alerts_toggle_on");
    String alertsToggleOff = getInstance().getConfig().getString("messages.alerts_toggle_off");
    if (!player.hasPermission(permission)) {
      String noPermission = getInstance().getConfig().getString("messages.no_permission");
      assert noPermission != null;
      player.sendMessage(ChatColor.translateAlternateColorCodes('&', noPermission));
      return;
    }
    if (args.length == 1) {
      if (Bukkit.getPlayer(args[0]) != null) {
        Player target = Bukkit.getPlayer(args[0]);
        String targetName = Objects.requireNonNull(Bukkit.getPlayer(args[0])).getName();
        ServerHelperPlugin.toggleReceivingDebug(
            Objects.requireNonNull(Bukkit.getPlayer(args[0])).getUniqueId());
        String alertsOtherToggleOn =
            getInstance().getConfig().getString("messages.alerts_toggle_on_other");
        String alertsOtherToggleOff =
            getInstance().getConfig().getString("messages.alerts_toggle_off_other");
        assert target != null;
        player.sendMessage(
            ChatColor.translateAlternateColorCodes(
                '&',
                (ServerHelperPlugin.isReceivingDebug(target.getUniqueId())
                    ? Objects.requireNonNull(alertsOtherToggleOn).replaceAll("%player%", targetName)
                    : Objects.requireNonNull(alertsOtherToggleOff)
                        .replaceAll("%player%", targetName))));
        target.sendMessage(
            ChatColor.translateAlternateColorCodes(
                '&',
                (ServerHelperPlugin.isReceivingDebug(target.getUniqueId())
                    ? Objects.requireNonNull(alertsToggleOn).replaceAll("%player%", targetName)
                    : Objects.requireNonNull(alertsToggleOff).replaceAll("%player%", targetName))));
      } else {
        String unknownPlayer = getInstance().getConfig().getString("messages.unknown_player");
        assert unknownPlayer != null;
        player.sendMessage(
            ChatColor.translateAlternateColorCodes(
                '&', Objects.requireNonNull(unknownPlayer).replaceAll("%player%", args[0])));
      }
      return;
    }
    ServerHelperPlugin.toggleReceivingDebug(player.getUniqueId());
    player.sendMessage(
        ChatColor.translateAlternateColorCodes(
            '&',
            (ServerHelperPlugin.isReceivingDebug(player.getUniqueId())
                ? Objects.requireNonNull(alertsToggleOn).replaceAll("%player%", player.getName())
                : Objects.requireNonNull(alertsToggleOff)
                    .replaceAll("%player%", player.getName()))));
  }
}
