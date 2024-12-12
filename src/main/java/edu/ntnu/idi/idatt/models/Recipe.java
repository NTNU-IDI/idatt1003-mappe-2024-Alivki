package edu.ntnu.idi.idatt.models;

import edu.ntnu.idi.idatt.utils.StringManipulation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * Represents a recipe with its details.
 *
 * <p>the {@code Recipe} class is designed to store
 * information about a recipe.</p>
 *
 * @author Alivki
 */
public class Recipe {
  private final String name;
  private final String description;
  private final String procedure;
  private final Map<Grocery, Float> groceries;
  private final int servings;

  /**
   * Constructs a new {@code Recipe} with all specified details.
   *
   * @param name        The name of the recipe item.
   * @param description Description of the recipe
   * @param procedure   Procedure on how to make recipe
   * @param servings    Number of servings recipe can produce
   */
  public Recipe(
      String name, String description, String procedure, int servings) {
    this.name = name;
    this.description = description;
    this.procedure = procedure;
    this.servings = servings;
    this.groceries = new HashMap<>();
  }

  /**
   * Gets the name of the recipe.
   *
   * @return a {@link String} with the name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the description of the recipe.
   *
   * @return a {@link String} with the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Gets the procedure of the recipe.
   *
   * @return a {@link String} with the procedure
   */
  public String getProcedure() {
    return procedure;
  }

  /**
   * Gets the list of groceries to make recipe.
   *
   * @return a {@link Map} with {@link Grocery} and {@link Float} of the quantity
   */
  public Map<Grocery, Float> getGroceries() {
    return groceries;
  }

  /**
   * Gets the number of servings the recipe will produce.
   *
   * @return a {@link Integer} with number of servings
   */
  public int getServings() {
    return servings;
  }

  /**
   * Adds groceries to the recipe.
   *
   * <p>Checks if there are any groceries in the recipe. If not
   * then it adds all. If not it checks if the specific grocery
   * is in the recipe and adds it only if it does not exist</p>
   *
   * @param newGroceries {@link Map} of the grocery and their quantities.
   */
  public void addGroceries(Map<Grocery, Float> newGroceries) {
    if (groceries.isEmpty()) {
      this.groceries.putAll(newGroceries);
      return;
    }

    newGroceries.forEach((grocery, quantity) -> {
      if (!groceries.containsKey(grocery)) {
        groceries.put(grocery, quantity);
      }
    });
  }

  /**
   * Removes the groceries of the provided list.
   *
   * @param removeGroceries {@link List} of string names for groceries.
   */
  public void removeGroceries(List<String> removeGroceries) {
    groceries.entrySet().removeIf(entry ->
        removeGroceries.contains(entry.getKey().getName())
    );
  }

  /**
   * Returns a content table with all information about the recipe.
   *
   * @return a {@link String} containing the content table.
   */
  public String printRecipe() {
    StringBuilder string = new StringBuilder();

    string.append(makeRecipeHeader());
    string.append(makeRecipeBody(groceries, description, servings, procedure));
    string.append("+--------------------+------------------------------------------+\n");

    return string.toString();
  }


  /**
   * Creates the content table header for the recipe content table.
   */
  public String makeRecipeHeader() {
    String inputName = name;
    int leftPadding;
    int rightPadding;
    int totalWidth = 65;

    if (inputName.length() > 57) {
      inputName = inputName.substring(0, 57) + "...";
      leftPadding = 1;
      rightPadding = 1;
    } else {
      leftPadding = (totalWidth - 2 - inputName.length()) / 2;
      rightPadding = (totalWidth - 2 - inputName.length()) % 2 == 0 ? leftPadding : leftPadding + 1;
    }

    return String.format("+---------------------------------------------------------------+%n")
           + String.format("|%" + leftPadding + "s%s%" + rightPadding + "s|%n", "", inputName, "")
           + String.format("+--------------------+------------------------------------------+%n");
  }

  /**
   * Creates the body for the recipe content table.
   *
   * <p>Dynamically creates the table based on how long description
   * and procedure is or how many groceries the recipe has.</p>
   *
   * @param groceries   Map of recipe groceries and their quantity
   * @param description The recipe description
   * @param servings    The number of servings the recipe will produce
   * @param procedure   The recipe procedure
   * @return a {@link String} containing the consent table with recipe information
   */
  public String makeRecipeBody(Map<Grocery, Float> groceries, String description,
                               int servings, String procedure) {
    StringBuilder string = new StringBuilder();

    String[] descriptionSplit = description.split("(?<=\\G.{40})");
    String[] procedureSplit = procedure.split("(?<=\\G.{40})");

    int numberOfRows =
        Math.max(groceries.size() + 3, descriptionSplit.length + procedureSplit.length + 2);

    ArrayList<String> groceryColum = makeGroceryColum(numberOfRows);
    ArrayList<String> infoColum =
        makeDescriptionColum(descriptionSplit, procedureSplit, numberOfRows);

    IntStream.range(0, numberOfRows)
        .forEach(j -> string.append(
            String.format("| %-18s | %-40s |%n", groceryColum.get(j), infoColum.get(j))));

    return string.toString();
  }

  /**
   * Creates the left hand side of the recipe content table.
   *
   * <p>Based on the number of groceries it creates the part of the
   * content table showing the recipe groceries.</p>
   *
   * @param numberOfRows how many rows the content table should have.
   * @return a {@link ArrayList} of {@link String} with row content
   */
  private ArrayList<String> makeGroceryColum(int numberOfRows) {
    ArrayList<String> groceryColum = new ArrayList<>();

    groceryColum.add("Ingredients:");

    groceries.entrySet().stream()
        .limit(numberOfRows)
        .forEach(entry -> {
          String outputName = entry.getKey().getName().length() > 10
              ? StringManipulation.shortenString(entry.getKey().getName(), 8)
              : entry.getKey().getName();

          groceryColum.add(String.format("%-10s%5.2f%s",
              outputName,
              entry.getValue(),
              entry.getKey().getUnit()));
        });

    while (groceryColum.size() < numberOfRows - 1) {
      groceryColum.add(" ");
    }

    if (groceryColum.size() == numberOfRows - 1) {
      double totalPrice = groceries.keySet().stream()
          .mapToDouble(Grocery::getPrice)
          .reduce(0.0f, Double::sum);
      groceryColum.add(String.format("Price %.1fkr", totalPrice));
    }

    return groceryColum;
  }

  /**
   * Makes the right hand side of the recipe content table.
   *
   * <p>Based on the length of the description and procedure
   * it dynamically creates the rows.</p>
   *
   * @param descriptionSplit Array of the description split at an interval
   * @param procedureSplit   Array of the procedure split at an interval
   * @param numberOfRows     Specifies the number of rows necessary for the table
   * @return a {@link ArrayList} of {@link String} with row content
   */
  private ArrayList<String> makeDescriptionColum(String[] descriptionSplit,
                                                 String[] procedureSplit, int numberOfRows) {
    ArrayList<String> infoColum = new ArrayList<>();

    IntStream.range(0, numberOfRows)
        .forEach(i -> {
          if (i < descriptionSplit.length) {
            infoColum.add(descriptionSplit[i]);
          } else if (i == descriptionSplit.length) {
            infoColum.add(String.format("Number of servings: %d", servings));
          } else if (i == descriptionSplit.length + 1) {
            infoColum.add("-".repeat(40));
          } else if (i >= descriptionSplit.length + 2
                     && i < descriptionSplit.length + procedureSplit.length + 2) {
            infoColum.add(procedureSplit[i - (descriptionSplit.length + 2)]);
          } else {
            infoColum.add(" ");
          }
        });

    return infoColum;
  }

  /**
   * To string method for the class used in debugging.
   *
   * @return a {@link String} containing all information of the grocery item.
   */
  @Override
  public String toString() {
    return String.format("Name: %s, Description: %s, Procedure: %s, Servings: %d",
        name, description, procedure, servings);
  }

  /**
   * Override method to check if two Recipe objects equals each-other.
   *
   * @param obj the obj to check
   * @return a {@link Boolean} indication if they are equals or not.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    Recipe recipe = (Recipe) obj;

    return Objects.equals(name.toLowerCase(),
        recipe.getName().toLowerCase());
  }

  /**
   * Override method for changing the hashcode to represent the class.
   *
   * @return a {@link Integer} with the hashcode.
   */
  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
