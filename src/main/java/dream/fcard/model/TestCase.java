package dream.fcard.model;

import java.io.File;
import java.io.FileNotFoundException;

import dream.fcard.logic.storage.Schema;
import dream.fcard.util.FileReadWrite;
import dream.fcard.util.datastructures.Pair;
import dream.fcard.util.json.JsonInterface;
import dream.fcard.util.json.jsontypes.JsonObject;
import dream.fcard.util.json.jsontypes.JsonValue;

/**
 * An object representing 1 input test case and 1 expected output file.
 */
public class TestCase implements JsonInterface {
    private File input;
    private File expectedOutput;

    public TestCase(File input, File expectedOutput) {
        this.input = input;
        this.expectedOutput = expectedOutput;
    }

    /**
     * Check if the given output matches what was in the given expected output file.
     * @param output generated by a code evaluator.
     * @return an object that says true if test case passed, false otherwise, and the test cases for reviewing.
     * @throws FileNotFoundException
     */
    public Pair<Boolean, Pair<String, String>> checkDiff(String output) throws FileNotFoundException {
        String expected = FileReadWrite.read(expectedOutput.getAbsolutePath());
        Pair<String, String> outputs = new Pair<>(expected, output);
        Pair<Boolean, Pair<String, String>> obj;
        if (!expected.equals(output)) {
            obj = new Pair<>(false, outputs);
        } else {
            obj = new Pair<>(true, outputs);
        }
        return obj;
    }

    public boolean hasMissingInput() {
        return input == null;
    }

    public boolean hasMissingExpectedOutput() {
        return expectedOutput == null;
    }

    @Override
    public JsonValue toJson() {
        JsonObject obj = new JsonObject();
        obj.put(Schema.TESTCASE_INPUT, input.getAbsolutePath());
        obj.put(Schema.TESTCASE_OUTPUT, expectedOutput.getAbsolutePath());
        return new JsonValue(obj);
    }
}
