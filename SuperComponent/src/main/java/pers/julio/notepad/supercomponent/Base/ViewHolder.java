package pers.julio.notepad.supercomponent.Base;

import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.StringRes;

/**
 * ClassName:  ViewHolder
 * Description: TODO
 * Author;  julio_chan  2020/4/14 13:57
 */
public class ViewHolder {
    private SparseArray<View> mViews;
    private View itemView;

    public ViewHolder(View itemView) {
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

    // 设置TextVIEW
    public ViewHolder setTextView(@IdRes int ViewID, String text) {
        if (text == null) {
            setVisibility(ViewID, View.INVISIBLE);
        }else {
            setVisibility(ViewID, View.VISIBLE);
            TextView view = getView(ViewID);
            if (view != null) view.setText(text);
        }
        return this;
    }
    public ViewHolder setTextView(@IdRes int ViewID, @StringRes int textRes) {
        TextView view = getView(ViewID);
        if (view != null) view.setText(textRes);
        return this;
    }
    public ViewHolder setTextViewColor(@IdRes int ViewID, @ColorInt int color) {
        TextView view = getView(ViewID);
        if (view != null) view.setTextColor(color);
        return this;
    }

    // 设置ImageVIEW
    public ViewHolder setImageView(@IdRes int ViewID, Drawable drawable) {
        if (drawable == null) {
            setVisibility(ViewID, View.GONE);
        } else {
            setVisibility(ViewID, View.VISIBLE);
            ImageView view = getView(ViewID);
            if (view != null) view.setImageDrawable(drawable);
        }
        return this;
    }
    public ViewHolder setImageViewResource(@IdRes int ViewID, @DrawableRes int imgRes) {
        ImageView view = getView(ViewID);
        if (view != null)  view.setImageResource(imgRes);
        return this;
    }
    public ViewHolder setImageViewBgResource(@IdRes int ViewID, @DrawableRes int imgRes) {
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
    public ViewHolder setVisibility(@IdRes int ViewID, int visibility) {
        View view = getView(ViewID);
        if (view != null)  view.setVisibility(visibility);
        return this;
    }
    public ViewHolder setViewPressed(@IdRes int ViewID, boolean pressed) {
        View view = getView(ViewID);
        if (view != null)  view.setPressed(pressed);
        return this;
    }
    public ViewHolder setViewSelected(@IdRes int ViewID, boolean selected) {
        View view = getView(ViewID);
        if (view != null)  view.setSelected(selected);
        return this;
    }
    public boolean isViewSelected(@IdRes int ViewID) {
        View view = getView(ViewID);
        if (view == null) return false;
        return  view.isSelected();
    }

    public ViewHolder setTouchListener(@IdRes int ViewID, View.OnTouchListener listener) {
        View view = getView(ViewID);
        if (view != null)  view.setOnTouchListener(listener);
        return this;
    }
    public ViewHolder setClickListener(@IdRes int ViewID, View.OnClickListener listener) {
        View view = getView(ViewID);
        if (view != null)  view.setOnClickListener(listener);
        return this;
    }
    public ViewHolder setLongClickListener(@IdRes int ViewID, View.OnLongClickListener listener) {
        View view = getView(ViewID);
        if (view != null)  view.setOnLongClickListener(listener);
        return this;
    }

    public ViewHolder setViewBackgroundColor(@IdRes int ViewID, @ColorInt int color) {
        View view = getView(ViewID);
        if (view != null)  view.setBackgroundColor(color);
        return this;
    }
    public ViewHolder setViewBackground(@IdRes int ViewID, Drawable drawable) {
        if (drawable == null) {
            setVisibility(ViewID, View.INVISIBLE);
        } else {
            setVisibility(ViewID, View.VISIBLE);
            View view = getView(ViewID);
            if (view != null) view.setBackground(drawable);
        }
        return this;
    }
    public ViewHolder setViewBackgroundResource(@IdRes int ViewID, int drawableId) {
        View view = getView(ViewID);
        if (view != null)  view.setBackgroundResource(drawableId);
        return this;
    }

    public ViewHolder setPowerState(@IdRes int ViewID, boolean powerOn) {
        View view = getView(ViewID);
        if (view != null)  view.setSelected(powerOn);
        return this;
    }
}
