package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String KEYWORD_PLACE = "places";
    public static final String KEYWORD_ACTIVITY = "activities";
    public static final String KEYWORD_TIME = "time";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays insights about the places visited, "
            + "activities done, or time spent with the person identified "
            + "by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) [INSIGHT_PARAMETER]\n"
            + "[INSIGHT_PARAMETER] can be [" + KEYWORD_PLACE + "], [" + KEYWORD_ACTIVITY
            + "], or [" + KEYWORD_TIME + ".\n"
            + "Example: " + COMMAND_WORD + " 1 " + KEYWORD_PLACE;

    public static final String MESSAGE_SUCCESS = "Displayed places visited with ";
    public static final String MESSAGE_INVALID_PARAMETER = "The entered parameter is invalid.";

    private final Index index;
    private final String parameter;

    public ViewCommand(Index index, String parameter) {
        requireNonNull(index);
        requireNonNull(parameter);

        this.index = index;
        this.parameter = parameter;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToView = lastShownList.get(index.getZeroBased());
        return new CommandResult(String.format(MESSAGE_SUCCESS, personToView));
    }
}
