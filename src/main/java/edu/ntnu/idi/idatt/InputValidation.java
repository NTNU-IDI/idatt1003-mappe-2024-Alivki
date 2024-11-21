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
    float quantityInput = 0;
    String unitInput = "";

    Pattern pattern = Pattern.compile("([0-9. ]+)([a-zA-Z]+)");
    Matcher splitInput = pattern.matcher(input);

    while (splitInput.find()) {
      if (splitInput.group(1) != null) {
        quantityInput = Float.parseFloat(splitInput.group(1));
        isValidFloat(quantityInput);
      } else {
        throw new IllegalArgumentException("Has to have a quantity and can not be empty!");
      }
      if (splitInput.group(2) != null) {
        unitInput = splitInput.group(2);
      } else {
        throw new IllegalArgumentException("Has to have unit and can not be empty!");
      }
    }

    if (weightConversionFactors.containsKey(unitInput.toLowerCase())) {
      quantityInput = convertWeight(quantityInput, unitInput);
      unitInput = "kg";
    }
    if (volumeConversionFactors.containsKey(unitInput.toLowerCase())) {
      quantityInput = convertVolume(quantityInput, unitInput);
      unitInput = "l";
    }
    if (unitInput.equalsIgnoreCase("stk")) {
      quantityInput = Math.round(quantityInput);
    }

    return new Object[] {quantityInput, unitInput};
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
