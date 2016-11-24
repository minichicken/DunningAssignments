package pe.kr.crasy.dunningassignments.alarm;

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

import java.util.Date;

import pe.kr.crasy.dunningassignments.R;
import pe.kr.crasy.dunningassignments.RVOnItemClickListener;

public class AlarmService extends Service {
    private WindowManager windowManager;
    private WindowManager.LayoutParams params;
    private View drawView;

    public AlarmService() {
        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSPARENT);
    }

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
        // TODO: Return the communication channel to the service.
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

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if(action.equals(Intent.ACTION_SCREEN_ON)){
                if(drawView.getWindowToken() == null){
                    windowManager.addView(drawView, params);
                }
            }

            if(action.equals(Intent.ACTION_SCREEN_OFF)){
                if(drawView.getWindowToken() == null){
                    windowManager.addView(drawView, params);
                }
            }
        }
    };
}
