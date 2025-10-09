package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Contains unit tests for {@link TagRegistry}.
 */
public class TagRegistryTest {

    private TagRegistry tagRegistry;

    @BeforeEach
    public void setUp() {
        // Start with a small allow list for each test
        tagRegistry = new TagRegistry(Set.of("friend", "family", "colleague"));
    }

    @Test
    public void constructor_nullSeed_safeInitialization() {
        // Should not throw and result in empty allowed set
        TagRegistry empty = new TagRegistry(null);
        assertTrue(empty.view().isEmpty());
    }

    @Test
    public void isAllowed_presentTag_returnsTrue() {
        assertTrue(tagRegistry.isAllowed("friend"));
    }

    @Test
    public void isAllowed_absentTag_returnsFalse() {
        assertFalse(tagRegistry.isAllowed("mentor"));
    }

    @Test
    public void isAllowed_caseInsensitive_returnsTrue() {
        assertTrue(tagRegistry.isAllowed("Family")); // stored lowercase
    }

    @Test
    public void add_newValidTag_successfullyAdded() {
        assertTrue(tagRegistry.add("mentor"));
        assertTrue(tagRegistry.isAllowed("mentor"));
    }

    @Test
    public void add_duplicateTag_returnsFalse() {
        assertFalse(tagRegistry.add("friend")); // already exists
    }

    @Test
    public void add_invalidTagFormat_returnsFalse() {
        // Contains invalid character '#'
        assertFalse(tagRegistry.add("friend#"));
        assertFalse(tagRegistry.isAllowed("friend#"));
    }

    @Test
    public void remove_existingTag_successfullyRemoved() {
        assertTrue(tagRegistry.remove("friend"));
        assertFalse(tagRegistry.isAllowed("friend"));
    }

    @Test
    public void remove_nonExistingTag_returnsFalse() {
        assertFalse(tagRegistry.remove("nonexistent"));
    }

    @Test
    public void view_returnsUnmodifiableSet() {
        Set<String> view = tagRegistry.view();
        assertEquals(Set.of("friend", "family", "colleague"), view);

        // ensure defensive copy (mutating should throw)
        assertNotSame(view, tagRegistry.view());
        assertThrows(UnsupportedOperationException.class, () -> view.add("newtag"));
    }

    private static <T extends Throwable> void assertThrows(Class<T> expected, Runnable action) {
        try {
            action.run();
        } catch (Throwable actual) {
            if (expected.isInstance(actual)) {
                return;
            }
            throw new AssertionError("Expected " + expected.getName() + " but got " + actual.getClass().getName(), actual);
        }
        throw new AssertionError("Expected " + expected.getName() + " but no exception thrown");
    }
}
