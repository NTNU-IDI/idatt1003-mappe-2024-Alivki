package edu.ntnu.idi.idatt;

import java.time.LocalDate;
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
    addGrocery();
    //testRecipe();
  }

  public void testRecipe() {
    ArrayList<Grocery> test = new ArrayList<>();
    ArrayList<Grocery> test1 = new ArrayList<>();
    ArrayList<Grocery> test2 = new ArrayList<>();
    test.add(new Grocery("cheese", "kg", 100, "23.11.2024", 1.4f));
    test1.add(new Grocery("milk", "l", 50, "23.11.2024", 1.4f));
    test2.add(new Grocery("cheese", "kg", 100, "23.11.2024", 1.4f));

    recipe = new Recipe("Taco", "putt alt sammen", "test", test, 4);

    recipe.addGroceries(test1);
    recipe.removeGroceries(test2);

    System.out.println(recipe.toString());
  }

  /**
   * .
   */
  private void addGrocery() {
    System.out.println("\nEnter grocery name: ");
    String name1 = scanner.nextLine();
    String name2 = scanner.nextLine();

    fridge.addGrocery(name1, "l", 110f, "28.11.2025", 1.2f);
    fridge.addGrocery(name2, "stk", 110f, "28.11.2024", 1.2f);
    printGroceries();

    //fridge.decreaseQuantity("cheese", 2.4f);

    fridge.findGrocery("cheese");

    //ocalDate test = LocalDate.of(2026, 12, 31);
    //fridge.bestBeforeDate(test);
  }

  /**
   * .
   */
  private void printGroceries() {
    fridge.printFridgeContent();
  }
}
