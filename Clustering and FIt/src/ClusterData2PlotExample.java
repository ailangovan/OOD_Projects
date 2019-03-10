import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * This is a test with ClusterData2.
 * Created by Oz on 2/26/2017.
 */
public class ClusterData2PlotExample {
  /**
   * Main test function.
   * @param args something so Javadocs doesn't yell at me.
   * @throws FileNotFoundException is file is not found.
   * @throws TimeoutException if kmeans times out.
   */
  public static void main(String[] args) throws FileNotFoundException, TimeoutException {

    Scanner sc = new Scanner(new FileInputStream("clusterdata-2.txt"));
    ListOfPoints lop = new ListOfPoints();

    ImagePlotter plotter = new ImagePlotter();
    plotter.setWidth(400);
    plotter.setHeight(400);

    plotter.setDimensions(-150, 450, -350, 350);

    while (sc.hasNext()) {
      double x = sc.nextDouble();
      double y = sc.nextDouble();

      lop.addPoint(x, y);
    }

    ArrayList<Point> points = lop.getPointArrayList();
    ArrayList<Integer> clusters = lop.kmeans(2);
    Point currentPoint;
    for (int i = 0; i < lop.getPointArrayList().size(); i++) {
      if (clusters.get(i) == 1) {
        currentPoint = lop.getPointArrayList().get(i);
        plotter.addPoint((int) currentPoint.getxCoor(), (int) currentPoint.getyCoor(), Color.GREEN);
      } else {
        currentPoint = lop.getPointArrayList().get(i);
        plotter.addPoint((int) currentPoint.getxCoor(), (int) currentPoint.getyCoor(), Color.RED);
      }
    }
    try {
      plotter.write("clusterdata-2.png");
    } catch (IOException e) {

    }
  }
}
