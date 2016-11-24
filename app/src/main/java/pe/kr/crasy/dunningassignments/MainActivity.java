package pe.kr.crasy.dunningassignments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;
import pe.kr.crasy.dunningassignments.alarm.AlarmService;
import pe.kr.crasy.dunningassignments.assignments.AssignmentsAdapter;
import pe.kr.crasy.dunningassignments.database.People;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Realm realm;
    private AssignmentsAdapter assignmentsAdapter;
    private ActionBar actionbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();
        setContentView(R.layout.main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            actionbar = getSupportActionBar();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM 월 dd일,\n E요일", Locale.getDefault());
            actionbar.setTitle(dateFormat.format(new Date()));
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view ->
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        RecyclerView.LayoutManager assignmentLayoutManager = new LinearLayoutManager(this);
        assignmentsAdapter = new AssignmentsAdapter();
        RecyclerView assignmentsView = (RecyclerView) findViewById(R.id.assignments);
        assignmentsView.setHasFixedSize(true);
        assignmentsView.setLayoutManager(assignmentLayoutManager);
        assignmentsView.setAdapter(assignmentsAdapter);
        assignmentsView.addOnItemTouchListener(
                new RVOnItemClickListener(this, assignmentsView, onItemClickListener));

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_DATE_CHANGED);
        registerReceiver(timeReceiver, intentFilter);

        startService(new Intent(this, AlarmService.class));


        assignmentsAdapter.addItem(new Date(),"hell", new Date(), "world");
        assignmentsAdapter.addItem(new Date(),"hell", new Date(), "world");
        assignmentsAdapter.addItem(new Date(),"hell", new Date(), "world");
        assignmentsAdapter.addItem(new Date(),"hell", new Date(), "world");
        assignmentsAdapter.addItem(new Date(),"hell", new Date(), "world");
        assignmentsAdapter.addItem(new Date(),"hell", new Date(), "world");

        for(People people : realm.where(People.class).findAll()){
            Log.d("ddd", people.getName());
        }
        realm.executeTransaction(realm1 -> {
            // Add a person
            People person = realm1.createObject(People.class);
            //person.setId(1);
            person.setName("Young Person");
            person.setAge(14);

        });
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(timeReceiver != null){
            unregisterReceiver(timeReceiver);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private BroadcastReceiver timeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(Intent.ACTION_DATE_CHANGED)){
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM 월 dd일,\n E요일", Locale.getDefault());
                actionbar.setTitle(dateFormat.format(new Date()));
            }
        }
    };

    private RVOnItemClickListener.OnItemClickListener
            onItemClickListener = new RVOnItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {

        }

        @Override
        public void onItemLongClick(View view, int position) {

        }
    };
}
