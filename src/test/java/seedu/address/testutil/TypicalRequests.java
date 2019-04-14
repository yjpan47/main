package seedu.address.testutil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.request.Request;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalRequests {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("ddMMyy");

    public static final Request REQUEST1 = new Request(TypicalPersons.ALICE, LocalDate.parse("090519", FORMATTER),
            LocalDate.parse("100519", FORMATTER));
    public static final Request REQUEST2 = new Request(TypicalPersons.BOB, LocalDate.parse("110519", FORMATTER),
            LocalDate.parse("120519", FORMATTER), TypicalPersons.CARL);
    public static final Request REQUEST3 = new Request(TypicalPersons.DANIEL, LocalDate.parse("130519", FORMATTER),
            LocalDate.parse("140519", FORMATTER));
    public static final Request REQUEST4 = new Request(TypicalPersons.ELLE, LocalDate.parse("220519", FORMATTER),
            LocalDate.parse("270519", FORMATTER), TypicalPersons.FIONA);


    private TypicalRequests() {} // prevents instantiation

    public static List<Request> getTypicalRequest() {
        return new ArrayList<>(Arrays.asList(REQUEST1, REQUEST2, REQUEST3, REQUEST4));
    }
}
