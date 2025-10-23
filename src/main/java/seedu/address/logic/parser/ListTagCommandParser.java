package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ListTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code ListTagCommand} object.
 * <p>
 * The {@code listtag} command takes no arguments — any additional input
 * will result in a {@link ParseException}.
 */
public class ListTagCommandParser implements Parser<ListTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * {@code ListTagCommand} and returns a {@code ListTagCommand} object
     * for execution.
     *
     * @param args The user input string.
     * @return A new {@code ListTagCommand}.
     * @throws ParseException If the user input contains unexpected arguments.
     */
    @Override
    public ListTagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();

        if (!trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, ListTagCommand.MESSAGE_USAGE));
        }

        return new ListTagCommand();
    }
}
