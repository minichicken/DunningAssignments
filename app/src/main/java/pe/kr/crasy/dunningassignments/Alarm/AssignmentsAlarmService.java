package pe.kr.crasy.dunningassignments.Alarm;

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

public class AssignmentsAlarmService extends Service {

    WindowManager windowManager;
    WindowManager.LayoutParams params;
    View drawView;

    public AssignmentsAlarmService() {
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
        LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        drawView = inflater.inflate(R.layout.assignments_alter_top, null);
        windowManager = (WindowManager)drawView.getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(drawView, params);

        RecyclerView recyclerView = (RecyclerView)drawView.findViewById(R.id.assignment_alter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(drawView.getContext());
        AssignmentsAlarmAdapter adapter = new AssignmentsAlarmAdapter(windowManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.addItem("hell", new Date(), "#ffffff");
        adapter.addItem("hell", new Date(), "#000000");
        adapter.addItem("hell", new Date(), "#000000");
        adapter.addItem("hell", new Date(), "#000000");
        adapter.addItem("hell", new Date(), "#000000");

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
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
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if(action.equals(Intent.ACTION_SCREEN_ON)){
                windowManager.addView(drawView, params);
            }
        }
    };
}