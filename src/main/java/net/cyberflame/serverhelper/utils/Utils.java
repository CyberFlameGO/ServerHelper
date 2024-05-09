package net.cyberflame.serverhelper.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sittable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

import static net.cyberflame.serverhelper.ServerHelperPlugin.getDeferredPets;
import static net.kyori.adventure.text.Component.text;

public class Utils
{
	// If I need to use more components than just text, I'll make this method accept "Component..." or whatever the
	// actual name of it is, so it can accept multiple components.
	public static Component component(String string)
	{
		return text().append(LegacyComponentSerializer.legacy('&').deserialize(string)).build();
	}

	@SuppressWarnings("deprecation")
	private static String toMinecraftChatColor(String text)
	{
		return ChatColor.translateAlternateColorCodes('&', text);
	}

	public static void sendMessage(CommandSender sender, String message)
	{
		sender.sendMessage(toMinecraftChatColor(message));
	}

	public static boolean notPet(EntityType compare)
	{
		return compare != EntityType.WOLF &&
				compare != EntityType.CAT &&
				compare != EntityType.PARROT;
	}

	public static boolean isPetAnchored(Entity entity) {
		return ((Sittable) entity).isSitting() || entity.isInsideVehicle();
	}

	public static boolean shouldDeferPetTeleport(Player p) {
		return p.isGliding() || p.isSwimming() || p.isInsideVehicle();
	}

	public static Location getPlayerSpawn(Player p) {
		return Objects.requireNonNullElse(p.getRespawnLocation(), p.getWorld().getSpawnLocation());
	}

	public static void teleportIfInMap(Player p) {
		HashMap deferredPets = getDeferredPets();
		if (deferredPets.containsKey(p.getUniqueId())) {
			HashSet<Entity> pets = (HashSet<Entity>) deferredPets.get(p.getUniqueId());
			for (Entity pet : pets) {
				pet.teleport(p);
			}
			deferredPets.remove(p.getUniqueId());
		}
	}
}
