import org.junit.Before;
import org.junit.Test;

import Model.CartesianPoint;
import Model.Point;

import static org.junit.Assert.assertEquals;

/**
 * Junit test class for CartesianPoint.
 */
public class CartesianPointTest {
  CartesianPoint testPoint;
  CartesianPoint testPointOne;

  @Before
  public void setUp() {
    testPoint = new Point(1.5,2.8);
    testPointOne = new Point(0,5);
  }

  @Test
  public void testDistance() {
    assertEquals(testPoint.euclidianDistance(testPoint),0,0);
    assertEquals(testPoint.euclidianDistance(testPointOne),2.66,0.01);
  }

  @Test
  public void testGetX() {
    assertEquals(testPoint.getX(),1.5,0.1);
  }

  @Test
  public void testGetY() {
    assertEquals(testPoint.getY(),2.8,0.1);
  }

}