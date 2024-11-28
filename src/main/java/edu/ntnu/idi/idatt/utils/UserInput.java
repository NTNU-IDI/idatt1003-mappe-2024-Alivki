package edu.ntnu.idi.idatt.utils;

import edu.ntnu.idi.idatt.InputValidation;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
   */
  public static int menuNumberSelect() {
    int input = -1;

    try {
      input = scanner.nextInt();
      scanner.nextLine();
    } catch (InputMismatchException e) {
      scanner.nextLine();
      System.err.println("Choose a number!");
    }

    return input;
  }

  /**
   * .
   */
  public static void enterKeyPress() {
    scanner.nextLine();
  }

  /**
   * .
   *
   * @return string
   */
  public static String stringInput() {
    String input = scanner.nextLine();

    InputValidation.isNotEmpty(input);
    InputValidation.isValidString(input);

    return input;
  }

  /**
   * .
   */
  public static String quantityAndUnitInput() {
    String input = scanner.nextLine();

    InputValidation.isNotEmpty(input);

    return input;
  }

  /**
   * .
   *
   * @return string
   */
  public static int intInput() {
    int input;

    try {
      input = scanner.nextInt();
      scanner.nextLine();
    } catch (InputMismatchException e) {
      throw new IllegalArgumentException("The input has to be number!");
    }

    InputValidation.isValidInteger(input);

    return input;
  }

  /**
   * .
   *
   * @return string
   */
  public static float floatInput() {
    float input = scanner.nextFloat();
    scanner.nextLine();

    InputValidation.isValidFloat(input);

    return input;
  }

  /**
   * .
   *
   * @return string
   */
  public static LocalDate dateInput() {
    String input = scanner.nextLine();

    return LocalDate.parse(input, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
  }
}
