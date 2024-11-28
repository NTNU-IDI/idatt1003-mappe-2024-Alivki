package edu.ntnu.idi.idatt.models;

import edu.ntnu.idi.idatt.utils.StringManipulation;
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
  private final Map<Grocery, Float> groceries;
  private final int servings;

  /**
   * .
   *
   * @param name        name
   * @param description description
   * @param procedure   procedure
   * @param servings    servings
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
  public Map<Grocery, Float> getGroceries() {
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
  public String addGroceries(Map<Grocery, Float> newGroceries) {
    if (groceries.isEmpty()) {
      this.groceries.putAll(newGroceries);

      return "Recipe was successfully created!";
    }

    newGroceries.forEach((grocery, quantity) -> {
      if (!groceries.containsKey(grocery)) {
        groceries.put(grocery, quantity);
      }
    });

    return "All groceries added";
  }

  /**
   * .
   */
  public String removeGroceries(List<String> removeGroceries) {
    for (String groceryName : removeGroceries) {
      groceries.forEach((grocery, quantity) -> {
        if (grocery.getName().equalsIgnoreCase(groceryName)) {
          groceries.remove(grocery);
        }
      });
    }

    return "All groceries were removed from the recipe!";
  }

  /**
   * .
   *
   * @return string test
   */
  public String printRecipe() {
    StringBuilder string = new StringBuilder();

    string.append(makeRecipeHeader());
    string.append(makeRecipeBody(groceries, description, servings, procedure));
    string.append("+-------------------+------------------------------------------+\n");

    return string.toString();
  }


  /**
   * .
   */
  public String makeRecipeHeader() {
    String inputName = name;
    int leftPadding;
    int rightPadding;
    int totalWidth = 64;

    if (inputName.length() > 57) {
      inputName = inputName.substring(0, 57) + "...";
      leftPadding = 1;
      rightPadding = 1;
    } else {
      leftPadding = (totalWidth - 2 - inputName.length()) / 2;
      rightPadding = (totalWidth - 2 - inputName.length()) % 2 == 0 ? leftPadding : leftPadding + 1;
    }

    return String.format("+--------------------------------------------------------------+%n")
        + String.format("|%" + leftPadding + "s%s%" + rightPadding + "s|%n", "", inputName, "")
        + String.format("+-------------------+------------------------------------------+%n");
  }

  /**
   * .
   *
   * @param groceries   groceries
   * @param description description
   * @param servings    servings
   * @param procedure   procedure
   * @return string
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

    for (int j = 0; j < numberOfRows; j++) {
      string.append(String.format("| %-17s | %-40s |%n", groceryColum.get(j), infoColum.get(j)));
    }

    return string.toString();
  }

  /**
   * .
   *
   * @param numberOfRows tes
   * @return test
   */
  private ArrayList<String> makeGroceryColum(int numberOfRows) {
    ArrayList<String> groceryColum = new ArrayList<>();

    groceryColum.add("Ingredients:");

    int i = 0;
    for (Map.Entry<Grocery, Float> entry : groceries.entrySet()) {
      String outputName =
          entry.getKey().getName().length() > 10
              ? StringManipulation.shortenString(entry.getKey().getName(), 8)
              : entry.getKey().getName();

      if (i < numberOfRows) {
        groceryColum.add(String.format("%-10s%5.2f%s",
            outputName,
            entry.getValue(),
            entry.getKey().getUnit()));
      }
      i++;
    }

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
   * .
   *
   * @param descriptionSplit fes
   * @param procedureSplit   sef
   * @param numberOfRows     fes
   * @return fes
   */
  private ArrayList<String> makeDescriptionColum(String[] descriptionSplit,
                                                 String[] procedureSplit, int numberOfRows) {
    ArrayList<String> infoColum = new ArrayList<>();

    for (int i = 0; i < numberOfRows; i++) {
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
    }

    return infoColum;
  }

  /**
   * .
   *
   * @return string test
   */
  //@Override
  //public String toString() {
  //}
}
