package io.github.alexwastakenn.antichatspam.events;

import io.github.alexwastakenn.antichatspam.AntiChatSpam;
import io.github.alexwastakenn.antichatspam.configs.AntiChatSpamConfig;
import io.github.alexwastakenn.antichatspam.messages.AntiChatSpamMessages;
import io.github.alexwastakenn.antichatspam.objects.Tracker;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;
import java.util.concurrent.ConcurrentHashMap;

public class AntiChatSpamEvents implements Listener, AntiChatSpamMessages {

  private final ConcurrentHashMap<Player, Tracker> messagesMap = new ConcurrentHashMap<>();
  private final ConcurrentHashMap<Player, Integer> mutedPlayersMap = new ConcurrentHashMap<>();

  public static AntiChatSpam plugin;

  public static AntiChatSpamConfig config;

  @EventHandler
  public void PlayerSentMessage(AsyncChatEvent event) {

    if (!AntiChatSpam.isPluginEnabled()) {
      return;
    }
    @NotNull Player player = event.getPlayer();

    if (player.isOp()) {
      return;
    }

    if (mutedPlayersMap.containsKey(player)) {
      if ((mutedPlayersMap.get(player) - Bukkit.getServer().getCurrentTick()) > 0) {
        mutedRemainderMessage(
            player, mutedPlayersMap.get(player) - Bukkit.getServer().getCurrentTick());
        event.setCancelled(true);
        return;
      } else {
        mutedPlayersMap.remove(player);
      }
    }

    if (!messagesMap.containsKey(player)) {
      messagesMap.put(
          player,
          new Tracker(Bukkit.getServer().getCurrentTick() + config.getStopPlayerTrackingInTicks()));
    }

    Tracker playerTracker = messagesMap.get(player);
    int numberOfMessages = playerTracker.getNumberOfMessages();
    int trackerPerishAtTick = playerTracker.getTrackerPerishAtTick();
    int remainder = trackerPerishAtTick - Bukkit.getServer().getCurrentTick();

    if (remainder < 0) {
      messagesMap.remove(player);
      return;
    }

    if (numberOfMessages != config.getMessagesUntilPlayerMute()) {
      playerTracker.incrementNumberOfMessages();
    }

    if (numberOfMessages == config.getMessagesUntilPlayerMute()) {
      mutedPlayersMap.put(
          player, Bukkit.getServer().getCurrentTick() + config.getUnmutePlayerInTicks());
      mutedMessage(player);
      event.setCancelled(true);
      messagesMap.remove(player);
    }
  }

  @EventHandler
  public void PlayerQuitServer(PlayerQuitEvent event) {
    Runnable playerQuitServer =
        () -> {
          @NotNull Player player = event.getPlayer();

          if (messagesMap.containsKey(player)) {
            messagesMap.remove(player);
          }

          if (mutedPlayersMap.containsKey(player)) {
            mutedPlayersMap.remove(player);
          }
        };
    Bukkit.getScheduler().runTaskAsynchronously(plugin, playerQuitServer);
  }
}
