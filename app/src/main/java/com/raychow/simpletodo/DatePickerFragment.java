package com.raychow.simpletodo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;


/**
 * Created by raymondchow on 8/15/15.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private static final String LOGTAG = "Date Picker Fragment";

    public static final String DUEMONTH = "Due Month";
    public static final String DUEDATE = "Due Date";
    public static final String DUEYEAR = "Due Year";


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker

        Bundle mArgs = getArguments();
        int year = mArgs.getInt(DUEYEAR);
        int month = mArgs.getInt(DUEMONTH);
        int day = mArgs.getInt(DUEDATE);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        EditItemActivity callingActivity = (EditItemActivity) getActivity();
        callingActivity.onEnterDatePicked(year, month, day);
    }

}

