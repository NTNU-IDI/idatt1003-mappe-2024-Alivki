package edu.ntnu.idi.idatt;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
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
   * Adds a grocery item to the list.
   * If it is an existing grocery it will call the grocery.increaseQuantity
   * method to increase its quantity. If it is a new item it will be added to
   * the list while maintaining sorted order of the array.
   *
   * @param name           The name of new grocery.
   * @param unit           The unit of new grocery.
   * @param price          The price of new grocery.
   * @param expirationDate The expirationDate of new grocery.
   * @param quantity       The quantity of new grocery.
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

    int low = 0;
    int high = groceries.size();

    while (low < high) {
      int mid = (low + high) / 2;
      if (groceries.get(mid).getName().compareTo(name) < 0) {
        low = mid + 1;
      } else {
        high = mid;
      }
    }

    Grocery newGrocery = new Grocery(name, unit, price, expirationDate, quantity);
    groceries.add(low, newGrocery);
    System.out.printf("%n%s was successfully added!%n", name);
  }

  /**
   * Finding a specific grocery by name.
   *
   * @param inputName The name of the grocery to search for.
   */
  public void findGrocery(String inputName) {
    for (Grocery grocery : groceries) {
      if (grocery.getName().equalsIgnoreCase(inputName)) {
        System.out.print(printFridgeHeader());
        System.out.print(grocery);
        System.out.format("+--------------+-----------+-----------------+------------------+%n");
        return;
      }
    }
    System.out.printf("%n%s not found!%n", inputName);
  }

  /**
   * Removing a grocery by name.
   *
   * @param inputName The name of the grocery to search for.
   */
  public void removeGrocery(String inputName) {
    for (Grocery grocery : groceries) {
      if (grocery.getName().equals(inputName)) {
        groceries.remove(grocery);
        System.out.printf("%n%s was removed.%n", inputName);
        return;
      }
    }
  }

  /**
   * Increases the quantity of grocery by name.
   *
   * @param inputName The name of the grocery to search for.
   * @param quantity The quantity to increase grocery quantity with.
   */
  public void increaseQuantity(String inputName, float quantity) {
    for (Grocery grocery : groceries) {
      if (grocery.getName().equals(inputName)) {
        grocery.increaseQuantity(quantity);
        System.out.printf("%n%s increase in quantity.%n", inputName);
        return;
      }
    }
  }

  /**
   * Decreases the quantity of grocery by name.
   * If the quantity after the decreasing operation is 0
   * it calls the removeGrocery with grocery name to remove.
   *
   * @param inputName The name of the grocery to search for.
   * @param quantity The quantity to decrease grocery quantity with.
   */
  public void decreaseQuantity(String inputName, float quantity) {
    for (Grocery grocery : groceries) {
      if (grocery.getName().equals(inputName)) {
        if (grocery.getQuantity() - quantity <= 0) {
          System.out.print("After decreasing quantity there was nothing left of the grocery!");
          removeGrocery(inputName);
          return;
        }
        grocery.decreaseQuantity(quantity);
        System.out.printf("%s decrease in quantity.%n", inputName);
        return;
      }
    }
  }

  /**
   * Prints a list of grocery items that have an expiration date before the specified date.
   * If no groceries with expiration date before inputDate prints no groceries exist.
   * Printed in ascending order by date.
   *
   * @param inputDate The date to check against the expiration date of the grocery item.
   */
  public void bestBeforeDate(LocalDate inputDate) {
    if (groceries.stream()
        .noneMatch(grocery -> grocery.getExpirationDate().isBefore(inputDate.plusDays(1)))) {
      System.out.printf("You have no groceries with a expiration date before %s.%n", inputDate);
      return;
    }

    System.out.print(printFridgeHeader());
    groceries.stream()
        .filter(grocery -> grocery.getExpirationDate().isBefore(inputDate.plusDays(1)))
        .sorted(Comparator.comparing(Grocery::getExpirationDate))
        .map(Grocery::toString)
        .forEach(System.out::print);
    System.out.format("+--------------+-----------+-----------------+------------------+%n");
  }

  /**
   * Printing all groceries in fridge in table.
   * If there is no groceries it prints error message to user.
   */
  public void printFridgeContent() {
    if (groceries.isEmpty()) {
      System.out.println("There is no groceries in your fridge!");
      return;
    }

    System.out.print(printFridgeHeader());
    groceries.stream().map(Grocery::toString).forEach(System.out::print);
    System.out.format("+--------------+-----------+-----------------+------------------+%n");
  }

  /**
   * Printing header for grocery content table.
   */
  private String printFridgeHeader() {
    return String.format("+---------------------------------------------------------------+%n")
        + String.format("|                       Fridge contents                         |%n")
        + String.format("+--------------+-----------+-----------------+------------------+%n")
        + String.format("| Name         | Quantity  | Price per unit  | Expiration date  |%n")
        + String.format("+--------------+-----------+-----------------+------------------+%n");
  }

  /**
   * Clears the console.
   */
  private static void clearConsole() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }
}
