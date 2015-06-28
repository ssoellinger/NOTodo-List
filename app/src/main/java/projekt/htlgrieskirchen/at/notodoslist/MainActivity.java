package projekt.htlgrieskirchen.at.notodoslist;

import android.app.Activity;


import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;

import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;


public class MainActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {
    public final static String TAG = "FragmentsDemo";


String where= null;
    EditText e1;
    EditText e2;
    Spinner spinner;
    CalendarView cv;
    ListView lstMain;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "oncreat main");
        setContentView(R.layout.activity_main);
        lstMain = (ListView) findViewById(R.id.listView);
        Intent intent=getIntent();
        if(intent.getExtras()!=null) {
            where= intent.getStringExtra("Done");

        }
        else
        {
            where = "Done='false'";

        }
        fillArrayList();


        Log.d(TAG, "oncreatView left3");
        lstMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "listener 1");
                entrySelected(id);
            }
        });
        registerForContextMenu(lstMain);
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
            startActivity(new Intent(this, PrefsActivity.class));
            return true;
        } else if (id == R.id.new_todo) {
            Intent intent =new Intent(this,New_Todo.class);
            startActivity(intent);

        }
        else if(id==R.id.history)
        {
            where="Done='true'";
Intent intent=new Intent(this,MainActivity.class);
            intent.putExtra("Done",where);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, " "+where);
        return new CursorLoader(this, TodoContentProvider.CONTENT_URI,TodosTbl.ALL_COLUMNS,where,
                null,
                TodosTbl.SORT_ORDER);


    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        String [] from = new String[] {TodosTbl.Title, TodosTbl.Description,TodosTbl.Priority,TodosTbl.Deadline};
        int[] to = new int[] {R.id.lblTitle, R.id.lblDescription, R.id.lblPriority, R.id.lblDeadline};
        Log.d(TAG, " list " + data.getColumnName(0));

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.list_row, data, from, to, 0);
        Log.d(TAG, " list2 main");

        lstMain.setAdapter(adapter);
        Log.d(TAG, "list3 main");
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {


    }

private Todo getTodo(Uri uri)
{
    String[] projection = TodosTbl.ALL_COLUMNS;
    Cursor cursor = this.getContentResolver().query(uri, projection, null, null, null);
    Todo todo = new Todo();
    if (cursor != null)
    {
        cursor.moveToFirst();
        String title = cursor.getString(cursor.getColumnIndexOrThrow(TodosTbl.Title));
        String description = cursor.getString(cursor.getColumnIndexOrThrow(TodosTbl.Description));
        String priority =cursor.getString(cursor.getColumnIndexOrThrow(TodosTbl.Priority));




        todo.setTitle(title);
        todo.setDescription(description);
        todo.setPriority(Priority.valueOf(priority));



        // always close the cursor
        cursor.close();
    }
    return todo;
}

    public void fillArrayList() {
        getLoaderManager().initLoader(0, null, this);

    }









    public void entrySelected(long id) {
        Uri uri = Uri.parse(TodoContentProvider.CONTENT_URI + "/" + id);
        Log.d(TAG, "entry list");
        Todo item = getTodo(uri);
        Log.d(TAG, "entry list2");
        Uri todoUri=null;
       Intent intent =new Intent(this,New_Todo.class);
        todoUri =Uri.parse(TodoContentProvider.CONTENT_URI + "/" + id);
        intent.putExtra(TodoContentProvider.CONTENT_ITEM_TYPE, todoUri);

        startActivity(intent);



    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        this.getMenuInflater().inflate(R.menu.menu_context, menu);
        if(!where.contentEquals("Done='false'")) {
            menu.getItem(1).setTitle("Rueckgaeng machen");
        }


        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.loeschen)
        {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            Uri uri = Uri.parse(TodoContentProvider.CONTENT_URI + "/" + info.id);
            this.getContentResolver().delete(uri, null, null);
            fillArrayList();
        }
        else if(id==R.id.erledigt)
        {AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            Uri uri = Uri.parse(TodoContentProvider.CONTENT_URI + "/" + info.id);
            ContentValues vals = new ContentValues();
            if(where.contentEquals("Done='false'")) {


                vals.put("Done","true");

            } else{

                vals.put("Done","false");
            }
            getContentResolver().update(uri, vals, null, null);


            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        return super.onContextItemSelected(item);
    }


}
