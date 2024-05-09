package net.cyberflame.serverhelper.listeners;

import java.util.UUID;
import net.cyberflame.serverhelper.ServerHelperPlugin;
import net.cyberflame.serverhelper.commons.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.RemoteServerCommandEvent;

public class RemoteServerCommandListener implements Listener {
  @EventHandler
  public void onRemoteServerCommand(RemoteServerCommandEvent event) {
    for (Player player : Bukkit.getOnlinePlayers()) {
      UUID uuid = player.getUniqueId();
      if (ServerHelperPlugin.isReceivingDebug(uuid)) {
        Utils.sendMessage(
            player, "&c[&6RemoteServerCommandListener&c] &7Command: " + event.getCommand());
      }
    }
  }
}
