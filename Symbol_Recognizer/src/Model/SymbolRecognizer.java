package Model;

import java.util.List;

/**
 * This interface represents all available methods for a SymbolRecognizer.
 */
public interface SymbolRecognizer {
  /**
   * Adds a basic symbol to the SymbolRecognizer.  It then checks to see if a composite
   * symbol can be formed out of the symbols the SymbolRecognizer currently has.
   * @param symbol is the BasicSymbol to be added to the Model.
   * @throws IllegalArgumentException if a non-basic symbol is attempted to be added.
   */
  void addBasicSymbol(Symbol symbol) throws IllegalArgumentException;

  /**
   * Prints the current Composite Symbols that have been completed.
   * @return the current list of Symbols in the symbolBank.
   */
  List<Symbol> returnCurrentSymbols();
}
