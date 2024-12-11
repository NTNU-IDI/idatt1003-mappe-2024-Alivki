package edu.ntnu.idi.idatt.utils;

/**
 * Util class to print menus in text user interface.
 */
public class PrintMenus {
  /**
   * Private constructor to prevent instantiation.
   *
   * @throws UnsupportedOperationException if utility class cannot be instantiated.
   */
  private PrintMenus() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("This class cannot be instantiated");
  }

  /**
   * .
   *
   * @return a {@link String} containing the menu.
   */
  public static String printMainMenu() {
    return """
          ----------------------------------------------
                              Menu
          ----------------------------------------------
          
              1. Edit and see your fridge
              2. Edit and see your cookbook
          
              0. Exit program
          
          ----------------------------------------------
          
          Type in the number you want:
          """;
  }

  /**
   * .
   *
   * @return a {@link String} containing the menu.
   */
  public static String printFridgeMenu() {
    return """
          ----------------------------------------------
                           Fridge menu
          ----------------------------------------------
          
              1. Add a grocery to the fridge
              2. Remove grocery from the fridge
              3. Increase quantity to a grocery
              4. Decrease quantity to a grocery
              5. See fridge overview and total price
              6. See a specific item
              7. See all groceries that has expired
          
              8. Go back to menu
              0. Exit program
          
          ----------------------------------------------
          
          Type in the number you want:
          """;
  }

  /**
   * .
   *
   * @return a {@link String} containing the menu.
   */
  public static String printCookbookMenu() {
    return """
          ----------------------------------------------
                          Cookbook menu
          ----------------------------------------------
          
              1. Add recipe
              2. Remove recipe
              3. See cookbook
              4. See recipe
              5. See recipe suggestions
          
              8. Go back to menu
              0. Exit program
          
          ----------------------------------------------
          
          Type in the number you want:
          """;
  }
}
