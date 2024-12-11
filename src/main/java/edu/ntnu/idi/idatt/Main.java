package edu.ntnu.idi.idatt;

import edu.ntnu.idi.idatt.views.TextUserInterface;

/**
 * Wrapper class for the static main method to run the application.
 */
public class Main {

  /**
   * Main method that runs the application.
   *
   * @param args are the arguments for the main method.
   */
  public static void main(String[] args) {
    TextUserInterface ui = new TextUserInterface();
    ui.init();
    ui.start();
  }
}