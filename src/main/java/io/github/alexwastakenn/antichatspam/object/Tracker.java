package io.github.alexwastakenn.antichatspam.object;

public class Tracker {
  private int numberOfMessages;
  private final int trackerPerishAtTick;

  public Tracker(int tick) {
    this.numberOfMessages = 1;
    this.trackerPerishAtTick = tick;
  }

  public int getNumberOfMessages() {
    return this.numberOfMessages;
  }

  public int getTrackerPerishAtTick() {
    return this.trackerPerishAtTick;
  }

  public void incrementNumberOfMessages() {
    this.numberOfMessages++;
  }
}
