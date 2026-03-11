package seedu.tutor.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import seedu.tutor.logic.commands.RelateAddCommand;
import seedu.tutor.logic.commands.RelateCommand;
import seedu.tutor.logic.commands.RelateDeleteCommand;
import seedu.tutor.logic.parser.exceptions.ParseException;

public class RelateCommandParserTest {

    private final RelateCommandParser parser = new RelateCommandParser();
    private final String[] successArgs = new String[] {"1 a\\test/test/test/test", "1 d\\1/1/1/1",
        "10    d\\   h sdw /  ewd as   qwe/adw  xdww/  dwdsd       qwdqw"};
    private final String[] failureArgs = new String[] {"", "1", "1 a\\test", "1 a\\test/test", "1 a\\test/test/test",
        "1 a\\test/test/test/test/test", "a\\test/test/test/test"};
    private final String[] extraArgs = new String[] {"1 a\\test/test/test/test n/linq", "1 d\\1/1/1/1 p/1234567",
        "10    d\\   h sdw /  ewd as   qwe/adw  xdww/  dwdsd       qwdqw e/"};

    @Test
    public void parserRelateCommandSuccess() {
        int passCount = successArgs.length;
        int testCount = successArgs.length;
        for (String args: successArgs) {
            Object o = new Object();
            try {
                o = parser.parse(args);
            } catch (ParseException pe) {
                testCount -= 1;
            }
            if (!(o instanceof RelateCommand)) {
                System.out.println("valid but failed: " + args);
            }
            assertEquals(passCount, testCount);
        }
    }

    @Test
    public void parserRelateCommandFailure() {
        int errorCount = failureArgs.length;
        int testCount = 0;
        for (String args: failureArgs) {
            Object o = new Object();
            try {
                o = parser.parse(args);
            } catch (ParseException pe) {
                testCount += 1;
            }
            if (o instanceof RelateCommand) {
                System.out.println("\ninvalid but passed: " + args);
            }
        }
        assertEquals(errorCount, testCount);
    }

    @Test
    public void relateCommandTypeTest() {
        RelateCommand[] results = new RelateCommand[successArgs.length];
        for (int i = 0; i < successArgs.length; i++) {
            try {
                results[i] = parser.parse(successArgs[i]);
            } catch (ParseException pe) {
                assert(false);
            }
        }
        assertFalse(!(results[0] instanceof RelateAddCommand));
        assertFalse(!(results[1] instanceof RelateDeleteCommand));
        assertFalse(!(results[2] instanceof RelateDeleteCommand));
    }

    @Test
    public void extraArgsTest() {
        int errorCount = extraArgs.length;
        int testCount = 0;
        for (String args: extraArgs) {
            Object o = new Object();
            try {
                o = parser.parse(args);
            } catch (ParseException pe) {
                testCount += 1;
            }
            if (o instanceof RelateCommand) {
                System.out.println("\ninvalid but passed: " + args);
            }
        }
        assertEquals(errorCount, testCount);
    }
}
