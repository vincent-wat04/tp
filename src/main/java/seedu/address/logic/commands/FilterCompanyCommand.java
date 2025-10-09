package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.CompanyContainsPredicate;

/**
 * Filters and lists all persons in address book whose company matches the specified company name.
 * Company name matching is case insensitive.
 */
public class FilterCompanyCommand extends Command {

    public static final String COMMAND_WORD = "filtercompany";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters all persons whose company name contains "
            + "the specified company name (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: COMPANY_NAME\n"
            + "Example: " + COMMAND_WORD + " Google";

    private final CompanyContainsPredicate predicate;

    public FilterCompanyCommand(CompanyContainsPredicate predicate) {
        this.predicate = predicate;
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
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilterCompanyCommand)) {
            return false;
        }

        FilterCompanyCommand otherCommand = (FilterCompanyCommand) other;
        return predicate.equals(otherCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}

