package pers.julio.notepad.recyclerview.Base;

import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.RecyclerView;

/**
 * ClassName:  BaseViewHolder
 * Description: TODO
 * Author;  julio_chan  2019/12/31 9:39
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;
    private View itemView;

    public BaseViewHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
        this.itemView = itemView;
        itemView.setTag(this);
    }

    public <T extends View> T getView(@IdRes int ViewID) {
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
    public BaseViewHolder setItemBackgroundResource(@DrawableRes int resid) {
        if (itemView!=null) itemView.setBackgroundResource(resid);
        return this;
    }
    public BaseViewHolder setItemBackgroundColor(@ColorInt int color) {
        if (itemView!=null) itemView.setBackgroundColor(color);
        return this;
    }
    public BaseViewHolder setItemBackground(Drawable drawable) {
        if (itemView!=null) itemView.setBackground(drawable);
        return this;
    }
    public  Drawable getItemBackground() {
        if (itemView==null) return null;
        return itemView.getBackground();
    }

    public BaseViewHolder setItemTouchEvent(View.OnTouchListener listener) {
        if (itemView!=null) itemView.setOnTouchListener(listener);
        return this;
    }

    // 设置TextVIEW
    public BaseViewHolder setTextView(@IdRes int ViewID, String text) {
        if (text == null) {
            setVisibility(ViewID, View.INVISIBLE);
        }else {
            setVisibility(ViewID, View.VISIBLE);
            TextView view = getView(ViewID);
            if (view != null) view.setText(text);
        }
        return this;
    }
    public BaseViewHolder setTextView(@IdRes int ViewID, @StringRes int textRes) {
        TextView view = getView(ViewID);
        if (view != null) view.setText(textRes);
        return this;
    }
    public BaseViewHolder setTextViewColor(@IdRes int ViewID, @ColorInt int color) {
        TextView view = getView(ViewID);
        if (view != null) view.setTextColor(color);
        return this;
    }

    // 设置ImageVIEW
    public BaseViewHolder setImageView(@IdRes int ViewID, Drawable drawable) {
        if (drawable == null) {
            setVisibility(ViewID, View.GONE);
        } else {
            setVisibility(ViewID, View.VISIBLE);
            ImageView view = getView(ViewID);
            if (view != null) view.setImageDrawable(drawable);
        }
        return this;
    }
    public BaseViewHolder setImageViewResource(@IdRes int ViewID, @DrawableRes int imgRes) {
        ImageView view = getView(ViewID);
        if (view != null)  view.setImageResource(imgRes);
        return this;
    }
    public BaseViewHolder setImageViewBgResource(@IdRes int ViewID, @DrawableRes int imgRes) {
        ImageView view = getView(ViewID);
        if (view != null)  view.setBackgroundResource(imgRes);
        return this;
    }

    public Drawable getDrawableFromImageView(@IdRes int ViewID) {
        ImageView view = getView(ViewID);
        if (view == null)  return null;
        return view.getBackground();
    }

    // 设置VIEW
    public BaseViewHolder setVisibility(@IdRes int ViewID, int visibility) {
        View view = getView(ViewID);
        if (view != null)  view.setVisibility(visibility);
        return this;
    }
    public BaseViewHolder setViewPressed(@IdRes int ViewID, boolean pressed) {
        View view = getView(ViewID);
        if (view != null)  view.setPressed(pressed);
        return this;
    }
    public BaseViewHolder setViewSelected(@IdRes int ViewID, boolean selected) {
        View view = getView(ViewID);
        if (view != null)  view.setSelected(selected);
        return this;
    }
    public boolean isViewSelected(@IdRes int ViewID) {
        View view = getView(ViewID);
        if (view == null) return false;
        return  view.isSelected();
    }

    public BaseViewHolder setTouchListener(@IdRes int ViewID, View.OnTouchListener listener) {
        View view = getView(ViewID);
        if (view != null)  view.setOnTouchListener(listener);
        return this;
    }
    public BaseViewHolder setClickListener(@IdRes int ViewID, View.OnClickListener listener) {
        View view = getView(ViewID);
        if (view != null)  view.setOnClickListener(listener);
        return this;
    }
    public BaseViewHolder setLongClickListener(@IdRes int ViewID, View.OnLongClickListener listener) {
        View view = getView(ViewID);
        if (view != null)  view.setOnLongClickListener(listener);
        return this;
    }

    public BaseViewHolder setViewBackgroundColor(@IdRes int ViewID, @ColorInt int color) {
        View view = getView(ViewID);
        if (view != null)  view.setBackgroundColor(color);
        return this;
    }
    public BaseViewHolder setViewBackground(@IdRes int ViewID, Drawable drawable) {
        if (drawable == null) {
            setVisibility(ViewID, View.INVISIBLE);
        } else {
            setVisibility(ViewID, View.VISIBLE);
            View view = getView(ViewID);
            if (view != null) view.setBackground(drawable);
        }
        return this;
    }
    public BaseViewHolder setViewBackgroundResource(@IdRes int ViewID, int drawableId) {
        View view = getView(ViewID);
        if (view != null)  view.setBackgroundResource(drawableId);
        return this;
    }

    public BaseViewHolder setPowerState(@IdRes int ViewID,boolean powerOn) {
        View view = getView(ViewID);
        if (view != null)  view.setSelected(powerOn);
        return this;
    }
}
