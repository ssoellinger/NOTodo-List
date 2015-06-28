package projekt.htlgrieskirchen.at.notodoslist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Simon on 21.06.2015.
 */
public class New_Todo extends Activity {
    EditText e1;
    EditText e2;
    Spinner spinner;
    DatePicker dp;
    Button datum;
    Button zeit;
   SimpleDateFormat simpleDateFormat;
    private Uri todoUri;
    public static final String TAG = MainActivity.TAG;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_todo);

        addTodo();
        Bundle extras = getIntent().getExtras();

        // check from the saved Instance
        todoUri = (savedInstanceState == null) ? null : (Uri) savedInstanceState
                .getParcelable(TodoContentProvider.CONTENT_ITEM_TYPE);

        // Or passed from the other activity
        if (extras != null) {
            todoUri = extras
                    .getParcelable(TodoContentProvider.CONTENT_ITEM_TYPE);

            fillData(todoUri);
        }
    }
    private void addTodo() {


                e1 = (EditText) findViewById(R.id.dialog_title);
                e2 = (EditText) findViewById(R.id.dialog_description);
                spinner = (Spinner) findViewById(R.id.spinner2);
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.priority_array,
                        android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
                spinner.setAdapter(adapter);
                 zeit = (Button) findViewById(R.id.zeit);
        datum = (Button) findViewById(R.id.datum);

        simpleDateFormat=new SimpleDateFormat("dd.MM.yyyy HH:mm");



            }
    private void fillData(Uri uri) {
        String[] projection = TodosTbl.ALL_COLUMNS;
        Cursor cursor = getContentResolver().query(uri, projection, null, null,
                null);
        if (cursor != null) {
            cursor.moveToFirst();
            String category = cursor.getString(cursor
                    .getColumnIndexOrThrow(TodosTbl.Priority));

            for (int i = 0; i < spinner.getCount(); i++) {

                String s = (String) spinner.getItemAtPosition(i);
                if (s.equalsIgnoreCase(category)) {
                    spinner.setSelection(i);
                }
            }

            e1.setText(cursor.getString(cursor
                    .getColumnIndexOrThrow(TodosTbl.Title)));
            e2.setText(cursor.getString(cursor
                    .getColumnIndexOrThrow(TodosTbl.Description)));
            if(TodosTbl.Deadline!=null) {
                try {
                    simpleDateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow(TodosTbl.Deadline)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar cal = simpleDateFormat.getCalendar();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);

                datum.setText(day + "." + (month + 1) + "." + year);
                zeit.setText(hour + ":" + minute);
            }
            // always close the cursor
            cursor.close();
        }
    }

    public void saveState(View view) {
        String titel = String.valueOf(e1.getText());
        String description = String.valueOf(e2.getText());
        ContentValues vals = new ContentValues();
        vals.put("Title", titel);
        vals.put("Description", description);
        if(!datum.getText().toString().equals("Datum")&&!zeit.getText().equals("Zeit")) {
            try {
                simpleDateFormat.parse(datum.getText().toString() + " " + zeit.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar cal = simpleDateFormat.getCalendar();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int hour =cal.get(Calendar.HOUR_OF_DAY);
            int minute =cal.get(Calendar.MINUTE);



            String date = String.valueOf(day + "." +( month+1) + "." + year+" "+hour +":"+minute);
            vals.put("Deadline", date);
        }




        String priority = (String) spinner.getSelectedItem();


        vals.put("Priority", priority);


                vals.put("Done","false");
if(todoUri==null) {
    getContentResolver().insert(TodoContentProvider.CONTENT_URI, vals);
}
        else
{
    getContentResolver().update(todoUri, vals, null, null);
}
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
        }

        public void back(View view)
    {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void done(View view)
    {
        ContentValues vals = new ContentValues();
        vals.put("Done","true");
        if(todoUri==null) {
            getContentResolver().insert(TodoContentProvider.CONTENT_URI, vals);
        }
        else
        {
            getContentResolver().update(todoUri, vals, null, null);
        }

        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        if(!zeit.getText().toString().equals("Zeit")) {
            Date buttonTime = null;
            try {
                buttonTime = SimpleDateFormat.getTimeInstance().parse(zeit.getText().toString()+":0");
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //Create a bundle to pass the date
            Bundle currentTime = new Bundle();
            currentTime.putLong("setTime", buttonTime.getTime());

            //Pass the bundle to the fragment

            newFragment.setArguments(currentTime);
        }


        newFragment.show(getFragmentManager(), "timePicker");


    }
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        if(!datum.getText().toString().equals("Datum")) {
            Date buttonDate = null;
            try {
                buttonDate = SimpleDateFormat.getDateInstance().parse(datum.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //Create a bundle to pass the date
            Bundle currentDate = new Bundle();
            currentDate.putLong("setDate", buttonDate.getTime());

            //Pass the bundle to the fragment

            newFragment.setArguments(currentDate);
        }
        newFragment.show(getFragmentManager(), "datePicker");

    }

}
