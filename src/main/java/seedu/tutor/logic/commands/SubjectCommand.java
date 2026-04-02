package seedu.tutor.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tutor.logic.parser.CliSyntax.PREFIX_CHANGE_SUBJECT;

import seedu.tutor.logic.commands.exceptions.CommandException;
import seedu.tutor.model.Model;
import seedu.tutor.model.label.Label;

/**
 * Changes subject field of a particular person or, change or delete a particular subject across all person
 */
public class SubjectCommand extends Command {

    public static final String COMMAND_WORD = "subject";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " edit subject/s for a particular person "
            + "or delete a particular subject across all person "
            + "or change a particular subject across all person.\n"
            + "Parameters: "
            + "[" + PREFIX_CHANGE_SUBJECT + "OLD_SUBJECT/NEW_SUBJECT]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CHANGE_SUBJECT + "Math/AddMath";

    /**
     * Types of SubjectCommand
     */
    public enum SubjectCommandType {
        EDIT, DELETE, CHANGE
    }

    private final SubjectCommandType type;
    private final Label[] subjects;

    /**
     * Returns a SubjectCommand object that changes the subject fields.
     * @param type Type of the SubjectCommand.
     * @param subjects Subjects in Label type.
     */
    public SubjectCommand(SubjectCommandType type, Label[] subjects) {
        this.type = type;
        this.subjects = subjects;
    }

    private Command getCommand() throws CommandException {
        switch (this.type) {

        case CHANGE:
            if (this.subjects.length != 2) {
                throw new CommandException("Input error: there should only have two subjects");
            }
            return new ChangeSubjectCommand(subjects[0], subjects[1]);

        case EDIT: return null;

        case DELETE: return null;

        default:
            // should not reach here
            return null;
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Command command = this.getCommand();
        requireNonNull(command);
        return command.execute(model);
    }
}
