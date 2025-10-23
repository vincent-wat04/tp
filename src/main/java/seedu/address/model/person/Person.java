package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Company company;
    private final List<Meeting> meetings;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Company company,
            List<Meeting> meetings, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, company, meetings, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.company = company;
        this.meetings = Collections.unmodifiableList(meetings);
        this.tags.addAll(tags);
    }

    /**
     * Constructor for backward compatibility with NextMeeting.
     * Converts NextMeeting to a single Meeting in the meetings list.
     */
    public Person(Name name, Phone phone, Email email, Address address, Company company,
            NextMeeting nextMeeting, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, company, nextMeeting, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.company = company;
        
        // Convert NextMeeting to Meeting list for backward compatibility
        List<Meeting> meetingList = new java.util.ArrayList<>();
        if (!nextMeeting.value.equals(NextMeeting.DEFAULT_VALUE)) {
            // Create a default meeting from NextMeeting string
            // This is a temporary solution until we fully migrate to Meeting objects
            meetingList.add(createMeetingFromNextMeeting(nextMeeting));
        }
        this.meetings = Collections.unmodifiableList(meetingList);
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Company getCompany() {
        return company;
    }

    public List<Meeting> getMeetings() {
        return meetings;
    }

    /**
     * Returns the next upcoming meeting (first non-completed meeting).
     * Returns empty Optional if no upcoming meetings exist.
     */
    public Optional<Meeting> getNextMeeting() {
        assert meetings != null : "Meetings list should not be null";
        return meetings.stream()
                .filter(meeting -> !meeting.isCompleted())
                .findFirst();
    }

    /**
     * Returns all completed meetings.
     */
    public List<Meeting> getCompletedMeetings() {
        assert meetings != null : "Meetings list should not be null";
        return meetings.stream()
                .filter(Meeting::isCompleted)
                .collect(Collectors.toList());
    }

    /**
     * Returns all upcoming meetings (non-completed).
     */
    public List<Meeting> getUpcomingMeetings() {
        assert meetings != null : "Meetings list should not be null";
        return meetings.stream()
                .filter(meeting -> !meeting.isCompleted())
                .collect(Collectors.toList());
    }

    /**
     * Helper method to create a Meeting from NextMeeting for backward compatibility.
     * This is a temporary solution until we fully migrate to Meeting objects.
     */
    private Meeting createMeetingFromNextMeeting(NextMeeting nextMeeting) {
        // Create a basic meeting with the nextMeeting string as title
        // This is a simplified conversion - in a real implementation, you might want
        // to parse the nextMeeting string to extract date/time information
        return new Meeting(
            new seedu.address.model.meeting.MeetingTitle(nextMeeting.value),
            java.time.LocalDate.now().plusDays(1), // Default to tomorrow
            java.time.LocalTime.of(9, 0), // Default to 9:00 AM
            java.time.LocalTime.of(10, 0), // Default to 10:00 AM
            List.of(this.name), // Include this person as participant
            false, // Not completed
            "" // No notes
        );
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && company.equals(otherPerson.company)
                && meetings.equals(otherPerson.meetings)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, company, meetings, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("company", company)
                .add("meetings", meetings)
                .add("tags", tags)
                .toString();
    }

}
