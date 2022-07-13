package io.github.alexwastakenn.antichatspam.listener;

import io.github.alexwastakenn.antichatspam.AntiChatSpam;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class PlayerActionListener implements Listener {

  private final HashMap<UUID, Integer> messageTracker = new HashMap<>();
  private final HashMap<UUID, Integer> mutedPlayers = new HashMap<>();

  public void PlayerSentMessageEvent(AsyncChatEvent event) {
    /*
     * Voids if the plugin is disabled.
     */
    if (!AntiChatSpam.isPluginEnabled()) {
      return;
    }

    /*
     * Anonymous async method to run on the event fire.
     * Gets the playerUniqueId.
     * Voids if the player has bypass permissions.
     * If the messageTracker map does not contain the playerUniqueId, put the new playerUniqueId into the messageTracker map.
     * If the messageTracker map contains the playerUniqueId, increment its value until it reaches integer 3.
     * Then, add the playerUniqueId to the mutedPlayer map and
     * remove the playerUniqueId from the messageTracker map and cancel the event.
     */
    new Thread(() -> {
      @NotNull Player player = event.getPlayer();
      @NotNull UUID playerUniqueId = player.getUniqueId();
      if (player.isOp()) {
          return;
      }

      //TODO Timer
      if (mutedPlayers.containsKey(playerUniqueId)) {
        event.setCancelled(true);
      }

      if (!messageTracker.containsKey(playerUniqueId)) {
        messageTracker.put(playerUniqueId, 1);
      }

      if (messageTracker.containsKey(playerUniqueId) && messageTracker.get(playerUniqueId) != 3) {
        messageTracker.merge(playerUniqueId, messageTracker.get(playerUniqueId), (x, y) -> x + 1);
      }

      if (messageTracker.get(playerUniqueId) == 3) {
        mutedPlayers.put(playerUniqueId, 5);
        messageTracker.remove(playerUniqueId);
        event.setCancelled(true);
        }
    });
  }
}
