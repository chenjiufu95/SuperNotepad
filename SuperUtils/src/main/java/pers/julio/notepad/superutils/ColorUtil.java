package pers.julio.notepad.superutils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.DrawableCompat;

/**
 * @file FileName, Created by julio_chan on 2019/8/16.
 */
public class ColorUtil {

    public static int dp2px(Context context,int dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }
    public static int sp2px(Context context,int spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.getResources().getDisplayMetrics());
    }

    @NonNull
    private static Drawable getCanTintDrawable(@NonNull Drawable drawable) {
        // 获取此drawable的共享状态实例
        Drawable.ConstantState state = drawable.getConstantState();
        // 对drawable 进行重新实例化、包装、可变操作
        return DrawableCompat.wrap(state == null ? drawable : state.newDrawable()).mutate();
    }

    public static Drawable DrawableTint(Context context,int drawResId, int color) {
        Drawable wrappedDrawable = getCanTintDrawable(context.getResources().getDrawable(drawResId));
        DrawableCompat.setTint(wrappedDrawable, color);
        return wrappedDrawable;
    }
    public static Drawable DrawableTint(@NonNull Drawable drawable, int color) {
        //return DrawableTintList(drawable, ColorStateList.valueOf(color));
        Drawable wrappedDrawable = getCanTintDrawable(drawable); //DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(wrappedDrawable, color);
        return wrappedDrawable;
    }

    public static Drawable DrawableTintList(@NonNull Drawable drawable, ColorStateList colors) {
        Drawable wrappedDrawable = getCanTintDrawable(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }

    public static void setImageViewBgColor(ImageView imageView, int colorResId) {
        if(imageView.getBackground() == null)  return;
        Drawable modeDrawable = imageView.getBackground().mutate();
        Drawable temp = DrawableTint(modeDrawable,colorResId);
        //ColorStateList colorStateList = ColorStateList.valueOf(view.getResources().getColor(colorResId));
        //DrawableCompat.setTintList(temp, colorStateList);
        imageView.setBackground(temp);
        //imageView.setImageTintList( ColorStateList.valueOf(colorResId));
    }
    public static void setImageViewColor(ImageView imageView, int colorResId) {
        if(imageView.getDrawable() == null)  return;
        Drawable modeDrawable = imageView.getDrawable().mutate();
        Drawable temp = DrawableTint(modeDrawable,colorResId);
        //ColorStateList colorStateList = ColorStateList.valueOf(view.getResources().getColor(colorResId));
        //DrawableCompat.setTintList(temp, colorStateList);
        imageView.setImageDrawable(temp);
        //imageView.setImageTintList( ColorStateList.valueOf(colorResId));
    }

    public static void setTextViewColor(Context context,TextView textView, int colorResId, int width_dp, int height_dp, int padding_dp) {
        int half_width = dp2px(context,width_dp)/2;
        int half_height= dp2px(context,height_dp)/2;
        textView.setTextColor(colorResId);
        Drawable[] drawables = textView.getCompoundDrawables();
        if(drawables == null) return;
        Drawable[] drawableTint = new  Drawable[drawables.length];
        for (int i = 0; i < drawables.length; i++) {
            drawableTint[i] = (drawables[i] == null) ? null : DrawableTint(drawables[i].mutate(), colorResId);
            if(drawableTint[i]!=null){
                Rect bound = drawableTint[i].getBounds();
                drawableTint[i].setBounds(bound.centerX()-half_width, bound.centerY()-half_height, bound.centerX()+half_width,bound.centerY()+half_height);
            }
        }
        textView.setCompoundDrawablePadding(dp2px(context,padding_dp));
        textView.setCompoundDrawables(drawableTint[0],drawableTint[1],drawableTint[2],drawableTint[3]);
    }

    public static void setTextViewColor(TextView textView, int colorResId) {
        textView.setTextColor(colorResId);
        Drawable[] drawables = textView.getCompoundDrawables();
        Drawable[] drawableTint = new  Drawable[drawables.length];
        if(drawables == null) return;
        for (int i = 0; i < drawables.length; i++) {
            drawableTint[i] = (drawables[i] == null) ? null : DrawableTint(drawables[i].mutate(), colorResId);
        }
        textView.setCompoundDrawablesWithIntrinsicBounds(drawableTint[0],drawableTint[1],drawableTint[2],drawableTint[3]);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static int setColorAlpha(Context context, @ColorRes int colorRes, int alpha){
        int color = context.getResources().getColor(colorRes);
        return setColorAlpha(color,alpha);
    }
    public static int setColorAlpha(int color, int alpha){
        return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
    }
}
