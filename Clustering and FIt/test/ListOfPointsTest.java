import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;
import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertEquals;


/**
 * This is the test class for the ListOfPoints class.
 * Created by Oz on 2/28/2017.
 */
public class ListOfPointsTest {

  ListOfPoints testLop;

  /**
   * Setup for the ListofPoints testing.
   */
  @Before
  public void setUp() {
    testLop = new ListOfPoints();
  }

  @Test
  public void addPoint() {
    //Check that the initial constructor is an empty list.
    int size = testLop.getPointArrayList().size();
    assertEquals(0, size);

    testLop.addPoint(0, 0);
    assertEquals(0, testLop.getPointArrayList().get(0).getxCoor(), 0.01);
    assertEquals(0, testLop.getPointArrayList().get(0).getyCoor(), 0.01);

    testLop.addPoint(1, 1);
    assertEquals(1, testLop.getPointArrayList().get(1).getxCoor(), 0.01);
    assertEquals(1, testLop.getPointArrayList().get(1).getyCoor(), 0.01);
  }

  @Test
  public void getPointArrayList() {

    ArrayList<Point> gottenList = testLop.getPointArrayList();
    int size = gottenList.size();
    assertEquals(0, size);

    testLop.addPoint(0, 0);
    testLop.addPoint(1, 1);
    gottenList = testLop.getPointArrayList();

    assertEquals(0, gottenList.get(0).getxCoor(), 0.01);
    assertEquals(0, gottenList.get(0).getyCoor(), 0.01);
    assertEquals(1, gottenList.get(1).getxCoor(), 0.01);
    assertEquals(1, gottenList.get(1).getyCoor(), 0.01);

  }

  @Test
  public void fitLine() {
    for (int i = 0; i < 100; i++) {
      testLop.addPoint(i, i);
    }
    Line testFitLine = testLop.fitLine();
    assertEquals(testFitLine.getIntercept(), 0, 0.01);
    assertEquals(testFitLine.getSlope(), 1, 0.01);

    testLop = new ListOfPoints();
    testLop.addPoint(10, 4);
    testLop.addPoint(10, 2);
    testLop.addPoint(40, 75);
    testLop.addPoint(32, 100);

    testFitLine = testLop.fitLine();
    assertEquals(testFitLine.getIntercept(), -22.87, 0.01);
    assertEquals(testFitLine.getSlope(), 2.96, 0.01);
  }

  /**
   * Tests for the kMeans cluster
   *
   * @throws IllegalArgumentException if the k passed in is out of bounds.
   */
  @Test
  public void kmeans() throws IllegalArgumentException, TimeoutException, FileNotFoundException {

    Scanner sc = new Scanner(new FileInputStream("clusterdata-2.txt"));

    while (sc.hasNext()) {
      double x = sc.nextDouble();
      double y = sc.nextDouble();

      testLop.addPoint(x, y);
    }

    //Adding a point that is really close to point in list.
    testLop.addPoint(383, -397);

    //These two points should be in same cluster.

    ArrayList<Integer> clusters = testLop.kmeans(2);

    int firstCluster = clusters.get(0);
    int secondCluster = clusters.get(testLop.getPointArrayList().size() - 1);

    assertEquals(firstCluster, secondCluster);
  }

  /**
   * Tests an invalid argument for kmeans.
   * @throws TimeoutException    if no kmeans can be thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidkMeans() throws TimeoutException {
    testLop.kmeans(0);
  }

  /**
   * Tests an invalid argument for kmeans.
   * @throws TimeoutException    if no kmeans can be thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidkMeans2() throws TimeoutException {
    testLop.kmeans(-1);
  }

  /**
   * Tests an invalid argument for kmeans.
   * @throws TimeoutException    if no kmeans can be thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidkMeans3() throws TimeoutException {
    testLop.kmeans(10000);
  }


  /**
   * Tests that the function properly timesout.
   * @throws IllegalArgumentException    if invalid arguement is passed for k.
   * @throws TimeoutException    if the functions runs too long.
   */
  @Test(expected = TimeoutException.class)
  public void testTimeout() throws IllegalArgumentException, TimeoutException {
    testLop = new ListOfPoints();
    for (int i = 0; i < 6000; i++) {
      testLop.addPoint(3, 3);
    }
    for (int i = 0; i < 6000; i++) {
      testLop.addPoint(5000, 5000);
    }

    ArrayList<Integer> clusters = testLop.kmeans(3);
  }

}