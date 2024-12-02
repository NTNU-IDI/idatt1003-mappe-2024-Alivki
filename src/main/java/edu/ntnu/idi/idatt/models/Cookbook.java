package edu.ntnu.idi.idatt.models;

import edu.ntnu.idi.idatt.utils.StringManipulation;
import java.util.ArrayList;
import java.util.IllegalFormatPrecisionException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * .
 */
public class Cookbook {
  private final List<Recipe> recipes;
  private final Fridge fridge;

  /**
   * Using array list because it is dynamic array with many useful methods.
   */
  public Cookbook(Fridge fridge) {
    recipes = new ArrayList<>();
    this.fridge = fridge;
  }

  /**
   * .
   */
  public String addRecipe(Recipe recipe) {
    if (recipeExist(recipe)) {
      return "Recipe is already in the cookbook!";
    }

    recipes.add(recipe);
    return "Recipe was added to the cookbook!";
  }

  /**
   * .
   */
  public String removeRecipe(String inputName) {
    if (recipes.isEmpty()) {
      return "You have no recipes in the cookbook!";
    }

    recipes.removeIf(recipe -> recipe.getName().equalsIgnoreCase(inputName));
    return String.format("Recipe for %s was removed from the cookbook!", inputName);
  }

  /**
   * .
   */
  public boolean canMakeRecipe(Recipe recipeToCheck) {
    List<GroceryItem> groceries = fridge.getGroceries();
    Map<Grocery, Float> neededGroceries = recipeToCheck.getGroceries();

    return neededGroceries.entrySet().stream().allMatch(entry -> groceries.stream().filter(
            groceryItem -> groceryItem.getGrocery().getName()
                .equalsIgnoreCase(entry.getKey().getName())).mapToDouble(GroceryItem::getQuantity)
        .sum() >= entry.getValue());
  }

  /**
   * .
   */
  public List<String> missingGroceries(String recipeName) {
    Optional<Recipe> recipeToCheck = findRecipe(recipeName);
    ArrayList<String> missingGroceries = new ArrayList<>();
    List<GroceryItem> groceries = fridge.getGroceries();
    Map<Grocery, Float> neededGroceries = recipeToCheck.get().getGroceries();

    for (Map.Entry<Grocery, Float> entry : neededGroceries.entrySet()) {
      boolean found = false;
      for (GroceryItem groceryItem : groceries) {
        if (entry.getKey().getName().equalsIgnoreCase(groceryItem.getGrocery().getName())) {
          found = true;
          if (groceryItem.getQuantity() < entry.getValue()) {
            float missingQuantity = entry.getValue() - groceryItem.getQuantity();
            missingGroceries.add(groceryItem.getGrocery().getName() + " " + missingQuantity + groceryItem.getGrocery().getUnit());
          }
          break;
        }
      }

      if (!found) {
        missingGroceries.add(entry.getKey().getName() + " " + entry.getValue() + entry.getKey().getUnit());
      }
    }

    for (String test : missingGroceries) {
      System.out.println(test);
    }

    return missingGroceries;
  }

  /**
   * .
   */
  public void printRecipeSuggestions() {
    if (recipes.isEmpty()) {
      System.out.printf("There is no groceries in your fridge!%n");
      return;
    }

    System.out.print(printCookbookHeader("Recipe suggestions"));
    System.out.printf(
        "+----------------------------------------------------------------------------+%n");
    System.out.print(StringManipulation.centerString("Recipe suggestions", 78));
    System.out.printf(
        "+----------------------------------------------------------------------------+%n");
    // System.out.print(recipeSuggestions());
  }

  /**
   * Finding a specific grocery by name.
   *
   * @param inputName The name of the grocery to search for.
   */
  public String printRecipe(String inputName) {
    Optional<Recipe> foundRecipe = findRecipe(inputName);

    if (foundRecipe.isEmpty()) {
      return String.format("%s is not in the cookbook!%n", inputName);
    }

    return foundRecipe.get().printRecipe();
  }

  /**
   * .
   */
  public String printCookbookContent() {
    if (recipes.isEmpty()) {
      return String.format("There is no recipes in your cookbook!%n");
    }

    return String.format("%s%s", printCookbookHeader("Cookbook"), printCookbookBody());
  }

  /**
   * Printing header for cookbook content table.
   */
  private String printCookbookHeader(String title) {
    return String.format(
        "+----------------------------------------------------------------------------+%n")
        + StringManipulation.centerString(title, 78)
        + String.format(
        "+------------------+---------------------------------------------+-----------+%n")
        + String.format(
        "| Recipe name      | Short description                           | Can make? |%n")
        + String.format(
        "+------------------+---------------------------------------------+-----------+%n");
  }

  /**
   * .
   */
  private String printCookbookBody() {
    StringBuilder string = new StringBuilder();

    for (Recipe recipe : recipes) {
      String recipeName =
          recipe.getName().length() > 16 ? StringManipulation.shortenString(recipe.getName(), 14) :
              recipe.getName();

      String recipeDescription =
          recipe.getDescription().length() > 43
              ? StringManipulation.shortenString(recipe.getDescription(), 41) :
              recipe.getDescription();

      String canMake = canMakeRecipe(recipe) ? "Yes" : "No";

      string.append(
          String.format("| %-16s | %-43s | %-9s |%n", recipeName, recipeDescription, canMake));
      string.append(
          "+------------------+---------------------------------------------+-----------+\n");
    }

    return string.toString();
  }

  /**
   * .
   */
  private boolean recipeExist(Recipe inputRecipe) {
    return recipes.stream().anyMatch(recipe -> recipe.equals(inputRecipe));
  }

  /**
   * .
   */
  private Optional<Recipe> findRecipe(String inputName) {
    return recipes.stream()
        .filter(recipe -> recipe.getName().equalsIgnoreCase(inputName))
        .findFirst();
  }
}
