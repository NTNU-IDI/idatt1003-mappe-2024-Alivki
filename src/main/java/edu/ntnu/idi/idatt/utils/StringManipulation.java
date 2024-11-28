package edu.ntnu.idi.idatt.utils;

/**
 * .
 */
public class StringManipulation {

  /**
   * Private constructor to prevent instantiation.
   */
  private StringManipulation() {
    throw new UnsupportedOperationException("Utility class - cannot be instantiated");
  }

  /**
   * .
   *
   * @param inputName awd
   * @return awd
   */
  public static String shortenString(String inputName, int shortenFrom) {
    return inputName.substring(0, shortenFrom) + "..";
  }

  /**
   * .
   *
   * @param inputString test
   * @param rowLength   tet
   * @return test
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
