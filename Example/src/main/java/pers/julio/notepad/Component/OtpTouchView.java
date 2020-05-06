package pers.julio.notepad.Component;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.DrawableRes;
import pers.julio.notepad.R;
import pers.julio.notepad.supercomponent.Base.BaseView;
import pers.julio.notepad.supercomponent.Base.TouchView;
import pers.julio.notepad.supercomponent.Base.ViewHolder;

/**
 * ClassName:  TouchView
 * Description: TODO
 * Author;  julio_chan  2020/4/14 15:09
 */
public class OtpTouchView extends TouchView {
    private String mTitle;
    private String mDes;
    private Drawable mImage;

    private int mColorDown;
    private int mColorUp;

    @Override
    protected int SetLayout() {
        return R.layout.otp_touch_view_layout;
    }

    private SimpleListener mSimpleListener = new SimpleListener() {
        @Override
        public void onTouchDown(BaseView view, ViewHolder holder) {
            super.onTouchDown(view, holder);
            holder.setTextViewColor(R.id.otp_touch_view_title, mColorDown);
            holder.setTextViewColor(R.id.otp_touch_view_des, mColorDown);
            holder.setViewBackgroundResource(R.id.otp_touch_view_root, R.drawable.my_corner05_border_touch_down);
            if(mEventListener!=null) mEventListener.onTouchDown(view, holder);
        }
        @Override
        public void onTouchUp(BaseView view, ViewHolder holder) {
            super.onTouchUp(view, holder);
            holder.setTextViewColor(R.id.otp_touch_view_title, mColorUp);
            holder.setTextViewColor(R.id.otp_touch_view_des, mColorUp);
            holder.setViewBackgroundResource(R.id.otp_touch_view_root, R.drawable.my_corner05_border_touch_up);
            if(mEventListener!=null) mEventListener.onTouchUp(view, holder);
        }
    };

    public OtpTouchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mColorDown = context.getResources().getColor(R.color.myGrayDark);
        mColorUp = context.getResources().getColor(R.color.white);
        setListener(mSimpleListener);
    }
    public OtpTouchView(Context context, String title, String des, @DrawableRes int imageRes) {
        this(context,null);
        mTitle = title;
        mDes = des;
        mImage = context.getResources().getDrawable(imageRes);
    }

    public void setDatas(String title,String des,Drawable image) {
        mTitle = title;
        mDes = des;
        mImage = image;
        RefreshViews();
    }
    public void RefreshViews() {
        Viewholder.setTextView(R.id.otp_touch_view_title, mTitle);
        Viewholder.setTextView(R.id.otp_touch_view_des, mDes);
        Viewholder.setImageView(R.id.otp_touch_view_image, mImage);
    }

    private EventListener mEventListener = null;
    public void setListener(EventListener listener){mEventListener = listener;}
    public interface EventListener {
        void onClick(View view, ViewHolder holder);
        void onTouchDown(View view, ViewHolder holder);
        void onTouchUp(View view, ViewHolder holder);
    }
}
