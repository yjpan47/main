package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
/**
 * JsonAdaptedHashMapUnit class to hold blocked dates of duty personnel
 */
public class JsonAdaptedHashMapUnit {

    private final String person;
    private final List<Integer> blockedDates = new ArrayList<>();

    /**
     * Constucts a {@code JsonAdaptedHashMapUnit} with the given hashmap details.
     */
    @JsonCreator

    public JsonAdaptedHashMapUnit(@JsonProperty("person") String person,
                       @JsonProperty("blockedDates") List<Integer> blockedDates) {
        this.person = person;
        if (blockedDates != null) {
            this.blockedDates.addAll(blockedDates);
        }
    }

    public String getPerson() {
        return this.person;
    }

    public List<Integer> getBlockedDates() {
        return this.getBlockedDates();
    }

}
