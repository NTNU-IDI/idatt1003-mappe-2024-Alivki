package edu.ntnu.idi.idatt;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * .
 */
public class Grocery {
  private final String name;
  private final String unit;
  private final float price;
  private final LocalDate expirationDate;
  private float quantity;

  /**
   * .
   */
  public Grocery(String name, String unit, float price, LocalDate expirationDate, float quantity) {
    this.name = name;
    this.unit  = unit;
    this.price = price;
    this.expirationDate = expirationDate;
    this.quantity = quantity;
  }

  public String getName() {
    return name;
  }

  public String getUnit() {
    return unit;
  }

  public float getPrice() {
    return price;
  }

  public float getQuantity() {
    return quantity;
  }

  public LocalDate getExpirationDate() {
    return expirationDate;
  }

  public void setQuantity(float quantity) {
    this.quantity = quantity;
  }

  @Override
  public String toString() {
    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    StringBuilder string = new StringBuilder();

    string.append(this.name).append(": ")
      .append(this.quantity).append(" ")
      .append(this.unit).append(", ")
      .append("price per ").append(unit).append(": ").append(this.price).append(", ")
      .append("expiration date: ").append(this.expirationDate);

    return string.toString();
  }
}
