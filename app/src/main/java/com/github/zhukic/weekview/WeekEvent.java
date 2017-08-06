package com.github.zhukic.weekview;

/**
 * @author Vladislav Zhukov (https://github.com/zhukic)
 */

public class WeekEvent {

    private final String name;

    private final int startHour;

    private final int endHour;

    private final int dayOfWeek;

    public WeekEvent(String name, int startHour, int endHour, int dayOfWeek) {
        this.name = name;
        this.startHour = startHour;
        this.endHour = endHour;
        this.dayOfWeek = dayOfWeek;
    }

    public String getName() {
        return name;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

}
