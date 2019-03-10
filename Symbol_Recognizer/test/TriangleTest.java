import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import Model.Circle;
import Model.LineSegment;
import Model.Symbol;
import Model.SymbolType;
import Model.Triangle;

/**
 * This is a test for the Triangle class.
 * Created by Oz on 4/7/2017.
 */
public class TriangleTest {
  Triangle eqTri;
  Triangle tri;
  Triangle semiConnectedTriangle;

  /**
   * Setups variables for testing.
   */
  @Before
  public void setUp() {
    // Setup Equilateral Triangle
    LineSegment a = new LineSegment(10, 0, 0, 0);
    LineSegment b = new LineSegment(10, 0, 5, 8.66);
    LineSegment c = new LineSegment(0, 0, 5, 8.66);
    List<Symbol> triList = new ArrayList<>();
    triList.add(a);
    triList.add(b);
    triList.add(c);

    eqTri = new Triangle(triList);

    // Setup regular triangle
    a = new LineSegment(-5, 0, 5, 0);
    b = new LineSegment(-5, 0, 1, 10);
    c = new LineSegment(5, 0, 1, 10);
    triList = new ArrayList<>();
    triList.add(a);
    triList.add(b);
    triList.add(c);
    tri = new Triangle(triList);

    // Setup triangle that isn't perfectly connected.
    a = new LineSegment(-4.7, 0.01, 5, 0);
    b = new LineSegment(-5.1, -0.2, 1, 10);
    c = new LineSegment(5, 0, 0.8, 10);
    triList = new ArrayList<>();
    triList.add(a);
    triList.add(b);
    triList.add(c);
    semiConnectedTriangle = new Triangle(triList);

  }

  /**
   * Test constructor passed a list of symbols of invalid size throws
   * IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidNumInputToConstructor() {
    List<Symbol> invalidList = new ArrayList<>();
    invalidList.add(new LineSegment(0, 0, 0, 0));

    Triangle invalid = new Triangle(invalidList);
  }

  /**
   * Tests constructor passed List<Symbol></Symbol> not containing all LineSegments throws
   * IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidInputToConstructor() {
    List<Symbol> invalidList = new ArrayList<>();
    invalidList.add(new Circle(0, 0, 10));
    invalidList.add(new Circle(1, 0, 10));
    invalidList.add(new Circle(20, 10, 40));

    Triangle invalid = new Triangle(invalidList);
  }

  /**
   * Tests constructor passed LineSegments that do not form a triangle throws
   * IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidTriangleToConstructor() {
    List<Symbol> invalidList = new ArrayList<>();
    invalidList.add(new LineSegment(0.2, 0.1, 1.0, 10.0));
    invalidList.add(new LineSegment(40, 20, 30, 90));
    invalidList.add(new LineSegment(200, 100, 80, 1000));

    Triangle invalid = new Triangle(invalidList);
  }

  /**
   * Tests the triangle returns valid Symboltype.
   */
  @Test
  public void symbolType() {
    Assert.assertEquals(SymbolType.EquilateralTriangle, eqTri.symbolType());
    Assert.assertEquals(SymbolType.Triangle, tri.symbolType());
    Assert.assertEquals(SymbolType.Triangle, semiConnectedTriangle.symbolType());
  }

  /**
   * This tests that the Triangle getSymbol returns a copy of the triangle and
   * also checks that the overridden equals method is functioning properly.
   */
  @Test
  public void getSymbolAndEquals() {
    SymbolType type = eqTri.symbolType();
    Symbol copy = eqTri.getSymbol();

    LineSegment a = new LineSegment(10, 0, 0, 0);
    LineSegment b = new LineSegment(10, 0, 5, 8.66);
    LineSegment c = new LineSegment(0, 0, 5, 8.66);
    List<Symbol> triList = new ArrayList<>();
    triList.add(a);
    triList.add(b);
    triList.add(c);
    Triangle eqTri2 = new Triangle(triList);

    Assert.assertEquals(SymbolType.EquilateralTriangle, type);
    Assert.assertTrue(copy instanceof Triangle);
    Assert.assertEquals(copy, eqTri2);
  }

}