package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Company} matches the company name given.
 */
public class CompanyContainsPredicate implements Predicate<Person> {
    private final String companyName;

    public CompanyContainsPredicate(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public boolean test(Person person) {
        if (companyName.isEmpty()) {
            return person.getCompany().value.isEmpty();
        }
        return StringUtil.containsWordIgnoreCase(person.getCompany().value, companyName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CompanyContainsPredicate)) {
            return false;
        }

        CompanyContainsPredicate otherPredicate = (CompanyContainsPredicate) other;
        return companyName.equals(otherPredicate.companyName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("companyName", companyName).toString();
    }
}

