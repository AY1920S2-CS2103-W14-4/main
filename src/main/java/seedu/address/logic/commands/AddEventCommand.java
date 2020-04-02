package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.person.ActivityList;
import seedu.address.model.person.Person;
import seedu.address.model.person.PlaceList;
import seedu.address.model.person.Time;

/**
 * Represents the command to add a new event to CoderLifeInsights.
 */
public class AddEventCommand extends Command {

    public static final String COMMAND_WORD = "add_event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates an event with a group or an individual"
            + "that adds an activity, place and time to the subject. \n"
            + "Parameters: [ACTIVITY] "
            + "["
            + PREFIX_PLACE
            + "PLACE] "
            + "["
            + PREFIX_MEMBER
            + "/"
            + PREFIX_GROUP
            + "INDEX] "
            + "["
            + PREFIX_TIME
            + "TIME] \n"
            + "Example: "
            + COMMAND_WORD
            + " Dancing "
            + PREFIX_MEMBER
            + "1 "
            + PREFIX_PLACE
            + "SCAPE "
            + PREFIX_TIME
            + "300";

    public static final String MESSAGE_INVALID_TIME_INPUT = "Time parameter needs to be at least 2 digits.\n"
            + "For example: "
            + "[5 minutes = 05]; "
            + "[1 hour = 100]; "
            + "[10 hours and 30 minutes = 1030]";
    public static final String MESSAGE_SUCCESS = "New event successfully added: %1$s";
    public static final String MESSAGE_DUPLICATE_GROUP = "Event with given arguments already exists. Please try again."
    public static final String MESSAGE_ARGUMENTS = "Activity: %1$s, Index: %2$d, Place: %3$s, Time: %4$s";

    private final Event toAdd;

    public AddEventCommand(Event event) {
        requireAllNonNull(event);
        this.toAdd = event;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index - 1 >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToEdit = lastShownList.get(index - 1);

        Time current = personToEdit.getTime();
        Time newTime = current.addTime(time.getMinutes(), time.getHours());

        PlaceList currentPlaceList = personToEdit.getPlaceList2();
        PlaceList newPlaceList = currentPlaceList.addPlace(place);

        ActivityList currentActivityList = personToEdit.getActivityList2();
        ActivityList newActivityList = currentActivityList.addActivity(activity);

        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getTags(), newTime, newPlaceList, newActivityList);
        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_SUCCESS, personToEdit));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddEventCommand)) {
            return false;
        }

        // state check
        AddEventCommand e = (AddEventCommand) other;
        return index == (e.index)
                && activity.equals(e.activity) && place.equals(e.place) && time.equals(e.time);
    }
}
