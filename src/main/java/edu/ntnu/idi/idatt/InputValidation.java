package edu.ntnu.idi.idatt;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * .
 */
public class InputValidation {
  public static final Map<String, Float> weightConversionFactors = new HashMap<>();
  public static final Map<String, Float> volumeConversionFactors = new HashMap<>();

  static {
    weightConversionFactors.put("g", 0.001f);
    weightConversionFactors.put("kg", 1.0f);

    volumeConversionFactors.put("ml", 0.001f);
    volumeConversionFactors.put("ds", 0.01f);
    volumeConversionFactors.put("cn", 0.1f);
    volumeConversionFactors.put("l", 1.0f);
  }

  /**
   * .
   *
   * @param input test
   * @return test
   */
  public static Object[] unitConversion(String input) {
    Object[] parsedInput = parseInput(input);

    float quantity = (Float) parsedInput[0];
    String unit = (String) parsedInput[1];

    if (weightConversionFactors.containsKey(unit.toLowerCase())) {
      quantity = convertWeight(quantity, unit);
      unit = "kg";
    } else if (volumeConversionFactors.containsKey(unit.toLowerCase())) {
      quantity = convertVolume(quantity, unit);
      unit = "l";
    } else if (unit.equalsIgnoreCase("stk")) {
      quantity = Math.round(quantity);
    }

    return new Object[] {quantity, unit};
  }

  /**
   * .
   *
   * @param input test
   * @return test
   */
  public static Object[] parseInput(String input) {
    if (input == null || input.trim().isEmpty()) {
      throw new IllegalArgumentException("Input for quantity and unit cannot be null or empty");
    }

    Pattern pattern = Pattern.compile("^\\s*([+-]?\\d*\\.?\\d+)\\s*([a-zA-Z]+)\\s*$");
    Matcher matcher = pattern.matcher(input);

    if (matcher.matches()) {
      String numberPart = matcher.group(1);
      String textPart = matcher.group(2);

      try {
        float number = Float.parseFloat(numberPart);
        return new Object[] {number, textPart};
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Invalid number format: " + numberPart);
      }
    } else {
      throw new IllegalArgumentException("Input must contain a numeric value and a text unit.");
    }
  }

  /**
   * .
   *
   * @param value test
   * @param unit  test
   * @return test
   */
  private static float convertWeight(float value, String unit) {
    float factor = weightConversionFactors.get(unit.toLowerCase());

    if (factor == 0) {
      throw new IllegalArgumentException("Unsupported weight unit!" + unit);
    }

    return factor * value;
  }

  /**
   * .
   *
   * @param value test
   * @param unit  test
   * @return test
   */
  private static float convertVolume(float value, String unit) {
    float factor = volumeConversionFactors.get(unit.toLowerCase());

    if (factor == 0) {
      throw new IllegalArgumentException("Unsupported volum unit!");
    }

    return factor * value;
  }

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
   */
  public static void isValidString(String input) {
    if (!input.matches("^[a-zA-Z0!@#$%^&*()_+={};':|,.<>/?øæå ]+$")) {
      throw new IllegalArgumentException("The input string can only contain characters");
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
   * @param input int
   * @throws IllegalArgumentException If input is negative or over 10000.
   */
  public static void isValidInteger(int input) {
    if (input < 0 || input > 100) {
      throw new IllegalArgumentException("Servings has to be between 0 and 100");
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
      //  throw new IllegalArgumentException("Expiration date has gone out. Date is in the past.");
    }
  }
}
