package pe.kr.crasy.dunningassignments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by maybe on 16. 11. 3.
 *
 */

class AssignmentsManagerAdapter extends RecyclerView.Adapter<AssignmentsManagerAdapter.ViewHolder>{
    private String[] data;
    AssignmentsManagerAdapter(String[] data) {
        this.data = data;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.test);
        }
    }


    @Override
    public AssignmentsManagerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assignments_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(data[position]);

    }

    @Override
    public int getItemCount() {
        return data.length;
    }
}
