package pers.julio.notepad.Activity.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Space;
import android.widget.Toast;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.impl.ConfirmPopupView;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pers.julio.notepad.recyclerview.Base.BaseViewHolder;
import pers.julio.notepad.recyclerview.Base.MyBaseAdapter;
import pers.julio.notepad.recyclerview.Bean.BaseItemBean;
import pers.julio.notepad.xpopup.MyPopup.MyInputCenterPopupView;
import pers.julio.notepad.xpopup.MyPopup.MyCenterListView;
import pers.julio.notepad.R;
import pers.julio.notepad.xpopup.MyPopup.MyLoginCenterPopupView;

public class XPopupViewActivity extends AppCompatActivity {
    private static final int ID_ITEM_ONE = 0x1000001;
    private static final int ID_ITEM_TWO = 0x1000002;
    private static final int ID_ITEM_THREE = 0x1000003;

    private static final int MY_XPOPUP_CONFIRM_FULL = 0x000;
    private static final int MY_XPOPUP_CONFIRM_NO_TITLE = 0x001;
    private static final int MY_XPOPUP_CONFIRM_NO_CONTENT = 0x002;
    private static final int MY_XPOPUP_CONFIRM_NO_CANCLE = 0x003;
    private static final int MY_XPOPUP_CONFIRM_INPUT = 0x004;
    private static final int MY_XPOPUP_CONFIRM_LOGIN = 0x005;
    private static final int MY_XPOPUP_CONFIRM_REGIST = 0x006;

    private static final int MY_XPOPUP_SINGLE_CHOICE_LIST_FUL = 0x010;
    private static final int MY_XPOPUP_MULTIPLE_CHOICE_LIST_FUL = 0x011;

    private static final int MY_XPOPUP_LOADING_VERTICAL = 0x030;
    private static final int MY_XPOPUP_LOADING_HORIZONTAL = 0x031;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_popupview);
        initDropDownMenu();
    }

    private DropDownMenu mDropDownMenu;
    private String headers[] = {"确认", "列表", "加载"/*, "依赖", "位置"*/};
    private List<View> popupViews = new ArrayList<>();

    private void initDropDownMenu() {
        mDropDownMenu = findViewById(R.id.ddm_popups_center);
        for (int i = 0; i < headers.length; i++) {
            GetPopupViews(i);
        }
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, new Space(this));
    }

    private void GetPopupViews(int index) {
        View rootView = getLayoutInflater().inflate(R.layout.my_drop_down_menu, null);
        popupViews.add(rootView);
        RecyclerView recyclerView = rootView.findViewById(R.id.rv_my_popup_view_list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        MyBaseAdapter<BaseItemBean> baseAdapter = new MyBaseAdapter<BaseItemBean>(R.layout.item_text_only, this, getItemBeanList(index), false) {
            @Override
            public void bindViewHolder(BaseViewHolder holder, final BaseItemBean itemBean, int pos) {
                holder.setTextView(R.id.item_text_name, itemBean.Key);
                holder.setClickListener(R.id.item_text_root, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch ((int)itemBean.Id) {
                            case MY_XPOPUP_CONFIRM_FULL:
                                showConfirmPopupView("标题", "我是内容我是内容我是内容我是内容我是内容我是内容", false);
                                break;
                            case MY_XPOPUP_CONFIRM_NO_TITLE:
                                showConfirmPopupView("", "我是内容我是内容我是内容我是内容我是内容我是内容", false);
                                break;
                            case MY_XPOPUP_CONFIRM_NO_CONTENT:
                                showConfirmPopupView("标题", "", false);
                                break;
                            case MY_XPOPUP_CONFIRM_NO_CANCLE:
                                showConfirmPopupView("标题", "我是内容我是内容我是内容我是内容我是内容我是内容", true);
                                break;
                            case MY_XPOPUP_CONFIRM_INPUT:
                                showInputConfirmPopupView("重命名", "旧名字", "请输入新的名字");
                                break;
                            case MY_XPOPUP_CONFIRM_LOGIN:
                                showLoginConfirmPopupView("登陆");
                                break;
                            case MY_XPOPUP_LOADING_VERTICAL:
                                showLoadingPopupView("加载中",2,true);
                                break;
                            case MY_XPOPUP_LOADING_HORIZONTAL:
                                showLoadingPopupView("正在处理，请稍后",2,false);
                                break;
                            case MY_XPOPUP_SINGLE_CHOICE_LIST_FUL:
                                ArrayList<BaseItemBean> datas = new ArrayList<>();
                                datas.add(BaseItemBean.builder(XPopupViewActivity.this).setId(ID_ITEM_ONE).setKey("列表显示").setLogo(R.drawable.icon_list).setSelected(true).setShowState(false).build());
                                datas.add(BaseItemBean.builder(XPopupViewActivity.this).setId(ID_ITEM_TWO).setKey("宫格显示").setLogo(R.drawable.icon_grid).setSelected(false).setShowState(false).build());
                                datas.add(BaseItemBean.builder(XPopupViewActivity.this).setId(ID_ITEM_THREE).setKey("设置背景").setLogo(R.drawable.icon_bg_sel).setSelected(false).setShowState(false).build());
                                showListPopupView("单选", datas, false);
                                break;
                            case MY_XPOPUP_MULTIPLE_CHOICE_LIST_FUL:
                                ArrayList<BaseItemBean> datas2 = new ArrayList<>();
                                datas2.add(BaseItemBean.builder(XPopupViewActivity.this).setId(ID_ITEM_ONE).setKey("标题1").setValue("描述1").setSelected(true).setShowState(true).build());
                                datas2.add(BaseItemBean.builder(XPopupViewActivity.this).setId(ID_ITEM_TWO).setKey("标题2").setValue("描述2").setSelected(false).setShowState(true).build());
                                datas2.add(BaseItemBean.builder(XPopupViewActivity.this).setId(ID_ITEM_THREE).setKey("标题3").setValue("描述3").setSelected(false).setShowState(true).build());
                                showListPopupView("多选", datas2, true);
                                break;

                            default:
                                break;
                        }
                    }
                });
            }
        };
        recyclerView.setAdapter(baseAdapter);
    }

    public void showLoadingPopupView(String tips, int timeout, boolean isVertical) {
        final BasePopupView loadingPopup = new XPopup.Builder(XPopupViewActivity.this)
                .dismissOnTouchOutside(false) // 点击外部是否关闭弹窗，默认为true
                //.dismissOnBackPressed(false) // 按返回键是否关闭弹窗，默认为true
                .asLoading(tips)
                .bindLayout(isVertical? R.layout.my_xpopup_loading_vertical:R.layout.my_xpopup_loading_horizontal)
                .show();
        loadingPopup.postDelayed(new Runnable() {
            @Override
            public void run() {
                //loadingPopup.dismiss();
                loadingPopup.dismissWith(new Runnable() {
                    @Override
                    public void run() { }
                });
            }
        }, timeout * 1000);
    }
    private ArrayList<BaseItemBean> getItemBeanList(int index) {
        ArrayList<BaseItemBean> ItemBeanList = new ArrayList<>();
        switch (index) {
            case 0:
                ItemBeanList.add( BaseItemBean.builder(this).setId(MY_XPOPUP_CONFIRM_FULL).setKey("全显示").build());
                ItemBeanList.add( BaseItemBean.builder(this).setId(MY_XPOPUP_CONFIRM_NO_TITLE).setKey("缺标题").build());
                ItemBeanList.add( BaseItemBean.builder(this).setId(MY_XPOPUP_CONFIRM_NO_CONTENT).setKey("缺内容").build());
                ItemBeanList.add( BaseItemBean.builder(this).setId(MY_XPOPUP_CONFIRM_NO_CANCLE).setKey("缺取消").build());
                ItemBeanList.add( BaseItemBean.builder(this).setId(MY_XPOPUP_CONFIRM_INPUT).setKey("带输入").build());
                ItemBeanList.add( BaseItemBean.builder(this).setId(MY_XPOPUP_CONFIRM_LOGIN).setKey("登陆框").build());
                ItemBeanList.add( BaseItemBean.builder(this).setId(MY_XPOPUP_CONFIRM_REGIST).setKey("注册框").build());
                break;
            case 1:
                ItemBeanList.add( BaseItemBean.builder(this).setId(MY_XPOPUP_SINGLE_CHOICE_LIST_FUL).setKey("单选列表").build());
                ItemBeanList.add( BaseItemBean.builder(this).setId(MY_XPOPUP_MULTIPLE_CHOICE_LIST_FUL).setKey("多选列表").build());
                break;
            case 2:
                ItemBeanList.add( BaseItemBean.builder(this).setId(MY_XPOPUP_LOADING_VERTICAL).setKey("加载(竖)").build());
                ItemBeanList.add( BaseItemBean.builder(this).setId(MY_XPOPUP_LOADING_HORIZONTAL).setKey("加载(横)").build());
                break;
        }
        return ItemBeanList;
    }

    /** 显示确认对话框，可定制显示模式(标题，内容，按钮等)
     * @param title
     * @param content
     * @param hideCancle
     */
    public void showConfirmPopupView(String title, String content, boolean hideCancle) {
        final ConfirmPopupView confirmPopupView = new XPopup.Builder(this).dismissOnTouchOutside(false).asConfirm(title, content, null);
        confirmPopupView.bindLayout(R.layout.my_xpopup_confirm);
        if (hideCancle) {
            //confirmPopupView.hideCancelBtn();
            // 通过Post方法 对里面的控件进行控制
            confirmPopupView.post(new Runnable() {
                @Override
                public void run() {
                    confirmPopupView.getPopupContentView().findViewById(R.id.tv_cancel).setVisibility(View.GONE);
                    confirmPopupView.getPopupContentView().findViewById(R.id.view_divider).setVisibility(View.GONE);
                }
            });
        }
        confirmPopupView.show();
    }

    /** 显示 最简单的输入对话框 （不需要的内容填""）
     * @param title   标题
     * @param text  输入框默认内容
     * @param textHint 输入内容提示
     */
    public void showInputConfirmPopupView(String title, String text, String textHint) {
        MyInputCenterPopupView inputBoxPopupView = new MyInputCenterPopupView(this, title, text, textHint);
        inputBoxPopupView.setInputConfirmListener(new OnInputConfirmListener() {
            @Override
            public void onConfirm(String text) {
            }
        });
        new XPopup.Builder(XPopupViewActivity.this).dismissOnTouchOutside(false).asCustom(inputBoxPopupView).show();
    }


    /** 显示登陆框，可自定义 回调函数及验证逻辑
     * @param title
     */
    public void showLoginConfirmPopupView(String title) {
        MyLoginCenterPopupView loginCenterPopupView = new MyLoginCenterPopupView(this, title);
        loginCenterPopupView.setListener(new MyLoginCenterPopupView.Listener() {
            // 用于 自定义中View的点击事件
            @Override
            public void onViewClick(View view) { }

            @Override
            public void onConfirm(String userName, String userPwd) {
            }

            @Override
            public boolean VerifyUserName(String nameInput, String nameErrorStr) {
                nameErrorStr = "输入用户名不能为空";
                return !nameInput.isEmpty();
            }

            @Override
            public boolean VerifyUserPwd(String pwdInput, String pwdErrorStr) {
                pwdErrorStr = "输入密码不能为空";
                return !pwdInput.isEmpty();
            }
        });
        new XPopup.Builder(XPopupViewActivity.this).dismissOnTouchOutside(false).asCustom(loginCenterPopupView).show();
    }

    /**  显示列表形式输入框
     * @param title
     * @param datas
     * @param isMutilSel  是否为多选框
     */
    public void showListPopupView(String title, ArrayList<BaseItemBean> datas, boolean isMutilSel) {
        MyCenterListView myCenterListView = new MyCenterListView(this, title, datas, isMutilSel);
        new XPopup.Builder(this).dismissOnTouchOutside(false).asCustom(myCenterListView).show();
        if (isMutilSel) {
            myCenterListView.setListener(new MyCenterListView.SimpleListener() {
                @Override
                public void onMultiSelConfirm(HashMap<Object, Boolean> Map) {
                    Toast.makeText(XPopupViewActivity.this, "多选结束", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            myCenterListView.setListener(new MyCenterListView.SimpleListener() {
                @Override
                public void onItemClick(int id) {
                    switch (id) {
                        case ID_ITEM_ONE:
                            Toast.makeText(XPopupViewActivity.this, "条目1 被点击", Toast.LENGTH_SHORT).show();
                            break;
                        case ID_ITEM_TWO:
                            Toast.makeText(XPopupViewActivity.this, "条目2 被点击", Toast.LENGTH_SHORT).show();
                            break;
                        case ID_ITEM_THREE:
                            Toast.makeText(XPopupViewActivity.this, "条目3 被点击", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            break;
                    }
                }
            });
        }
    }

}
