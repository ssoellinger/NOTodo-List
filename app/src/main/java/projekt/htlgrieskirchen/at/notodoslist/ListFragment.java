package projekt.htlgrieskirchen.at.notodoslist;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.sql.Date;



/**
 * Created by Simon on 11.06.2015.
 */
public abstract class ListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    public interface OnSelectionChangedListener{
         void onSelectionChanged(long id, Todo item);
    }

    private OnSelectionChangedListener listener;
    ListView lstMain;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container);
        lstMain = (ListView) view.findViewById(android.R.id.list);

        lstMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                entrySelected(id);
            }
        });

        registerForContextMenu(lstMain);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof OnSelectionChangedListener)
            listener = (OnSelectionChangedListener) activity;
        else
            Toast.makeText(getActivity(), "Activity " + activity.toString() + " does not implement required LeftFragment.OnSelectionChangedListener", Toast.LENGTH_LONG).show();
    }

    public void entrySelected (long id)
    {
        Uri uri = Uri.parse(TodoContentProvider.CONTENT_URI + "/" + id);
        Todo item = getTodo(uri);

        if(listener != null)
        {
            listener.onSelectionChanged(id, item);
        }
    }

    private Todo getTodo(Uri uri)
    {
        String[] projection = TodosTbl.ALL_COLUMNS;
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        Todo todo = new Todo();
        if (cursor != null)
        {
            cursor.moveToFirst();
            String titel = cursor.getString(cursor.getColumnIndexOrThrow(TodosTbl.Title));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(TodosTbl.Description));
            Date deadline = Date.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(TodosTbl.Deadline)));
            String priortiy = cursor.getString(cursor.getColumnIndexOrThrow(TodosTbl.Priority));


            todo.setTitle(titel);
            todo.setDescription(description);
            todo.setDeadline(deadline);
            todo.setPrority(Prority.valueOf(priortiy));


            // always close the cursor
            cursor.close();
        }
        return todo;
    }

    @TargetApi(16)
    public void fillArrayList()
    {
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), TodoContentProvider.CONTENT_URI, TodosTbl.ALL_COLUMNS, null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        String [] from = new String[] {TodosTbl.Title, TodosTbl.Description,TodosTbl.Deadline,TodosTbl.Priority};
        int[] to = new int[] {R.id.lblTitel, R.id.lblDescription, R.id.lblDeadline, R.id.lblPriority};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), R.layout.list_row, data, from, to, 0);
        lstMain.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {}

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.menu_context, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.loeschen)
        {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            Uri uri = Uri.parse(TodoContentProvider.CONTENT_URI + "/" + info.id);
            getActivity().getContentResolver().delete(uri, null, null);
            fillArrayList();
        }
        return super.onContextItemSelected(item);
    }
}
