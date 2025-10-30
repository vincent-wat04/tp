package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
 *     <li>The command displays the correct "no tags" message when the registry is empty.</li>
 *     <li>The command lists all unique tags alphabetically from the TagRegistry.</li>
 *     <li>The {@code equals} method behaves as expected.</li>
 * </ul>
 */
public class ListTagCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new AddressBook(), new UserPrefs());
    }

    /** Removes default allowed tags from the registry for deterministic testing. */
    private void clearDefaultRegistryTags() {
        var reg = model.getTagRegistry();
        assertNotNull(reg);
        reg.remove("friend");
        reg.remove("family");
        reg.remove("colleague");
        reg.remove("classmate");
        reg.remove("client");
    }

    /** Verifies that when the registry has no tags, the command returns the no-tags message. */
    @Test
    public void execute_noTagsInRegistry_showsNoTagsMessage() {
        clearDefaultRegistryTags();
        CommandResult result = new ListTagCommand().execute(model);
        assertEquals(ListTagCommand.MESSAGE_NO_TAGS, result.getFeedbackToUser());
    }

    /** Verifies that tags from the registry are listed alphabetically in a single line. */
    @Test
    public void execute_withRegistryTags_showsAlphabeticalTagList() {
        clearDefaultRegistryTags();
        model.addAllowedTag("friends");
        model.addAllowedTag("colleagues");

        // Add persons (optional, for integration coverage)
        model.addPerson(new PersonBuilder()
                .withName("Alice Pauline")
                .withTags("friends", "colleagues")
                .build());
        model.addPerson(new PersonBuilder()
                .withName("Bob Choo")
                .withTags("friends")
                .build());

        CommandResult result = new ListTagCommand().execute(model);
        String output = result.getFeedbackToUser().trim();

        assertTrue(output.startsWith(ListTagCommand.MESSAGE_HEADER));
        assertEquals("Tags: colleagues, friends", output);
    }

    /** Verifies that two ListTagCommand instances are equal since the command has no state. */
    @Test
    public void equals_sameType_returnsTrue() {
        assertTrue(new ListTagCommand().equals(new ListTagCommand()));
    }
}
