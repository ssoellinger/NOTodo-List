package projekt.htlgrieskirchen.at.notodoslist;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by Simon on 21.06.2015.
 */
public class New_Todo extends Activity {
    EditText e1;
    EditText e2;
    Spinner spinner;
    CalendarView cv;
    ListFragment listFragment;
    public static final String TAG = MainActivity.TAG;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_todo);
        listFragment =(ListFragment) getFragmentManager().findFragmentById(R.id.frag_list);
        addTodo();
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
                cv = (CalendarView) findViewById(R.id.calendarView);


            }


    public void saveState(View view) {
        String titel = String.valueOf(e1.getText());
        String description = String.valueOf(e2.getText());
        ContentValues vals = new ContentValues();
        vals.put("Title", titel);
        vals.put("Description", description);

        String priority = (String) spinner.getSelectedItem();


        switch (priority) {
            case "Wichtig":
                vals.put("Priority", String.valueOf(Priority.Wichtig));
                break;

            case "Normal":
                vals.put("Priority", String.valueOf(Priority.Normal));
                break;
            case "Keine":
                vals.put("Priority", String.valueOf(Priority.Keine_Prioritaet));
                break;
            default:
                vals.put("Priority", String.valueOf(Priority.Keine_Prioritaet));
        }

                vals.put("Deadline", cv.getDate());


                getContentResolver().insert(TodoContentProvider.CONTENT_URI, vals);

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
        }

        public void back(View view)
    {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }



}
