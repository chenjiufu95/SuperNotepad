package pers.julio.notepad.xpopup.MyPopup;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.impl.FullScreenPopupView;

import java.util.ArrayList;
import java.util.HashMap;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
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
 * ClassName:  MyFullScreenListView
 * Description: TODO
 * Author;  julio_chan  2020/4/27 13:55
 */
public class MyFullScreenListView extends FullScreenPopupView implements View.OnClickListener {
    protected Context mContext;
    protected TextView tv_title;
    protected String mTitle;
    private int curIndex = -1;  // 单选状态 记录当前选择的条目编号  默认 为都没选中
    private boolean mIsMultiselect = false;

    // 以下对多选时 保存的数据
    private ArrayList<BaseItemBean> mNewAddList;    //  针对 已选项 外 的条目 标记为新增状态；
    private ArrayList<BaseItemBean> mNewRemoveList; //  针对 已选项 中 的条目 标记为移除状态；

    private ArrayList<BaseItemBean> mInitSelList = new ArrayList<>();   // 加载前 已选择的条目
    private HashMap<Object,Boolean> mMultiSelectMap = new HashMap<>();

    public HashMap<Object,Boolean> getMultiSelectMap(){ return mMultiSelectMap; }
    public ArrayList<BaseItemBean> getNewAddList() { return mNewAddList; }
    public ArrayList<BaseItemBean> getNewRemoveList() { return mNewRemoveList; }

    public void setTitle(String title){
        mTitle = title;
        if(tv_title!=null) tv_title.setText(mTitle);
    }

    public void setDatas(ArrayList<BaseItemBean> datas, boolean isMultiselect){
        if(mDatas == null) mDatas = new ArrayList<>();
        mDatas.clear();
        mDatas.addAll(datas);
        mIsMultiselect = isMultiselect;
        if (mIsMultiselect) {
            buildMultiSelectMap();
        }
        refreshDatas(mDatas);
    }

    public MyFullScreenListView(@NonNull Context context) {
        super(context);
        mContext = context;
    }
    public MyFullScreenListView(@NonNull Context context, ArrayList<BaseItemBean> datas, boolean isMultiselect) {
        super(context);
        mContext = context;
        mDatas = datas;
        mIsMultiselect = isMultiselect;
        if (mIsMultiselect) {
            buildMultiSelectMap();
        }
    }
    public MyFullScreenListView(@NonNull Context context, ArrayList<BaseItemBean> datas, boolean isMultiselect, Listener listener) {
        super(context);
        mContext = context;
        mDatas = datas;
        mListener = listener;
        mIsMultiselect = isMultiselect;
        if (mIsMultiselect) {
            buildMultiSelectMap();
        }
    }

    public void buildMultiSelectMap() {
        if (mDatas != null && mDatas.size() > 0) {
            mMultiSelectMap.clear();
            int size = mDatas.size();
            for (int i = 0; i < size; i++) {
                BaseItemBean itemBean = mDatas.get(i);
                if(itemBean!=null) {
                    mMultiSelectMap.put(itemBean.Id, itemBean.Selected);
                    if (itemBean.Selected) { mInitSelList.add(itemBean); }
                }
            }
            mNewAddList = new ArrayList<>();
            mNewRemoveList = new ArrayList<>();
        }
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        initTopbar(true);
        initRecyclerView();
    }

    public void initTopbar(boolean isWhiteTheme) {        // 白色主题 & 有色主题
        //MyStatusBarUtil.setLightStatusBar(mContext, isWhiteTheme);   // 使状态栏 的文字可见
        //findViewById(R.id.topbar_root).setBackgroundColor(Color.TRANSPARENT); // 沉浸式 TopBar 透明 由背景决定显示
        tv_title = findViewById(R.id.topbar_title);
        tv_title.setText(mTitle);
        tv_title.setTextColor(isWhiteTheme ? Color.BLACK : Color.WHITE); // 白色主题用黑色字体，有色主题，用白色字体

        ImageView back = findViewById(R.id.topbar_left);
        back.setImageResource(isWhiteTheme ? R.drawable.topbar_back_black : R.drawable.topbar_back_white);
        back.setOnClickListener(this);
        TextView SaveText = findViewById(R.id.topbar_right_text);
        SaveText.setTextColor(isWhiteTheme ? Color.BLACK: Color.WHITE);
        SaveText.setOnClickListener(this);
        SaveText.setVisibility(VISIBLE);
        SaveText.setText(mContext.getString(R.string.save));

        ImageView SaveBtn = findViewById(R.id.topbar_right);
        SaveBtn.setImageResource(isWhiteTheme ? R.drawable.ic_selected : R.drawable.ic_unselected);
        SaveBtn.setOnClickListener(this);
        SaveBtn.setVisibility(INVISIBLE);
    }


    @Override
    protected int getImplLayoutId() { return mPopupLayout; }

    /** 设置PopupView 的布局样式，
     * @param popupLayout 注意：请在 默认布局基础上修改(id 不变)  默认布局：R.layout.my_xpopup_full_screen_list
     */
    protected int mPopupLayout = R.layout.my_xpopup_full_screen_list;
    public void setPopupLayout(int popupLayout) {  mPopupLayout = popupLayout; }

    /** 设置列表Item 的布局样式，
     * @param itemLayout 注意：请在 默认布局基础上修改(id 不变) 默认布局：R.layout.base_item
     */
    protected int mListItemLayout = R.layout.base_item;
    public void setListItemLayout(int itemLayout) { mListItemLayout = itemLayout; }

    /** 设置 分割线 及是否显示
     * @param decoration
     * @param showDecoration
     */
    private boolean mShowDecoration = true;
    private MyBaseDecoration mBaseDecoration;
    public void setItemDecoration(MyBaseDecoration decoration, boolean showDecoration) {
        mShowDecoration = showDecoration;
        mBaseDecoration = decoration;
    }

    /** 设置 布局管理器 分配规则
     * @param span            指定 列数
     * @param spanSizeLookup  指定 Span 分配规则
     */
    private GridLayoutManager mGridLayoutManager;
    public void setSpanAndLookup(int span, GridLayoutManager.SpanSizeLookup spanSizeLookup){
        if(mGridLayoutManager == null)  {
            mGridLayoutManager = new GridLayoutManager(mContext, span);
        }else {
            mGridLayoutManager.setSpanCount(span);
        }
        mGridLayoutManager.setSpanSizeLookup(spanSizeLookup);
    }
    private boolean SetGridLayoutManager(int span, GridLayoutManager.SpanSizeLookup spanSizeLookup) {
        if(spanSizeLookup == null){
            spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
                public int getSpanSize(int position) { return mGridLayoutManager.getSpanCount(); }
            };
        }
        setSpanAndLookup(span, spanSizeLookup);
        if(mRecyclerView == null){ mRecyclerView.setLayoutManager(mGridLayoutManager); }
        return mRecyclerView != null;
    }

    /** 数据 刷新*/
    public void refreshDatas(ArrayList<BaseItemBean> datas) {
        if (mDividers != null) { getDividers(datas); }
        if(mAdapter!=null){ mAdapter.refreshDatas(datas);}
    }
    private RecyclerView mRecyclerView;
    private MyBaseAdapter<BaseItemBean> mAdapter;
    private ArrayList<BaseItemBean> mDatas = new ArrayList<>();

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.my_xpopup_full_screen_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        //mDatas = new ArrayList<>();
        //mDatas.add(BaseItemBean.builder(mContext).setId(ID_ITEM_ONE).setText("标题1").setKey("副标题1").setValue("描述1").setLogo(R.drawable.icon_table).build());
        //mDatas.add(BaseItemBean.builder(mContext).setId(ID_ITEM_TWO).setText("标题2").setKey("副标题2").setValue("描述2").build());
        mAdapter = new MyBaseAdapter<BaseItemBean>(mListItemLayout/*R.layout.base_item*/, mContext, mDatas, false) {
            @Override
            public void bindViewHolder(final BaseViewHolder holder, final BaseItemBean itemBean, final int pos) {
                holder.setImageView(R.id.base_item_logo, itemBean.Logo);
                holder.setTextView(R.id.base_item_text, itemBean.Text);
                holder.setTextView(R.id.base_item_key, itemBean.Key);
                holder.setTextView(R.id.base_item_value, itemBean.Value);
                holder.setVisibility(R.id.base_item_state, itemBean.ShowState ? View.VISIBLE : View.INVISIBLE);
                if(itemBean.ShowState){ holder.setViewSelected(R.id.base_item_state, itemBean.Selected); }
                holder.setClickListener(R.id.base_item_root, new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(mIsMultiselect){
                            itemBean.Selected = !itemBean.Selected;
                            if(itemBean.ShowState){ holder.setViewSelected(R.id.base_item_state, itemBean.Selected); }

                            // 如果处于多选状态，则 通常需要 记录 增加/和删除的条目，以便最后 统一处理；
                            mMultiSelectMap.put(itemBean.Id, itemBean.Selected);
                            if(itemBean.Selected){   // 状态: 未选择-->选择
                                if(mInitSelList.contains(itemBean)) {  // 移除 存在于 mNewRemoveList 中的标记
                                    mNewRemoveList.remove(itemBean);
                                }else {
                                    mNewAddList.add(itemBean);  // 增加到 NewAddList 中，新增 选中状态
                                }
                            }else { // 状态: 选择-->未选择
                                if(mInitSelList.contains(itemBean)){ // 增加到 mNewRemoveList 中的标记
                                    mNewRemoveList.add(itemBean);
                                }else {
                                    mNewAddList.remove(itemBean);  // 移除 存在于 mNewAddList 中的标记
                                }
                            }
                        }else {   // 如果处于单选状态，则 要作互斥处理；
                            if (curIndex != pos) {
                                // 取消 上次条目
                                if (curIndex != -1 && curIndex < getDatas().size()) {
                                    BaseItemBean lastItemBean = getItemData(curIndex);
                                    lastItemBean.Selected = false;

                                    BaseViewHolder lastHolder = (BaseViewHolder) mRecyclerView.findViewHolderForAdapterPosition(curIndex);
                                    if (lastHolder != null && itemBean.ShowState) {
                                        lastHolder.setViewSelected(R.id.base_item_state, false);
                                    }
                                }
                                // 更改选中 当前条目
                                itemBean.Selected = true;
                                if (itemBean.ShowState) {
                                    holder.setViewSelected(R.id.base_item_state, true);
                                }
                                curIndex = pos;
                            }
                        }
                        if(mListener!=null) {  mListener.onItemClick(itemBean.Id, itemBean.Selected, mIsMultiselect); }
                    }
                });
            }
        };
        mRecyclerView.setAdapter(mAdapter);

        if(!mShowDecoration) return;
        int pedding = ColorUtil.dp2px(mContext,5);
        if (mBaseDecoration == null) {
            mBaseDecoration = new MyBaseDecoration();
            mBaseDecoration.setDividerLookup(new MySimpleDividerLookup(1, this.getResources().getColor(R.color.myDecoration_light), pedding, pedding));
            //mDividers = new ArrayList<>();
            //itemDecoration.setDividerLookup(new MyConfigurableDividerLookup(getDividers(datas)));
        }
        mRecyclerView.addItemDecoration(mBaseDecoration);
    }

    private ArrayList<DividerBean> mDividers;
    private  void getDividers(ArrayList<BaseItemBean> datas) {
        if(datas == null || datas.size() == 0) { mDividers.clear();return; }
        int dividerColor = getResources().getColor(R.color.myDecoration);
        int itemSize = datas.size();
        //if(mDividers ==null) { mDividers = new ArrayList<>();}
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
        void onItemClick(Object id, boolean select, boolean isMultiselect);
        void onConfirm(View view, boolean flag, boolean isMultiselect);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.topbar_left) {
            dismiss();
            if (mListener != null) { mListener.onConfirm(view, false, mIsMultiselect); }
        } else if (i == R.id.topbar_right_text || i == R.id.topbar_right) {
            showSettingPopup();
            if (mListener != null) { mListener.onConfirm(view, true, mIsMultiselect); }
        }
    }

    private BasePopupView mSettingPopup = null;
    public void showSettingPopup() {
        if(mSettingPopup!=null) return;
        mSettingPopup = new XPopup.Builder(mContext)
                .dismissOnTouchOutside(false) // 点击外部是否关闭弹窗，默认为true
                //.dismissOnBackPressed(false) // 按返回键是否关闭弹窗，默认为true
                .asLoading(mContext.getResources().getString(R.string.setting))
                .bindLayout(R.layout.my_xpopup_loading_vertical_black)
                .show();
        mSettingPopup.postDelayed(mTimeOutTask,5*1000);
    }
    private Runnable mTimeOutTask = new Runnable() {
        @Override
        public void run() {
            finishPopupView();
        }
    };
    public void finishPopupView() {
        if(mSettingPopup!=null){
            mSettingPopup.removeCallbacks(mTimeOutTask);
            mSettingPopup.dismiss();
            mSettingPopup = null;
        }
        dismiss();
    }
}
