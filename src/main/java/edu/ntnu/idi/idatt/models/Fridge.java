package edu.ntnu.idi.idatt.models;

import edu.ntnu.idi.idatt.utils.StringManipulation;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Represents a fridge filled with grocery items.
 *
 * <p>The {@code Fridge} class is design to manage a list of
 * {@link GroceryItem} objects.</p>
 *
 * @author Iver Lindholm
 * @version v2.0
 * @since v1.0
 */
public class Fridge {
  private final List<GroceryItem> groceries;

  /**
   * Constructs a new {@code Fridge} with an empty array list.
   */
  public Fridge() {
    groceries = new ArrayList<>();
  }

  /**
   * Gets the list of {@link GroceryItem} object stored in the fridge.
   *
   * @return a {@link List} of {@link GroceryItem} objects
   */
  public List<GroceryItem> getGroceries() {
    return groceries;
  }

  /**
   * Adds a grocery item to the list if it does not exist.
   *
   * <p>Method checks if the grocery item exist. If it does
   * it calls the increase quantity method. Else it adds it to the list
   * while maintaining a sorted array based on alphabetical order</p>
   *
   * @param newGrocery the {@link GroceryItem} object to be added
   * @return a {@link String} message indication if the item got added or not
   */
  public String addGrocery(GroceryItem newGrocery) {
    if (groceries.isEmpty()) {
      groceries.add(newGrocery);
      return String.format("%s was successfully added!%n", newGrocery.getGrocery().getName());
    }

    if (groceryExist(newGrocery)) {
      findGrocery(newGrocery.getGrocery().getName()).get()
          .increaseQuantity(newGrocery.getQuantity());
      return String.format(
          "%nGrocery is all ready in fridge. Increased the quantity of the grocery!%n");
    }

    groceries.add(findIndex(newGrocery), newGrocery);
    return String.format("%n%s was successfully added!%n", newGrocery.getGrocery().getName());
  }

  /**
   * Finding a specific grocery by name and returns information.
   *
   * @param inputName The name of the grocery to search for
   * @return a {@link String} containing the information about the grocery item
   */
  public String printGrocery(String inputName) {
    Optional<GroceryItem> foundGrocery = findGrocery(inputName);

    return foundGrocery.map(groceryItem -> String.format(
            "%s%s+--------------+-------------+-----------------+------------------+%n",
            printFridgeHeader(inputName), groceryItem.printGrocery()))
        .orElseGet(() -> String.format("You do not have %s in the fridge%n", inputName));

  }

  /**
   * Removing a grocery from the array list.
   *
   * @param inputName The name of the grocery to search for.
   * @return a {@link String} indication how the operation went.
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
   * @return a {@link String} indication how the operation went.
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
   *
   * <p>If the quantity after decreasing is 0 or below
   * it calls the removeGrocery method for the specified
   * {@link GroceryItem}</p>
   *
   * @param inputName The name of the grocery to search for.
   * @param quantity  The quantity to decrease grocery quantity with.
   * @return a {@link String} indication how the operation went.
   */
  public String decreaseQuantity(String inputName, float quantity) {
    Optional<GroceryItem> foundGrocery = findGrocery(inputName);

    if (foundGrocery.isEmpty()) {
      return String.format("You do not have this grocery in the fridge%n");
    }

    foundGrocery.get().decreaseQuantity(quantity);

    if (foundGrocery.get().getQuantity() <= 0) {
      removeGrocery(inputName);
      return "After decreasing quantity there was nothing left of the grocery!";
    }

    return String.format("%s decrease in quantity.%n", inputName);
  }


  /**
   * Returns a content table of expired grocery items.
   *
   * <p>Makes a content table of all grocery items that have
   * expired before the specified date. It is made in ascending order by date</p>
   *
   * @param inputDate The {@link LocalDate} to check against the expiration date.
   * @return a {@link String} contain error message or content table.
   */
  public String bestBeforeDate(LocalDate inputDate) {
    if (groceries.stream()
        .noneMatch(grocery -> grocery.getExpirationDate().isBefore(inputDate.plusDays(0)))) {
      return String.format("You have no groceries with a expiration date before %s.%n", inputDate);
    }

    final String totalPrice = String.format("%.2f %s", totalExpiredPrice(inputDate), "kr");

    StringBuilder outputStringOfGroceryItems = new StringBuilder();
    groceries.stream()
        .filter(grocery -> grocery.getExpirationDate().isBefore(inputDate.plusDays(0)))
        .sorted(Comparator.comparing(GroceryItem::getExpirationDate))
        .map(groceryItem -> String.format("%s", groceryItem.printGrocery()))
        .forEach(outputStringOfGroceryItems::append);

    return """
        %s%s+--------------+-------------+-----------------+------------------+
        | Total price: %-51s|
        +--------------+-------------+-----------------+------------------+%n
        """.formatted(printFridgeHeader("Expired groceries"), outputStringOfGroceryItems.toString(),
        totalPrice);
  }

  /**
   * Returns a content table contain all {@link GroceryItem} in fridge.
   *
   * @return a {@link String} containing error message or the content table for the fridge.
   */
  public String printFridgeContent() {
    if (groceries.isEmpty()) {
      return String.format("There is no groceries in your fridge!%n");
    }

    final String totalPrice = String.format("%.2f %s", totalPrice(), "kr");

    StringBuilder outputGroceries = new StringBuilder();
    groceries.stream().map(groceryItem -> String.format("%s", groceryItem.printGrocery()))
        .forEach(outputGroceries::append);

    return """
        %s%s+--------------+-------------+-----------------+------------------+
        | Total price: %-51s|
        +--------------+-------------+-----------------+------------------+
        """.formatted(printFridgeHeader("Fridge content"), outputGroceries, totalPrice);
  }

  /**
   * Calculates the total price of all expired {@link GroceryItem} objects.
   *
   * @param inputDate the {@link  LocalDate} to check against the expiery date.
   * @return a {@link Float} with the total price.
   */
  private float totalExpiredPrice(LocalDate inputDate) {
    return groceries.stream()
        .filter(grocery -> grocery.getExpirationDate().isBefore(inputDate.plusDays(0)))
        .map(groceryItem -> groceryItem.getGrocery().getPrice() * groceryItem.getQuantity())
        .reduce(0.0f, Float::sum);
  }

  /**
   * Calculates the total price of all {@link GroceryItem} objects.
   *
   * @return a {@link Float} with the total price.
   */
  private float totalPrice() {
    return groceries.stream()
        .map(groceryItem -> groceryItem.getGrocery().getPrice() * groceryItem.getQuantity())
        .reduce(0.0f, Float::sum);
  }

  /**
   * Checks if the specified grocery exist in the array list.
   *
   * @param inputGrocery the {@link GroceryItem} to check.
   * @return a {@link Boolean} indicating if the grocery item exist.
   */
  private boolean groceryExist(GroceryItem inputGrocery) {
    return groceries.stream().anyMatch(groceryItem -> groceryItem.equals(inputGrocery));
  }

  /**
   * Finds a grocery items based on input name.
   *
   * @param inputName the name input from user.
   * @return a {@link Optional} contain the found {@link GroceryItem} or null.
   */
  private Optional<GroceryItem> findGrocery(String inputName) {
    return groceries.stream()
        .filter(groceryItem -> groceryItem.getGrocery().getName().equalsIgnoreCase(inputName))
        .findFirst();
  }

  /**
   * Finds the correct index to add new grocery item to keep list sorted.
   *
   * <p>Binary search to find index to add new grocery item
   * to keep the list sorted alphabetically. Used this instead of
   * the built-in Collections.binarySearch because that need a list
   * of names and search term.</p>
   *
   * @return a {@link Integer} indication index for new grocery item to be added.
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
   * Makes content table header for fridge content.
   *
   * @param title title for the content table.
   * @return {@link String} containing the content table.
   */
  private String printFridgeHeader(String title) {
    return String.format("+-----------------------------------------------------------------+%n")
           + StringManipulation.centerString(title, 67)
           + String.format("+--------------+-------------+-----------------+------------------+%n")
           + String.format("| Name         | Quantity    | Price per unit  | Expiration date  |%n")
           + String.format("+--------------+-------------+-----------------+------------------+%n");
  }
}