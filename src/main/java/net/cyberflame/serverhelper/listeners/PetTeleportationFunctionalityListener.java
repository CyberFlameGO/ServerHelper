package net.cyberflame.serverhelper.listeners;

import static net.cyberflame.serverhelper.ServerHelperPlugin.getInstance;
import static net.cyberflame.serverhelper.utils.Utils.*;

import java.util.List;
import net.cyberflame.serverhelper.ServerHelperPlugin;
import net.cyberflame.serverhelper.utils.Utils;
import org.bukkit.Chunk;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.entity.EntityToggleSwimEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

public class PetTeleportationFunctionalityListener implements Listener {
  @EventHandler
  public void onChunkUnload(ChunkUnloadEvent event) {
    // Get entities inside chunk
    Entity[] entities = event.getChunk().getEntities();

    // Find pets
    for (Entity ent : entities) {
      // Get pets that can teleport with you
      if (notPet(ent.getType())) continue;
      // Teleport standing pets
      if (isPetAnchored(ent)) continue;

      // Execute if player is alive and online
      if (((Tameable) ent).getOwner() instanceof Player p) {
        if (p.isDead()) {
          ent.teleport(getPlayerSpawn(p));
          return;
        } else if (shouldDeferPetTeleport(p)) {
          ServerHelperPlugin.addDeferredPet(p.getUniqueId(), ent);
        } else ent.teleport(p);
      }
    }
  }

  @EventHandler
  public void onWorldChange(PlayerChangedWorldEvent event) {
    // Get entities inside chunk
    List<Entity> ents = event.getFrom().getEntities();

    // Find pets
    for (Entity ent : ents) {
      // Get pets that can teleport with you
      if (notPet(ent.getType())) continue;
      // Teleport standing pets
      Sittable sit = (Sittable) ent;
      if (sit.isSitting()) continue;
      Tameable tame = (Tameable) ent;

      // But only if the player is online!
      if (tame.getOwner() instanceof Player p) {
        ent.teleport(p);
        ent.setPortalCooldown(300); // Don't want pet to go through nether portal instantly
      }
    }
  }

  @EventHandler
  public void onPlayerDeath(PlayerDeathEvent event) {
    List<Entity> ents = event.getEntity().getWorld().getEntities();

    for (Entity ent : ents) {
      if (notPet(ent.getType())) continue;
      // Teleport standing pets
      if (isPetAnchored(ent)) continue;
      Tameable tame = (Tameable) ent;
      AnimalTamer owner = tame.getOwner();

      if (owner instanceof Player p && event.getPlayer() == owner) {
        ent.teleport(getPlayerSpawn(p));
      }
    }
  }

  @EventHandler
  public void onPlayerTeleport(PlayerTeleportEvent event) {

    Chunk teleportedFrom = event.getFrom().getChunk();
    if (teleportedFrom.isForceLoaded()) return;

    teleportedFrom.addPluginChunkTicket(getInstance());
    getInstance()
        .getServer()
        .getScheduler()
        .runTaskLaterAsynchronously(
            getInstance(),
            () -> teleportedFrom.removePluginChunkTicket(getInstance()),
            20L * 3); // 3 seconds
  }

  @EventHandler
  public void onEntityToggleGlide(EntityToggleGlideEvent event) {
    if (event.isGliding()) return;

    // If the entity is a player
    if (event.getEntity() instanceof Player p) {
      Utils.teleportIfInMap(p);
    }
  }

  @EventHandler
  public void onEntityToggleSwimming(EntityToggleSwimEvent event) {
    if (event.isSwimming()) return;

    // If the entity is a player
    if (event.getEntity() instanceof Player p) {
      Utils.teleportIfInMap(p);
    }
  }

  @EventHandler
  public void onPlayerExitVehicle(VehicleExitEvent event) {
    if (!(event.getExited() instanceof Player p)) return;

    Utils.teleportIfInMap(p);
  }
}
