import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

/**
 * This is a class representing a list of Points.
 * Created by Oz on 2/26/2017.
 */
public class ListOfPoints {

  private ArrayList<Point> pointArrayList;

  /**
   * Constructor for a new ListOfPoints.
   */
  public ListOfPoints() {
    this.pointArrayList = new ArrayList<Point>();
  }

  /**
   * Adds a new point to the end of the list.
   *
   * @param x is the xCoord for the point to be added.
   * @param y is the yCoord for the point to be added.
   */
  public void addPoint(double x, double y) {
    pointArrayList.add(new Point(x, y));
  }

  /**
   * Returns the list of points so far.
   *
   * @return the list of points so far.
   */
  public ArrayList<Point> getPointArrayList() {
    return this.pointArrayList;
  }

  /**
   * Calculates sum of x values for this list of points.
   *
   * @return the sum of x points in this list of points.
   */
  private double sumX() {
    int i = 0;
    Point currentPoint;
    double sumOfX = 0;

    while (pointArrayList.size() > i) {
      currentPoint = pointArrayList.get(i);
      sumOfX = currentPoint.getxCoor() + sumOfX;
      i++;
    }

    return sumOfX;
  }

  /**
   * Calculates the sum of y values for this list of points.
   *
   * @return the sum value of y values for this list of points.
   */
  private double sumY() {
    int i = 0;
    Point currentPoint;
    double sumOfY = 0;

    while (pointArrayList.size() > i) {
      currentPoint = pointArrayList.get(i);
      sumOfY = currentPoint.getyCoor() + sumOfY;
      i++;
    }

    return sumOfY;
  }

  /**
   * Calculates sum of XX values for this list of points.
   *
   * @return the sum of XX points in this list of points.
   */
  private double sumXX() {
    int i = 0;
    Point currentPoint;
    double sumOfXX = 0;

    while (pointArrayList.size() > i) {
      currentPoint = pointArrayList.get(i);
      sumOfXX = Math.pow(currentPoint.getxCoor(), 2) + sumOfXX;
      i++;
    }

    return sumOfXX;
  }

  /**
   * Calculates sum of XY values for this list of points.
   *
   * @return the sum of XY for points in this list of points.
   */
  private double sumXY() {
    int i = 0;
    Point currentPoint;
    double sumOfXY = 0;

    while (pointArrayList.size() > i) {
      currentPoint = pointArrayList.get(i);
      sumOfXY = (currentPoint.getxCoor() * currentPoint.getyCoor()) + sumOfXY;
      i++;
    }

    return sumOfXY;
  }

  /**
   * Array based summing of this list of points. Finds x, y, XX, and XY. This is better than
   * iterating the list 4 times to get the sums.
   *
   * @return the array with sums [x, y, XX, XY].
   */
  private double[] sumArray() {
    double[] sumArray;

    sumArray = new double[4];
    int i = 0;
    Point currentPoint;

    while (pointArrayList.size() > i) {
      currentPoint = pointArrayList.get(i);
      sumArray[0] = currentPoint.getxCoor() + sumArray[0];
      sumArray[1] = currentPoint.getyCoor() + sumArray[1];
      sumArray[2] = Math.pow(currentPoint.getxCoor(), 2) + sumArray[2];
      sumArray[3] = (currentPoint.getxCoor() * currentPoint.getyCoor()) + sumArray[3];
      i++;
    }
    return sumArray;
  }

  /**
   * This function returns the best fit line for this set of points.
   *
   * @return the Line representing best fit.
   */
  public Line fitLine() {

    double[] sumArray = this.sumArray();

    double sumX = sumArray[0];
    double sumY = sumArray[1];
    double sumXX = sumArray[2];
    double sumXY = sumArray[3];
    double n = this.pointArrayList.size();


    double d = (sumXX * n) - (sumX * sumX);
    double dm = (sumXY * n) - (sumX * sumY);
    double db = (sumY * sumXX) - (sumX * sumXY);

    double m = dm / d;
    double b = db / d;

    return new Line(m, b);
  }

  /**
   * Finds the clusters for this List of Points.
   *
   * @param k is the number of clusters to cluster points by.
   * @return a list of integers with ith element of list corresponding to cluster for ith element
   *         for this list.
   * @throws TimeoutException     is the function runs more than 100 iterations without finding
   *                              acceptable error threshold.
   * @throws IllegalArgumentException    if the k is invalid due to being less than or equal
   *                                     to 0 and larger than number of data points.
   */
  public ArrayList<Integer> kmeans(int k) throws TimeoutException, IllegalArgumentException {

    // Check if K is valid and throw IllegalArguementException if not
    if (k <= 0) {
      throw new IllegalArgumentException("k must be non-zero, positive int");
    }

    if (k > this.pointArrayList.size()) {
      throw new IllegalArgumentException("More clusters than points on graph.");
    }

    // Initialize the error to infinity
    double error = Double.POSITIVE_INFINITY;
    double threshhold = 0.0001;

    // Initialize list for integers with max distance.
    ArrayList<Integer> clusterList;
    ArrayList<Double> distanceList;
    clusterList = new ArrayList<Integer>();
    distanceList = new ArrayList<Double>();
    for (int i = 0; i < pointArrayList.size(); i++) {
      clusterList.add(0);
      distanceList.add(Double.MAX_VALUE);
    }

    // Loop once for each random cluster center chosen. Initial building of cluster points.
    for (int i = 0; i < k; i++) {
      // Get the element in list that is the current center.
      int center = (int) (Math.random() * pointArrayList.size());
      Point currentCenter = pointArrayList.get(center);
      for (int j = 0; j < pointArrayList.size(); j++) {
        double distance = pointArrayList.get(j).distance(currentCenter);
        double currentDistance = distanceList.get(j);
        if (distance < currentDistance) {
          distanceList.set(j, distance);
          clusterList.set(j, i);
        }
      }
    }
    // Now iterate 100 times assigning new clusters and checking error.
    for (int i = 0; i < 100; i++) {
      dataCluster(k, clusterList, distanceList);
      double newError = currentError(k, clusterList, distanceList);
      double checkError = ((Math.abs(newError - error)) / error);
      i++;
      if (checkError < threshhold) {
        return clusterList;
      }
      error = newError;
    }
    throw new TimeoutException("Ran too long -- No kMeans determined");
  }

  /**
   * This function mutates cluster list for each point based on the current centers and
   * distances.
   *
   * @param k            is the number of clusters.
   * @param clusters     is the current list of clusters.
   * @param distanceList is the list of minimum distances.
   */
  private void dataCluster(int k, ArrayList<Integer> clusters,
                           ArrayList<Double> distanceList) {

    for (int i = 0; i < k; i++) {
      // Calculate center for a cluster
      double x = 0;
      double y = 0;
      int count = 0;
      for (int j = 0; j < pointArrayList.size(); j++) {
        if (clusters.get(j) == (i)) {
          x = pointArrayList.get(j).getxCoor() + x;
          y = pointArrayList.get(j).getyCoor() + y;
          count++;
        }
      }
      x = x / count;
      y = y / count;
      Point currentCenter = new Point(x, y);
      // Iterate through the list and assign to minimum distance cluster
      for (int j = 0; j < pointArrayList.size(); j++) {
        double distance = pointArrayList.get(j).distance(currentCenter);
        double currentDistance = distanceList.get(j);
        if (distance < currentDistance) {
          distanceList.set(j, distance);
          clusters.set(j, i);
        }
      }
    }
  }

  /**
   * This function calculates an error given cluster list and distances.
   *
   * @param k         is the number of clusters.
   * @param clusters  is the current cluster list.
   * @param distances is the current distance list.
   * @return the double value of the current error.
   */
  private double currentError(int k, ArrayList<Integer> clusters,
                              ArrayList<Double> distances) {

    double distanceCounter = 0;

    for (int i = 0; i < k; i++) {
      // Calculate center for a cluster
      double x = 0;
      double y = 0;
      int count = 0;
      for (int j = 0; j < pointArrayList.size(); j++) {
        if (clusters.get(j) == i) {
          x = pointArrayList.get(j).getxCoor() + x;
          y = pointArrayList.get(j).getyCoor() + y;
          count++;
        }
      }
      x = x / count;
      y = y / count;
      Point currentCenter = new Point(x, y);

      for (int j = 0; j < pointArrayList.size(); j++) {
        if (clusters.get(j) == i) {
          double distance = pointArrayList.get(j).distance(currentCenter);
          distances.set(j, distance);
          distanceCounter = distances.get(j) + distanceCounter;
        }
      }
    }
    distanceCounter = distanceCounter / pointArrayList.size();
    return distanceCounter;
  }
}

