package net.cyberflame.serverhelper;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import net.cyberflame.serverhelper.commands.CommandLogic;
import net.cyberflame.serverhelper.commands.ServerHelperCommand;
import net.cyberflame.serverhelper.commands.debug.LogCommand;
import net.cyberflame.serverhelper.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ServerHelperPlugin extends JavaPlugin {
  private static Set<UUID> RECEIVING_DEBUG;
  private static ServerHelperPlugin INSTANCE;

  @Override
  public void onEnable() {
    this.saveDefaultConfig();
    getConfig().options().copyDefaults(true);
    saveConfig();
    ServerHelperPlugin.INSTANCE = this;
    RECEIVING_DEBUG = new HashSet<>();
    registerListeners();

    CommandLogic executor = new CommandLogic();

    CommandExecutor commandExecutor =
        (commandSender, command, s, strings) -> {
          String[] commandArgs = new String[strings.length + 1];
          commandArgs[0] = Objects.requireNonNull(s);
          System.arraycopy(strings, 0, commandArgs, 1, strings.length);
          executor.onCommand(commandSender, command, s, commandArgs);
          return true;
        };
    Objects.requireNonNull(getCommand("triggergc")).setExecutor(commandExecutor);
    Objects.requireNonNull(getCommand("toggledebug")).setExecutor(commandExecutor);
    Objects.requireNonNull(getCommand("serverhelper")).setExecutor(new ServerHelperCommand());
    Objects.requireNonNull(getCommand("log")).setExecutor(new LogCommand());
  }

  private void registerListeners() {
    PluginManager pm = Bukkit.getPluginManager();
    pm.registerEvents(new PetTeleportationFunctionalityListener(), this);
    pm.registerEvents(new InventoryCreativeListener(), this);
    pm.registerEvents(new McMMOPlayerLevelUpListener(), this);
    pm.registerEvents(new PlayerQuitListener(), this);
    pm.registerEvents(new RemoteServerCommandListener(), this);
  }

  @SuppressWarnings("FinalStaticMethod")
  public static final ServerHelperPlugin getInstance() {
    return ServerHelperPlugin.INSTANCE;
  }

  public static boolean isReceivingDebug(UUID uuid) {
    return RECEIVING_DEBUG.contains(uuid);
  }

  @SuppressWarnings("unused")
  public static Set<UUID> getReceivingDebug() {
    return RECEIVING_DEBUG;
  }

  public static void toggleReceivingDebug(UUID uuid) {
    if (isReceivingDebug(uuid)) RECEIVING_DEBUG.remove(uuid);
    else RECEIVING_DEBUG.add(uuid);
  }
}
