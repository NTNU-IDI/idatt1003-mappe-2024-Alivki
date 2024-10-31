package edu.ntnu.idi.idatt;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
      return value > 0 && value < 10000;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  /**
   * .
   *
   * @param input string
   * @return boolean
   */
  public boolean nameUnder32Char(String input) {
    return input.length() <= 32;
  }

  /**
   * .
   *
   * @param input local date
   * @return boolean
   */
  public static boolean parseExpirationDate(String input) {
    try {
      LocalDate parsedDateInput =
          LocalDate.parse(input, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
      if (parsedDateInput.isBefore(LocalDate.now())) {
        throw new IllegalArgumentException("Expiration date has gone out. Date is in the past.");
      }
      return true;
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("Invalid date format. Please use dd.MM.yyyy", e);
    }
  }
}
