package pe.kr.crasy.dunningassignments.alarm

import android.annotation.SuppressLint
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.PixelFormat
import android.os.IBinder
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

import org.w3c.dom.Text

import java.util.Date

import pe.kr.crasy.dunningassignments.R
import pe.kr.crasy.dunningassignments.RVOnItemClickListener

class AlarmService : Service() {
    private var context: Context = applicationContext


    private val params: WindowManager.LayoutParams? = null
    private var manager: WindowManager? = null
    private var receiver: BroadcastReceiver? = null
    private var adapter: AlarmAdapter? = null


    override fun onCreate() {
        super.onCreate()

        manager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT)


        val recyclerView = RecyclerView(this)
        recyclerView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT)


        Toast.makeText(context, "gegeg", Toast.LENGTH_LONG).show()
        val layoutManager = LinearLayoutManager(context)
        adapter = AlarmAdapter()
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        recyclerView.addOnItemTouchListener(
                RVOnItemClickListener(context!!, recyclerView, onItemClickListener))


        adapter!!.addItem("Gggg", Date(), "#ddffdd")
        manager!!.addView(recyclerView, params)
        //IntentFilter intentFilter = new IntentFilter();
        //intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        //intentFilter.addAction(Intent.ACTION_SCREEN_OFF);

        //receiver = new AlarmReceiver(drawView, windowManager);
        //registerReceiver(receiver, intentFilter);
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        adapter!!.clear()
        //for(Assignments assignment : realm.where(Assignments.class).findAll()){
        //    adapter.addItem(assignment.getTitle(), assignment.getDeadLine(), "#000000");
        //}
        adapter!!.notifyDataSetChanged()
        return Service.START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        if (receiver != null) {
            unregisterReceiver(receiver)
            receiver = null
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    private val onItemClickListener = object : RVOnItemClickListener.OnItemClickListener {
        override fun onItemClick(view: View, position: Int) {
            Toast.makeText(context, "gege", Toast.LENGTH_LONG).show()
            //if(drawView.getWindowToken() != null){
            //   windowManager.removeView(drawView);
            //}
        }

        override fun onItemLongClick(view: View, position: Int) {

        }
    }
}
