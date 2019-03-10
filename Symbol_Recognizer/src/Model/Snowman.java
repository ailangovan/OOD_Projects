package Model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents a Snowman Symbol object.  This object has three circles arranged
 * sequentially in decreasing size, with all centers lined up.
 */
public class Snowman implements Symbol {
  private List<Symbol> circles;

  /**
   * Constructs a Snowman.  A Snowman has three circles that form a line, and decrease in size
   * sequentially.
   * @param inputList is the List of circles being added.
   * @throws IllegalArgumentException if the constructor is passed a non Circle, Circles that
   *     do not line up within an r^2 of 0.05, do not sequentially decrease in size
   */
  public Snowman(List<Symbol> inputList)
          throws IllegalArgumentException {
    if (!checkCircles(inputList)) {
      throw new IllegalArgumentException("Not valid Snowman input.");
    }
    List<Symbol> temp = new LinkedList();
    for (Symbol c: inputList) {
      temp.add(c.getSymbol());
    }
    this.circles = temp;
  }

  /**
   * Returns the type of the given symbol.
   *
   * @return the SymbolType that corresponds to this Symbol's type.
   */
  @Override
  public SymbolType symbolType() {

    return SymbolType.Snowman;
  }

  /**
   * Returns the value of the given Symbol.  This value is a copy of the given in order
   * to prevent mutation.
   *
   * @return an exact copy of the given Symbol.
   */
  @Override
  public Symbol getSymbol() {
    List<Symbol> temp = new LinkedList();
    for (Symbol c: circles) {
      temp.add(c.getSymbol());
    }
    Symbol output = new Snowman(temp);
    return output;
  }

  private static boolean checkCircles(List<Symbol> inputList) {
    List<Circle> test = new LinkedList();
    for (Symbol s: inputList) {
      Circle temp = (Circle) s;
      if (test.contains(temp)) {
        return true;
      }
      test.add(temp);
    }
    Collections.sort(test);
    return collinear(test)
            && circlesAdjacent(test.subList(0,2))
            && circlesAdjacent(test.subList(1,3));
  }

  /**
   * Determines if the three circles' centers are collinear.  This is done by
   * setting the slopes between the 1st and 2nd circle equal to the slope of the
   * 2nd and 3rd circle centers.  In order to avoid potential issues with dividing by zero,
   * the denominators were factored such that there is no division.  An error of 30% is given.
   * @param circles are the circles that are being passed to the constructor as an
   *                attempt to make a snowman.
   * @return true if the slopes are close enough to equal or equal, false otherwise.
   */
  private static boolean collinear(List<Circle> circles) {
    Circle circleOne = circles.get(0);
    Circle circleTwo = circles.get(1);
    Circle circleThree = circles.get(2);

    double slope12 = (circleTwo.getCenter().getY() - circleOne.getCenter().getY())
            * (circleThree.getCenter().getX() - circleTwo.getCenter().getX());
    double slope23 = (circleThree.getCenter().getY() - circleTwo.getCenter().getY())
            * (circleTwo.getCenter().getX() - circleOne.getCenter().getX());
    if (slope23 != 0) {
      return Math.abs(slope12 / slope23) <= 1.3 && Math.abs(slope12 / slope23) >= 0.7;
    }
    return Math.abs(slope12 - slope23) < 20;
  }

  /**
   * Determines if the two circles are touching each other.  It determines this if the distance
   * between the two points is within 10% of touching each other to account for
   * imprecision in drawing.
   * @param input is a list of two circles.
   * @return true if they are adjacent and touching, false otherwise.
   */
  private static boolean circlesAdjacent(List<Circle> input) {
    double distance = input.get(0).getCenter().euclidianDistance(input.get(1).getCenter());
    double radiusOne = input.get(0).getRadius();
    double radiusTwo = input.get(1).getRadius();
    double differenceDistanceSumRadius = Math.abs(distance - (radiusTwo + radiusOne));
    return differenceDistanceSumRadius < 20;
  }

  /**
   * Returns the circles that construct this Snowman.
   * @return a List of the circles that construct the snowman.
   */
  public List<Symbol> getCircles() {
    List<Symbol> temp = new LinkedList();
    for (Symbol s: circles) {
      temp.add(s.getSymbol());
    }
    return temp;
  }

  /**
   * Equals method for the Snowman.  If the other object is a Snowman, compares each element
   * in this snowman to determine if the same circles are in the other Snowman.
   * @param other is the other object being compared to this Snowman.
   * @return true if the other object is a Snowman with the same values for circles,
   *     false otherwise.
   */
  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Snowman)) {
      return false;
    }

    Snowman temp = (Snowman) other;

    List<Symbol> otherCircles = temp.circles;
    for (Symbol s: this.circles) {
      if (!otherCircles.contains(s)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Generate the hashcode for this Snowman Object.
   * @return the hashcode for this Snowman Object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.circles.get(0),this.circles.get(1),this.circles.get(2));
  }
}
