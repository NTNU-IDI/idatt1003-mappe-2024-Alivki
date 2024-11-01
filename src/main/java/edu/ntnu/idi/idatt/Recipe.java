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
      ArrayList<Grocery> newGroceries, int servings) {
    this.groceries = new HashMap<>();
    this.name = name;
    this.description = description;
    this.procedure = procedure;
    this.servings = servings;

    int key = 0;
    for (Grocery grocery : newGroceries) {
      this.groceries.put(key + 1, grocery);
    }
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
    for (Grocery grocery : newGroceries) {
      if (groceries.containsValue(grocery)) {
        System.out.printf("%s is already in recipe!%n", grocery.getName());
      }

      groceries.put(groceries.size() + 1, grocery);
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
    //StringBuilder string = new StringBuilder();

    //groceries.forEach(string.append(Grocery.getName()));
    return "test";
  }
}
