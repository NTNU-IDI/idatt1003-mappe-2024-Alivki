package edu.ntnu.idi.idatt.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * .
 */
public class UserInput {
  static Scanner scanner = new Scanner(System.in);

  /**
   * Private constructor to prevent instantiation.
   */
  private UserInput() {
    throw new UnsupportedOperationException("Utility class - cannot be instantiated");
  }

  /**
   * .
   *
   * @return test
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
   * .
   *
   * @return test
   */
  public static String nameInput() {
    while (true) {
      try {
        String input = scanner.nextLine();
        InputValidation.isNotEmpty(input);
        InputValidation.isValidString(input);
        InputValidation.nameUnder32Char(input);
        input = input.trim();
        return input;
      } catch (IllegalArgumentException e) {
        System.err.println(e.getMessage());
        System.err.println("Please try again!");
      }
    }
  }

  /**
   * .
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
   * .
   *
   * @return string
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
   * .
   *
   * @return string
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
   * .
   *
   * @return string
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
   * .
   */
  public static void enterKeyPress() {
    scanner.nextLine();
  }
}
