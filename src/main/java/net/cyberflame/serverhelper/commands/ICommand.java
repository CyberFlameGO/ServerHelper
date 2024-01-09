package net.cyberflame.serverhelper.commands;

import org.bukkit.entity.Player;

public interface ICommand {

  void execute(Player player, String[] args);
}
