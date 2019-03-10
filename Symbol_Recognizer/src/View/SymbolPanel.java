package View;

import java.awt.*;
import java.util.List;

import javax.swing.*;

import Controller.ISymbolController;
import Model.Circle;
import Model.Hallow;
import Model.LineSegment;
import Model.Point;
import Model.Rectangle;
import Model.Snowman;
import Model.Symbol;
import Model.Triangle;

/**
 * This class represents the Panel that is being drawn of the symbols and user input in the
 * Symbol Recognizer.  It draws the user input last so that it supercedes all previously drawn
 * Symbols.  It places labels on all fully constructed symbols.
 */
public class SymbolPanel extends JPanel {
  private ISymbolController controller;

  /**
   * Constructs a SymbolPanel object with the following parameter:
   *
   * @param controller is the Controller for the SymbolRecognizer.
   */
  public SymbolPanel(ISymbolController controller) {
    super();
    setBackground(Color.BLUE);
    this.controller = controller;
    setBackground(Color.WHITE);
  }

  /**
   * Paints the components in the panel.  It first draws all the Symbols stored in the
   * Model, and then it constructs all of the current user input, if there is any.
   *
   * @param g is the Graphics being drawn.
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    List<Symbol> symbols = controller.getSymbols();
    List<Point> userInput = controller.getUserInput();

    // first draw all symbols
    for (int i = 0; i < symbols.size(); i++) {
      Symbol s = symbols.get(i);
      switch (s.symbolType()) {
        case Hallow:
          drawHallow(s, g);
          drawSymbolLabel(s, g);
          break;
        case Rectangle:
          drawRectangle(s, g);
          drawSymbolLabel(s, g);
          break;
        case LineSegment:
          drawLineSeg(s, g);
          drawSymbolLabel(s, g);
          break;
        case Circle:
          drawCircle(s, g);
          drawSymbolLabel(s, g);
          break;
        case Triangle:
          drawTriangle(s, g);
          drawSymbolLabel(s, g);
          break;
        case EquilateralTriangle:
          drawTriangle(s, g);
          drawSymbolLabel(s, g);
          break;
        default:
          drawSnowman(s, g);
          drawSymbolLabel(s, g);
      }
    }

    // Draw User Input last as it will draw on top of everything else.
    for (int i = 0; i < userInput.size(); i++) {
      if (i == 0) {
        Point onlyPoint = userInput.get(0);
        g.setColor(Color.BLACK);
        g.drawLine((int) onlyPoint.getX(), (int) onlyPoint.getY(),
                (int) onlyPoint.getX(), (int) onlyPoint.getY());
      } else {
        Point p1 = userInput.get(i - 1);
        Point p2 = userInput.get(i);
        g.setColor(Color.BLACK);
        g.drawLine((int) p1.getX(), (int) p1.getY(),
                (int) p2.getX(), (int) p2.getY());
      }
    }
  }

  private static void drawHallow(Symbol s, Graphics g){
    Hallow h = (Hallow) s;
    List<Symbol> toDraw = h.getSymbolList();
    for(Symbol draw : toDraw) {
      switch (draw.symbolType()){
        case Circle:
          drawCircle(draw, g);
          break;
        case Triangle:
          drawTriangle(draw, g);
          break;
        case EquilateralTriangle:
          drawTriangle(draw, g);
          break;
        case LineSegment:
          drawLineSeg(draw, g);
          break;
        default:
          break;
      }
    }
  }

  /**
   * Draws a LineSegment on the Panel.
   *
   * @param s is the LineSegment to be drawn.
   * @param g is the graphic corresponding to the LineSegment.
   */
  private static void drawLineSeg(Symbol s, Graphics g) {
    g.setColor(Color.BLUE);
    LineSegment lineSegment = (LineSegment) s;
    Point[] points = lineSegment.getLineVertices();
    g.drawLine((int) points[0].getX(), (int) points[0].getY(),
            (int) points[1].getX(), (int) points[1].getY());
  }

  /**
   * Draws a Circle on the Panel.
   *
   * @param g is the graphic corresponding to the Circle.
   */
  private static void drawCircle(Symbol s, Graphics g) {
    g.setColor(Color.BLUE);
    Circle c = (Circle) s;
    Point center = c.getCenter();
    int r = (int) c.getRadius();
    g.drawOval((int) center.getX() - r, (int) center.getY() - r,
            r * 2, r * 2);
  }

  /**
   * Draws a Triangle onto the Panel.  It does this by referring to the corresponding
   * draw method for the LineSegments that construct this Triangle.
   *
   * @param s is the Triangle to be drawn.
   * @param g is the graphic corresponding to the Triangle.
   */
  private static void drawTriangle(Symbol s, Graphics g) {
    g.setColor(Color.RED);
    Triangle t = (Triangle) s;
    List<Symbol> lineSegments = t.getLineSegments();
    for (Symbol ls : lineSegments) {
      drawLineSeg(ls, g);
    }
  }

  private static void drawRectangle(Symbol s, Graphics g) {
    g.setColor(Color.yellow);
    Rectangle r = (Rectangle) s;
    List<Symbol> lineSegments = r.getLineSegments();
    for (Symbol ls : lineSegments) {
      drawLineSeg(ls, g);
    }
  }

  /**
   * Draws a Snowman onto the Panel.  It does this by referring to the corresponding
   * draw method for the Circles that construct this Triangle.
   *
   * @param s is the Snowman to be drawn.
   * @param g is the graphic corresponding to the Snowman.
   */
  private static void drawSnowman(Symbol s, Graphics g) {
    g.setColor(Color.RED);
    Snowman sm = (Snowman) s;
    List<Symbol> circles = sm.getCircles();
    for (Symbol c : circles) {
      drawCircle(c, g);
    }
  }

  /**
   * Applies the appropriate labeling to the Symbol given.
   *
   * @param s is the symbol needing a label.
   * @param g is the graphic of the symbol.
   */
  private static void drawSymbolLabel(Symbol s, Graphics g) {
    g.setColor(Color.GREEN);
    switch (s.symbolType()) {
      case Hallow:
        Hallow h = (Hallow) s;
        List<Symbol> toDraw = h.getSymbolList();
        for(Symbol draw : toDraw) {
          switch (draw.symbolType()){
            case Circle:
              Circle c = (Circle) draw;
              Point center = c.getCenter();
              g.drawString("Hallow", (int) center.getX(), (int) center.getY());
              break;
            default:
              break;
          }
        }
        break;
      case Rectangle:
        Rectangle r = (Rectangle) s;
        LineSegment rctls = (LineSegment) r.getLineSegments().get(0);
        Point rp = rctls.getLineVertices()[0];
        g.drawString("Rectangle", (int) rp.getX(), (int)rp.getY());
        break;
      case LineSegment:
        LineSegment ls = (LineSegment) s;
        Point[] points = ls.getLineVertices();
        g.drawString("LineSegment", (int) points[0].getX(), (int) points[0].getY());
        break;
      case Circle:
        Circle c = (Circle) s;
        Point center = c.getCenter();
        g.drawString("Circle", (int) center.getX(), (int) center.getY());
        break;
      case Triangle:
        Triangle t = (Triangle) s;
        LineSegment tls = (LineSegment) t.getLineSegments().get(0);
        Point tp = tls.getLineVertices()[0];
        String tString = t.symbolType().toString();
        g.drawString(tString, (int) tp.getX(), (int) tp.getY());
        break;
      case EquilateralTriangle:
        Triangle et = (Triangle) s;
        LineSegment etls = (LineSegment) et.getLineSegments().get(0);
        Point etp = etls.getLineVertices()[0];
        String etString = et.symbolType().toString();
        g.drawString(etString, (int) etp.getX(), (int) etp.getY());
        break;
      default:
        Snowman sw = (Snowman) s;
        Circle swc = (Circle) sw.getCircles().get(0);
        Point swp = swc.getCenter();
        g.drawString("Snowman", (int) swp.getX(), (int) swp.getY());
        break;
    }
  }
}