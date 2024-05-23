package net.cyberflame.serverhelper.listeners;

import java.util.List;

import net.cyberflame.serverhelper.ServerHelperPlugin;
import net.cyberflame.serverhelper.utils.Utils;
import org.bukkit.Chunk;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.entity.EntityToggleSwimEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import static net.cyberflame.serverhelper.ServerHelperPlugin.getInstance;
import static net.cyberflame.serverhelper.utils.Utils.*;

public class PetTeleportationFunctionalityListener implements Listener {
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onChunkUnload(ChunkUnloadEvent event) {
		Entity[] entities = event.getChunk().getEntities();

		for (Entity ent : entities) {
			if (!isPet(ent.getType())) continue;

			if (((Tameable) ent).getOwner() instanceof Player p) {
				if (p.isDead()) {
					ent.teleport(getPlayerSpawn(p));
				} else if (shouldDeferPetTeleport(p)) {
					ServerHelperPlugin.addDeferredPet(p.getUniqueId(), ent);
				}
				else ent.teleport(p);
			}
		}
	}

	@EventHandler
	public void onWorldChange(PlayerChangedWorldEvent event) {
		List<Entity> ents = event.getFrom().getEntities();

		for (Entity ent : ents) {
			if (!isPet(ent.getType())) continue;
			if (isPetAnchored(ent)) continue;
			Tameable tame = (Tameable) ent;

			if (tame.getOwner() instanceof Player p) {
				ent.teleport(p);
				ent.setPortalCooldown(300); // Stops pet going through nether portal instantly
			}
		}
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event)
	{
		List<Entity> ents = event.getEntity().getWorld().getEntities();

		for (Entity ent : ents) {
			if (!isPet(ent.getType())) continue;
			if (isPetAnchored(ent)) continue;
			Tameable tame = (Tameable) ent;
			AnimalTamer owner = tame.getOwner();

			if (owner instanceof Player p && event.getPlayer().equals(owner)) {
				ent.teleport(getPlayerSpawn(p));
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerTeleport(PlayerTeleportEvent event) {

		Chunk teleportedFrom = event.getFrom().getChunk();
		if (teleportedFrom.isForceLoaded()) return;

		// Add a plugin chunk ticket to the chunk (to keep the chunk loaded for 3 seconds)
		teleportedFrom.addPluginChunkTicket(getInstance());
		getInstance().getServer().getScheduler().
				runTaskLaterAsynchronously(
				getInstance(), () -> teleportedFrom.removePluginChunkTicket(getInstance()),
				20L * 3); // 3 seconds
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onEntityToggleGlide(EntityToggleGlideEvent event) {
		if (event.isGliding()) return;

		if(event.getEntity() instanceof Player p) {
			Utils.teleportIfInMap(p);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onEntityToggleSwimming(EntityToggleSwimEvent event) {
		if (event.isSwimming()) return;

		if(event.getEntity() instanceof Player p) {
			Utils.teleportIfInMap(p);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerExitVehicle(VehicleExitEvent event) {
		if (!(event.getExited() instanceof Player p)) return;

		Utils.teleportIfInMap(p);
	}
}
