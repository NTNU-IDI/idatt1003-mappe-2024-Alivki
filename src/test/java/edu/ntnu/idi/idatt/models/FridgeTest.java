package edu.ntnu.idi.idatt.models;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FridgeTest {
  @Test
  @DisplayName("Test: fridge groceries getter method and add grocery method")
  void testGroceriesGetter() {
    Fridge fridge = new Fridge();
    Grocery cheese = new Grocery("Cheese", "kg", 100f);
    GroceryItem cheeseItem = new GroceryItem(cheese, LocalDate.of(2025, 12, 31), 1f);
    fridge.addGrocery(cheeseItem);

    List<GroceryItem> test = new ArrayList<>();
    test.add(cheeseItem);

    List<GroceryItem> test2 = new ArrayList<>();
    test2.add(cheeseItem);
    test2.add(cheeseItem);

    assertEquals(test, fridge.getGroceries(),"Get method not return correct values");
    assertNotEquals(test2, fridge.getGroceries(),"Get method not return correct values");
  }

  @Test
  @DisplayName("Test: test add grocery method")
  void testAddGroceryNull() {
    Fridge fridge = new Fridge();

    assertThrows(NullPointerException.class, () -> {
      fridge.addGrocery(null);
    });
  }

  @Test
  @DisplayName("Test: Remove grocery method")
  void testRemoveGrocery() {
    Fridge fridge = new Fridge();
    Grocery cheese = new Grocery("Cheese", "kg", 100f);
    GroceryItem cheeseItem = new GroceryItem(cheese, LocalDate.of(2025, 12, 31), 1f);
    fridge.addGrocery(cheeseItem);

    fridge.removeGrocery("Cheese");

    List<GroceryItem> test = new ArrayList<>();

    List<GroceryItem> test2 = new ArrayList<>();
    test2.add(cheeseItem);

    assertEquals(test, fridge.getGroceries(),"Get method not return correct values");
    assertNotEquals(test2, fridge.getGroceries(),"Get method not return correct values");
  }

  @Test
  @DisplayName("Test: increase quantity method")
  void testIncreaseQuantity() {
    Fridge fridge = new Fridge();
    Grocery cheese = new Grocery("Cheese", "kg", 100f);
    GroceryItem cheeseItem = new GroceryItem(cheese, LocalDate.of(2025, 12, 31), 1f);
    fridge.addGrocery(cheeseItem);

    fridge.increaseQuantity("Cheese", 1f);

    cheeseItem.increaseQuantity(1f);

    List<GroceryItem> test = new ArrayList<>();
    test.add(cheeseItem);

    List<GroceryItem> test2 = new ArrayList<>();

    assertEquals(test, fridge.getGroceries(),"Get method not return correct values");
    assertNotEquals(test2, fridge.getGroceries(),"Get method not return correct values");
  }

  @Test
  @DisplayName("Test: increase quantity method with negative value")
  void testIncreaseQuantityNegative() {
    Fridge fridge = new Fridge();
    Grocery cheese = new Grocery("Cheese", "kg", 100f);
    GroceryItem cheeseItem = new GroceryItem(cheese, LocalDate.of(2025, 12, 31), 1f);
    fridge.addGrocery(cheeseItem);

    fridge.increaseQuantity("Cheese", -1f);

    cheeseItem.increaseQuantity(1f);

    List<GroceryItem> test = new ArrayList<>();
    test.add(cheeseItem);

    List<GroceryItem> test2 = new ArrayList<>();

    assertEquals(test, fridge.getGroceries(),"Get method not return correct values");
    assertNotEquals(test2, fridge.getGroceries(),"Get method not return correct values");
  }

  @Test
  @DisplayName("Test: decrease quantity method")
  void decreaseQuantity() {
    Fridge fridge = new Fridge();
    Grocery cheese = new Grocery("Cheese", "kg", 100f);
    GroceryItem cheeseItem = new GroceryItem(cheese, LocalDate.of(2025, 12, 31), 1f);
    fridge.addGrocery(cheeseItem);

    fridge.increaseQuantity("Cheese", 0.5f);

    cheeseItem.decreaseQuantity(0.5f);

    List<GroceryItem> test = new ArrayList<>();
    test.add(cheeseItem);

    List<GroceryItem> test2 = new ArrayList<>();

    assertEquals(test, fridge.getGroceries(),"Get method not return correct values");
    assertNotEquals(test2, fridge.getGroceries(),"Get method not return correct values");
  }

  @Test
  @DisplayName("Test: decrease quantity method with negative value")
  void decreaseQuantityNegative() {
    Fridge fridge = new Fridge();
    Grocery cheese = new Grocery("Cheese", "kg", 100f);
    GroceryItem cheeseItem = new GroceryItem(cheese, LocalDate.of(2025, 12, 31), 1f);
    fridge.addGrocery(cheeseItem);

    fridge.decreaseQuantity("Cheese", -0.5f);

    cheeseItem.decreaseQuantity(0.5f);

    List<GroceryItem> test = new ArrayList<>();
    test.add(cheeseItem);

    List<GroceryItem> test2 = new ArrayList<>();

    assertEquals(test, fridge.getGroceries(),"Get method not return correct values");
    assertNotEquals(test2, fridge.getGroceries(),"Get method not return correct values");
  }

  @Test
  @DisplayName("Test: decrease quantity method to below zero")
  void decreaseQuantityZero() {
    Fridge fridge = new Fridge();
    Grocery cheese = new Grocery("Cheese", "kg", 100f);
    GroceryItem cheeseItem = new GroceryItem(cheese, LocalDate.of(2025, 12, 31), 1f);
    fridge.addGrocery(cheeseItem);

    fridge.decreaseQuantity("Cheese", 1f);

    List<GroceryItem> test = new ArrayList<>();
    test.add(cheeseItem);

    List<GroceryItem> test2 = new ArrayList<>();

    assertEquals(test2, fridge.getGroceries(),"Get method not return correct values");
    assertNotEquals(test, fridge.getGroceries(),"Get method not return correct values");
  }
}