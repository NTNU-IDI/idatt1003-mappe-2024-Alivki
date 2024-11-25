package edu.ntnu.idi.idatt;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * .
 */
public class Utils {
  static Scanner scanner = new Scanner(System.in);


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
