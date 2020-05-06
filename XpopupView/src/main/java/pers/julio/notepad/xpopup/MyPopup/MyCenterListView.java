package pers.julio.notepad.xpopup.MyPopup;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.lxj.xpopup.core.CenterPopupView;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pers.julio.notepad.recyclerview.Base.BaseViewHolder;
import pers.julio.notepad.recyclerview.Base.MyBaseAdapter;
import pers.julio.notepad.recyclerview.Base.MyBaseDecoration;
import pers.julio.notepad.recyclerview.Bean.BaseItemBean;
import pers.julio.notepad.recyclerview.Bean.DividerBean;
import pers.julio.notepad.recyclerview.MySimpleDividerLookup;
import pers.julio.notepad.recyclerview.Utils.ColorUtil;
import pers.julio.notepad.xpopup.R;

/**
 * ClassName:  MyListCenterPopupView
 * Description: TODO
 * Author;  julio_chan  2020/4/5 16:34
 */
public class MyCenterListView extends CenterPopupView implements View.OnClickListener{
    private Context mContext;
    private String mTitle;
    private boolean mIsMultiselect = false;
    private HashMap<Object,Boolean> mMultiSelectMap = null;

    private boolean IsHaveLogo = false;
    private TextView tv_title;

    public void setHaveLogo(boolean haveLogo) { IsHaveLogo = haveLogo; }

    /** 主动设置 重新显示控制栏，及其样式。（默认下，单选不显示 控制栏）
     * @param confirm   确认按钮 样式: 设置为 null 为隐藏， 设为空"" 这默认文字
     * @param cancel    取消按钮 样式: 设置为 null 为隐藏， 设为空"" 这默认文字
     */
    public void setControlPanel(final String confirm, final String cancel){
        this.post(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.list_ctrl_layout).setVisibility(VISIBLE);
                TextView confirmBtn = findViewById(R.id.list_confirm);
                TextView cancelBtn = findViewById(R.id.list_cancel);
                confirmBtn.setVisibility(confirm != null ? VISIBLE : GONE);
                cancelBtn.setVisibility(cancel != null ? VISIBLE : GONE);
                if(confirm != null && !"".equals(confirm.trim())){
                    confirmBtn.setText(confirm);
                }
                if(cancel != null && !"".equals(cancel.trim())){
                    cancelBtn.setText(confirm);
                }
            }
        });
    }

    public MyCenterListView(@NonNull Context context, String title, ArrayList<BaseItemBean> datas, boolean isMultiselect) {
        super(context);
        mContext = context;
        mTitle = title;
        mDatas = datas;
        mIsMultiselect = isMultiselect;
        if (mIsMultiselect) {
            getMultiSelectMap();
        }
    }
    public MyCenterListView(@NonNull Context context, String title, ArrayList<BaseItemBean> datas, boolean isMultiselect, Listener listener) {
        super(context);
        mContext = context;
        mTitle = title;
        mDatas = datas;
        mListener = listener;
        mIsMultiselect = isMultiselect;
        if (mIsMultiselect) {
            getMultiSelectMap();
        }
    }
    public void getMultiSelectMap() {
        if (mDatas != null && mDatas.size() > 0) {
            mMultiSelectMap = new HashMap<>();
            int size = mDatas.size();
            for (int i = 0; i < size; i++) {
                BaseItemBean itemBean = mDatas.get(i);
                if(itemBean!=null) { mMultiSelectMap.put(itemBean.Id, itemBean.Selected); }
            }
        }
    }



    @Override
    protected void onCreate() {
        super.onCreate();
        initRecyclerView();
        findViewById(R.id.list_ctrl_layout).setVisibility(mIsMultiselect ? VISIBLE : GONE);
        findViewById(R.id.list_confirm).setOnClickListener(this);
        findViewById(R.id.list_cancel).setOnClickListener(this);
        tv_title = findViewById(R.id.list_title);
        SetTitle(mTitle);
    }

    public void SetTitle(String title) {
        mTitle = title;
        if(tv_title == null) return;

        if(mTitle == null || mTitle.equals("")) {
            tv_title.setVisibility(GONE);
        }else {
            tv_title.setVisibility(VISIBLE);
            tv_title.setText(mTitle);
        }
    }

    /** 设置列表Item 的布局样式，
     * @param itemLayout 注意：请在 默认布局基础上修改(id 不变) 默认布局：R.layout.my_xpopup_center_list_item
     */
    public void setListItemLayout(int itemLayout) { mListItemLayout = itemLayout; }
    protected int mListItemLayout = R.layout.my_xpopup_center_list_item;

    /** 设置PopupView 的布局样式，
     * @param popupLayout 注意：请在 默认布局基础上修改(id 不变)  默认布局：R.layout.my_xpopup_full_screen_list
     */
    public void setPopupLayout(int popupLayout) {  mPopupLayout = popupLayout; }
    protected int mPopupLayout = R.layout.my_xpopup_center_list;

    @Override
    protected int getImplLayoutId() { return mPopupLayout; }


    /**  刷新数据*/
    private void refreshDatas(ArrayList<BaseItemBean> datas) {
        if (mDividers != null) { getDividers(datas); }
        mAdapter.refreshDatas(datas);
    }

    private RecyclerView mRecyclerView;
    private ArrayList<BaseItemBean> mDatas;
    private MyBaseAdapter<BaseItemBean> mAdapter;
    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.list_recyclerView);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 1);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            public int getSpanSize(int position) { return gridLayoutManager.getSpanCount(); }
        });

        //mDatas.add(new BaseItemBean(ID_ITEM_ONE, "标题1", "描述1",/*"图片1"*/null,true));
        //mDatas.add(new BaseItemBean(ID_ITEM_TWO, "标题2", "描述2", this.getResources().getDrawable(R.drawable.type_light),false));
        mAdapter = new MyBaseAdapter<BaseItemBean>(mListItemLayout/*R.layout.my_xpopup_center_list_item*/, mContext, mDatas, false) {
            @Override
            public void bindViewHolder(final BaseViewHolder holder, final BaseItemBean itemBean, int pos) {
                holder.setImageView(R.id.my_list_item_logo, itemBean.Logo);
                holder.setTextView(R.id.my_list_item_key, itemBean.Key);
                holder.setTextView(R.id.my_list_item_value, itemBean.Value);
                holder.setVisibility(R.id.my_list_item_state, itemBean.ShowState ? VISIBLE : INVISIBLE);
                if(itemBean.ShowState){ holder.setViewSelected(R.id.my_list_item_state, itemBean.Selected); }
                holder.setClickListener(R.id.my_list_item_root, new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        itemBean.Selected = !itemBean.Selected;
                        if(mIsMultiselect){
                            mMultiSelectMap.put(itemBean.Id, itemBean.Selected);
                            if(itemBean.ShowState){ holder.setViewSelected(R.id.my_list_item_state, itemBean.Selected); }
                        }else {
                            if(mListener!=null) {  mListener.onItemClick((int)itemBean.Id); }
                            dismiss();
                        }
                    }
                });
            }
        };
        MyBaseDecoration itemDecoration = new MyBaseDecoration();
        //mDividers = new ArrayList<>();
        //itemDecoration.setDividerLookup(new MyConfigurableDividerLookup(mDividers));
        itemDecoration.setDividerLookup(new MySimpleDividerLookup(1, this.getResources().getColor(R.color.myDecoration), IsHaveLogo?ColorUtil.dp2px(mContext,35):0, 0));

        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private ArrayList<DividerBean> mDividers;
    private  void getDividers(ArrayList<BaseItemBean> datas) {
        if(datas == null || datas.size() == 0) { mDividers.clear();return; }
        int dividerColor = getResources().getColor(R.color.myDecoration);
        int itemSize = datas.size();
        for (int i = 0; i < itemSize; i++) {
            if (i == 1) {
                mDividers.add(new DividerBean(ColorUtil.dp2px(mContext,20), dividerColor, 0, 0));
            } else {
                mDividers.add(new DividerBean(ColorUtil.dp2px(mContext,1), dividerColor, ColorUtil.dp2px(mContext,20), 0));
            }
        }
    }

    private Listener mListener = null;
    public void setListener(Listener listener) { mListener = listener; }
    public interface Listener {
        void onViewClick(View view);
        void onItemClick(int pos);
        void onMultiSelConfirm(HashMap<Object, Boolean> Map);
    }
    public static class SimpleListener implements Listener{
        @Override
        public void onViewClick(View view) { }
        @Override
        public void onItemClick(int pos) { }
        @Override
        public void onMultiSelConfirm(HashMap<Object, Boolean> Map) { }
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.list_confirm) {
            if (mIsMultiselect && mListener != null) {
                mListener.onMultiSelConfirm(mMultiSelectMap);
            }
            dismiss();

        } else if (i == R.id.list_cancel) {
            dismiss();
        } else {
        }
    }
}
