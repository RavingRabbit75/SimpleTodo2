package com.raychow.simpletodo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.raychow.simpletodo.TodoItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raymondchow on 7/24/15.
 */
public class TodoDataSource {

    // Logcat tags
    private static final String LOGTAG = "Todo Data Source";

    // Constants
    private static final String[] allColumns = {
        TodoDBOpenHelper.COLUMN_ID,
        TodoDBOpenHelper.COLUMN_DESCRIPTION,
        TodoDBOpenHelper.COLUMN_COMPLETIONSTATUS,
        TodoDBOpenHelper.COLUMN_PRIORITY,
        TodoDBOpenHelper.COLUMN_HASDUEDATE,
        TodoDBOpenHelper.COLUMN_DUEMONTH,
        TodoDBOpenHelper.COLUMN_DUEDATE,
        TodoDBOpenHelper.COLUMN_DUEYEAR,
        TodoDBOpenHelper.COLUMN_DUEHOUR,
        TodoDBOpenHelper.COLUMN_DUEMINUTE,
        TodoDBOpenHelper.COLUMN_DUEAMPM,
    };

    // database related variables
    SQLiteOpenHelper dbhelper;
    SQLiteDatabase database;



    public TodoDataSource(Context context) {
        dbhelper = new TodoDBOpenHelper(context);
    }

    public void open() {
        Log.i(LOGTAG, "Database opened");
        database = dbhelper.getWritableDatabase();
    }

    public void close() {
        Log.i(LOGTAG, "Database closed");
        dbhelper.close();
    }

    public TodoItem createNewTodoItem(TodoItem todoItem) {
        ContentValues values = new ContentValues();
        values.put(TodoDBOpenHelper.COLUMN_DESCRIPTION, todoItem.getTodoDescription());
        values.put(TodoDBOpenHelper.COLUMN_COMPLETIONSTATUS, todoItem.getTodoCompletedStatus());
        values.put(TodoDBOpenHelper.COLUMN_PRIORITY, todoItem.getTodoPriority());
        values.put(TodoDBOpenHelper.COLUMN_HASDUEDATE, todoItem.getDueDateAvailability());
        values.put(TodoDBOpenHelper.COLUMN_DUEMONTH, todoItem.getTodoDueMonth());
        values.put(TodoDBOpenHelper.COLUMN_DUEDATE, todoItem.getTodoDueDate());
        values.put(TodoDBOpenHelper.COLUMN_DUEYEAR, todoItem.getTodoDueYear());
        values.put(TodoDBOpenHelper.COLUMN_DUEHOUR, todoItem.getTodoDueHour());
        values.put(TodoDBOpenHelper.COLUMN_DUEMINUTE, todoItem.getTodoDueMinute());
        values.put(TodoDBOpenHelper.COLUMN_DUEAMPM, todoItem.getTodoDueAMPM());
        long insertid = database.insert(TodoDBOpenHelper.TABLE_TODO, null, values);
        todoItem.setId(insertid);
        return todoItem;
    }

    public boolean updateTodoItem(TodoItem todoItem) {
        String where = TodoDBOpenHelper.COLUMN_ID + "=" + todoItem.getId();
        ContentValues values = new ContentValues();
        values.put(TodoDBOpenHelper.COLUMN_DESCRIPTION, todoItem.getTodoDescription());
        values.put(TodoDBOpenHelper.COLUMN_COMPLETIONSTATUS, todoItem.getTodoCompletedStatus());
        values.put(TodoDBOpenHelper.COLUMN_PRIORITY, todoItem.getTodoPriority());
        values.put(TodoDBOpenHelper.COLUMN_HASDUEDATE, todoItem.getDueDateAvailability());
        values.put(TodoDBOpenHelper.COLUMN_DUEMONTH, todoItem.getTodoDueMonth());
        values.put(TodoDBOpenHelper.COLUMN_DUEDATE, todoItem.getTodoDueDate());
        values.put(TodoDBOpenHelper.COLUMN_DUEYEAR, todoItem.getTodoDueYear());
        values.put(TodoDBOpenHelper.COLUMN_DUEHOUR, todoItem.getTodoDueHour());
        values.put(TodoDBOpenHelper.COLUMN_DUEMINUTE, todoItem.getTodoDueMinute());
        values.put(TodoDBOpenHelper.COLUMN_DUEAMPM, todoItem.getTodoDueAMPM());
        int result = database.update(TodoDBOpenHelper.TABLE_TODO, values, where, null);

        return (result == 1);
    }

    public boolean deleteTodoItem(TodoItem todoItem) {
        String where = TodoDBOpenHelper.COLUMN_ID + "=" + todoItem.getId();
        int result = database.delete(TodoDBOpenHelper.TABLE_TODO, where, null);
        return (result == 1);
    }

    public List<TodoItem> findAll() {
        List<TodoItem> todoItems = new ArrayList<>();

        Cursor cursor = database.query(TodoDBOpenHelper.TABLE_TODO, allColumns, null, null, null, null, null);
        Log.i(LOGTAG, "Returned " + cursor.getCount() + " rows");

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                TodoItem todoItem = new TodoItem();
                todoItem.setId(cursor.getLong(cursor.getColumnIndex(TodoDBOpenHelper.COLUMN_ID)));
                todoItem.setTodoDescription(cursor.getString(cursor.getColumnIndex(TodoDBOpenHelper.COLUMN_DESCRIPTION)));
                todoItem.setTodoCompletedStatus(cursor.getInt(cursor.getColumnIndex(TodoDBOpenHelper.COLUMN_COMPLETIONSTATUS)));
                todoItem.setTodoPriority(cursor.getInt(cursor.getColumnIndex(TodoDBOpenHelper.COLUMN_PRIORITY)));
                todoItem.setDueDateAvailability(cursor.getInt(cursor.getColumnIndex(TodoDBOpenHelper.COLUMN_HASDUEDATE)));

                todoItem.setTodoDueDate(cursor.getInt(cursor.getColumnIndex(TodoDBOpenHelper.COLUMN_DUEMONTH)),
                                        cursor.getInt(cursor.getColumnIndex(TodoDBOpenHelper.COLUMN_DUEDATE)),
                                        cursor.getInt(cursor.getColumnIndex(TodoDBOpenHelper.COLUMN_DUEYEAR)));
                todoItem.setTodoDueTime(cursor.getInt(cursor.getColumnIndex(TodoDBOpenHelper.COLUMN_DUEHOUR)),
                                        cursor.getInt(cursor.getColumnIndex(TodoDBOpenHelper.COLUMN_DUEMINUTE)),
                                        cursor.getInt(cursor.getColumnIndex(TodoDBOpenHelper.COLUMN_DUEAMPM)));

                todoItems.add(todoItem);
            }
        }

        return todoItems;

    }

}



