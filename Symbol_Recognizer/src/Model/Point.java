package Model;

import java.util.Objects;

/**
 * This object represents a Coordinate Point in 2 Dimensional space of the form (X,Y).
 */
public class Point implements CartesianPoint {
  private double x;
  private double y;

  /**
   * Constructs a Point object with the following parameters:
   *
   * @param x is the x coordinate of the point.
   * @param y is the y coordinate of the point.
   */
  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Calculates the Euclidean distance of the given Cartesian Point according to the
   * formula of sqrt((x1 - x2)^2 + (y1 - y2)^2).  Returns this value as a double.
   *
   * @param o is the specified Cartesian point for getting the distance from this Cartesian Point.
   * @return the distance between the two points.
   */
  @Override
  public double euclidianDistance(CartesianPoint o) {
    return Math.sqrt(Math.pow((this.x - o.getX()), 2) + Math.pow((this.y - o.getY()), 2));
  }

  /**
   * Returns the value of the X coordinate of the Cartesian Point.
   *
   * @return the value of the x coordinate.
   */
  @Override
  public double getX() {
    double ret = this.x;
    return ret;
  }

  /**
   * Returns the value of the Y coordinate of the Cartesian Point.
   *
   * @return the value of the y coordinate.
   */
  @Override
  public double getY() {
    double ret = this.y;
    return ret;
  }

  /**
   * Return the midpoint of this point and other point.
   *
   * @param other Point to find midpoint to.
   * @return the Point that is the midpoint of this point and other point.
   */
  public Point midPoint(Point other) {
    double xCoord = (this.x + other.getX()) / 2;
    double yCoord = (this.y + other.getY()) / 2;

    return new Point(xCoord, yCoord);
  }

  /**
   * Checks if this point is equal to another object. They are equal if
   * the other object is a point and has same X and Y coords as this Point.
   *
   * @param other is the object to check equality with.
   * @return true if other object is a point and has same X and Y coords as this point. If not,
   *         false.
   */
  @Override
  public boolean equals(Object other) {
    if (other instanceof Point) {
      Point otherP = (Point) other;
      return (this.x == otherP.getX() && this.y == otherP.getY());
    }
    return false;
  }

  /**
   * Generates the hashcode for this Point.
   *
   * @return the int hash code for this Point.
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.x, this.y);
  }
}
