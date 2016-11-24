package pe.kr.crasy.dunningassignments.alarm;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import java.util.Date;

import pe.kr.crasy.dunningassignments.R;
import pe.kr.crasy.dunningassignments.RVOnItemClickListener;

public class AlarmService extends Service {
    private WindowManager windowManager;
    private View drawView;
    private BroadcastReceiver receiver;

    public AlarmService() {}

    @SuppressLint("InflateParams")
    @Override
    public void onCreate(){
        super.onCreate();
        LayoutInflater inflater = (LayoutInflater)getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        drawView = inflater.inflate(R.layout.assignment_alarm_top, null);
        windowManager = (WindowManager)getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);

        RecyclerView recyclerView = (RecyclerView)drawView.findViewById(R.id.assignment_alter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(drawView.getContext());
        AlarmAdapter adapter = new AlarmAdapter();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(
                new RVOnItemClickListener(drawView.getContext(), recyclerView, onItemClickListener));

        adapter.addItem("hell", new Date(), "#ffffff");
        adapter.addItem("hell", new Date(), "#000000");
        adapter.addItem("hell", new Date(), "#000000");
        adapter.addItem("hell", new Date(), "#000000");
        adapter.addItem("hell", new Date(), "#000000");


        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);

        receiver = new AlarmReceiver(drawView, windowManager);

        registerReceiver(receiver, intentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        super.onStartCommand(intent, flags, startId);
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
            if(drawView.getWindowToken() != null){
                windowManager.removeView(drawView);
            }
        }

        @Override
        public void onItemLongClick(View view, int position) {

        }
    };
}
