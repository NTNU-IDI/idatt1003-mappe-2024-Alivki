package edu.ntnu.idi.idatt;

import java.time.LocalDate;

/**
 * .
 */
public class InputValidation {

  /**
   * .
   *
   * @param input string
   * @throws IllegalArgumentException If string parameters is null.
   */
  public static void isNotEmpty(String input) throws IllegalArgumentException {
    if (input == null || input.isEmpty() || input.trim().isEmpty()) {
      throw new IllegalArgumentException("Grocery info can not be empty!");
    }
  }

  /**
   * .
   *
   * @param input float
   * @throws IllegalArgumentException If input is negative or over 10000.
   */
  public static void isValidFloat(Float input) {
    if (input < 0 || input > 10000) {
      throw new IllegalArgumentException("Price or quantity has to be between 0 and 10000");
    }
  }

  /**
   * .
   *
   * @param input string
   * @throws IllegalArgumentException If name is over 32 characters long.
   */
  public static void nameUnder32Char(String input) {
    if (input.length() > 32) {
      throw new IllegalArgumentException("The name has to be under 32 character!");
    }
  }

  /**
   * .
   *
   * @param input local date
   * @throws IllegalArgumentException If expiration date is in the past.
   */
  public static void isValidDate(LocalDate input) {
    if (input.isBefore(LocalDate.now())) {
      throw new IllegalArgumentException("Expiration date has gone out. Date is in the past.");
    }
  }
}
