package com.hornet.nepalidateconverter;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.hornet.dateconverter.DateConverter;
import com.hornet.dateconverter.DatePicker.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

public class view extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    ListView listView;
    NotesAdapter notesAdapter;
    notesgetter getnotes;
    Button datePicker;
    String date1,send_date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        listView = (ListView) findViewById(R.id.list1);
        datePicker = (Button) findViewById(R.id.materialDatePickerButton);
        datePicker.setOnClickListener(this);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.materialDatePickerButton) {
            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd = DatePickerDialog.newInstance(view.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH));


//            dpd.setThemeDark(modeDarkDate.isChecked());
//            dpd.dismissOnPause(dismissDate.isChecked());
//            dpd.showYearPickerFirst(showYearFirst.isChecked());
//            if (modeCustomAccentDate.isChecked()) {
//                dpd.setAccentColor(Color.parseColor("#9C27B0"));
//            }
//            if (titleDate.isChecked()) {
//                dpd.setTitle("DatePicker Title");
//            }

            dpd.show(getSupportFragmentManager(), "DatePicker");
            return;
        }
    }
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        date1 =  dayOfMonth + " " + getResources().getString( DateConverter.getNepaliMonth(monthOfYear)) + " " + year;
        send_date=date1.toString();
        //outputDatePicker.setText(date);
        Toast.makeText(view.this,send_date,Toast.LENGTH_LONG).show();
        notesAdapter = new NotesAdapter(view.this, R.layout.activity_view);
        listView.setAdapter(notesAdapter);
        //String reqsubjectcode=spinner.getSelectedItem().toString();
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        ArrayList<DatabaseHelper.Notes> notelist = db.getNotes(send_date);

        for (DatabaseHelper.Notes notes : notelist) {

            getnotes = new notesgetter(notes.date, notes.Person, notes.Place, notes.Task);

            notesAdapter.add(getnotes);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }
}
