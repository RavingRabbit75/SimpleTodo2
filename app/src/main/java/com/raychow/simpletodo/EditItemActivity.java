package com.raychow.simpletodo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class EditItemActivity extends ActionBarActivity {

    EditText mTodoTitle;
    Button mSaveButton;

    //Logcat Tags
    private final static String EDIT_ITEM_ACTIVITY = "Edit Item Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        mTodoTitle = (EditText) findViewById(R.id.editText);
        mSaveButton = (Button) findViewById(R.id.saveButton);
        String todoTitle = getIntent().getStringExtra("todoItem");

        mTodoTitle.setText(todoTitle);
        // checks the length of the edit text field and moves cursor to end.
        int textLength = mTodoTitle.getText().length();
        mTodoTitle.setSelection(textLength, textLength);
        setupListViewListener();
    }


    private void setupListViewListener() {

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit(v);
            }
        });

    }

    public void onSubmit(View v) {
        EditText etTodoTitle = (EditText) findViewById(R.id.editText);
        // Prepare data intent
        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra("todoTitle", etTodoTitle.getText().toString());
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}