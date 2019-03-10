package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This represents the Triangle Composite shape. It consists of three
 * line segments stored in a List<Symbols></Symbols>.
 * Created by aswat on 4/6/2017.
 */
public class Triangle implements Symbol {
  private List<Symbol> lineSegments;
  private SymbolType triangleType;

  /**
   * Constructs a triangle from a List<Symbols></Symbols>. The list must contain three
   * valid linesegments for the triangle to be created. Line segments must all connect within
   * error threshold and the degrees between each linesegment should be sum to ~180 degrees.
   *
   * @param symbolList is the list of symbols to attempt to build a triangle from.
   * @throws IllegalArgumentException if the input does not create a valid triangle.
   */
  public Triangle(List<Symbol> symbolList) throws IllegalArgumentException {
    if (symbolList.size() != 3 || !allLines(symbolList) || !validTriangle(symbolList)) {
      throw new IllegalArgumentException("Not a valid Triangle Input");
    }
    lineSegments = new ArrayList<>();
    for (Symbol s : symbolList) {
      Symbol toAdd = s.getSymbol();
      lineSegments.add(toAdd);
    }
    if (equilateralTriangle()) {
      this.triangleType = SymbolType.EquilateralTriangle;
    } else {
      this.triangleType = SymbolType.Triangle;
    }
  }

  /**
   * Checks if this triangle is an equilateral triangle.
   *
   * @return true if all angles are 60 (+/-) error degrees.
   */
  private boolean equilateralTriangle() {
    LineSegment a = (LineSegment) lineSegments.get(0);
    LineSegment b = (LineSegment) lineSegments.get(1);
    LineSegment c = (LineSegment) lineSegments.get(2);

    Double ab = a.getDegree(b);
    Double bc = b.getDegree(c);
    Double ac = a.getDegree(c);

    return (ab < 65 && ab > 55 && bc < 65 && bc > 55 && ac < 65 && ac > 55);

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

  private static boolean validTriangle(List<Symbol> lineList) {
    if (lineList.size() != 3) {
      return false;
    }
    LineSegment a = (LineSegment) lineList.get(0);
    LineSegment b = (LineSegment) lineList.get(1);
    LineSegment c = (LineSegment) lineList.get(2);

    if (a.shareOnePoint(b) && b.shareOnePoint(c) && c.shareOnePoint(a)) {
      double aDeg = a.getDegree(b);
      double bDeg = b.getDegree(c);
      double cDeg = c.getDegree(a);
      double sumDeg = aDeg + bDeg + cDeg;
      if (sumDeg < 195 && sumDeg > 165) {
        return true;
      }
    }
    return false;
  }

  /**
   * Get the symboltype of this triangle.
   *
   * @return the symboltype of this triangle.
   */
  @Override
  public SymbolType symbolType() {
    return this.triangleType;
  }

  /**
   * Creates a copy of this triangle and returns the copy.
   *
   * @return the copy of this Triangle.
   */
  @Override
  public Symbol getSymbol() {
    List<Symbol> temp = new ArrayList<>();
    for (Symbol c : lineSegments) {
      temp.add(c.getSymbol());
    }
    Symbol output = new Triangle(temp);
    return output;
  }

  /**
   * Returns the LineSegmeents that construct this Triangle.
   * @return a List of the line segments that construct the Triangle.
   */
  public List<Symbol> getLineSegments() {
    List<Symbol> temp = new ArrayList<>();
    for (Symbol s: lineSegments) {
      temp.add(s.getSymbol());
    }
    return temp;
  }

  /**
   * This method compares this triangle to other object and checks if they contain the same
   * line segments.
   * @param other is the object to compare to this triangle.
   * @return true if other object is a triangle and same line segments as this one.
   */
  public boolean equals(Object other) {
    if (other instanceof Triangle) {
      Triangle otherTriangle = (Triangle) other;
      List<Symbol> otherSegments = otherTriangle.lineSegments;
      if (otherSegments.size() == 3) {
        for (Symbol a : otherSegments) {
          if (!this.lineSegments.contains(a)) {
            return false;
          }
        }
        return true;
      }
    }
    return false;
  }

  /**
   * Generate the hashcode for this Triangle Object.
   *
   * @return the int hashcode for this triangle object.
   */
  public int hashCode() {
    return Objects.hash(lineSegments.get(0), lineSegments.get(1),
            lineSegments.get(2), this.triangleType);
  }
}
