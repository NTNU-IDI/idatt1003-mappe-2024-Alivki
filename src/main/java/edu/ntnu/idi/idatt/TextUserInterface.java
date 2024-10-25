package edu.ntnu.idi.idatt;

/**
 * .
 */
public class TextUserInterface {

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
    Grocery cheese = new Grocery("cheese", "kg", 110f, "28.10.2024", 1.2f);
    Grocery milk = new Grocery("milk", "l", 30f, "28.10.2024", 1f);
    Grocery bread = new Grocery("bread", "stk", 28f, "28.10.2024", 2f);
    Grocery ham = new Grocery("ham", "kg", 160f, "28.10.2024", 0.4f);
    System.out.printf("%s\n%s\n%s\n%s", cheese, milk, bread, ham);
  }
}
