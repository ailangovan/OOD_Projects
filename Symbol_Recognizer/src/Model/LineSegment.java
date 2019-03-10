package Model;

import java.util.Objects;

/**
 * This class represents a LineSegment.  A LineSegment has two vertices indicating both
 * ends of the line.  It is a basic symbol used to construct bigger symbols.
 */
public class LineSegment implements Symbol {
  private Point pointOne;
  private Point pointTwo;

  /**
   * Constructs a new LineSegment object with the following parameters:
   *
   * @param x1 is the x coordinate of the first end of the line.
   * @param y1 is the y coordinate of the first end of the line.
   * @param x2 is the x coordinate of the second end of the line.
   * @param y2 is the y coordinate of the second end of the line.
   * @throws IllegalArgumentException if the points are identical, due to the line segment needing a
   *                                  length greater than zero.
   */
  public LineSegment(double x1, double y1, double x2, double y2) throws IllegalArgumentException {
    if (x1 == x2 && y1 == y2) {
      throw new IllegalArgumentException("Line Segment must have a length greater than zero");
    }
    pointOne = new Point(x1, y1);
    pointTwo = new Point(x2, y2);
  }

  /**
   * Returns the type of the given symbol.
   *
   * @return the SymbolType that corresponds to this Symbol's type.
   */
  @Override
  public SymbolType symbolType() {
    return SymbolType.LineSegment;
  }

  /**
   * Returns the value of the given Symbol.  This value is a copy of the given in order
   * to prevent mutation.
   *
   * @return an exact copy of the given Symbol.
   */
  @Override
  public Symbol getSymbol() {
    Symbol output = new LineSegment(pointOne.getX(), pointOne.getY(),
            pointTwo.getX(), pointTwo.getY());
    return output;
  }

  /**
   * Returns the two vertices of the LineSegment as a two member array.
   *
   * @return an array of the two LineSegments that are the vertices of the LineSegment.
   */
  public Point[] getLineVertices() {
    Point tempOne = new Point(this.pointOne.getX(), this.pointOne.getY());
    Point tempTwo = new Point(this.pointTwo.getX(), this.pointTwo.getY());
    Point[] output = {tempOne, tempTwo};
    return output;
  }

  /**
   * This method checks if this LineSegment shares one point in common with another LineSegment.
   *
   * @param other LineSegment to compare to this one.
   * @return True if this shares only one point in common with another point.
   */
  public boolean shareOnePoint(LineSegment other) {
    Point[] otherPoints = other.getLineVertices();
    double errorThreshold = 0.15 * this.pointTwo.euclidianDistance(pointOne);
    int commonPoints = 0;
    for (Point p : otherPoints) {
      if (pointOne.euclidianDistance(p) < errorThreshold) {
        commonPoints = commonPoints + 1;
      }
      if (pointTwo.euclidianDistance(p) < errorThreshold) {
        commonPoints = commonPoints + 1;
      }
    }
    return commonPoints == 1;
  }


  /**
   * Gets the shared point between this line segment and
   * another one if it exists.
   *
   * @param other LineSegment to check for shared point with.
   * @return The shared point if one exists.
   * @throws IllegalArgumentException if the other LineSegment doesn't have a point in common.
   */
  public Point sharedPoint(LineSegment other) throws IllegalArgumentException {
    if (!this.shareOnePoint(other)) {
      throw new IllegalArgumentException(
              "These LineSegments do not share only one point in common"
      );
    }
    Point[] otherPoints = other.getLineVertices();

    double errorThreshold = 0.05 * this.pointTwo.euclidianDistance(pointOne);
    for (Point p : otherPoints) {
      if (pointOne.euclidianDistance(p) < errorThreshold) {
        return pointOne.midPoint(p);
      }
      if (pointTwo.euclidianDistance(p) < errorThreshold) {
        return pointTwo.midPoint(p);
      }
    }
    throw new IllegalArgumentException("These LineSegments do not share any points in common");
  }

  /**
   * Equals method to check if this linesegment equals another object.
   * Line segments are equal if they share the same pair of points.
   *
   * @param other is the other object to compare to.
   * @return true if the other object is a LineSegment and shares same point with this one.
   */
  @Override
  public boolean equals(Object other) {
    if (other instanceof LineSegment) {
      Point[] otherPoints = ((LineSegment) other).getLineVertices();
      Point otherPointOne = otherPoints[0];
      Point otherPointTwo = otherPoints[1];

      if (this.pointOne.equals(otherPointOne) && this.pointTwo.equals(otherPointTwo)) {
        return true;
      }
      if (this.pointTwo.equals(otherPointOne) && this.pointOne.equals(otherPointTwo)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Generate a hashcode for this LineSegment.
   *
   * @return the int hashcode for this LineSegment.
   */
  @Override
  public int hashCode() {
    return Objects.hash(pointOne, pointTwo);
  }

  /**
   * Gets the slope for this line.
   *
   * @return the double slope for this line segment.
   */
  public double getSlope() {
    return (pointOne.getY() - pointTwo.getY()) / (pointOne.getX() - pointTwo.getX());
  }

  /**
   * Get the degree between two LineSegments.
   *
   * @param other Line Segment to get slope with.
   * @return the slope in degrees.
   */
  public double getDegree(LineSegment other) {
    double m1 = this.getSlope();
    double m2 = other.getSlope();

    double toTan = Math.abs((m1 - m2) / (1 + m1 * m2));
    return Math.toDegrees(Math.atan(toTan));
  }
}
