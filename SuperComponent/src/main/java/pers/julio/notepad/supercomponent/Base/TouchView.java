package pers.julio.notepad.supercomponent.Base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * ClassName:  TouchView
 * Description: TODO
 * Author;  julio_chan  2020/4/20 12:46
 */
public abstract class TouchView extends BaseView implements View.OnTouchListener{
    public TouchView(Context context) { this(context, null, 0); }
    public TouchView(Context context, AttributeSet attrs) { this(context, attrs, 0); }
    public TouchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnTouchListener(this);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mListener != null) mListener.onTouchDown(this, Viewholder);
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if (mListener != null) mListener.onTouchUp(this, Viewholder);
                break;
        }
        return true;
    }
}
