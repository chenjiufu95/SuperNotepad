package pers.julio.notepad.supercomponent.Base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * ClassName:  BaseTouchView
 * Description: TODO
 * Author;  julio_chan  2020/4/14 13:49
 */
public abstract class BaseView extends FrameLayout  {
    protected View rootView;
    protected Context context;
    protected ViewHolder Viewholder;

    protected void InitView(){}
    protected abstract int SetLayout();

    public BaseView(Context context) { this(context,null,0); }
    public BaseView(Context context, AttributeSet attrs) { this(context, attrs,0); }
    public BaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        rootView = LayoutInflater.from(context).inflate(SetLayout(), this, false);
        Viewholder = new ViewHolder(rootView);
        addView(rootView);
        InitView();
    }

    protected Listener mListener = null;
    public void setListener(Listener listener){mListener = listener;}

    public interface Listener {
        void onItemClick(int pos);
        void onViewClick(View view);

        void onClick(BaseView view,ViewHolder holder);
        void onTouchUp(BaseView view,ViewHolder holder);
        void onTouchDown(BaseView view,ViewHolder holder);
    }
    public static class SimpleListener implements Listener {
        @Override
        public void onItemClick(int pos) { }
        @Override
        public void onViewClick(View view) { }
        @Override
        public void onClick(BaseView view, ViewHolder holder) { }
        @Override
        public void onTouchUp(BaseView view,ViewHolder holder) { }
        @Override
        public void onTouchDown(BaseView view,ViewHolder holder) { }
    }
}
