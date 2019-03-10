import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.event.MouseEvent;

import Controller.MockController;
import Controller.SymbolMouseListener;
import Model.SymbolRecognizer;
import Model.SymbolRecognizerImpl;

/**
 * This is a test class for the SymbolMouseListener. It conducts testing with the MockController
 * class.
 *
 * Created by Oz on 4/17/2017.
 */
public class SymbolMouseListenerTest {

  MockController controller;
  SymbolRecognizer model;
  SymbolMouseListener listener;

  /**
   * Sets up classes for testing. Passes a few mouse events to the listener.
   */
  @Before
  public void setUp() {
    model = new SymbolRecognizerImpl();
    controller = new MockController(model);
    listener = new SymbolMouseListener(controller);

    MouseEvent e = new MouseEvent(new Container(),MouseEvent.MOUSE_DRAGGED, 1, 0,
            100, 100, 1, false, MouseEvent.BUTTON1 );
    MouseEvent e2 = new MouseEvent(new Container(),MouseEvent.MOUSE_DRAGGED, 1, 0,
            101, 101, 1, false, MouseEvent.BUTTON1 );
    MouseEvent e3 = new MouseEvent(new Container(),MouseEvent.MOUSE_DRAGGED, 1, 0,
            102, 102, 1, false, MouseEvent.BUTTON1 );
    listener.mouseDragged(e);
    listener.mouseDragged(e2);
    listener.mouseDragged(e3);
  }

  /**
   * This method tests that the mouseDragged events call correct methods in controller.
   */
  @Test
  public void mouseDragged() {
    String actual = controller.getOutput();
    String expected =
            "MOUSE DRAG EVENT DETECTED, drawInput was called properly.\n"
                    + "MOUSE DRAG EVENT DETECTED, drawInput was called properly.\n"
                    + "MOUSE DRAG EVENT DETECTED, drawInput was called properly.\n";

    Assert.assertEquals(expected, actual);
  }

  /**
   * This method tests the mouseReleased events call the correct method in controller.
   */
  @Test
  public void mouseReleased() {
    MouseEvent e = new MouseEvent(new Container(),MouseEvent.MOUSE_RELEASED, 1, 0,
            103, 103, 1, false, MouseEvent.BUTTON1 );
    listener.mouseReleased(e);

    String actual = controller.getOutput();
    String expected =
            "MOUSE DRAG EVENT DETECTED, drawInput was called properly.\n"
                    + "MOUSE DRAG EVENT DETECTED, drawInput was called properly.\n"
                    + "MOUSE DRAG EVENT DETECTED, drawInput was called properly.\n"
                    + "MOUSE RELEASE EVENT DETECTED, setCanvasSize() was called properly.\n";

    Assert.assertEquals(expected, actual);

  }

  /**
   * This method tests the mouseReleased and mouseDragged ignore inputs when action is not from
   * left mouse button.
   */
  @Test
  public void wrongMouseButton() {
    controller = new MockController(model);
    listener = new SymbolMouseListener(controller);

    MouseEvent e = new MouseEvent(new Container(),MouseEvent.MOUSE_DRAGGED, 1, 0,
            100, 100, 1, false, MouseEvent.BUTTON2 );
    MouseEvent e2 = new MouseEvent(new Container(),MouseEvent.MOUSE_DRAGGED, 1, 0,
            101, 101, 1, false, MouseEvent.BUTTON3 );
    MouseEvent e3 = new MouseEvent(new Container(),MouseEvent.MOUSE_DRAGGED, 1, 0,
            102, 102, 1, false, MouseEvent.BUTTON2 );
    MouseEvent e4 = new MouseEvent(new Container(),MouseEvent.MOUSE_RELEASED, 1, 0,
            102, 102, 1, false, MouseEvent.BUTTON2 );
    listener.mouseDragged(e);
    listener.mouseDragged(e2);
    listener.mouseDragged(e3);
    listener.mouseReleased(e4);

    Assert.assertEquals("", controller.getOutput());
  }
}