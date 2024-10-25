package edu.ntnu.idi.idatt;

/**
 * .
 */
public class Main {

  /**
   * .
   */
  public static void main(String[] args) {
    // TODO: implement main method logic
    TUI ui = new TUI();
    ui.init();
    ui.start();

    Grocery ost = new Grocery("cheese", "kg", 100f, "10.11.2024", 1.2f);

    ost.setQuantity(2f);
    System.out.println(ost.toString());
  }
}