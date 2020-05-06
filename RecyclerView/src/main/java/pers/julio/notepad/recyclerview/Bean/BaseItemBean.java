package pers.julio.notepad.recyclerview.Bean;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.StringRes;
import pers.julio.notepad.recyclerview.Utils.ColorUtil;

/**
 * ClassName:  BaseItemBean
 * Description:
 * Author;  julio_chan  2019/12/31 9:39
 */
public class BaseItemBean {
    public Object Id;
    public int Type;
    public String Text = null;
    public String Key = null;
    public String Value = null;
    public Drawable Logo = null;
    public Drawable State = null;
    public boolean ShowState = false;
    public boolean Selected = false;

    //BaseItemBean.builder().setId(index).setKey(key).setValue(value).setLogo(logo).setShowState(state).setSelected(isSelected).setShowState(true).build();
    public BaseItemBean(int type,Object id, String text, String key, String value, Drawable logo, Drawable state, boolean isSelected, boolean showState) {
        Type = type;
        Id = id;
        Text = text;
        Key = key;
        Value = value;
        Logo = logo;
        State = state;
        Selected = isSelected;
        ShowState = showState;
    }

    public static Builder builder(Context context) {
        return new Builder(context);
    }

    public static class Builder {
        private Context mContext;
        public Object mId;
        public int mType;
        public int mColor = -1;
        public String mText = null;
        public String mKey = null;
        public String mValue = null;
        public Drawable mLogo = null;
        public Drawable mState = null;
        public boolean mSelected = false;
        public boolean mShowState = false;

        public Builder(Context context) {
            mContext = context;
        }

        public final Builder setId(Object id) {
            mId = id;
            return this;
        }
        public final Builder setType(int type) {
            mType = type;
            return this;
        }
        public final Builder setColor(@ColorInt int colorTint) {
            mColor = colorTint;
            return this;
        }
        public final Builder setText(@StringRes int textRes) {
            mText = mContext.getResources().getString(textRes);
            return this;
        }
        public final Builder setText(String text) {
            mText = text;
            return this;
        }

        public final Builder setKey(@StringRes int keyRes) {
            mKey = mContext.getResources().getString(keyRes);
            return this;
        }
        public final Builder setKey(String key) {
            mKey = key;
            return this;
        }

        public final Builder setValue(@StringRes int valueRes) {
            mValue = mContext.getResources().getString(valueRes);
            return this;
        }
        public final Builder setValue(String value) {
            mValue = value;
            return this;
        }

        public final Builder setLogo(@DrawableRes int logoRes) {
            mLogo = mContext.getResources().getDrawable(logoRes);
            return this;
        }
        public final Builder setLogo(Drawable logo) {
            mLogo = logo;
            return this;
        }

        public final Builder setState(@DrawableRes int stateRes) {
            mState = mContext.getResources().getDrawable(stateRes);
            return this;
        }
        public final Builder setState(Drawable state) {
            mState = state;
            return this;
        }

        public final Builder setShowState(boolean showState) {
            mShowState = showState;
            return this;
        }

        public final Builder setSelected(boolean selected) {
            mSelected = selected;
            return this;
        }

        public final BaseItemBean build() {
            if (mColor != -1) { ColorUtil.DrawableTint(mLogo, mColor); }
            return new BaseItemBean(mType,mId,mText,mKey,mValue,mLogo,mState,mSelected,mShowState);
        }
    }
}
