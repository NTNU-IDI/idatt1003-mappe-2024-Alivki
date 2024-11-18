package edu.ntnu.idi.idatt;

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
  public void addRecipe(Recipe recipe) {
    if (recipes.contains(recipe)) {
      System.out.println("Recipe is already in the cookbook!");
    }

    recipes.add(recipe);
    System.out.println("Recipe is added to the cookbook.");
  }

  /**
   * .
   */
  public void removeRecipe(String inputName) {
    if (recipes.isEmpty()) {
      System.out.println("No recipes in the cookbook");
    }

    recipes.removeIf(recipe -> recipe.getName().equalsIgnoreCase(inputName));
    System.out.printf("Recipe for %s was removed from the cookbook!", inputName);
  }

  /**
   * .
   */
  public boolean canMakeRecipe(Recipe recipeToCheck) {
    List<GroceryItem> groceries = fridge.getGroceries();
    Map<Grocery, Float> neededGroceries = recipeToCheck.getGroceries();
    int missingGrocery = 0;

    if (groceries.isEmpty()) {
      return false;
    }

    for (Grocery neededGrocery : neededGroceries.keySet()) {
      for (GroceryItem groceryItem : groceries) {
        if (!neededGrocery.getName().equalsIgnoreCase(groceryItem.getGrocery().getName())) {
          missingGrocery += 1;
        }
      }
    }

    return missingGrocery == 0;
  }

  /**
   * .
   */
  public List<String> missingGroceries(Recipe recipeToCheck) {
    ArrayList<String> missingGroceries = new ArrayList<>();
    List<GroceryItem> groceries = fridge.getGroceries();
    Map<Grocery, Float> neededGroceries = recipeToCheck.getGroceries();
    int missingGrocery = 0;

    for (Grocery neededGrocery : neededGroceries.keySet()) {
      for (GroceryItem groceryItem : groceries) {
        if (!neededGrocery.getName().equalsIgnoreCase(groceryItem.getGrocery().getName())) {
          missingGrocery += 1;
          missingGroceries.add(groceryItem.getGrocery().getName());
        }
      }
    }

    if (missingGrocery > 2) {
      missingGroceries.clear();
      missingGroceries.add("Missing more then 2 groceries to make this recipe");
      return missingGroceries;
    }

    if (missingGrocery < 3 && missingGrocery > 0) {
      return missingGroceries;
    }

    missingGroceries.add("No groceries missing!");
    return missingGroceries;
  }

  /**
   * Finding a specific grocery by name.
   *
   * @param inputName The name of the grocery to search for.
   */
  public void printRecipe(String inputName) {
    Optional<Recipe> foundRecipe = findRecipe(inputName);

    if (foundRecipe.isEmpty()) {
      System.out.printf("%s is not in the cookbook!%n", inputName);
      return;
    }

    System.out.print(foundRecipe.get());
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
   * .
   */
  public String recipeSuggestions() {

    return "test";
  }

  /**
   * .
   */
  private Optional<Recipe> findRecipe(String inputName) {
    for (Recipe recipe : recipes) {
      if (recipe.getName().equalsIgnoreCase(inputName)) {
        return Optional.of(recipe);
      }
    }
    return Optional.empty();
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
    System.out.print(centerString("Recipe suggestions", 78));
    System.out.printf(
        "+----------------------------------------------------------------------------+%n");
    System.out.print(recipeSuggestions());
  }

  /**
   * Printing header for cookbook content table.
   */
  private String printCookbookHeader(String title) {
    return String.format(
        "+----------------------------------------------------------------------------+%n")
        + centerString(title, 78)
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
          recipe.getName().length() > 16 ? shortenName(recipe.getName(), 14) : recipe.getName();

      String recipeDescription =
          recipe.getDescription().length() > 43 ? shortenName(recipe.getDescription(), 41) :
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
