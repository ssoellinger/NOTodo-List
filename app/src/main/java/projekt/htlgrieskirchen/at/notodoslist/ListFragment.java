package projekt.htlgrieskirchen.at.notodoslist;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;





/**
 * Created by Simon on 11.06.2015.
 */

public class ListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String TAG = MainActivity.TAG;
    ListView lstMain;
    ArrayList<Todo> items = new ArrayList<Todo>();
    private OnSelectionChangedListener listener;
    private static final DateFormat DF_DATE = SimpleDateFormat
            .getDateInstance(DateFormat.MEDIUM);
    private static final DateFormat DF_TIME = SimpleDateFormat
            .getTimeInstance(DateFormat.MEDIUM);
    Date date;

    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "oncreat left");
        super.onCreate(savedInstanceState);
        Log.d(TAG, "oncreat left2");

        Log.d(TAG, "oncreat left3");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "oncreatView left");
        View view = inflater.inflate(R.layout.fragment_list, container);
        Log.d(TAG, "oncreatView left2");
        lstMain = (ListView) view.findViewById(android.R.id.list);

            fillArrayList();


        Log.d(TAG, "oncreatView left3");
        lstMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                entrySelected(id);
            }
        });
        Log.d(TAG, "oncreatView left4");

        Log.d(TAG, "oncreatView left5");
        return view;
    }
    private Todo getTodo(Uri uri)
    {
        String[] projection = TodosTbl.ALL_COLUMNS;
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        Todo todo = new Todo();
        if (cursor != null)
        {
            cursor.moveToFirst();
            String title = cursor.getString(cursor.getColumnIndexOrThrow(TodosTbl.Title));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(TodosTbl.Description));
            String priority =cursor.getString(cursor.getColumnIndexOrThrow(TodosTbl.Priority));
            String deadline =cursor.getString(cursor.getColumnIndexOrThrow(TodosTbl.Deadline));




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

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnSelectionChangedListener) {
            listener = (OnSelectionChangedListener) activity;
        } else {
            Toast.makeText(getActivity(), "Activity" + activity.toString() + "does not implement required LeftFragment.OnSelectionChangedListener", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), TodoContentProvider.CONTENT_URI, TodosTbl.ALL_COLUMNS, null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        String [] from = new String[] {TodosTbl.Title, TodosTbl.Description,TodosTbl.Priority,TodosTbl.Deadline};
        int[] to = new int[] {R.id.lblTitel, R.id.lblDescription, R.id.lblPriority, R.id.lblDeadline};
        Log.d(TAG, ""+data.getColumnName(0));
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), R.layout.list_row, data, from, to, 0);
        lstMain.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public interface OnSelectionChangedListener {
        public void onSelectionChanged(long id, Todo item);
    }

    public void entrySelected(long id) {
        Uri uri = Uri.parse(TodoContentProvider.CONTENT_URI + "/" + id);
        Todo item = getTodo(uri);

        if (listener != null) {
            listener.onSelectionChanged(id, item);
        }


    }
}
