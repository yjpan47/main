package seedu.address.model.duty;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

import seedu.address.model.person.Person;
import seedu.address.commons.util.DateUtil;

public class DutyMonth {

    private List<Duty> scheduledDuties;

    private int year;
    private int monthIndex;
    private int firstDayOfWeekIndex;
    private HashMap<Person, List<Integer>> blockedDays;

    public DutyMonth(int year, int monthIndex, int firstDayOfWeekIndex) {
        if (DateUtil.isValidYear(year) && DateUtil.isValidMonth(monthIndex)
                && DateUtil.isValidDayOfWeek(firstDayOfWeekIndex)) {
            this.year = year;
            this.monthIndex = monthIndex;
            this.firstDayOfWeekIndex = firstDayOfWeekIndex;
            this.blockedDays = new HashMap<>();
        } else {
            throw new InvalidParameterException("Invalid Date");
        }
    }

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

    public void schedule(List<Person> persons) {

        // Temporary Storage for points earned
        HashMap<Person, Integer> points = new HashMap<>();
        for (Person person : persons) {
            points.put(person, DutyStorage.dutyPoints.getOrDefault(person, 0));
        }

        // List of Duties
        List<Duty> dutyList = generateAllDuties();

        // Priority Queue of Persons
        PriorityQueue<Person> personQueue = new PriorityQueue<>(Comparator.comparingInt(points::get));
        personQueue.addAll(persons);



        if (personQueue.isEmpty()) {
            this.scheduledDuties = dutyList;
            this.scheduledDuties.sort(Comparator.comparingInt(Duty::getDayIndex));
        }

        for (Duty duty : dutyList) {
            while (! duty.isFilled()) {
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
                personQueue.add(person);

                if (hasAssignable) {
                    duty.addPerson(person);
                    points.replace(person, points.get(person) + duty.getPoints());

                } else {
                    break;
                }
            }
        }
        this.scheduledDuties = dutyList;
        this.scheduledDuties.sort(Comparator.comparingInt(Duty::getDayIndex));
    }

    private boolean isAssignable(Person person, Duty duty) {
        if (duty.isFilled()) {
            return false;
        } else if ((duty.getPersons().contains(person))) {
            return false;
        } else return !this.blockedDays.containsKey(person)
                || !this.blockedDays.get(person).contains(duty.getDayIndex());
    }

    private List<Duty> generateAllDuties() {
        List<Duty> duties = new ArrayList<>();
        int dayOfWeek = this.getFirstDayOfWeekIndex();
        for (int day = 1; day <= this.getNumOfDays(); day++) {
            int capacity = DutySettings.getCapacity(this.monthIndex, day, dayOfWeek);
            int points = DutySettings.getPoints(this.monthIndex, day, dayOfWeek);
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

    public void printScheduledDuties() {
        for (Duty duty : this.getScheduledDuties()) {
            System.out.print(String.format("%s (%s) : [", duty.getDayIndex(), duty.getPoints()));
            for (Person person : duty.getPersons()) {
                System.out.print(String.format("%s (%s), ", person, DutyStorage.dutyPoints.get(person)));
            }
            System.out.println("]");
        }
    }
}
