package seedu.address.model.calendar;
/**
 *  Days that are used in duty allocations
 *  <li>{@link #MONDAY}</li>
 *  <li>{@link #TUESDAY}</li>
 *  <li>{@link #WEDNESDAY}</li>
 *  <li>{@link #THURSDAY}</li>
 *  <li>{@link #FRIDAY}</li>
 *  <li>{@link #SATURDAY}</li>
 *  <li>{@link #SUNDAY}</li>
 */
public enum Day {
    MONDAY (1),
    TUESDAY (2),
    WEDNESDAY (3),
    THURSDAY (4),
    FRIDAY (5),
    SATURDAY (6),
    SUNDAY (7);

    private int index;

    Day(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }
}
