package edu.ntnu.idi.idatt;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
  private final List<Grocery> groceries;

  /**
   * Using array list because it is dynamic array with many useful methods.
   */
  public Fridge() {
    groceries = new ArrayList<>();
  }

  /**
   * .
   */
  public List<Grocery> getGroceries() {
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
  public void addGrocery(Grocery newGrocery) {
    if (groceries.isEmpty()) {
      groceries.add(newGrocery);
      System.out.printf("%s was successfully added!", newGrocery.getName());
      return;
    }

    if (groceryExist(newGrocery)) {
      newGrocery.increaseQuantity(newGrocery.getQuantity());
      System.out.printf(
          "%nGrocery is all ready in fridge. Increased the quantity of the grocery!%n");
      return;
    }

    groceries.add(findIndex(newGrocery), newGrocery);
    System.out.printf("%n%s was successfully added!%n", newGrocery.getName());
  }

  /**
   * Finding a specific grocery by name.
   *
   * @param inputName The name of the grocery to search for.
   */
  public void printGrocery(String inputName) {
    Optional<Grocery> foundGrocery = findGrocery(inputName);

    if (foundGrocery.isEmpty()) {
      System.out.printf("You do no have %s in the fridge!", inputName);
      return;
    }

    System.out.print(printFridgeHeader());
    System.out.print(foundGrocery.get());
    System.out.format("+--------------+-----------+-----------------+------------------+%n");
  }

  /**
   * Removing a grocery by name.
   *
   * @param inputName The name of the grocery to search for.
   */
  public void removeGrocery(String inputName) {
    Optional<Grocery> foundGrocery = findGrocery(inputName);

    if (foundGrocery.isEmpty()) {
      System.out.printf("You do no have %s in the fridge!", inputName);
      return;
    }

    groceries.remove(foundGrocery.get());
    System.out.printf("%n%s was removed.%n", inputName);
  }

  /**
   * Increases the quantity of grocery by name.
   *
   * @param inputName The name of the grocery to search for.
   * @param quantity  The quantity to increase grocery quantity with.
   */
  public void increaseQuantity(String inputName, float quantity) {
    Optional<Grocery> foundGrocery = findGrocery(inputName);

    if (foundGrocery.isEmpty()) {
      System.out.printf("You do no have %s in the fridge!", inputName);
      return;
    }

    foundGrocery.get().increaseQuantity(quantity);
    System.out.printf("%n%s increase in quantity.%n", inputName);
  }

  /**
   * Decreases the quantity of grocery by name.
   * If the quantity after the decreasing operation is 0
   * it calls the removeGrocery with grocery name to remove.
   *
   * @param inputName The name of the grocery to search for.
   * @param quantity  The quantity to decrease grocery quantity with.
   */
  public void decreaseQuantity(String inputName, float quantity) {
    Optional<Grocery> foundGrocery = findGrocery(inputName);

    if (foundGrocery.isEmpty()) {
      System.out.println("You do no have this grocery in the Fridge!");
      return;
    }

    if (foundGrocery.get().getQuantity() - quantity <= 0) {
      removeGrocery(inputName);
      System.out.print("After decreasing quantity there was nothing left of the grocery!");
      return;
    }

    foundGrocery.get().decreaseQuantity(quantity);
    System.out.printf("%s decrease in quantity.%n", inputName);
  }


  /**
   * Prints a list of grocery items that have an expiration date before the specified date.
   * If no groceries with expiration date before inputDate prints no groceries exist.
   * Printed in ascending order by date.
   *
   * @param inputDate The date to check against the expiration date of the grocery item.
   */
  public void bestBeforeDate(String inputDate) {
    LocalDate parsedInputDate = LocalDate.parse(inputDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));

    if (groceries.stream()
        .noneMatch(grocery -> grocery.getExpirationDate().isBefore(parsedInputDate.plusDays(1)))) {
      System.out.printf("You have no groceries with a expiration date before %s.%n", inputDate);
      return;
    }

    System.out.print(printFridgeHeader());
    groceries.stream()
        .filter(grocery -> grocery.getExpirationDate().isBefore(parsedInputDate.plusDays(1)))
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
   * .
   */
  public boolean groceryExist(Grocery inputGrocery) {
    for (Grocery grocery : groceries) {
      if (grocery.equals(inputGrocery)) {
        return true;
      }
    }
    return false;
  }

  /**
   * .
   */
  public Optional<Grocery> findGrocery(String inputName) {
    for (Grocery grocery : groceries) {
      if (grocery.getName().equalsIgnoreCase(inputName)) {
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
  public int findIndex(Grocery inputGrocery) {
    int left = 0;
    int right = groceries.size();

    while (left < right) {
      int mid = (left + right) / 2;
      if (groceries.get(mid).getName().compareTo(inputGrocery.getName()) < 0) {
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
