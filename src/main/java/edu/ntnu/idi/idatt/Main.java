package edu.ntnu.idi.idatt;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * .
 */
public class Main {

  /**
   * .
   */
  public static void main(String[] args) {
    String dateInput = "23.10.2024";
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    LocalDate date = LocalDate.parse(dateInput, dateFormat);

    Grocery ost = new Grocery("ost", "kg", 100, date, 1.2f);

    System.out.println(ost.toString());
  }
}