package com.raychow.simpletodo;

import java.util.Calendar;

import android.util.Log;

/**
 * Created by raymondchow on 8/14/15.
 */
public final class ConvertUtil {

    // Logcat tags
    private static final String LOGTAG = "Convert Util";

    private ConvertUtil() {

    }

    public static String returnFormattedDueDate(int todo_dueYear, int todo_dueMonth, int todo_dueDate) {
        Calendar tempDueDate = getTodoCalendarObject(todo_dueYear, todo_dueMonth, todo_dueDate);

        String year = String.valueOf(todo_dueYear);
        String month = String.valueOf(todo_dueMonth+1);
        String day = String.valueOf(todo_dueDate);

        int dayOfWeek = tempDueDate.get(Calendar.DAY_OF_WEEK);
        String dayOfWeekString;

        switch (dayOfWeek) {
            case 1:
                dayOfWeekString = "Sun";
                break;
            case 2:
                dayOfWeekString = "Mon";
                break;
            case 3:
                dayOfWeekString = "Tue";
                break;
            case 4:
                dayOfWeekString = "Wed";
                break;
            case 5:
                dayOfWeekString = "Thu";
                break;
            case 6:
                dayOfWeekString = "Fri";
                break;
            case 7:
                dayOfWeekString = "Sat";
                break;
            default:
                dayOfWeekString = "Invalid day of the week";
                break;
        }

        String stringDate = dayOfWeekString + " " + month + "/" + day + "/" + year;
        return stringDate;

    }


    public static String returnFormattedDueTime(int todo_dueYear, int todo_dueMonth, int todo_dueDate, int todo_dueHour, int todo_dueMinute, int todo_dueAMPM) {

        Calendar tempDueDate = getTodoCalendarObject(todo_dueYear, todo_dueMonth, todo_dueDate, todo_dueHour, todo_dueMinute, todo_dueAMPM);

        String hour;
        if (tempDueDate.get(Calendar.HOUR) == 0) {
            hour = "12";
        } else {
            hour = String.valueOf(tempDueDate.get(Calendar.HOUR));
        }

        int amPm = tempDueDate.get(Calendar.AM_PM);

        String amPmString;

        String minute;
        if (tempDueDate.get(Calendar.MINUTE) < 10) {
            minute = "0" + String.valueOf(tempDueDate.get(Calendar.MINUTE));
        } else {
            minute = String.valueOf(tempDueDate.get(Calendar.MINUTE));
        }

        switch (amPm) {
            case 0:
                amPmString = "AM";
                break;
            case 1:
                amPmString = "PM";
                break;
            default:
                amPmString = "Invalid AM/PM";
                break;
        }


        String stringTime = hour + ":" + minute + " " + amPmString;


        Log.i(LOGTAG, stringTime);
        return stringTime;
    }



    private static Calendar getTodoCalendarObject(int todo_dueYear, int todo_dueMonth, int todo_dueDate) {

        int todo_dueHour = 0;
        int todo_dueMinute = 0;
        int todo_dueAMPM = 0;

        Calendar tempDate = createCalendarObj(todo_dueYear, todo_dueMonth, todo_dueDate, todo_dueHour, todo_dueMinute, todo_dueAMPM);

        return tempDate;
    }

    private static Calendar getTodoCalendarObject(int todo_dueYear, int todo_dueMonth, int todo_dueDate, int todo_dueHour, int todo_dueMinute, int todo_dueAMPM) {

        Calendar tempDate = createCalendarObj(todo_dueYear, todo_dueMonth, todo_dueDate, todo_dueHour, todo_dueMinute, todo_dueAMPM);

        return tempDate;
    }


    private static Calendar createCalendarObj(int todo_dueYear, int todo_dueMonth, int todo_dueDate, int todo_dueHour, int todo_dueMinute, int todo_dueAMPM) {

        Calendar tempDate = Calendar.getInstance();
        tempDate.clear();
        tempDate.set(Calendar.YEAR, todo_dueYear);
        tempDate.set(Calendar.MONTH, todo_dueMonth);
        tempDate.set(Calendar.DATE, todo_dueDate);
        tempDate.set(Calendar.HOUR, todo_dueHour);
        tempDate.set(Calendar.MINUTE, todo_dueMinute);
        tempDate.set(Calendar.AM_PM, todo_dueAMPM);

        return tempDate;
    }

}
