package projekt.htlgrieskirchen.at.notodoslist;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Simon on 21.06.2015.
 */
public class New_Todo extends Activity {
    EditText e1;
    EditText e2;
    Spinner spinner;
    DatePicker dp;
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
                dp = (DatePicker) findViewById(R.id.datePicker);
        simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy");



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
            try {
                simpleDateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow(TodosTbl.Deadline)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar cal=simpleDateFormat.getCalendar();
            int year=cal.get(Calendar.YEAR);
            int month=cal.get(Calendar.MONTH);
            int day=cal.get(Calendar.DAY_OF_MONTH);

            dp.updateDate(year,month,day);

            // always close the cursor
            cursor.close();
        }
    }

    public void saveState(View view) {
        String titel = String.valueOf(e1.getText());
        String description = String.valueOf(e2.getText());

        String date=String.valueOf(dp.getDayOfMonth()+"-"+(dp.getMonth()+1)+"-"+dp.getYear());

        ContentValues vals = new ContentValues();
        vals.put("Title", titel);
        vals.put("Description", description);

        String priority = (String) spinner.getSelectedItem();


        vals.put("Priority",priority);
        vals.put("Deadline",date);

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




}
