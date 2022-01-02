package net.cyberflame.serverhelper.listeners;

import net.cyberflame.serverhelper.ServerHelperPlugin;
import net.cyberflame.serverhelper.commons.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCreativeEvent;

import java.util.UUID;

public class InventoryCreativeListener implements Listener
{
	@EventHandler
	public void onInventoryCreative(InventoryCreativeEvent event)
	{
		for(Player player : Bukkit.getOnlinePlayers())
			{
				UUID uuid = player.getUniqueId();
				if (ServerHelperPlugin.isReceivingDebug(uuid))
					{
						Utils.sendMessage(player,
						                  "&c[&6InventoryCreativeListener&c] &7(This event currently only observes who " +
						                  "clicked, not anything else) \nEvent fired by: " + event.getWhoClicked().getName());
					}
			}
	}
}
