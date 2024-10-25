package edu.ntnu.idi.idatt;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a fridge filled with grocery items.
 * Operations as adding and removing items, finding grocery items based on name or expiration date.
 * Using list interface because it adds flexibility(can change list type),
 * encapsulation(hides implementation from the user) and collects objects in indexed sequence.
 */
public class Fridge {
  private final List<Grocery> groceries;

  /**
   * Using array list because it is dynamic array with many useful methods.
   */
  public Fridge() {
    groceries = new ArrayList<>();
  }

  /**
   * .
   *
   * @param grocery grocery object
   */
  public void addGrocery(Grocery grocery) {
    groceries.add(grocery);
    System.out.printf("Grocery was successfully added!%n");
    //TODO if grocery exist add to the quantity
    //TODO sort the list of groceries on added
  }

  /**
   * .
   */
  public void findGrocery() {
    //TODO based on name
  }

  /**
   * .
   */
  public void removeGrocery() {
    //TODO remove part of a grocery. If it is the full quantity remove the grocery from the fridge
  }

  /**
   * .
   */
  public void bestBeforeDate() {
    //TODO find all groceries that before best before date
  }

  /**
   * .
   */
  public void printFridgeContent() {
    groceries.forEach(System.out::println);
    System.out.format("+--------------+-----------+-----------------+------------------+%n");
    System.out.format("|                     Fridge contents                           |%n");
    System.out.format("+--------------+-----------+-----------------+------------------+%n");
    System.out.format("| Name         | Quantity  | Price per unit  | Expiration date  |%n");
    System.out.format("+--------------+-----------+-----------------+------------------+%n");
    String leftAlignment = "| %-12s | %-8s  | %-15.2f | %s       |%n";
    for (Grocery grocerie : groceries) {
      String name = grocerie.getName();
      if (name.length() > 10) {
        name = name.substring(0, 10) + "..";
      }
      System.out.format(leftAlignment,
          name,
          String.format("%.2f%s", grocerie.getQuantity(), grocerie.getUnit()),
          grocerie.getPrice(),
          grocerie.getExpirationDate());
      System.out.format("+--------------+-----------+-----------------+------------------+%n");
    }
  }
}
