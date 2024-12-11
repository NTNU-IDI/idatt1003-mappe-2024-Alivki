package edu.ntnu.idi.idatt.views;

import edu.ntnu.idi.idatt.models.Cookbook;
import edu.ntnu.idi.idatt.models.Fridge;
import edu.ntnu.idi.idatt.models.Grocery;
import edu.ntnu.idi.idatt.models.GroceryItem;
import edu.ntnu.idi.idatt.models.Recipe;
import edu.ntnu.idi.idatt.utils.FlushConsole;
import edu.ntnu.idi.idatt.utils.PrintMenus;
import edu.ntnu.idi.idatt.utils.UserInput;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@code TextUserInterface} class is the text based user interface for the program.
 * It initializes and starts the program by setting up the {@link Fridge} and {@link Cookbook}
 */
public class TextUserInterface {
  private Fridge fridge;
  private Cookbook cookbook;

  /**
   * Initializes the program by setting up the fridge and cookbook with initial data.
   * <p>
   * This method:
   * <ul>
   *   <li>Creates a {@link Fridge} instance and adds predefined groceries to it.</li>
   *   <li>Creates a {@link Cookbook} instance linked to the fridge and populates it with
   *       a sample recipe, "Taco," including its required ingredients.</li>
   * </ul>
   * </p>
   */
  public void init() {
    this.fridge = new Fridge();
    this.cookbook = new Cookbook(fridge);

    fridge.addGrocery(new GroceryItem(new Grocery("Cheese", "kg", 101), LocalDate.now(), 1));
    fridge.addGrocery(new GroceryItem(new Grocery("Milk", "l", 45), LocalDate.now(), (float) 1.5));
    fridge.addGrocery(new GroceryItem(new Grocery("Bread", "stk", 45), LocalDate.now(), 1));

    final Recipe taco = new Recipe("Taco",
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
   * Starts the application. This is the main loop of the application,
   * presenting the menu, retrieving the selected menu choice from the user,
   * and executing the selected functionality.
   */
  public void start() {
    int menuSelectInput = -1;

    do {
      FlushConsole.clearConsole();
      System.out.print(PrintMenus.printMainMenu());

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
   * Method to handle all input and output for fridge menu.
   */
  public void fridgeMenu() {
    int menuSelectInput = -1;

    do {
      System.out.println(PrintMenus.printFridgeMenu());

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
          start();
          return;
        }
        case 0 -> System.out.println("Exiting...");
      }

      System.out.println();
    } while (menuSelectInput != 0);
  }

  /**
   * Asks the users for the necessary parameters to create a new grocery item.
   * Then waits for enter input to shows the fridge menu again.
   */
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
   * Ask users for necessary input for remove a grocery item.
   * Then waits for enter input to shows the fridge menu again.
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
   * Asks user for necessary input to increase the quantity of a grocery item.
   * Then waits for enter input to shows the fridge menu again.
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
   * Asks user for necessary input to decrease the quantity of a grocery item.
   * Then waits for enter input to shows the fridge menu again.
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
   * Prints all the groceries in the fridge.
   * Then waits for user input to go back to fridge menu.
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
   * Asks user for name input. Then prints the grocery if found.
   * Else it prints error message.
   * Wait for user input to go back to fridge menu.
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
   * Prints all expired groceries then wait for user input to go back to fridge menu.
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
   * Method to handle all input and output for cookbook menu.
   */
  private void cookbookMenu() {
    int menuSelectInput = -1;

    do {
      System.out.println(PrintMenus.printCookbookMenu());

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
          start();
          return;
        }
        case 0 -> System.out.println("Exiting...");
      }

      System.out.println();
    } while (menuSelectInput != 0);
  }

  /**
   * Asks user for necessary input to create new recipe object.
   * Loops for the user to be able to input multiple groceries.
   * Then shows the cookbook menu again.
   */
  private void addRecipe() {
    final Map<Grocery, Float> groceries = new HashMap<>();

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
   * Asks user for necessary input to remove a grocery.
   * Then waits for enter input to show cookbook menu again.
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
   * Prints all recipes in the cookbook.
   * Then waits for user input to show the cookbook menu again.
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
   * Asks user for name input, then shows the specific menu.
   * Prints error message if recipe not found.
   * Then wait for user input to show cookbook menu again.
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

  /**
   * Prints the suggested recipes.
   * Then waits for user input to show cookbook menu again.
   */
  private void printRecipeSuggestions() {
    int menuSelectInput = -1;

    do {
      FlushConsole.clearConsole();
      System.out.print(cookbook.recipeSuggestions());
      System.out.printf("%n<- Go back to menu by entering 0:%n");

      menuSelectInput = UserInput.intInput();
    } while (menuSelectInput != 0);

    FlushConsole.clearConsole();
    cookbookMenu();
  }
}