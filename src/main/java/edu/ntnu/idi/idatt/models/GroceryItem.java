package edu.ntnu.idi.idatt.models;

import edu.ntnu.idi.idatt.utils.InputValidation;
import edu.ntnu.idi.idatt.utils.StringManipulation;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Represents a grocery item with its details.
 *
 * <p>the {@code GroceryItem} class is designed to store
 * information about the grocery item.</p>
 *
 * @author Iver Lindholm
 * @version v2.0
 * @since v1.0
 */
public class GroceryItem {
  private final Grocery grocery;
  private final LocalDate expirationDate;
  private float quantity;

  /**
   * Constructs a new {@code GroceryItem} with its specified details.
   *
   * @param expirationDate the expiration date for the grocery item with {@link LocalDate}.
   * @param quantity       Total quantity of the grocery.
   */
  public GroceryItem(Grocery grocery, LocalDate expirationDate, float quantity) {
    this.grocery = grocery;
    this.expirationDate = expirationDate;

    increaseQuantity(quantity);
  }

  /**
   * Gets the grocery object of the grocery item.
   *
   * @return a {@link Grocery}
   */
  public Grocery getGrocery() {
    return grocery;
  }

  /**
   * Gets the total quantity stored of specified grocery.
   *
   * @return a {@link Float} containing the quantity.
   */
  public float getQuantity() {
    return quantity;
  }

  /**
   * Gets the expiration date.
   *
   * @return a {@link LocalDate} with the expiration date.
   */
  public LocalDate getExpirationDate() {
    return expirationDate;
  }

  /**
   * Sets the new total quantity stored of specific grocery.
   *
   * @param quantity How much of a grocery is being added.
   */
  public void increaseQuantity(float quantity) {
    this.quantity += roundIfStk(quantity, grocery.getUnit());
  }

  /**
   * Sets the new total quantity stored of specific grocery.
   *
   * @param quantity How much of a grocery is being removed.
   */
  public void decreaseQuantity(float quantity) {
    this.quantity -= roundIfStk(quantity, grocery.getUnit());
  }

  /**
   * Construct a string to represent the grocery item.
   *
   * @return a {@link String} containing the name, quantity, unit and expiration date.
   */
  public String printGrocery() {
    String name =
        grocery.getName().length() > 12 ? StringManipulation.shortenString(grocery.getName(), 10) :
            grocery.getName();

    String unit = String.format("%.2f%s", this.quantity, grocery.getUnit());
    String price = String.format("%.2f%s", grocery.getPrice(), "kr");


    return String.format("| %-12s | %-11s | %-15s | %-16s |%n", name, unit,
        price, expirationDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
  }

  /**
   * Rounds the quantity to a whole number of the unit is stk.
   *
   * @param quantity the quantity of the grocery item.
   * @param unit     the unit if the grocer item.
   * @return a {@link Float} contain the rounded float.
   */
  private float roundIfStk(float quantity, String unit) {
    if (unit.equalsIgnoreCase("stk")) {
      return Math.round(quantity);
    } else {
      return quantity;
    }
  }

  /**
   * To string method for the class used in debugging.
   *
   * @return a {@link String} containing all information of the grocery item.
   */
  @Override
  public String toString() {
    return String.format("Name: %s, Unit: %s, Price: %f, Expiration date: %s, Quantity: %f",
        grocery.getName(), grocery.getUnit(), grocery.getPrice(), expirationDate, quantity);
  }

  /**
   * Override method to check if two Grocery item objects equals each-other.
   *
   * @param obj the obj to check
   * @return a {@link Boolean} indication if they are equals or not.
   */
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

  /**
   * Override method for changing the hashcode to represent the class.
   *
   * @return a {@link Integer} with the hashcode.
   */
  @Override
  public int hashCode() {
    return Objects.hash(grocery.getName());
  }
}
