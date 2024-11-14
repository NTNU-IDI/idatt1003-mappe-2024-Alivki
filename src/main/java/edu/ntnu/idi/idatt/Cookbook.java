package edu.ntnu.idi.idatt;

import java.util.ArrayList;
import java.util.List;

/**
 * .
 */
public class Cookbook {
  private final List<Recipe> recipes;

  /**
   * Using array list because it is dynamic array with many useful methods.
   */
  public Cookbook() {
    recipes = new ArrayList<>();
  }

  /**
   * .
   */
  public void addRecipe(Recipe recipe) {
    if (recipes.contains(recipe)) {
      System.out.println("Recipe is already in the cookbook!");
    }

    recipes.add(recipe);
  }

  /**
   * .
   */
  public void printCookbookContent() {
    if (recipes.isEmpty()) {
      System.out.printf("There is no groceries in your fridge!%n");
      return;
    }

    System.out.print(printCookbookHeader("Cookbook"));
    System.out.print(printCookbookBody());
  }

  /**
   * Printing header for cookbook content table.
   */
  private String printCookbookHeader(String title) {
    return String.format("+-----------------------------------------------------------------+%n")
        + centerString(title, 67)
        + String.format("+--------------+-------------------------------+------------------+%n")
        + String.format("| Recipe name  | Short description             | Can make?        |%n")
        + String.format("+--------------+-------------------------------+------------------+%n");
  }

  /**
   * .
   */
  private String printCookbookBody() {
    StringBuilder string = new StringBuilder();

    for (Recipe recipe : recipes) {
      String recipeName =
          recipe.getName().length() > 12 ? shortenName(recipe.getName(), 10) : recipe.getName();

      string.append(String.format("| %-12s | %-25s |     |%n", recipeName, recipe.getDescription()));
      string.append("+--------------+-------------------------------+------------------+\n");
    }

    return string.toString();
  }

  /**
   * .
   *
   * @param inputString test
   * @param rowLength   tet
   * @return test
   */
  private String centerString(String inputString, int rowLength) {
    int leftPadding;
    int rightPadding;

    if (inputString.length() > rowLength - 4) {
      inputString = inputString.substring(0, rowLength - 4) + "...";
      leftPadding = 1;
      rightPadding = 1;
    } else {
      leftPadding = (rowLength - 2 - inputString.length()) / 2;
      rightPadding =
          (rowLength - 2 - inputString.length()) % 2 == 0 ? leftPadding : leftPadding + 1;
    }

    return String.format("|%" + leftPadding + "s%s%" + rightPadding + "s|%n", "", inputString, "");
  }

  /**
   * .
   *
   * @param inputName awd
   * @return awd
   */
  private String shortenName(String inputName, int shotenFrom) {
    return inputName.substring(0, shotenFrom) + "..";
  }
}
