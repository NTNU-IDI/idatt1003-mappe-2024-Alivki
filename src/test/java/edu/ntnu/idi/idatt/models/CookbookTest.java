package edu.ntnu.idi.idatt.models;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CookbookTest {

  @Test
  @DisplayName("Test: add recipe method of cookbook")
  void testAddRecipe() {
    Cookbook cookbook = new Cookbook(new Fridge());
    Recipe taco = new Recipe("Taco", "Description", "Procedure", 4);
    final List<Recipe> recipes = new ArrayList();
    final List<Recipe> test = new ArrayList();
    recipes.add(taco);

    cookbook.addRecipe(taco);

    assertEquals(recipes, cookbook.getRecipes(), "Getter returned the wrong recipes output.");
    assertNotEquals(test, cookbook.getRecipes(), "Getter returned the wrong recipes output.");
  }

  @Test
  @DisplayName("Test: remove recipe method of cookbook")
  void removeRecipe() {
    Cookbook cookbook = new Cookbook(new Fridge());
    Recipe taco = new Recipe("Taco", "Description", "Procedure", 4);

    cookbook.addRecipe(taco);
    cookbook.removeRecipe("Taco");

    final List<Recipe> recipes = new ArrayList();
    final List<Recipe> test = new ArrayList();
    recipes.add(taco);

    assertEquals(test, cookbook.getRecipes(), "Getter returned the wrong recipes output.");
    assertNotEquals(recipes, cookbook.getRecipes(), "Getter returned the wrong recipes output.");
  }
}