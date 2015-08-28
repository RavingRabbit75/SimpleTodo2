package com.raychow.simpletodo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by raymondchow on 7/23/15.
 */
public class TodoDBOpenHelper extends SQLiteOpenHelper{

    // Logcat tags
    private static final String LOGTAG = "Todo DB Open Helper";

    // Constants
    private static final String DATABASE_NAME = "todoItems.db";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_TODO = "todoItems";
    public static final String COLUMN_ID = "todoItemId";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_COMPLETIONSTATUS = "completionStatus";
    public static final String COLUMN_PRIORITY = "priority";
    public static final String COLUMN_HASDUEDATE = "hasDueDate";
    public static final String COLUMN_DUEMONTH = "dueMonth";
    public static final String COLUMN_DUEDATE = "dueDate";
    public static final String COLUMN_DUEYEAR = "dueYear";
    public static final String COLUMN_DUEHOUR = "dueHour";
    public static final String COLUMN_DUEMINUTE = "dueMinute";
    public static final String COLUMN_DUEAMPM = "dueAMPM";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_TODO + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_DESCRIPTION + " TEXT, " +
            COLUMN_COMPLETIONSTATUS + " INTEGER, " +
            COLUMN_PRIORITY + " INTEGER, " +
            COLUMN_HASDUEDATE + " INTEGER, " +
            COLUMN_DUEMONTH + " INTEGER, " +
            COLUMN_DUEDATE + " INTEGER, " +
            COLUMN_DUEYEAR + " INTEGER, " +
            COLUMN_DUEHOUR + " INTEGER, " +
            COLUMN_DUEMINUTE + " INTEGER, " +
            COLUMN_DUEAMPM + " INTEGER " + ")";


    public TodoDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(LOGTAG, "On create started");
        db.execSQL(TABLE_CREATE);
        Log.i(LOGTAG, "Table has been created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
        onCreate(db);
    }


}
