package seedu.address.storage;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Person;
import seedu.address.model.request.Request;

/**
 * Jackson-friendly version of {@link Request}.
 */
class JsonAdaptedRequest {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Request's %s field is missing!";

    private final JsonAdaptedPerson requester;
    private final String allocatedDate;
    private final String requestedDate;
    private final JsonAdaptedPerson accepter;

    /**
     * Constructs a {@code JsonAdaptedRequest} with the given request details.
     */
    @JsonCreator

    public JsonAdaptedRequest(@JsonProperty("requester") JsonAdaptedPerson requester,
                              @JsonProperty("allocatedDate") String allocatedDate,
                              @JsonProperty("requestedDate") String requestedDate,
                              @JsonProperty("accepter") JsonAdaptedPerson accepter) {
        this.requester = requester;
        this.requestedDate = requestedDate;
        this.allocatedDate = allocatedDate;
        this.accepter = accepter;
    }

    /**
     * Converts a given {@code Request} into this class for Jackson use.
     */
    public JsonAdaptedRequest(Request source) {
        requester = new JsonAdaptedPerson(source.getRequester());
        allocatedDate = source.getAllocatedDate().toString();
        requestedDate = source.getRequestedDate().toString();
        if (source.getAccepter() == null) {
            accepter = null;
        } else {
            accepter = new JsonAdaptedPerson(source.getAccepter());
        }
    }

    /**
     * Converts this Jackson-friendly adapted request object into the model's {@code Request} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted request.
     */
    public Request toModelType() throws IllegalValueException {
        if (requester == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "requester"));
        }

        Person modelRequester = requester.toModelType();

        if (allocatedDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "allocated date"));
        }

        final LocalDate modelAllocatedDate = LocalDate.parse(allocatedDate);

        if (requestedDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "requested date"));
        }

        final LocalDate modelRequestedDate = LocalDate.parse(requestedDate);

        if (accepter != null) {
            Person modelAccepter = accepter.toModelType();
            return new Request(modelRequester, modelAllocatedDate, modelRequestedDate, modelAccepter);
        } else {
            return new Request(modelRequester, modelAllocatedDate, modelRequestedDate);
        }
    }

}
