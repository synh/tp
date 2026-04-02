package seedu.tutor.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.tutor.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tutor.logic.parser.CliSyntax.PREFIX_CHANGE_SUBJECT;
import static seedu.tutor.logic.parser.CliSyntax.PREFIX_DELETE_SUBJECT;
import static seedu.tutor.logic.parser.CliSyntax.PREFIX_EDIT_SUBJECT;

import seedu.tutor.logic.commands.SubjectCommand;
import seedu.tutor.logic.parser.exceptions.ParseException;
import seedu.tutor.model.label.Label;

/**
 * Parses input arguments and returns a new SubjectCommand object
 */
public class SubjectCommandParser implements Parser<SubjectCommand> {

    private static final String SUBJECT_NAME_ERROR = "Subject name should be alphanumerical.\n";

    /**
     * Parses the given {@code String} of arguments in the context of the SubjectCommand
     * and returns a SubjectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SubjectCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_EDIT_SUBJECT, PREFIX_CHANGE_SUBJECT,
                PREFIX_DELETE_SUBJECT);

        int argumentCount = argMultimap.getAllValues(PREFIX_CHANGE_SUBJECT).size()
                + argMultimap.getAllValues(PREFIX_DELETE_SUBJECT).size()
                + argMultimap.getAllValues(PREFIX_EDIT_SUBJECT).size();

        if (argumentCount != 1) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT + SubjectCommand.MESSAGE_USAGE);
        }

        if (argMultimap.getValue(PREFIX_CHANGE_SUBJECT).isPresent()) {
            String temp0 = argMultimap.getValue(PREFIX_CHANGE_SUBJECT).get();
            String[] temp1 = temp0.split("/");
            if (temp1.length != 2 || temp0.endsWith("/")) {
                throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT + SubjectCommand.MESSAGE_USAGE);
            }
            Label[] labels = new Label[2];
            try {
                labels[0] = ParserUtil.parseTag(temp1[0]);
                labels[1] = ParserUtil.parseTag(temp1[1]);
            } catch (ParseException pe) {
                throw new ParseException(SUBJECT_NAME_ERROR);
            }
            return new SubjectCommand(SubjectCommand.SubjectCommandType.CHANGE, labels);
        }

        // should not reach here
        return null;
    }
}
