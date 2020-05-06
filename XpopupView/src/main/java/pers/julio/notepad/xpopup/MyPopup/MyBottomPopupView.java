package pers.julio.notepad.xpopup.MyPopup;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.lxj.xpopup.core.BottomPopupView;

import androidx.annotation.NonNull;
import pers.julio.notepad.xpopup.Component.FocusTextView;
import pers.julio.notepad.xpopup.R;

/**
 * ClassName:  MyConfirmBottomPopupView
 * Description: TODO
 * Author;  julio_chan  2020/4/13 11:46
 */
public class MyBottomPopupView extends BottomPopupView implements View.OnClickListener {
    private FocusTextView tv_title;
    private TextView tv_content, tv_cancel, tv_confirm;
    private String title, content, hint, cancelText, confirmText;
    private boolean isHideCancel = false;

    public MyBottomPopupView(@NonNull Context context,String title, String content) {
        super(context);
        this.title = title;
        this.content = content;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.my_xpopup_bottom_popupview;
    }

    @Override
    protected void onCreate() {
        tv_title = findViewById(R.id.tv_title);
        tv_content = findViewById(R.id.tv_content);
        tv_cancel = findViewById(R.id.tv_cancel);
        tv_confirm = findViewById(R.id.tv_confirm);

        tv_cancel.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);

        if (!TextUtils.isEmpty(title)) {
            tv_title.setText(title);
        } else {
            tv_title.setVisibility(GONE);
        }

        if (!TextUtils.isEmpty(content)) {
            tv_content.setText(content);
        }else {
            tv_content.setVisibility(GONE);
        }
        if (!TextUtils.isEmpty(cancelText)) {
            tv_cancel.setText(cancelText);
        }
        if (!TextUtils.isEmpty(confirmText)) {
            tv_confirm.setText(confirmText);
        }
        if (isHideCancel) tv_cancel.setVisibility(GONE);
    }

    @Override
    public void onClick(View view) {
        dismiss();
        if (view.getId() == R.id.tv_confirm) {
            if(mListener!=null) mListener.onConfrim(true);
            return;
        } else if (view.getId() == R.id.tv_cancel) {
            if(mListener!=null) mListener.onConfrim(false);
            return;
        }
        if(mListener!=null) mListener.onViewClick(view);
    }

    private Listener mListener = null;
    public void setListener(Listener listener){mListener = listener;}
    public interface Listener {
        void onViewClick(View view);
        void onConfrim(boolean flag);
    }
}
