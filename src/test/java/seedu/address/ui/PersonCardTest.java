package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;

import javafx.application.Platform;
import seedu.address.testutil.PersonBuilder;

class PersonCardTest {

    private static final String SAMPLE_MEETING = "Demo day on Friday";

    @BeforeAll
    static void initToolkit() throws Exception {
        System.setProperty("javafx.platform", "Monocle");
        System.setProperty("glass.platform", "Monocle");
        System.setProperty("monocle.platform", "Headless");
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");

        FxToolkit.registerPrimaryStage();
        FxToolkit.setupFixture(() -> { /* ensure toolkit initialised */ });
    }

    @Test
    void display_nextMeetingLabelShowsFormattedValue() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<String> observedText = new AtomicReference<>();
        Platform.runLater(() -> {
            PersonCard card = new PersonCard(new PersonBuilder().withNextMeeting(SAMPLE_MEETING).build(), 1);
            observedText.set(card.getNextMeetingLabelText());
            latch.countDown();
        });
        assertTrue(latch.await(5, TimeUnit.SECONDS));
        assertEquals("Next meeting: " + SAMPLE_MEETING, observedText.get());
    }
}
