package io.github.alexwastakenn.antichatspam.configs;

import io.github.alexwastakenn.antichatspam.AntiChatSpam;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class AntiChatSpamConfig {

  public static AntiChatSpam plugin;
  private final ConfigurationSection config =
      Objects.requireNonNull(plugin.getConfig().getConfigurationSection("antichatspam"));

  private final int serverTps = getFromConfig("tickspersecond");

  @Getter private final int messagesUntilPlayerMute = getFromConfig("messagesuntilmute");

  @Getter private final int unmutePlayerInTicks = getFromConfig("unmutein") * serverTps;

  @Getter private final int stopPlayerTrackingInTicks = getFromConfig("stoptrackingin") * serverTps;

  private int getFromConfig(@NotNull String path) {
    assert config.getInt(path) != 0;
    return config.getInt(path);
  }
}
