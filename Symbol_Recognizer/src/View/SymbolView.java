package View;

import java.awt.Dimension;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * This class represents an implementation of the ISymbolView interface.  It
 * displays the lines drawn by a user via holding down their left mouse button.
 * It will also display all symbols in the Model that are stored.
 */
public class SymbolView extends JFrame implements ISymbolView {
  private JPanel drawPanel;
  Controller.ISymbolController controller;

  public SymbolView(Controller.ISymbolController controller) {
    super();
    this.controller = controller;
    setTitle("SymbolRecognizer");
    setSize(500, 500);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    drawPanel = new SymbolPanel(controller);
    //create a JScrollPane and add the drawPanel to it
    JScrollPane scrolls = new JScrollPane(drawPanel);
    //add the JScrollPane to wherever you would have added the drawPanel
    this.add(scrolls);
    this.add(drawPanel);
    setVisible(true);
  }

  /**
   * Sets the MouseListener for the View.  This determines when a user presses and releases
   * the left mouse button.
   */
  @Override
  public void setMouseListener(MouseListener listener) {
    drawPanel.addMouseListener(listener);
  }

  /**
   * Sets the MouseMotionListener for the View.  This determines when a user drags the mouse
   * while holding the left mouse button.
   */
  @Override
  public void setMouseMotionListener(MouseMotionListener motionListener) {
    drawPanel.addMouseMotionListener(motionListener);
  }

  /**
   * Redraws the View with all of the symbols that are in the current SymbolRecognizer.
   */
  @Override
  public void redrawScene() {
    drawPanel.setPreferredSize(new Dimension(100, 100));
    drawPanel.revalidate();
    drawPanel.repaint();
  }
}
