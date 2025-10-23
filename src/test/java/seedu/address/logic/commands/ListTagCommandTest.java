package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests for {@link ListTagCommand} using the real {@link ModelManager}.
 *
 * <p>This class verifies the following behaviors:
 * <ul>
 *     <li>The command displays the correct "no tags" message when no persons exist.</li>
 *     <li>The command lists all unique tags alphabetically along with their respective counts.</li>
 *     <li>The {@code equals} method behaves as expected.</li>
 * </ul>
 */
public class ListTagCommandTest {

    private Model model;

    /**
     * Sets up a fresh {@link ModelManager} with an empty address book before each test.
     */
    @BeforeEach
    public void setUp() {
        model = new ModelManager(new AddressBook(), new UserPrefs());
    }

    /**
     * Verifies that when there are no persons or tags in the model,
     * the command returns the {@link ListTagCommand#MESSAGE_NO_TAGS} message.
     */
    @Test
    public void execute_noPersons_showsNoTagsMessage() {
        CommandResult result = new ListTagCommand().execute(model);
        assertEquals(ListTagCommand.MESSAGE_NO_TAGS, result.getFeedbackToUser());
    }

    /**
     * Verifies that when persons with tags exist in the model,
     * the command output lists tags alphabetically with their contact counts.
     */
    @Test
    public void execute_withPersons_showsAlphabeticalTagListWithCounts() {
        // Alice has tags: friends, colleagues
        model.addPerson(new PersonBuilder()
                .withName("Alice Pauline")
                .withTags("friends", "colleagues")
                .build());

        // Bob has tag: friends
        model.addPerson(new PersonBuilder()
                .withName("Bob Choo")
                .withTags("friends")
                .build());

        // Execute listtag command
        CommandResult result = new ListTagCommand().execute(model);
        String output = result.getFeedbackToUser().trim();

        // Verify header appears
        assertTrue(output.startsWith(ListTagCommand.MESSAGE_HEADER.trim()));

        // Verify alphabetical order and counts
        String expectedLine1 = "1. colleagues (1 contacts)";
        String expectedLine2 = "2. friends (2 contacts)";

        int idx1 = output.indexOf(expectedLine1);
        int idx2 = output.indexOf(expectedLine2);
        assertTrue(idx1 >= 0 && idx2 > idx1, "Expected alphabetical order not found.\nOutput:\n" + output);
    }

    /**
     * Verifies that two {@code ListTagCommand} instances are considered equal
     * since the command has no state.
     */
    @Test
    public void equals_sameType_returnsTrue() {
        assertTrue(new ListTagCommand().equals(new ListTagCommand()));
    }
}
