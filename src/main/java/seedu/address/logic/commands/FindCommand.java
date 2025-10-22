package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Finds and lists all persons that satisfy the provided predicate.
 * <p>
 * Backward compatible with name-keyword search, and also supports exact tag
 * filtering via {@code t/TAG}. When both keywords and tags are supplied,
 * results must match both (logical AND). Multiple {@code t/} values are AND-ed.
 * <p>
 * If any requested tag is not in the allowed registry, the command shows a warning
 * and does not execute the search.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds persons by name keywords and/or tags.\n"
            + "Name match: case-insensitive contains.\n"
            + "Tag match: exact (case-sensitive). Multiple t/ are AND-ed.\n"
            + "Parameters: [KEYWORD [MORE_KEYWORDS]...] [t/TAG [t/TAG]...] [c/COMPANY...]\n"
            + "Examples:\n"
            + "  " + COMMAND_WORD + " alice bob\n"
            + "  " + COMMAND_WORD + " t/client t/vip\n"
            + "  " + COMMAND_WORD + " alice t/priority";

    /** Warning when filtering by tags that are not allowed by the registry. */
    public static final String MESSAGE_INVALID_FIND_TAG =
            "Cannot filter by invalid tag(s): %1$s. Allowed tags: %2$s";

    private final Predicate<Person> predicate;

    /** Raw tag names requested by the user (for registry validation & messaging). */
    private final List<String> requestedTags;

    /** Name-only / company-only constructor (no tag validation needed). */
    public FindCommand(Predicate<Person> predicate) {
        this(predicate, List.of());
    }

    /** Full constructor accepting a predicate and the raw tag names (may be empty). */
    public FindCommand(Predicate<Person> predicate, List<String> requestedTags) {
        this.predicate = requireNonNull(predicate);
        this.requestedTags = List.copyOf(Objects.requireNonNullElseGet(requestedTags, List::of));
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // If user supplied tags, validate them against the allowed registry.
        if (!requestedTags.isEmpty()) {
            List<String> invalid = new ArrayList<>();
            for (String tagName : requestedTags) {
                if (!model.getTagRegistry().isAllowed(tagName)) {
                    invalid.add(tagName);
                }
            }

            if (!invalid.isEmpty()) {
                Set<String> allowed = model.getTagRegistry().view();
                // Do NOT update the filtered list; just warn.
                throw new CommandException(String.format(MESSAGE_INVALID_FIND_TAG,
                        String.join(", ", invalid), allowed));
            }
        }

        model.updateFilteredPersonList(predicate);
        return new CommandResult(
            seedu.address.logic.Messages.getMessageForPersonListShownSummary(
                model.getFilteredPersonList().size()
            )
        );
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof FindCommand)
                && predicate.equals(((FindCommand) other).predicate)
                && requestedTags.equals(((FindCommand) other).requestedTags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .add("requestedTags", requestedTags)
                .toString();
    }
}
