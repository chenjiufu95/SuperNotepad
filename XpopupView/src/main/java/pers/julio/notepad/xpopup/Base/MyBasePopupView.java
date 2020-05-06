package pers.julio.notepad.xpopup.Base;

import android.content.Context;
import android.view.View;
import android.widget.TextView;


import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.core.CenterPopupView;

import androidx.annotation.NonNull;
import pers.julio.notepad.xpopup.R;

/**
 * ClassName:  MyInputBoxPopupView
 * Description: TODO
 * Author;  julio_chan  2020/4/10 19:01
 */
public class MyBasePopupView extends CenterPopupView implements View.OnClickListener {
    private Context mContext;

    private Listener mListener = null;
    public void setListener(Listener listener){mListener = listener;}
    public interface Listener {
        boolean initView(View rootview);
        void onViewClick(View view, Object... args);
    }

    public MyBasePopupView(@NonNull Context context) {
        super(context);
        mContext = context;
    }
    public MyBasePopupView(@NonNull Context context,Listener listener) {
        super(context);
        mContext = context;
        mListener = listener;
    }

    @Override
    protected int getImplLayoutId() { return bindLayoutId != 0 ? bindLayoutId : R.layout.my_xpopup_center_input; }

    /** 更换布局
     * @return
     */
    public MyBasePopupView bindLayout(int layoutId){
        bindLayoutId = layoutId;
        return this;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        boolean useDefaultView = true;
        if (bindLayoutId != 0 && mListener != null) {
            useDefaultView = mListener.initView(this);
        }
        if(useDefaultView) { initDefaultView(); }
    }

    private void initDefaultView() {
        /*TextView title = findViewById(R.id.input_title);
        if(title!=null)  title.setText(mTitle);
        TextView confirm = findViewById(R.id.input_confirm);
        if(confirm!=null)  confirm.setOnClickListener(this);
        TextView cancel = findViewById(R.id.input_cancel);
        if(cancel!=null)  cancel.setOnClickListener(this);

        til_inputBox = findViewById(R.id.input_layout);
        et_inputBox = findViewById(R.id.input_input);
        if(mTextHint!=null && til_inputBox!=null){
            til_inputBox.setHint("".equals(mTextHint) ? "请输入" : mTextHint);
        }
        if(mText!=null && et_inputBox!=null){
            et_inputBox.setText(mText);
            et_inputBox.setSelection(mText.length());
        }*/
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.input_confirm) {
            if (checkForm()) {
                //if (mListener != null)  mListener.onConfirm(et_inputBox.getText().toString().trim());
                dismiss();
            }
        } else if (i == R.id.input_cancel) {
            dismiss();
        } else {
        }
    }
    public boolean checkForm(){
        boolean isPass = true;
       /* final String inputbox = et_inputBox.getText().toString().trim();
        if (inputbox.isEmpty()) {
            til_inputBox.setError("输入为空");
            isPass = false;
        }else if(inputbox.equals(mText)) {
            til_inputBox.setError("默认数据没变化");
            isPass = false;
        } else {
            til_inputBox.setError(null);
        }*/
        return isPass;
    }
}
