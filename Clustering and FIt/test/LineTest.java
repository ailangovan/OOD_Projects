import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * This is the test for the Line class.
 * Created by Oz on 2/28/2017.
 */
public class LineTest {

  Line testLine;

  /**
   * Setup for test.
   */
  @Before
  public void setUp() {
    testLine = new Line(1, 1);
  }

  /**
   * Test for slope getter.
   */
  @Test
  public void getSlope() {

    assertEquals(1, testLine.getSlope(), 0.01);
  }

  /**
   * Test for the intercept getter.
   */
  @Test
  public void getIntercept() {
    assertEquals(1, testLine.getIntercept(), 0.01);
  }

}