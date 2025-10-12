package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.scene.control.Label;
import seedu.address.model.person.NextMeeting;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

class PersonCardTest {

    @BeforeAll
    static void initJavaFxToolkit() {
        try {
            Platform.startup(() -> { });
        } catch (IllegalStateException ignored) {
            // Toolkit already initialized. Nothing to do.
        }
    }

    @Test
    void formatNextMeetingLabel_validMeeting_returnsFormattedString() {
        NextMeeting meeting = new NextMeeting("Demo day on Friday");
        String result = PersonCard.formatNextMeetingLabel(meeting);
        assertEquals("Next meeting: Demo day on Friday", result);
    }

    @Test
    void formatNextMeetingLabel_defaultMeeting_returnsFormattedString() {
        NextMeeting meeting = new NextMeeting(NextMeeting.DEFAULT_VALUE);
        String result = PersonCard.formatNextMeetingLabel(meeting);
        assertEquals("Next meeting: No meeting scheduled", result);
    }

    @Test
    void formatNextMeetingLabel_variousMeetings_returnsFormattedString() {
        // Test various meeting scenarios
        assertEquals("Next meeting: Project sync on Monday",
                PersonCard.formatNextMeetingLabel(new NextMeeting("Project sync on Monday")));
        assertEquals("Next meeting: Client call at 3pm",
                PersonCard.formatNextMeetingLabel(new NextMeeting("Client call at 3pm")));
        assertEquals("Next meeting: Team standup tomorrow",
                PersonCard.formatNextMeetingLabel(new NextMeeting("Team standup tomorrow")));
    }

    @Test
    void getNextMeetingText_validPerson_returnsFormattedString() {
        Person person = new PersonBuilder().withNextMeeting("Demo day on Friday").build();
        String result = PersonCard.getNextMeetingText(person);
        assertEquals("Next meeting: Demo day on Friday", result);
    }

    @Test
    void getNextMeetingText_personWithDefaultMeeting_returnsFormattedString() {
        Person person = new PersonBuilder().withNextMeeting(NextMeeting.DEFAULT_VALUE).build();
        String result = PersonCard.getNextMeetingText(person);
        assertEquals("Next meeting: No meeting scheduled", result);
    }

    @Test
    void getNextMeetingText_variousPersons_returnsCorrectText() {
        Person person1 = new PersonBuilder().withNextMeeting("Client call at 3pm").build();
        assertEquals("Next meeting: Client call at 3pm", PersonCard.getNextMeetingText(person1));

        Person person2 = new PersonBuilder().withNextMeeting("Team standup tomorrow").build();
        assertEquals("Next meeting: Team standup tomorrow", PersonCard.getNextMeetingText(person2));
    }

    @Test
    void constructor_populatesNextMeetingLabel() throws Exception {
        Person person = new PersonBuilder().withNextMeeting("Demo day on Friday").build();
        runOnFxThreadAndWait(() -> {
            PersonCard personCard = new PersonCard(person, 1);
            Label nextMeetingLabel = (Label) personCard.getRoot().lookup("#nextMeeting");
            assertNotNull(nextMeetingLabel, "Unable to find nextMeeting label in loaded FXML.");
            assertEquals("Next meeting: Demo day on Friday", nextMeetingLabel.getText());
        });
    }

    private void runOnFxThreadAndWait(ThrowingRunnable action) throws Exception {
        FutureTask<Void> task = new FutureTask<>(() -> {
            action.run();
            return null;
        });
        Platform.runLater(task);
        try {
            task.get(Duration.ofSeconds(5).toMillis(), TimeUnit.MILLISECONDS);
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof Error) {
                throw (Error) cause;
            }
            if (cause instanceof Exception) {
                throw (Exception) cause;
            }
            throw new RuntimeException(cause);
        }
    }

    @FunctionalInterface
    private interface ThrowingRunnable {
        void run() throws Exception;
    }
}
