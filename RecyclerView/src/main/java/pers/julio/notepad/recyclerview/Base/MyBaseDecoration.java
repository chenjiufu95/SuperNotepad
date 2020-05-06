package pers.julio.notepad.recyclerview.Base;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.choices.divider.Divider;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @file FileName, Created by julio_chan on 2019/11/9.
 */
public class MyBaseDecoration extends RecyclerView.ItemDecoration {
    private Paint mPaint;
    private boolean mIgnoreLast = true;
    private DividerLookup dividerLookup;

    public MyBaseDecoration() {
        mPaint = new Paint();
        mIgnoreLast = true;
        dividerLookup = new DefaultDividerLookup();
    }
    public MyBaseDecoration(boolean ignoreLast) {
        mPaint = new Paint();
        mIgnoreLast = ignoreLast;
        dividerLookup = new DefaultDividerLookup();
    }
    public void setDividerLookup(DividerLookup dividerLookup) {
        this.dividerLookup = dividerLookup;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(child);
            Divider rightDivider = dividerLookup.getVerticalDivider(position);
            Divider bottomDivider = dividerLookup.getHorizontalDivider(position);
            if(mIgnoreLast && i == childCount-1) { return; }
            if (rightDivider != null) drawRightDivider(c, child, rightDivider);
            if (bottomDivider != null) drawBottomDivider(c, child, bottomDivider);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int itemCount = state.getItemCount();
        int position = parent.getChildAdapterPosition(view);
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            int spanCount = gridLayoutManager.getSpanCount();
            GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
            int spanIndex = spanSizeLookup.getSpanIndex(position, spanCount);
            int spanGroupIndex = spanSizeLookup.getSpanGroupIndex(position, spanCount);
            int lastSpanGroupIndex = spanSizeLookup.getSpanGroupIndex(itemCount - 1, spanCount);
            Divider vd = dividerLookup.getVerticalDivider(position);
            Divider hd = dividerLookup.getHorizontalDivider(position);
            outRect.right = (vd == null) ? 0 : vd.size;
            if (mIgnoreLast && spanIndex == spanCount-1) { outRect.right = 0; }

            outRect.bottom = (hd == null) ? 0 : hd.size;
            if (mIgnoreLast && lastSpanGroupIndex == spanGroupIndex) { outRect.bottom = 0; }

            return;
        } else if (layoutManager instanceof LinearLayoutManager) {
            Divider hd = dividerLookup.getHorizontalDivider(position);
            outRect.bottom = (hd == null) ? 0 : hd.size;
            if (mIgnoreLast && position == itemCount-1) {
                outRect.bottom = 0;
            }
            return;
        }
        throw new IllegalArgumentException("It is not currently supported StaggeredGridLayoutManager");
    }

    private void drawBottomDivider(Canvas c, View child, Divider bd) {
        if(bd.size <= 0) return;
        mPaint.setColor(bd.color);
        c.drawRect(child.getLeft() + bd.marginStart, child.getBottom(),
                child.getRight() - bd.marginEnd, child.getBottom() + bd.size,
                mPaint);
    }

    private void drawRightDivider(Canvas c, View child, Divider rd) {
        if(rd.size <= 0) return;
        mPaint.setColor(rd.color);
        c.drawRect(child.getRight(), child.getTop() + rd.marginStart,
                child.getRight() + rd.size, child.getBottom() - rd.marginEnd,
                mPaint);
    }


    private class DefaultDividerLookup implements DividerLookup {

        private Divider mDivider;

        public DefaultDividerLookup() {
            mDivider = new Divider.Builder().color(Color.GRAY).size(2).build();
        }

        @Override
        public Divider getVerticalDivider(int position) {
            return mDivider;
        }

        @Override
        public Divider getHorizontalDivider(int position) {
            return mDivider;
        }
    }

    public interface DividerLookup {
        Divider getVerticalDivider(int position);

        Divider getHorizontalDivider(int position);
    }

    public static class SimpleDividerLookup implements DividerLookup {

        @Override
        public Divider getVerticalDivider(int position) {
            return null;
        }

        @Override
        public Divider getHorizontalDivider(int position) {
            return null;
        }
    }

}

