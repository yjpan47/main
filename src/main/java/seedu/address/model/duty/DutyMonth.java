package seedu.address.model.duty;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import seedu.address.commons.util.CalendarUtil;
import seedu.address.commons.util.DateUtil;
import seedu.address.model.person.Person;
/**
 * Duty month class has a month of duties for the current month
 */
public class DutyMonth {

    private boolean confirmed = false;
    private boolean needsRollover = true;

    private int year;
    private int monthIndex;
    private int firstDayOfWeekIndex;
    private List<Duty> scheduledDuties = new ArrayList<>();
    private HashMap<Person, List<Integer>> blockedDays = new HashMap<>();

    /**
     * Default constructor
     * @param year current year
     * @param monthIndex current index of month (0 - 11)
     * @param firstDayOfWeekIndex day of the week of first day of current month (1 for Sunday - 7 for Saturday)
     */
    public DutyMonth(int year, int monthIndex, int firstDayOfWeekIndex) {
        if (DateUtil.isValidYear(year) && DateUtil.isValidMonth(monthIndex)
                && DateUtil.isValidDayOfWeek(firstDayOfWeekIndex)) {
            this.year = year;
            this.monthIndex = monthIndex;
            this.firstDayOfWeekIndex = firstDayOfWeekIndex;
        } else {
            throw new InvalidParameterException("Invalid Date");
        }
    }

    /**
     * Constructor for reconstructing DutyMonth object from json
     * @param confirmed whether this object is confirmed
     * @param needsRollover whether this needs a rollover
     * @param year current year
     * @param monthIndex current index of month (0 - 11)
     * @param firstDayOfWeekIndex day of the week of first day of current month (1 for Sunday - 7 for Saturday)
     * @param duties the list of the duties
     * @param blockedDays the list of blocked dates
     */
    public DutyMonth(boolean confirmed, boolean needsRollover, int year, int monthIndex, int firstDayOfWeekIndex,
                     List<Duty> duties, HashMap<Person, List<Integer>> blockedDays) {
        this.confirmed = confirmed;
        this.needsRollover = needsRollover;
        this.year = year;
        this.monthIndex = monthIndex;
        this.firstDayOfWeekIndex = firstDayOfWeekIndex;
        this.scheduledDuties.addAll(duties);
        this.blockedDays.putAll(blockedDays);
    }

    /**
     * Constructor for making a copy of the dutyMonth to commit
     * @param dutyMonth to be copied.
     */
    public DutyMonth(DutyMonth dutyMonth, boolean toCommit) {

        this.year = dutyMonth.getYear();
        this.monthIndex = dutyMonth.getMonthIndex();
        this.firstDayOfWeekIndex = dutyMonth.getFirstDayOfWeekIndex();

        if (toCommit) {
            this.scheduledDuties = new ArrayList<>(dutyMonth.getScheduledDuties());
            this.confirmed = dutyMonth.isConfirmed();
            for (Person person : dutyMonth.getBlockedDates().keySet()) {
                this.blockedDays.put(person, new ArrayList<>(dutyMonth.getBlockedDates().get(person)));
            }
        } else {
            this.blockedDays = new HashMap<>(dutyMonth.getBlockedDates());
            this.confirmed = false;
        }
    }

    /**
     * Adds a day to the blocked list for the person inputted
     */
    public void addBlockedDay(Person person, int day) {
        if (DateUtil.isValidDate(this.year, this.monthIndex, day)) {
            this.blockedDays.putIfAbsent(person, new ArrayList<>());
            if (!this.blockedDays.get(person).contains(day)) {
                this.blockedDays.get(person).add(day);
            }
        } else {
            throw new InvalidParameterException("Invalid Date");
        }
    }

    /**
     * Removed Blocked days from a person
     */
    public void removeBlockedDays(Person person) {
        if (this.blockedDays.get(person) != null) {
            this.blockedDays.get(person).clear();
        }
    }

    /**
     * Schedule allocates duties for the DutyMonth
     */
    public void schedule(List<Person> persons, DutySettings dutySettings, DutyStorage dutyStorage) {

        // Temporary Storage for points earned
        HashMap<Person, Integer> points = new HashMap<>();
        for (Person person : persons) {
            points.put(person, dutyStorage.getDutyPoints().getOrDefault(person, 0));
        }

        // List of Duties
        List<Duty> dutyList = generateAllDuties(dutySettings);

        // Priority Queue of Persons
        PriorityQueue<Person> personQueue = new PriorityQueue<>(Comparator.comparingInt(points::get));
        personQueue.addAll(persons);

        if (personQueue.isEmpty()) {
            this.scheduledDuties.addAll(dutyList);
            this.scheduledDuties.sort(Comparator.comparingInt(Duty::getDayIndex));
        }

        for (Duty duty : dutyList) {
            while (!duty.isFilled()) {
                boolean hasAssignable = true;

                Person person = personQueue.poll();
                List<Person> tempList = new ArrayList<>();

                while (!this.isAssignable(person, duty)) {
                    if (personQueue.isEmpty()) {
                        hasAssignable = false;
                        break;
                    }
                    tempList.add(person);
                    person = personQueue.poll();
                }
                personQueue.addAll(tempList);


                if (hasAssignable) {
                    duty.addPerson(person);
                    points.replace(person, points.get(person) + duty.getPoints());
                    personQueue.add(person);

                } else {
                    personQueue.add(person);
                    break;
                }

            }
        }
        this.scheduledDuties.clear();
        this.scheduledDuties.addAll(dutyList);
        this.scheduledDuties.sort(Comparator.comparingInt(Duty::getDayIndex));
    }

    /**
     * Checks if a person can be assigned to a duty on a given day
     */
    private boolean isAssignable(Person person, Duty duty) {
        if (duty.isFilled()) {
            return false;
        } else if ((duty.getPersons().contains(person))) {
            return false;
        } else {
            return !this.blockedDays.containsKey(person)
                    || !this.blockedDays.get(person).contains(duty.getDayIndex());
        }
    }

    /**
     * Generates duties for the month
     */
    private List<Duty> generateAllDuties(DutySettings dutySettings) {
        List<Duty> duties = new ArrayList<>();
        int dayOfWeek = this.getFirstDayOfWeekIndex();
        for (int day = 1; day <= this.getNumOfDays(); day++) {
            int capacity = dutySettings.getCapacity(this.monthIndex, day, dayOfWeek);
            int points = dutySettings.getPoints(this.monthIndex, day, dayOfWeek);
            Duty duty = new Duty(this.year, this.monthIndex, day, dayOfWeek, capacity, points);
            duties.add(duty);
            dayOfWeek = (dayOfWeek == 7) ? 1 : dayOfWeek + 1;
        }
        Collections.shuffle(duties);
        duties.sort((d1, d2) -> (d2.getPoints() - d1.getPoints()));
        return duties;
    }

    /**
     * Clears all duties in the month
     */
    public void clearAllDuties() {
        this.scheduledDuties = new ArrayList<>();
    }

    public int getFirstDayOfWeekIndex() {
        return firstDayOfWeekIndex;
    }

    public int getMonthIndex() {
        return monthIndex;
    }

    public int getYear() {
        return year;
    }

    public int getNumOfDays() {
        return DateUtil.getNumOfDaysInMonth(this.year, this.monthIndex);
    }

    public List<Duty> getScheduledDuties() {
        return this.scheduledDuties;
    }

    public HashMap<Person, List<Integer>> getBlockedDates() {
        return this.blockedDays;
    }

    /**
     * Prints the scheduled duties for the DutyMonth
     */
    public String printDuties() {

        StringBuilder sb = new StringBuilder();

        if (this.getUnfilledDuties().size() > 0) {
            sb.append("--- WARNING: THESE DUTIES ARE NOT COMPLETELY UNFILLED! ---\n");
            for (Duty duty : this.getUnfilledDuties()) {
                sb.append(String.format("%s | Filled: (%d/%d)\n", duty, duty.getPersons().size(), duty.getCapacity()));
            }
            sb.append("\nUse <settings> command to change manpower requirements "
                    + "or <add> command to add more persons.\n\n");
        }

        sb.append(String.format("---- Duty Roster for %1$s %2$s  ---- \n",
                DateUtil.getMonth(this.monthIndex), this.year));

        for (Duty duty : this.getScheduledDuties()) {
            sb.append(String.format("%s | %s | %s\n", duty, duty.getStatus(), duty.getPersons()));
        }

        return sb.toString();
    }

    /**
     * Prints the points allocated to duty personnel for that month
     */
    public String printPoints(DutyStorage dutyStorage) {
        StringBuilder sb = new StringBuilder();
        sb.append("--- POINTS AWARDED ----\n");

        Set<Person> personsThisMonth = new HashSet<>();
        for (Duty duty : this.scheduledDuties) {
            personsThisMonth.addAll(duty.getPersons());
        }

        for (Person person : personsThisMonth) {
            int pointsInThePast = dutyStorage.getPoints(person);
            int pointsGained = this.getScheduledDuties().stream()
                    .filter(duty -> duty.getPersons().contains(person))
                    .mapToInt(Duty::getPoints)
                    .sum();
            sb.append(String.format("%s | Points Accumulated: %d | Points Awarded: %d\n",
                    person, pointsInThePast, pointsGained));
        }
        return sb.toString();
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public boolean isRollover() {
        return this.needsRollover;
    }

    public void confirm() {
        this.confirmed = true;
    }

    public void unconfirm() {
        this.confirmed = false;
    }

    /**
     * Checks if a person has a duty on a given day
     */
    public boolean isAssignedToDuty(String nric, int day) {
        List<Person> personsOnDuty = scheduledDuties.get(day - 1).getPersons();
        for (Person person : personsOnDuty) {
            if (person.getNric().toString().equals(nric)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return list of unfilled duties after scheduling
     * @return list of unfilled duties
     */
    public List<Duty> getUnfilledDuties() {
        List<Duty> unfilledDuties = new ArrayList<>();
        for (Duty duty : this.getScheduledDuties()) {
            if (!duty.isFilled()) {
                unfilledDuties.add(duty);
            }
        }
        return unfilledDuties;
    }

    /**
     * Swap duties between two persons
     * @param p1 person 1 to be swapped
     * @param d1 person 1 current duty
     * @param p2 person 2 to be swapped
     * @param d2 person 2 current duty
     * @param dutyStorage dutyStorage
     */
    public void swap(Person p1, Duty d1, Person p2, Duty d2, DutyStorage dutyStorage) {
        if (this.isConfirmed()) {
            for (Duty duty : this.getScheduledDuties()) {
                if (duty.equals(d1)) {
                    duty.replacePerson(p1, p2);
                }
                if (duty.equals(d2)) {
                    duty.replacePerson(p2, p1);
                }
            }
            dutyStorage.undo();
            dutyStorage.update(this.getScheduledDuties());
        }
    }

    /**
     * Swap duties between two persons based on their allocated days.
     * @param t1 person 1 to be swapped
     * @param t2 person 2 to be swapped
     * @param dayOne person 1's duty day
     * @param dayTwo person 2's duty day
     * @param dutyStorage dutyStorage
     */
    public void swap(Person t1, Person t2, int dayOne, int dayTwo, DutyStorage dutyStorage) {
        Duty dutyOne = this.getScheduledDuties().get(dayOne - 1);
        Duty dutyTwo = this.getScheduledDuties().get(dayTwo - 1);
        dutyOne.replacePerson(t1, t2);
        dutyTwo.replacePerson(t2, t1);
        dutyStorage.undo();
        dutyStorage.update(this.getScheduledDuties());
    }

    /**
     * Get the number of days in this month
     * @return number of days
     */
    public int getNumberOfDays() {
        return CalendarUtil.getNumOfDays(this.year, this.monthIndex);
    }
}
