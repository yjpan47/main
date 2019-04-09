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

import seedu.address.commons.util.DateUtil;
import seedu.address.model.person.Person;
/**
 * Duty month class has a month of duties for the current month
 */
public class DutyMonth {

    private boolean confirmed = false;

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
     * @param year current year
     * @param monthIndex current index of month (0 - 11)
     * @param firstDayOfWeekIndex day of the week of first day of current month (1 for Sunday - 7 for Saturday)
     * @param duties the list of the duties
     * @param blockedDays the list of blocked dates
     */
    public DutyMonth(boolean confirmed, int year, int monthIndex, int firstDayOfWeekIndex,
                     List<Duty> duties, HashMap<Person, List<Integer>> blockedDays) {
        this.confirmed = confirmed;
        this.year = year;
        this.monthIndex = monthIndex;
        this.firstDayOfWeekIndex = firstDayOfWeekIndex;
        this.scheduledDuties.addAll(duties);
        this.blockedDays.putAll(blockedDays);
    }

    /**
     * Constructor for making a dummy copy of the dutyMonth for the purpose of scheduling
     * @param dutyMonth to be copied.
     */
    public DutyMonth(DutyMonth dutyMonth) {
        this.year = dutyMonth.getYear();
        this.monthIndex = dutyMonth.getMonthIndex();
        this.firstDayOfWeekIndex = dutyMonth.getFirstDayOfWeekIndex();
        this.blockedDays = new HashMap<>(dutyMonth.getBlockedDates());
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
     * Generates duty object for the month
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

        sb.append(String.format("---- Duty Roster for %1$s %2$s  ---- \n",
                DateUtil.getMonth(this.monthIndex), this.year));

        for (Duty duty : this.getScheduledDuties()) {
            sb.append(String.format("%-3d %-10s (%d/%d) : [",
                    duty.getDayIndex(), DateUtil.getDayOfWeek(duty.getDayOfWeekIndex()),
                    duty.getPersons().size(), duty.getCapacity()));

            for (Person person : duty.getPersons()) {
                sb.append(String.format("%s %s , ", person.getRank(), person.getName()));
            }

            sb.append(" ]\n");
        }
        return sb.toString();
    }
    /**
     * Prints the points allocated to the duty personnel for that month
     */
    public String printPoints(DutyStorage dutyStorage) {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Points Awarded ----\n");

        Set<Person> personsThisMonth = new HashSet<>();
        for (Duty duty : this.scheduledDuties) {
            personsThisMonth.addAll(duty.getPersons());
        }

        for (Person person : personsThisMonth) {
            int pointsInThePast = dutyStorage.getPoints(person);
            int pointsThisMonth = this.getScheduledDuties().stream()
                    .filter(duty -> duty.getPersons().contains(person))
                    .mapToInt(Duty::getPoints)
                    .sum();
            sb.append(String.format("%3s %-20s %3d       + %-2d\n",
                    person.getRank(), person.getName(),
                    pointsInThePast,
                    pointsThisMonth));
        }
        return sb.toString();
    }

    public boolean isConfirmed() {
        return confirmed;
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
}
