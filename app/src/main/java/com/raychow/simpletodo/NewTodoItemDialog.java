package com.raychow.simpletodo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Dialog;
import android.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;


/**
 * Created by raymondchow on 7/29/15.
 */
public class NewTodoItemDialog extends DialogFragment {


    private static final String LOGTAG = "New Todo Item Dialog";

    private EditText mEditText;


    public NewTodoItemDialog() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState) {
        Log.i(LOGTAG, "onCreateDialog method called");

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialoglayout = inflater.inflate(R.layout.fragment_new_todo, null);
        mEditText = (EditText) dialoglayout.findViewById(R.id.etNewTodoItem);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("New Todo Item")
                .setView(dialoglayout)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (mEditText.getText().toString().length() == 0) {
                    Log.i(LOGTAG, "No text in field.");
                    getDialog().dismiss();
                } else {
                    Log.i(LOGTAG, "Something was in the field.");
                    MainActivity callingActivity = (MainActivity) getActivity();
                    callingActivity.onSaveNewTodoItem(mEditText.getText().toString());
                }
            }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getDialog().dismiss();
                    }
                });


        Dialog d = builder.create();
        d.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return d;

    }

}
