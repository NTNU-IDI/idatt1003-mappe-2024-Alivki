package edu.ntnu.idi.idatt.models;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GroceryItemTest {

  @Test
  @DisplayName("Test: GroceryItem grocery getter")
  void testGroceryGetter() {
    Grocery cheese = new Grocery("Cheese", "kg", 100f);
    Grocery milk = new Grocery("Milk", "l", 50f);
    GroceryItem cheeseItem =
        new GroceryItem(cheese, LocalDate.of(2025, 12, 31), 1f);

    assertEquals(cheese, cheeseItem.getGrocery(),
        "The grocery getter did not return the expected value.");
    assertNotEquals(milk, cheeseItem.getGrocery(), "The grocery getter returned the wrong value.");
  }

  @Test
  @DisplayName("Test: GroceryItem grocery getter")
  void testGroceryGetternull() {
    assertThrows(NullPointerException.class, () -> {
      GroceryItem cheeseItem =
          new GroceryItem(null, LocalDate.of(2025, 12, 31), 1f);
    });
  }

  @Test
  @DisplayName("Test: GroceryItem quantity getter")
  void testQuantityGetter() {
    Grocery cheese = new Grocery("Cheese", "kg", 100f);
    GroceryItem cheeseItem =
        new GroceryItem(cheese, LocalDate.of(2025, 12, 31), 1f);
    assertEquals(1f, cheeseItem.getQuantity(),
        "The grocery getter did not return the expected value.");
    assertNotEquals(50f, cheeseItem.getQuantity(), "The grocery getter returned the wrong value.");
  }

  @Test
  @DisplayName("Test: GroceryItem expiration date getter")
  void testExpirationDateGetter() {
    Grocery cheese = new Grocery("Cheese", "kg", 100f);
    GroceryItem cheeseItem =
        new GroceryItem(cheese, LocalDate.of(2025, 12, 31), 1f);
    assertEquals(LocalDate.of(2025, 12, 31), cheeseItem.getExpirationDate(),
        "The grocery getter did not return the expected value.");
    assertNotEquals(LocalDate.of(2024, 12, 30), cheeseItem.getExpirationDate(),
        "The grocery getter returned the wrong value.");
  }

  @Test
  @DisplayName("Test: GroceryItem expiration date getter")
  void testExpirationDateGetterHandlesnull() {
    Grocery cheese = new Grocery("Cheese", "kg", 100f);
    GroceryItem cheeseItem =
        new GroceryItem(cheese, null, 1f);
    assertEquals(null, cheeseItem.getExpirationDate(),
        "The grocery getter did not return the expected value.");
    assertNotEquals(LocalDate.of(2024, 12, 30), cheeseItem.getExpirationDate(),
        "The grocery getter returned the wrong value.");
  }

  @Test
  void testIncreaseQuantity() {
    Grocery cheese = new Grocery("Cheese", "kg", 100f);
    GroceryItem cheeseItem =
        new GroceryItem(cheese, LocalDate.of(2025, 12, 31), 1f);

    cheeseItem.increaseQuantity(1f);

    assertEquals(2f, cheeseItem.getQuantity(),
        "The grocery getter did not return the expected value.");
    assertNotEquals(1f, cheeseItem.getQuantity(),
        "The grocery getter returned the wrong value.");
  }

  @Test
  void testIncreaseNegativeQuantity() {
    Grocery cheese = new Grocery("Cheese", "kg", 100f);
    GroceryItem cheeseItem =
        new GroceryItem(cheese, LocalDate.of(2025, 12, 31), 1f);

    cheeseItem.increaseQuantity(-1f);

    assertEquals(0f, cheeseItem.getQuantity(),
        "The grocery getter did not return the expected value.");
    assertNotEquals(1f, cheeseItem.getQuantity(),
        "The grocery getter returned the wrong value.");
  }

  @Test
  void testDecreaseQuantity() {
    Grocery cheese = new Grocery("Cheese", "kg", 100f);
    GroceryItem cheeseItem =
        new GroceryItem(cheese, LocalDate.of(2025, 12, 31), 1f);

    cheeseItem.decreaseQuantity(0.5f);

    assertEquals(0.5f, cheeseItem.getQuantity(),
        "The grocery getter did not return the expected value.");
    assertNotEquals(1f, cheeseItem.getQuantity(),
        "The grocery getter returned the wrong value.");
  }

  @Test
  void testDecreaseNegativeQuantity() {
    Grocery cheese = new Grocery("Cheese", "kg", 100f);
    GroceryItem cheeseItem =
        new GroceryItem(cheese, LocalDate.of(2025, 12, 31), 1f);

    cheeseItem.decreaseQuantity(-0.5f);

    assertEquals(1.5f, cheeseItem.getQuantity(),
        "The grocery getter did not return the expected value.");
    assertNotEquals(1f, cheeseItem.getQuantity(),
        "The grocery getter returned the wrong value.");
  }

  @Test
  void testDecreaseQuantityToBelowZero() {
    Grocery cheese = new Grocery("Cheese", "kg", 100f);
    GroceryItem cheeseItem =
        new GroceryItem(cheese, LocalDate.of(2025, 12, 31), 1f);

    cheeseItem.decreaseQuantity(1.5f);

    assertEquals(-0.5f, cheeseItem.getQuantity(),
        "The grocery getter did not return the expected value.");
    assertNotEquals(1f, cheeseItem.getQuantity(),
        "The grocery getter returned the wrong value.");
  }

  @Test
  void testToString() {
    Grocery cheese = new Grocery("Cheese", "kg", 100f);
    GroceryItem cheeseItem =
        new GroceryItem(cheese, LocalDate.of(2025, 12, 31), 1f);

    assertEquals(
        "Name: Cheese, Unit: kg, Price: 100.000000, Expiration date: 2025-12-31, Quantity: 1.000000",
        cheeseItem.toString(),
        "The to string value was not correct");
    assertNotEquals("Name: Milk, Unit: l, Price: 50, Expiration date: 2025-12-30, Quantity: 1",
        cheeseItem.toString(),
        "The grocery getter returned the wrong value.");
  }

  @Test
  void testEqualsSameObj() {
    Grocery cheese = new Grocery("Cheese", "kg", 100f);
    GroceryItem cheeseItem =
        new GroceryItem(cheese, LocalDate.of(2025, 12, 31), 1f);

    assertEquals(cheeseItem, cheeseItem, "An object should be equal to itself.");
  }

  @Test
  void testEquals() {
    Grocery cheese1 = new Grocery("Cheese", "kg", 100f);
    GroceryItem cheeseItem1 =
        new GroceryItem(cheese1, LocalDate.of(2025, 12, 31), 1f);
    Grocery cheese2 = new Grocery("Cheese", "kg", 100f);
    GroceryItem cheeseItem2 =
        new GroceryItem(cheese2, LocalDate.of(2025, 12, 31), 1f);

    assertTrue(cheeseItem1.equals(cheeseItem2), "An object should be equal to itself.");
  }

  @Test
  void testEqualsK() {
    Grocery cheese = new Grocery("Cheese", "kg", 100f);
    GroceryItem cheeseItem =
        new GroceryItem(cheese, LocalDate.of(2025, 12, 31), 1f);
    Grocery milk = new Grocery("Milk", "l", 50f);
    GroceryItem milkItem =
        new GroceryItem(milk, LocalDate.of(2025, 12, 31), 1f);

    assertFalse(cheeseItem.equals(milkItem), "An object should be equal to itself.");
  }

  @Test
  void testHashCode() {
    Grocery cheese1 = new Grocery("Cheese", "kg", 100f);
    GroceryItem cheeseItem1 =
        new GroceryItem(cheese1, LocalDate.of(2025, 12, 31), 1f);
    Grocery cheese2 = new Grocery("Cheese", "kg", 100f);
    GroceryItem cheeseItem2 =
        new GroceryItem(cheese2, LocalDate.of(2025, 12, 31), 1f);
    assertEquals(cheeseItem1.hashCode(), cheeseItem2.hashCode(),
        "Equal objects must have the same hashCode.");
  }

  @Test
  void testHashCodeNot() {
    Grocery cheese = new Grocery("Cheese", "kg", 100f);
    GroceryItem cheeseItem =
        new GroceryItem(cheese, LocalDate.of(2025, 12, 31), 1f);
    Grocery milk = new Grocery("Milk", "l", 50f);
    GroceryItem milkItem =
        new GroceryItem(milk, LocalDate.of(2025, 12, 31), 1f);
    assertNotEquals(cheeseItem.hashCode(), milkItem.hashCode(),
        "Different objects should have different hashCodes.");
  }
}