package Controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import Model.Point;

/**
 * This is the SymbolMouseListener class.  It also has a controller that it updates when it
 * recieved certain inputs from MouseDragged and MouseReleased events.
 */
public class SymbolMouseListener extends MouseAdapter {

  private SymbolController controller;


  public SymbolMouseListener(SymbolController controller) {
    super();
    this.controller = controller;
  }

  /**
   * This function adds points to the pointlist for mouse events when the left mouse
   * button is clicked and dragged. It also calls the controller to draw the input as it is
   * added so that input is constantly being redrawn with input.
   *
   * @param e is the mouse event input.
   */
  @Override
  public void mouseDragged(MouseEvent e) {

    if (SwingUtilities.isLeftMouseButton(e)) {
      double x = (double) e.getX();
      double y = (double) e.getY();

      controller.getUserInput().add(new Point(x, y));
      controller.drawInput();

    }
  }

  /**
   * This function calls the model to attempt a draw of any input received so far.
   *
   * @param e is the mouse event input.
   */
  @Override
  public void mouseReleased(MouseEvent e) {
    if (e.getButton() == MouseEvent.BUTTON1) {
      controller.setCanvasSize();
    }
  }
}

