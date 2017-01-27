package pe.kr.crasy.dunningassignments.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.view.View
import android.view.WindowManager

class AlarmReceiver(private val drawView: View, private val windowManager: WindowManager) : BroadcastReceiver() {
    private val params: WindowManager.LayoutParams? = null

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (action == Intent.ACTION_BOOT_COMPLETED) {
            context.startService(Intent(context.applicationContext, AlarmService::class.java))
        }
        if (action == Intent.ACTION_SCREEN_ON) {
            if (drawView.windowToken == null) {
                windowManager.addView(drawView, params)
            }
        }

        if (action == Intent.ACTION_SCREEN_OFF) {
            if (drawView.windowToken == null) {
                windowManager.addView(drawView, params)
            }
        }
    }
}
