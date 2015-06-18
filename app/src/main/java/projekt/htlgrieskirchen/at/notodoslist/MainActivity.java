package projekt.htlgrieskirchen.at.notodoslist;

import android.app.Activity;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;



public class MainActivity extends Activity implements ListFragment.OnSelectionChangedListener,LocationListener {
    private DetailFragment detailFragment;
    private boolean showDetail = false;
    private ListFragment listFragment;

    private static LocationManager locMan = null;
    EditText e1;
    EditText e2;
    EditText e3;
    EditText e4;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        detailFragment = (DetailFragment) getFragmentManager().findFragmentById(R.id.frag_detail);
        listFragment= (ListFragment) getFragmentManager().findFragmentById(R.id.frag_list);
        showDetail = detailFragment != null && detailFragment.isInLayout();
    }
    @Override
    public void onSelectionChanged(long pos, Todo item) {
        if(showDetail)
            detailFragment.show(pos, item);
        else
            callFragmentActivity(pos, item);
    }
    private void callFragmentActivity(long pos, Todo item)
    {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("POS", pos);
        intent.putExtra("ITEM", item);
        startActivity(intent);

        setSpinner();

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
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }



    public void setSpinner()
    {
        Spinner mySpinner = (Spinner) findViewById(R.id.spinner);
        mySpinner.setAdapter(new ArrayAdapter<Prority>(this, android.R.layout.simple_spinner_item, Prority.values()));
    }


}
