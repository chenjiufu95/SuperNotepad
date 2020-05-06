package pers.julio.notepad.recyclerview.Base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @file FileName, Created by julio_chan on 2019/4/10.
 */
public abstract class MyBaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    protected Context mContext;
    protected int LayoutID;
    protected List<T> mDatas;
    protected boolean mUseDataAddress = false;

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public MyBaseAdapter(int layoutID, Context mContext , List<T> datas, boolean useDataAddress) {
        this.LayoutID = layoutID;
        this.mContext = mContext;
        mUseDataAddress = useDataAddress;
        if (mUseDataAddress){
            this.mDatas = datas;
        }else {
            this.mDatas = new ArrayList<>();
            if (datas!=null)
                mDatas.addAll(datas);
        }
    }

    public void  refreshDatas(List<T> datas) {
        if (datas==null || datas.size() == 0)  mDatas.clear();
        if (!mUseDataAddress && datas!=null && mDatas!=null) {
            mDatas.clear();
            mDatas.addAll(datas);
        }
        notifyDataSetChanged();
    }
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(LayoutID, parent , false);
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            bindViewHolder(holder, mDatas.get(position), position);
        }else {
            doItemRefresh(holder, mDatas.get(position), position);
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        bindViewHolder(holder, mDatas.get(position), position);
    }

    @Override
    public int getItemCount() {
        if (mDatas==null) return 0;
        return mDatas.size();
    }

    public List<T> getDatas(){ return mDatas;}
    public T getItemData(int pos){ return mDatas.get(pos);}

    public abstract void bindViewHolder(BaseViewHolder holder , T t, int pos);

    public void doItemRefresh(BaseViewHolder holder , T t , int pos){

    };

   /* public class BaseViewHolder extends RecyclerView.ViewHolder {
        private SparseArray<View> mViews;
        private View itemView;

        public BaseViewHolder(View itemView) {
            super(itemView);
            mViews = new SparseArray<View>();
            this.itemView = itemView;
            itemView.setTag(this);
        }

        public <T extends View> T getView(int ViewID) {
            View view = mViews.get(ViewID);
            if (view == null) {
                view = itemView.findViewById(ViewID);
                mViews.put(ViewID, view);
            }
            return (T) view;
        }

        // 设置item
        public BaseViewHolder setItemLongClickListener(View.OnLongClickListener listener) {
            if (itemView!=null) itemView.setOnLongClickListener(listener);
            return this;
        }
        public BaseViewHolder setItemClickListener(View.OnClickListener listener) {
            if (itemView!=null) itemView.setOnClickListener(listener);
            return this;
        }
        public BaseViewHolder setItemBackground(int color) {
            if (itemView!=null) itemView.setBackgroundResource(color);
            return this;
        }
        public Drawable getItemBackground() {
            if (itemView==null) return null;
            return itemView.getBackground();
        }

        // 设置TextVIEW
        public BaseViewHolder setTextView(int ViewID, String text) {
            TextView view = (TextView)getView(ViewID);
            if (view != null) view.setText(text);
            return this;
        }
        public BaseViewHolder setTextView(int ViewID, int textRes) {
            TextView view = (TextView)getView(ViewID);
            if (view != null) view.setText(textRes);
            return this;
        }
        public BaseViewHolder setTextViewColor(int ViewID, int color) {
            TextView view = (TextView)getView(ViewID);
            if (view != null) view.setTextColor(color);
            return this;
        }

        // 设置ImageVIEW
        public BaseViewHolder setImageViewBgResource(int ViewID, int imgRes) {
            ImageView view = (ImageView)getView(ViewID);
            if (view != null)  view.setBackgroundResource(imgRes);
            return this;
        }
        public Drawable getDrawableFromImageView(int ViewID) {
            ImageView view = (ImageView)getView(ViewID);
            if (view == null)  return null;
            Drawable image = view.getBackground();
            return image;
        }

        // 设置VIEW
        public BaseViewHolder setVisibility(int ViewID, int visibility) {
            View view = getView(ViewID);
            if (view != null)  view.setVisibility(visibility);
            return this;
        }
        public BaseViewHolder setViewPressed(int ViewID, boolean pressed) {
            View view = getView(ViewID);
            if (view != null)  view.setViewPressed(pressed);
            return this;
        }
        public BaseViewHolder setViewSelected(int ViewID,boolean selected) {
            View view = getView(ViewID);
            if (view != null)  view.setViewSelected(selected);
            return this;
        }
        public boolean isViewSelected(int ViewID) {
            View view = getView(ViewID);
            if (view == null) return false;
            return  view.isViewSelected();
        }

        public BaseViewHolder setClickListener(int ViewID, View.OnClickListener listener) {
            View view = getView(ViewID);
            if (view != null)  view.setOnClickListener(listener);
            return this;
        }
        public BaseViewHolder setLongClickListener(int ViewID, View.OnLongClickListener listener) {
            View view = getView(ViewID);
            if (view != null)  view.setOnLongClickListener(listener);
            return this;
        }
        public BaseViewHolder setViewBackground(int ViewID, int color) {
            View view = getView(ViewID);
            if (view != null)  view.setBackgroundResource(color);
            return this;
        }

        public BaseViewHolder setPowerState(int ViewID,int powerOn) {
            View view = getView(ViewID);
            if (view != null)  view.setViewSelected(powerOn ==0 ? false: true);
            return this;
        }
    }*/
}

