package Controller;

import Model.SymbolRecognizer;

/**
 * This is the MockControllerClass that is used for testing the SymbolMouseListener.
 * The functions that do have to be invoked by the listener will instead update the output string
 * in this class and can be returned for JUnit comparisons.
 *
 * Created by Oz on 4/17/2017.
 */
public class MockController extends SymbolController {

  private String output;

  /**
   * Construct this mock controller with given model.
   *
   * @param model for this controller.
   */
  public MockController(SymbolRecognizer model) {
    super(model);
    this.output = "";
  }

  /**
   * Appends dragevent string to current output string.
   */
  @Override
  public void drawInput() {
    output += "MOUSE DRAG EVENT DETECTED, drawInput was called properly.\n";
  }

  /**
   * Appends release event string to current output string.
   */
  @Override
  public void setCanvasSize() {
    output += "MOUSE RELEASE EVENT DETECTED, setCanvasSize() was called properly.\n";
  }


  /**
   * Gets the output of this mockcontroller.
   *
   * @return String output.
   */
  public String getOutput() {
    return output;
  }


}
