package net.cyberflame.serverhelper.commands.debug;

import static net.cyberflame.serverhelper.ServerHelperPlugin.*;
import static org.bukkit.Bukkit.*;

import net.cyberflame.serverhelper.commands.ICommand;
import net.cyberflame.serverhelper.commons.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class GCTriggerCommand implements ICommand {
  // I'm aware this is player-only, because it's a debug command.
  @Override
  public void execute(Player player, String[] args) {
    final String permission = "serverhelper.gc";
    if (!player.hasPermission(permission)) {
      String noPermission = getInstance().getConfig().getString("messages.no_permission");
      assert noPermission != null;
      player.sendMessage(ChatColor.translateAlternateColorCodes('&', noPermission));
      return;
    }
    final String message = getInstance().getConfig().getString("messages.gc");
    getScheduler().runTaskAsynchronously(getInstance(), System::gc);
    // Components are the super confusing new way of sending messages.
    // I'm using Kyori Adventure components because they seem more modern, among other reasons

    // Anyone who can execute the command can see its output
    broadcast(Utils.component(message), permission);
  }
}
