package edu.ntnu.idi.idatt.utils;

/**
 * .
 */
public class FlushConsole {

  /**
   * Private constructor to prevent instantiation.
   */
  private FlushConsole() {
    throw new UnsupportedOperationException("Utility class - cannot be instantiated");
  }

  /**
   * Clears the console.
   */
  public static void clearConsole() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }
}
