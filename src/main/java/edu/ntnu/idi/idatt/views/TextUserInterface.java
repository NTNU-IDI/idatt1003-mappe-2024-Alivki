package edu.ntnu.idi.idatt.views;

import edu.ntnu.idi.idatt.models.Cookbook;
import edu.ntnu.idi.idatt.models.Fridge;
import edu.ntnu.idi.idatt.models.Grocery;
import edu.ntnu.idi.idatt.models.GroceryItem;
import edu.ntnu.idi.idatt.models.Recipe;
import edu.ntnu.idi.idatt.utils.FlushConsole;
import edu.ntnu.idi.idatt.utils.UserInput;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * .
 */
public class TextUserInterface {
  private Fridge fridge;
  private Cookbook cookbook;

  /**
   * .
   */
  public void init() {
    this.fridge = new Fridge();
    this.cookbook = new Cookbook(fridge);

    fridge.addGrocery(new GroceryItem(new Grocery("Cheese", "kg", 101), LocalDate.now(), 1));
    fridge.addGrocery(new GroceryItem(new Grocery("Milk", "l", 45), LocalDate.now(), (float) 1.5));
    fridge.addGrocery(new GroceryItem(new Grocery("Bread", "stk", 45), LocalDate.now(), 1));

    Recipe taco = new Recipe("Taco",
        "Taco is a normal dinner in Norway. Most people eat it on fridays.",
        "To make the norwegian taco you choose your ingredients then put them on a tortilla.",
        3);
    Map<Grocery, Float> groceries = new HashMap<>();
    groceries.put(new Grocery("Tortilla", "stk", 101), (float) 4);
    groceries.put(new Grocery("Cheese", "kg", 101), (float) 0.2);
    groceries.put(new Grocery("Meat", "kg", (float) 64.5), (float) 0.4);
    groceries.put(new Grocery("Onion", "stk", 25), (float) 1);
    groceries.put(new Grocery("Cucumber", "stk", 25), (float) 1);
    taco.addGroceries(groceries);
    cookbook.addRecipe(taco);
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
    int menuSelectInput = -1;

    do {
      FlushConsole.clearConsole();
      System.out.println("----------------------------------------------");
      System.out.println("                    Menu");
      System.out.println("----------------------------------------------");
      System.out.println("\n    1. Edit and see your fridge");
      System.out.println("    2. Edit and see your cookbook");
      System.out.println("\n    0. Exit program");
      System.out.println("\n----------------------------------------------");
      System.out.println("\nType in the number you want:");

      menuSelectInput = UserInput.intInput();

      switch (menuSelectInput) {
        case 1 -> {
          FlushConsole.clearConsole();
          fridgeMenu();
          return;
        }
        case 2 -> {
          FlushConsole.clearConsole();
          cookbookMenu();
          return;
        }
        case 0 -> System.out.println("Exiting...");
      }

      System.out.println();
    } while (menuSelectInput != 0);
  }

  /**
   * .
   */
  public void fridgeMenu() {
    int menuSelectInput = -1;

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

      menuSelectInput = UserInput.intInput();

      switch (menuSelectInput) {
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
      }

      System.out.println();
    } while (menuSelectInput != 0);
  }

  private void addGrocery() {
    FlushConsole.clearConsole();
    System.out.println("Write the grocery name");
    final String groceryName = UserInput.nameInput();

    FlushConsole.clearConsole();
    System.out.println("Write the total price you payed for the grocery");
    final float price = UserInput.floatInput();

    FlushConsole.clearConsole();
    System.out.println("Write the quantity and the unit of the grocery");
    System.out.println("Example: 2 kg, 1.5 L");
    final Object[] quantityAndUnitObj = UserInput.unitAndQuantityInput();

    FlushConsole.clearConsole();
    System.out.println("Write the expiration date of the grocery in the format dd.MM.yyyy");
    final LocalDate expirationDate = UserInput.dateInput();

    float pricePerUnit = price / (float) quantityAndUnitObj[0];

    Grocery newGrocery = new Grocery(groceryName, (String) quantityAndUnitObj[1], pricePerUnit);
    GroceryItem newGroceryItem =
        new GroceryItem(newGrocery, expirationDate, (float) quantityAndUnitObj[0]);

    FlushConsole.clearConsole();
    System.out.print(fridge.addGrocery(newGroceryItem));

    System.out.printf("%nPress enter to continue....");
    UserInput.enterKeyPress();

    FlushConsole.clearConsole();
    fridgeMenu();
  }

  /**
   * .
   */
  private void removeGrocery() {
    FlushConsole.clearConsole();
    System.out.println("Write the name of the grocery you want to remove.");
    final String groceryName = UserInput.nameInput();

    FlushConsole.clearConsole();
    System.out.print(fridge.removeGrocery(groceryName));

    System.out.printf("%nPress enter to continue....");
    UserInput.enterKeyPress();

    FlushConsole.clearConsole();
    fridgeMenu();
  }

  /**
   * .
   */
  private void increaseQuantity() {
    FlushConsole.clearConsole();
    System.out.println("Write the grocery you want to add more of.");
    final String groceryName = UserInput.nameInput();

    FlushConsole.clearConsole();
    System.out.println("Write how much you are adding the grocery in the format without unit");
    final float inputQuantity = UserInput.floatInput();

    FlushConsole.clearConsole();
    System.out.print(fridge.increaseQuantity(groceryName, inputQuantity));

    System.out.printf("%nPress enter to continue....");
    UserInput.enterKeyPress();

    FlushConsole.clearConsole();
    fridgeMenu();
  }

  /**
   * .
   */
  private void decreaseQuantity() {
    FlushConsole.clearConsole();
    System.out.println("Write the name of the grocery you want to decrease the quantity of.");
    final String groceryName = UserInput.nameInput();

    FlushConsole.clearConsole();
    System.out.println("Write how much you want to remove without unit");
    final float inputQuantity = UserInput.floatInput();

    FlushConsole.clearConsole();
    System.out.print(fridge.decreaseQuantity(groceryName, inputQuantity));

    System.out.printf("%nPress enter to continue....");
    UserInput.enterKeyPress();

    FlushConsole.clearConsole();
    fridgeMenu();
  }

  /**
   * .
   */
  private void printGroceries() {
    int menuSelectInput = -1;

    do {
      FlushConsole.clearConsole();
      System.out.print(fridge.printFridgeContent());
      System.out.printf("%n<- Go back to menu by entering 0:%n");

      menuSelectInput = UserInput.intInput();
    } while (menuSelectInput != 0);

    FlushConsole.clearConsole();
    fridgeMenu();
  }

  /**
   * .
   */
  private void printGrocery() {
    int menuSelectInput = -1;

    do {
      FlushConsole.clearConsole();
      System.out.println("Write the name of the grocery you are looking for.");
      final String groceryName = UserInput.stringInput();

      FlushConsole.clearConsole();
      System.out.print(fridge.printGrocery(groceryName));
      System.out.printf("%n<- Go back to menu by entering 0:%n");

      menuSelectInput = UserInput.intInput();

    } while (menuSelectInput != 0);


    FlushConsole.clearConsole();
    fridgeMenu();
  }

  /**
   * .
   */
  private void expiredGroceries() {
    LocalDate today = LocalDate.now();
    int optionSelect = -1;

    do {
      FlushConsole.clearConsole();
      System.out.print(fridge.bestBeforeDate(today));
      System.out.printf("%n<- Go back by entering 0:%n");

      optionSelect = UserInput.intInput();

    } while (optionSelect != 0);

    FlushConsole.clearConsole();
    fridgeMenu();
  }

  /**
   * .
   */
  private void cookbookMenu() {
    int menuSelectInput = -1;

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

      menuSelectInput = UserInput.intInput();

      switch (menuSelectInput) {
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
          printRecipeSuggestions();
          return;
        }
        case 8 -> {
          menu();
          return;
        }
        case 0 -> System.out.println("Exiting...");
      }

      System.out.println();
    } while (menuSelectInput != 0);
  }

  /**
   * .
   */
  private void addRecipe() {
    Map<Grocery, Float> groceries = new HashMap<>();

    FlushConsole.clearConsole();
    System.out.println("Write the recipe name");
    final String recipeName = UserInput.nameInput();

    FlushConsole.clearConsole();
    System.out.println("Write a short description for the recipe.");
    final String recipeDescription = UserInput.stringInput();

    FlushConsole.clearConsole();
    System.out.println("Write the procedure for the recipe.");
    final String recipeProcedure = UserInput.stringInput();

    FlushConsole.clearConsole();
    System.out.println("Write how many servings this recipe will produce.");
    final int recipeServings = UserInput.intInput();

    FlushConsole.clearConsole();
    System.out.println("Next step is to input all groceries in needed in the recipe.");
    System.out.println(
        "When you are done inputting all the groceries write 0 instead of grocery name.");

    System.out.printf("%nPress enter to continue....");
    UserInput.enterKeyPress();

    String groceryName = "1";
    while (Integer.parseInt(groceryName) != 0) {
      FlushConsole.clearConsole();
      System.out.println("Write the grocery name.");
      groceryName = UserInput.nameInput();

      if (groceryName.equalsIgnoreCase("0")) {
        break;
      }

      FlushConsole.clearConsole();
      System.out.println("Write the quantity of the grocery.");
      System.out.println("Example: 2.2kg, 2l, 200g");
      final Object[] quanAndUnitInput = UserInput.unitAndQuantityInput();

      FlushConsole.clearConsole();
      System.out.println("Write the price per unit of the grocery");
      final float pricePerUnit = UserInput.floatInput();

      Grocery newGrocery = new Grocery(groceryName, (String) quanAndUnitInput[1], pricePerUnit);

      groceries.put(newGrocery, (Float) quanAndUnitInput[0]);

      if (!groceryName.equalsIgnoreCase("0")) {
        groceryName = "1";
      }
    }

    Recipe newRecipe =
        new Recipe(recipeName, recipeDescription, recipeProcedure, recipeServings);

    FlushConsole.clearConsole();
    newRecipe.addGroceries(groceries);
    System.out.println(cookbook.addRecipe(newRecipe));

    System.out.printf("%nPress enter to continue....");
    UserInput.enterKeyPress();

    FlushConsole.clearConsole();
    cookbookMenu();
  }

  /**
   * .
   */
  private void removeRecipe() {
    FlushConsole.clearConsole();
    System.out.println("Write the name of the recipe you want to remove.");
    final String recipeName = UserInput.nameInput();

    FlushConsole.clearConsole();
    System.out.println(cookbook.removeRecipe(recipeName));
    System.out.printf("%nPress enter to continue....");
    UserInput.enterKeyPress();

    cookbookMenu();
  }

  /**
   * .
   */
  private void printCookbook() {
    int menuSelectInput = -1;

    do {
      FlushConsole.clearConsole();
      System.out.println(cookbook.printCookbookContent());
      System.out.printf("%n<- Go back to menu by entering 0:%n");

      menuSelectInput = UserInput.intInput();
    } while (menuSelectInput != 0);

    FlushConsole.clearConsole();
    cookbookMenu();
  }

  /**
   * .
   */
  private void seeRecipe() {
    int menuSelectInput = -1;

    do {
      FlushConsole.clearConsole();
      System.out.println("Write the name of the recipe.");
      final String recipeName = UserInput.nameInput();

      FlushConsole.clearConsole();
      System.out.println(cookbook.printRecipe(recipeName));
      System.out.printf("%n<- Go back to menu by entering 0:%n");

      menuSelectInput = UserInput.intInput();
    } while (menuSelectInput != 0);

    FlushConsole.clearConsole();
    cookbookMenu();
  }

  private void printRecipeSuggestions() {
    cookbook.printRecipeSuggestions();
    System.out.printf("%nPress enter to continue....");
    UserInput.enterKeyPress();

    FlushConsole.clearConsole();
    cookbookMenu();
  }
}