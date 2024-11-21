package edu.ntnu.idi.idatt;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

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
    //testRecipe();
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
        scanner.next();
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
        scanner.next();
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
    LocalDate parsedExpirationDate = null;

    System.out.println("Write the grocery name");
    String name = scanner.nextLine().toLowerCase();

    System.out.println("Write the total price you payed for the grocery");
    float price = scanner.nextFloat();
    scanner.nextLine();

    System.out.println("Write the quantity and the unit of the grocery");
    System.out.println("Example: 2 kg, 1.5 L");
    String quantityAndUnit = scanner.nextLine();

    System.out.println("Write the expiration date of the grocery in the format dd.MM.yyyy");
    String expirationDate = scanner.nextLine();

    try {
      parsedExpirationDate =
          LocalDate.parse(expirationDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
      quantityAndUnitObj = InputValidation.unitConversion(quantityAndUnit);
      InputValidation.isNotEmpty(expirationDate);
      InputValidation.isNotEmpty(name);
      InputValidation.isValidFloat(price);
      InputValidation.nameUnder32Char(name);

      InputValidation.isValidDate(parsedExpirationDate);
    } catch (IllegalArgumentException | DateTimeParseException e) {
      if (e instanceof DateTimeParseException) {
        System.err.print("Invalid date format, please use dd.MM.yyyy");
        addGrocery();
        return;
      }
      System.err.print(e.getMessage());
      addGrocery();
    }

    float pricePerUnit = price / (float) quantityAndUnitObj[0];

    Grocery newGrocery = new Grocery(name, (String) quantityAndUnitObj[1], pricePerUnit);
    GroceryItem newGroceryItem =
        new GroceryItem(newGrocery, parsedExpirationDate, (float) quantityAndUnitObj[0]);

    System.out.print(fridge.addGrocery(newGroceryItem));

    fridgeMenu();
  }

  /**
   * .
   */
  private void removeGrocery() {
    System.out.println("Write the name of the grocery you want to remove.");
    final String groceryName = scanner.nextLine();

    System.out.print(fridge.removeGrocery(groceryName));
    fridgeMenu();
  }

  /**
   * .
   */
  private void increaseQuantity() {
    System.out.println("Write the grocery you want to add more of.");
    final String groceryName = scanner.nextLine();

    System.out.println("Write how much you are adding the grocery in the format without unit");
    float addQuantity = scanner.nextFloat();
    scanner.nextLine();

    try {
      InputValidation.isValidFloat(addQuantity);
    } catch (IllegalArgumentException e) {
      System.err.print(e.getMessage());
      addGrocery();
    }

    System.out.print(fridge.increaseQuantity(groceryName, addQuantity));
    fridgeMenu();
  }

  /**
   * .
   */
  private void decreaseQuantity() {
    System.out.println("Write the grocery you want to add more of.");
    final String groceryName = scanner.nextLine();

    System.out.println("Write how much you are adding the grocery in the format without unit");
    float removeQuantity = scanner.nextFloat();
    scanner.nextLine();

    try {
      InputValidation.isValidFloat(removeQuantity);
    } catch (IllegalArgumentException e) {
      System.err.print(e.getMessage());
      addGrocery();
    }

    System.out.print(fridge.decreaseQuantity(groceryName, removeQuantity));
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
    System.out.println("Write the name of the grocery you are looking for.");
    String inputName = scanner.nextLine();

    System.out.print(fridge.printGrocery(inputName));
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
  public void cookbookMenu() {
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
        scanner.next();
        continue;
      }

      switch (choose) {
        case 1 -> {
          addRecipe();
          return;
        }
        case 2 -> {
          System.out.println("test");
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

    System.out.println("Write the recipe name");

    String name = scanner.nextLine().toLowerCase();
    System.out.println("Write a short description for the recipe.");
    String description = scanner.nextLine();

    System.out.println("Write the procedure for the recipe.");
    String procedure = scanner.nextLine();

    String groceryInput = "1";
    while (Integer.parseInt(groceryInput) != 0) {
      System.out.println("Write the grocery name,");
      groceryInput = scanner.nextLine();

      System.out.println("Write the quantity of the grocery.");
      System.out.println("Example: 2.2kg, 2l, 200g");
      System.out.println("\n Input 0 when done:");

      String groceryQuantityAndUnit = scanner.nextLine();

      try {
        quantityAndUnitObj = InputValidation.unitConversion(groceryQuantityAndUnit);
      } catch (IllegalArgumentException e) {
        System.err.println(e.getMessage());
        addRecipe();
      }

      Grocery newGrocery = new Grocery(groceryInput, (String) quantityAndUnitObj[1], 0f);

      groceries.put(newGrocery, (Float) quantityAndUnitObj[0]);

      if (!groceryInput.equalsIgnoreCase("0")) {
        groceryInput = "1";
      }
    }

    System.out.println("Write how many servings this recipe will produce.");
    int servings = scanner.nextInt();
    scanner.nextLine();

    try {
      InputValidation.isNotEmpty(name);
      InputValidation.isNotEmpty(description);
      InputValidation.isNotEmpty(procedure);
      InputValidation.nameUnder32Char(name);
      InputValidation.isValidInteger(servings);
    } catch (IllegalArgumentException e) {
      System.err.print(e.getMessage());
      addRecipe();
    }

    Recipe newRecipe =
        new Recipe(name, description, procedure, groceries, servings);

    cookbook.addRecipe(newRecipe);

    cookbookMenu();
  }

  /**
   * .
   */
  public void printCookbook() {
    cookbook.printCookbookContent();
    cookbookMenu();
  }

  /**
   * .
   */
  private void seeRecipe() {
    System.out.println("Write the name of the recipe.");
    String name = scanner.nextLine();

    cookbook.printRecipe(name);
    cookbookMenu();
  }

  /**
   * .
   */
  public void testRecipe() {
    HashMap<Grocery, Float> test = new HashMap<>();
    Grocery test1 = new Grocery("Cheese", "kg", 0);
    Grocery test2 = new Grocery("Milk", "L", 0);
    float test1Quan = 2f;
    float test2Quan = 1.5f;

    test.put(test1, test1Quan);
    test.put(test2, test2Quan);

    String name = "Taco";
    String description = "Meksikans rett som norge har gjort sin egen, den er bla";
    String procedure = "Putt alt sammen på en lefse";
    int servings = 4;

    InputValidation.isNotEmpty(name);
    InputValidation.isNotEmpty(description);
    InputValidation.isNotEmpty(procedure);
    InputValidation.isValidInteger(servings);

    Recipe recipee = new Recipe(name, description, procedure, test, servings);

    fridge.addGrocery(new GroceryItem(test1, LocalDate.now(), 2.2f));

    cookbook.addRecipe(recipee);

    cookbook.printCookbookContent();
  }
}
