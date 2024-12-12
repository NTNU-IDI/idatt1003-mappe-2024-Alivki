package edu.ntnu.idi.idatt.models;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RecipeTest {

  @Test
  @DisplayName("Test: Recipe name getter")
  void testNameGetter() {
    Recipe taco = new Recipe("Taco", "Description", "Procedure", 1);
    assertEquals("Taco", taco.getName());
    assertNotEquals("Fish", taco.getName());
  }

  @Test
  @DisplayName("Test: Recipe constructor handles null")
  void testNameGetterNull() {
    Recipe taco = new Recipe(null, "Description", "Procedure", 1);
    assertEquals(null, taco.getName());
    assertNotEquals("Fish", taco.getName());
  }

  @Test
  @DisplayName("Test: Recipe constructor handles empty")
  void testNameGetterEmpty() {
    Recipe taco = new Recipe("", "Description", "Procedure", 1);
    assertEquals("", taco.getName());
    assertNotEquals("Fish", taco.getName());
  }

  @Test
  @DisplayName("Test: Recipe description getter")
  void testDescriptionGetter() {
    Recipe taco = new Recipe("Taco", "Description", "Procedure", 1);
    assertEquals("Description", taco.getDescription());
    assertNotEquals("Des", taco.getDescription());
  }

  @Test
  @DisplayName("Test: Recipe constructor description handles null")
  void testDescriptionGetterNull() {
    Recipe taco = new Recipe("Taco", null, "Procedure", 1);
    assertEquals(null, taco.getDescription());
    assertNotEquals("Des", taco.getDescription());
  }

  @Test
  @DisplayName("Test: Recipe constructor description handles empty")
  void testDescriptionGetterEmpty() {
    Recipe taco = new Recipe("Taco", "", "Procedure", 1);
    assertEquals("", taco.getDescription());
    assertNotEquals("Des", taco.getDescription());
  }

  @Test
  @DisplayName("Test: Recipe procedure getter")
  void testProcedureGetter() {
    Recipe taco = new Recipe("Taco", "Description", "Procedure", 1);
    assertEquals("Procedure", taco.getProcedure());
    assertNotEquals("Des", taco.getProcedure());
  }

  @Test
  @DisplayName("Test: Recipe constructor procedure handles null")
  void testProcedureGetterNull() {
    Recipe taco = new Recipe("Taco", "Description", null, 1);
    assertEquals(null, taco.getProcedure());
    assertNotEquals("Des", taco.getProcedure());
  }

  @Test
  @DisplayName("Test: Recipe constructor procedure handles empty")
  void testProcedureGetterEmpty() {
    Recipe taco = new Recipe("Taco", "Description", "", 1);
    assertEquals("", taco.getProcedure());
    assertNotEquals("Des", taco.getProcedure());
  }

  @Test
  @DisplayName("Test: Recipe servings getter")
  void getServings() {
    Recipe taco = new Recipe("Taco", "Description", "Procedure", 1);
    assertEquals(1, taco.getServings());
    assertNotEquals(2, taco.getServings());
  }

  @Test
  @DisplayName("Test: Recipe constructor servings handles negative")
  void getServingsNegative() {
    Recipe taco = new Recipe("Taco", "Description", "Procedure", -2);
    assertEquals(-2, taco.getServings());
    assertNotEquals(2, taco.getServings());
  }

  @Test
  @DisplayName("Test: Recipe groceries getter")
  void testGroceriesGetter() {
    Map<Grocery, Float> groceries = new HashMap<>();
    groceries.put(new Grocery("Cheese", "kg", 100f), 1f);
    groceries.put(new Grocery("Milk", "l", 50f), 1f);

    Recipe taco = new Recipe("Taco", "Description", "Procedure", 1);

    taco.addGroceries(groceries);

    assertEquals(groceries, taco.getGroceries());
  }

  @Test
  @DisplayName("Test: Add groceries")
  void testAddGroceries() {
    Grocery cheese = new Grocery("Cheese", "kg", 100f);
    Grocery bread = new Grocery("Bread", "stk", 100f);

    Map<Grocery, Float> groceries = new HashMap<>();
    groceries.put(cheese, 1f);

    Map<Grocery, Float> addGroceries = new HashMap<>();
    addGroceries.put(bread, 1f);

    Recipe taco = new Recipe("Taco", "Description", "Procedure", 1);

    taco.addGroceries(groceries);
    taco.addGroceries(addGroceries);

    groceries.put(bread, 1f);

    assertEquals(groceries, taco.getGroceries());
  }

  @Test
  @DisplayName("Test: Remove groceries")
  void removeGroceries() {
    Grocery cheese = new Grocery("Cheese", "kg", 100f);
    Grocery milk = new Grocery("Milk", "l", 100f);

    Map<Grocery, Float> groceries = new HashMap<>();
    groceries.put(cheese, 1f);
    groceries.put(milk, 1f);

    Map<Grocery, Float> removedGroceries = new HashMap<>();
    removedGroceries.put(cheese, 1f);

    Recipe taco = new Recipe("Taco", "Description", "Procedure", 1);

    taco.addGroceries(groceries);

    List<String> removeGroceries = new ArrayList<>();
    removeGroceries.add("Milk");

    taco.removeGroceries(removeGroceries);

    assertEquals(removedGroceries, taco.getGroceries());
  }

  @Test
  @DisplayName("Test: To string method of recipe")
  void testToString() {
    Recipe taco = new Recipe("Taco", "Description", "Procedure", 1);
    assertEquals("Name: Taco, Description: Description, Procedure: Procedure, Servings: 1",
        taco.toString(), "To string not returning what it should");
    assertNotEquals("Name: Test, Description: Description, Procedure: Procedure, Servings: 1",
        taco.toString(), "To string returned wrong value");
  }

  @Test
  @DisplayName("Test: Equals same object")
  void testEqualsSameObj() {
    Recipe taco = new Recipe("Taco", "Description", "Procedure", 1);

    assertEquals(taco, taco, "An object should be equal to itself.");
  }

  @Test
  @DisplayName("Test: Equals two different objects")
  void testEquals() {
    Recipe taco1 = new Recipe("Taco", "Description", "Procedure", 1);
    Recipe taco2 = new Recipe("Taco", "Description", "Procedure", 1);

    assertTrue(taco1.equals(taco2), "An object should be equal to itself.");
  }

  @Test
  @DisplayName("Test: Equals two different objects")
  void testEqualsDifferentObj() {
    Recipe taco = new Recipe("Taco", "Description", "Procedure", 1);
    Recipe test = new Recipe("test", "Description", "Procedure", 1);

    assertFalse(taco.equals(test), "An object should be equal to itself.");
  }

  @Test
  @DisplayName("Test: override hash code method")
  void testHashCode() {
    Recipe taco1 = new Recipe("Taco", "Description", "Procedure", 1);
    Recipe taco2 = new Recipe("Taco", "Description", "Procedure", 1);
    assertEquals(taco1.hashCode(), taco2.hashCode(),
        "Equal objects must have the same hashCode.");
  }

  @Test
  @DisplayName("Test: override hash code method two different objects")
  void testHashCodeNot() {
    Recipe taco = new Recipe("Taco", "Description", "Procedure", 1);
    Recipe test = new Recipe("test", "Description", "Procedure", 1);
    assertNotEquals(taco.hashCode(), test.hashCode(),
        "Different objects should have different hashCodes.");
  }
}