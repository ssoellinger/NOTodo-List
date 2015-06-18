package projekt.htlgrieskirchen.at.notodoslist;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;

/**
 * Created by droithmayr on 11.06.2015.
 */
public class DetailFragment extends Fragment{
    TextView textView1;
    TextView textView2;
    TextView textView3;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container);

        textView1 = (TextView) view.findViewById(R.id.textView);
        textView2 = (TextView) view.findViewById(R.id.textView2);
        textView3 = (TextView) view.findViewById(R.id.textView3);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void show(long id, Todo item)
    {
        Todo todo = item;


        textView1.setText(item.getTitle());
        textView2.setText(item.getDescription());
        textView3.setText(""+ item.getDeadline());

    }


}
