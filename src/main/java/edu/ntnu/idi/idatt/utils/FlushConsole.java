package edu.ntnu.idi.idatt.utils;

/**
 * .
 */
public class FlushConsole {

  /**
   * Clears the console.
   */
  public static void clearConsole() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }
}
