package edu.ntnu.idi.idatt.utils;

/**
 * Util class to handle string manipulation.
 *
 * @author Iver Lindholm
 * @version v2.0
 * @since v1.2
 */
public class StringManipulation {

  /**
   * Private constructor to prevent instantiation.
   *
   * @throws UnsupportedOperationException if utility class cannot be instantiated.
   */
  private StringManipulation() {
    throw new UnsupportedOperationException("Utility class - cannot be instantiated");
  }

  /**
   * Shortens a string to a specified length.
   *
   * @param inputName input string
   * @param shortenFrom integer value that decides how long the string should be.
   * @return a {@link String} with the shortened string.
   */
  public static String shortenString(String inputName, int shortenFrom) {
    return inputName.substring(0, shortenFrom) + "..";
  }

  /**
   * Centers a string between | this character.
   *
   * @param inputString string input
   * @param rowLength   how long the row should be
   * @return a {@link String} with the input string in the middle
   */
  public static String centerString(String inputString, int rowLength) {
    int leftPadding;
    int rightPadding;

    if (inputString.length() > rowLength - 4) {
      inputString = StringManipulation.shortenString(inputString, rowLength - 6);
      leftPadding = 1;
      rightPadding = 1;
    } else {
      leftPadding = (rowLength - 2 - inputString.length()) / 2;
      rightPadding =
          (rowLength - 2 - inputString.length()) % 2 == 0 ? leftPadding : leftPadding + 1;
    }

    return String.format("|%" + leftPadding + "s%s%" + rightPadding + "s|%n", "", inputString, "");
  }
}
