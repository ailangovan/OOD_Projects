import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import Model.LineSegment;
import Model.Point;
import Model.SymbolType;

/**
 * This is the test class for the LineSegment Class.
 * Created by Oz on 4/7/2017.
 */
public class LineSegmentTest {

  LineSegment testSegment;
  LineSegment testSegment2;
  LineSegment testSegment3;

  /**
   * Setup variables and segments for testing.
   */
  @Before
  public void setUp() {
    testSegment = new LineSegment(1, 1, 3, 3);
    testSegment2 = new LineSegment(1, 0.95, 4, 4);
    testSegment3 = new LineSegment(1, 1, 3, 3);
  }

  @Test
  public void symbolType() {
    SymbolType type = testSegment.symbolType();

    Assert.assertEquals(SymbolType.LineSegment, type);

  }

  @Test
  public void getSymbolAndEquals() {


    Assert.assertTrue(testSegment3.equals(testSegment.getSymbol()));
    Assert.assertFalse(testSegment3.equals(testSegment2.getSymbol()));
  }

  @Test
  public void getLineVertices() {
    Point[] points = testSegment.getLineVertices();
    Assert.assertTrue(points[0].equals(new Point(1, 1)));
    Assert.assertTrue(points[1].equals(new Point(3, 3)));
  }

  @Test
  public void shareOnePoint() {
    LineSegment testSegment4 = new LineSegment(41, 1, 124, 22);
    // One shared point.
    Assert.assertTrue(testSegment.shareOnePoint(testSegment2));
    // Two shared points.
    Assert.assertFalse(testSegment.shareOnePoint(testSegment3));
    // No shared points.
    Assert.assertFalse(testSegment.shareOnePoint(testSegment4));

  }

  @Test(expected = IllegalArgumentException.class)
  public void sharedPointNoPointsShared() {
    Point shared = testSegment.sharedPoint(testSegment3);
  }

  @Test
  public void sharedPoint() {
    Point shared = testSegment.sharedPoint(testSegment2);
    Assert.assertEquals(1, shared.getX(), 0.01);
    Assert.assertEquals(0.975, shared.getY(), 0.01);
  }

  @Test
  public void getSlope() {

    double slope = testSegment.getSlope();
    double slope2 = testSegment2.getSlope();
    Assert.assertEquals(1.0, slope, 0.01);
    Assert.assertEquals(1.0167, slope2, 0.01);
  }

  @Test
  public void getDegree() {

    double degree = testSegment.getDegree(testSegment);
    Assert.assertEquals(0, degree, 0.01);


    testSegment = new LineSegment(0, 0, 10, 10);
    testSegment2 = new LineSegment(0, 0, 10, 0);
    degree = testSegment.getDegree(testSegment2);

    Assert.assertEquals(45, degree, 0.01);
  }

}