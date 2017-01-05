package pe.kr.crasy.dunningassignments.Edit;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmList;
import pe.kr.crasy.dunningassignments.R;
import pe.kr.crasy.dunningassignments.database.Assignments;
import pe.kr.crasy.dunningassignments.database.People;

public class EditActivity extends AppCompatActivity implements EditPresenter.PView{

    private static EditPresenter presenter;
    private LinearLayout linearLayout;
    private LinearLayout.LayoutParams params;
    private EditText peaple;
    private EditText title;
    private EditText location;
    private EditText startLine;
    private EditText deadLine;
    private EditText metadata;

    private SimpleDateFormat format;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        presenter = new EditPresenter(this);

        format = new SimpleDateFormat("yyyy년 MM월 dd일 hh:mm", Locale.getDefault());

        linearLayout = (LinearLayout)findViewById(R.id.content_edit);
        params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        peaple = new EditText(this);
        peaple.setLayoutParams(params);
        peaple.setHint("그룹원 이름");
        linearLayout.addView(peaple);

        title     = (EditText)findViewById(R.id.editTitle);
        location  = (EditText)findViewById(R.id.editLocation);
        startLine = (EditText)findViewById(R.id.editStartLine);
        deadLine  = (EditText)findViewById(R.id.editDeadLine);
        metadata  = (EditText)findViewById(R.id.editMeta);

        startLine.setFocusable(false);
        startLine.setFocusableInTouchMode(false);
        deadLine.setFocusable(false);
        deadLine.setFocusableInTouchMode(false);

        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                presenter.onTitleUpdate(charSequence.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        location.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                presenter.onLocationUpdate(charSequence.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        metadata.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                presenter.onMetaDataUpdate(charSequence.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        startLine.setOnClickListener(view -> {
            DatePicker picker = new DatePicker();
            picker.show(getSupportFragmentManager(), "startLine");
        });
        deadLine.setOnClickListener(view -> {
            DatePicker picker = new DatePicker();
            picker.show(getSupportFragmentManager(), "deadLine");
        });
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        presenter.closeActivity();
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
            presenter.closeActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCloseActivity(boolean isSaved) {
        if(isSaved){
            Toast.makeText(getApplicationContext(), "저장되었습니다.", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "저장할 데이터가 부족합니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStartLineText(Date date) {
        startLine.setText(format.format(date));
    }

    @Override
    public void onDeadLineText(Date date) {
        deadLine.setText(format.format(date));
    }

    public static class TimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener{
        private Calendar calendar;
        private String title;
        public TimePicker(Calendar calendar, String title){
            this.calendar = calendar;
            this.title = title;
        }
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
            calendar.set(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH),
                    hour, min, 0);
            presenter.onDateUpdate(calendar.getTime(), getTag());
        }
    }

    public static class DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        private Calendar calendar = Calendar.getInstance();
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
            calendar.set(year, mon, day);
            TimePicker timePicker = new TimePicker(calendar, getTag());
            timePicker.show(getFragmentManager(), getTag());
        }
    }
}
