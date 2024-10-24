package edu.ntnu.idi.idatt;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GroceryTest {
  Grocery ost = new Grocery("ost", "kg", 100, "11.10.2024", 1.142f);

  @Test
  void getName() {
    assertEquals("ost", ost.getName());
    assertNotEquals("melk", ost.getName());
  }

  @Test
  void getUnit() {
    assertEquals("kg", ost.getUnit());
    assertNotEquals("L", ost.getUnit());
  }

  @Test
  void getPrice() {
    assertEquals(100, ost.getPrice());
    assertNotEquals(110, ost.getPrice());
  }

  @Test
  void getQuantity() {
    assertEquals(1.142f, ost.getQuantity());
    assertNotEquals(1.2f, ost.getQuantity());
  }

  @Test
  void getExpirationDate() {
    assertEquals(LocalDate.of(2024, 10, 11), ost.getExpirationDate());
    assertNotEquals(LocalDate.of(2025, 10, 11), ost.getExpirationDate());
  }

  @Test
  void setQuantity() {
    ost.setQuantity(1.2f);
    assertEquals(1.2f, ost.getQuantity());
    assertNotEquals(1.1f, ost.getQuantity());
  }

  @Test
  void testToString() {
    assertEquals(
        "ost: 1.142 kg, price per kg: 100.0, expiration date: October 11, 2024",
        ost.toString());
    assertNotEquals("ost: 1.2kg", ost.toString());
  }
}