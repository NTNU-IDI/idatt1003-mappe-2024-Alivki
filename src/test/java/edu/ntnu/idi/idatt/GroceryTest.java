package edu.ntnu.idi.idatt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import jdk.jfr.Description;
import jdk.jfr.Name;
import org.junit.jupiter.api.Test;

class GroceryTest {
  Grocery cheese = new Grocery("cheese", "kg", 100f, "01.01.2025", 1.2f);

  @Test
  @Name("test Constructor Throws IllegalArgumentException When Name Is Null")
  @Description("test")
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
    assertThrows(NullPointerException.class, () ->
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
        new Grocery("cheese", "kg", 100f, "01.01.2025", 10001f)
    );
  }

  @Test
  void testConstructorThrowsIllegalArgumentExceptionWhenExpirationDateIsInvalid() {
    assertThrows(DateTimeParseException.class, () ->
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
  void testIncreaseQuantityValidPositiveInput() {
    cheese.increaseQuantity(1.3f);
    assertEquals(2.5f, cheese.getQuantity());
    assertNotEquals(1.3f, cheese.getQuantity());
  }

  @Test
  void testIncreaseQuantityZeroInput() {
    cheese.increaseQuantity(0f);
    assertEquals(1.2f, cheese.getQuantity());
    assertNotEquals(1.3f, cheese.getQuantity());
  }

  @Test
  void testIncreaseQuantityValidPositiveInputForUnitStk() {
    Grocery egg = new Grocery("egg", "stk", 50f, "01.01.2025", 1.2f);
    assertEquals(1f, egg.getQuantity());
    egg.increaseQuantity(1.3f);
    assertEquals(2f, egg.getQuantity());
    assertNotEquals(2.5f, egg.getQuantity());
  }

  @Test
  void testIncreaseQuantityZeroInputForUnitStk() {
    Grocery egg = new Grocery("egg", "stk", 50f, "01.01.2025", 1.2f);
    egg.increaseQuantity(0f);
    assertEquals(1f, egg.getQuantity());
    assertNotEquals(1.3f, egg.getQuantity());
  }

  @Test
  void testIncreaseQuantityThrowsIllegalArgumentExceptionWhenNewQuantityIsNegative() {
    assertThrows(IllegalArgumentException.class, () ->
        cheese.increaseQuantity(-2f)
    );
  }

  @Test
  void testIncreaseQuantityThrowsIllegalArgumentExceptionWhenNewQuantityIsAbove10000() {
    assertThrows(IllegalArgumentException.class, () ->
        cheese.increaseQuantity(10001f)
    );
  }

  @Test
  void testDecreaseQuantityValidPositiveInput() {
    cheese.decreaseQuantity(0.2f);
    assertEquals(1f, cheese.getQuantity());
    assertNotEquals(1.3f, cheese.getQuantity());
  }

  @Test
  void testDecreaseQuantityZeroInput() {
    cheese.decreaseQuantity(0f);
    assertEquals(1.2f, cheese.getQuantity());
    assertNotEquals(1.3f, cheese.getQuantity());
  }

  @Test
  void testDecreaseQuantityValidPositiveInputForUnitStk() {
    Grocery egg = new Grocery("egg", "stk", 50f, "01.01.2025", 2.2f);
    assertEquals(2f, egg.getQuantity());
    egg.decreaseQuantity(1.3f);
    assertEquals(1f, egg.getQuantity());
    assertNotEquals(2.5f, egg.getQuantity());
  }

  @Test
  void testDecreaseQuantityZeroInputForUnitStk() {
    Grocery egg = new Grocery("egg", "stk", 50f, "01.01.2025", 2.2f);
    egg.increaseQuantity(0f);
    assertEquals(2f, egg.getQuantity());
    assertNotEquals(1.3f, egg.getQuantity());
  }

  @Test
  void testDecreaseQuantityThrowsIllegalArgumentExceptionWhenNewQuantityIsNegative() {
    assertThrows(IllegalArgumentException.class, () ->
        cheese.decreaseQuantity(-2f)
    );
  }

  @Test
  void testDecreaseQuantityThrowsIllegalArgumentExceptionWhenNewQuantityIsAbove10000() {
    assertThrows(IllegalArgumentException.class, () ->
        cheese.decreaseQuantity(10001f)
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
    assertEquals(String.format(
            "| cheese       | 1.20KG    | 100.00kr        | 2025-01-01       |%n"),
        cheese.toString());
    assertNotEquals("cheese: 1.2kg", cheese.toString());
  }
}