package io.github.alexwastakenn.antichatspam;

import io.github.alexwastakenn.antichatspam.commands.AntiChatSpamCommand;
import io.github.alexwastakenn.antichatspam.events.AntiChatSpamEvents;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Objects;
import java.util.logging.Level;

public final class AntiChatSpam extends JavaPlugin {

  private static boolean acsEnabled = true;

  @Override
  public void onEnable() {
    saveDefaultConfig();
    Objects.requireNonNull(getCommand("antichatspam")).setExecutor(new AntiChatSpamCommand());
    Bukkit.getServer().getPluginManager().registerEvents(new AntiChatSpamEvents(), this);
    this.getLogger()
        .log(
            Level.INFO,
            "[AntiChatSpam] Plugin loaded. If any issues arise contact alex#6890 @ discord.");
  }

  @Override
  public void onDisable() {
    this.getLogger()
        .log(
            Level.SEVERE,
            "[AntiChatSpam] Plugin unloaded. Either the server crashed or it was shut down");
  }

  /*
   * Returns the status of the plugin. Either true for enabled or false for disabled.
   */
  public static boolean isPluginEnabled() {
    return acsEnabled;
  }

  /*
   * Sets the status of the plugin. Either true for enabled or false for disabled.
   */
  public static void enablePlugin(boolean enable) {
    acsEnabled = enable;
  }
}
