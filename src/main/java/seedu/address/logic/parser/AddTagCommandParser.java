package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Optional;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code AddTagCommand} object.
 * <p>
 * Expected format: {@code addtag t/TAG}
 */
public class AddTagCommandParser implements Parser<AddTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code AddTagCommand}
     * and returns an {@code AddTagCommand} object for execution.
     *
     * @param args the input arguments after the command word
     * @return the corresponding {@code AddTagCommand}
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public AddTagCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        Optional<String> tagOpt = argMultimap.getValue(PREFIX_TAG);
        if (tagOpt.isEmpty() || !argMultimap.getPreamble().isBlank()) {
            throw new ParseException(String.format(
                    Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE)); // <-- use Messages.*
        }

        String tagName = tagOpt.get().trim();
        return new AddTagCommand(tagName);
    }
}
