package net.cyberflame.serverhelper.listeners;

import java.util.concurrent.ConcurrentHashMap;
import net.cyberflame.serverhelper.ServerHelperPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;

public class AsyncPlayerChatListener implements Listener {
  public static ConcurrentHashMap<String, String> messages = new ConcurrentHashMap<>();

  @EventHandler(priority = EventPriority.MONITOR)
  @SuppressWarnings("deprecation")
  public void onChat(@NotNull AsyncPlayerChatEvent event) {
    String message = event.getMessage();
    if (message.startsWith("/")) {
      return;
    }

    String uuid = event.getPlayer().getUniqueId().toString();
    if (message.startsWith("s/")) {
      String[] split = message.split("/");
      if (split.length >= 3) {
        String oldString = split[1];
        String newString = split[2];

        if (messages.get(uuid) != null) {
          final Player player = event.getPlayer();
          final String newMessage =
              messages.get(uuid).replace(oldString, "\u00A7o" + newString + "\u00A7r");
          if (newMessage.length() <= 150) {
            ServerHelperPlugin.getInstance()
                .getServer()
                .getScheduler()
                .scheduleSyncDelayedTask(
                    ServerHelperPlugin.getInstance(),
                    () -> {
                      try {
                        player.chat(newMessage);
                      } catch (Exception e) {
                        e.printStackTrace();
                      }
                    },
                    1);
          }
        }

        return;
      }
    }

    messages.put(uuid, message);
  }
}
