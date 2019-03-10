package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an implementation of a SymbolRecognizer.  The SymbolRecognizer takes
 * in basic symbols consisting of LineSegments and Circles, and will combine them into
 * appropriate composite Symbols consisting of the most recently entered symbols.  The
 * composite symbols are Triangles, Equilateral Triangles, and Snowmen.
 */
public class SymbolRecognizerImpl implements SymbolRecognizer {
  private List<Symbol> symbolBank;

  /**
   * Constructs a SymbolRecognizerImpl object with an empty symbolBank.
   * symbolBank is a list where the Symbols in the Model are stored.
   */
  public SymbolRecognizerImpl() {
    this.symbolBank = new ArrayList();
  }

  /**
   * Adds a basic symbol to the SymbolRecognizer.  It then checks to see if a composite
   * symbol can be formed out of the symbols the SymbolRecognizer currently has.
   * Basic symbols are LineSegments and Circles.  Composite Symbols are Triangles, Equilateral
   * Triangles, and Snowman.
   * @param symbol is the BasicSymbol to be added to the Model.
   * @throws IllegalArgumentException if a non-basic symbol is attempted to be added.
   */
  @Override
  public void addBasicSymbol(Symbol symbol) throws IllegalArgumentException {
    SymbolType type = symbol.symbolType();
    if (type == SymbolType.Circle || type == SymbolType.LineSegment) {
      symbolBank.add(symbol);
      this.recognizeCompositeSymbol(type);
    } else {
      throw new IllegalArgumentException("Only basic symbols can be added by the user");
    }
  }

  /**
   * Given the current set of basic symbols stored by the Model,
   * Determines if there are any of the shapes the Model recognizes,
   * that can be built.  If no symbols can be formed, nothing occurs.
   */
  private void recognizeCompositeSymbol(SymbolType type) {
    if (type == SymbolType.LineSegment) {
      this.attemptTriangle();
      this.attemptRectangle();
    } else {
      this.attemptSnowman();
    }
  }

  private void attemptHallow() {
    try {
      List<Symbol> cSymbol = this.getSymbol(1, SymbolType.Circle);
      List<Symbol> lSymbol = this.getSymbol(1, SymbolType.LineSegment);
      List<Symbol> tSymbol = this.getSymbol(1, SymbolType.Triangle);

      List<Symbol> hallowList = new ArrayList<>();
      hallowList.add(cSymbol.get(0));
      hallowList.add(lSymbol.get(0));
      hallowList.add(tSymbol.get(0));

      Hallow toAdd = new Hallow(hallowList);
      for (int i = 0; i < 1; i++) {
        this.removeNextSymbol(SymbolType.Circle);
        this.removeNextSymbol(SymbolType.LineSegment);
        this.removeNextSymbol(SymbolType.Triangle);
      }
      this.symbolBank.add(toAdd);
    } catch (IllegalArgumentException e) {
      // Empty catch block because Hallow is invalid.
    }
  }
  /**
   * Attempts to create a Snowman out of the 3 most recent Circles if they are present.
   * If a Snowman can be created, all three circles are removed and a snowman is added.
   * Otherwise, nothing happens to the symbolBank.
   */
  private void attemptSnowman() {
    try {
      List<Symbol> symbols = this.getSymbol(3, SymbolType.Circle);
      Snowman toAdd = new Snowman(symbols);
      for (int i = 0; i < 3; i++) {
        this.removeNextSymbol(SymbolType.Circle);
      }
      this.symbolBank.add(toAdd);
    } catch (IllegalArgumentException e) {
      // Empty catch block because if Snowman to add is invalid,
      // then do nothing to the symbolBank.
    }
  }

  /**
   * Attempts to create a Triangle out of the 3 most recent LineSegments if they are present.
   * If a Triangle can be created, all three LineSegments are removed and a triangle is added.
   * Otherwise, nothing happens to the symbolBank.
   */
  private void attemptTriangle() {
    try {
      List<Symbol> symbols = this.getSymbol(3, SymbolType.LineSegment);
      Triangle toAdd = new Triangle(symbols);
      for (int i = 0; i < 3; i++) {
        this.removeNextSymbol(SymbolType.LineSegment);
      }
      this.symbolBank.add(toAdd);
      this.attemptHallow();
    } catch (IllegalArgumentException e) {
      // Empty catch block because if Triangle to add is invalid,
      // then do nothing to the symbolBank.
    }
  }

  /**
   * Attempts to create a Rectangle out of the 3 most recent LineSegments if they are present.
   * If a Rectangle can be created, all three LineSegments are removed and a triangle is added.
   * Otherwise, nothing happens to the symbolBank.
   */
  private void attemptRectangle() {
    try {
      List<Symbol> symbols = this.getSymbol(4, SymbolType.LineSegment);
      Rectangle toAdd = new Rectangle(symbols);
      for(int i = 0; i < 4; i++){
        this.removeNextSymbol(SymbolType.LineSegment);
      }
      this.symbolBank.add(toAdd);
    } catch (IllegalArgumentException e) {
      // Empty catch block because if Rectangle to add is invalid,
      // then do nothing to the symbolBank.
    }
  }

  /**
   * Helper intended to locate n number of Symbols based on the SymbolType passed.  It leverages
   * the Symbol interface's getSymbol() function in order to not mutate the original symbolBank.
   *
   * @param n          is the number of symbols to find.
   * @param symbolType is the type of symbol being searched for.
   * @return a list containing n number of the symbols of type SymbolType in the symbolBank.
   * @throws IllegalArgumentException if < n symbols are found.  This is determined if there is
   *                                  nothing returned before reaching the end of the symbolBank.
   */
  private List<Symbol> getSymbol(int n, SymbolType symbolType)
          throws IllegalArgumentException {
    List<Symbol> output = new ArrayList();
    for (int i = symbolBank.size() - 1; i >= 0; i--) {
      if (symbolBank.get(i).symbolType() == symbolType) {
        output.add(symbolBank.get(i).getSymbol());
      }

      if (output.size() == n) {
        return output;
      }
    }
    throw new IllegalArgumentException("Not enough of the specified symbol found.");
  }

  /**
   * Removes the next symbol in the symbolBank that is of the given SymbolType.
   *
   * @param symbolType is the SymbolType that is to be removed next from the symbolBank.
   */
  private void removeNextSymbol(SymbolType symbolType) {
    for (int i = symbolBank.size() - 1; i >= 0; i--) {
      if (this.symbolBank.get(i).symbolType() == symbolType) {
        this.symbolBank.remove(i);
        return;
      }
    }
  }

  /**
   * Returns the current symbolBank.  Copies all elements in the symbolBank to a new List in
   * order to prevent mutation.
   *
   * @return a list of symbols containing all values in the symbolBank.
   */
  @Override
  public List<Symbol> returnCurrentSymbols() {
    List<Symbol> output = new ArrayList();
    for (Symbol s : symbolBank) {
      output.add(s.getSymbol());
    }
    return output;
  }
}
