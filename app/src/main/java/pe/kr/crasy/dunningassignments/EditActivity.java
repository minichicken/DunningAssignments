package pe.kr.crasy.dunningassignments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmList;
import pe.kr.crasy.dunningassignments.database.Assignments;
import pe.kr.crasy.dunningassignments.database.People;

public class EditActivity extends AppCompatActivity {
    private Realm realm;
    private EditText title;
    private EditText location;
    private EditText startLine;
    private EditText deadLine;
    private EditText metadata;
    private Date startDate;
    private Date deadDate;
    private static Calendar tempCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(this);
        realm = Realm.getDefaultInstance();
        setContentView(R.layout.edit_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        title     = (EditText)findViewById(R.id.editTitle);
        location  = (EditText)findViewById(R.id.editLocation);
        startLine = (EditText)findViewById(R.id.editStartLine);
        deadLine  = (EditText)findViewById(R.id.editDeadLine);
        metadata  = (EditText)findViewById(R.id.editMeta);
        tempCalendar = Calendar.getInstance();

        startLine.setFocusable(false);
        startLine.setFocusableInTouchMode(false);
        deadLine.setFocusable(false);
        deadLine.setFocusableInTouchMode(false);

        startLine.setOnClickListener(view -> {
            DatePicker picker = new DatePicker();
            picker.show(getSupportFragmentManager(), "Date");
            SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault());
            startLine.setText(format.format(tempCalendar.getTime()));
            startDate = tempCalendar.getTime();
        });

        deadLine.setOnClickListener(view -> {
            DatePicker picker = new DatePicker();
            picker.show(getSupportFragmentManager(), "Date");
            SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault());
            deadLine.setText(format.format(tempCalendar.getTime()));
            deadDate = tempCalendar.getTime();
        });
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        save();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.save){
            save();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void save(){
        realm.executeTransaction(realm1 -> {
            Assignments assignments = realm1.createObject(Assignments.class);
            assignments.setTitle(title.getText().toString());
            assignments.setLocation(location.getText().toString());
            assignments.setStartLine(startDate);
            assignments.setDeadLine(deadDate);
            assignments.setMetaData(metadata.getText().toString());

            RealmList<People> peoples = new RealmList<>();

            People people = realm1.createObject(People.class);
            peoples.add(people);
            assignments.setPeople(peoples);
        });
        Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
    }

    public static class TimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle on){
            final Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int min  = calendar.get(Calendar.MINUTE);
            return new TimePickerDialog(
                    getActivity(), this, hour, min, DateFormat.is24HourFormat(getActivity()));
        }

        @Override
        public void onTimeSet(android.widget.TimePicker timePicker, int hour, int min) {
            tempCalendar.set(
                    tempCalendar.get(Calendar.YEAR),
                    tempCalendar.get(Calendar.MONTH),
                    tempCalendar.get(Calendar.DAY_OF_MONTH),
                    hour, min, 0);
        }
    }

    public static class DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int year  = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day   = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(android.widget.DatePicker datePicker, int year, int mon, int day) {
            tempCalendar.set(year, mon, day);
            TimePicker timePicker = new TimePicker();
            timePicker.show(getFragmentManager(), "Time");
        }
    }
}
