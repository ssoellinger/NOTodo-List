package projekt.htlgrieskirchen.at.notodoslist;



import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.Normalizer;


/**
 * Created by droithmayr on 11.06.2015.
 */
public class DetailFragment extends Fragment{
    EditText edittext1;
    EditText editText2;
    Spinner spinner;
    public static final String TAG = MainActivity.TAG;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container);

        edittext1 = (EditText) view.findViewById(R.id.editText);
        editText2 = (EditText) view.findViewById(R.id.editText2);
        spinner= (Spinner) view.findViewById(R.id.spinner);





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
        Priority priority= item.getPriority();


        ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(getActivity(),R.array.priority_array,
                android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
       switch(priority){
           case Wichtig:
               spinner.setSelection(0);
               break;

           case Normal:
               spinner.setSelection(1);
               break;
           case Keine_Prioritaet:
               spinner.setSelection(2);
               break;
           default :
               spinner.setSelection(1);




        }

    }


}
