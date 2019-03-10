/**
 * This class represents a line in format y(x) = mx + b.
 * Created by Oz on 2/26/2017.
 */
public class Line {
  private double slope;
  private double intercept;

  /**
   * Constructor for a line.
   *
   * @param slope     is the slope for this line.
   * @param intercept is the intercept for this line.
   */
  public Line(double slope, double intercept) {
    this.slope = slope;
    this.intercept = intercept;
  }

  /**
   * Gets the slope for this Line.
   *
   * @return the Slope of this line.
   */
  public double getSlope() {
    return this.slope;
  }

  /**
   * Gets the intercept of this Line.
   *
   * @return the intercept of this Line.
   */
  public double getIntercept() {
    return this.intercept;
  }
}
