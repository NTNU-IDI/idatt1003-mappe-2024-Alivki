package edu.ntnu.idi.idatt;

/**
 * .
 */
public class InputValidation {
  /**
   * .
   *
   * @param input string
   * @return boolean
   */
  public boolean isNotEmpty(String input) {
    return input != null && !input.trim().isEmpty();
  }

  /**
   * .
   *
   * @param input string
   * @return boolean
   */
  public boolean isValidInteger(String input) {
    try {
      float value = Float.parseFloat(input);
      return value > 0;
    } catch (NumberFormatException e) {
      return false;
    }
  }
}
