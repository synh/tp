package seedu.tutor.logic.parser;

import org.junit.jupiter.api.Test;
import seedu.tutor.logic.commands.RelateAddCommand;
import seedu.tutor.logic.commands.RelateCommand;
import seedu.tutor.logic.commands.RelateDeleteCommand;
import seedu.tutor.logic.parser.exceptions.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class RelateCommandParserTest {

    private final RelateCommandParser parser = new RelateCommandParser();
    String[] success_args = new String[] {"1 a\\test/test/test/test", "1 d\\1/1/1/1",
            "10    d\\   h sdw /  ewd as   qwe/adw  xdww/  dwdsd       qwdqw"};
    String[] failure_args = new String[] {"", "1", "1 a\\test", "1 a\\test/test", "1 a\\test/test/test",
            "1 a\\test/test/test/test/test", "a\\test/test/test/test"};
    String[] extra_args = new String[] {"1 a\\test/test/test/test n/linq", "1 d\\1/1/1/1 p/1234567",
            "10    d\\   h sdw /  ewd as   qwe/adw  xdww/  dwdsd       qwdqw e/"};

    @Test
    public void parser_RelateCommand_success() {
        int passCount = success_args.length;
        int testCount = success_args.length;
        for (String args: success_args) {
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
    public void parser_RelateCommand_failure() {
        int errorCount = failure_args.length;
        int testCount = 0;
        for (String args: failure_args) {
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
    public void RelateCommand_Type_Test() {
        RelateCommand[] results = new RelateCommand[success_args.length];
        for (int i = 0; i < success_args.length; i++) {
            try {
                results[i] = parser.parse(success_args[i]);
            } catch (ParseException pe) {
                assert(false);
            }
        }
        assertFalse(!(results[0] instanceof RelateAddCommand));
        assertFalse(!(results[1] instanceof RelateDeleteCommand));
        assertFalse(!(results[2] instanceof RelateDeleteCommand));
    }

    @Test
    public void extra_args_Test() {
        int errorCount = extra_args.length;
        int testCount = 0;
        for (String args: extra_args) {
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
