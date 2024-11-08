package edu.ntnu.idi.idatt;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.sound.midi.Receiver;

/**
 * .
 */
public class TextUserInterface {
  private Fridge fridge;
  private Scanner scanner;
  public Recipe recipe;

  /**
   * .
   */
  public void init() {
    this.fridge = new Fridge();
    scanner = new Scanner(System.in);
  }

  /**
   * .
   */
  public void start() {
    //addGrocery();
    testRecipe();
  }

  public void testRecipe() {
    ArrayList<Grocery> test = new ArrayList<>();
    ArrayList<Grocery> test1 = new ArrayList<>();
    test.add(new Grocery("cheese", "kg", 100, "23.11.2024", 1.4f));
    test.add(new Grocery("milk", "l", 50, "23.11.2024", 1.4f));
    test1.add(new Grocery("salsa", "l", 100, "23.11.2024", 0.2f));
    test1.add(new Grocery("test","kg",100,"23.11.2024", 4f));

    recipe = new Recipe("Taco", "en meksikans rett", "putt alt sammen", test, 4);

    recipe.addGroceries(test1);
    //recipe.removeGroceries(test2);

    System.out.println(recipe.toString());
  }

  /**
   * .
   */
  private void addGrocery() {
    System.out.println("\nEnter grocery name: ");
    String name1 = scanner.nextLine();
    String name2 = scanner.nextLine();

    try {
      Grocery grocery = new Grocery(name1, "l", 1.2f, "23.11.2024", 100f);
      fridge.addGrocery(grocery);
      Grocery grocery1 = new Grocery(name2, "l", 100f, "23.11.2024", 1f);
      fridge.addGrocery(grocery1);
    } catch (IllegalArgumentException | DateTimeParseException e) {
      if (e instanceof DateTimeParseException) {
        System.err.print("Invalid date format, please use dd.MM.yyyy");
        return;
      }
      System.err.print(e.getMessage());
    }
  }

  /**
   * .
   */
  private void printGroceries() {
    fridge.printFridgeContent();
  }
}
