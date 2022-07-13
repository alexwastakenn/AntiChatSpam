package io.github.alexwastakenn.antichatspam.commands;

import io.github.alexwastakenn.antichatspam.AntiChatSpam;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AntiChatSpamCommand implements CommandExecutor {

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    /*
     * Cancels the command if the sender isn't a player.
     */
    if (!(sender instanceof Player player)) {
      return true;
    }

    /*
     * Cancels the command if there are no arguments,
     * returns an info message to the player.
     */
    if (args.length == 0) {
      return true;
    }

    /*
     * Cancels the command if there's more than one argument,
     * returns a custom usage message to the player.
     */
    if (args.length != 1) {
      player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "[AntiChatSpam]" + ChatColor.RESET + " " + ChatColor.GREEN + " Too many arguments.");
      return true;
    }

    /*
     * Cancels the command if the arguments are not either "on" for true or "off" for false.
     * Initializes boolean variable "enabled" for further use.
     */
    boolean enabled;
    if (args[0].equals("on")) {
      enabled = true;
    } else if (args[0].equals("off")) {
      enabled = false;
    } else {
      player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "[AntiChatSpam]" + ChatColor.RESET + " "
          + ChatColor.GREEN + " Invalid arguments, please use either on or off");
      return true;
    }

    /*
     * If the plugin is already enabled or disabled,
     * returns a message to the player about the status,
     * otherwise it enables or disables the plugin.
     */
    if (AntiChatSpam.isPluginEnabled()) {
      if (enabled) {
        player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "[AntiChatSpam]" + ChatColor.RESET + " " + ChatColor.GREEN + " The plugin is already enabled.");
      } else {
        AntiChatSpam.enablePlugin(false);
        player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "[AntiChatSpam]" + ChatColor.RESET + " " + ChatColor.GREEN + " The plugin has been disabled.");
      }
    } else {
      if (!enabled) {
        player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "[AntiChatSpam]" + ChatColor.RESET + " " + ChatColor.GREEN + " The plugin is already disabled.");
      } else {
        AntiChatSpam.enablePlugin(true);
        player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "[AntiChatSpam]" + ChatColor.RESET + " " + ChatColor.GREEN + " The plugin has been enabled.");
      }
    }
    return true;
  }
}
