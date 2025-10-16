package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.TagRegistry;

/**
 * Unit tests for {@link AddTagCommand}.
 */
public class AddTagCommandTest {

    @Test
    public void constructor_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddTagCommand(null));
    }

    @Test
    public void execute_validNewTag_addsAndReturnsMessage() throws Exception {
        ModelStubWithTagRegistry modelStub = new ModelStubWithTagRegistry(Set.of("friend"));

        AddTagCommand cmd = new AddTagCommand("colleague");
        CommandResult result = cmd.execute(modelStub);

        assertEquals(String.format(AddTagCommand.MESSAGE_SUCCESS, "colleague"),
                result.getFeedbackToUser());
        // sanity: registry updated
        assertEquals(true, modelStub.getTagRegistry().isAllowed("colleague"));
    }

    @Test
    public void execute_duplicateTag_throwsCommandException() {
        ModelStubWithTagRegistry modelStub = new ModelStubWithTagRegistry(Set.of("friend"));

        AddTagCommand duplicate = new AddTagCommand("friend");
        assertThrows(CommandException.class, () -> duplicate.execute(modelStub));
    }

    @Test
    public void execute_invalidTag_throwsCommandException() {
        ModelStubWithTagRegistry modelStub = new ModelStubWithTagRegistry(Set.of("friend"));

        // non-alphanumeric -> invalid per Tag.MESSAGE_CONSTRAINTS / Tag.isValidTagName
        AddTagCommand invalid = new AddTagCommand("bad!tag");
        assertThrows(CommandException.class, () -> invalid.execute(modelStub));
    }

    @Test
    public void equals() {
        AddTagCommand a = new AddTagCommand("friends");
        AddTagCommand aCopy = new AddTagCommand("friends");
        AddTagCommand b = new AddTagCommand("colleague");

        // same object
        org.junit.jupiter.api.Assertions.assertTrue(a.equals(a));
        // same value
        org.junit.jupiter.api.Assertions.assertTrue(a.equals(aCopy));
        // different value
        org.junit.jupiter.api.Assertions.assertFalse(a.equals(b));
        // null
        org.junit.jupiter.api.Assertions.assertFalse(a.equals(null));
        // different type
        org.junit.jupiter.api.Assertions.assertFalse(a.equals(1));
    }

    /**
     * Minimal Model stub that provides a mutable TagRegistry for testing AddTagCommand.
     * Unused methods throw AssertionError to catch accidental calls.
     */
    private static class ModelStubWithTagRegistry implements Model {
        private final TagRegistry registry;

        ModelStubWithTagRegistry(Set<String> initialAllowed) {
            // use a mutable set to emulate runtime growth
            this.registry = new TagRegistry(new HashSet<>(initialAllowed));
        }

        // ----- methods used by AddTagCommand -----

        @Override
        public TagRegistry getTagRegistry() {
            return registry;
        }

        @Override
        public void addAllowedTag(String tagName) {
            registry.add(tagName);
        }

        // ----- unused Model methods (fail fast if called) -----

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            // many AB-3 tests return a fresh AddressBook() here
            return new AddressBook();
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }
}
