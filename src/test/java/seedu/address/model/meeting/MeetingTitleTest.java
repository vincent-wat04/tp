package seedu.address.model.meeting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link MeetingTitle}.
 */
public class MeetingTitleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MeetingTitle(null));
    }

    @Test
    public void constructor_invalid_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new MeetingTitle(""));
        assertThrows(IllegalArgumentException.class, () -> new MeetingTitle("   "));
        String longName = "A".repeat(101);
        assertThrows(IllegalArgumentException.class, () -> new MeetingTitle(longName));
    }

    @Test
    public void constructor_valid_success() {
        MeetingTitle title = new MeetingTitle("Sprint sync");
        assertEquals("Sprint sync", title.toString());
        assertTrue(MeetingTitle.isValidTitle("Sprint sync"));
        assertTrue(MeetingTitle.isValidTitle("One word"));
    }
}
