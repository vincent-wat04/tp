package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCompanyCommand;
import seedu.address.model.person.CompanyContainsPredicate;

public class FilterCompanyCommandParserTest {

    private FilterCompanyCommandParser parser = new FilterCompanyCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCompanyCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterCompanyCommand() {
        // no leading and trailing whitespaces
        FilterCompanyCommand expectedCommand =
                new FilterCompanyCommand(new CompanyContainsPredicate("Google"));
        assertParseSuccess(parser, "Google", expectedCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Google \n \t  \t", expectedCommand);
    }

    @Test
    public void parse_companyWithSpaces_returnsFilterCompanyCommand() {
        FilterCompanyCommand expectedCommand =
                new FilterCompanyCommand(new CompanyContainsPredicate("Google Inc"));
        assertParseSuccess(parser, "Google Inc", expectedCommand);
    }
}

