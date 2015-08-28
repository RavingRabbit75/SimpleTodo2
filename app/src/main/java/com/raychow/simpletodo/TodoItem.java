package com.raychow.simpletodo;

import android.util.Log;


/**
 * Created by Raymond Chow on 6/20/15.
 */

public class TodoItem {

    // Logcat tags
    private static final String LOGTAG = "Todo Item";

    // To-do item properties
    private long id;
    private String todo_description;
    private int todo_priority;
    private int todo_Completed;
    private int todo_dueDateAvailable;
    private int todo_dueMonth;
    private int todo_dueDate;
    private int todo_dueYear;
    private int todo_dueHour;
    private int todo_dueMinute;
    private int todo_dueAMPM;


    public TodoItem() {
        todo_description = "No Description";
        todo_priority = 0; // 0 = none, 1 = low, 2 = medium, 3 = high
        todo_dueDateAvailable = 0; // 0 = no due date, 1 = there is a due date
        todo_dueMonth = 0; // 0-11, 0 = January
        todo_dueDate = 1; // 1 = first day of month
        todo_dueYear = 2000;
        todo_dueHour = 0; // 0-11, 0 = 12
        todo_dueMinute = 0; // 0-59
        todo_dueAMPM = 0; // 0 = AM, 1 = PM
        todo_Completed = 0; // 0 = not completed, 1 = completed
    }


    ////////////// Getters and Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTodoDescription() {
        return todo_description;
    }

    public void setTodoDescription(String tempDescription) {
        todo_description = tempDescription;
    }

    public String getTodoDueDateString() {
        String fullDate = ConvertUtil.returnFormattedDueDate(todo_dueYear, todo_dueMonth, todo_dueDate);
        Log.i(LOGTAG, fullDate);
        return fullDate;
    }

    public void setTodoDueDate(int month, int date, int year) {
        todo_dueMonth = month;
        todo_dueDate = date;
        todo_dueYear = year;
    }

    public int getTodoDueMonth() {
        return todo_dueMonth;
    }

    public int getTodoDueDate() {
        return todo_dueDate;
    }

    public int getTodoDueYear() {
        return todo_dueYear;
    }

    public String getTodoDueTimeString() {

        String fullTime = ConvertUtil.returnFormattedDueTime(todo_dueYear,
                todo_dueMonth,
                todo_dueDate,
                todo_dueHour,
                todo_dueMinute,
                todo_dueAMPM);
        return fullTime;
    }

    public void setTodoDueTime(int hour, int minute, int ampm) {
        todo_dueHour = hour;
        todo_dueMinute = minute;
        todo_dueAMPM = ampm;
    }

    public int getTodoDueHour() {
        return todo_dueHour;
    }

    public int getTodoDueMinute() {
        return todo_dueMinute;
    }

    public int getTodoDueAMPM() {
        return todo_dueAMPM;
    }

    public int getTodoPriority() {
        return todo_priority;
    }

    public String getPriorityAsString() {

        String priorityString;

        switch (todo_priority) {
            case 0:
                priorityString = "No";
                break;
            case 1:
                priorityString = "Low";
                break;
            case 2:
                priorityString = "Medium";
                break;
            case 3:
                priorityString = "High";
                break;
            default:
                priorityString = "Invalid priority range";
                break;
        }

        return priorityString;
    }

    public void setTodoPriority(int tempPriority) {
        todo_priority = tempPriority;
    }

    public int getTodoCompletedStatus() {
        return todo_Completed;
    }

    public void setTodoCompletedStatus(int status) {
        todo_Completed = status;
    }

    public int getDueDateAvailability() {
        return todo_dueDateAvailable;
    }

    public void setDueDateAvailability(int hasDueDate) {
        todo_dueDateAvailable = hasDueDate;
    }


    public int getDrawableId() {
        int tempLong = 0101;
        return tempLong;
    }



}
