package pers.julio.notepad.Activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import pers.julio.notepad.Constants;
import pers.julio.notepad.Fragment.AccessibilityFragment;
import pers.julio.notepad.Fragment.BtAndUartFragment;
import pers.julio.notepad.Fragment.ComponentFragment;
import pers.julio.notepad.Fragment.EventFragment;
import pers.julio.notepad.Fragment.FunctionListFragment;
import pers.julio.notepad.Fragment.NetworkFragment;
import pers.julio.notepad.Fragment.PopupViewFragment;
import pers.julio.notepad.Fragment.RenderFragment;
import pers.julio.notepad.Fragment.TableFragment;
import pers.julio.notepad.Fragment.TestFragment;
import pers.julio.notepad.Fragment.UtilsFragment;
import pers.julio.notepad.R;
import pers.julio.notepad.recyclerview.Utils.ColorUtil;
import pers.julio.notepad.superevent.EventBus.Base.BaseEvent;
import pers.julio.notepad.superevent.EventBus.Base.BaseEventModel;
import pers.julio.notepad.superfragment.NativeFragment.Base.BaseFragmentActivity;
import pers.julio.notepad.superutils.MyStatusBarUtil;

public class ExampleActivity extends BaseFragmentActivity implements View.OnClickListener {
    private TextView mTitle;
    private FunctionListFragment mFragRoot;

    private AccessibilityFragment mFragAccessibility;
    private ComponentFragment mFragComponet;
    private NetworkFragment mFragNetwork;
    private BtAndUartFragment mFragBt;
    private EventFragment mFragEvent;
    private RenderFragment mFragRender;
    private UtilsFragment mFragUtils;
    private TableFragment mFragTable;
    private PopupViewFragment mFragPopupView;
    private TestFragment mTestFrag;

    @Override
    public int ContainerViewId() { return R.id.frag_container; }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        BaseEvent.register(this);
        initTopbar(false);
        initTabs();
        mFragRoot = new FunctionListFragment();
        AddFirstFragment(savedInstanceState, mFragRoot);
    }

    public void initTopbar(boolean isWhiteTheme) {        // 白色主题 & 有色主题
        MyStatusBarUtil.setLightStatusBar(this, isWhiteTheme);   // 使状态栏 的文字可见
        findViewById(R.id.topbar_root).setBackgroundColor(Color.TRANSPARENT); // 沉浸式 TopBar 透明 由背景决定显示
        findViewById(R.id.topbar_decoration).setVisibility(View.INVISIBLE);

        mTitle = findViewById(R.id.topbar_title);
        mTitle.setTextColor(isWhiteTheme ? Color.BLACK : Color.WHITE); // 白色主题用黑色字体，有色主题，用白色字体
        mTitle.setText("SuperNotepad");

        ImageView back = findViewById(R.id.topbar_left);
        back.setImageResource(isWhiteTheme ? R.drawable.topbar_back_black : R.drawable.topbar_back_white);
        back.setOnClickListener(this);
        ImageView setBtn = findViewById(R.id.topbar_right);
        setBtn.setImageResource(isWhiteTheme ? R.drawable.topbar_more_black : R.drawable.topbar_more_white);
        setBtn.setOnClickListener(this);
        //setBtn.setVisibility(View.INVISIBLE);      // View.GONE 隐藏会占据位置 导致标题不居中
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTitle.setText("SuperNotepad");
    }
    public void SetTitle(String title){ mTitle.setText(title); }
    public void AddTestLog(String log){
       if (mTestFrag != null) { mTestFrag.AddLogs(log); }
    }
    public void DoFragAction(int fargId) {
        switch (fargId){
            case Constants.FUN_POPUPVIEW:
                if(mFragEvent == null){ mFragPopupView  = new PopupViewFragment();}
                ShowFragment(mFragPopupView);
                break;
            case Constants.FUN_ACCESSIBILITY:
                if(mFragAccessibility == null){ mFragAccessibility = new AccessibilityFragment(); }
                ShowFragment(mFragAccessibility);
                break;
            case Constants.FUN_TABLE:
                if(mFragTable == null) mFragTable = new TableFragment();
                ShowFragment(mFragTable);
                break;
            case Constants.FUN_COMPONENT:
                if (mFragComponet == null) { mFragComponet = new ComponentFragment(); }
                ShowFragment(mFragComponet);
                break;
            case Constants.FUN_NETWORK:
                if (mFragNetwork == null) {  mFragNetwork = new NetworkFragment(); }
                ShowFragment(mFragNetwork);
                break;
            case Constants.FUN_NFC:
                if(mFragBt == null){ mFragBt = new BtAndUartFragment(); }
                ShowFragment(mFragBt);
                break;
            case Constants.FUN_EVENT:
                if(mFragEvent == null){ mFragEvent = new EventFragment(); }
                ShowFragment(mFragEvent);
                break;
            case Constants.FUN_RENDER:
                if(mFragEvent == null){ mFragRender = new RenderFragment(); }
                ShowFragment(mFragRender);
                break;
            case Constants.FUN_UTILS:
                if(mFragEvent == null){ mFragUtils = new UtilsFragment(); }
                ShowFragment(mFragUtils);
                break;
            default:
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseEvent.unregister(this);
    }

    private void goBack() {
        if(mCurFragment instanceof FunctionListFragment){
            finish();
        }else {
            HideFragment();
        }
    }
    @Override
    public void onBackPressed() { goBack(); }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.topbar_left:
                goBack();
                break;
            case R.id.topbar_right:
                if(mTestFrag == null){ mTestFrag = new TestFragment(); }
                ShowFragment(mTestFrag);
                break;
            case R.id.tv_tab_main:
                setSelected(0);
                break;
            case R.id.tv_tab01:
                setSelected(1);
                break;
            case R.id.tv_tab02:
                setSelected(2);
                break;
            case R.id.tv_tab03:
                setSelected(3);
                break;
            case R.id.tv_tab04:
                setSelected(4);
                break;
        }
    }

    //BaseEvent.register(this);
    //BaseEvent.unregister(this);
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void BaseBulbEvent(BaseEventModel baseEventModel) {
        switch (baseEventModel.getCode()) {
            case BaseEvent.OBJECT:
                mTitle.setText((String) baseEventModel.getData());
                break;
            case BaseEvent.Map:
                HashMap<String, Object> map = (HashMap<String, Object>) baseEventModel.getData();
                if ((int) map.get("res") == 0) {
                    String name = (String) map.get("name");
                    int age = (int) map.get("age");
                    boolean pass = (boolean) map.get("pass");
                    mTitle.setText("Name= " + name + ", Age = " + age);
                }
                break;
            case BaseEvent.JSON:
                //{"res":0,"cmd":41,"dev_id":"sfddd_DT-PLUG","ssid":"Doit","pass":"555"}
                String jsonStr = (String) baseEventModel.getData();
                if (jsonStr.contains("\"res\":0")) {
                    try {
                        JSONObject jsonObject = new JSONObject(jsonStr);
                        String name = jsonObject.getString("name");
                        int age = jsonObject.getInt("age");
                        boolean pass = jsonObject.getBoolean("pass");
                        mTitle.setText("Name= " + name + ", Pass = " + pass);
                    } catch (JSONException e) { e.printStackTrace(); }
                }
                break;
        }
    }

    private int TAB_ICON_SIZE = 20; //ColorUtil.dp2px(this, 20);
    private final int[] COLORS = new int[]{Color.WHITE, Color.parseColor("#33bbff")};
    private TextView mTabMain, mTab1, mTab2, mTab3, mTab4;

    private void initTabs() {
        TAB_ICON_SIZE = ColorUtil.dp2px(this, 25);
        findViewById(R.id.ll_tabs_layout).setBackgroundColor(Color.TRANSPARENT);
        mTab1 = findViewById(R.id.tv_tab01);
        mTab2 = findViewById(R.id.tv_tab02);
        mTab3 = findViewById(R.id.tv_tab03);
        mTab4 = findViewById(R.id.tv_tab04);
        mTabMain = findViewById(R.id.tv_tab_main);
        mTabMain.setOnClickListener(this);
        mTab1.setOnClickListener(this);
        mTab2.setOnClickListener(this);
        mTab3.setOnClickListener(this);
        mTab4.setOnClickListener(this);
        setSelected(0);
    }

    @SuppressLint("NewApi")
    public void setSelected(int index) {
        resetBackground();
        switch (index) {
            case 0: {
                mTabMain.setTextColor(COLORS[1]);
                Drawable drawableTop = getResources().getDrawable(R.drawable.icon_tab_main_selected);
                drawableTop.setBounds(0, 0, TAB_ICON_SIZE, TAB_ICON_SIZE);
                mTabMain.setCompoundDrawables(null, drawableTop, null, null);
                //mTabMain.setCompoundDrawableTintList(ColorStateList.valueOf(selectedColor));
                break;
            }
            case 1:
                mTab1.setTextColor(COLORS[1]);
                Drawable drawableTop1 = getResources().getDrawable(R.drawable.icon_tab01_selected);
                drawableTop1.setBounds(0, 0, TAB_ICON_SIZE, TAB_ICON_SIZE);
                mTab1.setCompoundDrawables(null, drawableTop1, null, null);
                break;
            case 2:
                mTab2.setTextColor(COLORS[1]);
                Drawable drawableTop2 = getResources().getDrawable(R.drawable.icon_tab02_selected);
                drawableTop2.setBounds(0, 0, TAB_ICON_SIZE, TAB_ICON_SIZE);
                mTab2.setCompoundDrawables(null, drawableTop2, null, null);
                break;
            case 3:
                mTab3.setTextColor(COLORS[1]);
                Drawable drawableTop3 = getResources().getDrawable(R.drawable.icon_tab03_selected);
                drawableTop3.setBounds(0, 0, TAB_ICON_SIZE, TAB_ICON_SIZE);
                mTab3.setCompoundDrawables(null, drawableTop3, null, null);
                break;
            case 4:
                mTab4.setTextColor(COLORS[1]);
                Drawable drawableTop4 = getResources().getDrawable(R.drawable.icon_tab04_selected);
                drawableTop4.setBounds(0, 0, TAB_ICON_SIZE, TAB_ICON_SIZE);
                mTab4.setCompoundDrawables(null, drawableTop4, null, null);
                break;
        }
    }

    @SuppressLint("NewApi")
    public void resetBackground() {
        mTabMain.setTextColor(COLORS[0]);
        //mTabMain.setCompoundDrawableTintList(ColorStateList.valueOf(Color.WHITE));
        Drawable drawableTop = getResources().getDrawable(R.drawable.icon_tab_main);
        drawableTop.setBounds(0, 0, TAB_ICON_SIZE, TAB_ICON_SIZE);
        mTabMain.setCompoundDrawables(null, drawableTop, null, null);

        mTab1.setTextColor(COLORS[0]);
        Drawable drawableTop1 = getResources().getDrawable(R.drawable.icon_tab01);
        drawableTop1.setBounds(0, 0, TAB_ICON_SIZE, TAB_ICON_SIZE);
        mTab1.setCompoundDrawables(null, drawableTop1, null, null);

        mTab2.setTextColor(COLORS[0]);
        Drawable drawableTop2 = getResources().getDrawable(R.drawable.icon_tab02);
        drawableTop2.setBounds(0, 0, TAB_ICON_SIZE, TAB_ICON_SIZE);
        mTab2.setCompoundDrawables(null, drawableTop2, null, null);

        mTab3.setTextColor(COLORS[0]);
        Drawable drawableTop3 = getResources().getDrawable(R.drawable.icon_tab03);
        drawableTop3.setBounds(0, 0, TAB_ICON_SIZE, TAB_ICON_SIZE);
        mTab3.setCompoundDrawables(null, drawableTop3, null, null);

        mTab4.setTextColor(COLORS[0]);
        Drawable drawableTop4 = getResources().getDrawable(R.drawable.icon_tab04);
        drawableTop4.setBounds(0, 0, TAB_ICON_SIZE, TAB_ICON_SIZE);
        mTab4.setCompoundDrawables(null, drawableTop4, null, null);
    }
}
