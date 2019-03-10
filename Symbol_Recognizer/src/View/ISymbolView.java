package View;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * This interface contains all available methods from a SymbolRecognizer's view.
 */
public interface ISymbolView {
  /**
   * Sets the MouseListener for the View.  This determines when a user presses and releases
   * the left mouse button.
   * @param listener
   */
  void setMouseListener(MouseListener listener);

  /**
   * Sets the MouseMotionListener for the View.  This determines when a user drags the mouse
   * while holding the left mouse button.
   * @param motionListener
   */
  void setMouseMotionListener(MouseMotionListener motionListener);

  /**
   * Redraws the View with all of the symbols that are in the current SymbolRecognizer, as
   * well as the userInput as it's happening.
   */
  void redrawScene();
}
