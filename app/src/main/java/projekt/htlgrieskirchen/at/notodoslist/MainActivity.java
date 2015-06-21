package projekt.htlgrieskirchen.at.notodoslist;

import android.app.Activity;


import android.content.Intent;

import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;



public class MainActivity extends Activity implements ListFragment.OnSelectionChangedListener {
    public final static String TAG="FragmentsDemo";
    private DetailFragment detailFragment;
    private boolean showDetail = false;
    public static LocationManager locMan = null;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        detailFragment =(DetailFragment)getFragmentManager().findFragmentById(R.id.frag_detail);
        showDetail =detailFragment !=null && detailFragment.isInLayout();



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
    public void onSelectionChanged(int pos, Todo item) {
        if (showDetail)
            detailFragment.show(pos, item);
        else
            callFragmentActivity(pos, item);
    }
    private void callFragmentActivity(int pos, Todo item) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("POS", pos);
        intent.putExtra("ITEM", item);
        startActivity(intent);

    }






}
