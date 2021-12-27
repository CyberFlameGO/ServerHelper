package net.cyberflame.serverhelper.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.cyberflame.serverhelper.commons.utils.Utils;
import static net.cyberflame.serverhelper.Main.*;
import static org.bukkit.Bukkit.*;

public class GCTriggerCommand implements ICommand
{
    // I'm aware this is player-only, because it's a debug command.
    @Override
    public void execute(Player player, String[] args) {
        final String permission = "serverhelper.gc";
        if (!player.hasPermission(permission))
            {
                String noPermission = getInstance().getConfig().getString("messages.no_permission");
                assert noPermission != null;
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', noPermission));
                return;
            }
        final String message = getInstance().getConfig().getString("messages.gc");
        getScheduler().runTaskAsynchronously(getInstance(), System :: gc);
        // Components are the super confusing new way of sending messages.
        // I'm using Kyori Adventure components because they seem more modern, among other reasons
        // TODO: Make this work off of the debug toggle functionality, or potentially change to a permission-based
        //  debug toggle
        broadcast(Utils.component(message), permission);
    }
}