package edu.ntnu.idi.idatt.models;

import edu.ntnu.idi.idatt.utils.StringManipulation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Represents a cookbook containing a collection of recipes and access to a fridge.
 *
 * <p>The {@code Cookbook} class is designed to
 * manage a list of recipes and to interact with a {@link Fridge}.
 * {@link Fridge} object that provides access to stored groceries</p>
 *
 * @author Iver Lindholm
 * @version v2.0
 * @since v1.1
 */
public class Cookbook {
  private final List<Recipe> recipes;
  private final Fridge fridge;

  /**
   * Constructs a new {@code Cookbook} with an empty recipe list and the specified fridge.
   *
   * @param fridge the {@link Fridge} instance representing the user's fridge
   */
  public Cookbook(Fridge fridge) {
    recipes = new ArrayList<>();
    this.fridge = fridge;
  }

  /**
   * Gets the list of {@link Recipe} object stored in the cookbook.
   *
   * @return a {@link List} of {@link Recipe} objects
   */
  public List<Recipe> getRecipes() {
    return recipes;
  }

  /**
   * Adds a recipe to the cookbook if it does not already exist.
   *
   * <p>Method checks if the provided recipe is in array list
   * before adding it returning a success message or a message
   * indicating the recipe is in the cookbook.</p>
   *
   * @param recipe the {@link Recipe} object to be added to the cookbook
   * @return a {@link String} message indicating the result of the operation.
   */
  public String addRecipe(Recipe recipe) {
    if (recipeExist(recipe)) {
      return "Recipe is already in the cookbook!";
    }

    recipes.add(recipe);
    return "Recipe was added to the cookbook!";
  }

  /**
   * Removes the recipe from array list.
   *
   * <p>Method checks if the provided string is a object in array list
   * and removes the object it is.</p>
   *
   * @param inputName the string input name from user
   * @return a {@link String} message indicating the result of the operation.
   */
  public String removeRecipe(String inputName) {
    if (recipes.isEmpty()) {
      return "You have no recipes in the cookbook!";
    }

    recipes.removeIf(recipe -> recipe.getName().equalsIgnoreCase(inputName));
    return String.format("Recipe for %s was removed from the cookbook!", inputName);
  }

  /**
   * Checks if the recipe can be made with current groceries in fridge.
   *
   * <p>Method gets {@link Fridge} groceries and check if any groceries are
   * missing from the list of groceries in specified {@link Recipe}</p>
   *
   * @param recipeToCheck the {@link Recipe} to be checked
   * @return a {@link Boolean} indication if recipe can be made or not
   */
  private boolean canMakeRecipe(Recipe recipeToCheck) {
    List<GroceryItem> groceries = fridge.getGroceries();
    Map<Grocery, Float> neededGroceries = recipeToCheck.getGroceries();

    return neededGroceries.entrySet().stream().allMatch(entry -> groceries.stream().filter(
            groceryItem -> groceryItem.getGrocery().getName()
                .equalsIgnoreCase(entry.getKey().getName())).mapToDouble(GroceryItem::getQuantity)
        .sum() >= entry.getValue());
  }

  /**
   * Makes a table for recipe suggestions to be shown for user.
   *
   * @return a {@link String} containing error message or the table of suggestions
   */
  public String recipeSuggestions() {
    if (recipes.isEmpty()) {
      return "There is no groceries in your fridge!\n";
    }

    return """
        +--------------------------------------------------+
        %s+--------------------------------------------------+
        | Recipe Name     | Missing groceries              |
        +-----------------+--------------------------------+
        %s
        """.formatted(StringManipulation.centerString("Recipe suggestions", 52),
        makeRecipeSuggestionsBody());
  }

  /**
   * Makes the body of the recipe suggestion table.
   *
   * <p>Method checks if the recipe can be made or if it is missing
   * groceries. If none are missing</p>
   *
   * @return a {@link String} containing rows of recipe suggestion table for all recipes
   */
  private String makeRecipeSuggestionsBody() {
    StringBuilder string = new StringBuilder();

    for (Recipe recipe : recipes) {
      if (canMakeRecipe(recipe)) {
        string.append("""
            | %-15s | You have every thing you need  |
            |                 | Go make it if you want!        |
            +-----------------+--------------------------------+
            """.formatted(recipe.getName()));
      } else {
        if (numberOfMissingGroceries(recipe)) {
          string.append(missingGroceries(recipe));
        }
      }
    }

    return string.toString();
  }

  /**
   * Checks if the {@link Fridge} is missing more than 2 groceries for specified recipe.
   *
   * @param recipe the {@link Recipe} object to check
   * @return a {@link Boolean} indication if the fridge are missing 2 or more groceries
   */
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
   * Making rows for recipe suggestion table for recipe missing groceries.
   *
   * @param recipe the {@link Recipe} object to make rows for
   * @return a {@link String} containing rows for recipe suggestion table
   */
  private String missingGroceries(Recipe recipe) {
    List<GroceryItem> groceries = fridge.getGroceries();
    Map<Grocery, Float> neededGroceries = recipe.getGroceries();
    StringBuilder string = new StringBuilder();
    List<String> missing = new ArrayList<>();

    for (Map.Entry<Grocery, Float> entry : neededGroceries.entrySet()) {
      for (GroceryItem groceryItem : groceries) {
        if (!entry.getKey().getName().equalsIgnoreCase(groceryItem.getGrocery().getName())) {
          missing.add(entry.getKey().getName());
          break;
        }
      }
    }

    if (missing.size() <= 1) {
      string.append("""
          | %-15s | You only need one more grocery |
          |                 | to make this recipe!           |
          |                 | 1. %-27s |
          +-----------------+--------------------------------+
          """.formatted(recipe.getName(), missing.getFirst()));
    } else {
      string.append("""
          | %-15s | You need two more groceries to |
          |                 | make this recipe!              |
          |                 | 1. %-27s |
          |                 | 2. %-27s |
          +-----------------+--------------------------------+
          """.formatted(recipe.getName(), missing.get(0), missing.get(1)));
    }

    return string.toString();
  }

  /**
   * Prints recipe from storage to user.
   *
   * @param inputName The name of the recipe to search for.
   * @return a {@link String} containing the information about the recipe
   */
  public String printRecipe(String inputName) {
    Optional<Recipe> foundRecipe = findRecipe(inputName);

    if (foundRecipe.isEmpty()) {
      return String.format("%s is not in the cookbook!%n", inputName);
    }

    return foundRecipe.get().printRecipe();
  }

  /**
   * Returns a content table containing the recipes of the cookbook.
   *
   * <p>Checks if there are recipes in the cookbook and returns
   * correct information</p>
   *
   * @return a {@link String} containing the cookbook content or error message
   */
  public String printCookbookContent() {
    if (recipes.isEmpty()) {
      return String.format("There is no recipes in your cookbook!%n");
    }

    return String.format("%s%s", printCookbookHeader(), printCookbookBody());
  }

  /**
   * Makes header for cookbook content table.
   *
   * @return a {@link String} containing the content table for the cookbook
   */
  private String printCookbookHeader() {
    return """
        +----------------------------------------------------------------------------+
        %s+------------------+---------------------------------------------+-----------+
        | Recipe name      | Short description                           | Can make? |
        +------------------+---------------------------------------------+-----------+
        """.formatted(StringManipulation.centerString("Cookbook", 78));
  }

  /**
   * Makes cookbook content table body.
   *
   * <p>Method creates the rows of the content table body. It checks if the
   * recipe can be made and return the correct string if it can or not</p>
   *
   * @return a {@link String} containing the rows of the content table body
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
   * Checks if recipe exist in the cookbook.
   *
   * @return a {@link Boolean} indicating if the recipe exist or not
   */
  private boolean recipeExist(Recipe inputRecipe) {
    return recipes.stream().anyMatch(recipe -> recipe.equals(inputRecipe));
  }

  /**
   * Find a recipe in the cookbook array list from name.
   *
   * @param inputName input name in string from
   * @return a {@link Optional} containing {@link Recipe} if recipe exist
   */
  private Optional<Recipe> findRecipe(String inputName) {
    return recipes.stream()
        .filter(recipe -> recipe.getName().equalsIgnoreCase(inputName))
        .findFirst();
  }
}
