package seedu.address.model.calendar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * Represents a month in DutyCalendar containing duty details of each day
 */
public class DutyMonth {

    private int monthIndex;
    private int firstDayWeekIndex;
    private int numOfDays;
    private List<Duty> duties;
    private List<Person> persons;

    public DutyMonth(int monthIndex, int firstDayWeekIndex) {

        if (monthIndex >= 1 && monthIndex <= 12 && firstDayWeekIndex >= 0 && firstDayWeekIndex <= 7) {
            this.monthIndex = monthIndex;
            this.firstDayWeekIndex = firstDayWeekIndex;
            this.numOfDays = getNumOfDays();
            this.duties = new ArrayList<>();
            this.persons = new ArrayList<>();

            // Create all duties
            this.generateDuties();

        } else {
            throw new InputMismatchException("Invalid Month Index or first Day Index");
        }
    }

    public DutyMonth(int monthIndex, int firstDayWeekIndex, List<Duty> duties) {

        if (monthIndex >= 1 && monthIndex <= 12 && firstDayWeekIndex >= 0 && firstDayWeekIndex <= 7) {
            this.monthIndex = monthIndex;
            this.firstDayWeekIndex = firstDayWeekIndex;
            this.numOfDays = getNumOfDays();
            this.duties = new ArrayList<>(duties);
            this.persons = new ArrayList<>();
        } else {
            throw new InputMismatchException("Invalid Month Index or first Day Index");
        }
    }

    public void addDutyPersons(List<Person> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Import all Persons involved in duty scheduling for the month
     */
    private void importPersons(UniquePersonList personList) {
        for (Person person : personList) {
            this.persons.add(person);
        }
    }


    /**
     * Initialize the all duties for the month. Done during construction.
     */
    private void generateDuties() {
        int weekCounter = this.firstDayWeekIndex;
        for (int day = 1; day <= this.numOfDays; day++) {
            if (weekCounter >= 1 && weekCounter <= 4) {
                this.duties.add(new DutyTypeA(this.monthIndex, day, weekCounter));
            } else if (weekCounter == 5) {
                this.duties.add(new DutyTypeB(this.monthIndex, day, weekCounter));
            } else {
                this.duties.add(new DutyTypeC(this.monthIndex, day, weekCounter));
            }
            weekCounter++;
            if (weekCounter == 7) {
                weekCounter = 1;
            }
        }
    }

    /**
     * The scheduler method.
     * Called to schedule duties for the month.
     * Attempts to match Duties with Persons.
     */
    public void schedule() {
        List<Duty> dutyList = this.arrangeDuties();
        PriorityQueue<Person> personQueue = this.arrangePersons();
        for (Duty duty : dutyList) {
            if (personQueue.isEmpty()) {
                continue;
            }

            while (!duty.isFilled()) {
                boolean matchExist = true;
                int originalVacancies = duty.getNumOfVacancies();

                Person currPerson = personQueue.poll();
                List<Person> tempList = new ArrayList<>();

                while (!this.isAssignable(duty, currPerson)) {
                    if (personQueue.isEmpty()) {
                        matchExist = false;
                        break;
                    }
                    tempList.add(currPerson);
                    currPerson = personQueue.poll();
                }
                personQueue.addAll(tempList);

                if (matchExist) {
                    this.assign(duty, currPerson);
                }

                // Add current person back into the priority queue only after assignment
                personQueue.add(currPerson);

                if (duty.getNumOfVacancies() == originalVacancies) {
                    break;
                }
            }

        }
    }

    /**
     * Assign a Duty to a Person
     */
    private void assign(Duty duty, Person person) {
        duty.addPerson(person);
        person.addDuty(duty);
    }

    /**
     * Check whether a Duty a assignable to a Person taking into consideration:
     * 1) Whether the duty is already filled
     * 2) Whether the duty already assigned to the person / the person already assigned the duty
     * 3) Whether the person blocked that duty date
     */
    private boolean isAssignable(Duty duty, Person person) {
        if (duty.isFilled()) {
            return false;
        } else if (duty.getPersons().contains(person)) {
            return false;
        } else if (person.getDuties().contains(duty)) {
            return false;
        } else if (person.getBlockedDates().contains(duty.getDayIndex())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Returns a list of duties with the following considerations
     * 1) Type A duties are placed at the  front of the list followed by Type B then Type C
     * 2) Amongst each of the types, the duties are shuffled
     */
    private List<Duty> arrangeDuties() {
        List<Duty> dutiesTypeA = this.duties.stream()
                .filter(duty -> duty instanceof DutyTypeA)
                .collect(Collectors.toList());
        Collections.shuffle(dutiesTypeA);

        List<Duty> dutiesTypeB = this.duties.stream()
                .filter(duty -> duty instanceof DutyTypeB)
                .collect(Collectors.toList());
        Collections.shuffle(dutiesTypeB);

        List<Duty> dutiesTypeC = this.duties.stream()
                .filter(duty -> duty instanceof DutyTypeC)
                .collect(Collectors.toList());
        Collections.shuffle(dutiesTypeC);

        List<Duty> arrangedList = new ArrayList<>();
        arrangedList.addAll(dutiesTypeA);
        arrangedList.addAll(dutiesTypeB);
        arrangedList.addAll(dutiesTypeC);

        return arrangedList;

    }

    /**
     * Returns are priority queue that ranks the Persons based on their accumulated duty points
     */
    private PriorityQueue<Person> arrangePersons() {
        PriorityQueue<Person> pq = new PriorityQueue<>(Comparator.comparingInt(Person::getDutyPoints));
        pq.addAll(this.persons);
        return pq;
    }

    /**
     * Gets current month in String format
     */
    public String getMonth() {
        String[] months = {"January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November", "December"};
        return months[this.monthIndex - 1];
    }

    /**
     * Returns current month
     */
    public boolean isCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        int currentMonth = cal.get(Calendar.MONTH);
        return (this.monthIndex == currentMonth);
    }

    /**
     * Gets the number of days in the current month
     */
    private int getNumOfDays() {
        if (Arrays.asList(1, 3, 5, 7, 8, 10, 12).contains(this.monthIndex)) {
            return 31;
        } else if (Arrays.asList(4, 6, 9, 11).contains(this.monthIndex)) {
            return 30;
        } else if (this.monthIndex == 2) {
            return 28;
        } else {
            throw new InputMismatchException("Invalid Month Index.");
        }
    }

    public int getMonthIndex() {
        return monthIndex;
    }

    public int getFirstDayWeekIndex() {
        return firstDayWeekIndex;
    }

    public List<Duty> getDuties() {
        return duties;
    }
}
