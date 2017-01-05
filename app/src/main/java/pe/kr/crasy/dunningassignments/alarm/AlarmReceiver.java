package pe.kr.crasy.dunningassignments.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.view.View;
import android.view.WindowManager;

public class AlarmReceiver extends BroadcastReceiver {
    private View drawView;
    private WindowManager windowManager;
    private WindowManager.LayoutParams params;
    public AlarmReceiver(View view, WindowManager winManager) {
        this.drawView = view;
        this.windowManager = winManager;

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_BOOT_COMPLETED)){
            context.startService(new Intent(context.getApplicationContext(), AlarmService.class));
        }
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
}
