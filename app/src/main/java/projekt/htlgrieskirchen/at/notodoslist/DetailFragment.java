package projekt.htlgrieskirchen.at.notodoslist;



import android.app.Fragment;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;



/**
 * Created by droithmayr on 11.06.2015.
 */
public class DetailFragment extends Fragment{
    EditText edittext1;
    EditText editText2;

    public static final String TAG = MainActivity.TAG;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container);

        edittext1 = (EditText) view.findViewById(R.id.editText);
        editText2 = (EditText) view.findViewById(R.id.editText2);




        return view;
    }








    @Override
    public void onStart() {
        super.onStart();
    }

    public void show(long id, Todo item)
    {
        Todo todo = item;


        edittext1.setText(item.getTitle());
        editText2.setText(item.getDescription());


    }


}
