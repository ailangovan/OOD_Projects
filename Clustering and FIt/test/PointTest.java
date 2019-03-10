import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * This is the test class for the Point.
 * Created by Oz on 2/28/2017.
 */
public class PointTest {

  Point testPoint1 = new Point(4, 4);
  Point testPoint2 = new Point(1, 1);

  /**
   * Tests the xCoor function.
   */
  @Test
  public void getxCoor() {

    assertEquals(4, testPoint1.getxCoor(), 0.01);
    assertEquals(1, testPoint2.getxCoor(), 0.01);
  }

  /**
   * Tests the yCoor getter.
   */
  @Test
  public void getyCoor() {

    assertEquals(4, testPoint1.getyCoor(), 0.01);
    assertEquals(1, testPoint2.getyCoor(), 0.01);
  }

  /**
   * Tests the distance function.
   */
  @Test
  public void distance() {

    double distance = testPoint1.distance(testPoint2);

    assertEquals(4.24, distance, 0.01);

  }

}