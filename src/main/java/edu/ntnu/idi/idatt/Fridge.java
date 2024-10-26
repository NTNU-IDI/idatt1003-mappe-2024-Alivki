package edu.ntnu.idi.idatt;

import java.time.LocalDate;
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
   * @param name           name
   * @param unit           unit
   * @param price          price
   * @param expirationDate expirationDate
   * @param quantity       quantity
   */
  public void addGrocery(
      String name, String unit, float price, String expirationDate, float quantity) {
    if (groceries.isEmpty()) {
      Grocery newGrocery = new Grocery(name, unit, price, expirationDate, quantity);
      groceries.add(newGrocery);
      System.out.printf("%s was successfully added!", name);
      return;
    }

    for (Grocery grocery : groceries) {
      if (grocery.getName().equals(name)) {
        grocery.increaseQuantity(quantity);
        System.out.printf(
            "%nGrocery is all ready in fridge. Increased the quantity of the grocery!%n");
        return;
      }
    }

    Grocery newGrocery = new Grocery(name, unit, price, expirationDate, quantity);
    groceries.add(newGrocery);
    System.out.printf("%n%s was successfully added!%n", name);

    //TODO sort the list of groceries on added
  }

  /**
   * .
   */
  public void findGrocery(String inputName) {
    //clearConsole();
    for (Grocery grocery : groceries) {
      if (grocery.getName().equalsIgnoreCase(inputName)) {
        System.out.print(printFridgeHeader());
        System.out.print(grocery);
        System.out.format("+--------------+-----------+-----------------+------------------+%n");
        return;
      }
    }
    System.out.printf("%s not found!", inputName);
  }

  /**
   * .
   */
  public void removeGrocery(String name) {
    for (Grocery grocery : groceries) {
      if (grocery.getName().equals(name)) {
        groceries.remove(grocery);
        System.out.printf("%n%s was removed.%n", name);
        return;
      }
    }
    //TODO remove part of a grocery. If it is the full quantity remove the grocery from the fridge
  }

  /**
   * .
   */
  public void increaseQuantity(String name, float quantity) {
    //TODO add part of a grocery
    for (Grocery grocery : groceries) {
      if (grocery.getName().equals(name)) {
        grocery.increaseQuantity(quantity);
        System.out.printf("%n%s increase in quantity.%n", name);
        return;
      }
    }
  }

  /**
   * .
   */
  public void decreaseQuantity(String name, float quantity) {
    //TODO remove part of a grocery. If it is the full quantity remove the grocery from the fridge
    for (Grocery grocery : groceries) {
      if (grocery.getName().equals(name)) {
        if (grocery.getQuantity() - quantity <= 0) {
          System.out.print("After decreasing quantity there was nothing left of the grocery!");
          removeGrocery(name);
          return;
        }
        grocery.decreaseQuantity(quantity);
        System.out.printf("%n%s decrease in quantity.%n", name);
        return;
      }
    }
  }

  /**
   * .
   */
  public void bestBeforeDate(LocalDate inputDate) {
    //TODO find all groceries that before best before date
  }

  /**
   * .
   */
  public void printFridgeContent() {
    //clearConsole();
    if (groceries.isEmpty()) {
      System.out.println("There is no groceries in your fridge!");
      return;
    }
    System.out.print(printFridgeHeader());
    for (Grocery grocery : groceries) {
      System.out.print(grocery);
      System.out.format("+--------------+-----------+-----------------+------------------+%n");
    }
  }

  /**
   * .
   */
  private String printFridgeHeader() {
    return String.format("+---------------------------------------------------------------+%n")
        + String.format("|                       Fridge contents                         |%n")
        + String.format("+--------------+-----------+-----------------+------------------+%n")
        + String.format("| Name         | Quantity  | Price per unit  | Expiration date  |%n")
        + String.format("+--------------+-----------+-----------------+------------------+%n");
  }

  /**
   * Clearing the console.
   */
  private static void clearConsole() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }
}
