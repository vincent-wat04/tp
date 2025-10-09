package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
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
 * <li>{@code find KEYWORD [KEYWORD]... t/TAG [t/TAG]...}</li>
 * </ul>
 * Multiple {@code t/} values are OR-ed. If both keywords and tags are given,
 * they are AND-ed.
 */
public class FindCommandParser implements Parser<FindCommand> {

    @Override
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Tokenize to separate optional tag(s) from the preamble (name keywords)
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

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

            // AND semantics: a person must have *all* required tags
            tagPredicate = person -> {
                Set<Tag> personTags = person.getTags();
                return personTags.containsAll(required);
            };
        }

        // Build name predicate from preamble (space-separated keywords)
        String preamble = argMultimap.getPreamble().trim();
        Predicate<Person> namePredicate = person -> true; // neutral
        if (!preamble.isEmpty()) {
            List<String> keywords = Arrays.asList(preamble.split("\\s+"));
            namePredicate = new NameContainsKeywordsPredicate(keywords);
        }

        // If both are neutral, it's an empty query
        boolean noTags = rawTags.isEmpty();
        boolean noKeywords = preamble.isEmpty();
        if (noTags && noKeywords) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // Combine with AND so both constraints apply when both present
        Predicate<Person> combined = namePredicate.and(tagPredicate);
        return new FindCommand(combined);
    }
}
