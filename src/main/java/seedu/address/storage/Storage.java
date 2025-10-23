package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Represents the API of the Storage component.
 * <p>
 * The {@code Storage} interface provides methods to read and save both user preferences
 * and the address book data to persistent storage.
 * </p>
 * <p>
 * It serves as a unifying abstraction for different types of storage used in the application,
 * combining the functionalities of {@link AddressBookStorage} and {@link UserPrefsStorage}.
 * </p>
 */
public interface Storage extends AddressBookStorage, UserPrefsStorage {

    /**
     * Reads the user preferences from storage.
     *
     * @return an {@code Optional} containing the {@code UserPrefs} if available or empty if not found.
     * @throws DataLoadingException if there is an error reading the data from storage
     */
    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    /**
     * Saves the given user preferences to storage.
     *
     * @param userPrefs the {@code ReadOnlyUserPrefs} to save; must not be {@code null}
     * @throws IOException if there is an error writing to the file
     */
    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    /**
     * Returns the file path where the address book data is stored.
     *
     * @return the {@code Path} to the address book file
     */
    @Override
    Path getAddressBookFilePath();

    /**
     * Reads the address book data from storage.
     *
     * @return an {@code Optional} containing the {@code ReadOnlyAddressBook} if available, or empty if not found.
     * @throws DataLoadingException if there is an error reading the data from storage
     */
    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException;

    /**
     * Saves the given address book data to storage.
     *
     * @param addressBook the {@code ReadOnlyAddressBook} to save; must not be {@code null}
     * @throws IOException if there is an error writing to the file
     */
    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;
}

