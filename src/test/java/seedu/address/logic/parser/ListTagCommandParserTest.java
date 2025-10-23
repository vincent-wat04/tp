package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListTagCommand;

/**
 * Contains unit tests for {@link ListTagCommandParser}.
 *
 * <p>This test class ensures that:
 * <ul>
 *     <li>Whitespace-only input is accepted, since {@code listtag} takes no arguments.</li>
 *     <li>Any extra arguments are rejected with a proper usage message.</li>
 * </ul>
 */
public class ListTagCommandParserTest {

    private final ListTagCommandParser parser = new ListTagCommandParser();

    /**
     * Verifies that no-argument or whitespace-only input
     * successfully produces a {@link ListTagCommand}.
     */
    @Test
    public void parse_noArgs_success() {
        assertParseSuccess(parser, "", new ListTagCommand());
        assertParseSuccess(parser, "   ", new ListTagCommand());
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }

    /**
     * Verifies that input containing extra arguments results in a parse failure.
     */
    @Test
    public void parse_withArgs_failure() {
        assertParseFailure(parser, " extra",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ListTagCommand.MESSAGE_USAGE));

        assertParseFailure(parser, " t/friends",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ListTagCommand.MESSAGE_USAGE));
    }
}
