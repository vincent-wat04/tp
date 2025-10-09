package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Finds and lists all persons that satisfy the provided predicate.
 * <p>
 * Backward compatible with name-keyword search, and now also supports exact tag
 * filtering via {@code t/TAG}. When both keywords and tags are supplied,
 * results
 * must match both (logical AND). Multiple {@code t/} values are allowed
 * (logical OR).
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds persons by name keywords and/or tags.\n"
            + "Name match: case-insensitive contains.\n"
            + "Tag match: exact (case-sensitive). Multiple t/ are AND-ed.\n"
            + "Parameters: [KEYWORD [MORE_KEYWORDS]...] [t/TAG [t/TAG]...]\n"
            + "Examples:\n"
            + "  " + COMMAND_WORD + " alice bob\n"
            + "  " + COMMAND_WORD + " t/clients t/vip\n"
            + "  " + COMMAND_WORD + " alice t/priority";

    private final Predicate<Person> predicate;

    public FindCommand(Predicate<Person> predicate) {
        this.predicate = requireNonNull(predicate);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof FindCommand)
                        && predicate.equals(((FindCommand) other).predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
