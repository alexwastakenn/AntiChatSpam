package io.github.alexwastakenn.antichatspam.listener;

import io.github.alexwastakenn.antichatspam.AntiChatSpam;
import io.github.alexwastakenn.antichatspam.messages.AntiChatSpamMessages;
import io.github.alexwastakenn.antichatspam.object.Tracker;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;
import java.text.DecimalFormat;
import java.util.concurrent.ConcurrentHashMap;

public class AntiChatSpamListener implements Listener, AntiChatSpamMessages {

  private final ConcurrentHashMap<Player, Tracker> messageTracker = new ConcurrentHashMap<>();
  private final ConcurrentHashMap<Player, Integer> mutedPlayers = new ConcurrentHashMap<>();

  private final int muteAtNumberOfMessages = 3;
  private final int flushMessageTrackerInSeconds = 2;
  private final int unmutePlayerInSeconds = 5;


  private static final DecimalFormat decimalFormat = new DecimalFormat("0.0");

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
    Thread asyncMessageSent = new Thread(() -> {
      /*
       * Gets the player.
       */
      @NotNull Player player = event.getPlayer();
      /*
       * Voids if the player has bypass permissions.
       */
      if (player.isOp()) {
          return;
      }

      //TODO mutedPlayers map conditions, improve comments

      /*
       * If a player key does not exist,
       * creates a new player key with Tracker value.
       * The Tracker object already initializes with numberOfMessages = 1,
       * it is only needed to provide an integer which holds the value
       * of a tick when the player was put into the messageTracker map.
       */
      if (!messageTracker.containsKey(player)) {
        messageTracker.put(player, new Tracker(Bukkit.getServer().getCurrentTick() + (flushMessageTrackerInSeconds * 20)));
      }

      /*
       * If the getTrackerPerishAtTick tick is smaller or equal to the current tick of the server,
       * remove the player key from the messageTracker map.
       */
      if (messageTracker.get(player).getTrackerPerishAtTick() <= Bukkit.getServer().getCurrentTick()) {
        messageTracker.remove(player);
      }

      /*
       * If the numberOfMessages of player key are not equal to muteAtNumberOfMessages value,
       * increment the numberOfMessages for player key.
       */
      if (messageTracker.get(player).getNumberOfMessages() != muteAtNumberOfMessages) {
        messageTracker.get(player).incrementNumberOfMessages();
      }

      /*
       * If the numberOfMessage of player key is equal to muteAtNumberOfMessages value,
       * put the player key with the current tick of the server into mutedPlayers map.
       * Then, send a message to the player that they have been muted,
       * cancel their AsyncChatEvent and remove them from the messageTracker map.
       */
      if (messageTracker.get(player).getNumberOfMessages() == muteAtNumberOfMessages) {
        mutedPlayers.put(player, Bukkit.getServer().getCurrentTick());
        mutedMessage(player);
        event.setCancelled(true);
        messageTracker.remove(player);
      }
    });
    asyncMessageSent.start();
  }

  public void PlayerQuitServer(PlayerQuitEvent event) {
    Thread playerQuitServer = new Thread(() -> {
      @NotNull Player player = event.getPlayer();

      if (messageTracker.containsKey(player)) {
        messageTracker.remove(player);
      }

      if (mutedPlayers.containsKey(player)) {
        mutedPlayers.remove(player);
      }
    });
  }
}
