package View;

import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.*;

import Controller.ISymbolController;
import Model.*;
import Model.Point;

/**
 * Test class used for testing the Controller.  All methods log messages instead of their
 * normal responses.
 */
public class MockView extends JFrame implements ISymbolView {
  private JPanel drawPanel;
  protected String testOutput;
  Controller.ISymbolController controller;

  public MockView(Controller.ISymbolController controller) {
    super();
    setTitle("SymbolRecognizer");
    setSize(500, 500);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.controller = controller;
    drawPanel = new SymbolPanel();
    //create a JScrollPane and add the drawPanel to it
    JScrollPane scrolls = new JScrollPane(drawPanel);
    //add the JScrollPane to wherever you would have added the drawPanel
    this.add(scrolls);
    this.add(drawPanel);
    testOutput = "";
  }

  /**
   * Gets the testOutput String.
   */
  public String getTestOutput() {
    return testOutput;
  }

  /**
   * Sets the MouseListener for the View.  This determines when a user presses and releases
   * the left mouse button.
   */
  @Override
  public void setMouseListener(MouseListener listener) {
    drawPanel.addMouseListener(listener);
  }

  /**
   * Sets the MouseMotionListener for the View.  This determines when a user drags the mouse
   * while holding the left mouse button.
   */
  @Override
  public void setMouseMotionListener(MouseMotionListener motionListener) {
    drawPanel.addMouseMotionListener(motionListener);
  }

  /**
   * Redraws the View with all of the symbols that are in the current SymbolRecognizer.
   */
  @Override
  public void redrawScene() {
    testOutput += "redrawScene Called Properly.\n";
    java.util.List<Model.Point> userInput = controller.getUserInput();
    java.util.List<Symbol> symbols = controller.getSymbols();



    for (int i = 0; i < symbols.size(); i++) {
      Symbol s = symbols.get(i);
      switch (s.symbolType()) {
        case LineSegment:
          LineSegment lineSegment = (LineSegment) s;
          Point[] points = lineSegment.getLineVertices();
          testOutput += String.format("LineSegment added at (%d,%d), (%d,%d).\n",
                  (int) points[0].getX(), (int) points[0].getY(),
                  (int) points[1].getX(), (int) points[1].getY());
          testOutput += "Label Added: Line Segment.\n";
          break;
        case Circle:
          Circle c = (Circle) s;
          Point center = c.getCenter();
          int r = (int) c.getRadius();
          testOutput += String.format("Circle added at (%d,%d) with radius, %d.\n",
                  (int) center.getX(), (int) center.getY(),
                  r);
          testOutput += "Label Added: Circle.\n";
          break;
        case Triangle:
          testOutput += "Added: Triangle.\n";
          testOutput += "Label Added: Triangle.\n";
          break;
        case EquilateralTriangle:
          testOutput += "Added: Equilateral Triangle.\n";
          testOutput += "Label Added: Equilateral Triangle.\n";
          break;
        default:
          testOutput += "Added: Snowman.\n";
          testOutput += "Label Added: Snowman.\n";

      }
    }

    testOutput += "This is a list of all the User Input Points:\n";
    for (int i = 0; i < userInput.size(); i++) {
      if (i == 0) {
        Model.Point onlyPoint = userInput.get(0);
        testOutput += String.format("(%d,%d)",(int) onlyPoint.getX(), (int) onlyPoint.getY());
      } else {
        Model.Point p1 = userInput.get(i - 1);
        Model.Point p2 = userInput.get(i);
        testOutput += String.format("(%d,%d)",(int) p2.getX(), (int) p2.getY());
      }
    }
    testOutput += "\n";
  }

  class SymbolPanel extends JPanel {
    private ISymbolController controller;
    /**
     * Constructs a SymbolPanel object.
     */
    public SymbolPanel() {
      super();
      setBackground(Color.BLUE);
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
      java.util.List<Symbol> symbols = controller.getSymbols();
      java.util.List<Model.Point> userInput = controller.getUserInput();

      // first draw all symbols
      for (int i = 0; i < symbols.size(); i++) {
        Symbol s = symbols.get(i);
        switch (s.symbolType()) {
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
          default:
            drawSnowman(s, g);
            drawSymbolLabel(s, g);
        }
      }

      // Draw User Input last as it will draw on top of everything else.
      testOutput += "This is a list of all the User Input Points:\n";
      for (int i = 0; i < userInput.size(); i++) {
        if (i == 0) {
          Model.Point onlyPoint = userInput.get(0);
          g.setColor(Color.BLACK);
          g.drawLine((int) onlyPoint.getX(), (int) onlyPoint.getY(),
                  (int) onlyPoint.getX(), (int) onlyPoint.getY());
          testOutput += String.format("(%d,%d)",(int) onlyPoint.getX(), (int) onlyPoint.getY());
        } else {
          Model.Point p1 = userInput.get(i - 1);
          Model.Point p2 = userInput.get(i);
          g.setColor(Color.BLACK);
          g.drawLine((int) p1.getX(), (int) p1.getY(),
                  (int) p2.getX(), (int) p2.getY());
          testOutput += String.format("(%d,%d)",(int) p2.getX(), (int) p2.getY());
        }
      }
    }

    public String getPointString() {
      String testOutput = "";

      return testOutput;
    }

    /**
     * Draws a LineSegment on the Panel.
     *
     * @param s is the LineSegment to be drawn.
     * @param g is the graphic corresponding to the LineSegment.
     */
    private void drawLineSeg(Symbol s, Graphics g) {
      g.setColor(Color.BLUE);
      LineSegment lineSegment = (LineSegment) s;
      Model.Point[] points = lineSegment.getLineVertices();
      g.drawLine((int) points[0].getX(), (int) points[0].getY(),
              (int) points[1].getX(), (int) points[1].getY());
      testOutput += String.format("LineSegment added at (%d,%d), (%d,%d).\n",
              (int) points[0].getX(), (int) points[0].getY(),
              (int) points[1].getX(), (int) points[1].getY());
    }

    /**
     * Draws a Circle on the Panel.
     *
     * @param g is the graphic corresponding to the Circle.
     */
    private void drawCircle(Symbol s, Graphics g) {
      g.setColor(Color.BLUE);
      Circle c = (Circle) s;
      Model.Point center = c.getCenter();
      int r = (int) c.getRadius();
      g.drawOval((int) center.getX() - r, (int) center.getY() - r,
              r * 2, r * 2);
      testOutput += String.format("Circle added at (%d,%d) with radius, %d.\n",
              (int) center.getX(), (int) center.getY(),
              r);
    }

    /**
     * Draws a Triangle onto the Panel.  It does this by referring to the corresponding
     * draw method for the LineSegments that construct this Triangle.
     *
     * @param s is the Triangle to be drawn.
     * @param g is the graphic corresponding to the Triangle.
     */
    private void drawTriangle(Symbol s, Graphics g) {
      g.setColor(Color.RED);
      Triangle t = (Triangle) s;
      java.util.List<Symbol> lineSegments = t.getLineSegments();
      testOutput += "Triangle added with the following LineSegments:\n";
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
    private void drawSnowman(Symbol s, Graphics g) {
      g.setColor(Color.RED);
      Snowman sm = (Snowman) s;
      java.util.List<Symbol> circles = sm.getCircles();
      testOutput += "Snowman added with the following Circles:\n";
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
    private void drawSymbolLabel(Symbol s, Graphics g) {
      g.setColor(Color.GREEN);
      switch (s.symbolType()) {
        case LineSegment:
          LineSegment ls = (LineSegment) s;
          Model.Point[] points = ls.getLineVertices();
          g.drawString("LineSegment", (int) points[0].getX(), (int) points[0].getY());
          testOutput += "Label Added: LineSegment.\n";
          break;
        case Circle:
          Circle c = (Circle) s;
          Model.Point center = c.getCenter();
          g.drawString("Circle", (int) center.getX(), (int) center.getY());
          testOutput += "Label Added: Circle.\n";
          break;
        case Triangle:
          Triangle t = (Triangle) s;
          LineSegment tls = (LineSegment) t.getLineSegments().get(0);
          Model.Point tp = tls.getLineVertices()[0];
          g.drawString("Triangle", (int) tp.getX(), (int) tp.getY());
          testOutput += "Label Added: Triangle.\n";
          break;
        case EquilateralTriangle:
          Triangle et = (Triangle) s;
          LineSegment etls = (LineSegment) et.getLineSegments().get(0);
          Model.Point etp = etls.getLineVertices()[0];
          g.drawString("Equilateral Triangle", (int) etp.getX(), (int) etp.getY());
          testOutput += "Label Added: Equilateral Triangle.\n";
          break;
        default:
          Snowman sw = (Snowman) s;
          Circle swc = (Circle) sw.getCircles().get(0);
          Model.Point swp = swc.getCenter();
          g.drawString("Snowman", (int) swp.getX(), (int) swp.getY());
          testOutput += "Label Added: Snowman.\n";
          break;
      }
    }
  }
}
