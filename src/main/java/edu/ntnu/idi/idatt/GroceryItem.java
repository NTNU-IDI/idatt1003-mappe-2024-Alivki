package edu.ntnu.idi.idatt;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * .
 */
public class GroceryItem {
  private final Grocery grocery;
  private final LocalDate expirationDate;
  private float quantity;

  /**
   * .
   *
   * @param expirationDate Expiration date of the grocery in.
   *                       LocalDate is immutable, useful methods for date manipulation,
   *                       Only day, month and year variables.
   * @param quantity       Total quantity of the grocery.
   *                       Float because we need precision when measuring the grocery.
   */
  public GroceryItem(Grocery grocery, LocalDate expirationDate, float quantity) {
    InputValidation.isValidFloat(quantity);
    InputValidation.isValidDate(expirationDate);

    this.grocery = grocery;
    this.expirationDate = expirationDate;

    increaseQuantity(quantity);
  }

  /**
   * .
   */
  public Grocery getGrocery() {
    return grocery;
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

    this.quantity += roundIfStk(quantity, grocery.getUnit());
  }

  /**
   * Sets the new total quantity stored of specific grocery.
   *
   * @param quantity How much of a grocery is being removed.
   * @throws IllegalArgumentException If the quantity is negative or above 1000.
   */
  public void decreaseQuantity(float quantity) throws IllegalArgumentException {
    InputValidation.isValidFloat(quantity);

    this.quantity -= roundIfStk(quantity, grocery.getUnit());
  }

  /**
   * Returns a string representation of the grocery.
   * Using StringBuilder for efficient string concatenation.
   * Formating the date to a more readable format with DateTimeFormatter.
   *
   * @return A string containing the name, quantity, unit and expiration date.
   */
  public String printGrocery() {
    String name =
        grocery.getName().length() > 12 ? shortenName(grocery.getName(), 10) : grocery.getName();

    String unit = String.format("%.2f%s", this.quantity, grocery.getUnit());
    String price = String.format("%.2f%s", grocery.getPrice(), "kr");


    return String.format("| %-12s | %-11s | %-15s | %-16s |%n", name, unit,
        price, expirationDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
  }

  /**
   * .
   *
   * @param inputName awd
   * @return awd
   */
  private String shortenName(String inputName, int shortenFrom) {
    return inputName.substring(0, shortenFrom) + "..";
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
   */
  //@Override
  //public String toString() {
  //}
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    GroceryItem groceryItem = (GroceryItem) obj;

    return Objects.equals(grocery.getName().toLowerCase(),
        groceryItem.getGrocery().getName().toLowerCase());
  }

  @Override
  public int hashCode() {
    return Objects.hash(grocery.getName());
  }
}
