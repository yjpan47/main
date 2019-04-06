package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalPersonnelDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.request.Request;
import seedu.address.testutil.PersonBuilder;

public class PersonnelDatabaseTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final PersonnelDatabase personnelDatabase = new PersonnelDatabase();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), personnelDatabase.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        personnelDatabase.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyPersonnelDatabase_replacesData() {
        PersonnelDatabase newData = getTypicalPersonnelDatabase();
        personnelDatabase.resetData(newData);
        assertEquals(newData, personnelDatabase);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        PersonnelDatabaseStub newData = new PersonnelDatabaseStub(newPersons);

        thrown.expect(DuplicatePersonException.class);
        personnelDatabase.resetData(newData);
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        personnelDatabase.hasPerson((Person) null);
    }

    @Test
    public void hasPerson_personNotInPersonnelDatabase_returnsFalse() {
        assertFalse(personnelDatabase.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInPersonnelDatabase_returnsTrue() {
        personnelDatabase.addPerson(ALICE);
        assertTrue(personnelDatabase.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInPersonnelDatabase_returnsTrue() {
        personnelDatabase.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(personnelDatabase.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        personnelDatabase.getPersonList().remove(0);
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        personnelDatabase.addListener(listener);
        personnelDatabase.addPerson(ALICE);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        personnelDatabase.addListener(listener);
        personnelDatabase.removeListener(listener);
        personnelDatabase.addPerson(ALICE);
        assertEquals(0, counter.get());
    }

    /**
     * A stub ReadOnlyPersonnelDatabase whose persons list can violate interface constraints.
     */
    private static class PersonnelDatabaseStub implements ReadOnlyPersonnelDatabase {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        // To be edited if necessary
        private final DutyCalendar dutyCalendar = new DutyCalendar();
        private final List<Request> requests = new ArrayList<>();

        PersonnelDatabaseStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public List<Request> getRequestList() {
            return requests;
        }

        @Override
        public DutyCalendar getDutyCalendar() {
            return dutyCalendar;
        }

        @Override
        public void addListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }
    }

}
