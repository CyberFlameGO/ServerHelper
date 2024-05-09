package net.cyberflame.serverhelper.listeners;

import net.cyberflame.serverhelper.ServerHelperPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerQuitListener implements Listener
{
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		UUID uuid = event.getPlayer().getUniqueId();
		if (ServerHelperPlugin.isReceivingDebug(uuid))
			ServerHelperPlugin.toggleReceivingDebug(uuid);
	}
}
