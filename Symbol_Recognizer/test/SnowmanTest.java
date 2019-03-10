import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import Model.Circle;
import Model.Snowman;
import Model.Symbol;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Junit test class for Snowman.
 */
public class SnowmanTest {
  Circle goodCircleOne = new Circle(0,0,0.5);
  Circle goodCircleTwo = new Circle(2,0,1.5);
  Circle goodCircleThree = new Circle(6,0,2.5);
  Circle notCollinearOne = new Circle(0,5,0.5);
  Circle notCollinearTwo = new Circle(2,5,1.5);
  Circle notCollinearThree = new Circle(6,7,2.5);
  Circle badRadiusOne = new Circle(0,0,50);
  Circle badRadiusTwo = new Circle(2,0,50);
  Circle badRadiusThree = new Circle(6,0,100);
  Circle circleOne = new Circle(0,0,3);
  Circle circleTwo = new Circle(3,4,2);
  Circle circleThree = new Circle(5,6,1);
  Snowman testSnowman;
  Snowman testSnowmanOne;

  /**
   * Sets up arguments for snowman and initializes several valid snowman.
   */
  @Before
  public void setUp() {
    List<Symbol> temp = new LinkedList();
    temp.add(goodCircleTwo);
    temp.add(goodCircleOne);
    temp.add(goodCircleThree);
    testSnowman = new Snowman(temp);

    List<Symbol> tempOne = new LinkedList();
    tempOne.add(circleOne);
    tempOne.add(circleTwo);
    tempOne.add(circleThree);
    testSnowmanOne = new Snowman(tempOne);
  }

  /**
   * Three tests to show if outer, middle, or other outer circles have radii too big.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testBadRadiusOne() {
    List<Symbol> temp = new LinkedList();
    temp.add(badRadiusOne);
    temp.add(goodCircleTwo);
    temp.add(goodCircleThree);
    Snowman badSnowman = new Snowman(temp);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testBadRadiusTwo() {
    List<Symbol> temp = new LinkedList();
    temp.add(goodCircleOne);
    temp.add(badRadiusTwo);
    temp.add(goodCircleThree);
    Snowman badSnowman = new Snowman(temp);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testBadRadiusThree() {
    List<Symbol> temp = new LinkedList();
    temp.add(goodCircleOne);
    temp.add(goodCircleTwo);
    temp.add(badRadiusThree);
    Snowman badSnowman = new Snowman(temp);
  }

  /**
   * Three tests showing non-collinear/non-adjacent circles.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testNotCollinearOne() {
    List<Symbol> temp = new LinkedList();
    temp.add(notCollinearOne);
    temp.add(goodCircleTwo);
    temp.add(goodCircleThree);
    Snowman badSnowman = new Snowman(temp);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNotCollinearTwo() {
    List<Symbol> temp = new LinkedList();
    temp.add(goodCircleOne);
    temp.add(notCollinearTwo);
    temp.add(goodCircleThree);
    Snowman badSnowman = new Snowman(temp);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNotCollinearThree() {
    List<Symbol> temp = new LinkedList();
    temp.add(goodCircleOne);
    temp.add(goodCircleTwo);
    temp.add(notCollinearThree);
    Snowman badSnowman = new Snowman(temp);
  }

  /**
   * Test for symbolType().
   */
  @Test
  public void symbolType() {
    List<Symbol> temp = new LinkedList<>();
    temp.add(goodCircleOne);
    temp.add(goodCircleTwo);
    temp.add(goodCircleThree);
    Snowman tempSnow = new Snowman(temp);
    assertTrue(testSnowman.getSymbol().equals(tempSnow));
  }

  /**
   * Test for getSymbol().
   */
  @Test
  public void getSymbol() {
    List<Symbol> temp = new LinkedList();
    temp.add(goodCircleTwo.getSymbol());
    temp.add(goodCircleOne.getSymbol());
    temp.add(goodCircleThree.getSymbol());
    Snowman getSymbolSnowman = new Snowman(temp);
    assertEquals(testSnowman.getSymbol(),getSymbolSnowman);
  }

}