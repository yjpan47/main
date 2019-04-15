package seedu.address.model.request;

import java.time.LocalDate;

import seedu.address.model.person.Person;

/**
 * Request class to get requested info such as duty dates and requester
 */
public class Request {

    private Person requester;
    private LocalDate allocatedDate;
    private LocalDate requestedDate;
    private Person accepter;

    public Request(Person requester, LocalDate allocatedDate, LocalDate requestedDate) {
        this.requester = requester;
        this.allocatedDate = allocatedDate;
        this.requestedDate = requestedDate;
        this.accepter = null;
    }

    public Request(Person requester, LocalDate allocatedDate, LocalDate requestedDate, Person accepter) {
        this.requester = requester;
        this.allocatedDate = allocatedDate;
        this.requestedDate = requestedDate;
        this.accepter = accepter;
    }

    public String getRequesterNric() {
        return requester.getNric().toString();
    }

    public LocalDate getAllocatedDate() {
        return allocatedDate;
    }

    public LocalDate getRequestedDate() {
        return requestedDate;
    }

    public boolean isAccepterValid() {
        return accepter != null;
    }

    public String getAccepterNric() {
        return accepter.getNric().toString();
    }

    public Person getRequester() {
        return requester;
    }

    public void setRequester(Person requester) {
        this.requester = requester;
    }

    public Person getAccepter() {
        return accepter;
    }

    public void setAccepter(Person accepter) {
        this.accepter = accepter;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getRequesterNric()).append(" requests a swap from ")
                .append(getAllocatedDate().toString()).append(" to ")
                .append(getRequestedDate().toString());
        if (isAccepterValid()) {
            sb.append(". ").append(getAccepterNric()).append(" has accepted");
        }

        return sb.toString();
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof Request // instanceof handles nulls
                && requester.equals(((Request) other).requester)
                && allocatedDate.equals(((Request) other).allocatedDate)
                && requestedDate.equals(((Request) other).requestedDate);
    }
}
