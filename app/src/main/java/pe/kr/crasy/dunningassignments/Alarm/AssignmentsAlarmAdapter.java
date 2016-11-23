package pe.kr.crasy.dunningassignments.Alarm;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import pe.kr.crasy.dunningassignments.R;

/**
 *
 * Created by maybe on 16. 11. 21.
 */

class AssignmentsAlarmAdapter extends RecyclerView.Adapter<AssignmentsAlarmAdapter.ViewHolder> {
    private ArrayList<AssignmentsAlarmItem> mItem = new ArrayList<>();
    private View.OnClickListener mOnclickListener = new OnCustomClickListener();

    AssignmentsAlarmAdapter() {}

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assignments_alter_top_layout, parent, false);
        view.setOnClickListener(mOnclickListener);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setBackgroundColor(mItem.get(position).getColor());
        holder.title.setText(mItem.get(position).getTitle());

        Date start = new Date();

        Date end = mItem.get(position).getDate();
        long fin = (end.getTime() - start.getTime())/(24 * 60 * 60 * 1000);

        holder.date.setText(String.valueOf(fin) + "일 남았습니다.");
        DisplayMetrics metrics = holder.itemView.getContext().getResources().getDisplayMetrics();

        holder.layout.setLayoutParams(new LinearLayout.LayoutParams(
                metrics.widthPixels/2,
                WindowManager.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public int getItemCount() {
        return mItem.size();
    }

    void addItem(String title, Date date, String color){
        AssignmentsAlarmItem item = new AssignmentsAlarmItem();
        item.setTitle(title);
        item.setDate(date);
        item.setColor(Color.parseColor(color));
        mItem.add(item);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView date;
        LinearLayout layout;
        ViewHolder(View itemView) {
            super(itemView);
            title   = (TextView)itemView.findViewById(R.id.assignment_alter_title);
            date    = (TextView)itemView.findViewById(R.id.assignment_alter_date);
            layout  = (LinearLayout)itemView.findViewById(R.id.assignment_alter_size);
        }
    }

    private class AssignmentsAlarmItem{
        private String title;
        private Date date;
        private int color;

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        Date getDate() {
            return date;
        }

        void setDate(Date date) {
            this.date = date;
        }
    }

    private class OnCustomClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

        }
    }
}
