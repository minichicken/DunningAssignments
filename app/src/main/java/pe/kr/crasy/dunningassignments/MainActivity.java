package pe.kr.crasy.dunningassignments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView mRecyclerView;
    private AssignmentsManagerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Intent assignmentsAlarm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AssignmentsEditActivity.class);
            startActivity(intent);
        });
        /*
        fab.setOnClickListener(view -> Snackbar
                .make(view, "Replace with your own action (feat. lam da)", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
        */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        mRecyclerView = (RecyclerView)findViewById(R.id.assignments);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new AssignmentsManagerAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.addItem(new Date(),"hell", new Date(), "world");
        mAdapter.addItem(new Date(),"hell", new Date(), "world");
        mAdapter.addItem(new Date(),"hell", new Date(), "world");
        mAdapter.addItem(new Date(),"hell", new Date(), "world");
        mAdapter.addItem(new Date(),"hell", new Date(), "world");

        assignmentsAlarm = new Intent(this, AssignmentsAlarmService.class);

        /*
         *
         *  title
         *  location
         *  deadline
         *  description
         *  team people
         *  team leader
         *
        */


    }

    @Override
    public void onPause(){
        super.onPause();
        startService(new Intent(this, AssignmentsAlarmService.class));
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
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

/*
* http://itmir.tistory.com/548
* https://github.com/taehwandev/Android-BlogExample/blob/master/2016-05-08-Android-Overlay-Permission-Example/src/main/java/tech/thdev/android_overlay_permission_example/MainActivity.java
* http://thdev.tech/androiddev/2016/05/08/Android-Overlay-Permission.html
*/

