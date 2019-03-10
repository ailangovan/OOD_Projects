package Controller;

import java.util.List;

import Model.Point;
import Model.Symbol;
import View.ISymbolView;

/**
 * This interface contains all available methods for the Controller of a SymbolRecognizer.
 */
public interface ISymbolController {
  /**
   * Returns all of the Symbols stored by the Model.
   *
   * @return the list of Symbols stored in the Model.
   */
  List<Symbol> getSymbols();

  /**
   * Adds a symbol to the Model.
   *
   * @param userInput is the set of coordinates from the User clicking and dragging the mouse.
   */
  void addSymbol(List<Point> userInput);

  /**
   * Gets the current user input in controller.
   *
   * @return list of points that is the user inputs.
   */
  List<Point> getUserInput();

  /**
   * Sets the view of the controller to the given view.
   *
   * @param view is the view to set as this controller's view.
   */
  void setView(ISymbolView view);
}
