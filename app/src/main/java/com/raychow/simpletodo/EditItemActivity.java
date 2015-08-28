package com.raychow.simpletodo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;


import java.util.Calendar;


public class EditItemActivity extends ActionBarActivity {

    //Logcat Tags
    private final static String LOGTAG = "Edit Item Activity";

    // Constants
    public static final String DESCRIPTION = "Description";
    public static final String PRIORITY = "Priority";
    public static final String HASDUEDATE = "Has a Due Date";
    public static final String DUEMONTH = "Due Month";
    public static final String DUEDATE = "Due Date";
    public static final String DUEYEAR = "Due Year";
    public static final String DUEHOUR = "Due Hour";
    public static final String DUEMINUTE = "Due Minute";
    public static final String DUEAMPM = "Due AMPM";


    // non-view variables
    private String todo_description;
    private int todo_priority;
    private int todo_dueDateAvailable;
    private int todo_dueMonth;
    private int todo_dueDate;
    private int todo_dueYear;
    private int todo_dueHour;
    private int todo_dueMinute;
    private int todo_dueAMPM;

    // view related variables
    private EditText mTodo_description;
    private TextView mTodo_dueDate;
    private TextView mTodo_dueTime;
    private RelativeLayout mTodo_dueDateGroup;
    private Button mEditDueDateBtn;
    private Button mEditDueTimeBtn;
    private Switch mDueDateSwitch;
    private Switch mPrioritySwitch;
    private RadioGroup mPriority_radioGroup;
    private RadioButton mPriority_radio1;
    private RadioButton mPriority_radio2;
    private RadioButton mPriority_radio3;
    private RelativeLayout mEditItemRootView;
    private ScrollView mScrollView;
    private LinearLayout mTodo_dateFieldGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        mScrollView = (ScrollView) findViewById(R.id.scrollView);
        mEditItemRootView = (RelativeLayout) findViewById(R.id.EditItemRootView);

        mTodo_description = (EditText) findViewById(R.id.etTodoDescription);
        mTodo_dueDate = (TextView) findViewById(R.id.todoDueDate);
        mTodo_dueTime = (TextView) findViewById(R.id.todoDueTime);
        mTodo_dueDateGroup = (RelativeLayout) findViewById(R.id.todoDueDateGroup);
        mTodo_dueDateGroup.setClickable(false);
        mTodo_dueDateGroup.setFocusableInTouchMode(false);

        mTodo_dateFieldGroup= (LinearLayout) findViewById(R.id.dateFieldGroup);
        mTodo_dateFieldGroup.setClickable(false);
        mTodo_dateFieldGroup.setFocusableInTouchMode(false);

        mEditDueDateBtn = (Button) findViewById(R.id.editDueDateBtn);
        mEditDueTimeBtn = (Button) findViewById(R.id.editDueTimeBtn);

        mDueDateSwitch = (Switch) findViewById(R.id.dueDateSwitch);

        mPrioritySwitch = (Switch) findViewById(R.id.prioritySwitch);
        mPriority_radioGroup = (RadioGroup) findViewById(R.id.priority_radioGroup);
        mPriority_radio1 = (RadioButton) findViewById(R.id.radioButton1);
        mPriority_radio2 = (RadioButton) findViewById(R.id.radioButton2);
        mPriority_radio3 = (RadioButton) findViewById(R.id.radioButton3);

        todo_description = getIntent().getStringExtra(DESCRIPTION);
        todo_priority = getIntent().getIntExtra(PRIORITY, 0);
        todo_dueDateAvailable = getIntent().getIntExtra(HASDUEDATE, 0);
        todo_dueMonth = getIntent().getIntExtra(DUEMONTH, 0);
        todo_dueDate = getIntent().getIntExtra(DUEDATE, 0);
        todo_dueYear = getIntent().getIntExtra(DUEYEAR, 0);
        todo_dueHour = getIntent().getIntExtra(DUEHOUR, 0);
        todo_dueMinute = getIntent().getIntExtra(DUEMINUTE, 0);
        todo_dueAMPM = getIntent().getIntExtra(DUEAMPM, 0);

        mTodo_description.setText(todo_description);

        mTodo_dueDate.setText(ConvertUtil.returnFormattedDueDate(todo_dueYear, todo_dueMonth, todo_dueDate));
        mTodo_dueTime.setText(ConvertUtil.returnFormattedDueTime(todo_dueYear, todo_dueMonth, todo_dueDate, todo_dueHour, todo_dueMinute, todo_dueAMPM));

        if (todo_dueDateAvailable == 0) {
            mTodo_dueDateGroup.setVisibility(View.GONE);
        } else {
            mDueDateSwitch.setChecked(true);
        }

        if (todo_priority == 0) {
            mPriority_radioGroup.setVisibility(View.GONE);
        } else {
            mPrioritySwitch.setChecked(true);
            switch (todo_priority) {
                case 1:
                    mPriority_radio1.toggle();
                    break;
                case 2:
                    mPriority_radio2.toggle();
                    break;
                case 3:
                    mPriority_radio3.toggle();
                    break;
                default:
                    Log.i(LOGTAG, "Zero or out of range priority.");
                    break;
            }
        }

        // checks the length of the edit text field and moves cursor to end.
        int textLength = mTodo_description.getText().length();
        mTodo_description.setSelection(textLength, textLength);
        setupListViewListener();

    }

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(EditItemActivity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    private void setupListViewListener() {

        // Puts a listener on the scroll view to close keyboard if tapped on.
        mScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(mEditItemRootView);
                mEditItemRootView.requestFocus();
                return false;
            }
        });

        // Puts a listener on the scroll view to close keyboard if tapped on.
        mEditItemRootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(mEditItemRootView);
                mEditItemRootView.requestFocus();
                return false;
            }
        });


        // Listener for the due date switch.
        mDueDateSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDueDateSwitch.isChecked()) {
                    todo_dueDateAvailable = 1;
                    todo_dueYear = getCurrentYear();
                    todo_dueMonth = getCurrentMonth();
                    todo_dueDate = getCurrentDate();
                    todo_dueHour = 0;
                    todo_dueMinute = 0;
                    todo_dueAMPM = 0;

                    mTodo_dueDate.setText(ConvertUtil.returnFormattedDueDate(todo_dueYear, todo_dueMonth, todo_dueDate));
                    mTodo_dueTime.setText(ConvertUtil.returnFormattedDueTime(todo_dueYear, todo_dueMonth, todo_dueDate, todo_dueHour, todo_dueMinute, todo_dueAMPM));

                    mTodo_dueDateGroup.setVisibility(View.VISIBLE);
                } else {
                    todo_dueDateAvailable = 0;
                    mTodo_dueDateGroup.setVisibility(View.GONE);
                }
            }
        });

        mEditDueDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOGTAG, "Edit Date button clicked");
                showDatePickerDialog();
            }
        });

        mEditDueTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOGTAG, "Edit Time button clicked");
                showTimePickerDialog();
            }
        });

        // Listener for the priority level switch.
        mPrioritySwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPrioritySwitch.isChecked()) {
                    todo_priority = 1;
                    mPriority_radio1.toggle();
                    mPriority_radioGroup.setVisibility(View.VISIBLE);
                } else {
                    mPriority_radioGroup.setVisibility(View.GONE);
                    todo_priority = 0;
                    mPriority_radio1.toggle();
                }
            }
        });


        mPriority_radio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todo_priority = 1;
            }
        });

        mPriority_radio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todo_priority = 2;
            }
        });

        mPriority_radio3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todo_priority = 3;
            }
        });

    }


    private int getCurrentYear() {
        Calendar currentDate = Calendar.getInstance();
        return currentDate.get(Calendar.YEAR);
    }

    private int getCurrentMonth() {
        Calendar currentDate = Calendar.getInstance();
        return currentDate.get(Calendar.MONTH);
    }

    private int getCurrentDate() {
        Calendar currentDate = Calendar.getInstance();
        return currentDate.get(Calendar.DATE);
    }


    private void showDatePickerDialog() {
        // Calls the stock Android date picker dialog fragment.
        // The date picker is seeded with some initial values.

        Log.i(LOGTAG, "Show Date Picker Dialog method called");

        Bundle args = new Bundle();
        args.putInt(DUEYEAR, todo_dueYear);
        args.putInt(DUEMONTH, todo_dueMonth);
        args.putInt(DUEDATE, todo_dueDate);

        DatePickerFragment dialog = new DatePickerFragment();
        dialog.setArguments(args);

        dialog.show(getFragmentManager(), "MyDialog");
    }

    private void showTimePickerDialog() {
        // Calls the stock Android time picker dialog fragment.
        // The time picker is seeded with some initial values.

        Log.i(LOGTAG, "Show Date Picker Dialog method called");

        Bundle args = new Bundle();
        args.putInt(DUEHOUR, todo_dueHour);
        args.putInt(DUEMINUTE, todo_dueMinute);
        args.putInt(DUEAMPM, todo_dueAMPM);

        TimePickerFragment dialog = new TimePickerFragment();
        dialog.setArguments(args);

        dialog.show(getFragmentManager(), "MyDialog");
    }

    public void onSubmit() {
        EditText etTodoTitle = (EditText) findViewById(R.id.etTodoDescription);

        // Prepare data intent
        Intent data = new Intent();

        // Pass relevant data back as a result
        Bundle extras = new Bundle();

        extras.putString(DESCRIPTION, etTodoTitle.getText().toString());
        extras.putInt(PRIORITY, todo_priority);
        extras.putInt(HASDUEDATE, todo_dueDateAvailable);
        extras.putInt(DUEYEAR, todo_dueYear);
        extras.putInt(DUEMONTH, todo_dueMonth);
        extras.putInt(DUEDATE, todo_dueDate);

        extras.putInt(DUEHOUR, todo_dueHour);
        extras.putInt(DUEMINUTE, todo_dueMinute);
        extras.putInt(DUEAMPM, todo_dueAMPM);

        data.putExtras(extras);

        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }

    // Function is called by Date Picker dialog fragment when finished
    public void onEnterDatePicked(int year, int month, int date) {
        Log.i(LOGTAG, "Date picked: " + String.valueOf(year) + "/" + String.valueOf(month) + "/" + String.valueOf(date));

        todo_dueYear = year;
        todo_dueMonth = month;
        todo_dueDate = date;
        mTodo_dueDate.setText(ConvertUtil.returnFormattedDueDate(todo_dueYear, todo_dueMonth, todo_dueDate));

    }

    // Function is called by Time Picker dialog fragment when finished
    public void onEnterTimePicked(int hourOfDay, int minute) {
        Log.i(LOGTAG, "Time picked: " + String.valueOf(hourOfDay) + ":" + String.valueOf(minute));

        if (hourOfDay > 12) {
            todo_dueAMPM = 1;
        } else {
            todo_dueAMPM = 0;
        }

        if (hourOfDay > 12) {
            todo_dueHour = hourOfDay - 12;
        } else {
            todo_dueHour = hourOfDay;
        }

        todo_dueMinute = minute;

        mTodo_dueTime.setText(ConvertUtil.returnFormattedDueTime(todo_dueYear, todo_dueMonth, todo_dueDate, todo_dueHour, todo_dueMinute, todo_dueAMPM));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_SaveEdits) {
            onSubmit();
            Log.i(LOGTAG, "Save Edits Selected");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}
