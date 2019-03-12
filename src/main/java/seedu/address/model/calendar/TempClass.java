/*A temporary class used for intermediate testing. Pls delete before submission.*/

package seedu.address.model.calendar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class TempClass {

    Nric nric1 = new Nric("S9875493J");
    Nric nric2 = new Nric("S8798473L");
    Nric nric3 = new Nric("S8698443K");

    Name name1 = new Name("John1");
    Name name2 = new Name("John2");
    Name name3 = new Name("John3");

    Company company1 = new Company("Alpha");
    Company company2 = new Company("Beta");
    Company company3 = new Company("Gamma");

    Section section1 = new Section("1");
    Section section2 = new Section("2");
    Section section3 = new Section("3");

    Rank rank1 = new Rank("REC");
    Rank rank2 = new Rank("CPL");
    Rank rank3 = new Rank("MAJ");

    Phone phone1 = new Phone("09403942");
    Phone phone2 = new Phone("798749374");
    Phone phone3 = new Phone("473974983");

    Set<Tag> tags = new HashSet<>();

    Person person1 = new Person(nric1, company1, section1, rank1, name1, phone1, tags);
    Person person2 = new Person(nric2, company2, section2, rank2, name2, phone2, tags);
    Person person3 = new Person(nric3, company3, section3, rank3, name3, phone3, tags);

    public List<Person> persons = new ArrayList<>();

    public LocalDate date = LocalDate.now();

    public Duty duty;

    public TempClass() {
        persons.add(person1);
        persons.add(person2);
        persons.add(person3);
        duty = new Duty(date, Meridiem.AM);
        duty.addDutyMen(persons);
    }

    public ObservableList<Person> getDutyPersons() {
        return FXCollections.observableArrayList(persons);
    }
}
