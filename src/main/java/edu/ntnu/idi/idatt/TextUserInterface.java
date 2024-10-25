package edu.ntnu.idi.idatt;

import java.util.Scanner;

/**
 * .
 */
public class TextUserInterface {
  private final Fridge fridge;
  private final Scanner scanner;

  /**
   * .
   */
  public TextUserInterface(Fridge fridge) {
    this.fridge = fridge;
    this.scanner = new Scanner(System.in);
  }

  /**
   * .
   */
  public void init() {
    // TODO
  }

  /**
   * .
   */
  public void start() {
    //Grocery cheese = new Grocery("cheese", "kg", 110f, "28.10.2024", 1.2f);
    //Grocery milk = new Grocery("milk", "l", 30f, "28.10.2024", 1f);
    //Grocery bread = new Grocery("bread", "stk", 28f, "28.10.2024", 2f);
    //Grocery ham = new Grocery("ham", "kg", 160f, "28.10.2024", 0.4f);
    //System.out.printf("%s\n%s\n%s\n%s", cheese, milk, bread, ham);

    addGrocery();
  }

  /**
   * .
   */
  private void addGrocery() {
    System.out.println("Enter grocery name: ");
    String name = scanner.nextLine();

    Grocery input = new Grocery(name, "kg", 110f, "28.10.2024", 1.2f);
    fridge.addGrocery(input);
  }
}
