package edu.ntnu.idi.idatt;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
   * @throws IllegalArgumentException If string parameters is null.
   * @throws IllegalArgumentException If float values are negative.
   * @throws IllegalArgumentException If name is over 32 characters long.
   * @throws IllegalArgumentException If price is above 10000.
   * @throws IllegalArgumentException If quantity is above 1000.
   * @throws IllegalArgumentException If try block throws DateTimeParseException, wrong format.
   * @throws IllegalArgumentException If expiration date is in the past.
   */
  public Grocery(String name, String unit, float price, String expirationDate, float quantity)
      throws IllegalArgumentException {
    if (name == null || unit == null || expirationDate == null) {
      throw new IllegalArgumentException("Grocery info can not be null");
    }

    if (name.isEmpty() || unit.isEmpty() || expirationDate.isEmpty()) {
      throw new IllegalArgumentException("Grocery info can not be null");
    }

    if (price < 0 || quantity < 0) {
      throw new IllegalArgumentException("The price or quantity can not be negative");
    }

    if (name.length() > 32) {
      throw new IllegalArgumentException("The name can not be above 32 characters");
    }

    if (price > 10000) {
      throw new IllegalArgumentException("The price can not be above 10000 characters");
    }

    if (quantity > 1000) {
      throw new IllegalArgumentException("The quantity can not be above 1000 characters");
    }

    this.name = name;
    this.unit = unit.toUpperCase();
    this.price = price;

    this.quantity = roundIfStk(quantity, unit);

    this.expirationDate = parseDateInput(expirationDate);
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
    if (quantity < 0 || quantity > 1000) {
      throw new IllegalArgumentException("Quantity has to be above 0 or under 1000");
    }

    this.quantity = roundIfStk(quantity, unit);
  }

  /**
   * Sets the new total quantity stored of specific grocery.
   *
   * @param quantity How much of a grocery is being removed.
   * @throws IllegalArgumentException If the quantity is negative or above 1000.
   */
  public void decreaseQuantity(float quantity) throws IllegalArgumentException {
    if (quantity < 0 || quantity > 1000) {
      throw new IllegalArgumentException("Quantity has to be above 0 or under 1000");
    }

    this.quantity = roundIfStk(quantity, unit);
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
    String names = this.name;
    if (this.name.length() > 10) {
      names = name.substring(0, 10) + "..";
    }

    String priceUnit = String.format("%.2f%s", this.quantity, this.unit);

    return String.format("| %-12s | %-8s  | %.2fkr        | %s       |%n",
        names,
        priceUnit,
        this.price,
        this.expirationDate);
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

  /**
   * .
   *
   * @param inputExpirationDate expirationdate
   * @return LocalDate
   */
  private LocalDate parseDateInput(String inputExpirationDate) {
    try {
      LocalDate parsedDateInput =
          LocalDate.parse(inputExpirationDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
      if (parsedDateInput.isBefore(LocalDate.now())) {
        throw new IllegalArgumentException("Expiration date has gone out. Date is in the past.");
      }
      return parsedDateInput;
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("Invalid date format. Please use dd.MM.yyyy", e);
    }
  }
}
