package pers.julio.notepad.Fragment;

import android.content.Context;
import android.icu.util.IslamicCalendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pers.julio.notepad.Activity.ExampleActivity;
import pers.julio.notepad.R;
import pers.julio.notepad.recyclerview.Base.BaseViewHolder;
import pers.julio.notepad.recyclerview.Base.MyBaseAdapter;
import pers.julio.notepad.recyclerview.Bean.BaseItemBean;
import pers.julio.notepad.xpopup.MyPopup.MyBottomPopupView;
import pers.julio.notepad.xpopup.MyPopup.MyFullScreenListView;
import pers.julio.notepad.xpopup.MyPopup.MyInputCenterPopupView;
import pers.julio.notepad.xpopup.MyPopup.MyCenterListView;
import pers.julio.notepad.xpopup.MyPopup.MyLoginCenterPopupView;

/**
 * ClassName:  PopupViewFragment
 * Description: TODO
 * Author;  julio_chan  2020/4/11 9:13
 */
public class PopupViewFragment extends Fragment {
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
    private static final int MY_XPOPUP_FULL_SCEEN_SINGLE_CHOICE_LIST = 0x012;
    private static final int MY_XPOPUP_FULL_SCEEN_MULTIPLE_CHOICE_LIST = 0x013;

    private static final int MY_XPOPUP_LOADING_VERTICAL = 0x030;
    private static final int MY_XPOPUP_LOADING_HORIZONTAL = 0x031;

    private static final int MY_XPOPUP_BOTTOM_CONFRIM = 0x100;
    private static final int MY_XPOPUP_BOTTOM_LIST = 0x101;


    private View rootView;
    private ExampleActivity activity;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_popupview, null);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        activity = (ExampleActivity) getActivity();
        initCenterDDM();
        //initBottomDDM();
    }

    private DropDownMenu mDdmCenter;
    private String Center[] = {"确认", "列表", "加载","底部"/*, "依赖框", "位置框"*/};
    private List<View> centerPopupViews = new ArrayList<>();

    private void initCenterDDM() {
        mDdmCenter = rootView.findViewById(R.id.ddm_popups_center);
        for (int i = 0; i < Center.length; i++) {
            GetCenterPopupViews(i);
        }
        mDdmCenter.setDropDownMenu(Arrays.asList(Center), centerPopupViews, new Space(activity));
    }

    /*private DropDownMenu mDdmBottom;
    private String Bottom[] = {"确认框", "列表框" };
    private List<View> bottomPopupViews = new ArrayList<>();

    private void initBottomDDM() {
        mDdmBottom = rootView.findViewById(R.id.ddm_popups_bottom);
        for (int i = 0; i < Bottom.length; i++) {
            GetCenterPopupViews(i);
        }
        mDdmBottom.setDropDownMenu(Arrays.asList(Center), bottomPopupViews, new Space(activity));
    }*/

    private void GetCenterPopupViews(int index) {
        View rootView = getLayoutInflater().inflate(R.layout.my_drop_down_menu, null);
        centerPopupViews.add(rootView);
        RecyclerView recyclerView = rootView.findViewById(R.id.rv_my_popup_view_list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 4);
        recyclerView.setLayoutManager(gridLayoutManager);
        MyBaseAdapter<BaseItemBean> baseAdapter = new MyBaseAdapter<BaseItemBean>(R.layout.item_text_only, activity, getItemBeanList(index), false) {
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
                                datas.add(BaseItemBean.builder(activity).setId(ID_ITEM_ONE).setKey("列表显示").setLogo(activity.getResources().getDrawable(R.drawable.icon_list)).setSelected(true).setShowState(false).build());
                                datas.add(BaseItemBean.builder(activity).setId(ID_ITEM_TWO).setKey("宫格显示").setLogo(activity.getResources().getDrawable(R.drawable.icon_grid)).setSelected(false).setShowState(false).build());
                                datas.add(BaseItemBean.builder(activity).setId(ID_ITEM_THREE).setKey("设置背景").setLogo(activity.getResources().getDrawable(R.drawable.icon_bg_sel)).setSelected(false).setShowState(false).build());

                                showListPopupView("单选", datas, false);
                                break;
                            case MY_XPOPUP_MULTIPLE_CHOICE_LIST_FUL:
                                ArrayList<BaseItemBean> datas2 = new ArrayList<>();
                                datas2.add(BaseItemBean.builder(activity).setId(ID_ITEM_ONE).setKey("标题1").setValue("描述1").setSelected(true).setShowState(true).build());
                                datas2.add(BaseItemBean.builder(activity).setId(ID_ITEM_TWO).setKey("标题2").setValue("描述2").setSelected(false).setShowState(true).build());
                                datas2.add(BaseItemBean.builder(activity).setId(ID_ITEM_THREE).setKey("标题3").setValue("描述3").setSelected(false).setShowState(true).build());
                                showListPopupView("多选", datas2, true);
                                break;
                            case MY_XPOPUP_FULL_SCEEN_SINGLE_CHOICE_LIST:  // 单选
                                ShowFullScreenListView(activity,LoadDevicesForGroup(),false);
                                break;
                            case MY_XPOPUP_FULL_SCEEN_MULTIPLE_CHOICE_LIST:  // 多选
                                ShowFullScreenListView(activity,LoadDevicesForGroup(),true);
                                //ShowGroupManagePage(activity);
                                break;
                            case MY_XPOPUP_BOTTOM_CONFRIM:
                                showBottomPopupView("标题", "我是内容我是内容我是内容我是", false);
                                break;
                            case MY_XPOPUP_BOTTOM_LIST:
                                showBottomPopupView("标题", "我是内容我是内容我是内容我是", true);
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

    //private ArrayList<BaseDevice> mAddList;
    //private ArrayList<BaseDevice> mRemoveList;
    private MyFullScreenListView mGroupDetailPage;
    public void ShowGroupManagePage(Context context) {
//        if (mAddList == null) { mAddList = new ArrayList<>(); }
//        if (mRemoveList == null) { mRemoveList = new ArrayList<>(); }
//        mAddList.clear();
//        mRemoveList.clear();

        if (mGroupDetailPage == null) {
            mGroupDetailPage = new MyFullScreenListView(context);
            mGroupDetailPage.setListItemLayout(R.layout.item_device_popup);
            mGroupDetailPage.setItemDecoration(null,false);
            mGroupDetailPage.setTitle("分组设备管理");
        }
        mGroupDetailPage.setDatas(LoadDevicesForGroup(), true);
        mGroupDetailPage.setListener(new MyFullScreenListView.Listener() {
            @Override
            public void onViewClick(View view) { }
            @Override
            public void onItemClick(Object id, boolean selected, boolean isMultiselect) {
                String devId = (String) id;
                //BaseDevice device = DeviceManager.shareInstance().getDeviceById(devId);
                //ArrayList<BaseDevice> curGroupDeviceList = DeviceManager.shareInstance().getCurrGroupDeviceList();
                /*if(selected){  // 增加的
                    if(curGroupDeviceList.contains(device)) {
                        mRemoveList.remove(device);
                    }else {
                        mAddList.add(device);
                    }
                }else { // 取消
                    if(curGroupDeviceList.contains(device)){  //取消掉 原先的
                        mRemoveList.add(device);
                    }else {
                        mAddList.remove(device);
                    }
                }*/
            }
            @Override
            public void onConfirm(View view, boolean flag, boolean isMultiselect) {
                if(flag){
                    //onAddDeviceToGroup0(mAddList);
                    //onRemoveDeviceFromGroup0(mRemoveList);
                }
            }
        });
        new XPopup.Builder(context).dismissOnTouchOutside(false).asCustom(mGroupDetailPage).show();
    }

    public ArrayList<BaseItemBean> LoadDevicesForGroup() {
        ArrayList<BaseItemBean> deviceItems = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            BaseItemBean itemBean = BaseItemBean.builder(activity).setId("id_" + (i + 1)).setType(i).setText("name_" + (i + 1))
                    .setKey("state_" + (i + 1)).setValue("mark_" +(i + 1)).setLogo(R.drawable.icon_test).setShowState(true).setSelected( i<10).build();
            deviceItems.add(itemBean);
        }
        return deviceItems;
    }

    private MyFullScreenListView myFullScreenListView;
    public void ShowFullScreenListView(final Context context, ArrayList<BaseItemBean> datas, boolean isMultiselect) {
        myFullScreenListView = new MyFullScreenListView(context, datas, isMultiselect);
        myFullScreenListView.setListItemLayout(R.layout.item_device_popup);
        myFullScreenListView.setItemDecoration(null,false);
        myFullScreenListView.setTitle("分组设备管理");
        myFullScreenListView.setListener(new MyFullScreenListView.Listener() {
            @Override
            public void onViewClick(View view) { }
            @Override
            public void onItemClick(Object id, boolean selected, boolean isMultisel) {
                if (isMultisel) {
                    //String msg = "多项选择: 条目Id: " + (String) id + "==> "+(selected ? "选中" : "取消");
                    //Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                } else {
                    //String msg = "单项选择: 条目Id: " + (String) id + "==> 选中";
                    //Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onConfirm(View view, boolean flag, boolean isMultisel) {
                if(flag){
                    if(isMultisel){
                        ArrayList<BaseItemBean> newAddList = myFullScreenListView.getNewAddList();
                        ArrayList<BaseItemBean> newRemoveList = myFullScreenListView.getNewRemoveList();
                        String msg = "新增: " + newAddList.size() + ", 移除: " + newRemoveList.size();
                        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                        //onAddDeviceToGroup0(mAddList);
                        //onRemoveDeviceFromGroup0(mRemoveList);
                    }else {
                        //Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        new XPopup.Builder(context).dismissOnTouchOutside(false).asCustom(myFullScreenListView).show();
    }

    public void showLoadingPopupView(String tips, int timeout, boolean isVertical) {
        final BasePopupView loadingPopup = new XPopup.Builder(activity)
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
                ItemBeanList.add( BaseItemBean.builder(activity).setId(MY_XPOPUP_CONFIRM_FULL).setKey("全显示").build());
                ItemBeanList.add( BaseItemBean.builder(activity).setId(MY_XPOPUP_CONFIRM_NO_TITLE).setKey("缺标题").build());
                ItemBeanList.add( BaseItemBean.builder(activity).setId(MY_XPOPUP_CONFIRM_NO_CONTENT).setKey("缺内容").build());
                ItemBeanList.add( BaseItemBean.builder(activity).setId(MY_XPOPUP_CONFIRM_NO_CANCLE).setKey("缺取消").build());
                ItemBeanList.add( BaseItemBean.builder(activity).setId(MY_XPOPUP_CONFIRM_INPUT).setKey("带输入").build());
                ItemBeanList.add( BaseItemBean.builder(activity).setId(MY_XPOPUP_CONFIRM_LOGIN).setKey("登陆框").build());
                ItemBeanList.add( BaseItemBean.builder(activity).setId(MY_XPOPUP_CONFIRM_REGIST).setKey("注册框").build());
                break;
            case 1:
                ItemBeanList.add( BaseItemBean.builder(activity).setId(MY_XPOPUP_SINGLE_CHOICE_LIST_FUL).setKey("单选列表").build());
                ItemBeanList.add( BaseItemBean.builder(activity).setId(MY_XPOPUP_MULTIPLE_CHOICE_LIST_FUL).setKey("多选列表").build());

                ItemBeanList.add( BaseItemBean.builder(activity).setId(MY_XPOPUP_FULL_SCEEN_SINGLE_CHOICE_LIST).setKey("全屏单选").build());
                ItemBeanList.add( BaseItemBean.builder(activity).setId(MY_XPOPUP_FULL_SCEEN_MULTIPLE_CHOICE_LIST).setKey("全屏多选").build());
                break;
            case 2:
                ItemBeanList.add( BaseItemBean.builder(activity).setId(MY_XPOPUP_LOADING_VERTICAL).setKey("加载(竖)").build());
                ItemBeanList.add( BaseItemBean.builder(activity).setId(MY_XPOPUP_LOADING_HORIZONTAL).setKey("加载(横)").build());
                break;
            case 3:
                ItemBeanList.add( BaseItemBean.builder(activity).setId(MY_XPOPUP_BOTTOM_LIST).setKey("底部列表").build());
                ItemBeanList.add( BaseItemBean.builder(activity).setId(MY_XPOPUP_BOTTOM_CONFRIM).setKey("底部确认").build());
                ItemBeanList.add( BaseItemBean.builder(activity).setId(MY_XPOPUP_BOTTOM_CONFRIM).setKey("底部确认").build());
                break;
        }
        return ItemBeanList;
    }


    public void showBottomPopupView(String title, String content, boolean hideCancle) {
        final MyBottomPopupView myBottomPopupView = new MyBottomPopupView(activity,title,content);
        if (hideCancle) {
            myBottomPopupView.post(new Runnable() {
                @Override
                public void run() {
                    myBottomPopupView.getPopupContentView().findViewById(R.id.tv_cancel).setVisibility(View.GONE);
                }
            });
        }
        myBottomPopupView.setListener(new MyBottomPopupView.Listener() {
            @Override
            public void onViewClick(View view) {
                Toast.makeText(activity, "View 被点击", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onConfrim(boolean flag) {
                Toast.makeText(activity, flag ? "确定" : "取消", Toast.LENGTH_SHORT).show();
            }
        });
        new XPopup.Builder(activity).dismissOnTouchOutside(false).asCustom(myBottomPopupView).show();
    }

    /** 显示确认对话框，可定制显示模式(标题，内容，按钮等)
     * @param title
     * @param content
     * @param hideCancle
     */
    public void showConfirmPopupView(String title, String content, boolean hideCancle) {
        final ConfirmPopupView confirmPopupView = new XPopup.Builder(activity).dismissOnTouchOutside(false).asConfirm(title, content, null);
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
        MyInputCenterPopupView inputBoxPopupView = new MyInputCenterPopupView(activity, title, text, textHint);
        inputBoxPopupView.setInputConfirmListener(new OnInputConfirmListener() {
            @Override
            public void onConfirm(String text) {
            }
        });
        new XPopup.Builder(activity).dismissOnTouchOutside(false).asCustom(inputBoxPopupView).show();
    }


    /** 显示登陆框，可自定义 回调函数及验证逻辑
     * @param title
     */
    public void showLoginConfirmPopupView(String title) {
        MyLoginCenterPopupView loginCenterPopupView = new MyLoginCenterPopupView(activity, title);
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
        new XPopup.Builder(activity).dismissOnTouchOutside(false).asCustom(loginCenterPopupView).show();
    }

    /**  显示列表形式输入框
     * @param title
     * @param datas
     * @param isMutilSel  是否为多选框
     */
    public void showListPopupView(String title, ArrayList<BaseItemBean> datas, boolean isMutilSel) {
        MyCenterListView myCenterListView = new MyCenterListView(activity, title, datas, isMutilSel);
        new XPopup.Builder(activity).dismissOnTouchOutside(false).asCustom(myCenterListView).show();
        if (isMutilSel) {
            myCenterListView.setListener(new MyCenterListView.SimpleListener() {
                @Override
                public void onMultiSelConfirm(HashMap<Object, Boolean> Map) {
                    Toast.makeText(activity, "多选结束", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            myCenterListView.setListener(new MyCenterListView.SimpleListener() {
                @Override
                public void onItemClick(int id) {
                    switch (id) {
                        case ID_ITEM_ONE:
                            Toast.makeText(activity, "条目1 被点击", Toast.LENGTH_SHORT).show();
                            break;
                        case ID_ITEM_TWO:
                            Toast.makeText(activity, "条目2 被点击", Toast.LENGTH_SHORT).show();
                            break;
                        case ID_ITEM_THREE:
                            Toast.makeText(activity, "条目3 被点击", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            break;
                    }
                }
            });
        }
    }
}
