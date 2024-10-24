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
   * @param expirationDate Expiration date of the grocery in.
   * @param quantity       Total quantity of the grocery.
   * @throws IllegalArgumentException If string parameters is null.
   * @throws IllegalArgumentException If float values are negative.
   * @throws IllegalArgumentException If try block throws DateTimeParseException, wrong format.
   */
  public Grocery(String name, String unit, float price, String expirationDate, float quantity)
      throws IllegalArgumentException {
    if (name == null || unit == null || expirationDate == null) {
      throw new IllegalArgumentException("Grocery info can not be null");
    }

    if (price < 0 || quantity < 0) {
      throw new IllegalArgumentException("The price or quantity can not be negative");
    }

    this.name = name;
    this.unit = unit;
    this.price = price;
    this.quantity = quantity;

    try {
      this.expirationDate =
          LocalDate.parse(expirationDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("Invalid date format. Please use dd.MM.yyyy", e);
    }
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
   * @param quantity The new quantity of a grocery getting stored.
   * @throws IllegalArgumentException if the quantity is negative.
   */
  public void setQuantity(float quantity) throws IllegalArgumentException {
    if (quantity < 0) {
      throw new IllegalArgumentException("Quantity has to be above 0");
    }
    this.quantity = this.quantity + quantity;
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
    StringBuilder string = new StringBuilder();

    string.append(this.name).append(": ")
        .append(this.quantity).append(" ")
        .append(this.unit).append(", ")
        .append("price per ").append(unit).append(": ").append(this.price).append(", ")
        .append("expiration date: ")
        .append(this.expirationDate.format(DateTimeFormatter.ofPattern("MMMM d, yyyy")));

    return string.toString();
  }
}
