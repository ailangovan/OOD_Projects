package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the hallow class. I wasn't able to finish this class properly.
 * Created by Oz on 4/25/2017.
 */
public class Hallow implements Symbol {
  List<Symbol> symbols;

  public Hallow(List<Symbol> symbolList) throws IllegalArgumentException {
    if (symbolList.size() != 3 || !validSymbols(symbolList) || !validHallow(symbolList)) {
      throw new IllegalArgumentException("Not a valid Hallow Input");
    }
    symbols = new ArrayList<>();
    for (Symbol s : symbolList) {
      Symbol toAdd = s.getSymbol();
      symbols.add(toAdd);
    }
  }

  private static boolean validSymbols(List<Symbol> symbols) {
    int c = 0;
    int t = 0;
    int l = 0;

    for(Symbol s : symbols) {
      switch (s.symbolType()) {
        case Circle:
          c = 1;
          break;
        case EquilateralTriangle:
          t = 1;
          break;
        case Triangle:
          t = 1;
          break;
        case LineSegment:
          l = 1;
          break;
        default:
          break;
      }
    }
   return (c == 1 && t == 1 && l == 1);
  }

  private static boolean validHallow(List<Symbol> symbolList) {
    Circle c = new Circle(0,0,10);
    // Setup Equilateral Triangle
    LineSegment at = new LineSegment(10, 0, 0, 0);
    LineSegment bt = new LineSegment(10, 0, 5, 8.66);
    LineSegment ct = new LineSegment(0, 0, 5, 8.66);
    List<Symbol> triList = new ArrayList<>();
    triList.add(at);
    triList.add(bt);
    triList.add(ct);

    Triangle t = new Triangle(triList);
    LineSegment l = new LineSegment(0,0,1,1);

    for(int i = 0; i < symbolList.size(); i++) {
      Symbol s = symbolList.get(i);
      switch (s.symbolType()) {
        case Circle:
          c = (Circle) s.getSymbol();
          break;
        case EquilateralTriangle:
          t = (Triangle) s.getSymbol();
          break;
        case Triangle:
          t = (Triangle) s.getSymbol();
          break;
        case LineSegment:
          l = (LineSegment) s.getSymbol();
          break;
        default:
      }
    }


    boolean circleInTriangle = true;


    List<Symbol> lineSegments = t.getLineSegments();
    for(Symbol segment : lineSegments) {
      LineSegment lSegment = (LineSegment) segment;
      double x = (lSegment.getLineVertices()[0].getX() - lSegment.getLineVertices()[1].getX()) / 2;
      double y = (lSegment.getLineVertices()[0].getY() - lSegment.getLineVertices()[1].getY()) / 2;

      Point vertex = new Point(x,y);
      Double dist = vertex.euclidianDistance(c.getCenter());

      double radius = c.getRadius();
      double error = c.getRadius() * 0.10;

      boolean lineTangent = dist > (radius - error) && dist < (radius + error);

      if(!lineTangent) {
        circleInTriangle = false;
      }
    }
    System.out.println(Boolean.toString(circleInTriangle));

    boolean lineBisectsTriangle = false;
    int segmentCount = 0;
    for(Symbol segment : lineSegments) {
      LineSegment lSegment = (LineSegment) segment;
      if(l.shareOnePoint(lSegment)) {
        segmentCount++;
      }

      double x = (lSegment.getLineVertices()[0].getX() - lSegment.getLineVertices()[1].getX()) / 2;
      double y = (lSegment.getLineVertices()[0].getY() - lSegment.getLineVertices()[1].getY()) / 2;

      Point vertex = new Point(x,y);
      double error = l.getLineVertices()[0].euclidianDistance(l.getLineVertices()[1]) * 0.40;
      lineBisectsTriangle =
              (vertex.euclidianDistance(l.getLineVertices()[0])
              < vertex.euclidianDistance(l.getLineVertices()[0]) + error
              && vertex.euclidianDistance(l.getLineVertices()[0])
              > vertex.euclidianDistance(l.getLineVertices()[0]) - error)
              || (vertex.euclidianDistance(l.getLineVertices()[1])
              < vertex.euclidianDistance(l.getLineVertices()[1]) + error
              && vertex.euclidianDistance(l.getLineVertices()[1])
              > vertex.euclidianDistance(l.getLineVertices()[1]) - error);

      if(lineBisectsTriangle) {
        return circleInTriangle && lineBisectsTriangle && segmentCount == 2;
      }
    }

    return false;
  }
  /**
   * Returns the type of the given symbol.
   *
   * @return the SymbolType that corresponds to this Symbol's type.
   */
  @Override
  public SymbolType symbolType() {
    return SymbolType.Hallow;
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
    for (Symbol c : symbols) {
      temp.add(c.getSymbol());
    }
    Symbol output = new Hallow(temp);
    return output;
  }

  public List<Symbol> getSymbolList() {
    return symbols;
  }
}
