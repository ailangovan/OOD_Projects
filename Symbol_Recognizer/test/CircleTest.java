import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import Model.Circle;
import Model.Point;
import Model.Symbol;
import Model.SymbolType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Junit test class for Circles.
 */
public class CircleTest {
  Circle testCircle;
  Circle testCircleDifferent;
  Circle testCircleSame;

  /**
   * Constructs one base test Circle, a circle that's different, and a circle that's the same
   * as the base.
   */
  @Before
  public void setUp() {
    testCircle = new Circle(0,0,1);
    testCircleDifferent = new Circle(1,1,5);
    testCircleSame = new Circle(0,0,1);
  }

  /**
   * Test to ensure correct SymbolType is returned for the method symbolType().
   */
  @Test
  public void symbolType() {
    assertEquals(testCircle.symbolType(), SymbolType.Circle);
  }

  @Test
  public void getSymbol() {
    Symbol temp = testCircle.getSymbol();
    assertEquals(temp,testCircle);
    assertTrue(testCircle.equals(testCircleSame));
    assertFalse(testCircle.equals(testCircleDifferent));
  }

  /**
   * Test for getCenter().
   */
  @Test
  public void getCenter() {
    assertTrue(testCircle.getCenter().equals(new Point(0,0)));
  }

  /**
   * Test for getRadius().
   */
  @Test
  public void getRadius() {
    assertEquals(testCircle.getRadius(),1,1);

  }

  /**
   * Test ensuring compareTo is just comparing the radii of different circles.
   */
  @Test
  public void compareTo() {
    Circle temp = new Circle(0,0,0.5);
    assertEquals(testCircle.compareTo(testCircleDifferent),-1);
    assertEquals(testCircle.compareTo(testCircleSame),0);
    assertEquals(testCircle.compareTo(temp),1);
  }

  /**
   * Test for compareTo() showing Collections.sort using compareTo to
   * sort circles based on radius.
   */
  @Test
  public void testSortCircle() {
    Circle one = new Circle(0,0,5);
    Circle two = new Circle(0,0,15);
    Circle three = new Circle(0,0,25);
    List<Circle> temp = new LinkedList();
    temp.add(three);
    temp.add(two);
    temp.add(one);

    for (int i = 0; i < 3; i++) {
      assertEquals(temp.get(i).getRadius(),25 - (i * 10),0);
    }

    Collections.sort(temp);

    for (int i = 0; i < 3; i++) {
      assertEquals(temp.get(i).getRadius(),5 + (i * 10),0);
    }
  }
}