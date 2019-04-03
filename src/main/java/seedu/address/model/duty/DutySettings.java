package seedu.address.model.duty;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

import seedu.address.commons.util.DateUtil;

public class DutySettings implements Serializable {

    private static final int[] DEFAULT_DUTY_POINTS_IN_WEEK = {4, 2 , 2 , 2 , 2 , 3 ,4};
    private static final int[] DEFAULT_DUTY_CAPACITY_IN_WEEK = {3, 2 , 2 , 2 , 2 , 2 ,3};

    private int[] dutyPointsInWeek;
    private int[] dutyCapacityInWeek;
    private HashMap<Integer, HashMap<Integer, Integer>> dutyPointsException;
    private HashMap<Integer, HashMap<Integer, Integer>> dutyCapacityException;


    public DutySettings() {
        this.dutyPointsInWeek = DEFAULT_DUTY_POINTS_IN_WEEK;
        this.dutyCapacityInWeek = DEFAULT_DUTY_CAPACITY_IN_WEEK;
        this.dutyPointsException = new HashMap<>();
        this.dutyCapacityException = new HashMap<>();
    }

    public DutySettings(int[] dutyPointsInWeek, int[] dutyCapacityInWeek) {
        this.dutyPointsInWeek = dutyCapacityInWeek;
        this.dutyCapacityInWeek = dutyCapacityInWeek;
    }

    public void setPoints(int dayOfWeek, int points) {
        this.dutyPointsInWeek[dayOfWeek - 1] = points;
    }

    public void setPoints(int month, int day, int points) {
        this.dutyPointsException.putIfAbsent(month, new HashMap<>());
        this.dutyPointsException.get(month).put(day, points);
    }

    public void setCapacity(int dayOfWeek, int capacity) {
        this.dutyCapacityInWeek[dayOfWeek - 1] = capacity;
    }

    public void setCapacity(int month, int day, int capacity) {
        this.dutyCapacityException.putIfAbsent(month, new HashMap<>());
        this.dutyCapacityException.get(month).put(day, capacity);
    }

    public int getPoints(int month, int day, int dayOfWeek) {
        if (this.dutyPointsException.containsKey(month) && this.dutyPointsException.get(month).containsKey(day)) {
            return this.dutyPointsException.get(month).get(day);
        } else {
            return this.dutyPointsInWeek[dayOfWeek - 1];
        }
    }

    public int getCapacity(int month, int day, int dayOfWeek) {
        if (this.dutyCapacityException.containsKey(month) && this.dutyCapacityException.get(month).containsKey(day)) {
            return this.dutyCapacityException.get(month).get(day);
        } else {
            return this.dutyCapacityInWeek[dayOfWeek - 1];
        }
    }

    public String printDayOfWeek() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            sb.append(String.format("%15s : [Manpower Needed = %2d] [Points Awarded = %2d]\n" ,
                    DateUtil.getDayOfWeek(i + 1), this.dutyPointsInWeek[i], this.dutyCapacityInWeek[i]));
        }
        return sb.toString();
    }
}