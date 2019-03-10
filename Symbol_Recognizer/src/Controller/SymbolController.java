package Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import Model.Circle;
import Model.LineSegment;
import Model.Point;
import Model.Symbol;
import Model.SymbolRecognizer;
import View.ISymbolView;

/**
 * This class is the SymbolController. It has a model and view for recognizing symbols. There is
 * also a SymbolMouseListener in the class to handle user input to the view. The controller sets the
 * listener and appropriately delegate tasks to model and view as input is received.
 */
public class SymbolController implements ISymbolController {
  private SymbolRecognizer model;
  private ISymbolView view;
  private List<Point> userInput;
  private SymbolMouseListener listener;

  public SymbolController(SymbolRecognizer model) {
    this.model = model;
    this.listener = new SymbolMouseListener(this);
    userInput = new ArrayList<>();
  }

  /**
   * Returns all of the Symbols stored by the Model.
   *
   * @return the list of Symbols stored in the Model.
   */
  @Override
  public List<Symbol> getSymbols() {
    return model.returnCurrentSymbols();
  }

  /**
   * Adds a symbol to the Model. The method first checks that list of points for a
   * line fit and then for a circle fit. It adds at most one symbol and checks linefit
   * before circle. The goodness of fit is set to 0.95.
   *
   * @param userInput is the set of points to attempt to add to model if this is a good fit for a
   *                  symbol in the model.
   */
  @Override
  public void addSymbol(List<Point> userInput) {
    // Attempt a line fit.
    double[] lineFitValues = findLineTValues(userInput);
    if (lineFit(lineFitValues) > 0.70) {
      double x1, y1, x2, y2;
      x1 = lineFitValues[4] + lineFitValues[0] * lineFitValues[6];
      y1 = lineFitValues[5] + lineFitValues[0] * lineFitValues[7];
      x2 = lineFitValues[4] + lineFitValues[1] * lineFitValues[6];
      y2 = lineFitValues[5] + lineFitValues[1] * lineFitValues[7];

      model.addBasicSymbol(new LineSegment(x2, y2, x1, y1));
      return;
    }

    // Attempt a circle fit.
    double[] fitCircleParameters = fitCircleParameters(userInput);
    if (fitCircle(fitCircleParameters) > 0.95) {
      double x, y, rad;
      x = fitCircleParameters[0];
      y = fitCircleParameters[1];
      rad = fitCircleParameters[2];

      model.addBasicSymbol(new Circle(x, y, rad));
      return;
    }


  }

  /**
   * Returns the list of points currently stored in this controller. This is the stored points from
   * SymbolMouseListener that represents the points created from user dragging the mouse.
   *
   * @return the list of points that are stored in this controller.
   */
  @Override
  public List<Point> getUserInput() {
    return this.userInput;
  }

  /**
   * Sets the view of the controller to the given view. The method also sets the mouselistener and
   * updates the view using helper functions.
   *
   * @param view is the view to set as this controller's view.
   */
  @Override
  public void setView(ISymbolView view) {
    this.view = view;
    setMouseListener();
    setMouseMotionListener();
    setCanvasSize();
  }

  /**
   * This method makes a new SymbolMouseListener and calls the view to set the mouselistener to be
   * this listener.
   */
  private void setMouseListener() {

    listener = new Controller.SymbolMouseListener(this);
    view.setMouseListener(listener);
  }

  /**
   * This method makes a new SymbolMouseListener and calls the view to set the MouseMotionListener
   * to be this listener.
   */
  private void setMouseMotionListener() {

    listener = new Controller.SymbolMouseListener(this);
    view.setMouseMotionListener(listener);
  }


  /**
   * Using userInput, the controller attempts to add a symbol with those points. After, the points
   * are cleared and view is called to redraw updated model.
   */
  protected void setCanvasSize() {
    Objects.requireNonNull(model);
    Objects.requireNonNull(view);


    // FIT OBJECTS AND CLEAR LIST
    addSymbol(userInput);
    userInput = new ArrayList<>();
    view.redrawScene();
  }

  /**
   * This method calls the view to redraw.
   */
  protected void drawInput() {
    Objects.requireNonNull(model);
    Objects.requireNonNull(view);
    view.redrawScene();
  }

  /**
   * Finds the fit of list given q and r parameters for a set of points.
   *
   * @param fitCircleParameters Is the array containing the q and r values for circle to check fit
   *                            for.
   * @return 0 - 1, with higher value indicating better fit.
   */
  private double fitCircle(double[] fitCircleParameters) {

    double q = fitCircleParameters[3];
    double r = fitCircleParameters[2];

    return 1 - Math.min(1, (q / r));
  }

  /**
   * This function takes a list of points and computes values necessary for determining goodness of
   * fit and information regarding best fit circle. The values are stored in an array.
   *
   * @param points is the list of points to compute values for.
   * @return double[4] containing [cX, xY, r, q] to use for finding best fit circle.
   */
  private double[] fitCircleParameters(List<Point> points) {
    double sX, sY, sXY, sXX, sYY, sX2Y2, sXX2Y2, sYX2Y2;

    // Initialize all sums to zero.
    sX = sY = sXY = sXX = sYY = sX2Y2 = sXX2Y2 = sYX2Y2 = 0;

    for (Point p : points) {
      double x = p.getX();
      double y = p.getY();

      sX += x;
      sY += y;
      sXY += x * y;
      sXX += x * x;
      sYY += y * y;
      sX2Y2 += Math.pow(x, 2) + Math.pow(y, 2);
      sXX2Y2 += x * (Math.pow(x, 2) + Math.pow(y, 2));
      sYX2Y2 += y * (Math.pow(x, 2) + Math.pow(y, 2));
    }

    double n = points.size();
    double d, da, db, dc;

    d = (sXX * ((n * sYY) - (sY * sY)))
            - (sXY * ((n * sXY) - (sX * sY)))
            + (sX * ((sXY * sY) - (sYY * sX)));

    da = (sXX2Y2 * ((n * sYY) - (sY * sY)))
            - (sXY * ((n * sYX2Y2) - (sX2Y2 * sY)))
            + (sX * ((sYX2Y2 * sY) - (sYY * sX2Y2)));

    db = (sXX * ((n * sYX2Y2) - (sX2Y2 * sY)))
            - (sXX2Y2 * ((n * sXY) - (sX * sY)))
            + (sX * ((sXY * sX2Y2) - (sYX2Y2 * sX)));

    dc = (sXX * ((sYY * sX2Y2) - (sYX2Y2 * sY)))
            - (sXY * ((sXY * sX2Y2) - (sYX2Y2 * sX)))
            + (sXX2Y2 * ((sXY * sY) - (sYY * sX)));

    double a = da / d;
    double b = db / d;
    double c = dc / d;

    double cX = a / 2;
    double cY = b / 2;
    double r2 = c + (cX * cX) + (cY * cY);
    double r = Math.sqrt(r2);

    double m = 0;

    for (Point p : points) {
      double x = p.getX();
      double y = p.getY();

      double dI = ((x - cX) * (x - cX)) + ((y - cY) * (y - cY)) - r2;
      m += Math.abs(dI);
    }

    double q = Math.sqrt(m) / n;

    // Initialize the return array.
    double[] returnArray = new double[4];
    returnArray[0] = cX;
    returnArray[1] = cY;
    returnArray[2] = r;
    returnArray[3] = q;

    return returnArray;
  }

  /**
   * Finds the tValues for linefit and linesegment equations from a given list of points.
   *
   * @param points is the list of points to find tMin, tMax, tOMin, tOMax.
   * @return array of doubles with [tMin, tMax, tOMin, tOMax, avgX, avgY, a, b].
   */
  private double[] findLineTValues(List<Point> points) {

    double sX, sY, q, avgX, avgY, sXY, sXX, sYY, theta1, theta2;
    int n;

    sX = sY = sXY = sXX = sYY = 0;
    for (Point p : points) {
      double x = p.getX();
      double y = p.getY();

      sX = sX + x;
      sY = sY + y;
    }

    n = points.size();

    avgX = sX / n;
    avgY = sY / n;


    for (Point p : points) {
      double x = p.getX();
      double y = p.getY();

      sXY = sXY + ((x - avgX) * (y - avgY));
      sXX = sXX + ((x - avgX) * (x - avgX));
      sYY = sYY + ((y - avgY) * (y - avgY));
    }

    q = (2 * sXY) / (sXX - sYY);

    theta1 = Math.atan(q);
    theta2 = Math.toDegrees(theta1) + 180;
    theta2 = Math.toRadians(theta2);

    double fTheta1 = (2 * sXY * Math.sin(theta1)) - ((sYY - sXX) * Math.cos(theta1));
    double fTheta2 = (2 * sXY * Math.sin(theta2)) - ((sYY - sXX) * Math.cos(theta2));


    double m;
    if (fTheta1 > 0) {
      m = theta1;
    } else {
      m = theta2;
    }

    double a = Math.cos(m / 2);
    double b = Math.sin(m / 2);


    // Find the Line Segment.
    double tMin, tMax, tOMin, tOMax;
    tMin = 0;
    tOMin = 0;
    tMax = 0;
    tOMax = 0;

    for (Point p : points) {
      double x = p.getX();
      double y = p.getY();

      double t = (a * (x - avgX)) + (b * (y - avgY));
      double tO = (b * (x - avgX)) - (a * (y - avgY));
      if (t < tMin) {
        tMin = t;
      }
      if (tO < tOMin) {
        tOMin = tO;
      }
      if (t > tMax) {
        tMax = t;
      }
      if (tO > tOMax) {
        tOMax = tO;
      }
    }
    double[] tVal = new double[8];
    tVal[0] = tMin;
    tVal[1] = tMax;
    tVal[2] = tOMin;
    tVal[3] = tOMax;
    tVal[4] = avgX;
    tVal[5] = avgY;
    tVal[6] = a;
    tVal[7] = b;

    return tVal;
  }

  /**
   * Returns the goodness of fit of a list of points to a line.
   *
   * @param tVal contains the parameters to attempt the line fit with.
   * @return 0 - 1 representing goodness of fit. Higher values indicate better fit.
   */
  private double lineFit(double[] tVal) {

    double tMin = tVal[0];
    double tMax = tVal[1];
    double tOMin = tVal[2];
    double tOMax = tVal[3];

    double val = (tOMax - tOMin) / (tMax - tMin);
    return 1 - (Math.min(1, val));
  }
}
