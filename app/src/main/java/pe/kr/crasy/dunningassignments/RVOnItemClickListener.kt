package pe.kr.crasy.dunningassignments

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

/**

 * Created by maybe on 16. 11. 24.
 */

class RVOnItemClickListener(context: Context, recyclerView: RecyclerView, private val listener: RVOnItemClickListener.OnItemClickListener?) : RecyclerView.OnItemTouchListener {

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
        fun onItemLongClick(view: View, position: Int)
    }

    private val gestureDetector: GestureDetector

    init {
        gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return true
            }

            override fun onLongPress(e: MotionEvent) {
                val view = recyclerView.findChildViewUnder(e.x, e.y)
                if (view != null && listener != null) {
                    listener.onItemLongClick(view, recyclerView.getChildAdapterPosition(view))
                }
            }
        })
    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        val view = rv.findChildViewUnder(e.x, e.y)
        if (view != null && listener != null && gestureDetector.onTouchEvent(e)) {
            listener.onItemClick(view, rv.getChildAdapterPosition(view))
            return true
        }
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
}
