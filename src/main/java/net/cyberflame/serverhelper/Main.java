package net.cyberflame.serverhelper;

import net.cyberflame.serverhelper.commands.CommandLogic;
import net.cyberflame.serverhelper.commands.RootCommand;
import net.cyberflame.serverhelper.listeners.tempname;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class Main extends JavaPlugin
{
    private static Set<UUID> receivingDebug;
    private static Main instance;

    @Override
    public void onEnable()
    {
        this.saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();
        Main.instance = this;
        receivingDebug = new HashSet<>();
        registerListeners();

        CommandLogic executor = new CommandLogic();

        CommandExecutor commandExecutor = (commandSender, command, s, strings) ->
            {
                String[] commandArgs = new String[strings.length + 1];
                commandArgs[0] = Objects.requireNonNull(s);
                System.arraycopy(strings, 0, commandArgs, 1, strings.length);
                executor.onCommand(commandSender, command, s, commandArgs);
                return true;
            };
        Objects.requireNonNull(getCommand("triggergc")).setExecutor(commandExecutor);
        Objects.requireNonNull(getCommand("toggledebug")).setExecutor(commandExecutor);
        Objects.requireNonNull(getCommand("serverhelper")).setExecutor(new RootCommand());
    }

    private void registerListeners()
    {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new tempname(), this);
    }

    @SuppressWarnings("FinalStaticMethod")
    public static final Main getInstance()
    {
        return Main.instance;
    }

    public boolean getReceivingDebug(UUID uuid)
    {
        return receivingDebug.contains(uuid);
    }

    public void setReceivingDebug(UUID uuid)
    {
        if (getReceivingDebug(uuid)) receivingDebug.remove(uuid);
        else receivingDebug.add(uuid);
    }
}
