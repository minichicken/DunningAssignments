package pe.kr.crasy.dunningassignments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 *
 * Created by maybe on 16. 11. 24.
 */

public class RVOnItemClickListener implements RecyclerView.OnItemTouchListener {
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener listener;
    private GestureDetector gestureDetector;


    public RVOnItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener click){
        listener = click;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e){
                return true;
            }
            @Override
            public void onLongPress(MotionEvent e){
                View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if(view != null && listener != null){
                    listener.onItemLongClick(view, recyclerView.getChildAdapterPosition(view));
                }
            }
        });
    }
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View view = rv.findChildViewUnder(e.getX(), e.getY());
        if(view != null && listener != null && gestureDetector.onTouchEvent(e)){
            listener.onItemClick(view, rv.getChildAdapterPosition(view));
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
