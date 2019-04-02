package seedu.address.model.duty;

import java.util.HashMap;

public class DutySettings {

    private static int[] dutyPointsInWeek = new int[7];
    static int[] dutyCapacityInWeek = new int[7];
    static HashMap<Integer, HashMap<Integer, Integer>> dutyPointsException = new HashMap<>();
    static HashMap<Integer, HashMap<Integer, Integer>> dutyCapacityException = new HashMap<>();

    public static void setDefault() {
        for (int i = 0; i < 7; i++) {
            dutyPointsInWeek[i] = 2;
            dutyCapacityInWeek[i] = 2;
        }
        dutyPointsException.clear();
        dutyCapacityException.clear();
    }

    public static void setPoints(int dayOfWeek, int points) {
        dutyPointsInWeek[dayOfWeek - 1] = points;
    }

    public static void setPoints(int month, int day, int points) {
        dutyPointsException.putIfAbsent(month, new HashMap<>());
        dutyPointsException.get(month).put(day, points);
    }

    public static void setCapacity(int dayOfWeek, int capacity) {
        dutyCapacityInWeek[dayOfWeek - 1] = capacity;
    }

    public static void setCapacity(int month, int day, int capacity) {
        dutyCapacityException.putIfAbsent(month, new HashMap<>());
        dutyCapacityException.get(month).put(day, capacity);
    }

    public static int getPoints(int month, int day, int dayOfWeek) {
        if (dutyPointsException.containsKey(month) && dutyPointsException.get(month).containsKey(day)) {
            return dutyPointsException.get(month).get(day);
        } else {
            return dutyPointsInWeek[dayOfWeek - 1];
        }
    }

    public static int getCapacity(int month, int day, int dayOfWeek) {
        if (dutyCapacityException.containsKey(month) && dutyCapacityException.get(month).containsKey(day)) {
            return dutyCapacityException.get(month).get(day);
        } else {
            return dutyCapacityInWeek[dayOfWeek - 1];
        }
    }
}
