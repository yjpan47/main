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

    private final String requesterNric;
    private final String allocatedDate;
    private final String requestedDate;
    private final String accepterNric;

    /**
     * Constructs a {@code JsonAdaptedRequest} with the given request details.
     */
    @JsonCreator

    public JsonAdaptedRequest(@JsonProperty("requesterNric") String requesterNric, @JsonProperty("allocatedDate") String allocatedDate,
                              @JsonProperty("requestedDate") String requestedDate, @JsonProperty("accepterNric") String accepterNric) {
        this.requesterNric = requesterNric;
        this.requestedDate = requestedDate;
        this.allocatedDate = allocatedDate;
        this.accepterNric = accepterNric;
    }

    /**
     * Converts a given {@code Request} into this class for Jackson use.
     */
    public JsonAdaptedRequest(Request source) {
        requesterNric = source.getRequesterNric();
        allocatedDate = source.getAllocatedDate().toString();
        requestedDate = source.getRequestedDate().toString();
        accepterNric = source.getAccepterNric();
    }

    /**
     * Converts this Jackson-friendly adapted request object into the model's {@code Request} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted request.
     */
    public Request toModelType() throws IllegalValueException {
        if (requesterNric == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "requester NRIC"));
        }

        if (allocatedDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "allocated date"));
        }

        final LocalDate modelAllocatedDate = LocalDate.parse(allocatedDate);

        if (requestedDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "requested date"));
        }

        final LocalDate modelRequestedDate = LocalDate.parse(requestedDate);

        if (accepterNric == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "accepter NRIC"));
        }

        return new Request(requesterNric, modelRequestedDate, modelAllocatedDate, accepterNric);
    }

}
