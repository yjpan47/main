package seedu.address.commons.core;

/**
 *  Usertypes that can be used
 *  <li>{@link #GENERAL}</li>
 *  <li>{@link #ADMIN}</li>
 */
public enum UserType {
    GENERAL,
    ADMIN;

    public static final String MESSAGE_CONSTRAINTS = "UserType character must be 'A' or 'G' only.";
    public static final String DEFAULT_ADMIN_USERNAME = "Admin";
}
