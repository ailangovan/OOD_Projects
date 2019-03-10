import Controller.ISymbolController;
import Controller.SymbolController;
import Model.SymbolRecognizer;
import Model.SymbolRecognizerImpl;
import View.ISymbolView;
import View.SymbolView;

/**
 * This class is used to run the SymbolRecognizer.  It combines the Model with the View
 * and the Controller.
 */
public class SymbolRecognizerMain {
  public static void main(String[] args) {
    SymbolRecognizer model = new SymbolRecognizerImpl();
    ISymbolController controller = new SymbolController(model);
    ISymbolView view = new SymbolView(controller);
    controller.setView(view);
  }
}
