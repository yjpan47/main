package seedu.address.model.duty;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.Assert;

public class DutyTest {

    private Duty duty;

    @Before
    public void setUp() {
        duty = new Duty(2019, 4, 1, 4, 3,
                3, new ArrayList<>());
        duty.addPerson(ALICE);
        duty.addPerson(BENSON);
        duty.addPerson(CARL);
    }

    @Test
    public void removePerson() {
        duty.removePerson(ALICE);
        assertFalse(duty.contains(ALICE));
    }

    @Test
    public void replacePerson() {
        duty.replacePerson(BENSON, DANIEL);
        assertFalse(duty.contains(BENSON));
        assertTrue(duty.contains(DANIEL));
    }

    @Test
    public void replaceNonExistentPersonThrowsPersonNotFoundException() {
        Assert.assertThrows(PersonNotFoundException.class, () -> duty.replacePerson(ELLE, DANIEL));
    }

    @Test
    public void replacePersonWithExistingPersonThrowsPersonNotFoundException() {
        Assert.assertThrows(DuplicatePersonException.class, () -> duty.replacePerson(BENSON, ALICE));
    }


}
