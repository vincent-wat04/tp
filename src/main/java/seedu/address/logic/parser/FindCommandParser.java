package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new {@link FindCommand} object.
 *
 * Supported forms:
 * <ul>
 * <li>{@code find KEYWORD [KEYWORD]...}</li>
 * <li>{@code find t/TAG [t/TAG]...}</li>
 * <li>{@code find c/COMPANY [c/COMPANY]...}</li>
 * <li>{@code find KEYWORD [KEYWORD]... t/TAG [t/TAG]... c/COMPANY [c/COMPANY]...}</li>
 * </ul>
 * Multiple {@code t/} values are AND-ed. Multiple {@code c/} values are OR-ed.
 * If keywords, tags, and/or company are given, they are AND-ed together.
 */
public class FindCommandParser implements Parser<FindCommand> {
    /**
     * Parses the given {@code String} of arguments and returns a {@code FindCommand} object for execution.
     * <p>
     * The method supports searching by:
     * <ul>
     *   <li>Name keywords (from the preamble, space-separated)</li>
     *   <li>Tags (provided using {@code t/}, matched with AND semantics)</li>
     *   <li>Company names (provided using {@code c/}, matched with OR semantics via case-insensitive substring)</li>
     * </ul>
     * </p>
     *
     * <p>
     * The parser ensures:
     * <ul>
     *   <li>Tags are valid according to {@link Tag#isValidTagName(String)}</li>
     *   <li>Empty company prefixes (e.g., {@code c/}) are rejected</li>
     *   <li>At least one search criterion (name, tag, or company) must be provided</li>
     * </ul>
     * </p>
     *
     * <p>
     * Examples:
     * <pre>
     * find Alex t/friend
     * find c/Google
     * find Alex c/Meta t/colleague
     * </pre>
     * </p>
     *
     * @param args the user input arguments string to parse
     * @return a {@code FindCommand} representing the parsed search criteria
     * @throws ParseException if any of the provided arguments are invalid or the input format is incorrect
     */
    @Override
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Tokenize arguments by tag and company prefixes
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG, PREFIX_COMPANY);

        // Build tag predicate (AND across all provided t/)
        Predicate<Person> tagPredicate = person -> true; // neutral
        List<String> rawTags = argMultimap.getAllValues(PREFIX_TAG).stream()
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        if (!rawTags.isEmpty()) {
            for (String t : rawTags) {
                if (!Tag.isValidTagName(t)) {
                    throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
                }
            }
            final Set<Tag> required = rawTags.stream().map(Tag::new).collect(Collectors.toSet());

            // AND semantics: a person must have all required tags
            tagPredicate = person -> {
                Set<Tag> personTags = person.getTags();
                return personTags.containsAll(required);
            };
        }

        // Build company predicate (OR across provided c/ values; case-insensitive substring)
        Predicate<Person> companyPredicate = person -> true; // neutral
        List<String> allCompanyValues = argMultimap.getAllValues(PREFIX_COMPANY);
        boolean hasEmptyCompanyValue = allCompanyValues.stream().anyMatch(s -> s.trim().isEmpty());
        List<String> rawCompanies = allCompanyValues.stream()
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
        if (hasEmptyCompanyValue && rawCompanies.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        if (!rawCompanies.isEmpty()) {
            final List<String> lowered = rawCompanies.stream().map(String::toLowerCase).collect(Collectors.toList());
            companyPredicate = person -> {
                String companyValueLower = person.getCompany().value.toLowerCase();
                return lowered.stream().anyMatch(companyValueLower::contains);
            };
        }

        // Build name predicate from preamble (space-separated keywords)
        String preamble = argMultimap.getPreamble().trim();
        Predicate<Person> namePredicate = person -> true; // neutral
        if (!preamble.isEmpty()) {
            List<String> keywords = Arrays.asList(preamble.split("\\s+"));
            namePredicate = new NameContainsKeywordsPredicate(keywords);
        }

        // Determine presence of filters
        boolean hasTags = !rawTags.isEmpty();
        boolean hasKeywords = !preamble.isEmpty();
        boolean hasCompanies = !rawCompanies.isEmpty();

        // Reject empty query
        if (!hasTags && !hasKeywords && !hasCompanies) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // Handle single-condition queries
        if (hasKeywords && !hasTags && !hasCompanies) {
            return new FindCommand(namePredicate);
        }
        if (!hasKeywords && hasTags && !hasCompanies) {
            return new FindCommand(tagPredicate, rawTags);
        }
        if (!hasKeywords && !hasTags && hasCompanies) {
            return new FindCommand(companyPredicate);
        }

        // Combine all present predicates with AND semantics
        return new FindCommand(namePredicate.and(tagPredicate).and(companyPredicate), rawTags);
    }

}
