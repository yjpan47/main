package seedu.address.model.duty;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.Assert;
import seedu.address.testutil.TypicalPersons;

public class DutyAlgorithmTest {

    private static final int Test_Frequency = 1000;
    private static final int DAYS_IN_WEEK = 7;
    private static final int RANDOM_POINTS_LIMIT = 99;
    private static final int RANDOM_CAPACITY_LIMIT = 9;

    private DutyMonth dutyMonth1;
    private DutyMonth dutyMonth2;
    private DutyMonth dutyMonth3;
    private DutyMonth dutyMonth4;
    private DutyMonth dutyMonth5;
    private DutySettings dutySettings;
    private DutyStorage dutyStorage;
    private List<Person> personList;

    @Before
    public void setUp() {
        dutyMonth1 = new DutyMonth(2018, 1, 1);
        dutyMonth2 = new DutyMonth(2019, 1, 5);
        dutyMonth3 = new DutyMonth(2019, 3, 1);
        dutyMonth4 = new DutyMonth(2020, 5, 1);
        dutyMonth5 = new DutyMonth(2020, 11, 2);

        dutySettings = new DutySettings();
        dutyStorage = new DutyStorage();

        personList = TypicalPersons.getTypicalPersons();


    }

    @Test
    public void dutyMonthContructorTest() {

        Assert.assertThrows(InvalidParameterException.class, ()
            -> new DutyMonth(2020, 12, 2));
        Assert.assertThrows(InvalidParameterException.class, ()
            -> new DutyMonth(1999, 11, 8));
        Assert.assertThrows(InvalidParameterException.class, ()
            -> new DutyMonth(2010, -1, 4));
        Assert.assertThrows(InvalidParameterException.class, ()
            -> new DutyMonth(2018, 4, 0));

        assertEquals(dutyMonth1.getYear(), 2018);
        assertEquals(dutyMonth2.getYear(), 2019);
        assertEquals(dutyMonth3.getMonthIndex(), 3);
        assertEquals(dutyMonth4.getNumOfDays(), 30);
        assertTrue(dutyMonth2.getBlockedDates().isEmpty());
        assertFalse(dutyMonth1.isConfirmed());
        assertTrue(dutyMonth5.isRollover());
        assertEquals(dutyMonth3.getScheduledDuties().size(), 0);
    }

    @Test
    public void addBlockDayInvalidTest() {
        Assert.assertThrows(InvalidParameterException.class, ()
            -> dutyMonth2.addBlockedDay(TypicalPersons.ALICE, -1));
        Assert.assertThrows(InvalidParameterException.class, ()
            -> dutyMonth2.addBlockedDay(TypicalPersons.BOB, 0));
        Assert.assertThrows(InvalidParameterException.class, ()
            -> dutyMonth2.addBlockedDay(TypicalPersons.BENSON, 29));
        Assert.assertThrows(InvalidParameterException.class, ()
            -> dutyMonth3.addBlockedDay(TypicalPersons.CARL, 32));
    }


    @Test
    public void addBlockDayTest() {
        assertTrue(dutyMonth3.getBlockedDates().isEmpty());
        dutyMonth3.addBlockedDay(TypicalPersons.ALICE, 1);
        dutyMonth3.addBlockedDay(TypicalPersons.ALICE, 1);
        dutyMonth3.addBlockedDay(TypicalPersons.ALICE, 4);
        dutyMonth3.addBlockedDay(TypicalPersons.BOB, 5);
        dutyMonth3.addBlockedDay(TypicalPersons.GEORGE, 5);
        assertFalse(dutyMonth3.getBlockedDates().isEmpty());
        assertTrue(dutyMonth3.getBlockedDates().get(TypicalPersons.ALICE).contains(1));
        assertTrue(dutyMonth3.getBlockedDates().get(TypicalPersons.ALICE).contains(4));
        assertFalse(dutyMonth3.getBlockedDates().get(TypicalPersons.ALICE).contains(5));
        assertTrue(dutyMonth3.getBlockedDates().get(TypicalPersons.BOB).contains(5));
        assertTrue(dutyMonth3.getBlockedDates().get(TypicalPersons.GEORGE).contains(5));
        assertEquals(dutyMonth3.getBlockedDates().keySet().size(), 3);
    }

    @Test
    public void removeBlockedDaysTest() {
        dutyMonth2.addBlockedDay(TypicalPersons.AMY, 1);
        dutyMonth2.addBlockedDay(TypicalPersons.AMY, 4);
        dutyMonth2.addBlockedDay(TypicalPersons.AMY, 7);
        assertTrue(dutyMonth2.getBlockedDates().get(TypicalPersons.AMY).contains(1));
        assertTrue(dutyMonth2.getBlockedDates().get(TypicalPersons.AMY).contains(4));
        assertTrue(dutyMonth2.getBlockedDates().get(TypicalPersons.AMY).contains(7));
        dutyMonth2.removeBlockedDays(TypicalPersons.AMY);
        assertFalse(dutyMonth2.getBlockedDates().get(TypicalPersons.AMY).contains(4));
        assertEquals(dutyMonth2.getBlockedDates().keySet().size(), 1);
        assertEquals(dutyMonth2.getBlockedDates().get(TypicalPersons.AMY).size(), 0);

    }


    @Test
    public void scheduleDefaultSettingsTest() {
        assertEquals(dutyMonth1.getScheduledDuties().size(), 0);
        dutyMonth1.schedule(personList, dutySettings, dutyStorage);
        assertEquals(dutyMonth1.getScheduledDuties().size(), dutyMonth1.getNumOfDays());
        assertFalse(dutyMonth1.isConfirmed());
        HashMap<Person, Integer> pointsGained = new HashMap<>();
        for (Duty duty : dutyMonth1.getScheduledDuties()) {
            assertEquals(duty.getPoints(), dutySettings.getPoints(dutyMonth1.getMonthIndex(),
                    duty.getDayIndex(), duty.getDayOfWeekIndex()));
            for (Person person : duty.getPersons()) {
                pointsGained.putIfAbsent(person, 0);
                pointsGained.replace(person, pointsGained.get(person) + duty.getPoints());
                for (Person p : duty.getPersons()) {
                    if (!p.equals(person)) {
                        assertTrue(duty.getPersonsString(person.getNric().toString()).contains(p.getName().toString()));
                    }
                }
            }
            for (Person p : duty.getPersons()) {
                int count = 0;
                for (Person q : duty.getPersons()) {
                    if (p.getNric().toString().equals(q.getNric().toString())) {
                        count++;
                    }
                }
                assertEquals(count, 1);
            }
        }
        assertEquals(dutyStorage.getDutyPoints().size(), 0);
        assertEquals(dutyStorage.getDutyRecords().size(), 0);
        dutyStorage.update(dutyMonth1.getScheduledDuties());
        for (Person p : pointsGained.keySet()) {
            assertEquals((int) pointsGained.get(p), dutyStorage.getPoints(p));
        }
        assertEquals(dutyStorage.getDutyPoints().size(), personList.size());
        assertEquals(dutyStorage.getDutyRecords().size(), personList.size());
    }

    @Test
    public void scheduleDifferentSettingsTest() {
        Random random = new Random();
        for (int i = 0; i < Test_Frequency; i++) {
            for (int day = 1; day <= DAYS_IN_WEEK; day++) {
                int rpoints = random.nextInt(RANDOM_POINTS_LIMIT) + 1;
                int rcapacity = random.nextInt(RANDOM_CAPACITY_LIMIT) + 1;
                dutySettings.setPoints(day, rpoints);
                dutySettings.setCapacity(day, rcapacity);
            }
            dutyMonth2.schedule(personList, dutySettings, dutyStorage);
            assertEquals(dutyMonth2.getScheduledDuties().size(), dutyMonth2.getNumOfDays());
            for (Duty duty : dutyMonth2.getScheduledDuties()) {
                assertEquals(duty.getPoints(), dutySettings.getPoints(dutyMonth2.getMonthIndex(),
                        duty.getDayIndex(), duty.getDayOfWeekIndex()));
                assertEquals(duty.getPersons().size(), dutySettings.getCapacity(dutyMonth2.getMonthIndex(),
                        duty.getDayIndex(), duty.getDayOfWeekIndex()));
            }
        }
    }
    @Test
    public void dutyMonthPrintDutiesTest() {
        String expected = "---- Duty Roster for February 2018  ---- \n";
        assertEquals(dutyMonth1.printDuties(), expected);
        dutyMonth4.schedule(personList, dutySettings, dutyStorage);
    }

    @Test
    public void dutyMonthPrintPointsTest() {
        String expected = "--- POINTS AWARDED ----\n";
        assertEquals(dutyMonth1.printPoints(dutyStorage), expected);
        dutyMonth5.schedule(personList, dutySettings, dutyStorage);
        for (Person p : personList) {
            assertTrue(dutyMonth5.printPoints(dutyStorage).contains(p.toString()));
        }
    }

    @Test
    public void dutyStoragePrintDetailsTest() {
        String output = "PTE Benson Meier\n" + "Points : 0\n" + "--- RECORDS ---\n";
        assertEquals(dutyStorage.printDetails(personList.get(1)), output);
    }

    @Test
    public void dutyStoragePrintPointsTest() {
        String output = "--- POINTS ACCUMULATED ----\n";
        assertEquals(dutyStorage.printPoints(), output);
    }

    @Test
    public void dutyStorageRewardTest() {
        Random random = new Random();
        for (Person p : personList) {
            dutyStorage.addPerson(p);
        }
        dutyMonth3.schedule(personList, dutySettings, dutyStorage);
        dutyStorage.update(dutyMonth3.getScheduledDuties());
        for (Person person : TypicalPersons.getTypicalPersons()) {
            int before = dutyStorage.getPoints(person);
            int points = random.nextInt(RANDOM_POINTS_LIMIT);
            dutyStorage.reward(person, points);
            int after = dutyStorage.getPoints(person);
            assertEquals(before + points, after);
        }
    }

    @Test
    public void dutyStoragePenalizeTest() {
        Random random = new Random();
        for (Person p : personList) {
            dutyStorage.addPerson(p);
        }
        dutyMonth4.schedule(personList, dutySettings, dutyStorage);
        dutyStorage.update(dutyMonth4.getScheduledDuties());
        for (Person person : TypicalPersons.getTypicalPersons()) {
            int before = dutyStorage.getPoints(person);
            int points = random.nextInt(RANDOM_POINTS_LIMIT);
            dutyStorage.penalize(person, points);
            int after = dutyStorage.getPoints(person);
            assertEquals(before - points, after);
        }
    }
}



