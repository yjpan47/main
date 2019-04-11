package seedu.address.model.request;

import seedu.address.model.person.Person;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Request class to get requested info such as duty dates and requester
 */
public class Request {

    private Person requester;
    private LocalDate allocatedDate;
    private LocalDate requestedDate;
    private Optional<Person> accepter;

    public Request(Person requester, LocalDate allocatedDate, LocalDate requestedDate) {
        this.requester = requester;
        this.allocatedDate = allocatedDate;
        this.requestedDate = requestedDate;
        this.accepter = Optional.empty();
    }

    public Request(Person requester, LocalDate allocatedDate, LocalDate requestedDate, Person accepter) {
        this.requester = requester;
        this.allocatedDate = allocatedDate;
        this.requestedDate = requestedDate;
        this.accepter = Optional.of(accepter);
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
        return accepter.isPresent();
    }

    public String getAccepterNric() {
        return accepter.get().getNric().toString();
    }

    public Person getRequester() {
        return requester;
    }

    public Person getAccepter() {
        return accepter.get();
    }

    public void setAccepter(Person accepter) {
        this.accepter = Optional.of(accepter);
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
}
