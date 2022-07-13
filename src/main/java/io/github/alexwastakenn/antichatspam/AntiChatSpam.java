package io.github.alexwastakenn.antichatspam;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import io.github.alexwastakenn.antichatspam.listener.PlayerActionListener;
import org.jetbrains.annotations.NotNull;

public final class AntiChatSpam extends JavaPlugin {

  /*
   * Status of the plugin, to check if it's disabled or enabled.
   */
  private static boolean acsEnabled = true;
  private final PluginManager manager = getServer().getPluginManager();

  @Override
  public void onEnable() {
    try {
      manager.registerEvents(new @NotNull PlayerActionListener(), this);

      System.out.println("[AntiChatSpam] Plugin loaded. If any issues arise contact alex#6890 @ discord.");
    } catch (Exception e) {
      System.out.println("[AntiChatSpam] Plugin failed to load due to an error.\nPlease contact alex#6890 @ discord.");
    }
  }

  @Override
  public void onDisable() {
    System.out.println("[AntiChatSpam] Plugin unloaded. Either the server crashed or it was shut down");
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
