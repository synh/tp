package seedu.tutor.logic.commands;

import static java.util.Objects.requireNonNull;

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

    private final Label oldSubject;
    private final Label newSubject;
    private final EditCommandParser parser = new EditCommandParser();

    /**
     * Returns a Command object that changes a particular subject across the whole list.
     * @param oldSubject The name of the subject to be changed.
     * @param newSubject The name of the subject to be added.
     */
    ChangeSubjectCommand(Label oldSubject, Label newSubject) throws CommandException {
        this.oldSubject = oldSubject;
        this.newSubject = newSubject;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        requireNonNull(model);
        List<Person> persons = model.getTutorMap().getPersonList();
        List<Command> editCommands = new ArrayList<>();

        for (int index = 0; index < persons.size(); index++) {
            Person person = persons.get(index);
            Set<Label> subjects = new HashSet<>(person.getSubjects());
            if (subjects.contains(oldSubject)) {
                subjects.remove(oldSubject);
                subjects.add(newSubject);
                Set<String> args = new HashSet<>();
                for (Label label: subjects) {
                    args.add(label.labelName);
                }
                StringBuilder input = new StringBuilder(" " + (index + 1));
                for (String subject: args) {
                    input.append(" s/");
                    input.append(subject);
                }
                EditCommand editCommand;
                try {
                    System.out.println(input);
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

        if (commandResult == null) {
            throw new CommandException("Subject does not exist");
        } else {
            return commandResult;
        }
    }
}
