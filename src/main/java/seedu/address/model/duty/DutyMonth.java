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

import seedu.address.model.person.Person;
import seedu.address.commons.util.DateUtil;

public class DutyMonth {

    private boolean confirmed = false;
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

    public DutyMonth(int year, int monthIndex, int firstDayOfWeekIndex, DutySettings dutySettings) {
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

    public DutyMonth(int year, int monthIndex, int firstDayOfWeekIndex,
                     List<Duty> duties, HashMap<Person, List<Integer>> blockedDays) {
        this.year = year;
        this.monthIndex = monthIndex;
        this.firstDayOfWeekIndex = firstDayOfWeekIndex;
        this.scheduledDuties = new ArrayList<>(duties);
        this.blockedDays = new HashMap<>(blockedDays);
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

    public void schedule(List<Person> persons, DutySettings dutySettings, DutyStorage dutyStorage) {

        // Temporary Storage for points earned
        HashMap<Person, Integer> points = new HashMap<>();
        for (Person person : persons) {
            points.put(person, dutyStorage.dutyPoints.getOrDefault(person, 0));
        }

        // List of Duties
        List<Duty> dutyList = generateAllDuties(dutySettings);

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

    public String printPoints(DutyStorage dutyStorage) {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Points Awarded ----\n");

        Set<Person> PersonsThisMonth = new HashSet<>();
        for (Duty duty : this.scheduledDuties) {
            PersonsThisMonth.addAll(duty.getPersons());
        }

        for (Person person : PersonsThisMonth) {
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
        confirmed = true;
    }
}
