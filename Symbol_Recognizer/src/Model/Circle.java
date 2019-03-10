package Model;


/**
 * This class represents a Circle Symbol.  A circle has a single Point field that represents
 * the center of the Circle, and then a radius value to indicate size.
 */
public class Circle implements Symbol, Comparable<Circle> {
  private Point center;
  private double radius;

  /**
   * Constructs a Circle with the following parameters:
   *
   * @param x      is the x coordinate of the center of the Circle
   * @param y      is the y coordinate of the center of the Circle
   * @param radius is the radius of the Circle.
   * @throws IllegalArgumentException if the radius is not greater than zero.
   */
  public Circle(double x, double y, double radius) throws IllegalArgumentException {
    if (radius <= 0) {
      throw new IllegalArgumentException("Radius must be greater than zero.");
    }
    this.center = new Point(x, y);
    this.radius = radius;
  }

  /**
   * Returns the type of the given symbol.
   *
   * @return the SymbolType that corresponds to this Symbol's type.
   */
  @Override
  public SymbolType symbolType() {
    return SymbolType.Circle;
  }

  /**
   * Returns the value of the given Symbol.  This value is a copy of the given in order
   * to prevent mutation.
   *
   * @return an exact copy of the given Symbol.
   */
  @Override
  public Symbol getSymbol() {
    Symbol output = new Circle(center.getX(), center.getY(), radius);
    return output;
  }

  /**
   * Returns the Center of this Circle.
   *
   * @return the value of the Center of this Circle.
   */
  public Point getCenter() {
    Point output = new Point(this.center.getX(), this.center.getY());
    return output;
  }

  /**
   * Returns the value of the radius of this Circle.
   *
   * @return the value of the radius of this Circle.
   */
  public double getRadius() {
    double output = this.radius;
    return output;
  }

  /**
   * Compares this object with the specified object for order.  Returns a
   * negative integer, zero, or a positive integer as this object is less
   * than, equal to, or greater than the specified object.
   *
   * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
   * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
   * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
   * <tt>y.compareTo(x)</tt> throws an exception.)
   *
   * <p>The implementor must also ensure that the relation is transitive:
   * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
   * <tt>x.compareTo(z)&gt;0</tt>.
   *
   * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
   * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
   * all <tt>z</tt>.
   *
   * <p>It is strongly recommended, but <i>not</i> strictly required that
   * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
   * class that implements the <tt>Comparable</tt> interface and violates
   * this condition should clearly indicate this fact.  The recommended
   * language is "Note: this class has a natural ordering that is
   * inconsistent with equals."
   *
   * <p>In the foregoing description, the notation
   * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
   * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
   * <tt>0</tt>, or <tt>1</tt> according to whether the value of
   * <i>expression</i> is negative, zero or positive.
   *
   * @param o the object to be compared.
   * @return a negative integer, zero, or a positive integer as this object is less than, equal to,
   *         or greater than the specified object.
   * @throws NullPointerException if the specified object is null
   * @throws ClassCastException   if the specified object's type prevents it from being compared to
   *                              this object.
   */
  @Override
  public int compareTo(Circle o) {
    if (radius == o.radius) {
      return 0;
    } else if (radius > o.radius) {
      return 1;
    } else {
      return -1;
    }
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Circle)) {
      return false;
    }

    Circle temp = (Circle) other;

    return temp.getCenter().equals(this.getCenter()) && temp.getRadius() == this.getRadius();
  }
}
