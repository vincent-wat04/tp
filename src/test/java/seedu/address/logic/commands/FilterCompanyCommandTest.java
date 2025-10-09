package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.CompanyContainsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FilterCompanyCommand}.
 */
public class FilterCompanyCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        CompanyContainsPredicate firstPredicate = new CompanyContainsPredicate("Google");
        CompanyContainsPredicate secondPredicate = new CompanyContainsPredicate("Microsoft");

        FilterCompanyCommand filterFirstCommand = new FilterCompanyCommand(firstPredicate);
        FilterCompanyCommand filterSecondCommand = new FilterCompanyCommand(secondPredicate);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterCompanyCommand filterFirstCommandCopy = new FilterCompanyCommand(firstPredicate);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different company -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void execute_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        CompanyContainsPredicate predicate = new CompanyContainsPredicate("NonexistentCompany");
        FilterCompanyCommand command = new FilterCompanyCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        CompanyContainsPredicate predicate = new CompanyContainsPredicate("Google");
        FilterCompanyCommand command = new FilterCompanyCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, CARL), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        CompanyContainsPredicate predicate = new CompanyContainsPredicate("Google");
        FilterCompanyCommand filterCompanyCommand = new FilterCompanyCommand(predicate);
        String expected = FilterCompanyCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, filterCompanyCommand.toString());
    }
}

