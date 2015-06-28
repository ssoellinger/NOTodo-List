package projekt.htlgrieskirchen.at.notodoslist;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by Simon on 28.06.2015.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker

        Bundle setDate = this.getArguments();
        final Calendar c = Calendar.getInstance();
        if(setDate!=null) {

            Long currDate = setDate.getLong("setDate");

            c.setTimeInMillis(currDate);
        }
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        ((Button) getActivity().findViewById(R.id.datum)).setText(dayOfMonth+"."+(monthOfYear+1)+"."+year);
    }
}
