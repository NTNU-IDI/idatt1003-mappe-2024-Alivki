package edu.ntnu.idi.idatt;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import jdk.jshell.execution.Util;

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
    this.cookbook = new Cookbook(fridge);
    scanner = new Scanner(System.in);
  }

  /**
   * .
   */
  public void start() {
    menu();
  }

  /**
   * .
   */
  public void menu() {
    int choose = -1;

    do {
      System.out.println("----------------------------------------------");
      System.out.println("                    Menu");
      System.out.println("----------------------------------------------");
      System.out.println("\n    1. Edit and see your fridge");
      System.out.println("    2. Edit and see your cookbook");
      System.out.println("\n    0. Exit program");
      System.out.println("\n----------------------------------------------");
      System.out.println("\nType in the number you want:");

      try {
        choose = scanner.nextInt();
        scanner.nextLine();
      } catch (InputMismatchException e) {
        System.out.println("Choose a number!");
        scanner.nextLine();
        continue;
      }

      switch (choose) {
        case 1 -> {
          fridgeMenu();
          return;
        }
        case 2 -> {
          cookbookMenu();
          return;
        }
        case 0 -> System.out.println("Exiting...");
        default -> System.out.println("Choose a number, try again.");
      }

      System.out.println();
    } while (choose != 0);
  }

  /**
   * .
   */
  public void fridgeMenu() {
    int choose = -1;

    do {
      System.out.println("----------------------------------------------");
      System.out.println("                 Fridge menu");
      System.out.println("----------------------------------------------");
      System.out.println("\n    1. Add a grocery to the fridge");
      System.out.println("    2. Remove grocery from the fridge");
      System.out.println("    3. Increase quantity to a grocery");
      System.out.println("    4. Decrease quantity to a grocery");
      System.out.println("    5. See fridge overview and total price");
      System.out.println("    6. See a specific item");
      System.out.println("    7. See all groceries that has expired");
      System.out.println("\n    8. Go back to menu");
      System.out.println("    0. Exit program");
      System.out.println("\n----------------------------------------------");
      System.out.println("\nType in the number you want:");

      try {
        choose = scanner.nextInt();
        scanner.nextLine();
      } catch (InputMismatchException e) {
        System.out.println("Choose a number!");
        scanner.nextLine();
        continue;
      }

      switch (choose) {
        case 1 -> {
          addGrocery();
          return;
        }
        case 2 -> {
          removeGrocery();
          return;
        }
        case 3 -> {
          increaseQuantity();
          return;
        }
        case 4 -> {
          decreaseQuantity();
          return;
        }
        case 5 -> {
          printGroceries();
          return;
        }
        case 6 -> {
          printGrocery();
          return;
        }
        case 7 -> {
          expiredGroceries();
          return;
        }
        case 8 -> {
          menu();
          return;
        }
        case 0 -> System.out.println("Exiting...");
        default -> System.out.println("Choose a number, try again.");
      }

      System.out.println();
    } while (choose != 0);
  }

  /**
   * .
   */
  private void addGrocery() {
    Object[] quantityAndUnitObj = null;
    LocalDate expirationDate = null;
    String groceryName = "";
    String quantityAndUnit = "";
    float price = 0;

    try {
      System.out.println("Write the grocery name");
      groceryName = Utils.stringInput();

      System.out.println("Write the total price you payed for the grocery");
      price = Utils.floatInput();

      System.out.println("Write the quantity and the unit of the grocery");
      System.out.println("Example: 2 kg, 1.5 L");
      quantityAndUnit = Utils.quantityAndUnitInput();
      quantityAndUnitObj = InputValidation.unitConversion(quantityAndUnit);

      System.out.println("Write the expiration date of the grocery in the format dd.MM.yyyy");
      expirationDate = Utils.dateInput();
    } catch (IllegalArgumentException | InputMismatchException | DateTimeParseException e) {
      if (e instanceof DateTimeParseException) {
        System.err.print("Invalid date format, please use dd.MM.yyyy");
        addGrocery();
        return;
      }
      System.err.println(e.getMessage());
      addGrocery();
    }

    float pricePerUnit = price / (float) quantityAndUnitObj[0];

    Grocery newGrocery = new Grocery(groceryName, (String) quantityAndUnitObj[1], pricePerUnit);
    GroceryItem newGroceryItem =
        new GroceryItem(newGrocery, expirationDate, (float) quantityAndUnitObj[0]);

    System.out.print(fridge.addGrocery(newGroceryItem));

    fridgeMenu();
  }

  /**
   * .
   */
  private void removeGrocery() {
    String groceryName = "";

    try {
      System.out.println("Write the name of the grocery you want to remove.");
      groceryName = Utils.stringInput();
    } catch (IllegalArgumentException e) {
      System.err.println(e.getMessage());
      removeGrocery();
    }

    System.out.print(fridge.removeGrocery(groceryName));
    fridgeMenu();
  }

  /**
   * .
   */
  private void increaseQuantity() {
    String groceryName = "";
    float inputQuantity = 0;

    try {
      System.out.println("Write the grocery you want to add more of.");
      groceryName = Utils.stringInput();

      System.out.println("Write how much you are adding the grocery in the format without unit");
      inputQuantity = Utils.floatInput();
    } catch (IllegalArgumentException e) {
      System.err.println(e.getMessage());
      increaseQuantity();
    }

    System.out.print(fridge.increaseQuantity(groceryName, inputQuantity));
    fridgeMenu();
  }

  /**
   * .
   */
  private void decreaseQuantity() {
    String groceryName = "";
    float inputQuantity = 0;

    try {
      System.out.println("Write the name of the grocery you want to decrease the quantity of.");
      groceryName = Utils.stringInput();

      System.out.println("Write how much you want to remove without unit");
      inputQuantity = Utils.floatInput();
    } catch (IllegalArgumentException e) {
      System.err.println(e.getMessage());
      increaseQuantity();
    }

    System.out.print(fridge.decreaseQuantity(groceryName, inputQuantity));
    fridgeMenu();
  }

  /**
   * .
   */
  private void printGroceries() {
    System.out.print(fridge.printFridgeContent());
    fridgeMenu();
  }

  /**
   * .
   */
  private void printGrocery() {
    String groceryName = "";

    try {
      System.out.println("Write the name of the grocery you are looking for.");
      groceryName = Utils.stringInput();
    } catch (IllegalArgumentException e) {
      System.err.println(e.getMessage());
      removeGrocery();
    }

    System.out.print(fridge.printGrocery(groceryName));
    fridgeMenu();
  }

  /**
   * .
   */
  private void expiredGroceries() {
    LocalDate today = LocalDate.now();
    System.out.print(fridge.bestBeforeDate(today));
    fridgeMenu();
  }

  /**
   * .
   */
  private void cookbookMenu() {
    int choose = -1;

    do {
      System.out.println("----------------------------------------------");
      System.out.println("                Cookbook menu");
      System.out.println("----------------------------------------------");
      System.out.println("\n    1. Add recipe");
      System.out.println("    2. Remove recipe");
      System.out.println("    3. See cookbook");
      System.out.println("    4. See recipe");
      System.out.println("    5. See recipe suggestions");
      System.out.println("\n    8. Go back to menu");
      System.out.println("    0. Exit program");
      System.out.println("\n----------------------------------------------");
      System.out.println("\nType in the number you want:");

      try {
        choose = scanner.nextInt();
        scanner.nextLine();
      } catch (InputMismatchException e) {
        System.out.println("Choose a number!");
        scanner.nextLine();
        continue;
      }

      switch (choose) {
        case 1 -> {
          addRecipe();
          return;
        }
        case 2 -> {
          removeRecipe();
          return;
        }
        case 3 -> {
          printCookbook();
          return;
        }
        case 4 -> {
          seeRecipe();
          return;
        }
        case 5 -> {
          System.out.println("test");
          return;
        }
        case 8 -> {
          menu();
          return;
        }
        case 0 -> System.out.println("Exiting...");
        default -> System.out.println("Choose a number, try again.");
      }

      System.out.println();
    } while (choose != 0);
  }

  /**
   * .
   */
  private void addRecipe() {
    Object[] quantityAndUnitObj = null;
    Map<Grocery, Float> groceries = new HashMap<>();
    String recipeName = "";
    String recipeDescription = "";
    String recipeProcedure = "";
    int recipeServings = 0;

    try {
      System.out.println("Write the recipe name");
      recipeName = Utils.stringInput();

      System.out.println("Write a short description for the recipe.");
      recipeDescription = Utils.stringInput();

      System.out.println("Write the procedure for the recipe.");
      recipeProcedure = Utils.stringInput();

      System.out.println("Write how many servings this recipe will produce.");
      recipeServings = Utils.intInput();
    } catch (IllegalArgumentException e) {
      System.err.println(e.getMessage());
      addRecipe();
    }

    System.out.println("Next step is to input all groceries in needed in the recipe.");
    System.out.println(
        "When you are done inputting all the groceries write 0 instead of grocery name.");

    String groceryName = "1";
    while (Integer.parseInt(groceryName) != 0) {
      System.out.println("Write the grocery name.");
      groceryName = Utils.quantityAndUnitInput();

      if (groceryName.equalsIgnoreCase("0")) {
        break;
      }

      System.out.println("Write the quantity of the grocery.");
      System.out.println("Example: 2.2kg, 2l, 200g");
      String groceryQuantityAndUnit = Utils.quantityAndUnitInput();

      System.out.println("Write the price per unit of the grocery");
      float pricePerUnit = Utils.floatInput();

      try {
        quantityAndUnitObj = InputValidation.unitConversion(groceryQuantityAndUnit);
      } catch (IllegalArgumentException e) {
        System.err.println(e.getMessage());
        addRecipe();
      }

      Grocery newGrocery = new Grocery(groceryName, (String) quantityAndUnitObj[1], pricePerUnit);

      groceries.put(newGrocery, (Float) quantityAndUnitObj[0]);

      if (!groceryName.equalsIgnoreCase("0")) {
        groceryName = "1";
      }
    }

    Recipe newRecipe =
        new Recipe(recipeName, recipeDescription, recipeProcedure, recipeServings);

    System.out.println(newRecipe.addGroceries(groceries));
    System.out.println(cookbook.addRecipe(newRecipe));

    cookbookMenu();
  }

  /**
   * .
   */
  private void removeRecipe() {
    String recipeName;

    System.out.println("Write the name of the recipe.");
    recipeName = Utils.stringInput();

    System.out.println(cookbook.removeRecipe(recipeName));
    cookbookMenu();
  }

  /**
   * .
   */
  private void printCookbook() {
    System.out.println(cookbook.printCookbookContent());
    cookbookMenu();
  }

  /**
   * .
   */
  private void seeRecipe() {
    String recipeName;

    System.out.println("Write the name of the recipe.");
    recipeName = Utils.stringInput();

    System.out.println(cookbook.printRecipe(recipeName));
    cookbookMenu();
  }
}
