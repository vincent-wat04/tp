package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.NextMeeting;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label company;
    @FXML
    private Label nextMeeting;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        renderCompany(person.getCompany().value);
        nextMeeting.setText(getNextMeetingText(person));
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    /**
     * Gets the formatted next meeting text for the given person.
     * This method encapsulates the logic for displaying next meeting information.
     * Package-private for testing.
     */
    static String getNextMeetingText(Person person) {
        return formatNextMeetingLabel(person.getNextMeeting());
    }

    /**
     * Formats a NextMeeting object into a display string.
     * Package-private for testing.
     */
    static String formatNextMeetingLabel(NextMeeting nextMeeting) {
        return "Next meeting: " + nextMeeting.value;
    }

    /**
     * Determines if the company label should be shown.
     * Package-private for testing.
     */
    static boolean shouldShowCompany(String companyValue) {
        return companyValue != null && !companyValue.trim().isEmpty();
    }

    private void renderCompany(String companyValue) {
        boolean show = shouldShowCompany(companyValue);
        if (show) {
            company.setText("Company: " + companyValue);
            company.setVisible(true);
            company.setManaged(true);
        } else {
            company.setText("");
            company.setVisible(false);
            company.setManaged(false); // do not occupy space when hidden
        }
    }
}
