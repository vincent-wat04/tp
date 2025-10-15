package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Tests for {@link FindCommandParser}.
 *
 * Note: For tag-based parsing success cases, predicate equality is difficult to
 * assert without
 * introducing a concrete predicate class. Here we validate:
 * - existing name-only behaviour (success),
 * - empty args (failure),
 * - malformed tag inputs (failure).
 */
public class FindCommandParserTest {

    private final FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand = new FindCommand(
                new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
    }

    /** Empty tag after t/ should fail. */
    @Test
    public void parse_emptyTag_throwsParseException() {
        assertParseFailure(parser, " t/ ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    /** Invalid tag (violates Tag constraints) should fail. */
    @Test
    public void parse_invalidTag_throwsParseException() {
        assertParseFailure(parser, " t/bad tag",
                seedu.address.model.tag.Tag.MESSAGE_CONSTRAINTS);
    }

    /**
     * Multiple tags should be accepted (AND semantics) – at least ensure it parses
     * into a FindCommand.
     */
    @Test
    public void parse_multipleTags_parsesToFindCommand() throws Exception {
        var cmd = parser.parse(" t/clients t/vip");
        assertTrue(cmd instanceof FindCommand);
        // We don't assert predicate equality here because tag predicates are built
        // inline.
    }

    /** Company-only should parse; single c/ */
    @Test
    public void parse_companyOnly_parsesToFindCommand() throws Exception {
        var cmd = parser.parse(" c/Google");
        assertTrue(cmd instanceof FindCommand);
    }

    /** Multiple companies should be OR-combined; ensure it parses. */
    @Test
    public void parse_multipleCompanies_parsesToFindCommand() throws Exception {
        var cmd = parser.parse(" c/Google c/Microsoft");
        assertTrue(cmd instanceof FindCommand);
    }

    /** Name + tag + multiple companies (AND across kinds, OR within c/) */
    @Test
    public void parse_nameTagsAndCompanies_parsesToFindCommand() throws Exception {
        var cmd = parser.parse(" Alice t/clients c/Google c/Microsoft");
        assertTrue(cmd instanceof FindCommand);
    }

    /** Empty company value should fail. */
    @Test
    public void parse_emptyCompany_throwsParseException() {
        assertParseFailure(parser, " c/ ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }
}
