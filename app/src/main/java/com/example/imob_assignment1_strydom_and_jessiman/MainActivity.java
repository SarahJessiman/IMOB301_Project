package com.example.imob_assignment1_strydom_and_jessiman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imob_assignment1_strydom_and_jessiman.placeholder.PlaceholderContent;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    int StudID;
    String Username;
    String Password;
    String groupChoice;

    private DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        final EditText username = (EditText) findViewById(R.id.txtUsername);
        final EditText password = (EditText) findViewById(R.id.txtPassword);

        final Spinner group = (Spinner) findViewById(R.id.txtGroup);

        group.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 1){
                    groupChoice = "Admin";
                } else if (i == 2) {
                    groupChoice = "Instructor";
                }
                else if (i == 3){
                    groupChoice = "Student";
                }

            }

                                            @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Button login = (Button) findViewById(R.id.loginBtn);
        login.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {
                String  Username =  username.getText().toString();
                String Password = password.getText().toString();

//                 groupChoice = group.getSelectedItem().toString();

                if(groupChoice == "Admin")
                {
                    Boolean userPassCheckResult = myDb.checkAdminUsernamePassword(Username, Password);
                    if (userPassCheckResult)
                    {
                        Toast.makeText(MainActivity.this, "! Admin logged in!", Toast.LENGTH_LONG).show();

                        startActivity(new Intent(MainActivity.this, ItemDetailHostActivity.class));

                        username.setText("");
                        password.setText("");

                    }

                    else
                    {
                        Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }

                } else if (groupChoice == "Instructor") {



                    Boolean userPassCheckResult = myDb.checkInstructorUsernamePassword(Username, Password);
                    if (userPassCheckResult)
                    {
                        Toast.makeText(MainActivity.this, "! Instructor logged in !", Toast.LENGTH_LONG).show();

                        startActivity(new Intent(MainActivity.this, Instructor.class));
                        username.setText("");
                        password.setText("");
                    }

                    else
                    {
                        Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }


                }
                else if (groupChoice == "Student"){

                    Boolean userPassCheckResult = myDb.checkStudentUsernamePassword(Username, Password);
                    if (userPassCheckResult)
                    {
                        Toast.makeText(MainActivity.this, "! Student logged in !", Toast.LENGTH_LONG).show();

                        startActivity(new Intent(MainActivity.this, Student.class));
                        username.setText("");
                        password.setText("");
                    }

                    else
                    {
                        Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }

                }



//                Username = username.getText().toString();
//                Password = password.getText().toString();
//                StudID = Integer.parseInt(studID.getText().toString());
//
//                groupChoice = group.getSelectedItem().toString();
//                output.setText("Student ID: " +StudID + ", Username: " +Username +", Password: " +Password + ", Account: " +groupChoice);
//
            }


        });


//        group.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if(i == 1)
//                {
//
//                    String  Username =  username.getText().toString();
//                    String Password = password.getText().toString();
//
//                    Boolean userPassCheckResult = myDb.checkAdminUsernamePassword(Username, Password);
//                    if (userPassCheckResult)
//                    {
//                       Toast.makeText(MainActivity.this, "! Admin logged in!", Toast.LENGTH_LONG).show();
//
//                        startActivity(new Intent(MainActivity.this, ItemDetailHostActivity.class));
//
//                    }
//
//                    else
//                    {
//                        Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
//                    }
//
//                } else if (i == 2) {
//
//
//
//                    Boolean userPassCheckResult = myDb.checkIntstructorUsernamePassword(Username, Password);
//                    if (userPassCheckResult)
//                    {
//                        Toast.makeText(MainActivity.this, "! Instructor logged in !", Toast.LENGTH_LONG).show();
//
//                        startActivity(new Intent(MainActivity.this, Instructor.class));
//
//                    }
//
//                    else
//                    {
//                        Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
//                    }
//
//
//                }
//                else if (i == 3){
//
//                    Boolean userPassCheckResult = myDb.checkStudentUsernamePassword(Username, Password);
//                    if (userPassCheckResult)
//                    {
//                        Toast.makeText(MainActivity.this, "! Student logged in !", Toast.LENGTH_LONG).show();
//
//                        startActivity(new Intent(MainActivity.this, Student.class));
//
//                    }
//
//                    else
//                    {
//                        Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
           }

//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

    }

//}

