/*A temporary class used for intermediate testing. Pls delete before submission.*/

package seedu.address.model.duty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Company;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Rank;
import seedu.address.model.person.Section;
import seedu.address.model.tag.Tag;

/**
 * A temporary class to be deleted before submission
 */
public class TempClass {

    private Nric nric1 = new Nric("S9875493J");
    private Nric nric2 = new Nric("S8798473L");
    private Nric nric3 = new Nric("S8698443K");

    private Name name1 = new Name("John1");
    private Name name2 = new Name("John2");
    private Name name3 = new Name("John3");

    private Company company1 = new Company("Alpha");
    private Company company2 = new Company("Beta");
    private Company company3 = new Company("Gamma");

    private Section section1 = new Section("1");
    private Section section2 = new Section("2");
    private Section section3 = new Section("3");

    private Phone phone1 = new Phone("09403942");
    private Phone phone2 = new Phone("798749374");
    private Phone phone3 = new Phone("473974983");
    private Rank rank1 = new Rank("REC");
    private Rank rank2 = new Rank("CPL");
    private Rank rank3 = new Rank("MAJ");

    private Set<Tag> tags = new HashSet<>();

    private Person person1 = new Person(nric1, company1, section1, rank1, name1, phone1, tags);
    private Person person2 = new Person(nric2, company2, section2, rank2, name2, phone2, tags);
    private Person person3 = new Person(nric3, company3, section3, rank3, name3, phone3, tags);

    private List<Person> persons = new ArrayList<>();

    private LocalDate date = LocalDate.now();

    private Duty duty;

    public TempClass() {
        persons.add(person1);
        persons.add(person2);
        persons.add(person3);
    }

    public ObservableList<Person> getDutyPersons() {
        return FXCollections.observableArrayList(persons);
    }
}
