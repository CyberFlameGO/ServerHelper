package net.cyberflame.serverhelper.listeners;

import com.gmail.nossr50.events.experience.McMMOPlayerLevelUpEvent;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class McMMOPlayerLevelUpListener implements Listener {
  @EventHandler
  public void onMcMMOPlayerLevelUp(McMMOPlayerLevelUpEvent event) {
    Player player = event.getPlayer();
    int bonusClaimBlocks =
        GriefPrevention.instance
            .dataStore
            .getPlayerData(player.getUniqueId())
            .getBonusClaimBlocks();
    int skillLevel = event.getSkillLevel();
    if ((skillLevel % 50 == 0) && (bonusClaimBlocks < 20000))
      GriefPrevention.instance.dataStore.adjustGroupBonusBlocks(
          String.valueOf(player.getUniqueId()), bonusClaimBlocks + 100);
  }
}
