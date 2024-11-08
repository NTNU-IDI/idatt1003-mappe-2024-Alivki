package edu.ntnu.idi.idatt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * .
 */
public class Recipe {
  private final String name;
  private final String description;
  private final String procedure;
  private final Map<Integer, Grocery> groceries;
  private final int servings;

  /**
   * .
   *
   * @param name         name
   * @param description  description
   * @param procedure    procedure
   * @param newGroceries groceries
   * @param servings     servings
   */
  public Recipe(
      String name, String description, String procedure,
      List<Grocery> newGroceries, int servings) {
    this.groceries = new HashMap<>();

    this.name = name;
    this.description = description;
    this.procedure = procedure;
    this.servings = servings;

    addGroceries(newGroceries);
    //int key = 0;
    //for (Grocery grocery : newGroceries) {
    //  this.groceries.put(key + 1, grocery);
    //}
  }

  /**
   * .
   *
   * @return name
   */
  public String getName() {
    return name;
  }

  /**
   * .
   *
   * @return description
   */
  public String getDescription() {
    return description;
  }

  /**
   * .
   *
   * @return procedure
   */
  public String getProcedure() {
    return procedure;
  }

  /**
   * .
   *
   * @return groceries
   */
  public Map<Integer, Grocery> getGroceries() {
    return groceries;
  }

  /**
   * .
   *
   * @return servings
   */
  public int getServings() {
    return servings;
  }

  /**
   * .
   */
  public void addGroceries(List<Grocery> newGroceries) {
    if (groceries.isEmpty()) {
      int key = 0;

      for (Grocery grocery : newGroceries) {
        this.groceries.put(key, grocery);
        key += 1;
      }

      System.out.println("Recipe was successfully created!");
      return;
    }

    for (Grocery grocery : newGroceries) {
      if (groceries.containsValue(grocery)) {
        System.out.printf("%s is already in recipe!%n", grocery.getName());
      }

      groceries.put(groceries.size(), grocery);
      System.out.printf("%s was added to the recipe!%n", grocery.getName());
    }
  }

  /**
   * .
   */
  public void removeGroceries(List<Grocery> removeGroceries) {
    for (Grocery grocery : removeGroceries) {
      if (groceries.containsValue(grocery)) {
        Integer key = groceries.entrySet().stream()
            .filter(entry -> entry.getValue().getName().equalsIgnoreCase(grocery.getName()))
            .map(Map.Entry::getKey).findFirst().orElse(null);

        groceries.remove(key, grocery);

        System.out.printf("%s was removed from recipe!%n", grocery.getName());
        return;
      }

      System.out.printf("%s is not in recipe!%n", grocery.getName());
    }
  }

  /**
   * .
   *
   * @return string test
   */
  @Override
  public String toString() {
    StringBuilder string = new StringBuilder();

    //groceries.forEach((k, v) ->
    //        string.append(v.getName() + "\n")
    //);

    //return String.format("%s%n %s%n Number of servings: %s%n%n %s%n%n Ingredients:%n%s",
    //    name, description, servings, procedure, string);

    string.append(printRecipeHeader());
    string.append(printTest1(groceries, description, servings, procedure));
    string.append("+----------------+------------------------------------------+\n");

    return string.toString();
  }

  /**
   * .
   */
  public String printRecipeHeader() {
    int totalWidth = 61;
    int leftPadding = (totalWidth - 2 - name.length()) / 2;
    int rightPadding = (totalWidth - 2 - name.length()) % 2 == 0 ? leftPadding : leftPadding + 1;

    return String.format("+-----------------------------------------------------------+%n")
        + String.format("|%" + leftPadding + "s%s%" + rightPadding + "s|%n", "", name, "")
        + String.format("+----------------+------------------------------------------+%n");
  }

  public String printTest1(Map<Integer, Grocery> groceries, String description, int servings,
                           String procedure) {
    StringBuilder string = new StringBuilder();

    String[] descriptionSplit = description.split("(?<=\\G.{40})");
    String[] procedureSplit = procedure.split("(?<=\\G.{40})");

    ArrayList<String> groceryCol = new ArrayList<>();
    groceryCol.add("Ingredients:");
    ArrayList<String> infoCol = new ArrayList<>();

    int numberOfRows;
    if (groceries.size() > descriptionSplit.length + procedureSplit.length + 2) {
      numberOfRows = groceries.size() + 1;
    } else {
      numberOfRows = descriptionSplit.length + procedureSplit.length + 2;
    }

    for (int i = 0; i < numberOfRows; i++) {
      if (i < descriptionSplit.length) {
        infoCol.add(descriptionSplit[i]);
      } else if (i == descriptionSplit.length) {
        infoCol.add(String.format("Number of servings: %d", servings));
      } else if (i == descriptionSplit.length + 1) {
        infoCol.add("-".repeat(40));
      } else if (i >= descriptionSplit.length + 2
          && i < descriptionSplit.length + procedureSplit.length + 2) {
        infoCol.add(procedureSplit[i - (descriptionSplit.length + 2)]);
      } else {
        infoCol.add(" ");
      }
    }

    int i = 0;
    for (Map.Entry<Integer, Grocery> entry : groceries.entrySet()) {
      if (i < numberOfRows) {
        groceryCol.add(entry.getValue().getName());
      }
      i++;
    }
    while (groceryCol.size() < numberOfRows) {
      groceryCol.add(" ");
    }

    for (int j = 0; j < numberOfRows; j++) {
      string.append(String.format("| %-14s | %-40s |%n", groceryCol.get(j), infoCol.get(j)));
    }

    return string.toString();
  }
}
