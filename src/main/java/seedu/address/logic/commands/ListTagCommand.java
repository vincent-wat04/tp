package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Lists all unique tags in the address book.
 * <p>
 * This command provides users with a quick overview of all tags currently
 * associated with contacts
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
    public static final String MESSAGE_HEADER = "Tags: ";

    /**
     * Executes the command by collecting all unique tags from the current model
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
        var registryView = model.getTagRegistry().view(); // Set<String>
        if (registryView.isEmpty()) {
            return new CommandResult(MESSAGE_NO_TAGS);
        }

        // Alphabetical, case-insensitive
        var sorted = new java.util.ArrayList<>(registryView);
        sorted.sort(String.CASE_INSENSITIVE_ORDER);

        String tagLine = String.join(", ", sorted);
        return new CommandResult(MESSAGE_HEADER + tagLine);
    }

    @Override
    public boolean equals(Object other) {
        return other == this || other instanceof ListTagCommand;
    }
}
