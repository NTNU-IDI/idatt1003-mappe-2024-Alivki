package edu.ntnu.idi.idatt;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * .
 */
public class Utils {
  Scanner scanner = new Scanner(System.in);

  /**
   * .
   *
   * @return string
   */
  public String stingInput() {
    String input = scanner.nextLine();

    InputValidation.isNotEmpty(input);

    return input;
  }

  /**
   * .
   *
   * @return string
   */
  public int intInput() {
    int input = scanner.nextInt();
    scanner.nextLine();

    InputValidation.isValidInteger(input);

    return input;
  }

  /**
   * .
   *
   * @return string
   */
  public float floatInput() {
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
  public LocalDate dateInput() {
    String input = scanner.nextLine();

    return LocalDate.parse(input, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
  }
}
