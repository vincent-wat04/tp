package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class CompanyContainsPredicateTest {

    @Test
    public void equals() {
        CompanyContainsPredicate firstPredicate = new CompanyContainsPredicate("Google");
        CompanyContainsPredicate secondPredicate = new CompanyContainsPredicate("Microsoft");

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CompanyContainsPredicate firstPredicateCopy = new CompanyContainsPredicate("Google");
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different company name -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_companyContainsKeyword_returnsTrue() {
        // Exact match
        CompanyContainsPredicate predicate = new CompanyContainsPredicate("Google");
        assertTrue(predicate.test(new PersonBuilder().withCompany("Google").build()));

        // Mixed-case
        predicate = new CompanyContainsPredicate("gOOgle");
        assertTrue(predicate.test(new PersonBuilder().withCompany("Google").build()));

        // Partial match with word boundary
        predicate = new CompanyContainsPredicate("Google");
        assertTrue(predicate.test(new PersonBuilder().withCompany("Google Inc").build()));
    }

    @Test
    public void test_companyDoesNotContainKeyword_returnsFalse() {
        // Non-matching keyword
        CompanyContainsPredicate predicate = new CompanyContainsPredicate("Microsoft");
        assertFalse(predicate.test(new PersonBuilder().withCompany("Google").build()));

        // Empty company when searching for non-empty
        predicate = new CompanyContainsPredicate("Google");
        assertFalse(predicate.test(new PersonBuilder().withCompany("").build()));

        // Keyword matches part of word but not full word
        predicate = new CompanyContainsPredicate("Goo");
        assertFalse(predicate.test(new PersonBuilder().withCompany("Google").build()));
    }

    @Test
    public void test_emptyCompanySearch_returnsTrue() {
        // Search for empty company - should match persons with no company
        CompanyContainsPredicate predicate = new CompanyContainsPredicate("");
        assertTrue(predicate.test(new PersonBuilder().withCompany("").build()));

        // Should not match persons with a company
        assertFalse(predicate.test(new PersonBuilder().withCompany("Google").build()));
    }

    @Test
    public void toStringMethod() {
        CompanyContainsPredicate predicate = new CompanyContainsPredicate("Google");
        String expected = CompanyContainsPredicate.class.getCanonicalName() + "{companyName=Google}";
        assertEquals(expected, predicate.toString());
    }
}

