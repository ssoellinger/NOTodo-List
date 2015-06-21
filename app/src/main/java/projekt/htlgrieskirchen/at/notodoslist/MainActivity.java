package projekt.htlgrieskirchen.at.notodoslist;

import android.app.Activity;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;



public class MainActivity extends Activity implements ListFragment.OnSelectionChangedListener {
    public final static String TAG = "FragmentsDemo";
    private DetailFragment detailFragment;

    private boolean showDetail = false;
    public static LocationManager locMan = null;
    EditText e1;
    EditText e2;
    Spinner spinner;
    CalendarView cv;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        detailFragment = (DetailFragment) getFragmentManager().findFragmentById(R.id.frag_detail);

        showDetail = detailFragment != null && detailFragment.isInLayout();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        } else if (id == R.id.new_todo) {
            Intent intent =new Intent(this,New_Todo.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onSelectionChanged(long id, Todo item) {
        if (showDetail)
            detailFragment.show(id, item);
        else
            callFragmentActivity(id, item);
    }

    private void callFragmentActivity(long id, Todo item) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("POS", id);
        intent.putExtra("ITEM", item);
        startActivity(intent);

    }




}
