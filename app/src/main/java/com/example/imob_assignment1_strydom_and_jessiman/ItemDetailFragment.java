package com.example.imob_assignment1_strydom_and_jessiman;

import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.view.DragEvent;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.example.imob_assignment1_strydom_and_jessiman.placeholder.PlaceholderContent;
import com.example.imob_assignment1_strydom_and_jessiman.databinding.FragmentItemDetailBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListFragment}
 * in two-pane mode (on larger screen devices) or self-contained
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The placeholder content this fragment is presenting.
     */
    private PlaceholderContent.PlaceholderItem mItem;
    private CollapsingToolbarLayout mToolbarLayout;
    private TextView mTextView;
    private DatabaseHelper myDb;
    final Calendar myCalendar = Calendar.getInstance();


    private final View.OnDragListener dragListener = (v, event) -> {
        if (event.getAction() == DragEvent.ACTION_DROP) {
            ClipData.Item clipDataItem = event.getClipData().getItemAt(0);
            mItem = PlaceholderContent.ITEM_MAP.get(clipDataItem.getText().toString());
            updateContent();
        }
        return true;
    };
    private FragmentItemDetailBinding binding;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myDb = new DatabaseHelper(getContext());

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the placeholder content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = PlaceholderContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentItemDetailBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        // Show the placeholder content as text in a TextView & in the toolbar if available.
        updateContent();
        rootView.setOnDragListener(dragListener);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void updateContent() {
        Context context = getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (mItem != null) {

            int screen = context.getResources().getIdentifier(
                    mItem.screenFile, "layout", context.getPackageName()
            );

            View child = inflater.inflate(screen, null);

            binding.baseContainer.addView(child);

            switch(mItem.screenName) {

                case "Create Student Records":
                    createStudentScreen();

                case "Create Module Records":
                    createModuleScreen();

                case "Create Instructor Records":
                    createInstructorScreen();

                default:
            }
        }
    }

    //createStudentScreen
    void createStudentScreen() {
        View myView = binding.baseContainer;

        if (myView != null) {
            //EditText studId = myView.findViewById(R.id.studId);
            EditText studName = myView.findViewById(R.id.studName);
            EditText studSurname = myView.findViewById(R.id.studSurname);
            EditText studDob = myView.findViewById(R.id.studDob);

            DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int day) {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH,month);
                    myCalendar.set(Calendar.DAY_OF_MONTH,day);

                    String myFormat="MM/dd/yy";
                    SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
                    studDob.setText(dateFormat.format(myCalendar.getTime()));
                }
            };

            studDob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new DatePickerDialog(
                            getContext(), date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)
                    ).show();
                }
            });



            Button btn = myView.findViewById(R.id.createStudBtn);

            if (btn != null) {
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name = studName.getText().toString();
                        String surname = studSurname.getText().toString();
                        String dob = studDob.getText().toString();
                        String StudentPassword = name.toLowerCase() + surname.toLowerCase();
                        String StudentUsername = name.toLowerCase() + "_" + surname.toLowerCase();

                        boolean isStudentInserted = myDb.insertStudentData(name, surname, dob, StudentPassword, StudentUsername);
                        if(isStudentInserted){
                            Toast.makeText(getContext(), "Student added", Toast.LENGTH_LONG).show();
                            studName.setText("");
                            studSurname.setText("");
                            studDob.setText("");
                        }
                        else{
                            Toast.makeText(getContext(), "Student not added", Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }


        }
    }

    //createModuleScreen
    void  createModuleScreen() {
        View myView = binding.baseContainer;

        if (myView != null) {
//            EditText modId = myView.findViewById(R.id.modId);
            EditText modName = myView.findViewById(R.id.modName);
            EditText modDuration = myView.findViewById(R.id.modDuration);

//             Spinner status = myView.findViewById(R.id.statusSpinner);

            Button btn = myView.findViewById(R.id.createModBtn);

            if (btn != null) {
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String modname = modName.getText().toString();
                        int duration = Integer.parseInt(modDuration.getText().toString());


                        boolean isModuleInserted = myDb.insertModuleData(modname, duration);
                        if(isModuleInserted){
                            Toast.makeText(getContext(), "Module added", Toast.LENGTH_LONG).show();
                            modName.setText("");
                            modDuration.setText("");

                        }
                        else{
                            Toast.makeText(getContext(), "Module not added", Toast.LENGTH_LONG).show();
                        }
//
                    }
                });
            }


        }
    }

    void createInstructorScreen() {

        View myView = binding.baseContainer;

        if (myView != null) {
//            EditText instId = myView.findViewById(R.id.instId);
            EditText instName = myView.findViewById(R.id.instName);
            EditText instSurname = myView.findViewById(R.id.instSurname);


            Button btn = myView.findViewById(R.id.createInstBtn);

            if (btn != null) {
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String name = instName.getText().toString();
                        String surname = instSurname.getText().toString();
                        String InstructorPassword = name.toLowerCase() + surname.toLowerCase();
                        String InstructorUsername = name.toLowerCase() + "_" + surname.toLowerCase();

                        boolean isInstructorInserted = myDb.insertInstructorData(name, surname, InstructorPassword, InstructorUsername);
                        if(isInstructorInserted){
                            Toast.makeText(getContext(), "Instructor added", Toast.LENGTH_LONG).show();
                            instName.setText("");
                            instSurname.setText("");

                        }
                        else{
                            Toast.makeText(getContext(), "Instructor not added", Toast.LENGTH_LONG).show();
                        }

                    }


                });
            }


        }
    }

}