package io.github.alexwastakenn.antichatspam.objects;

import lombok.Getter;

public final class Tracker {
  @Getter private int numberOfMessages;
  @Getter private final int trackerPerishAtTick;

  public Tracker(int tick) {
    this.numberOfMessages = 1;
    this.trackerPerishAtTick = tick;
  }

  public void incrementNumberOfMessages() {
    this.numberOfMessages++;
  }
}
