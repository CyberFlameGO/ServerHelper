package net.cyberflame.serverhelper;

import net.cyberflame.serverhelper.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class ServerHelperPlugin extends JavaPlugin
{
    private static ServerHelperPlugin INSTANCE;
    private static HashMap<UUID, HashSet<Entity>> deferredPets = new HashMap<>();

    @Override
    public void onEnable()
    {
        ServerHelperPlugin.INSTANCE = this;
        getServer().getMessenger().registerOutgoingPluginChannel(this, "cyberflame:main");
        registerListeners();
    }

    private void registerListeners()
    {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PetTeleportationFunctionalityListener(), this);
        pm.registerEvents(new PlayerJoinListener(), this);
    }

    @SuppressWarnings("FinalStaticMethod")
    public static final ServerHelperPlugin getInstance()
    {
        return ServerHelperPlugin.INSTANCE;
    }

    public static HashMap<UUID, HashSet<Entity>> getDeferredPets() {
        return deferredPets;
    }

    public static void addDeferredPet(UUID player, Entity pet) {
        if (deferredPets.containsKey(player)) {
            deferredPets.get(player).add(pet);
        }
        else {
            HashSet<Entity> pets = new HashSet<>();
            pets.add(pet);
            deferredPets.put(player, pets);
        }
    }
}
