package seedu.tutor.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tutor.logic.parser.CliSyntax.PREFIX_NEW_SUBJECT;
import static seedu.tutor.logic.parser.CliSyntax.PREFIX_OLD_SUBJECT;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.tutor.logic.commands.exceptions.CommandException;
import seedu.tutor.logic.parser.EditCommandParser;
import seedu.tutor.logic.parser.exceptions.ParseException;
import seedu.tutor.model.Model;
import seedu.tutor.model.label.Label;
import seedu.tutor.model.person.Person;

/**
 * Change a subject across the whole list.
 */
public class ChangeSubjectCommand extends Command {

    public static final String COMMAND_WORD = "changeSubject";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "Changes a particular subject across all person.\n"
            + "Parameters: "
            + "[" + PREFIX_OLD_SUBJECT + "SUBJECT_TO_BE_CHANGED] "
            + "[" + PREFIX_NEW_SUBJECT + "NEW_SUBJECT]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_OLD_SUBJECT + "Math " + PREFIX_NEW_SUBJECT + "A-Math";

    private final Label oldSubject;
    private final Label newSubject;
    private final EditCommandParser parser = new EditCommandParser();

    /**
     * Returns a Command object that changes a particular subject across the whole list.
     * @param oldSubject The name of the subject to be changed.
     * @param newSubject The name of the subject to be added.
     */
    public ChangeSubjectCommand(String oldSubject, String newSubject) {
        this.oldSubject = new Label(oldSubject);
        this.newSubject = new Label(newSubject);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        requireNonNull(model);
        List<Person> persons = model.getTutorMap().getPersonList();
        List<Command> editCommands = new ArrayList<>();

        for (int index = 0; index < persons.size(); index++) {
            Person person = persons.get(index);
            Set<Label> subjects = person.getSubjects();
            if (subjects.contains(oldSubject)) {
                subjects.remove(oldSubject);
                subjects.add(newSubject);
                Set<String> args = new HashSet<>();
                for (Label label: subjects) {
                    args.add(label.labelName);
                }
                StringBuilder input = new StringBuilder(" " + (index + 1) + "s/");
                for (String subject: args) {
                    input.append(subject);
                    input.append(" ");
                }
                EditCommand editCommand;
                try {
                    editCommand = parser.parse(input.toString());
                } catch (ParseException pe) {
                    throw new CommandException("Unknown error, by ChangeSubjectCommand");
                }
                editCommands.add(editCommand);
            }
        }

        CommandResult commandResult = null;
        for (Command editCommand: editCommands) {
            CommandResult temp = editCommand.execute(model);
            commandResult = CommandResult.merge(commandResult, temp);
        }
        return commandResult;
    }
}
