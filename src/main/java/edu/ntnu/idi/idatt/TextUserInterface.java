package edu.ntnu.idi.idatt;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import javax.sound.midi.Receiver;

/**
 * .
 */
public class TextUserInterface {
  private Fridge fridge;
  private Cookbook cookbook;
  private Scanner scanner;

  /**
   * .
   */
  public void init() {
    this.fridge = new Fridge();
    this.cookbook = new Cookbook();
    scanner = new Scanner(System.in);
  }

  /**
   * .
   */
  public void start() {
    addGrocery();
    //testRecipe();
  }

  public void testRecipe() {
    HashMap<Grocery, Float> test = new HashMap<>();
    Grocery test1 = new Grocery("Cheese", "kg", 0);
    Grocery test2 = new Grocery("Milk", "L", 0);
    float test1Quan = 2f;
    float test2Quan = 1.5f;

    test.put(test1, test1Quan);
    test.put(test2, test2Quan);

    String name = "taco jasdflkasdfljkasdf lkasjdf";
    String description = "Meksikans rett som norge har gjort sin egen";
    String procedure = "Putt alt sammen på en lefse";
    int servings = 4;

    InputValidation.isNotEmpty(name);
    InputValidation.isNotEmpty(description);
    InputValidation.isNotEmpty(procedure);
    InputValidation.isValidInteger(servings);

    Recipe recipee = new Recipe(name, description, procedure, test, servings);

    cookbook.addRecipe(recipee);

    cookbook.printCookbookContent();
  }

  /**
   * .
   */
  private void addGrocery() {
    System.out.println("\nEnter grocery name: ");
    String name1 = scanner.nextLine();
    float price = 1f;
    String inputExpir = "23.11.2024";
    String quants = "2.2 stk";
    Object[] quantUnit = null;
    LocalDate expir = null;

    try {
      expir = LocalDate.parse(inputExpir, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
      quantUnit = InputValidation.unitConversion(quants);
      InputValidation.isNotEmpty(inputExpir);
      InputValidation.isNotEmpty(name1);
      InputValidation.isValidFloat(price);
      InputValidation.nameUnder32Char(name1);

      InputValidation.isValidDate(expir);
    } catch (IllegalArgumentException | DateTimeParseException e) {
      if (e instanceof DateTimeParseException) {
        System.err.print("Invalid date format, please use dd.MM.yyyy");
        return;
      }
      System.err.print(e.getMessage());
    }

    Grocery grocery = new Grocery(name1, (String) quantUnit[1], price);
    GroceryItem groceryItem = new GroceryItem(grocery, expir, (float) quantUnit[0]);

    fridge.addGrocery(groceryItem);
    fridge.printGrocery("cheese");

    printGroceries();
  }

  /**
   * .
   */
  private void printGroceries() {
    fridge.printFridgeContent();
  }
}
