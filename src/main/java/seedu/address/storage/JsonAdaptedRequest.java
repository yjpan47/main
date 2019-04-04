package seedu.address.storage;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.request.Request;

/**
 * Jackson-friendly version of {@link Request}.
 */
class JsonAdaptedRequest {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Request's %s field is missing!";

    private final String nric;
    private final String allocatedDate;
    private final String requestedDate;

    /**
     * Constructs a {@code JsonAdaptedRequest} with the given request details.
     */
    @JsonCreator

    public JsonAdaptedRequest(@JsonProperty("nric") String nric, @JsonProperty("allocatedDate") String allocatedDate,
                              @JsonProperty("requestedDate") String requestedDate) {
        this.nric = nric;
        this.requestedDate = requestedDate;
        this.allocatedDate = allocatedDate;
    }

    /**
     * Converts a given {@code Request} into this class for Jackson use.
     */
    public JsonAdaptedRequest(Request source) {
        nric = source.getNric();
        allocatedDate = source.getAllocatedDate().toString();
        requestedDate = source.getRequestedDate().toString();
    }

    /**
     * Converts this Jackson-friendly adapted request object into the model's {@code Request} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted request.
     */
    public Request toModelType() throws IllegalValueException {
        if (nric == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "NRIC"));
        }
        final String modelNric = nric;

        if (allocatedDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "allocated date"));
        }

        final LocalDate modelAllocatedDate = LocalDate.parse(allocatedDate);

        if (requestedDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "requested date"));
        }

        final LocalDate modelRequestedDate = LocalDate.parse(requestedDate);

        return new Request(modelNric, modelRequestedDate, modelAllocatedDate);
    }

}
