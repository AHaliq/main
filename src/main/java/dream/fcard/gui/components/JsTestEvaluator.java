package dream.fcard.gui.components;

import java.io.IOException;
import java.util.function.Consumer;

import dream.fcard.model.cards.JavascriptCard;
import dream.fcard.util.JsTestCaseRunner;
import dream.fcard.util.datastructures.Pair;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

/**
 * Controller for the pop up window that connects to a JS card. It will send the result of the user's attempt
 * back to the evaluate function.
 */
public class JsTestEvaluator {
    @FXML
    private TextArea textEditor;
    @FXML
    private Button runCodeButton;
    @FXML
    private TextArea consoleOutput;

    private Consumer<Pair<Integer, Pair<Integer, Integer>>> returnCode;
    private JsTestCaseRunner runner;
    private JavascriptCard card;
    /**
     * Runs the code in the text editor in JavaScript and returns the output.
     * @throws IOException if code cannot be compiled.
     */
    @FXML
    private void runCode() throws IOException {
        String code = textEditor.getText();
        runner = new JsTestCaseRunner(code, card.getBack());
        returnCode.accept(runner.testCode()); // send results back to the card
        consoleOutput.setText(runner.getConsoleDisplay());
    }

    public void setCodeReturner(Consumer<Pair<Integer, Pair<Integer, Integer>>> returnResult) {
        this.returnCode = returnResult;
    }
    public void setCard(JavascriptCard c) {
        this.card = c;
    }
}
