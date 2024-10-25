package edu.ntnu.idi.idatt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class GroceryTest {
  Grocery cheese = new Grocery("cheese", "kg", 100f, "01.01.2025", 1.2f);

  @Test
  void testConstructorThrowsIllegalArgumentExceptionWhenNameIsNull() {
    assertThrows(IllegalArgumentException.class, () ->
        new Grocery(null, "kg", 100f, "01.01.2025", 1f)
    );
  }

  @Test
  void testConstructorThrowsIllegalArgumentExceptionWhenUnitIsNull() {
    assertThrows(IllegalArgumentException.class, () ->
        new Grocery("cheese", null, 100f, "01.01.2025", 1f)
    );
  }

  @Test
  void testConstructorThrowsIllegalArgumentExceptionWhenDateIsNull() {
    assertThrows(IllegalArgumentException.class, () ->
        new Grocery("cheese", "kg", 100f, null, 1f)
    );
  }

  @Test
  void testConstructorThrowsIllegalArgumentExceptionWhenPriceIsNegative() {
    assertThrows(IllegalArgumentException.class, () ->
        new Grocery("cheese", "kg", -100f, "01.01.2025", 1f)
    );
  }

  @Test
  void testConstructorThrowsIllegalArgumentExceptionWhenQuantityIsNegative() {
    assertThrows(IllegalArgumentException.class, () ->
        new Grocery("cheese", "kg", 100f, "01.01.2025", -1f)
    );
  }

  @Test
  void testConstructorThrowsIllegalArgumentExceptionWhenNameLengthIsAbove32() {
    assertThrows(IllegalArgumentException.class, () ->
        new Grocery("Lorem ipsum odor amet, consectett", "kg", 100f, "01.01.2025", 1f)
    );
  }

  @Test
  void testConstructorThrowsIllegalArgumentExceptionWhenPriceIsAbove10000() {
    assertThrows(IllegalArgumentException.class, () ->
        new Grocery("cheese", "kg", 10001f, "01.01.2025", 1f)
    );
  }

  @Test
  void testConstructorThrowsIllegalArgumentExceptionWhenQuantityIsAbove1000() {
    assertThrows(IllegalArgumentException.class, () ->
        new Grocery("cheese", "kg", 100f, "01.01.2025", 1001f)
    );
  }

  @Test
  void testConstructorThrowsIllegalArgumentExceptionWhenExpirationDateIsInvalid() {
    assertThrows(IllegalArgumentException.class, () ->
        new Grocery("cheese", "kg", 100f, "invalid", 1f)
    );
  }

  @Test
  void testConstructorThrowsIllegalArgumentExceptionWhenExpirationDateIsInPast() {
    assertThrows(IllegalArgumentException.class, () ->
        new Grocery("cheese", "kg", 100f, "01.01.2023", 1f)
    );
  }

  @Test
  void testGetNameIsEqualsToName() {
    assertEquals("cheese", cheese.getName());
    assertNotEquals("milk", cheese.getName());
  }

  @Test
  void testGetUnitIsEqualsToUnit() {
    assertEquals("KG", cheese.getUnit());
    assertNotEquals("L", cheese.getUnit());
  }

  @Test
  void testGetPriceIsEqualsToPrice() {
    assertEquals(100, cheese.getPrice());
    assertNotEquals(110, cheese.getPrice());
  }

  @Test
  void testGetQuantityIsEqualsToQuantity() {
    assertEquals(1.2f, cheese.getQuantity());
    assertNotEquals(1.3f, cheese.getQuantity());
  }

  @Test
  void testGetExpirationDateIsEqualsToExpirationDate() {
    assertEquals(LocalDate.of(2025, 1, 1), cheese.getExpirationDate());
    assertNotEquals(LocalDate.of(2025, 10, 11), cheese.getExpirationDate());
  }

  @Test
  void testSetQuantityValidPositiveInput() {
    cheese.setQuantity(1.3f);
    assertEquals(2.5f, cheese.getQuantity());
    assertNotEquals(1.3f, cheese.getQuantity());
  }

  @Test
  void testSetQuantityZeroInput() {
    cheese.setQuantity(0f);
    assertEquals(1.2f, cheese.getQuantity());
    assertNotEquals(1.3f, cheese.getQuantity());
  }

  @Test
  void testSetQuantityThrowsIllegalArgumentExceptionWhenNewQuantityIsNegative() {
    assertThrows(IllegalArgumentException.class, () ->
        cheese.setQuantity(-2f)
    );
  }

  @Test
  void testSetQuantityThrowsIllegalArgumentExceptionWhenNewQuantityIsAbove1000() {
    assertThrows(IllegalArgumentException.class, () ->
        cheese.setQuantity(1001f)
    );
  }

  @Test
  void testConstructorRoundsQuantityToSingleDigitIfUnitIsStk() {
    Grocery milk = new Grocery("milk", "stk", 100f, "01.01.2025", 2.5f);
    assertEquals(3f, milk.getQuantity());
    assertNotEquals(2.5f, milk.getQuantity());
  }

  @Test
  void testConstructorDoesNotRoundIfQuantityIsOtherThenStk() {
    Grocery juice = new Grocery("milk", "l", 100f, "01.01.2025", 2.5f);
    Grocery ham = new Grocery("milk", "kg", 100f, "01.01.2025", 2.5f);
    assertEquals(2.5f, juice.getQuantity());
    assertEquals(2.5f, ham.getQuantity());
    assertNotEquals(2f, juice.getQuantity());
    assertNotEquals(2f, ham.getQuantity());
  }

  @Test
  void testToStringIsEqualsToOutput() {
    assertEquals(
        "cheese: 1.2 KG, price per KG: 100.0, expiration date: January 1, 2025",
        cheese.toString());
    assertNotEquals("cheese: 1.2kg", cheese.toString());
  }
}