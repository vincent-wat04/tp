package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@link AddTagCommand} object.
 * Expected usage: {@code addtag t/TAG}
 */
public class AddTagCommandParser implements Parser<AddTagCommand> {

    // Fast path: handle the common "t/<value>" shape directly (allows leading/trailing spaces).
    // Captures anything non-empty after "t/".
    private static final Pattern RAW_TAG_ONLY =
            Pattern.compile("^\\s*t/(\\S.*)\\s*$");

    @Override
    public AddTagCommand parse(String args) throws ParseException {
        final String raw = args == null ? "" : args;

        // 1) Regex fast-path: accept "t/..." with optional surrounding whitespace
        Matcher m = RAW_TAG_ONLY.matcher(raw);
        if (m.matches()) {
            String tagName = m.group(1).trim();
            if (tagName.isEmpty()) {
                throw new ParseException(String.format(
                        Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
            }
            return new AddTagCommand(tagName);
        }

        // 2) Standard path: tokenize by prefixes (works if your tokenizer recognizes t/ without needing a space)
        String trimmed = raw.trim();
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(trimmed, PREFIX_TAG);

        // Reject true preamble (i.e., actual non-prefix tokens like "foo t/x")
        if (!argMultimap.getPreamble().isBlank()) {
            throw new ParseException(String.format(
                    Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        }

        Optional<String> tagOpt = argMultimap.getValue(PREFIX_TAG);
        if (tagOpt.isEmpty()) {
            throw new ParseException(String.format(
                    Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        }

        String tagName = tagOpt.get().trim();
        if (tagName.isEmpty()) {
            throw new ParseException(String.format(
                    Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        }

        return new AddTagCommand(tagName);
    }
}
