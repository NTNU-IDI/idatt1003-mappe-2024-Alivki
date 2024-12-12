package edu.ntnu.idi.idatt.models;

/**
 * Represents a grocery with its details.
 *
 * <p>the {@code Grocery} class is designed to store
 * information about the grocery item.</p>
 *
 * @author Iver Lindholm
 * @version v2.0
 * @since v1.0
 */
public class Grocery {
  private final String name;
  private final String unit;
  private final float price;

  /**
   * Constructs a new {@code Grocery} with all specified details.
   *
   * @param name  The name of the grocery item.
   * @param unit  Unit of measurement for the grocery.
   * @param price Price per unit of the grocery.
   *              Float because we need precision when measuring price per unit.
   */
  public Grocery(String name, String unit, float price) {
    this.name = name;
    this.unit = unit;
    this.price = price;
  }

  /**
   * Gets the name of the grocery.
   *
   * @return a {@link String} containing the name.
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the unit of specified grocery.
   *
   * @return a {@link String} containing the unit of measurement.
   */
  public String getUnit() {
    return unit;
  }

  /**
   * Gets the price of the grocery per unit.
   *
   * @return a {@link Float} containing the price per unit.
   */
  public float getPrice() {
    return price;
  }
}
