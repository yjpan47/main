package seedu.address.storage;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Jackson-friendly class to hold a person in dutyStorage
 */
public class JsonAdaptedDutyStoragePerson {
    private String nric;
    private int dutyPoints;
    private List<String> dutyRecords;
    private int prevDutyPoints;
    private List<String> prevDutyRecords;

    /**
     * Constructs a {@code JsonAdaptedDutyStoragePerson} with the given details.
     */
    @JsonCreator
    public JsonAdaptedDutyStoragePerson(@JsonProperty("person") String nric,
                                        @JsonProperty("dutyPoints") int dutyPoints,
                                        @JsonProperty("dutyRecords") List<String> dutyRecords,
                                        @JsonProperty("PrevDutyPoints") int prevDutyPoints,
                                        @JsonProperty("PrevDutyRecord") List<String> prevDutyRecords) {
        this.nric = nric;
        this.dutyPoints = dutyPoints;
        this.dutyRecords = dutyRecords;
        this.prevDutyPoints = prevDutyPoints;
        this.prevDutyRecords = prevDutyRecords;
    }


    public String getNric() {
        return nric;
    }

    public int getDutyPoints() {
        return dutyPoints;
    }

    public List<String> getDutyRecords() {
        return dutyRecords;
    }

    public int getPrevDutyPoints() {
        return prevDutyPoints;
    }

    public List<String> getPrevDutyRecords() {
        return prevDutyRecords;
    }
}
