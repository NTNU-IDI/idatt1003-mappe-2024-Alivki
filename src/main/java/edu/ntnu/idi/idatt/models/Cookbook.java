package edu.ntnu.idi.idatt.models;

import edu.ntnu.idi.idatt.utils.StringManipulation;
import java.util.ArrayList;
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
  public void printRecipeSuggestions() {
    if (recipes.isEmpty()) {
      System.out.printf("There is no groceries in your fridge!%n");
      return;
    }

    System.out.println(String.format(
        "+--------------------------------------------------+%n")
        + StringManipulation.centerString("Recipe suggestions", 52)
        + String.format(
        "+-----------------+--------------------------------+%n")
        + String.format(
        "| Recipe Name     | Missing groceries              |%n")
        + String.format(
        "+-----------------+--------------------------------+"));
    System.out.println(makeRecipeSuggestionsBody());
  }

  private boolean numberOfMissingGroceries(Recipe recipe) {
    List<GroceryItem> groceries = fridge.getGroceries();

    return recipe.getGroceries().entrySet().stream()
        .filter(entry -> groceries.stream()
            .filter(groceryItem -> groceryItem.getGrocery().getName()
                .equalsIgnoreCase(entry.getKey().getName()))
            .map(GroceryItem::getQuantity)
            .findFirst()
            .orElse(0f) < entry.getValue())
        .limit(3)
        .count() <= 2;
  }

  /**
   * .
   */
  private String missingGroceries(Recipe recipe) {
    List<GroceryItem> groceries = fridge.getGroceries();
    Map<Grocery, Float> neededGroceries = recipe.getGroceries();
    StringBuilder string = new StringBuilder();


    for (Map.Entry<Grocery, Float> entry : neededGroceries.entrySet()) {
      for (GroceryItem groceryItem : groceries) {
        if (!entry.getKey().getName().equalsIgnoreCase(groceryItem.getGrocery().getName())) {

        }
      }
    }

    return recipe.getName() + "\n";
  }

  /**
   * .
   */
  private String makeRecipeSuggestionsBody() {
    StringBuilder string = new StringBuilder();

    for (Recipe recipe : recipes) {
      if (canMakeRecipe(recipe)) {
        string.append(String.format(
            "| %-15s | You have every thing you need  |%n|                 | Go make it if you want!        |%n+-----------------+--------------------------------+",
            recipe.getName()));
      } else {
        if (numberOfMissingGroceries(recipe)) {
          string.append(missingGroceries(recipe));
        }
      }
    }

    return string.toString();
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
