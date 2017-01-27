package pe.kr.crasy.dunningassignments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Vibrator
import android.support.v7.app.ActionBar
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

import io.realm.Realm
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.main_app_bar.*
import kotlinx.android.synthetic.main.main_content.*

import pe.kr.crasy.dunningassignments.edit.EditActivity
import pe.kr.crasy.dunningassignments.assignments.AssignmentsAdapter
import pe.kr.crasy.dunningassignments.assignments.AssignmentsModel
import pe.kr.crasy.dunningassignments.database.Assignments
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var realm: Realm by Delegates.notNull() //null이 아닐거라고 명시 해주는듯..
    private var vibrator: Vibrator? = null
    private var actionbar: ActionBar? = null
    private var intentFilter: IntentFilter = IntentFilter()
    private var adapter: AssignmentsAdapter = AssignmentsAdapter()
    private val format = SimpleDateFormat("MM 월 dd일, E요일", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Realm.init(applicationContext)
        realm = Realm.getDefaultInstance()
        setContentView(R.layout.main_activity)
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            actionbar = supportActionBar
            actionbar!!.title = format.format(Date())
        }

        fab.setOnClickListener { view -> startActivity(Intent(this@MainActivity, EditActivity::class.java)) }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        val linearLayoutManager = LinearLayoutManager(this)
        assignments.layoutManager = linearLayoutManager
        assignments.setHasFixedSize(true)
        assignments.adapter = adapter
        assignments.addOnItemTouchListener(
                RVOnItemClickListener(this, assignments, onItemClickListener))

        intentFilter.addAction(Intent.ACTION_DATE_CHANGED)
        registerReceiver(timeReceiver, intentFilter)

        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        //startService(new Intent(this, AlarmService.class));
    }


    public override fun onPause() {
        super.onPause()
        unregisterReceiver(timeReceiver)
    }

    public override fun onResume() {
        super.onResume()
        registerReceiver(timeReceiver, intentFilter)
        adapter.clear()
        for (assignments in realm.where(Assignments::class.java).findAll()) {
            val model = AssignmentsModel()
            model.title = assignments.title
            model.deadline = assignments.deadLine
            model.location = assignments.location
            adapter.addItem(model)
        }
    }

    public override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private val timeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (action == Intent.ACTION_DATE_CHANGED) {
                actionbar!!.title = format.format(Date())
            }
        }
    }

    private val onItemClickListener = object : RVOnItemClickListener.OnItemClickListener {
        override fun onItemClick(view: View, position: Int) {
            val item = adapter.getItem(position)
            Toast.makeText(view.context, item.title, Toast.LENGTH_SHORT).show()
        }

        override fun onItemLongClick(view: View, position: Int) {
            vibrator!!.vibrate(100)
            val title = adapter.getItem(position).title
            val items = arrayOf("수정", "삭제")
            AlertDialog.Builder(this@MainActivity)
                    .setTitle(title)
                    .setItems(items) { dialogInterface, i ->
                        if (i == 0) {
                            Toast.makeText(applicationContext, "수정", Toast.LENGTH_SHORT).show()
                        } else {
                            realm.executeTransaction { realm1 ->
                                val assignments = realm1.where(Assignments::class.java).findAll()
                                assignments.deleteFromRealm(position)
                            }
                            adapter.removeItem(position)
                            Toast.makeText(applicationContext, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }.create().show()
        }
    }
}
