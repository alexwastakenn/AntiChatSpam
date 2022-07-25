package io.github.alexwastakenn.antichatspam.messages;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

public interface AntiChatSpamMessages {

  @NotNull
  String prefix = ChatColor.GREEN + "" + ChatColor.BOLD + "[AntiChatSpam]" + ChatColor.RESET + "";

  @NotNull String consolePrefix = "[AntiChatSpam]";

  @NotNull DecimalFormat decimalFormat = new DecimalFormat("0.0");

  default void tooManyArgumentsMessage(@NotNull Player player) {
    player.sendMessage(prefix + ChatColor.GREEN + " Too many arguments.");
  }

  default void invalidArgumentsMessage(@NotNull Player player) {
    player.sendMessage(
        prefix + ChatColor.GREEN + " Invalid arguments, please use either on or off.");
  }

  default void alreadyEnabledMessage(@NotNull Player player) {
    player.sendMessage(prefix + ChatColor.GREEN + " The plugin is already enabled.");
  }

  default void alreadyDisabledMessage(@NotNull Player player) {
    player.sendMessage(prefix + ChatColor.GREEN + " The plugin is already disabled.");
  }

  default void hasBeenEnabledMessage(@NotNull Player player, boolean toConsoleAsWell) {
    player.sendMessage(prefix + ChatColor.GREEN + " The plugin has been enabled.");
    if (toConsoleAsWell) {
      System.out.println(
          consolePrefix + " The plugin has been enabled in-game by " + player.getName() + ".");
    }
  }

  default void hasBeenDisabledMessage(@NotNull Player player, boolean toConsoleAsWell) {
    player.sendMessage(prefix + ChatColor.GREEN + " The plugin has been disabled.");
    if (toConsoleAsWell) {
      System.out.println(
          consolePrefix + " The plugin has been disabled in-game by " + player.getName() + ".");
    }
  }

  default void mutedMessage(@NotNull Player player) {
    player.sendMessage(prefix + ChatColor.GREEN + " You've been muted for 5.0 seconds.");
  }

  default void mutedRemainderMessage(@NotNull Player player, double remainder) {
    player.sendMessage(
        prefix
            + ChatColor.GREEN
            + " You're still muted for "
            + decimalFormat.format(remainder / 20)
            + " seconds.");
  }
}
