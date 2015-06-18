package projekt.htlgrieskirchen.at.notodoslist;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Simon on 11.06.2015.
 */
public class ListFragment extends Fragment {
    public static final String TAG = MainActivity.TAG;
    ListView lstMain;
    ArrayList<Todo> items= new ArrayList<Todo>();
    private OnSelectionChangedListener listener;
    private static final DateFormat DF_DATE = SimpleDateFormat
            .getDateInstance(DateFormat.MEDIUM);
    private static final DateFormat DF_TIME = SimpleDateFormat
            .getTimeInstance(DateFormat.MEDIUM);
    Date date;
    public void onCreate(Bundle savedInstanceState){
        Log.d(TAG, "oncreat left");
        super.onCreate(savedInstanceState);
        Log.d(TAG, "oncreat left2");
        try {
            fillArrayList();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "oncreat left3");
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        Log.d(TAG, "oncreatView left");
        View view =inflater.inflate(R.layout.fragment_list,container);
        Log.d(TAG, "oncreatView left2");
        lstMain= (ListView) view.findViewById(R.id.listView);
        Log.d(TAG, "oncreatView left3");
        lstMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                entrySelected(position);
            }
        });
        Log.d(TAG, "oncreatView left4");

        Log.d(TAG, "oncreatView left5");
        return view;
    }
    public void onStart()
    {
        Log.d(TAG, "onStart ListFragment");
        super.onStart();
        final ArrayAdapter<Todo> adapter=new ArrayAdapter<Todo>(getActivity(),android.R.layout.simple_list_item_1,items);
        lstMain.setAdapter(adapter);
    }
    private void fillArrayList() throws ParseException {
        items =new ArrayList<Todo>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
        date = (Date) dateFormat.parse("1.1.2001");
        items.add(new Todo("Smartphone","Lg Nexus 5, 5 Zoll",Prority.Wichtig,date));
        items.add(new Todo("Spielzeug","Bruder Traktor",Prority.Keine_Prioritaet,date));
        items.add(new Todo("Netzwerktechnik","Cisco Layer 3 Switch 3560 ",Prority.Normal,date));
        items.add(new Todo( "Server", "Windows Server 2008R2", Prority.Wichtig,date) );
    }
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        if(activity instanceof OnSelectionChangedListener)
        {
            listener=(OnSelectionChangedListener) activity;
        }
        else
        {
            Toast.makeText(getActivity(), "Activity" + activity.toString() + "does not implement required LeftFragment.OnSelectionChangedListener", Toast.LENGTH_LONG).show();
        }
    }
    public interface OnSelectionChangedListener
    {
        public void onSelectionChanged(int pos,Todo item);
    }

    public void entrySelected(int pos)
    {
        Todo item =items.get(pos);
        if(listener !=null)
        {
            listener.onSelectionChanged(pos,item);
        }

    }
}
