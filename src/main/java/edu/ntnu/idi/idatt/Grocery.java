package edu.ntnu.idi.idatt;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Represents a grocery item with its details and operations.
 */
public class Grocery {
  private final String name;
  private final String unit;
  private final float price;
  private final LocalDate expirationDate;
  private float quantity;

  /**
   * Constructs a new Grocery with all specified details.
   *
   * @param name           The name of the grocery item.
   * @param unit           Unit of measurement for the grocery.
   * @param price          Price per unit of the grocery.
   *                       Float because we need precision when measuring price per unit.
   * @param expirationDate Expiration date of the grocery in.
   *                       LocalDate is immutable, useful methods for date manipulation,
   *                       Only day, month and year variables.
   * @param quantity       Total quantity of the grocery.
   *                       Float because we need precision when measuring the grocery.
   */
  public Grocery(String name, String unit, float price, String expirationDate, float quantity)
      throws IllegalArgumentException {
    LocalDate parsedDateInput =
        LocalDate.parse(expirationDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    InputValidation.isValidDate(parsedDateInput);

    InputValidation.isNotEmpty(name);
    InputValidation.isNotEmpty(unit);
    InputValidation.isNotEmpty(expirationDate);

    InputValidation.nameUnder32Char(name);

    InputValidation.isValidFloat(price);
    InputValidation.isValidFloat(quantity);

    this.name = name;
    this.unit = unit.toUpperCase();
    this.price = price;

    this.quantity = roundIfStk(quantity, unit);

    this.expirationDate = parsedDateInput;
  }

  /**
   * Gets the name of the grocery.
   *
   * @return The name.
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the unit of specified grocery.
   *
   * @return The unit of measurement.
   */
  public String getUnit() {
    return unit;
  }

  /**
   * Gets the price of the grocery per unit.
   *
   * @return The price per unit.
   */
  public float getPrice() {
    return price;
  }

  /**
   * Gets the total quantity stored of specified grocery.
   *
   * @return The quantity.
   */
  public float getQuantity() {
    return quantity;
  }

  /**
   * Gets the expiration date.
   *
   * @return the expiration date.
   */
  public LocalDate getExpirationDate() {
    return expirationDate;
  }

  /**
   * Sets the new total quantity stored of specific grocery.
   *
   * @param quantity How much of a grocery is being added.
   * @throws IllegalArgumentException If the quantity is negative or above 1000.
   */
  public void increaseQuantity(float quantity) throws IllegalArgumentException {
    InputValidation.isValidFloat(quantity);

    this.quantity += roundIfStk(quantity, unit);
  }

  /**
   * Sets the new total quantity stored of specific grocery.
   *
   * @param quantity How much of a grocery is being removed.
   * @throws IllegalArgumentException If the quantity is negative or above 1000.
   */
  public void decreaseQuantity(float quantity) throws IllegalArgumentException {
    InputValidation.isValidFloat(quantity);

    this.quantity -= roundIfStk(quantity, unit);
  }

  /**
   * Returns a string representation of the grocery.
   * Using StringBuilder for efficient string concatenation.
   * Formating the date to a more readable format with DateTimeFormatter.
   *
   * @return A string containing the name, quantity, unit and expiration date.
   */
  @Override
  public String toString() {
    String outputName = this.name;
    if (this.name.length() > 10) {
      outputName = name.substring(0, 10) + "..";
    }

    String priceUnit = String.format("%.2f%s", this.quantity, this.unit);

    return String.format("| %-12s | %-8s  | %.2fkr        | %s       |%n", outputName, priceUnit,
        this.price, this.expirationDate);
  }

  /**
   * .
   *
   * @param obj input grocery from the user
   * @return boolean
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    Grocery grocery = (Grocery) obj;

    return Objects.equals(name, grocery.name);
  }

  /**
   * .
   *
   * @return int hashcode
   */
  @Override
  public int hashCode() {
    return name.hashCode();
  }

  /**
   * .
   */
  private float roundIfStk(float quantity, String unit) {
    if (unit.equalsIgnoreCase("stk")) {
      return Math.round(quantity);
    } else {
      return quantity;
    }
  }
}
