package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RANK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SECTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERTYPE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UiCommandInteraction;
import seedu.address.commons.core.UserType;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.duty.Duty;
import seedu.address.model.duty.DutyMonth;
import seedu.address.model.duty.DutyStorage;
import seedu.address.model.person.Company;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Password;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Rank;
import seedu.address.model.person.Section;
import seedu.address.model.request.Request;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the personnel database.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE_ADMIN = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NRIC + "NRIC] "
            + "[" + PREFIX_COMPANY + "COMPANY] "
            + "[" + PREFIX_SECTION + "SECTION] "
            + "[" + PREFIX_RANK + "RANK] "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_TAG + "TAG] "
            + "[" + PREFIX_PASSWORD + "PASSWORD] "
            + "[" + PREFIX_USERTYPE + "USERTYPE] ...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_RANK + "CFC "
            + PREFIX_USERTYPE + "A";;

    public static final String MESSAGE_USAGE_GENERAL = COMMAND_WORD + ": Edits the details of yourself. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + "[" + PREFIX_COMPANY + "COMPANY] "
            + "[" + PREFIX_SECTION + "SECTION] "
            + "[" + PREFIX_RANK + "RANK] "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_TAG + "TAG] "
            + "[" + PREFIX_PASSWORD + "PASSWORD] ...\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_PHONE + "91234567 " + PREFIX_RANK + "CFC";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the personnel database.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;
    private final String userName;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     * @param userName of person used to enter the command
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor, String userName) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);
        requireNonNull(userName);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
        this.userName = userName;
    }

    /**
     * @param editPersonDescriptor details to edit the person with
     * @param userName of person used to enter the command
     */
    public EditCommand(EditPersonDescriptor editPersonDescriptor, String userName) {
        requireNonNull(editPersonDescriptor);
        requireNonNull(userName);

        this.index = null;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
        this.userName = userName;
    }

    @Override
    public CommandResult executeAdmin(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        boolean editedNricUserType = false;
        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);
        String personToEditUserName = personToEdit.getNric().value;
        String editedPersonUserName = editedPerson.getNric().value;
        UserType persontoEditUserType = personToEdit.getUserType();
        UserType editedPersonUserType = editedPerson.getUserType();
        if (personToEditUserName.equals(userName)
                && (!editedPersonUserName.equals(personToEditUserName)
                || !editedPersonUserType.equals(persontoEditUserType))) {
            editedNricUserType = true;
        }

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        DutyMonth dutyMonth = model.getDutyCalendar().getNextMonth();
        DutyStorage dutyStorage = model.getDutyStorage();
        for (Duty duty : dutyMonth.getScheduledDuties()) {
            if (duty.contains(personToEdit)) {
                duty.replacePerson(personToEdit, editedPerson);
            }
        }
        dutyStorage.replacePerson(personToEdit, editedPerson);

        List<Request> requests = model.getPersonnelDatabase().getRequestList();
        for (Request req : requests) {
            if (req.getRequester().equals(personToEdit)) {
                req.setRequester(editedPerson);
            } else if (req.getAccepter().equals(personToEdit)) {
                req.setAccepter(editedPerson);
            }
        }

        model.commitPersonnelDatabase();
        if (editedNricUserType) {
            return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedPerson),
                    UiCommandInteraction.EXIT);
        }
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));
    }

    @Override
    public CommandResult executeGeneral(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        Person personToEdit = model.findPerson(userName);
        if (personToEdit == null) {
            throw new CommandException(Messages.MESSAGE_USER_NOT_FOUND);
        }
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitPersonnelDatabase();
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Nric updatedNric = editPersonDescriptor.getNric().orElse(personToEdit.getNric());
        Company updatedCompany = editPersonDescriptor.getCompany().orElse(personToEdit.getCompany());
        Section updatedSection = editPersonDescriptor.getSection().orElse(personToEdit.getSection());
        Rank updatedRank = editPersonDescriptor.getRank().orElse(personToEdit.getRank());
        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());
        Password password = editPersonDescriptor.getPassword().orElse(personToEdit.getPassword());
        UserType userType = editPersonDescriptor.getUserType().orElse(personToEdit.getUserType());
        return new Person(updatedNric, updatedCompany, updatedSection, updatedRank, updatedName,
                updatedPhone, updatedTags, password, userType);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        if (index == null) {
            if (e.index == null) {
                return editPersonDescriptor.equals(e.editPersonDescriptor)
                        && userName.equals(e.userName);
            }
            return false;
        }
        return index.equals(e.index)
                && editPersonDescriptor.equals(e.editPersonDescriptor)
                && userName.equals(e.userName);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Nric nric;
        private Company company;
        private Section section;
        private Rank rank;
        private Name name;
        private Phone phone;
        private Set<Tag> tags;
        private Password password;
        private UserType userType;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setNric(toCopy.nric);
            setCompany(toCopy.company);
            setSection(toCopy.section);
            setRank(toCopy.rank);
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setTags(toCopy.tags);
            setPassword(toCopy.password);
            setUserType(toCopy.userType);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(nric, company, section, rank, name, phone, tags, password, userType);
        }

        public void setNric(Nric nric) {
            this.nric = nric;
        }

        public Optional<Nric> getNric() {
            return Optional.ofNullable(nric);
        }

        public void setCompany(Company company) {
            this.company = company;
        }

        public Optional<Company> getCompany() {
            return Optional.ofNullable(company);
        }

        public void setSection(Section section) {
            this.section = section;
        }

        public Optional<Section> getSection() {
            return Optional.ofNullable(section);
        }

        public void setRank(Rank rank) {
            this.rank = rank;
        }

        public Optional<Rank> getRank() {
            return Optional.ofNullable(rank);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        public void setPassword(Password password) {
            this.password = password;
        }

        public Optional<Password> getPassword() {
            return Optional.ofNullable(password);
        }

        public void setUserType(UserType userType) {
            this.userType = userType;
        }

        public Optional<UserType> getUserType() {
            return Optional.ofNullable(userType);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditPersonDescriptor e = (EditPersonDescriptor) other;
            return getNric().equals(e.getNric())
                    && getCompany().equals(e.getCompany())
                    && getSection().equals(e.getSection())
                    && getRank().equals(e.getRank())
                    && getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getTags().equals(e.getTags())
                    && getPassword().equals(e.getPassword())
                    && getUserType().equals(e.getUserType());
        }
    }
}
