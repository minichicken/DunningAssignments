package pe.kr.crasy.dunningassignments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;

import pe.kr.crasy.dunningassignments.Edit.EditActivity;
import pe.kr.crasy.dunningassignments.alarm.AlarmService;
import pe.kr.crasy.dunningassignments.assignments.AssignmentsAdapter;
import pe.kr.crasy.dunningassignments.assignments.AssignmentsModel;
import pe.kr.crasy.dunningassignments.database.Assignments;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Realm realm;
    private ActionBar actionbar;
    private IntentFilter intentFilter;
    private AssignmentsAdapter adapter;
    private SimpleDateFormat format = new SimpleDateFormat("MM 월 dd일, E요일", Locale.getDefault());
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
            actionbar.setTitle(format.format(new Date()));
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, EditActivity.class))
        );

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        adapter = new AssignmentsAdapter();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.assignments);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(
                new RVOnItemClickListener(this, recyclerView, onItemClickListener));

        intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_DATE_CHANGED);
        registerReceiver(timeReceiver, intentFilter);

        startService(new Intent(this, AlarmService.class));
    }


    @Override
    public void onPause(){
        super.onPause();
        unregisterReceiver(timeReceiver);
    }

    @Override
    public void onResume(){
        super.onResume();
        registerReceiver(timeReceiver, intentFilter);
        adapter.clear();
        for(Assignments assignments : realm.where(Assignments.class).findAll()){
            AssignmentsModel model = new AssignmentsModel();
            model.setTitle(assignments.getTitle());
            model.setDeadline(assignments.getDeadLine());
            model.setLocation(assignments.getLocation());
            adapter.addItem(model);
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        realm.close();
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
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private final BroadcastReceiver timeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(Intent.ACTION_DATE_CHANGED)){
                actionbar.setTitle(format.format(new Date()));
            }
        }
    };

    private RVOnItemClickListener.OnItemClickListener
            onItemClickListener = new RVOnItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            AssignmentsModel item = adapter.getItem(position);
            Toast.makeText(view.getContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onItemLongClick(View view, int position) {
            //show dialog message to remove
        }
    };
}
