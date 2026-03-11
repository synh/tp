package seedu.tutor.logic.commands;

import seedu.tutor.commons.core.index.Index;
import seedu.tutor.model.Model;
import seedu.tutor.model.relation.Relation;

/**
 * skeleton code for RelateAddCommand
 */
public class RelateAddCommand extends RelateCommand {

    public static final String MESSAGE_SUCCESS = "";

    public RelateAddCommand(Index index, Relation relation) {
        //
    }

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
