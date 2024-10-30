package edu.ntnu.idi.idatt;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
    fridge.addGrocery("cheese", "kg", 100f, "01.01.2025", 2f);
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
    fridge.addGrocery("cheese", "kg", 100f, "01.01.2025", 2f);
    fridge.addGrocery("milk", "l", 50f, "01.01.2025", 2);
    List<Grocery> groceries = fridge.getGroceries();

    assertEquals(2, groceries.size());
    assertNotEquals(1, groceries.size());
    assertEquals("cheese", groceries.getFirst().getName());
    assertEquals("milk", groceries.get(1).getName());
  }

  @Test
  void testAddGroceryExistingItem() {
    fridge.addGrocery("cheese", "kg", 100f, "01.01.2025", 1f);
    fridge.addGrocery("cheese", "kg", 100f, "01.01.2025", 1f);
    List<Grocery> groceries = fridge.getGroceries();

    assertEquals(1, groceries.size());
    assertEquals(2, groceries.getFirst().getQuantity());
    assertNotEquals(2, groceries.size());
    assertNotEquals(1, groceries.getFirst().getQuantity());
  }

  @Test
  void testAddGroceryExistingItemStk() {
    fridge.addGrocery("cheese", "stk", 100f, "01.01.2025", 1.2f);
    fridge.addGrocery("cheese", "stk", 100f, "01.01.2025", 1.5f);
    List<Grocery> groceries = fridge.getGroceries();

    assertEquals(1, groceries.size());
    assertEquals(3, groceries.getFirst().getQuantity());
    assertNotEquals(2, groceries.size());
    assertNotEquals(1, groceries.getFirst().getQuantity());
  }

  @Test
  void testRemoveGrocery() {
    fridge.addGrocery("Cheese", "kg", 100f, "01.01.2025", 1f);
    fridge.removeGrocery("Cheese");
    List<Grocery> groceries = fridge.getGroceries();

    assertTrue(groceries.isEmpty());
    assertNotEquals(1, groceries.size());
  }

  @Test
  void testRemoveNonExistingGrocery() {
    fridge.addGrocery("cheese", "kg", 100f, "01.01.2025", 1f);
    fridge.removeGrocery("milk");
    List<Grocery> groceries = fridge.getGroceries();

    assertEquals(1, groceries.size());
    assertEquals("cheese", groceries.getFirst().getName());
    assertNotEquals(2, groceries.size());
    assertNotEquals("milk", groceries.getFirst().getName());
  }

  @Test
  void testIncreaseQuantity() {
    fridge.addGrocery("cheese", "kg", 100f, "01.01.2025", 2f);
    fridge.increaseQuantity("cheese", 1f);
    List<Grocery> groceries = fridge.getGroceries();

    assertEquals(3, groceries.getFirst().getQuantity());
    assertNotEquals(4, groceries.getFirst().getQuantity());
  }

  @Test
  void testDecreaseQuantity() {
    fridge.addGrocery("cheese", "kg", 100f, "01.01.2025", 4);
    fridge.decreaseQuantity("cheese", 2);
    List<Grocery> groceries = fridge.getGroceries();

    assertEquals(2, groceries.getFirst().getQuantity());
    assertNotEquals(4, groceries.getFirst().getQuantity());
  }

  @Test
  void testDecreaseQuantityToZero() {
    fridge.addGrocery("cheese", "kg", 100f, "01.01.2025", 2.2f);
    fridge.decreaseQuantity("cheese", 2.2f);
    List<Grocery> groceries = fridge.getGroceries();

    assertTrue(groceries.isEmpty());
    assertNotEquals(1, groceries.size());
  }

  @Test
  void testFindGrocery() {
    fridge.addGrocery("cheese", "kg", 100f, "01.01.2025", 3);
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
    fridge.addGrocery("cheese", "kg", 100f, "01.01.2025", 1f);
    fridge.addGrocery("milk", "l", 50f, "01.01.2025", 1f);
    LocalDate inputDate = LocalDate.of(2026, 1, 1);

    assertDoesNotThrow(() -> {
      fridge.bestBeforeDate(inputDate);
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
    fridge.addGrocery("Bread", "stk", 1.00f, "01.01.2025", 1);
    assertDoesNotThrow(() -> {
      fridge.printFridgeContent();
    });
  }
}
