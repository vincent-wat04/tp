package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddTagCommand;

/**
 * Unit tests for {@link AddTagCommandParser}.
 */
public class AddTagCommandParserTest {

    private final AddTagCommandParser parser = new AddTagCommandParser();

    @Test
    public void parse_validArgs_returnsAddTagCommand() {
        assertParseSuccess(parser, "t/friends", new AddTagCommand("friends"));
        assertParseSuccess(parser, "t/friends   ", new AddTagCommand("friends"));
        assertParseSuccess(parser, "   t/friends   ", new AddTagCommand("friends"));
        assertParseSuccess(parser, "\t t/friends\t", new AddTagCommand("friends"));
        assertParseSuccess(parser, "t/FRIENDS", new AddTagCommand("FRIENDS"));
        assertParseSuccess(parser, "  t/FrIeNdS  ", new AddTagCommand("FrIeNdS"));
    }

    @Test
    public void parse_missingPrefix_throwsParseException() {
        assertParseFailure(parser, "friends",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyTag_throwsParseException() {
        assertParseFailure(parser, "t/",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "t/   ",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_withPreamble_throwsParseException() {
        assertParseFailure(parser, "random t/friends",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "hello world t/friends",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "# t/friends",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_onlyWhitespace_throwsParseException() {
        assertParseFailure(parser, "   ",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
    }
}
