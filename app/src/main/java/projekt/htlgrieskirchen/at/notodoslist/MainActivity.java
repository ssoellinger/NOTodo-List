package projekt.htlgrieskirchen.at.notodoslist;

import android.app.Activity;


import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;

import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

        fillArrayList();


        Log.d(TAG, "oncreatView left3");
        lstMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "listener 1");
                entrySelected(id);
            }
        });
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

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, TodoContentProvider.CONTENT_URI,TodosTbl.ALL_COLUMNS,"Done='false' or Done=null",
                null,
                null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        String [] from = new String[] {TodosTbl.Title, TodosTbl.Description,TodosTbl.Priority,TodosTbl.Deadline};
        int[] to = new int[] {R.id.lblTitel, R.id.lblDescription, R.id.lblPriority, R.id.lblDeadline};
        Log.d(TAG, " list "+data.getColumnName(0));
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.list_row, data, from, to, 0);
        Log.d(TAG, " list2 main");

        lstMain.setAdapter(adapter);
        Log.d(TAG, "list3 main");
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

/*

    @Override
    public void onSelectionChanged(long id, Todo item) {
        if (showDetail) {
            Log.d(TAG, "oncreat main3");
            detailFragment.show(id, item);
            Log.d(TAG, "oncreat main4");
        }
        else
            callFragmentActivity(id, item);
    }

    private void callFragmentActivity(long id, Todo item) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("POS", id);
        intent.putExtra("ITEM", item);
        startActivity(intent);

    }
*/
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
    /*   public void onStart() {
           Log.d(TAG, "onStart ListFragment");
           super.onStart();
           final ArrayAdapter<Todo> adapter = new ArrayAdapter<Todo>(getActivity(), android.R.layout.simple_list_item_1, items);
           lstMain.setAdapter(adapter);
       }
   */
    public void fillArrayList() {
        getLoaderManager().initLoader(0, null, this);
         /*    items = new ArrayList<Todo>();
     SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
      date = (Date) dateFormat.parse("1.1.2001");
        items.add(new Todo("Smartphone", "Lg Nexus 5, 5 Zoll", Priority.Wichtig, date));
        items.add(new Todo("Spielzeug", "Bruder Traktor", Priority.Keine_Prioritaet, date));
        items.add(new Todo("Netzwerktechnik", "Cisco Layer 3 Switch 3560 ", Priority.Normal, date));
        items.add(new Todo("Server", "Windows Server 2008R2", Priority.Wichtig, date));
    */
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


}
