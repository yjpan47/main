package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command, type 'help' to see UserGuide";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_INVALID_DATE = "Date entered is invalid!";

    //For parse exception when attempting to parse command that you have no authority over
    public static final String MESSAGE_NO_AUTHORITY_PARSE = "Your do not have the authority to call this command!";

    //Command exception, shouldn't be displayed if MESSAGE_NO_AUTHORITY_PARSE works.
    public static final String MESSAGE_NO_AUTHORITY = "You do not have the authority to access this command!";

    public static final String MESSAGE_NO_USER = "No user found!";
    public static final String MESSAGE_USER_NOT_FOUND = "Your username is not found!";

    public static final String MESSAGE_MASTERADMIN_ERROR = "Master admin cannot have duties!";

}
