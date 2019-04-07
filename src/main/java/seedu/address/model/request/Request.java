package seedu.address.model.request;

import java.time.LocalDate;

/**
 * Request class to get requested info such as duty dates and requesterNric
 */
public class Request {
    public static final String EMPTY_ACCEPTER_FIELD = "EMPTY";

    private String requesterNric;
    private LocalDate allocatedDate;
    private LocalDate requestedDate;
    private String accepterNric;

    public Request(String requesterNric, LocalDate allocatedDate, LocalDate requestedDate) {
        this.requesterNric = requesterNric;
        this.allocatedDate = allocatedDate;
        this.requestedDate = requestedDate;
        this.accepterNric = EMPTY_ACCEPTER_FIELD;
    }

    public Request(String requesterNric, LocalDate allocatedDate, LocalDate requestedDate, String accepterNric) {
        this.requesterNric = requesterNric;
        this.allocatedDate = allocatedDate;
        this.requestedDate = requestedDate;
        this.accepterNric = accepterNric;
    }

    public String getRequesterNric() {
        return requesterNric;
    }

    public LocalDate getAllocatedDate() {
        return allocatedDate;
    }

    public LocalDate getRequestedDate() {
        return requestedDate;
    }

    public String getAccepterNric() {
        return accepterNric;
    }

    public void setAccepterNric(String accepterNric) {
        this.accepterNric = accepterNric;
    }
}
