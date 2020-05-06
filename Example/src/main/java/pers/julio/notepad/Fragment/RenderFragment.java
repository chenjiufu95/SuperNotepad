package pers.julio.notepad.Fragment;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import pers.julio.notepad.Activity.ExampleActivity;
import pers.julio.notepad.R;
import pers.julio.notepad.recyclerview.Utils.ColorUtil;

/**
 * ClassName:  RenderFragment
 * Description: TODO
 * Author;  julio_chan  2020/4/11 9:22
 */
public class RenderFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private ExampleActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_render, null);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        activity = (ExampleActivity) getActivity();
        initTabs();
    }


    private final int[] COLORS = new int[]{Color.WHITE, Color.parseColor("#33bbff")};
    private TextView mTabName0,mTabName1,mTabName2,mTabName3,mTabName4;
    private ImageView mTabImage0,mTabImage1,mTabImage2,mTabImage3,mTabImage4;

    private void initTabs() {
        rootView.findViewById(R.id.ll_tabs_layout).setBackgroundColor(Color.TRANSPARENT);
        rootView.findViewById(R.id.tab_layout_main).setOnClickListener(this);
        rootView.findViewById(R.id.tab_layout01).setOnClickListener(this);
        rootView.findViewById(R.id.tab_layout02).setOnClickListener(this);
        rootView.findViewById(R.id.tab_layout03).setOnClickListener(this);
        rootView.findViewById(R.id.tab_layout04).setOnClickListener(this);

        mTabName0 = rootView.findViewById(R.id.tab_main_name);
        mTabImage0 = rootView.findViewById(R.id.tab_main_image);
        mTabName1 = rootView.findViewById(R.id.tab01_name);
        mTabImage1 = rootView.findViewById(R.id.tab01_image);
        mTabName2 = rootView.findViewById(R.id.tab02_name);
        mTabImage2 = rootView.findViewById(R.id.tab02_image);
        mTabName3 = rootView.findViewById(R.id.tab03_name);
        mTabImage3 = rootView.findViewById(R.id.tab03_image);
        mTabName4 = rootView.findViewById(R.id.tab04_name);
        mTabImage4 = rootView.findViewById(R.id.tab04_image);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tab_layout_main:
                setSelected(0);
                break;
            case R.id.tab_layout01:
                setSelected(1);
                break;
            case R.id.tab_layout02:
                setSelected(2);
                break;
            case R.id.tab_layout03:
                setSelected(3);
                break;
            case R.id.tab_layout04:
                setSelected(4);
                break;
        }
    }

    // 通过图片着色 达到图片替换效果
    @SuppressLint("NewApi")
    public void setSelected(int index) {
        resetBackground();
        switch (index) {
            case 0:
                mTabName0.setTextColor(COLORS[1]);
                //mTabName0.setCompoundDrawableTintList(ColorStateList.valueOf(COLORS[1]));
                ColorUtil.setImageViewColor(mTabImage0, COLORS[1]);
                mTabImage0.setImageTintList(ColorStateList.valueOf(COLORS[1]));
                break;
            case 1:
                mTabName1.setTextColor(COLORS[1]);
                mTabImage1.setImageTintList(ColorStateList.valueOf(COLORS[1]));
                break;
            case 2:
                mTabName2.setTextColor(COLORS[1]);
                mTabImage2.setImageTintList(ColorStateList.valueOf(COLORS[1]));
                break;
            case 3:
                mTabName3.setTextColor(COLORS[1]);
                mTabImage3.setImageTintList(ColorStateList.valueOf(COLORS[1]));
                break;
            case 4:
                mTabName4.setTextColor(COLORS[1]);
                mTabImage4.setImageTintList(ColorStateList.valueOf(COLORS[1]));
                break;
        }
    }
    @SuppressLint("NewApi")
    public void resetBackground() {
        mTabName0.setTextColor(COLORS[0]);
        mTabImage0.setImageTintList(ColorStateList.valueOf(COLORS[0]));
        mTabName1.setTextColor(COLORS[0]);
        mTabImage1.setImageTintList(ColorStateList.valueOf(COLORS[0]));
        mTabName2.setTextColor(COLORS[0]);
        mTabImage2.setImageTintList(ColorStateList.valueOf(COLORS[0]));
        mTabName3.setTextColor(COLORS[0]);
        mTabImage3.setImageTintList(ColorStateList.valueOf(COLORS[0]));
        mTabName4.setTextColor(COLORS[0]);
        mTabImage4.setImageTintList(ColorStateList.valueOf(COLORS[0]));
    }
}
