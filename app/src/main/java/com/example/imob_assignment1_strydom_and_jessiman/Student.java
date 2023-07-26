package com.example.imob_assignment1_strydom_and_jessiman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.content.Context;

import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.CheckBox;
import android.widget.SimpleAdapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class Student extends AppCompatActivity {
    private DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);


        myDb = new DatabaseHelper(this);

        ArrayList<HashMap<String, String>> taskList = myDb.GetTasks();
        ListView lv = (ListView) findViewById(R.id.task_list);
        ListAdapter adapter = new SimpleAdapter(Student.this, taskList, R.layout.list_row, new String[]{"TaskName", "TaskDueDate", "TaskModule"}, new int[]{R.id.TaskName, R.id.TaskDueDate, R.id.TaskModule});
        lv.setAdapter(new CheckboxAdapter(Student.this, taskList));



    }

    public class CheckboxAdapter extends BaseAdapter {
        Context context;

        List<HashMap<String, String>> _items;

        public CheckboxAdapter(Context context, List<HashMap<String, String>> items) {
            this.context = context;
            _items = items;

        }

        @Override
        public int getCount() {
            return _items.toArray().length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.list_row, parent, false);
            }

            TextView taskName = convertView.findViewById(R.id.TaskName);
            taskName.setText(_items.get(position).get("TaskName").toString());


            TextView taskModule = convertView.findViewById(R.id.TaskModule);
            taskModule.setText(_items.get(position).get("TaskModule").toString());

            TextView taskDueDate = convertView.findViewById(R.id.TaskDueDate);
            taskDueDate.setText(_items.get(position).get("TaskDueDate").toString());


            CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkComplete);

            boolean val = false;

            if (_items.get(position).get("Completed").toString().equals("1")) {
                val = true;
            }

            cb.setChecked(val);

            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    // should be id since it's more unique
                    boolean res = myDb.updateCompletion(taskName.getText().toString(), b);

                    if (res) {
                        Toast.makeText(
                            context, "Task Completion Status Changed!", Toast.LENGTH_SHORT
                        ).show();
                    } else {
                        Toast.makeText(
                            context, "Unknown Error Occurred!", Toast.LENGTH_SHORT
                        ).show();
                    }
                }
            });

            return convertView;
        }
   }
}