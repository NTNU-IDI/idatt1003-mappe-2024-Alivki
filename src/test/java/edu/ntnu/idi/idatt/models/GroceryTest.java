package edu.ntnu.idi.idatt.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Grocery class.
 */
public class GroceryTest {
  @Test
  @DisplayName("Test: Grocery name getter")
  public void testNameGetter() {
    Grocery banana = new Grocery("Banana", "stk", 1f);
    assertEquals("Banana", banana.getName(), "The name getter did not return the expected value.");
    assertNotEquals("Cheese", banana.getName(), "The unit getter returned the wrong name");
  }

  @Test
  @DisplayName("Test: Grocery unit getter")
  public void testUnitGetter() {
    Grocery milk = new Grocery("Milk", "kg", 20f);
    assertEquals("kg", milk.getUnit(), "The unit getter did not return the expected value.");
    assertNotEquals("l", milk.getUnit(), "The unit getter returned the wrong value");
  }

  @Test
  @DisplayName("Test: Grocery price getter")
  public void testPriceGetter() {
    Grocery bread = new Grocery("Bread", "stk", 35f);
    assertEquals(35, bread.getPrice(), "The price getter did not return the expected value.");
    assertNotEquals(10f, bread.getPrice(), "The unit getter returned the wrong value");
  }

  @Test
  @DisplayName("Test: zero price input")
  public void testZeroPrice() {
    Grocery tomato = new Grocery("Tomato", "kg", 0f);
    assertEquals(0f, tomato.getPrice(), "The price getter did not handle zero correctly.");
    assertNotEquals(10f, tomato.getPrice(), "The price getter returned the wrong value");
  }

  @Test
  @DisplayName("Test: Negative price input")
  public void testNegativePrice() {
    Grocery cheese = new Grocery("Cheese", "unit", -20f);
    assertEquals(-20f, cheese.getPrice(), "The price getter did not handle negative values correctly.");
    assertNotEquals(10f, cheese.getPrice(), "The price getter returned the wrong value");
  }

  @Test
  @DisplayName("Test: Grocery constructor handles empty name")
  public void testEmptyName() {
    Grocery cheese = new Grocery("", "kg", 20f);
    assertEquals("", cheese.getName(), "The name getter did not empty values correct.");
    assertNotEquals("Cheese", cheese.getName(), "The name getter returned the wrong value");
  }

  @Test
  @DisplayName("Test: Grocery constructor handles empty unit")
  public void testEmptyUnit() {
    Grocery cheese = new Grocery("Cheese", "", 20f);
    assertEquals("", cheese.getUnit(), "The unit getter did not empty values correct.");
    assertNotEquals("kg", cheese.getUnit(), "The unit getter returned the wrong value");
  }

  @Test
  @DisplayName("Test: Grocery constructor handles null value name")
  public void testNullValueName() {
    Grocery cheese = new Grocery(null, "kg", 20f);
    assertEquals(null, cheese.getName(), "The name getter did not empty null value correct.");
    assertNotEquals("kg", cheese.getName(), "The name getter returned the wrong value");
  }

  @Test
  @DisplayName("Test: Grocery constructor handles null value name")
  public void testNullValueUnit() {
    Grocery cheese = new Grocery("Cheese", null, 20f);
    assertEquals(null, cheese.getUnit(), "The unit getter did not empty values correct.");
    assertNotEquals("kg", cheese.getUnit (), "The unit getter returned the wrong value");
  }
}