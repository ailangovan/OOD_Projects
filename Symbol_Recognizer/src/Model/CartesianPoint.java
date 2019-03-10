package Model;

/**
 * This represents all available methods for Cartesian Points.  Cartesian Points are two
 * dimensional for this interface.
 */
public interface CartesianPoint {
  /**
   * Calculates the Euclidean distance of the given Cartesian Point according to the
   * formula of sqrt((x1 - x2)^2 + (y1 - y2)^2).  Returns this value as a double.
   * @param o is the specified Cartesian point for getting the distance from this Cartesian Point.
   * @return the distance between the two points.
   */
  double euclidianDistance(CartesianPoint o);

  /**
   * Returns the value of the X coordinate of the Cartesian Point.
   * @return the value of the x coordinate.
   */
  double getX();

  /**
   * Returns the value of the Y coordinate of the Cartesian Point.
   * @return the value of the y coordinate.
   */
  double getY();
}
