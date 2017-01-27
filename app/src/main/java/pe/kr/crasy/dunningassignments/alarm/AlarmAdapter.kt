package pe.kr.crasy.dunningassignments.alarm

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView

import java.util.ArrayList
import java.util.Date

import pe.kr.crasy.dunningassignments.R

/**
 *
 * Created by maybe on 16. 11. 24.
 */

internal class AlarmAdapter : RecyclerView.Adapter<AlarmAdapter.ViewHolder>() {
    private val mItem = ArrayList<AlarmItem>()

    fun addItem(title: String, date: Date, color: String) {
        val item = AlarmItem()
        item.title = title
        item.date = date
        item.color = Color.parseColor(color)
        mItem.add(item)
    }

    fun clear() {
        val size = mItem.size
        if (size > 0) {
            for (i in 0..size - 1) {
                mItem.removeAt(0)
            }
            this.notifyItemRangeRemoved(0, size)
        }
    }

    internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.assignment_alter_title) as TextView
        var date: TextView = itemView.findViewById(R.id.assignment_alter_date) as TextView
        var layout: LinearLayout = itemView.findViewById(R.id.assignment_alter_size) as LinearLayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.assignment_alarm_top_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setBackgroundColor(mItem[position].color)
        holder.title.text = mItem[position].title

        val end = mItem[position].date
        val fin = (end!!.time - Date().time) / 86400000 //24 * 60 * 60 * 1000
        val d_date = fin.toString() + "일 남았습니다."
        holder.date.text = d_date
        val metrics = holder.itemView.context.resources.displayMetrics

        holder.layout.layoutParams = LinearLayout.LayoutParams(
                metrics.widthPixels / 2,
                WindowManager.LayoutParams.WRAP_CONTENT)
    }

    override fun getItemCount(): Int {
        return mItem.size
    }
}
