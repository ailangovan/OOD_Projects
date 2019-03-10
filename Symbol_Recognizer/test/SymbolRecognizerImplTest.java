import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Model.Circle;
import Model.LineSegment;
import Model.Snowman;
import Model.Symbol;
import Model.SymbolRecognizer;
import Model.SymbolRecognizerImpl;
import Model.Triangle;

/**
 * Test for the SymbolRecognizerImpl.
 */
public class SymbolRecognizerImplTest {
  SymbolRecognizer bank;

  /**
   * Generates an Empty SymbolRecognizerImpl.
   */
  @Before
  public void setUp() {
    bank = new SymbolRecognizerImpl();
  }

  /**
   * Test to make sure SymbolRecognizerImpl constructor is working as expected.
   */
  @Test
  public void testConstructor() {
    Assert.assertTrue(bank != null);
    List<Symbol> symbolList = bank.returnCurrentSymbols();
    Assert.assertEquals(0, symbolList.size());
  }

  /**
   * Test to make sure an exception is thrown if the Model is passed a non-basic Symbol.
   */
  @Test(expected = IllegalArgumentException.class)
  public void addCompositeSymbolFail() {
    LineSegment a = new LineSegment(10, 0, 0, 0);
    LineSegment b = new LineSegment(10, 0, 5, 8.66);
    LineSegment c = new LineSegment(0, 0, 5, 8.66);
    List<Symbol> triangleList = new ArrayList<>();
    triangleList.add(a);
    triangleList.add(b);
    triangleList.add(c);

    bank.addBasicSymbol(new Triangle(triangleList));
  }

  /**
   * Test to make sure the Model adds basic symbols.
   */
  @Test
  public void addBasicSymbol() {
    bank.addBasicSymbol(new LineSegment(10, 0, 0, 0));
    bank.addBasicSymbol(new Circle(0, 0, 10));
    List<Symbol> symbolList = bank.returnCurrentSymbols();
    Assert.assertTrue(symbolList.contains(new LineSegment(10, 0, 0, 0)));
    Assert.assertTrue(symbolList.contains(new Circle(0, 0, 10)));
  }

  /**
   * Test to show that the Model adds Triangles to the SymbolRecognizer.
   */
  @Test
  public void testTriangleInSymbolList() {
    LineSegment a = new LineSegment(10, 0, 0, 0);
    LineSegment b = new LineSegment(10, 0, 5, 8.66);
    LineSegment c = new LineSegment(0, 0, 5, 8.66);
    List<Symbol> triangleList = new ArrayList<>();
    triangleList.add(a);
    triangleList.add(b);
    triangleList.add(c);

    //Test consecutive addition.
    bank.addBasicSymbol(a);
    bank.addBasicSymbol(b);
    bank.addBasicSymbol(c);
    List<Symbol> symbolList = bank.returnCurrentSymbols();
    Assert.assertEquals(1, symbolList.size());
    Assert.assertEquals(symbolList.get(0), new Triangle(triangleList));
  }

  /**
   * Test to show that Triangles are created when there are three line segments in a row,
   * despite interruptions by circles.
   */
  @Test
  public void testTriangleWithCircleInSymbolList() {
    LineSegment a = new LineSegment(10, 0, 0, 0);
    LineSegment b = new LineSegment(10, 0, 5, 8.66);
    LineSegment c = new LineSegment(0, 0, 5, 8.66);
    Circle circle = new Circle(0, 0, 10);
    List<Symbol> triangleList = new ArrayList<>();
    triangleList.add(a);
    triangleList.add(b);
    triangleList.add(c);

    //Test interrupted addition.
    bank.addBasicSymbol(a);
    bank.addBasicSymbol(b);
    bank.addBasicSymbol(circle);
    bank.addBasicSymbol(c);
    List<Symbol> symbolList = bank.returnCurrentSymbols();
    Assert.assertEquals(2, symbolList.size());
    // Second element in list should be the triangle.
    Assert.assertEquals(symbolList.get(0), new Circle(0, 0, 10));
    Assert.assertEquals(symbolList.get(1), new Triangle(triangleList));
  }

  /**
   * Test showing Snowman symbols can be constructed, as well as showing
   * it ignores line segments in the process.
   */
  @Test
  public void testSnowmanInSymbolList() {
    Circle goodCircleOne = new Circle(0,0,0.5);
    Circle goodCircleTwo = new Circle(2,0,1.5);
    Circle goodCircleThree = new Circle(6,0,2.5);
    Circle circleOne = new Circle(0,0,3);
    Circle circleTwo = new Circle(3,4,2);
    Circle circleThree = new Circle(5,6,1);
    LineSegment c = new LineSegment(0, 0, 5, 8.66);
    bank.addBasicSymbol(goodCircleOne);
    bank.addBasicSymbol(goodCircleThree);
    bank.addBasicSymbol(c);
    bank.addBasicSymbol(goodCircleTwo);
    bank.addBasicSymbol(circleThree);
    bank.addBasicSymbol(circleTwo);
    bank.addBasicSymbol(circleOne);

    List<Symbol> snowOne = new LinkedList();
    snowOne.add(goodCircleOne.getSymbol());
    snowOne.add(goodCircleTwo.getSymbol());
    snowOne.add(goodCircleThree.getSymbol());
    Snowman output1 = new Snowman(snowOne);

    List<Symbol> snowTwo = new LinkedList();
    snowTwo.add(circleOne.getSymbol());
    snowTwo.add(circleTwo.getSymbol());
    snowTwo.add(circleThree.getSymbol());
    Snowman output2 = new Snowman(snowTwo);

    List<Symbol> symbolList = bank.returnCurrentSymbols();
    Assert.assertEquals(symbolList.get(1),output1);
    Assert.assertEquals(symbolList.get(2),output2);

  }

  /**
   * Shows that Triangles and Snowman symbols can be created despite being added with
   * interruptions.  Will finish a Snowman first and a Triangle second.
   */
  @Test
  public void testSubsequentSymbolAdditions() {
    LineSegment a = new LineSegment(10, 0, 0, 0);
    LineSegment b = new LineSegment(10, 0, 5, 8.66);
    LineSegment c = new LineSegment(0, 0, 5, 8.66);
    Circle circleOne = new Circle(0,0,3);
    Circle circleTwo = new Circle(3,4,2);
    Circle circleThree = new Circle(5,6,1);

    List<Symbol> triangleList = new ArrayList<>();
    triangleList.add(a);
    triangleList.add(b);
    triangleList.add(c);
    Triangle theTriangle = new Triangle(triangleList);

    List<Symbol> snow = new LinkedList();
    snow.add(circleOne.getSymbol());
    snow.add(circleTwo.getSymbol());
    snow.add(circleThree.getSymbol());
    Snowman theSnowMan = new Snowman(snow);

    bank.addBasicSymbol(a);
    bank.addBasicSymbol(circleOne);
    bank.addBasicSymbol(circleTwo);
    bank.addBasicSymbol(b);
    bank.addBasicSymbol(circleThree);
    bank.addBasicSymbol(c);
    List<Symbol> symbolList = bank.returnCurrentSymbols();

    Assert.assertEquals(symbolList.get(0),theSnowMan.getSymbol());
    Assert.assertEquals(symbolList.get(1),theTriangle.getSymbol());
  }

  /**
   * Test showing that if a Composite Symbol can't be constructed out of the three parts found,
   * the symbols are left as is in the list.  Then if a symbol is added so that the previous
   * symbols WILL form a composite symbol, it will form that symbol.
   */
  @Test
  public void testBadCompositeSymbols() {
    Circle goodCircleOne = new Circle(0,0,0.5);
    Circle goodCircleTwo = new Circle(2,0,1.5);
    Circle goodCircleThree = new Circle(6,0,2.5);
    Circle notCollinearOne = new Circle(0,5,0.5);
    LineSegment a = new LineSegment(10, 0, 0, 0);
    LineSegment b = new LineSegment(10, 0, 5, 8.66);
    LineSegment c = new LineSegment(0, 0, 5, 8.66);
    LineSegment f = new LineSegment(0,0,100,10);

    List<Symbol> triangleList = new ArrayList<>();
    triangleList.add(a);
    triangleList.add(b);
    triangleList.add(c);
    Triangle theTriangle = new Triangle(triangleList);

    List<Symbol> snow = new LinkedList();
    snow.add(goodCircleOne.getSymbol());
    snow.add(goodCircleTwo.getSymbol());
    snow.add(goodCircleThree.getSymbol());
    Snowman theSnowMan = new Snowman(snow);

    bank.addBasicSymbol(notCollinearOne);
    bank.addBasicSymbol(f);
    bank.addBasicSymbol(a);
    bank.addBasicSymbol(goodCircleTwo);
    bank.addBasicSymbol(b);
    bank.addBasicSymbol(goodCircleThree);
    bank.addBasicSymbol(goodCircleOne);
    bank.addBasicSymbol(c);
    List<Symbol> symbolList = bank.returnCurrentSymbols();
    Assert.assertEquals(symbolList.get(0),notCollinearOne.getSymbol());
    Assert.assertEquals(symbolList.get(1),f.getSymbol());
    Assert.assertEquals(symbolList.get(2),theSnowMan.getSymbol());
    Assert.assertEquals(symbolList.get(3),theTriangle.getSymbol());

  }

  /**
   * Test showing returnCurrentSymbols() functions as expected.
   */
  @Test
  public void returnCurrentSymbols() {
    Circle circle = new Circle(0, 0, 10);
    LineSegment a = new LineSegment(10, 0, 0, 0);
    List<Symbol> symbolList = bank.returnCurrentSymbols();
    Assert.assertEquals(0, symbolList.size());

    bank.addBasicSymbol(circle);
    bank.addBasicSymbol(a);
    symbolList = bank.returnCurrentSymbols();
    Assert.assertEquals(2, symbolList.size());
    Assert.assertEquals(symbolList.get(0), new Circle(0, 0, 10));
    Assert.assertEquals(symbolList.get(1), new LineSegment(10, 0, 0, 0));
  }
}