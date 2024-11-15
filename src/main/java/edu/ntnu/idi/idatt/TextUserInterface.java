package edu.ntnu.idi.idatt;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.InputMismatchException;
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
    this.cookbook = new Cookbook();
    scanner = new Scanner(System.in);
  }

  /**
   * .
   */
  public void start() {
    menu();
    //addGrocery();
    //testRecipe();
  }

  /**
   * .
   */
  public void menu() {
    int choose = -1;

    do {
      System.out.println("--------- Menu ---------");
      System.out.println("1. Edit and see your fridge");
      System.out.println("2. Edit and see your cookbook");
      System.out.println("0. Exit program");

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
      System.out.println("--------- Fridge menu ---------");
      System.out.println("1. Add a grocery to the fridge");
      System.out.println("2. Remove grocery from the fridge");
      System.out.println("3. Increase quantity to a grocery");
      System.out.println("4. Decrease quantity to a grocery");
      System.out.println("5. See fridge overview");
      System.out.println("6. See a specific item");
      System.out.println("8. Go back to menu");
      System.out.println("0. Exit program");

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
          System.out.println("test");
          return;
        }
        case 3 -> {
          System.out.println("test");
          return;
        }
        case 4 -> {
          System.out.println("test");
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
    String name = scanner.nextLine();

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

    fridge.addGrocery(newGroceryItem);

    fridgeMenu();
  }

  /**
   * .
   */
  private void printGroceries() {
    fridge.printFridgeContent();
    fridgeMenu();
  }

  /**
   * .
   */
  private void printGrocery() {
    System.out.println("Write the name of the grocery you are looking for.");
    String inputName = scanner.nextLine();

    fridge.printGrocery(inputName);
    fridgeMenu();
  }

  /**
   * .
   */
  public void cookbookMenu() {
    int choose = -1;

    do {
      System.out.println("--------- Cookbook menu ---------");
      System.out.println("1. Add recipe");
      System.out.println("2. Remove recipe");
      System.out.println("3. See all recipes");
      System.out.println("4. See recipe");
      System.out.println("5. See total cookbook price");
      System.out.println("8. Go back to menu");
      System.out.println("0. Exit program");

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
          System.out.println("test");
          return;
        }
        case 2 -> {
          System.out.println("test");
          return;
        }
        case 3 -> {
          System.out.println("test");
          return;
        }
        case 4 -> {
          System.out.println("test");
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
}
