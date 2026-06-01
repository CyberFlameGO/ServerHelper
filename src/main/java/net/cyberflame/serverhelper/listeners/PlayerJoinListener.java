package net.cyberflame.serverhelper.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    // https://docs.papermc.io/velocity/dev/plugin-messaging#case-3-receiving-a-plugin-message-from-a-backend-server
    // https://docs.papermc.io/paper/dev/plugin-messaging
  }
}
