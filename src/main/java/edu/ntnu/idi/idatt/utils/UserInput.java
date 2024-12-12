package edu.ntnu.idi.idatt.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Util class to hande user input.
 *
 * @author Iver Lindholm
 * @version v2.0
 * @since v1.2
 */
public class UserInput {
  static Scanner scanner = new Scanner(System.in);

  /**
   * Private constructor to prevent instantiation.
   *
   * @throws UnsupportedOperationException if utility class cannot be instantiated.
   */
  private UserInput() {
    throw new UnsupportedOperationException("Utility class - cannot be instantiated");
  }

  /**
   * Uses java scanner to take an integer input.
   *
   * <p>Only returns when the input does not throw
   * exceptions and passes input validation</p>
   *
   * @return a {@link Integer}
   * @throws IllegalArgumentException if input is invalid
   */
  public static int intInput() {
    while (true) {
      try {
        int input = scanner.nextInt();
        scanner.nextLine();
        InputValidation.isValidInteger(input);
        return input;
      } catch (InputMismatchException e) {
        System.out.println("Invalid input! Please enter a valid number.");
        scanner.nextLine();
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  /**
   * Uses java scanner to take a string input for name.
   *
   * <p>Only returns when the input does not throw
   * exceptions and passes input validation. Has to only be
   * letters and not be to long</p>
   *
   * @return a {@link String}
   * @throws IllegalArgumentException if input is invalid
   */
  public static String nameInput() {
    while (true) {
      try {
        String input = scanner.nextLine();
        InputValidation.isNotEmpty(input);
        InputValidation.isValidString(input);
        InputValidation.nameOver32Char(input);
        input = input.trim();
        return input;
      } catch (IllegalArgumentException e) {
        System.err.println(e.getMessage());
        System.err.println("Please try again!");
      }
    }
  }

  /**
   * Uses java scanner to take a string input for quantity and unit.
   *
   * <p>Only returns when the input does not throw
   * exceptions and passes input validation. Has to be both
   * a float value and a string.</p>
   *
   * @return a {@link Object} with a float value and a string value as elements
   * @throws IllegalArgumentException if input is invalid
   */
  public static Object[] unitAndQuantityInput() {
    while (true) {
      try {
        String input = scanner.nextLine();
        return InputValidation.unitConversion(input);
      } catch (IllegalArgumentException e) {
        System.err.println(e.getMessage());
      }
    }
  }

  /**
   * Uses java scanner to take a string input.
   *
   * <p>Only returns when the input does not throw
   * exceptions and passes input validation</p>
   *
   * @return a {@link String}
   * @throws IllegalArgumentException if input is invalid
   */
  public static String stringInput() {
    while (true) {
      try {
        String input = scanner.nextLine();
        InputValidation.isNotEmpty(input);
        return input;
      } catch (IllegalArgumentException e) {
        System.err.println(e.getMessage());
      }
    }
  }

  /**
   * Uses java scanner to take a float input.
   *
   * <p>Only returns when the input does not throw
   * exceptions and passes input validation</p>
   *
   * @return a {@link Float}
   * @throws IllegalArgumentException if input is invalid
   */
  public static float floatInput() {
    while (true) {
      try {
        float input = scanner.nextFloat();
        scanner.nextLine();
        InputValidation.isValidFloat(input);
        return input;
      } catch (InputMismatchException e) {
        System.out.println("Invalid input! Please enter a valid floating point number.");
        scanner.nextLine();
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  /**
   * Uses java scanner to take a string input and parses it to {@link LocalDate}.
   *
   * <p>Only returns when the input does not throw
   * exceptions and passes input validation</p>
   *
   * @return a {@link LocalDate} specified from user input
   * @throws IllegalArgumentException if input is invalid
   */
  public static LocalDate dateInput() {
    while (true) {
      try {
        String input = scanner.nextLine();
        return LocalDate.parse(input, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
      } catch (DateTimeParseException e) {
        System.err.println("Invalid date format. Please try again with the format dd.MM.yyyy!");
      }
    }
  }

  /**
   * Checks if user presses the enter key.
   */
  public static void enterKeyPress() {
    scanner.nextLine();
  }
}
