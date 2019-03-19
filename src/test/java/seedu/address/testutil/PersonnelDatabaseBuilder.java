package seedu.address.testutil;

import seedu.address.model.PersonnelDatabase;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building PersonnelDatabase objects.
 * Example usage: <br>
 *     {@code PersonnelDatabase ab = new PersonnelDatabaseBuilder().withPerson("John", "Doe").build();}
 */
public class PersonnelDatabaseBuilder {

    private PersonnelDatabase personnelDatabase;

    public PersonnelDatabaseBuilder() {
        personnelDatabase = new PersonnelDatabase();
    }

    public PersonnelDatabaseBuilder(PersonnelDatabase personnelDatabase) {
        this.personnelDatabase = personnelDatabase;
    }

    /**
     * Adds a new {@code Person} to the {@code PersonnelDatabase} that we are building.
     */
    public PersonnelDatabaseBuilder withPerson(Person person) {
        personnelDatabase.addPerson(person);
        return this;
    }

    public PersonnelDatabase build() {
        return personnelDatabase;
    }
}
