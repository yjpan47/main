package seedu.address.model.calendar;

public enum Day {
    MONDAY (1),
    TUESDAY (2),
    WEDNESDAY (3),
    THURSDAY (4),
    FRIDAY (5),
    SATURDAY (6),
    SUNDAY (7);

    public int index;

    Day(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }
}
