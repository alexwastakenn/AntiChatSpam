package io.github.alexwastakenn.antichatspam.listener;

import io.github.alexwastakenn.antichatspam.AntiChatSpam;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class AntiChatSpamListener implements Listener {

  private final ConcurrentHashMap<UUID, Integer> messageTracker = new ConcurrentHashMap<>();
  private final ConcurrentHashMap<UUID, Integer> mutedPlayers = new ConcurrentHashMap<>();

  public void PlayerSentMessageEvent(AsyncChatEvent event) {
    /*
     * Voids if the plugin is disabled.
     */
    if (!AntiChatSpam.isPluginEnabled()) {
      return;
    }

    /*
     * Anonymous async method to run on the event fire.
     */
    new Thread(() -> {
      /*
       * Gets the playerUniqueId.
       */
      @NotNull Player player = event.getPlayer();
      @NotNull UUID playerUniqueId = player.getUniqueId();
      /*
       * Voids if the player has bypass permissions.
       */
      if (player.isOp()) {
          return;
      }

      //TODO Timer
      if (mutedPlayers.containsKey(playerUniqueId)) {
        event.setCancelled(true);
      }

      /*
       * If the messageTracker map does not contain the playerUniqueId,
       * put the new playerUniqueId into the messageTracker map.
       */
      if (!messageTracker.containsKey(playerUniqueId)) {
        messageTracker.put(playerUniqueId, 1);
      }

      /*
       * If the messageTracker map contains the playerUniqueId,
       * increment its value until it reaches integer 3.
       */
      if (messageTracker.containsKey(playerUniqueId) && messageTracker.get(playerUniqueId) != 3) {
        messageTracker.merge(playerUniqueId, messageTracker.get(playerUniqueId), (x, y) -> x + 1);
      }

      /*
       * Add the playerUniqueId to the mutedPlayer map,
       * remove the playerUniqueId from the messageTracker map
       * and cancel the event.
       */
      if (messageTracker.get(playerUniqueId) == 3) {
        mutedPlayers.put(playerUniqueId, 5);
        messageTracker.remove(playerUniqueId);
        event.setCancelled(true);
        }
    });
  }
}
