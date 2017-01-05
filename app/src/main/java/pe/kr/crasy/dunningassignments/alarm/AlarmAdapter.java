package pe.kr.crasy.dunningassignments.alarm;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import pe.kr.crasy.dunningassignments.R;

/**
 *
 * Created by maybe on 16. 11. 24.
 */

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ViewHolder> {
    private ArrayList<AlarmItem> mItem = new ArrayList<>();

    public void addItem(String title, Date date, String color){
        AlarmItem item = new AlarmItem();
        item.setTitle(title);
        item.setDate(date);
        item.setColor(Color.parseColor(color));
        mItem.add(item);
    }

    public void clear(){
        int size = mItem.size();
        if(size > 0){
            for(int i = 0; i < size; i++){
                mItem.remove(0);
            }
            this.notifyItemRangeRemoved(0, size);
        }
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

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assignment_alarm_top_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setBackgroundColor(mItem.get(position).getColor());
        holder.title.setText(mItem.get(position).getTitle());

        Date end = mItem.get(position).getDate();
        long fin = (end.getTime() - new Date().getTime())/86400000; //24 * 60 * 60 * 1000
        String d_date = String.valueOf(fin) + "일 남았습니다.";
        holder.date.setText(String.valueOf(d_date));
        DisplayMetrics metrics = holder.itemView.getContext().getResources().getDisplayMetrics();

        holder.layout.setLayoutParams(new LinearLayout.LayoutParams(
                metrics.widthPixels/2,
                WindowManager.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public int getItemCount() {
        return mItem.size();
    }
}
