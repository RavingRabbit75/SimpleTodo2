package com.raychow.simpletodo;

import android.app.DialogFragment;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TimePicker;


/**
 * Created by raymondchow on 8/15/15.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private static final String LOGTAG = "Time Picker Fragment";

    public static final String DUEHOUR = "Due Hour";
    public static final String DUEMINUTE = "Due Minute";
    public static final String DUEAMPM = "Due AMPM";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker

        Bundle mArgs = getArguments();
        int hour = mArgs.getInt(DUEHOUR);
        int minute = mArgs.getInt(DUEMINUTE);
        int ampm = mArgs.getInt(DUEAMPM);

        if (ampm == 1) {
            hour = hour + 12;
        }

        Log.i(LOGTAG, String.valueOf(hour));

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        EditItemActivity callingActivity = (EditItemActivity) getActivity();
        callingActivity.onEnterTimePicked(hourOfDay, minute);
    }
}