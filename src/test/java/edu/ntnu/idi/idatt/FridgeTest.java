package edu.ntnu.idi.idatt;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FridgeTest {

  private Fridge fridge;

  @BeforeEach
  void setUp() {
    fridge = new Fridge();
  }

  @Test
  void testAddGroceryNewItem() {
    Grocery grocery =
        new Grocery("cheese", "kg", 100f, "01.01.2025", 2f);
    fridge.addGrocery(grocery);
    List<Grocery> groceries = fridge.getGroceries();

    assertEquals(1, groceries.size());
    assertNotEquals(2, groceries.size());
    assertEquals("cheese", groceries.getFirst().getName());
    assertEquals("KG", groceries.getFirst().getUnit());
    assertEquals(100f, groceries.getFirst().getPrice());
    assertEquals(LocalDate.of(2025, 1, 1), groceries.getFirst().getExpirationDate());
    assertEquals(2f, groceries.getFirst().getQuantity());

    assertNotEquals("milk", groceries.getFirst().getName());
    assertNotEquals("L", groceries.getFirst().getUnit());
    assertNotEquals(50f, groceries.getFirst().getPrice());
    assertNotEquals(LocalDate.of(2024, 1, 1), groceries.getFirst().getExpirationDate());
    assertNotEquals(1f, groceries.getFirst().getQuantity());
  }

  @Test
  void testAddTwoGroceryNewItems() {
    Grocery cheese =
        new Grocery("cheese", "kg", 100f, "01.01.2025", 2f);
    Grocery milk =
        new Grocery("milk", "l", 50f, "01.01.2025", 2);
    fridge.addGrocery(cheese);
    fridge.addGrocery(milk);
    List<Grocery> groceries = fridge.getGroceries();

    assertEquals(2, groceries.size());
    assertNotEquals(1, groceries.size());
    assertEquals("cheese", groceries.getFirst().getName());
    assertEquals("milk", groceries.get(1).getName());
  }

  @Test
  void testAddGroceryExistingItem() {
    Grocery cheese =
        new Grocery("cheese", "kg", 100f, "01.01.2025", 2f);
    fridge.addGrocery(cheese);
    fridge.addGrocery(cheese);
    List<Grocery> groceries = fridge.getGroceries();

    assertEquals(1, groceries.size());
    assertEquals(4, groceries.getFirst().getQuantity());
    assertNotEquals(2, groceries.size());
    assertNotEquals(1, groceries.getFirst().getQuantity());
  }

  @Test
  void testAddGroceryExistingItemStk() {
    Grocery cheese =
        new Grocery("cheese", "stk", 100f, "01.01.2025", 1.5f);
    fridge.addGrocery(cheese);
    fridge.addGrocery(cheese);
    List<Grocery> groceries = fridge.getGroceries();

    assertEquals(1, groceries.size());
    assertEquals(4, groceries.getFirst().getQuantity());
    assertNotEquals(2, groceries.size());
    assertNotEquals(1, groceries.getFirst().getQuantity());
  }

  @Test
  void testRemoveGrocery() {
    Grocery cheese =
        new Grocery("cheese", "kg", 100f, "01.01.2025", 2f);
    fridge.addGrocery(cheese);
    fridge.removeGrocery("Cheese");
    List<Grocery> groceries = fridge.getGroceries();

    assertTrue(groceries.isEmpty());
    assertNotEquals(1, groceries.size());
  }

  @Test
  void testRemoveNonExistingGrocery() {
    Grocery cheese =
        new Grocery("cheese", "kg", 100f, "01.01.2025", 2f);
    fridge.addGrocery(cheese);
    fridge.removeGrocery("milk");
    List<Grocery> groceries = fridge.getGroceries();

    assertEquals(1, groceries.size());
    assertEquals("cheese", groceries.getFirst().getName());
    assertNotEquals(2, groceries.size());
    assertNotEquals("milk", groceries.getFirst().getName());
  }

  @Test
  void testIncreaseQuantity() {
    Grocery cheese =
        new Grocery("cheese", "kg", 100f, "01.01.2025", 2f);
    fridge.addGrocery(cheese);
    fridge.increaseQuantity("cheese", 1f);
    List<Grocery> groceries = fridge.getGroceries();

    assertEquals(3, groceries.getFirst().getQuantity());
    assertNotEquals(4, groceries.getFirst().getQuantity());
  }

  @Test
  void testDecreaseQuantity() {
    Grocery cheese =
        new Grocery("cheese", "kg", 100f, "01.01.2025", 2f);
    fridge.addGrocery(cheese);
    fridge.decreaseQuantity("cheese", 1);
    List<Grocery> groceries = fridge.getGroceries();

    assertEquals(1, groceries.getFirst().getQuantity());
    assertNotEquals(4, groceries.getFirst().getQuantity());
  }

  @Test
  void testDecreaseQuantityToZero() {
    Grocery cheese =
        new Grocery("cheese", "kg", 100f, "01.01.2025", 2f);
    fridge.addGrocery(cheese);
    fridge.decreaseQuantity("cheese", 2.2f);
    List<Grocery> groceries = fridge.getGroceries();

    assertTrue(groceries.isEmpty());
    assertNotEquals(1, groceries.size());
  }

  @Test
  void testFindGrocery() {
    Grocery cheese =
        new Grocery("cheese", "kg", 100f, "01.01.2025", 2f);
    fridge.addGrocery(cheese);
    List<Grocery> groceries = fridge.getGroceries();

    assertDoesNotThrow(() -> {
      fridge.findGrocery("cheese");
    });
    assertNotEquals("cheese", groceries.getFirst().toString());
  }

  @Test
  void testFindGroceryWhenEmptyGroceries() {
    List<Grocery> groceries = fridge.getGroceries();

    assertDoesNotThrow(() -> {
      fridge.findGrocery("cheese");
    });
    assertTrue(groceries.isEmpty());
  }

  @Test
  void testBestBeforeDate() {
    Grocery cheese =
        new Grocery("cheese", "kg", 100f, "01.01.2025", 2f);
    fridge.addGrocery(cheese);
    Grocery milk =
        new Grocery("milk", "l", 100f, "01.01.2025", 2f);
    fridge.addGrocery(milk);

    assertDoesNotThrow(() -> {
      fridge.bestBeforeDate("01.01.2026");
    });
  }

  @Test
  void testPrintFridgeContentEmpty() {
    assertDoesNotThrow(() -> {
      fridge.printFridgeContent();
    });
  }

  @Test
  void testPrintFridgeContent() {
    Grocery cheese =
        new Grocery("cheese", "kg", 100f, "01.01.2025", 2f);
    fridge.addGrocery(cheese);
    assertDoesNotThrow(() -> {
      fridge.printFridgeContent();
    });
  }
}
