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

    Grocery ost = new Grocery("ost", "kg", 100, "10.11.2024", 1.2f);

    ost.setQuantity(2f);
    System.out.println(ost.toString());
  }
}