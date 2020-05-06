package pers.julio.notepad.xpopup.MyPopup;

import android.content.Context;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;

import androidx.annotation.NonNull;
import pers.julio.notepad.xpopup.R;

/**
 * ClassName:  MyInputBoxPopupView
 * Description: TODO
 * Author;  julio_chan  2020/3/31 19:01
 */
public class MyInputCenterPopupView extends CenterPopupView implements View.OnClickListener {
    private Context mContext;
    private String mTitle, mTextHint, mText;
    private TextInputLayout til_inputBox;
    private AutoCompleteTextView et_inputBox;
    private OnInputConfirmListener mConfirmListener = null;
    public void setInputConfirmListener(OnInputConfirmListener listener) { mConfirmListener = listener; }


    public MyInputCenterPopupView(@NonNull Context context, String title, String text, String textHint) {
        super(context);
        mContext = context;
        mTitle = title;
        mText = text;
        mTextHint = textHint;
    }
    public MyInputCenterPopupView(@NonNull Context context, String title, String text, String hint, OnInputConfirmListener listener) {
        super(context);
        mContext = context;
        mTitle = title;
        mText = text;
        mTextHint = hint;
        mConfirmListener = listener;
    }

    @Override
    protected int getImplLayoutId() { return R.layout.my_xpopup_center_input; }

    @Override
    protected void onCreate() {
        super.onCreate();
        ((TextView)findViewById(R.id.input_title)).setText(mTitle);
        findViewById(R.id.input_confirm).setOnClickListener(this);
        findViewById(R.id.input_cancel).setOnClickListener(this);
        til_inputBox = findViewById(R.id.input_layout);
        et_inputBox = findViewById(R.id.input_input);
        if(mTextHint!=null){
            til_inputBox.setHint("".equals(mTextHint) ? "请输入" : mTextHint);
        }
        if(mText!=null){
            et_inputBox.setText(mText);
            et_inputBox.setSelection(mText.length());
        }
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.input_confirm) {
            if (checkForm()) {
                if (mConfirmListener != null) mConfirmListener.onConfirm(et_inputBox.getText().toString().trim());
                dismiss();
            }
        } else if (i == R.id.input_cancel) {
            dismiss();
        } else {
        }
    }
    public boolean checkForm(){
        boolean isPass = true;
        final String inputbox = et_inputBox.getText().toString().trim();
        if (inputbox.isEmpty()) {
            til_inputBox.setError("输入为空");
            isPass = false;
        }else if(inputbox.equals(mText)) {
            til_inputBox.setError("默认数据没变化");
            isPass = false;
        } else {
            til_inputBox.setError(null);
        }
        return isPass;
    }
}
