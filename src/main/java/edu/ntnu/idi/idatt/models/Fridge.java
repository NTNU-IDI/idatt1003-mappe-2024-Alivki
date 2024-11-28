package edu.ntnu.idi.idatt.models;

import edu.ntnu.idi.idatt.utils.StringManipulation;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Represents a fridge filled with grocery items.
 * Operations as adding and removing items, finding grocery items based on name or expiration date.
 * Using list interface because it adds flexibility(can change list type),
 * encapsulation(hides implementation from the user) and collects objects in indexed sequence.
 */
public class Fridge {
  private final List<GroceryItem> groceries;

  /**
   * Using array list because it is dynamic array with many useful methods.
   */
  public Fridge() {
    groceries = new ArrayList<>();
  }

  /**
   * .
   */
  public List<GroceryItem> getGroceries() {
    return groceries;
  }

  /**
   * Adds a grocery item to the list.
   * If it is an existing grocery it will call the grocery.increaseQuantity
   * method to increase its quantity. If it is a new item it will be added to
   * the list while maintaining sorted order of the array.
   *
   * @param newGrocery the new grocery.
   */
  public String addGrocery(GroceryItem newGrocery) {
    if (groceries.isEmpty()) {
      groceries.add(newGrocery);
      return String.format("%s was successfully added!%n", newGrocery.getGrocery().getName());
    }

    if (groceryExist(newGrocery)) {
      newGrocery.increaseQuantity(newGrocery.getQuantity());
      return String.format(
          "%nGrocery is all ready in fridge. Increased the quantity of the grocery!%n");
    }

    groceries.add(findIndex(newGrocery), newGrocery);
    return String.format("%n%s was successfully added!%n", newGrocery.getGrocery().getName());
  }

  /**
   * Finding a specific grocery by name.
   *
   * @param inputName The name of the grocery to search for.
   */
  public String printGrocery(String inputName) {
    Optional<GroceryItem> foundGrocery = findGrocery(inputName);

    return foundGrocery.map(groceryItem -> String.format(
            "%s%s+--------------+-------------+-----------------+------------------+%n",
            printFridgeHeader(inputName), groceryItem.printGrocery()))
        .orElseGet(() -> String.format("You do not have %s in the fridge%n", inputName));

  }

  /**
   * Removing a grocery by name.
   *
   * @param inputName The name of the grocery to search for.
   */
  public String removeGrocery(String inputName) {
    Optional<GroceryItem> foundGrocery = findGrocery(inputName);

    if (foundGrocery.isEmpty()) {
      return String.format("You do not have %s in the fridge!%n", inputName);
    }

    groceries.remove(foundGrocery.get());
    return String.format("%n%s was removed.%n", inputName);
  }

  /**
   * Increases the quantity of grocery by name.
   *
   * @param inputName The name of the grocery to search for.
   * @param quantity  The quantity to increase grocery quantity with.
   */
  public String increaseQuantity(String inputName, float quantity) {
    Optional<GroceryItem> foundGrocery = findGrocery(inputName);

    if (foundGrocery.isEmpty()) {
      return String.format("You do not have %s in the fridge!%n", inputName);
    }

    foundGrocery.get().increaseQuantity(quantity);
    return String.format("%n%s increase in the quantity %n", inputName);
  }

  /**
   * Decreases the quantity of grocery by name.
   * If the quantity after the decreasing operation is 0
   * it calls the removeGrocery with grocery name to remove.
   *
   * @param inputName The name of the grocery to search for.
   * @param quantity  The quantity to decrease grocery quantity with.
   */
  public String decreaseQuantity(String inputName, float quantity) {
    Optional<GroceryItem> foundGrocery = findGrocery(inputName);

    if (foundGrocery.isEmpty()) {
      return String.format("You do not have this grocery in the fridge%n");
    }

    if (foundGrocery.get().getQuantity() - quantity <= 0) {
      removeGrocery(inputName);
      return "After decreasing quantity there was nothing left of the grocery!";
    }

    foundGrocery.get().decreaseQuantity(quantity);
    return String.format("%s decrease in quantity.%n", inputName);
  }


  /**
   * Prints a list of grocery items that have an expiration date before the specified date.
   * If no groceries with expiration date before inputDate prints no groceries exist.
   * Printed in ascending order by date.
   *
   * @param inputDate The date to check against the expiration date of the grocery item.
   */
  public String bestBeforeDate(LocalDate inputDate) {
    if (groceries.stream()
        .noneMatch(grocery -> grocery.getExpirationDate().isBefore(inputDate.plusDays(1)))) {
      return String.format("You have no groceries with a expiration date before %s.%n", inputDate);
    }

    final String totalPrice = String.format("%.2f %s", totalExpiredPrice(inputDate), "kr");

    StringBuilder outputStringOfGroceryItems = new StringBuilder();
    groceries.stream()
        .filter(grocery -> grocery.getExpirationDate().isBefore(inputDate.plusDays(1)))
        .sorted(Comparator.comparing(GroceryItem::getExpirationDate))
        .map(groceryItem -> String.format("%s", groceryItem.printGrocery()))
        .forEach(outputStringOfGroceryItems::append);

    return String.format(
        "%s"
            + "%s+--------------+-------------+-----------------+------------------+%n"
            + "| Total price: %-51s| %n"
            + "+--------------+-------------+-----------------+------------------+%n",
        printFridgeHeader("Expired groceries"), outputStringOfGroceryItems.toString(), totalPrice);
  }

  /**
   * Printing all groceries in fridge in table.
   * If there is no groceries it prints error message to user.
   */
  public String printFridgeContent() {
    if (groceries.isEmpty()) {
      return String.format("There is no groceries in your fridge!%n");
    }

    final String totalPrice = String.format("%.2f %s", totalPrice(), "kr");

    StringBuilder outputGroceries = new StringBuilder();
    groceries.stream().map(groceryItem -> String.format("%s", groceryItem.printGrocery()))
        .forEach(outputGroceries::append);

    return String.format("%s"
            + "%s"
            + "+--------------+-------------+-----------------+------------------+%n"
            + "| Total price: %-51s| %n"
            + "+--------------+-------------+-----------------+------------------+%n",
        printFridgeHeader("Fridge content"), outputGroceries, totalPrice);
  }

  /**
   * .
   */
  private float totalExpiredPrice(LocalDate inputDate) {
    return groceries.stream()
        .filter(grocery -> grocery.getExpirationDate().isBefore(inputDate.plusDays(1)))
        .map(groceryItem -> groceryItem.getGrocery().getPrice() * groceryItem.getQuantity())
        .reduce(0.0f, Float::sum);
  }

  /**
   * .
   */
  private float totalPrice() {
    return groceries.stream()
        .map(groceryItem -> groceryItem.getGrocery().getPrice() * groceryItem.getQuantity())
        .reduce(0.0f, Float::sum);
  }

  /**
   * .
   */
  private boolean groceryExist(GroceryItem inputGrocery) {
    for (GroceryItem grocery : groceries) {
      if (grocery.equals(inputGrocery)) {
        return true;
      }
    }
    return false;
  }

  /**
   * .
   */
  private Optional<GroceryItem> findGrocery(String inputName) {
    for (GroceryItem grocery : groceries) {
      if (grocery.getGrocery().getName().equalsIgnoreCase(inputName)) {
        return Optional.of(grocery);
      }
    }
    return Optional.empty();
  }

  /**
   * Made my own binary search because it was fun. And so I did not have to make a list
   * for the built-in Collections.binarySearch that needs a list of names and the search term.
   *
   * @return return index
   */
  private int findIndex(GroceryItem inputGrocery) {
    int left = 0;
    int right = groceries.size();

    while (left < right) {
      int mid = (left + right) / 2;
      if (groceries.get(mid).getGrocery().getName().toLowerCase()
          .compareTo(inputGrocery.getGrocery().getName().toLowerCase())
          < 0) {
        left = mid + 1;
      } else {
        right = mid;
      }
    }

    return left;
  }

  /**
   * Printing header for grocery content table.
   */
  private String printFridgeHeader(String title) {
    return String.format("+-----------------------------------------------------------------+%n")
        + StringManipulation.centerString(title, 67)
        + String.format("+--------------+-------------+-----------------+------------------+%n")
        + String.format("| Name         | Quantity    | Price per unit  | Expiration date  |%n")
        + String.format("+--------------+-------------+-----------------+------------------+%n");
  }
}