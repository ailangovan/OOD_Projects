package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the composite Rectangle class. It stores the basic shapes as a list of
 * Symbols.
 * Created by Oz on 4/25/2017.
 */
public class Rectangle implements Symbol {
  private List<Symbol> lineSegments;

  /**
   * Constructs a triangle from a List<Symbols></Symbols>. The list must contain three
   * valid linesegments for the triangle to be created. Line segments must all connect within
   * error threshold and the degrees between each linesegment should be sum to ~180 degrees.
   *
   * @param symbolList is the list of symbols to attempt to build a triangle from.
   * @throws IllegalArgumentException if the input does not create a valid triangle.
   */
  public Rectangle(List<Symbol> symbolList) throws IllegalArgumentException {
    if (symbolList.size() != 4 || !allLines(symbolList) || !validRectangle(symbolList)) {
      throw new IllegalArgumentException("Not a valid Triangle Input");
    }
    lineSegments = new ArrayList<>();
    for (Symbol s : symbolList) {
      Symbol toAdd = s.getSymbol();
      lineSegments.add(toAdd);
    }
  }

  /**
   * Checks if all symbols tested are line segments or not,
   *
   * @param symbols is the list of symbols to check if there are valid line segments.
   * @return true if list is empty or contains only line segments and false otherwise.
   */
  private static boolean allLines(List<Symbol> symbols) {
    for (Symbol s : symbols) {
      if (!(s instanceof LineSegment)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Checks if this is a valid Rectangle.
   * @param lines is the lines to check for rectangularity.
   * @return boolean true if it is rectangle, and false otherwise.
   */
  private static boolean validRectangle(List<Symbol> lines){

    for(Symbol s : lines) {
      int count = 0;
      for(Symbol b : lines) {
        LineSegment lineS = (LineSegment) s;
        LineSegment lineB = (LineSegment) b;
        if(lineS.shareOnePoint(lineB)){
          double deg = lineS.getDegree(lineB);
          if(80 < deg && deg < 100) {
            count++;
          }
        }
      }
      if(count != 2) {
        return false;
      }
    }
    return true;
  }

  /**
   * Returns the type of the given symbol.
   *
   * @return the SymbolType that corresponds to this Symbol's type.
   */
  @Override
  public SymbolType symbolType() {
    return SymbolType.Rectangle;
  }

  /**
   * Returns the value of the given Symbol.  This value is a copy of the given in order
   * to prevent mutation.
   *
   * @return an exact copy of the given Symbol.
   */
  @Override
  public Symbol getSymbol() {
    List<Symbol> temp = new ArrayList<>();
    for (Symbol c : lineSegments) {
      temp.add(c.getSymbol());
    }
    Symbol output = new Rectangle(temp);
    return output;
  }

  /**
   * Returns the LineSegmeents that construct this Rectangle.
   * @return a List of the line segments that construct the Rectangle.
   */
  public List<Symbol> getLineSegments() {
    List<Symbol> temp = new ArrayList<>();
    for (Symbol s: lineSegments) {
      temp.add(s.getSymbol());
    }
    return temp;
  }
}
