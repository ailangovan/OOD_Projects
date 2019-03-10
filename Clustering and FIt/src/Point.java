/**
 * This is the Point class to represent a 2D Point on a graph.
 * Created by Oz on 2/26/2017.
 */
public class Point {
  private double xCoor;
  private double yCoor;

  /**
   * This is the constructor for the 2D Point.
   *
   * @param xCoor for this point.
   * @param yCoor for this point.
   */
  public Point(double xCoor, double yCoor) {
    this.xCoor = xCoor;
    this.yCoor = yCoor;
  }

  /**
   * gets the xCoor for this point.
   * @return the xCoor for this point.
   */
  public double getxCoor() {
    return this.xCoor;
  }

  /**
   * Gets the yCoor for this point.
   * @return the yCoor for this point.
   */
  public double getyCoor() {
    return this.yCoor;
  }

  /**
   * Calculate the distance between this point and other point.
   * @param p is the other point.
   * @return the double distance between this point and point p.
   */
  public double distance(Point p) {
    double distance;
    double dx;
    double dy;

    dx = Math.pow((this.xCoor - p.getxCoor()), 2);
    dy = Math.pow((this.yCoor - p.getyCoor()), 2);

    distance = Math.sqrt((dx + dy));

    return distance;
  }
}
