package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CompanyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Company(null));
    }

    @Test
    public void constructor_invalidCompany_throwsIllegalArgumentException() {
        String invalidCompany = " "; // only whitespace
        assertThrows(IllegalArgumentException.class, () -> new Company(invalidCompany));
    }

    @Test
    public void isValidCompany() {
        // null company
        assertThrows(NullPointerException.class, () -> Company.isValidCompany(null));

        // invalid companies
        assertFalse(Company.isValidCompany(" ")); // only spaces

        // valid companies
        assertTrue(Company.isValidCompany("")); // empty string
        assertTrue(Company.isValidCompany("Google"));
        assertTrue(Company.isValidCompany("Microsoft Corporation"));
        assertTrue(Company.isValidCompany("ABC Inc."));
        assertTrue(Company.isValidCompany("123 Company"));
        assertTrue(Company.isValidCompany("a")); // single character
        assertTrue(Company.isValidCompany("Google LLC")); // with suffix
        assertTrue(Company.isValidCompany("Apple Inc. - Singapore Branch")); // complex name
    }

    @Test
    public void equals() {
        Company company = new Company("Google");

        // same values -> returns true
        assertTrue(company.equals(new Company("Google")));

        // same object -> returns true
        assertTrue(company.equals(company));

        // null -> returns false
        assertFalse(company.equals(null));

        // different types -> returns false
        assertFalse(company.equals(5.0f));

        // different values -> returns false
        assertFalse(company.equals(new Company("Microsoft")));
    }

    @Test
    public void hashCodeTest() {
        Company company1 = new Company("Google");
        Company company2 = new Company("Google");
        Company company3 = new Company("Microsoft");

        // same company -> same hash code
        assertEquals(company1.hashCode(), company2.hashCode());

        // different company -> likely different hash code (not guaranteed, but likely)
        assertFalse(company1.hashCode() == company3.hashCode());
    }

    @Test
    public void toStringMethod() {
        Company company = new Company("Google");
        assertEquals("Google", company.toString());

        Company emptyCompany = new Company("");
        assertEquals("", emptyCompany.toString());
    }
}

