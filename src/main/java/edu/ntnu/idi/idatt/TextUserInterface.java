package edu.ntnu.idi.idatt;

import java.util.Scanner;

/**
 * .
 */
public class TextUserInterface {
  private Fridge fridge;
  private Scanner scanner;

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
  }

  /**
   * .
   */
  private void addGrocery() {
    System.out.println("\nEnter grocery name: ");
    String name1 = scanner.nextLine();
    String name2 = scanner.nextLine();

    fridge.addGrocery(name1, "l", 110f, "28.10.2024", 1.2f);
    fridge.addGrocery(name2, "stk", 110f, "28.10.2024", 1.2f);

    printGroceries();

    fridge.decreaseQuantity("cheese", 2.4f);

    fridge.findGrocery("cheese");
  }

  /**
   * .
   */
  private void printGroceries() {
    fridge.printFridgeContent();
  }
}
