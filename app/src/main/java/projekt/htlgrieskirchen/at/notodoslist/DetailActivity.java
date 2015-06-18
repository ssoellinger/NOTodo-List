package projekt.htlgrieskirchen.at.notodoslist;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Simon on 14.03.2015.
 */
public class DetailActivity extends Activity {
    public final static String TAG = DetailActivity.class.getSimpleName();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //exit, if in Landscape-Mode
        int orientation = getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            finish();
            return;
        }

        setContentView(R.layout.activity_detail);
        handleIntent();
    }

    private void handleIntent()
    {
        Intent intent = getIntent();
        if(intent == null) return;
        Log.d(TAG, "handleIntent detailactiviy");
        DetailFragment detailFragment = (DetailFragment) getFragmentManager().findFragmentById(R.id.frag_detail);

        int pos = intent.getIntExtra("POS", -1);
        Todo item = null;
        if ( intent.hasExtra("ITEM") ) item = (Todo) intent.getSerializableExtra("ITEM");
        if ( item != null && detailFragment != null )
            detailFragment.show(pos, item);
        else
            Log.d(TAG, " item == null");
    }
}