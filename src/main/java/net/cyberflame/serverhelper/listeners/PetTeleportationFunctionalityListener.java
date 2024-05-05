package net.cyberflame.serverhelper.listeners;

import java.util.List;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import static net.cyberflame.serverhelper.commons.utils.Utils.getPlayerSpawn;
import static net.cyberflame.serverhelper.commons.utils.Utils.isPet;

public class PetTeleportationFunctionalityListener implements Listener {
	@EventHandler
	public void onChunkUnload(ChunkUnloadEvent event) {
		// Get entities inside chunk
		Entity[] entities = event.getChunk().getEntities();

		// Find pets
		for (Entity ent : entities) {
			// Get pets that can teleport with you
			if (!isPet(ent.getType())) continue;
			// Teleport standing pets
			Sittable sit = (Sittable) ent;
			if (sit.isSitting()) continue;
			Tameable tame = (Tameable) ent;

			// Execute if player is alive and online
			if (tame.getOwner() instanceof Player p) if(!p.isDead()) ent.teleport(p);
			else ent.teleport(getPlayerSpawn(p));

		}
	}

	@EventHandler
	public void onWorldChange(PlayerChangedWorldEvent event) {
		// Get entities inside chunk
		List<Entity> ents = event.getFrom().getEntities();

		// Find pets
		for (Entity ent : ents) {
			// Get pets that can teleport with you
			if (!isPet(ent.getType())) continue;
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
	public void onPlayerDeath(PlayerDeathEvent event)
	{
		List<Entity> ents = event.getEntity().getWorld().getEntities();

		for (Entity ent : ents) {
			if (!isPet(ent.getType())) continue;
			// Teleport standing pets
			Sittable sit = (Sittable) ent;
			if (sit.isSitting()) continue;
			Tameable tame = (Tameable) ent;
			AnimalTamer owner = tame.getOwner();

			if (owner instanceof Player p && event.getPlayer() == owner) {
				ent.teleport(getPlayerSpawn(p));
			}
		}
	}

//	@EventHandler
//	public void onPlayerTeleport(PlayerTeleportEvent event) {
//		// Get entities inside chunk
//		List<Entity> ents = event.getFrom().g
//
//		// Find pets
//		for (Entity ent : ents) {
//			// Get pets that can teleport with you
//			if (!isPet(ent.getType())) continue;
//			// Teleport standing pets
//			Sittable sit = (Sittable) ent;
//			if (sit.isSitting()) continue;
//			Tameable tame = (Tameable) ent;
//
//			// But only if the player is online!
//			if (tame.getOwner() instanceof Player p) {
//				ent.teleport(p);
//				ent.setPortalCooldown(300); // Don't want pet to go through nether portal instantly
//			}
//		}
//	}
}
