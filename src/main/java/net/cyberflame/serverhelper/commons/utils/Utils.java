package net.cyberflame.serverhelper.commons.utils;

import static net.kyori.adventure.text.Component.text;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Utils {
  // If I need to use more components than just text, I'll make this method accept "Component..." or
  // whatever the
  // actual name of it is, so it can accept multiple components.
  public static Component component(String string) {
    return text().append(LegacyComponentSerializer.legacy('&').deserialize(string)).build();
  }

  private static String toMinecraftChatColor(String text) {
    return ChatColor.translateAlternateColorCodes('&', text);
  }

  public static void sendMessage(CommandSender sender, String message) {
    sender.sendMessage(toMinecraftChatColor(message));
  }
}
