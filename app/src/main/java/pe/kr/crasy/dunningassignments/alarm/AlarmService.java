package pe.kr.crasy.dunningassignments.alarm;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Date;

import pe.kr.crasy.dunningassignments.R;
import pe.kr.crasy.dunningassignments.RVOnItemClickListener;

public class AlarmService extends Service{
    private Context context;





    private WindowManager.LayoutParams params;
    private WindowManager manager;
    private BroadcastReceiver receiver;
    private AlarmAdapter adapter;



    @SuppressLint("InflateParams")
    @Override
    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();


        manager = (WindowManager)getSystemService(WINDOW_SERVICE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT);


        RecyclerView recyclerView = new RecyclerView(this);
        recyclerView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));


        Toast.makeText(context, "gegeg", Toast.LENGTH_LONG).show();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        adapter = new AlarmAdapter();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(
                new RVOnItemClickListener(context, recyclerView, onItemClickListener));


        adapter.addItem("Gggg", new Date(), "#ddffdd");
        manager.addView(recyclerView, params);
        //IntentFilter intentFilter = new IntentFilter();
        //intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        //intentFilter.addAction(Intent.ACTION_SCREEN_OFF);

        //receiver = new AlarmReceiver(drawView, windowManager);
        //registerReceiver(receiver, intentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        super.onStartCommand(intent, flags, startId);
        adapter.clear();
        //for(Assignments assignment : realm.where(Assignments.class).findAll()){
        //    adapter.addItem(assignment.getTitle(), assignment.getDeadLine(), "#000000");
        //}
        adapter.notifyDataSetChanged();
        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(receiver != null){
            unregisterReceiver(receiver);
            receiver = null;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private RVOnItemClickListener.OnItemClickListener
            onItemClickListener = new RVOnItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            Toast.makeText(context, "gege", Toast.LENGTH_LONG).show();
            //if(drawView.getWindowToken() != null){
            //   windowManager.removeView(drawView);
            //}
        }

        @Override
        public void onItemLongClick(View view, int position) {

        }
    };
}
