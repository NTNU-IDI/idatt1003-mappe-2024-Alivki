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
    Grocery cheese = new Grocery("cheese", "kg", 110f, "28.10.2024", 1.2f);
    Grocery milk = new Grocery("milk", "l", 30f, "28.10.2025", 1f);
    Grocery bread = new Grocery("bread", "stk", 28f, "28.10.2026", 2f);
    Grocery ham = new Grocery("ham", "kg", 160f, "28.10.2027", 0.4f);
    System.out.printf("%s%n%s%n%s%n%s", cheese, milk, bread, ham);

    addGrocery();
  }

  /**
   * .
   */
  private void addGrocery() {
    System.out.println("\nEnter grocery name: ");
    String name1 = scanner.nextLine();
    String name2 = scanner.nextLine();

    Grocery input1 = new Grocery(name1, "l", 110f, "28.10.2024", 1.2f);
    Grocery input2 = new Grocery(name2, "stk", 110f, "28.10.2024", 1.2f);
    fridge.addGrocery(input1);
    fridge.addGrocery(input2);
    printGroceries();
  }

  /**
   * .
   */
  private void printGroceries() {
    fridge.printFridgeContent();
  }
}
