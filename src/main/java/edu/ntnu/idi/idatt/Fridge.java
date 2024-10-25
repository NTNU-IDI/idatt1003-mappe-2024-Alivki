package edu.ntnu.idi.idatt;

import java.util.ArrayList;
import java.util.List;

/**
 * Test.
 */
public class Fridge {
  private final List<Grocery> groceries;

  /**
   *.
   */
  public Fridge() {
    groceries = new ArrayList<>();
  }

  /**
   *.
   *
   * @param grocery grocery object
   */
  public void addGrocery(Grocery grocery) {
    groceries.add(grocery);
    System.out.printf("Grocery has been added: %s", grocery.toString());
  }
}
