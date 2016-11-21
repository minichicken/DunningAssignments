package pe.kr.crasy.dunningassignments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by maybe on 16. 11. 3.
 *
 */

class AssignmentsManagerAdapter
        extends RecyclerView.Adapter<AssignmentsManagerAdapter.ViewHolder> {
    private ArrayList<AssignmentManagerItem> mAssignmentsManagerItem = new ArrayList<>();

    void addItem(Date date, String title, Date deadline, String location){
        AssignmentManagerItem Item = new AssignmentManagerItem();
        Item.setDate(date);
        Item.setTitle(title);
        Item.setDeadline(deadline);
        Item.setLocation(location);
        mAssignmentsManagerItem.add(Item);
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
    public AssignmentsManagerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assignments_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Date date = mAssignmentsManagerItem.get(position).getDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");
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

    private class AssignmentManagerItem {
        private Date date;
        private String title;
        private Date deadline;
        private String location;

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Date getDeadline() {
            return deadline;
        }

        public void setDeadline(Date deadline) {
            this.deadline = deadline;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }
}
