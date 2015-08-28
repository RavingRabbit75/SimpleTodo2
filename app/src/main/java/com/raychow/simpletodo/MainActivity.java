package com.raychow.simpletodo;
import com.raychow.simpletodo.db.TodoDataSource;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    // Logcat tags
    private static final String LOGTAG = "Main Activity";

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

    private static final int REQUEST_CODE = 1;

    // non-view variables
    public static List<TodoItem> listOfTodoItemsObjects = new ArrayList<>(); // Array list of todoItem objects
    public static List<Integer> filteredItems = new ArrayList<>(); // Temporary container for holding which objects match the current filter
    public static List<TodoItem> displayListOfTodoObjects = new ArrayList<>(); // The final list of todoItem objects to be actually displayed
    public static int currentDisplayFilter; // determines what todoItem items are displayed: 0 = all, 1 = uncompleted, 2 = completed
    private static long currentTodoItemSelected; // todoItem selected to be edited in details view
    private static TodoDataSource dataSource;

    // view related variables
    public static ListView listOfTodoItems_view;
    public static ListTodoAdapter listOfTodoItemsAdaptor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataSource = new TodoDataSource(this);
        dataSource.open();

        listOfTodoItemsObjects = dataSource.findAll();

        currentDisplayFilter = 0;
        setFilterForList(currentDisplayFilter);

        listOfTodoItems_view = (ListView) findViewById(R.id.listOfTodoItems_view);
        listOfTodoItems_view.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listOfTodoItemsAdaptor = new ListTodoAdapter(this, displayListOfTodoObjects);
        listOfTodoItems_view.setAdapter(listOfTodoItemsAdaptor);

        setupListViewListener();

    }


    private void setupListViewListener() {

        listOfTodoItems_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                TodoItem todoObject = (TodoItem) listOfTodoItems_view.getItemAtPosition(position);

                Log.i(LOGTAG, "Description in that position: " + todoObject.getTodoDescription());
                Log.i(LOGTAG, "Item ID: " + todoObject.getId());

                currentTodoItemSelected = todoObject.getId();
                Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
                Bundle extras = new Bundle();
                extras.putString(DESCRIPTION, todoObject.getTodoDescription());
                extras.putInt(PRIORITY, todoObject.getTodoPriority());
                extras.putInt(HASDUEDATE, todoObject.getDueDateAvailability());
                extras.putInt(DUEMONTH, todoObject.getTodoDueMonth());
                extras.putInt(DUEDATE, todoObject.getTodoDueDate());
                extras.putInt(DUEYEAR, todoObject.getTodoDueYear());
                extras.putInt(DUEHOUR, todoObject.getTodoDueHour());
                extras.putInt(DUEMINUTE, todoObject.getTodoDueMinute());
                extras.putInt(DUEAMPM, todoObject.getTodoDueAMPM());

                intent.putExtras(extras);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });


        listOfTodoItems_view.setOnItemLongClickListener(
            new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    deleteTodoItem(position);
                    return true;
                }
            }
        );
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(LOGTAG, "onActivityResult called");

        int position = -1;

        if (resultCode == ActionBarActivity.RESULT_OK && requestCode == REQUEST_CODE) {
            Bundle extras = data.getExtras();

            String description = extras.getString(EditItemActivity.DESCRIPTION);
            int priority = extras.getInt(EditItemActivity.PRIORITY);
            int hasDueDate = extras.getInt(EditItemActivity.HASDUEDATE);
            int year = extras.getInt(EditItemActivity.DUEYEAR);
            int month = extras.getInt(EditItemActivity.DUEMONTH);
            int date = extras.getInt(EditItemActivity.DUEDATE);
            int hour = extras.getInt(EditItemActivity.DUEHOUR);
            int minute = extras.getInt(EditItemActivity.DUEMINUTE);
            int ampm = extras.getInt(EditItemActivity.DUEAMPM);


            for (int i = 0; i < listOfTodoItemsObjects.size(); i++) {
                if (listOfTodoItemsObjects.get(i).getId() == currentTodoItemSelected) {
                    position = i;
                } else {
                    Log.i(LOGTAG, "No matching ID from returning Todo Edit Activity");
                }
            }

            listOfTodoItemsObjects.get(position).setTodoDescription(description);
            listOfTodoItemsObjects.get(position).setTodoPriority(priority);
            listOfTodoItemsObjects.get(position).setDueDateAvailability(hasDueDate);
            listOfTodoItemsObjects.get(position).setTodoDueDate(month, date, year);
            listOfTodoItemsObjects.get(position).setTodoDueTime(hour, minute, ampm);

            setFilterForList(currentDisplayFilter);
            listOfTodoItemsAdaptor.notifyDataSetChanged();

        } else {

        }

        dataSource.open();
        dataSource.updateTodoItem(listOfTodoItemsObjects.get(position));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_newTodoItem) {
            onNewTodoItem();
            Log.i(LOGTAG, "New Todo Item Selected");
            return true;
        } else if (id == R.id.action_ShowUnCompletedItems) {
            currentDisplayFilter = 1;
            setFilterForList(currentDisplayFilter);
            listOfTodoItemsAdaptor.notifyDataSetChanged();
            Log.i(LOGTAG, "Show Uncompleted Selected");
            return true;
        } else if (id == R.id.action_ShowCompletedItems){
            currentDisplayFilter = 2;
            setFilterForList(currentDisplayFilter);
            listOfTodoItemsAdaptor.notifyDataSetChanged();
            Log.i(LOGTAG, "Show Completed Selected");
            return true;
        } else if (id == R.id.action_ShowAllItems) {
            currentDisplayFilter = 0;
            setFilterForList(currentDisplayFilter);
            listOfTodoItemsAdaptor.notifyDataSetChanged();
            Log.i(LOGTAG, "Show All Selected");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setFilterForList(int option) {
        filteredItems.clear();
        displayListOfTodoObjects.clear();

        if (option == 0) {
            for (int i = 0; i < listOfTodoItemsObjects.size(); i++) {
                filteredItems.add(i);
            }
        } else if (option == 1) {
            for (int i = 0; i < listOfTodoItemsObjects.size(); i++) {
                if(listOfTodoItemsObjects.get(i).getTodoCompletedStatus() == 0) {
                    filteredItems.add(i);
                }
            }
        } else if (option == 2) {
            for (int i = 0; i < listOfTodoItemsObjects.size(); i++) {
                if(listOfTodoItemsObjects.get(i).getTodoCompletedStatus() == 1) {
                    filteredItems.add(i);
                }
            }
        }

        for (int i = 0; i < filteredItems.size(); i++) {
            displayListOfTodoObjects.add(listOfTodoItemsObjects.get(filteredItems.get(i)));
        }
        Log.i(LOGTAG, "Filtered items: " + filteredItems.toString());


    }


    public static void checkBoxTurnedOn(int completionStatus, int position) {

        TodoItem todoObject = (TodoItem) listOfTodoItems_view.getItemAtPosition(position);
        int position2 = 0;

        for (int i = 0; i < listOfTodoItemsObjects.size(); i++) {
            if (listOfTodoItemsObjects.get(i).getId() == todoObject.getId()) {
                position2 = i;
            } else {
                Log.i(LOGTAG, "No matching ID from returning Todo Edit Activity");
            }
        }

        listOfTodoItemsObjects.get(position2).setTodoCompletedStatus(completionStatus);

        dataSource.open();
        dataSource.updateTodoItem(listOfTodoItemsObjects.get(position));
    }


    public void onNewTodoItem() {
        showAlertDialog();
    }


    public void deleteTodoItem(int position) {

        TodoItem todoObject = (TodoItem) listOfTodoItems_view.getItemAtPosition(position);
        int position2 = 0;

        for (int i = 0; i < listOfTodoItemsObjects.size(); i++) {
            if (listOfTodoItemsObjects.get(i).getId() == todoObject.getId()) {
                position2 = i;
            } else {
                Log.i(LOGTAG, "No matching ID from returning Todo Edit Activity");
            }
        }

        TodoItem todoItem = listOfTodoItemsObjects.get(position2);
        dataSource.deleteTodoItem(todoItem);
        listOfTodoItemsObjects.remove(position2);
        setFilterForList(currentDisplayFilter);
        listOfTodoItemsAdaptor.notifyDataSetChanged();
    }


    @Override
    protected void onResume() {
        super.onResume();
        dataSource.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dataSource.close();
    }



    private void showAlertDialog() {
        Log.i(LOGTAG, "Show Alert Dialog method called");
        NewTodoItemDialog dialog = new NewTodoItemDialog();

        dialog.show(getFragmentManager(), "MyDialog");

    }

    public void onSaveNewTodoItem(String newTodoDescription) {
        TodoItem tempItem = new TodoItem();
        tempItem.setTodoDescription(newTodoDescription);
        tempItem.setTodoCompletedStatus(0);
        tempItem.setTodoPriority(0);

        listOfTodoItemsObjects.add(tempItem);

        setFilterForList(currentDisplayFilter);
        listOfTodoItemsAdaptor.notifyDataSetChanged();

        dataSource.open();
        dataSource.createNewTodoItem(listOfTodoItemsObjects.get(listOfTodoItemsObjects.size() - 1));

    }


}