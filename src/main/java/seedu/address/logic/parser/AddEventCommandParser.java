package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Time;

/**
 * Parses input arguments and creates a new {@code AddEventCommand object}
 */
public class AddEventCommandParser implements Parser<AddEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code AddEventCommand}
     * and returns a {@code AddEventCommand} object for execution
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AddEventCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MEMBER, PREFIX_GROUP, PREFIX_TIME,
                PREFIX_PLACE);
        int index;
        if (argMultimap.getValue(PREFIX_MEMBER).isEmpty()) {
            index = Integer.parseInt(argMultimap.getValue(PREFIX_GROUP).get());
        } else {
            index = Integer.parseInt(argMultimap.getValue(PREFIX_MEMBER).get());
        }

        char[] time = argMultimap.getValue(PREFIX_TIME).get().toCharArray();
        String mins = "";
        String hours = "";
        if (time.length < 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_ARGUMENTS));
        } else {
            int marker = time.length - 2;
            for (int i = marker; i < time.length; i++) {
                mins += time[i];
            }
            for (int i = 0; i < marker; i++) {
                hours += time[i];
            }
            if (hours.equals("")) {
                hours += "0";
            }
        }
        String activity = argMultimap.getPreamble();
        String place = argMultimap.getValue(PREFIX_PLACE).get();


        return new AddEventCommand(activity, index, place, new Time(Integer.parseInt(mins), Integer.parseInt(hours)));
    }
}

