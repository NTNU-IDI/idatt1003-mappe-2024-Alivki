package edu.ntnu.idi.idatt.models;

/**
 * Represents a grocery item with its details and operations.
 */
public class Grocery {
  private final String name;
  private final String unit;
  private final float price;

  /**
   * Constructs a new Grocery with all specified details.
   *
   * @param name           The name of the grocery item.
   * @param unit           Unit of measurement for the grocery.
   * @param price          Price per unit of the grocery.
   *                       Float because we need precision when measuring price per unit.
   */
  public Grocery(String name, String unit, float price) {
    this.name = name;
    this.unit = unit;
    this.price = price;
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
}
