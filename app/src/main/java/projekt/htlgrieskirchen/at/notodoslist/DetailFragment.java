package projekt.htlgrieskirchen.at.notodoslist;

import android.app.Fragment;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Simon on 18.06.2015.
 */
public class DetailFragment extends Fragment {
    public static final String TAG = MainActivity.TAG;
    private TextView id;
    private TextView titel;
    private TextView bs;
    private TextView adresse;
    private TextView postition;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "oncreatView right");
        View view = inflater.inflate(R.layout.fragment_detail, container);

        titel = (TextView) view.findViewById(R.id.textView2);
        bs = (TextView) view.findViewById(R.id.textView4);
        adresse = (TextView) view.findViewById(R.id.textView6);
        postition = (TextView) view.findViewById(R.id.textView8);


        return view;
    }

    public void show(int pos, Todo item) {
        Log.d(TAG, "show Detailfragment " + pos + "/" + item.toString());

        titel.setText(item.getTitle());
        bs.setText(item.getDescription());






    }
}
