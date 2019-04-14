package seedu.address.logic.commands;

//import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccessGeneral;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.ALICE_NRIC;
import static seedu.address.testutil.TypicalPersons.getTypicalPersonnelDatabaseWithDuties;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;

//import seedu.address.commons.core.index.Index;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.duty.Duty;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ViewSwapsCommand.
 */
public class ViewSwapsCommandTest {

    private Model model = new ModelManager(getTypicalPersonnelDatabaseWithDuties(), new UserPrefs());

    @Test
    public void execute() {
        List<Duty> duties = model.getDutyCalendar().getNextMonth().getScheduledDuties();
        int nextMonthIndex = model.getDutyCalendar().getNextMonth().getMonthIndex() + 1;
        int nextMonthYear = model.getDutyCalendar().getNextMonth().getYear();
        int allocatedDayIndex = 0;
        LocalDate allocatedDate = null;
        LocalDate requestedDate = null;
        Person accepter = null;
        for (Duty duty : duties) {
            if (duty.getPersons().contains(ALICE)) {
                allocatedDayIndex = duty.getDayIndex();
                allocatedDate = LocalDate.of(nextMonthYear, nextMonthIndex, allocatedDayIndex);
                break;
            }
        }
        for (Duty duty : duties) {
            if (!duty.getPersons().contains(ALICE)) {
                requestedDate = LocalDate.of(nextMonthYear, nextMonthIndex, duty.getDayIndex());
                for (Person person : duty.getPersons()) {
                    if (!duties.get(allocatedDayIndex - 1).getPersons().contains(person)) {
                        accepter = person;
                        break;
                    }
                }
                break;
            }
        }
        CommandHistory ch = new CommandHistory();
        assertCommandSuccessGeneral(new SwapCommand(allocatedDate, requestedDate, ALICE_NRIC), model,
                ch, new CommandResult(SwapCommand.MESSAGE_SUCCESS), model);
        //assertCommandSuccessGeneral(new AcceptSwapCommand(accepter.getNric().toString(), Index.fromOneBased(1)),
        // model, ch, new CommandResult(AcceptSwapCommand.MESSAGE_SUCCESS), model);

        StringBuilder sb = new StringBuilder();
        sb.append(ViewSwapsCommand.MESSAGE_SUCCESS);
        sb.append("1. ").append(model.getPersonnelDatabase().getRequestList().get(0).toString()).append("\n");
        sb.append("\nEnter approveSwap INDEX to approve or rejectSwap INDEX to reject.\n");

        //assertCommandSuccess(new ViewSwapsCommand(), model, ch, new CommandResult(sb.toString()), model);
    }
}
