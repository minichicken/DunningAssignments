package pe.kr.crasy.dunningassignments;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import io.realm.Realm;
import pe.kr.crasy.dunningassignments.database.People;

public class EditActivity extends AppCompatActivity {
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(this);
        realm = Realm.getDefaultInstance();
        setContentView(R.layout.edit_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        realm.executeTransaction(realm1 -> {
            // Add a person
            People person = realm1.createObject(People.class);
            //person.setId(1);
            person.setName("Young Person");
            person.setAge(14);

        });
    }
}
