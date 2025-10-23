package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Handles reading and writing of {@code AddressBook} data to and from a JSON file on the local storage.
 * <p>
 * This class provides a concrete implementation of {@link AddressBookStorage} using JSON serialization.
 * It ensures that the data in the address book can be persisted across application runs and
 * correctly loaded back into the model when needed.
 * </p>
 * <p>
 * The expected file format corresponds to the structure defined in {@link JsonSerializableAddressBook}.
 * </p>
 */
public class JsonAddressBookStorage implements AddressBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonAddressBookStorage.class);

    /** Path to the JSON file where the address book data is stored. */
    private Path filePath;

    /**
     * Creates a {@code JsonAddressBookStorage} that stores data at the specified file path.
     *
     * @param filePath The path to the JSON file used for storage. Must not be {@code null}.
     */
    public JsonAddressBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    /**
     * Returns the file path to the JSON file where the address book data is stored.
     *
     * @return The {@code Path} to the address book file.
     */
    public Path getAddressBookFilePath() {
        return filePath;
    }

    /**
     * Reads the address book data from the default file path.
     *
     * @return An {@code Optional} containing the {@code ReadOnlyAddressBook} if data is successfully read,
     *         or an empty {@code Optional} if the file does not exist.
     * @throws DataLoadingException If the data in the file is invalid or cannot be parsed.
     */
    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException {
        return readAddressBook(filePath);
    }

    /**
     * Reads the address book data from the specified file path.
     * <p>
     * The file is expected to be in JSON format that matches the structure defined in
     * {@link JsonSerializableAddressBook}. If the file does not exist, an empty {@code Optional} is returned.
     * </p>
     *
     * @param filePath The location of the data file. Must not be {@code null}.
     * @return An {@code Optional} containing the {@code ReadOnlyAddressBook} if data is successfully read,
     *         or an empty {@code Optional} if the file does not exist.
     * @throws DataLoadingException If there are illegal or missing values in the JSON file.
     */
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableAddressBook> jsonAddressBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableAddressBook.class);
        if (!jsonAddressBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAddressBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    /**
     * Saves the given address book to the default file path in JSON format.
     *
     * @param addressBook The address book to save. Must not be {@code null}.
     * @throws IOException If an error occurs while writing to the file.
     */
    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, filePath);
    }

    /**
     * Saves the given address book data to the specified file path in JSON format.
     * <p>
     * If the file does not exist, it will be created automatically.
     * </p>
     *
     * @param addressBook The {@code ReadOnlyAddressBook} to save. Must not be {@code null}.
     * @param filePath The location of the file to save the data to. Must not be {@code null}.
     * @throws IOException If an error occurs while creating or writing to the file.
     */
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableAddressBook(addressBook), filePath);
    }
}
