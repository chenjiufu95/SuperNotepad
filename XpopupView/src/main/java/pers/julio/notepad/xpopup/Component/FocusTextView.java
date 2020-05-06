package pers.julio.notepad.xpopup.Component;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * ClassName:  FocusTextView
 * Description: TODO
 * Author;  julio_chan  2020/3/27 10:55
 */
@SuppressLint("AppCompatCustomView")
public class FocusTextView extends TextView {
    public FocusTextView(Context context) { super(context); }
    public FocusTextView(Context context, @Nullable AttributeSet attrs) { super(context, attrs); }
    public FocusTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
