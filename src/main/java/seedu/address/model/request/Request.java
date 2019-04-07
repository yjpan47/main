package seedu.address.model.request;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Request class to get requested info such as duty dates and requesterNric
 */
public class Request {
    private String requesterNric;
    private LocalDate allocatedDate;
    private LocalDate requestedDate;
    private Optional<String> accepterNric;

    public Request(String requesterNric, LocalDate allocatedDate, LocalDate requestedDate) {
        this.requesterNric = requesterNric;
        this.allocatedDate = allocatedDate;
        this.requestedDate = requestedDate;
        this.accepterNric = Optional.empty();
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
        return accepterNric.get();
    }

    public void setAccepterNric(String accepterNric) {
        this.accepterNric = Optional.of(accepterNric);
    }
}
