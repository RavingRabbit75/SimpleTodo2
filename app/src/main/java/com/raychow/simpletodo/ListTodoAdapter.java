package com.raychow.simpletodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Raymond Chow on 6/20/15.
 */
public class ListTodoAdapter extends BaseAdapter {

    // Logcat tags
    private static final String LOGTAG = "List Todo Adapter";

    Context context;
    List<TodoItem> listTodoItems;
    LayoutInflater inflater;

    public ListTodoAdapter(MainActivity mainActivity, List<TodoItem> listNotes) {
        this.listTodoItems = listNotes;
        this.inflater = LayoutInflater.from(mainActivity);
        this.context = mainActivity;
    }


    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;


        if (convertView == null) {

            holder = new ViewHolder();
            convertView = this.inflater.inflate(R.layout.todo_list_item_layout, parent, false);

            holder.txtDescription = (TextView) convertView.findViewById(R.id.todoDescription);
            holder.txtDueDate = (TextView) convertView.findViewById(R.id.todoDueDate);
            holder.txtPriority = (TextView) convertView.findViewById(R.id.priorityText);
            holder.cbCompletionStatus = (CheckBox) convertView.findViewById(R.id.completionStatusCheckBox);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        TodoItem todoItem = listTodoItems.get(position);
        holder.txtDescription.setText(todoItem.getTodoDescription());

        if (todoItem.getTodoPriority() == 0) {
            // turn off priority view
            View b = convertView.findViewById(R.id.priorityText);
            b.setVisibility(View.GONE);
        } else {
            holder.txtPriority.setText(todoItem.getPriorityAsString() + " Priority");
            View b = convertView.findViewById(R.id.priorityText);
            b.setVisibility(View.VISIBLE);
        }

        if (todoItem.getDueDateAvailability() == 0) {
            // turn off priority view
            View b = convertView.findViewById(R.id.todoDueDate);
            b.setVisibility(View.GONE);
        } else {
            if (todoItem.getTodoDueHour()==0 && todoItem.getTodoDueMinute()==0 && todoItem.getTodoDueAMPM()==0) {
                holder.txtDueDate.setText("Due: " + todoItem.getTodoDueDateString());
            } else {
                holder.txtDueDate.setText("Due: " + todoItem.getTodoDueDateString() + ", " + todoItem.getTodoDueTimeString());
            }
            View b = convertView.findViewById(R.id.todoDueDate);
            b.setVisibility(View.VISIBLE);
        }

        holder.cbCompletionStatus.setTag(position);
        if (todoItem.getTodoCompletedStatus() == 0) {
            holder.cbCompletionStatus.setChecked(false);
        } else if (todoItem.getTodoCompletedStatus() == 1) {
            holder.cbCompletionStatus.setChecked(true);
        }


        holder.cbCompletionStatus.setOnClickListener(new View.OnClickListener() {
            // Listener for individual checkbox
            @Override
            public void onClick(View v) {
                int position = (Integer)v.getTag();
                if(holder.cbCompletionStatus.isChecked()) {
                    MainActivity.checkBoxTurnedOn(1, position);
                } else {
                    MainActivity.checkBoxTurnedOn(0, position);
                };

            }
        });

        return convertView;
    }


    private class ViewHolder {
        TextView txtDescription;
        TextView txtDueDate;
        TextView txtPriority;
        CheckBox cbCompletionStatus;
    }

    public long getItemId(int position) {
        return listTodoItems.get(position).getDrawableId();
    }

    public TodoItem getItem(int position) {
        return listTodoItems.get(position);
    }

    public int getCount() {
        return listTodoItems.size();
    }


}
