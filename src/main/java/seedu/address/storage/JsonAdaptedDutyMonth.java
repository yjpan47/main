package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.model.calendar.DutyMonth;

/**
 * Jackson-friendly version of {@link DutyMonth}
 */
public class JsonAdaptedDutyMonth {

    private final List<JsonAdaptedDuty> duties = new ArrayList<>();



}
