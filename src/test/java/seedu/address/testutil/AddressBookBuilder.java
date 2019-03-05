package seedu.address.testutil;

import seedu.address.model.PersonnelDatabase;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code PersonnelDatabase ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private PersonnelDatabase personnelDatabase;

    public AddressBookBuilder() {
        personnelDatabase = new PersonnelDatabase();
    }

    public AddressBookBuilder(PersonnelDatabase personnelDatabase) {
        this.personnelDatabase = personnelDatabase;
    }

    /**
     * Adds a new {@code Person} to the {@code PersonnelDatabase} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        personnelDatabase.addPerson(person);
        return this;
    }

    public PersonnelDatabase build() {
        return personnelDatabase;
    }
}
