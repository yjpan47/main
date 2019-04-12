package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.commons.core.UiCommandInteraction;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;
    private final UiCommandInteraction uiCommandInteraction;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */

    public CommandResult(String feedbackToUser, UiCommandInteraction uiCommandInteraction) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.uiCommandInteraction = uiCommandInteraction;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, null);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public UiCommandInteraction getUiCommandInteraction() {
        return uiCommandInteraction;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && uiCommandInteraction == otherCommandResult.getUiCommandInteraction();
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, uiCommandInteraction);
    }

}
