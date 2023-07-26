package com.example.imob_assignment1_strydom_and_jessiman;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;


// Creating All table
public class DatabaseHelper extends SQLiteOpenHelper{


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UnivercitySystem";

    //Admin Table
    private static final String TABLE_ADMIN = "AdminTable";
    private static final String KEY_ID = "AdminID";
    private static final String KEY_Username = "Username";
    private static final String KEY_Password = "Password";


    //Student Table
    private static final String TABLE_STUDENT = "StudentTable";
    private static final String KEY_StudID = "StudentID";
    private static final String KEY_StudName = "Name";
    private static final String KEY_StudSurname = "Surname";
    private static final String KEY_DOB = "DOB";
    private static final String KEY_StudentPassword = "StudentPassword";
    private static final String KEY_StudentUsername = "StudentUsername";

    //Module Table
    private static final String TABLE_MODULE = "ModuleTable";
    private static final String KEY_ModID = "ModID";
    private static final String KEY_ModName = "ModName";
    private static final String KEY_ModDuration = "ModDuration";


    //Instructor Table

    private static final String TABLE_INSTRUCTOR = "InstructorTable";
    private static final String KEY_InstructorID = "InstructorID";
    private static final String KEY_InstructorName = "InstructorName";
    private static final String KEY_InstructorSurname = "InstructorSurname";
    private static final String KEY_InstructorPassword = "InstructorPassword";
    private static final String KEY_InstructorUsername = "InstructorUsername";

    //Task Table

    private static final String TABLE_TASK = "TaskTable";
    public static final String KEY_TaskID = "TaskID";
    public static final String KEY_TaskName = "TaskName";
    private static final String KEY_TaskDueDate = "TaskDueDate";
    private static final String KEY_TaskModule = "TaskModule";
    public static final String KEY_Completed = "Completed";


    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
      // Create Admin table
        db.execSQL("create table " + TABLE_ADMIN +
                "(AdminID INTEGER PRIMARY KEY AUTOINCREMENT, Username TEXT, Password TEXT, AdminUsername TEXT)");
        //Hard coding 1 admins
        db.execSQL("INSERT INTO " + TABLE_ADMIN + "(Username, Password) VALUES('Hollan', '@dm!n')");


        // Create Student table
        db.execSQL("create table " + TABLE_STUDENT +
                "(StudentID INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Surname TEXT, DOB DATE, StudentPassword TEXT, StudentUsername TEXT)");

        // Create Module table
        db.execSQL("create table " + TABLE_MODULE +
                "(ModID INTEGER PRIMARY KEY AUTOINCREMENT, ModName TEXT, ModDuration INTEGER)");

        // Create Instructor table
        db.execSQL("create table " + TABLE_INSTRUCTOR +
                "(InstructorID INTEGER PRIMARY KEY AUTOINCREMENT, InstructorName TEXT, InstructorSurname TEXT, InstructorPassword TEXT, InstructorUsername)");

        // Create Task table
        db.execSQL("create table " + TABLE_TASK +
                "(TaskID INTEGER PRIMARY KEY AUTOINCREMENT, TaskName TEXT, TaskDueDate DATE, TaskModule TEXT, Completed BOOLEAN )");

    }

    //Dropping tables if exists
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MODULE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INSTRUCTOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        onCreate(db);
    }


    //Student
    public boolean insertStudentData(String name, String surname, String dob, String StudentPassword, String StudentUsername) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_StudName, name);
        contentValues.put(KEY_StudSurname, surname);
        contentValues.put(KEY_DOB, dob);
        contentValues.put(KEY_StudentPassword, StudentPassword);
        contentValues.put(KEY_StudentUsername, StudentUsername);

        long result = db.insert(TABLE_STUDENT, null, contentValues);
        return result != -1;
    }
    //Module
    public boolean insertModuleData(String name, int duration) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ModName, name);
        contentValues.put(KEY_ModDuration, duration);

        long result = db.insert(TABLE_MODULE, null, contentValues);
        return result != -1;
    }
    //Instructor
    public boolean insertInstructorData(String name, String surname, String InstructorPassword, String InstructorUsername) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_InstructorName, name);
        contentValues.put(KEY_InstructorSurname, surname);
        contentValues.put(KEY_InstructorPassword, InstructorPassword);
        contentValues.put(KEY_InstructorUsername, InstructorUsername);

        long result = db.insert(TABLE_INSTRUCTOR, null, contentValues);
        return result != -1;
    }

    //Task
    public boolean insertTaskData(String taskName, String taskDueDate, String taskModule, Boolean Completed) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TaskName, taskName);
        contentValues.put(KEY_TaskDueDate, taskDueDate);
        contentValues.put(KEY_TaskModule, taskModule);
        contentValues.put(KEY_Completed, Completed);

        long result = db.insert(TABLE_TASK, null, contentValues);
        return result != -1;
    }

    @SuppressLint("Range")
    public ArrayList<HashMap<String, String>> GetTasks(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> taskList = new ArrayList<>();
        String query = "SELECT * FROM "+ TABLE_TASK;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("TaskId",cursor.getString(cursor.getColumnIndex(KEY_TaskID)));
            user.put("TaskName",cursor.getString(cursor.getColumnIndex(KEY_TaskName)));
            user.put("TaskDueDate",cursor.getString(cursor.getColumnIndex(KEY_TaskDueDate)));
            user.put("TaskModule",cursor.getString(cursor.getColumnIndex(KEY_TaskModule)));
            user.put("Completed",cursor.getString(cursor.getColumnIndex(KEY_Completed)));
            taskList.add(user);
        }
        return  taskList;
    }

    //Check Admin Exists
    public Boolean checkAdminUsernamePassword (String Username, String Password)  // check the username and password
    {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from AdminTable where Username = ? and Password = ?", new String[] {Username, Password});

        if (cursor.getCount() > 0)  // if the data exists then return true
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    //Check Student Exists
    public Boolean checkStudentUsernamePassword (String Username, String Password)  // check the username and password
    {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from StudentTable where StudentUsername = ? and StudentPassword = ?", new String[] {Username, Password});

        if (cursor.getCount() > 0)  // if the data exists then return true
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    //Check Instructor Exists
    public Boolean checkInstructorUsernamePassword (String Username, String Password)  // check the username and password
    {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from InstructorTable where InstructorUsername = ? and InstructorPassword = ?", new String[] {Username, Password});

        if (cursor.getCount() > 0)  // if the data exists then return true
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean updateCompletion(String TaskName, boolean newVal) {
        SQLiteDatabase db = this.getWritableDatabase();

        String val = "0";
        if (newVal) {
            val = "1";
        }

        try {
            db.execSQL("UPDATE TaskTable SET Completed = " + val + " WHERE TaskName = '" + TaskName + "'");
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}






