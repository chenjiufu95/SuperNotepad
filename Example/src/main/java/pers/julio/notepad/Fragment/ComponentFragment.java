package pers.julio.notepad.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import pers.julio.notepad.Activity.ExampleActivity;
import pers.julio.notepad.Component.OtpTouchView;
import pers.julio.notepad.R;
import pers.julio.notepad.supercomponent.Base.BaseView;
import pers.julio.notepad.supercomponent.Base.ViewHolder;

/**
 * ClassName:  ComponentFragment
 * Description: TODO
 * Author;  julio_chan  2020/4/11 9:19
 */
public class ComponentFragment extends Fragment {
    private View rootView;
    private ExampleActivity activity;

    private OtpTouchView mMode, mModeRGB,mModeTemp, mModeRead;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_component, null);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        activity = (ExampleActivity) getActivity();
        //TextView textView = rootView.findViewById(R.id.);
        //rootView.findViewById(R.id.).setOnClickListener(this);

        mModeRGB = rootView.findViewById(R.id.otp_color_mode_rgb);
        mModeRGB.setDatas("RGB", "一", this.getResources().getDrawable(R.drawable.ui4_otp_rgb));
        //mModeRGB.setListener(mTouchViewListener);

        mModeTemp = rootView.findViewById(R.id.otp_color_mode_temp);
        mModeTemp.setDatas("色温", "一", this.getResources().getDrawable(R.drawable.ui4_otp_rgb));
        //mModeTemp.setListener(mTouchViewListener);

        mModeRead = rootView.findViewById(R.id.otp_color_mode_read);
        mModeRead.setDatas("阅读", "一", this.getResources().getDrawable(R.drawable.ui4_otp_rgb));
        //mModeRead.setListener(mTouchViewListener);

        mMode = rootView.findViewById(R.id.otp_color_mode);
        mMode.setDatas("模式", "一", this.getResources().getDrawable(R.drawable.ui4_otp_rgb));
        //mMode.setListener(mTouchViewListener);
    }

   /* private BaseView.Listener mTouchViewListener = new BaseView.SimpleListener(){
        @Override
        public void onTouchDown(BaseView view, ViewHolder holder) {
            holder.setTextViewColor(R.id.otp_touch_view_title, parent.getResources().getColor(R.color.myTouchColor));
            holder.setTextViewColor(R.id.otp_touch_view_des, parent.getResources().getColor(R.color.myTouchColor));
            holder.setViewBackgroundResource(R.id.otp_touch_view_root, R.drawable.my_corner05_border_touch_down);
        }
        @Override
        public void onTouchUp(BaseView view,ViewHolder holder) {
            holder.setTextViewColor(R.id.otp_touch_view_title, parent.getResources().getColor(R.color.White));
            holder.setTextViewColor(R.id.otp_touch_view_des, parent.getResources().getColor(R.color.White));
            holder.setViewBackgroundResource(R.id.otp_touch_view_root, R.drawable.my_corner05_border_touch_up);
        }
    };*/
}
