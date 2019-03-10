import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * This is an example of the LineFit in the Plotter.
 * Created by Oz on 2/28/2017.
 */
public class LineFitTest {
  /**
   * Main Function for Plotter.
   */
  public static void main(String[] args) throws FileNotFoundException, TimeoutException {

    Scanner sc = new Scanner(new FileInputStream("linedata-1.txt"));
    ListOfPoints lop = new ListOfPoints();

    ImagePlotter plotter = new ImagePlotter();
    plotter.setWidth(400);
    plotter.setHeight(400);

    plotter.setDimensions(-350, 350, -350, 350);

    while (sc.hasNext()) {
      double x = sc.nextDouble();
      double y = sc.nextDouble();

      lop.addPoint(x, y);
    }

    ArrayList<Point> points = lop.getPointArrayList();
    Line fitLine = lop.fitLine();

    Point currentPoint;
    for (int i = 0; i < lop.getPointArrayList().size(); i++) {
      currentPoint = lop.getPointArrayList().get(i);
      plotter.addPoint((int) currentPoint.getxCoor(), (int) currentPoint.getyCoor(), Color.BLACK);
    }
    int y1 = (int) fitLine.getIntercept();
    int x1 = 0;
    int x2 = 1000;
    double y2 = (fitLine.getSlope() * x2) + y1;
    plotter.addLine(x1, y1, x2, (int) y2);
    int x3 = -1000;
    double y3 = (fitLine.getSlope() * x3) + y1;
    plotter.addLine(x3, (int) y3, x1, y1);

    try {
      plotter.write("linear.png");
    } catch (IOException e) {

    }
  }
}
