package pe.kr.crasy.dunningassignments.assignments

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Locale

import pe.kr.crasy.dunningassignments.R

/**

 * Created by maybe on 16. 11. 24.
 */

class AssignmentsAdapter : RecyclerView.Adapter<AssignmentsAdapter.ViewHolder>() {
    private val mAssignmentsManagerItem = ArrayList<AssignmentsModel>()

    fun addItem(model: AssignmentsModel) {
        mAssignmentsManagerItem.add(model)
    }

    fun getItem(position: Int): AssignmentsModel {
        return mAssignmentsManagerItem[position]
    }

    fun removeItem(position: Int) {
        mAssignmentsManagerItem.removeAt(position)
        this.notifyItemRemoved(position)
    }

    fun clear() {
        val size = mAssignmentsManagerItem.size
        if (size > 0) {
            for (i in 0..size - 1) {
                mAssignmentsManagerItem.removeAt(0)
            }
            this.notifyItemRangeRemoved(0, size)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var assignment_date: TextView = itemView.findViewById(R.id.assignment_date) as TextView
        var assignment_day: TextView = itemView.findViewById(R.id.assignment_day) as TextView
        var assignment_title: TextView = itemView.findViewById(R.id.assignment_title) as TextView
        var assignment_deadline: TextView = itemView.findViewById(R.id.assignment_deadline) as TextView
        var assignment_location: TextView = itemView.findViewById(R.id.assignment_location) as TextView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssignmentsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.assignment_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date = mAssignmentsManagerItem[position].deadline
        var simpleDateFormat = SimpleDateFormat("M.dd", Locale.getDefault())
        holder.assignment_date.text = simpleDateFormat.format(date)
        simpleDateFormat = SimpleDateFormat("E요일", Locale.getDefault())
        holder.assignment_day.text = simpleDateFormat.format(date)
        holder.assignment_title.text = mAssignmentsManagerItem[position].title
        simpleDateFormat = SimpleDateFormat("M월 W째주 E요일 dd일", Locale.getDefault())
        holder.assignment_deadline.text = simpleDateFormat.format(mAssignmentsManagerItem[position].deadline)
        holder.assignment_location.text = mAssignmentsManagerItem[position].location
    }

    override fun getItemCount(): Int {
        return mAssignmentsManagerItem.size
    }
}
