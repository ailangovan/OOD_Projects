package Model;

/**
 * This interface represents methods implemented by all symbol objects.
 * Symbols for this implementation include Triangles, Equilateral Triangles,
 * Line Segments, Circles, and Snowmen.
 */
public interface Symbol {
  /**
   * Returns the type of the given symbol.
   *
   * @return the SymbolType that corresponds to this Symbol's type.
   */
  SymbolType symbolType();

  /**
   * Returns the value of the given Symbol.  This value is a copy of the given in order
   * to prevent mutation.
   * @return an exact copy of the given Symbol.
   */
  Symbol getSymbol();
}
