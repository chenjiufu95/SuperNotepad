package pers.julio.notepad.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pers.julio.notepad.Activity.ExampleActivity;
import pers.julio.notepad.Constants;
import pers.julio.notepad.R;
import pers.julio.notepad.recyclerview.Base.BaseViewHolder;
import pers.julio.notepad.recyclerview.Base.MyBaseAdapter;
import pers.julio.notepad.recyclerview.Bean.BaseItemBean;
import pers.julio.notepad.recyclerview.Bean.DividerBean;
import pers.julio.notepad.recyclerview.Utils.ColorUtil;

/**
 * ClassName:  FunctionListFragment
 * Description: TODO
 * Author;  julio_chan  2020/4/11 9:10
 */
public class FunctionListFragment extends Fragment {
    private View rootView;
    private ExampleActivity parent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_functions, null);
        return rootView;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        parent = (ExampleActivity) getActivity();
        initRecyclerView();
    }

    private RecyclerView mRecyclerView;
    private ArrayList<BaseItemBean> mDatas;
    private MyBaseAdapter<BaseItemBean> mAdapter;
    private void initRecyclerView() {
        mRecyclerView = rootView.findViewById(R.id.rv_func_list);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(parent, 3);
        /*gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            public int getSpanSize(int position) { return gridLayoutManager.getSpanCount(); }
        });*/

        mDatas = new ArrayList<>();
        mDatas.add(BaseItemBean.builder(parent).setId(Constants.FUN_TABLE).setKey("表格").setLogo(R.drawable.icon_table).build());
        mDatas.add(BaseItemBean.builder(parent).setId(Constants.FUN_COMPONENT).setKey("组件集").setLogo(R.drawable.icon_component).build());
        mDatas.add(BaseItemBean.builder(parent).setId(Constants.FUN_POPUPVIEW).setKey("对话框").setLogo(R.drawable.icon_popup).build());
        mDatas.add(BaseItemBean.builder(parent).setId(Constants.FUN_NETWORK).setKey("网络通信").setLogo(R.drawable.icon_newwork).build());
        mDatas.add(BaseItemBean.builder(parent).setId(Constants.FUN_NFC).setKey("蓝牙串口").setLogo(R.drawable.icon_bt_uart).build());
        mDatas.add(BaseItemBean.builder(parent).setId(Constants.FUN_EVENT).setKey("事件消息").setLogo(R.drawable.icon_event).build());
        mDatas.add(BaseItemBean.builder(parent).setId(Constants.FUN_ACCESSIBILITY).setKey("辅助功能").setLogo(R.drawable.icon_accessibility).build());
        mDatas.add(BaseItemBean.builder(parent).setId(Constants.FUN_RENDER).setKey("渲染装饰").setLogo(R.drawable.icon_render).build());
        mDatas.add(BaseItemBean.builder(parent).setId(Constants.FUN_UTILS).setKey("工具集合").setLogo(R.drawable.icon_utils).build());

        mAdapter = new MyBaseAdapter<BaseItemBean>(R.layout.item_icon_key, parent, mDatas, false) {
            @Override
            public void bindViewHolder(BaseViewHolder holder, final BaseItemBean itemBean, int pos) {
                holder.setImageView(R.id.logo_key_logo, itemBean.Logo);
                holder.setTextView(R.id.logo_key_key, itemBean.Key);
                holder.setTextView(R.id.logo_key_value, itemBean.Value);
                holder.setClickListener(R.id.item_logo_key_root, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        parent.DoFragAction((int)itemBean.Id);
                    }
                });
            }
        };
        //MyBaseDecoration itemDecoration = new MyBaseDecoration();
        //mDividers = new ArrayList<>();
        //itemDecoration.setDividerLookup(new MyConfigurableDividerLookup(mDividers));
        //itemDecoration.setDividerLookup(new MySimpleDividerLookup(ColorUtil.dp2px(this,1), this.getResources().getColor(R.color.myDecoration), ColorUtil.dp2px(this,3), ColorUtil.dp2px(this,3)));

        //mRecyclerView.addItemDecoration(itemDecoration);
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
                mDividers.add(new DividerBean(ColorUtil.dp2px(parent,20), dividerColor, 0, 0));
            } else {
                mDividers.add(new DividerBean(ColorUtil.dp2px(parent,1), dividerColor, ColorUtil.dp2px(parent,20), 0));
            }
        }
    }

}
