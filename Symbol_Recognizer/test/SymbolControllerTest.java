import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.event.MouseEvent;

import Controller.SymbolController;
import Controller.SymbolMouseListener;
import Model.Circle;
import Model.LineSegment;
import Model.SymbolRecognizer;
import Model.SymbolRecognizerImpl;
import View.MockView;

/**
 * This is a test for the SymbolController class.
 *
 * Created by Oz on 4/16/2017.
 */
public class SymbolControllerTest {

  SymbolController controller;
  SymbolRecognizer model;
  SymbolMouseListener listener;
  MockView view;

  /**
   * Sets up classes for testing.
   */
  @Before
  public void setUp() {
    model = new SymbolRecognizerImpl();
    controller = new SymbolController(model);
    listener = new SymbolMouseListener(controller);
    view = new MockView(controller);

    controller.setView(view);
  }

  /**
   * Tests that when input is passed to the controller. The view appropriately gets called
   * to draw those inputs.
   */
  @Test
  public void testLineInputToView() {
    for (int i = 0; i < 10; i++) {
      MouseEvent e = new MouseEvent(new Container(), MouseEvent.MOUSE_DRAGGED, 1, 0,
              i, i, 1, false, MouseEvent.BUTTON1);
      listener.mouseDragged(e);

    }

    String output = view.getTestOutput();
    String expected = "redrawScene Called Properly.\n"
            + "This is a list of all the User Input Points:\n"
            + "\n"
            + "redrawScene Called Properly.\n"
            + "This is a list of all the User Input Points:\n"
            + "(0,0)\n"
            + "redrawScene Called Properly.\n"
            + "This is a list of all the User Input Points:\n"
            + "(0,0)(1,1)\n"
            + "redrawScene Called Properly.\n"
            + "This is a list of all the User Input Points:\n"
            + "(0,0)(1,1)(2,2)\n"
            + "redrawScene Called Properly.\n"
            + "This is a list of all the User Input Points:\n"
            + "(0,0)(1,1)(2,2)(3,3)\n"
            + "redrawScene Called Properly.\n"
            + "This is a list of all the User Input Points:\n"
            + "(0,0)(1,1)(2,2)(3,3)(4,4)\n"
            + "redrawScene Called Properly.\n"
            + "This is a list of all the User Input Points:\n"
            + "(0,0)(1,1)(2,2)(3,3)(4,4)(5,5)\n"
            + "redrawScene Called Properly.\n"
            + "This is a list of all the User Input Points:\n"
            + "(0,0)(1,1)(2,2)(3,3)(4,4)(5,5)(6,6)\n"
            + "redrawScene Called Properly.\n"
            + "This is a list of all the User Input Points:\n"
            + "(0,0)(1,1)(2,2)(3,3)(4,4)(5,5)(6,6)(7,7)\n"
            + "redrawScene Called Properly.\n"
            + "This is a list of all the User Input Points:\n"
            + "(0,0)(1,1)(2,2)(3,3)(4,4)(5,5)(6,6)(7,7)(8,8)\n"
            + "redrawScene Called Properly.\n"
            + "This is a list of all the User Input Points:\n"
            + "(0,0)(1,1)(2,2)(3,3)(4,4)(5,5)(6,6)(7,7)(8,8)(9,9)\n";

    Assert.assertEquals(controller.getUserInput().size(), 10);
    Assert.assertEquals(expected, output);
  }

  /**
   * Tests adding a linesegment to the controller sends to view properly.
   */
  @Test
  public void testAddingLineSegment() {
    for (int i = 0; i < 10; i++) {
      MouseEvent e = new MouseEvent(new Container(), MouseEvent.MOUSE_DRAGGED, 1, 0,
              i, i, 1, false, MouseEvent.BUTTON1);
      listener.mouseDragged(e);

    }
    MouseEvent e = new MouseEvent(new Container(), MouseEvent.MOUSE_RELEASED, 1, 0,
            10, 10, 1, false, MouseEvent.BUTTON1);
    listener.mouseReleased(e);

    String output = view.getTestOutput();
    String expected = "redrawScene Called Properly.\n"
            + "This is a list of all the User Input Points:\n"
            + "\n"
            + "redrawScene Called Properly.\n"
            + "This is a list of all the User Input Points:\n"
            + "(0,0)\n"
            + "redrawScene Called Properly.\n"
            + "This is a list of all the User Input Points:\n"
            + "(0,0)(1,1)\n"
            + "redrawScene Called Properly.\n"
            + "This is a list of all the User Input Points:\n"
            + "(0,0)(1,1)(2,2)\n"
            + "redrawScene Called Properly.\n"
            + "This is a list of all the User Input Points:\n"
            + "(0,0)(1,1)(2,2)(3,3)\n"
            + "redrawScene Called Properly.\n"
            + "This is a list of all the User Input Points:\n"
            + "(0,0)(1,1)(2,2)(3,3)(4,4)\n"
            + "redrawScene Called Properly.\n"
            + "This is a list of all the User Input Points:\n"
            + "(0,0)(1,1)(2,2)(3,3)(4,4)(5,5)\n"
            + "redrawScene Called Properly.\n"
            + "This is a list of all the User Input Points:\n"
            + "(0,0)(1,1)(2,2)(3,3)(4,4)(5,5)(6,6)\n"
            + "redrawScene Called Properly.\n"
            + "This is a list of all the User Input Points:\n"
            + "(0,0)(1,1)(2,2)(3,3)(4,4)(5,5)(6,6)(7,7)\n"
            + "redrawScene Called Properly.\n"
            + "This is a list of all the User Input Points:\n"
            + "(0,0)(1,1)(2,2)(3,3)(4,4)(5,5)(6,6)(7,7)(8,8)\n"
            + "redrawScene Called Properly.\n"
            + "This is a list of all the User Input Points:\n"
            + "(0,0)(1,1)(2,2)(3,3)(4,4)(5,5)(6,6)(7,7)(8,8)(9,9)\n"
            + "redrawScene Called Properly.\n"
            + "LineSegment added at (9,9), (0,0).\n"
            + "Label Added: Line Segment.\n"
            + "This is a list of all the User Input Points:\n"
            + "\n";
    // Check user input is cleared.
    Assert.assertEquals(controller.getUserInput().size(), 0);
    Assert.assertEquals(expected, output);
  }

  /**
   * Tests adding a circle to the controller sends task to view properly.
   */
  @Test
  public void testAddingCircle() {
    MouseEvent e = new MouseEvent(new Container(), MouseEvent.MOUSE_RELEASED, 1, 0,
            0, 6, 1, false, MouseEvent.BUTTON1);
    listener.mouseDragged(e);
    e = new MouseEvent(new Container(), MouseEvent.MOUSE_RELEASED, 1, 0,
            6, 0, 1, false, MouseEvent.BUTTON1);
    listener.mouseDragged(e);
    e = new MouseEvent(new Container(), MouseEvent.MOUSE_RELEASED, 1, 0,
            -6, 0, 1, false, MouseEvent.BUTTON1);
    listener.mouseDragged(e);
    e = new MouseEvent(new Container(), MouseEvent.MOUSE_RELEASED, 1, 0,
            0, -6, 1, false, MouseEvent.BUTTON1);
    listener.mouseDragged(e);
    e = new MouseEvent(new Container(), MouseEvent.MOUSE_RELEASED, 1, 0,
            0, -6, 1, false, MouseEvent.BUTTON1);
    listener.mouseReleased(e);

    String output = view.getTestOutput();
    String expected = "redrawScene Called Properly.\n"
            + "This is a list of all the User Input Points:\n"
            + "\n"
            + "redrawScene Called Properly.\n"
            + "This is a list of all the User Input Points:\n"
            + "(0,6)\n"
            + "redrawScene Called Properly.\n"
            + "This is a list of all the User Input Points:\n"
            + "(0,6)(6,0)\n"
            + "redrawScene Called Properly.\n"
            + "This is a list of all the User Input Points:\n"
            + "(0,6)(6,0)(-6,0)\n"
            + "redrawScene Called Properly.\n"
            + "This is a list of all the User Input Points:\n"
            + "(0,6)(6,0)(-6,0)(0,-6)\n"
            + "redrawScene Called Properly.\n"
            + "Circle added at (0,0) with radius, 6.\n"
            + "Label Added: Circle.\n"
            + "This is a list of all the User Input Points:\n"
            + "\n";
    // Check user input is cleared.
    Assert.assertEquals(controller.getUserInput().size(), 0);
    Assert.assertEquals(expected, output);
  }

  /**
   * This tests that if the User inputs something not close enough to be a Circle or
   * a LineSegment, the User Input is cleared upon mouse release and no Symbol was added
   * to the Model.
   */
  @Test
  public void testAddingNonsense() {
    MouseEvent e = new MouseEvent(new Container(), MouseEvent.MOUSE_RELEASED, 1, 0,
            0, 120937, 1, false, MouseEvent.BUTTON1);
    listener.mouseDragged(e);
    e = new MouseEvent(new Container(), MouseEvent.MOUSE_RELEASED, 1, 0,
            102973, 12312, 1, false, MouseEvent.BUTTON1);
    listener.mouseDragged(e);
    e = new MouseEvent(new Container(), MouseEvent.MOUSE_RELEASED, 1, 0,
            -123124, 12321, 1, false, MouseEvent.BUTTON1);
    listener.mouseDragged(e);
    e = new MouseEvent(new Container(), MouseEvent.MOUSE_RELEASED, 1, 0,
            124124, -2, 1, false, MouseEvent.BUTTON1);
    listener.mouseDragged(e);
    e = new MouseEvent(new Container(), MouseEvent.MOUSE_RELEASED, 1, 0,
            4124, -128903710, 1, false, MouseEvent.BUTTON1);
    listener.mouseReleased(e);

    String output = view.getTestOutput();
    String expected =
            "redrawScene Called Properly.\n"
                    + "This is a list of all the User Input Points:\n"
                    + "\n"
                    + "redrawScene Called Properly.\n"
                    + "This is a list of all the User Input Points:\n"
                    + "(0,120937)\n"
                    + "redrawScene Called Properly.\n"
                    + "This is a list of all the User Input Points:\n"
                    + "(0,120937)(102973,12312)\n"
                    + "redrawScene Called Properly.\n"
                    + "This is a list of all the User Input Points:\n"
                    + "(0,120937)(102973,12312)(-123124,12321)\n"
                    + "redrawScene Called Properly.\n"
                    + "This is a list of all the User Input Points:\n"
                    + "(0,120937)(102973,12312)(-123124,12321)(124124,-2)\n"
                    + "redrawScene Called Properly.\n"
                    + "This is a list of all the User Input Points:\n"
                    + "\n";
    // Check user input is cleared.
    Assert.assertEquals(controller.getUserInput().size(), 0);
    Assert.assertEquals(expected, output);
  }

  /**
   * This tests that the composite shapes in the model get added to view from controller.
   */
  @Test
  public void testCompositeShapeDrawn() {
    LineSegment a = new LineSegment(10, 0, 0, 0);
    LineSegment b = new LineSegment(10, 0, 5, 8.66);
    LineSegment c = new LineSegment(0, 0, 5, 8.66);

    model.addBasicSymbol(a);
    model.addBasicSymbol(b);
    model.addBasicSymbol(c);

    Circle goodCircleOne = new Circle(0, 0, 0.5);
    Circle goodCircleTwo = new Circle(2, 0, 1.5);
    Circle goodCircleThree = new Circle(6, 0, 2.5);
    model.addBasicSymbol(goodCircleOne);
    model.addBasicSymbol(goodCircleTwo);
    model.addBasicSymbol(goodCircleThree);

    MouseEvent e = new MouseEvent(new Container(), MouseEvent.MOUSE_RELEASED, 1, 0,
            4124, -128903710, 1, false, MouseEvent.BUTTON1);
    listener.mouseReleased(e);

    String output = view.getTestOutput();
    String expected =
            "redrawScene Called Properly.\n"
                    + "This is a list of all the User Input Points:\n"
                    + "\n"
                    + "redrawScene Called Properly.\n"
                    + "Added: Equilateral Triangle.\n"
                    + "Label Added: Equilateral Triangle.\n"
                    + "Added: Snowman.\n"
                    + "Label Added: Snowman.\n"
                    + "This is a list of all the User Input Points:"
                    + "\n\n";
    // Check user input is cleared.
    Assert.assertEquals(controller.getUserInput().size(), 0);
    Assert.assertEquals(expected, output);
  }
}