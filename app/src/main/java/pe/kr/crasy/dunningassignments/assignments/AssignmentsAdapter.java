package pe.kr.crasy.dunningassignments.assignments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import pe.kr.crasy.dunningassignments.R;

/**
 *
 * Created by maybe on 16. 11. 24.
 */

public class AssignmentsAdapter
        extends RecyclerView.Adapter<AssignmentsAdapter.ViewHolder>{
    private ArrayList<AssignmentsItem> mAssignmentsManagerItem = new ArrayList<>();

    public void addItem(Date date, String title, Date deadline, String location){
        AssignmentsItem Item = new AssignmentsItem();
        Item.setDate(date);
        Item.setTitle(title);
        Item.setDeadline(deadline);
        Item.setLocation(location);
        mAssignmentsManagerItem.add(Item);
    }

    AssignmentsItem getItem(int position){
        return mAssignmentsManagerItem.get(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView assignment_date;
        TextView assignment_day;
        TextView assignment_title;
        TextView assignment_deadline;
        TextView assignment_location;
        ViewHolder(View itemView) {
            super(itemView);
            assignment_date     = (TextView)itemView.findViewById(R.id.assignment_date);
            assignment_day      = (TextView)itemView.findViewById(R.id.assignment_day);
            assignment_title    = (TextView)itemView.findViewById(R.id.assignment_title);
            assignment_deadline = (TextView)itemView.findViewById(R.id.assignment_deadline);
            assignment_location = (TextView)itemView.findViewById(R.id.assignment_location);
        }
    }

    @Override
    public AssignmentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assignment_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Date date = mAssignmentsManagerItem.get(position).getDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M.dd");
        holder.assignment_date.setText(simpleDateFormat.format(date));
        simpleDateFormat = new SimpleDateFormat("E요일");
        holder.assignment_day.setText(simpleDateFormat.format(date));
        holder.assignment_title.setText(mAssignmentsManagerItem.get(position).getTitle());
        simpleDateFormat = new SimpleDateFormat("M월 W째주 E요일 dd일");
        holder.assignment_deadline.setText(simpleDateFormat.format(mAssignmentsManagerItem.get(position).getDeadline()));
        holder.assignment_location.setText(mAssignmentsManagerItem.get(position).getLocation());
    }

    @Override
    public int getItemCount() {
        return mAssignmentsManagerItem.size();
    }
}
