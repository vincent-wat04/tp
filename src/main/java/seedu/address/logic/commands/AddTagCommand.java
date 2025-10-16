package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

/**
 * Adds a new allowed tag into the {@code TagRegistry} so that it can be used in future
 * {@code add} or {@code edit} commands.
 * <p>
 * This command dynamically extends the list of permissible tags at runtime.
 * Example usage: {@code addtag t/friends}
 */
public class AddTagCommand extends Command {

    public static final String COMMAND_WORD = "addtag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a tag to the allowed tag list.\n"
            + "Parameters: " + PREFIX_TAG + "TAG\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_TAG + "friends";

    public static final String MESSAGE_SUCCESS = "Tag added: %1$s";
    public static final String MESSAGE_DUPLICATE = "This tag already exists in the allowed list.";
    public static final String MESSAGE_INVALID = Tag.MESSAGE_CONSTRAINTS;

    private final String tagName;

    /**
     * Creates an {@code AddTagCommand} to add the specified {@code tagName}
     *
     * @param tagName the name of the tag to be added
     */
    public AddTagCommand(String tagName) {
        requireNonNull(tagName);
        this.tagName = tagName;
    }

    /**
     * Executes the command by adding the tag to the {@code TagRegistry}.
     * Throws a {@code CommandException} if the tag name is invalid or already exists.
     *
     * @param model the model which contains the tag registry
     * @return a {@code CommandResult} indicating success
     * @throws CommandException if tag name is invalid or already exists
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!Tag.isValidTagName(tagName)) {
            throw new CommandException(MESSAGE_INVALID);
        }
        if (model.getTagRegistry().isAllowed(tagName)) {
            throw new CommandException(MESSAGE_DUPLICATE);
        }

        model.addAllowedTag(tagName);
        return new CommandResult(String.format(MESSAGE_SUCCESS, tagName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddTagCommand
                && tagName.equals(((AddTagCommand) other).tagName));
    }
}
