package edu.ntnu.idi.idatt.utils;

/**
 * Util class to flush the console.
 *
 * @author Iver Lindholm
 * @version v2.0
 * @since v1.2
 */
public class FlushConsole {

  /**
   * Private constructor to prevent instantiation.
   *
   * @throws UnsupportedOperationException if utility class cannot be instantiated.
   */
  private FlushConsole() throws UnsupportedOperationException {
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
