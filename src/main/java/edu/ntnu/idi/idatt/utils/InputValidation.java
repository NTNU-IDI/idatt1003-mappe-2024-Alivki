package edu.ntnu.idi.idatt.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Util class for input validation and conversion between units.
 *
 * @author Iver Lindholm
 * @version v2.0
 * @since v1.1
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
   * Private constructor to prevent instantiation.
   *
   * @throws UnsupportedOperationException if utility class cannot be instantiated.
   */
  private InputValidation() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("This class cannot be instantiated");
  }

  /**
   * Converts the input to the correct unit.
   *
   * @param input user string input form grocery quantity and unit
   * @return a {@link Object} with the converted quantity and the new unit.
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
   * Split the user input into a quantity and a unit.
   *
   * @param input string input
   * @return a {@link Object} with quantity as first element and unit as the second.
   * @throws IllegalArgumentException if the input is in the wrong form
   */
  public static Object[] parseInput(String input) throws IllegalArgumentException {
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
   * Does the conversion of the quantity for weight.
   *
   * @param value quantity input
   * @param unit  unit input
   * @return a {@link Float} converted quantity.
   */
  private static float convertWeight(float value, String unit) {
    float factor = weightConversionFactors.get(unit.toLowerCase());

    if (factor == 0) {
      throw new IllegalArgumentException("Unsupported weight unit!" + unit);
    }

    return factor * value;
  }

  /**
   * Does the quantity conversion for volume.
   *
   * @param value quantity input
   * @param unit  unit input
   * @return a {@link Float} converted quantity.
   */
  private static float convertVolume(float value, String unit) {
    float factor = volumeConversionFactors.get(unit.toLowerCase());

    if (factor == 0) {
      throw new IllegalArgumentException("Unsupported volume unit!");
    }

    return factor * value;
  }

  /**
   * Checks if the input is empty or null.
   *
   * @param input string input
   * @throws IllegalArgumentException If string parameters is null or empty.
   */
  public static void isNotEmpty(String input) throws IllegalArgumentException {
    if (input == null || input.isEmpty() || input.trim().isEmpty()) {
      throw new IllegalArgumentException("Grocery info can not be empty!");
    }
  }

  /**
   * Checks if it is a valid string.
   *
   * @param input string input
   * @throws IllegalArgumentException If string contains illegal characters.
   */
  public static void isValidString(String input) throws IllegalArgumentException {
    if (!input.matches("^[a-zA-Z0!@#$%^&*()_+={};':|,.<>/?øæå]+$")) {
      throw new IllegalArgumentException("The input string can only contain characters");
    }
  }

  /**
   * Checks if input is valid float between set numbers.
   *
   * @param input float input
   * @throws IllegalArgumentException If input is negative or over 10000.
   */
  public static void isValidFloat(Float input) throws IllegalArgumentException {
    if (input < 0 || input > 10000) {
      throw new IllegalArgumentException("Price or quantity has to be between 0 and 10000");
    }
  }

  /**
   * Checks if input is a valid integer between set numbers.
   *
   * @param input integer input
   * @throws IllegalArgumentException If input is negative or over 10000.
   */
  public static void isValidInteger(int input) {
    if (input < 0 || input > 100) {
      throw new IllegalArgumentException("Integer input has to be between 0 and 100");
    }
  }

  /**
   * Checks if input string is more then 32 characters long.
   *
   * @param input input string
   * @throws IllegalArgumentException If name is over 32 characters long.
   */
  public static void nameOver32Char(String input) throws IllegalArgumentException {
    if (input.length() > 32) {
      throw new IllegalArgumentException("The name has to be under 32 character!");
    }
  }
}
