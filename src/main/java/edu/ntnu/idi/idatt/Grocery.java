package edu.ntnu.idi.idatt;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
   * @param name The name of the grocery item.
   * @param unit Unit of measurement for the grocery.
   * @param price Price per unit of the grocery.
   * @param expirationDate Expiration date of the grocery in.
   * @param quantity Total quantity of the grocery.
   * @throws IllegalArgumentException if and parameters are invalid (e.g., negative params).
   */
  public Grocery(String name, String unit, float price, String expirationDate, float quantity) {
    this.name = name;
    this.unit = unit;
    this.price = price;
    this.quantity = quantity;
    this.expirationDate =
        LocalDate.parse(expirationDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
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
   * @throws IllegalArgumentException if the quantity is negativ.
   */
  public void setQuantity(float quantity) {
    this.quantity = quantity;
  }

  /**
   * Returns a string representation of the grocery.
   *
   * @return A string containing the name, quantity, unit and expiration date.
   */
  @Override
  public String toString() {
    //Using StringBuilder for efficient string concatenation.
    StringBuilder string = new StringBuilder();

    string.append(this.name).append(": ")
        .append(this.quantity).append(" ")
        .append(this.unit).append(", ")
        .append("price per ").append(unit).append(": ").append(this.price).append(", ")
        .append("expiration date: ")
        //Format the date to a more readable format with DateTimeFormatter.
        .append(this.expirationDate.format(DateTimeFormatter.ofPattern("MMMM d, yyyy")));

    return string.toString();
  }
}
