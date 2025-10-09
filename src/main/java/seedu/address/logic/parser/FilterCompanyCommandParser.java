package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.FilterCompanyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.CompanyContainsPredicate;

/**
 * Parses input arguments and creates a new FilterCompanyCommand object
 */
public class FilterCompanyCommandParser implements Parser<FilterCompanyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCompanyCommand
     * and returns a FilterCompanyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCompanyCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCompanyCommand.MESSAGE_USAGE));
        }

        return new FilterCompanyCommand(new CompanyContainsPredicate(trimmedArgs));
    }
}

