package io.github.alexwastakenn.antichatspam.commands;

import io.github.alexwastakenn.antichatspam.AntiChatSpam;
import io.github.alexwastakenn.antichatspam.messages.AntiChatSpamMessages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AntiChatSpamCommand implements CommandExecutor, AntiChatSpamMessages {

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

    if (!(sender instanceof Player player)) {
      return true;
    }

    if (args.length == 0) {
      return true;
    }

    if (args.length != 1) {
      tooManyArgumentsMessage(player);
      return true;
    }

    boolean enabled;
    if (args[0].equals("on")) {
      enabled = true;
    } else if (args[0].equals("off")) {
      enabled = false;
    } else {
      invalidArgumentsMessage(player);
      return true;
    }

    if (AntiChatSpam.isPluginEnabled()) {
      if (enabled) {
        alreadyEnabledMessage(player);
      } else {
        AntiChatSpam.enablePlugin(false);
        hasBeenDisabledMessage(player, true);
      }
    } else {
      if (!enabled) {
        alreadyDisabledMessage(player);
      } else {
        AntiChatSpam.enablePlugin(true);
        hasBeenEnabledMessage(player, true);
      }
    }
    return true;
  }
}
