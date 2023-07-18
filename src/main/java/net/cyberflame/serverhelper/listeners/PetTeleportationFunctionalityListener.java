package net.cyberflame.serverhelper.listeners;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sittable;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

public class PetTeleportationFunctionalityListener implements Listener
{
	@EventHandler
	public void onChunkUnload(ChunkUnloadEvent event)
	{
		// Get entities inside chunk
		Entity[] entities = event.getChunk().getEntities();

		// Find pets
		for (Entity ent : entities) {
			// Get pets that can teleport with you
			if (ent.getType() == EntityType.WOLF || ent.getType() == EntityType.CAT || ent.getType() == EntityType.PARROT) {
				// Teleport standing pets
				Sittable sit = (Sittable) ent;
				if (!sit.isSitting()) {
					Tameable tame = (Tameable) ent;

					// But only if the player is online!
					if (tame.getOwner() instanceof Player p) {
						ent.teleport(p);
					}
				}
			}
		}
	}

	@EventHandler
	public void onWorldChange(PlayerChangedWorldEvent event)
	{
		// Get entities inside chunk
		List<Entity> ents = event.getFrom().getEntities();

		// Find pets
		for (Entity ent : ents) {
			// Get pets that can teleport with you
			if (ent.getType() == EntityType.WOLF || ent.getType() == EntityType.CAT || ent.getType() == EntityType.PARROT) {
				// Teleport standing pets
				Sittable sit = (Sittable) ent;
				if (!sit.isSitting()) {
					Tameable tame = (Tameable) ent;

					// But only if the player is online!
					if (tame.getOwner() instanceof Player p) {
						ent.teleport(p);
						ent.setPortalCooldown(300); // Don't want pet to go through nether portal instantly
					}
				}
			}
		}
	}
}
