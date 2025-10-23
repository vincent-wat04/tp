package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Lists all unique tags in the address book.
 * <p>
 * This command provides users with a quick overview of all tags currently
 * associated with contacts, displayed in alphabetical order and accompanied
 * by the number of contacts tagged with each tag.
 * <p>
 * Example usage:
 * <pre>
 *     listtag
 * </pre>
 */
public class ListTagCommand extends Command {

    public static final String COMMAND_WORD = "listtag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all unique tags in the address book.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_NO_TAGS = "There are currently no tags.";
    public static final String MESSAGE_HEADER = "Here are all tags in use:\n";

    /**
     * Executes the command by collecting all unique tags from the current model
     * and displaying them in alphabetical order with the number of contacts
     * associated with each tag.
     *
     * @param model The {@code Model} which the command should operate on.
     * @return A {@code CommandResult} containing the formatted list of tags or
     *         a message indicating no tags exist.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        List<Person> persons = model.getFilteredPersonList();
        Map<String, Integer> tagCounts = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        // Count tag occurrences across all persons
        for (Person person : persons) {
            for (Tag tag : person.getTags()) {
                tagCounts.merge(tag.tagName, 1, Integer::sum);
            }
        }

        // Handle empty tag set
        if (tagCounts.isEmpty()) {
            return new CommandResult(MESSAGE_NO_TAGS);
        }

        // Build result string
        StringBuilder sb = new StringBuilder(MESSAGE_HEADER);
        int index = 1;
        for (Map.Entry<String, Integer> entry : tagCounts.entrySet()) {
            sb.append(index++)
                    .append(". ")
                    .append(entry.getKey())
                    .append(" (")
                    .append(entry.getValue())
                    .append(" contacts)");

            if (index <= tagCounts.size()) {
                sb.append("\n");
            }
        }

        return new CommandResult(sb.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this || other instanceof ListTagCommand;
    }
}
