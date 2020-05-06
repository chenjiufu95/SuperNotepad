package pers.julio.notepad.xpopup.MyPopup;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.lxj.xpopup.core.CenterPopupView;

import androidx.annotation.NonNull;
import pers.julio.notepad.xpopup.R;

/**
 * ClassName:  MyLoginPopupView
 * Description: TODO
 * Author;  julio_chan  2020/3/31 19:01
 */
public class MyLoginCenterPopupView extends CenterPopupView implements View.OnClickListener {
    private Context mContext;
    private String mTitle;

    private TextInputLayout til_UserName, til_UserPwd;
    private AutoCompleteTextView et_UserName, et_UserPwd;

    private String mNameErrorStr,mPwdErrorStr;
    private String mUserName,mUserPwd;
    public void setUserName(String userName) {  mUserName = userName; }
    public void setUserPwd(String userPwd) { mUserPwd = userPwd; }

    public MyLoginCenterPopupView(@NonNull Context context, String title) {
        super(context);
        mContext = context;
        mTitle = title;
    }
    public MyLoginCenterPopupView(@NonNull Context context, String title, Listener listener) {
        super(context);
        mContext = context;
        mTitle = title;
        mListener = listener;
    }
    @Override
    protected int getImplLayoutId() { return R.layout.my_xpopup_center_login; }



    @Override
    protected void onCreate() {
        super.onCreate();
        ((TextView)findViewById(R.id.login_title)).setText(mTitle);
        findViewById(R.id.login_confirm).setOnClickListener(this);
        findViewById(R.id.login_cancel).setOnClickListener(this);
        til_UserName = findViewById(R.id.login_user_name_layout);
        til_UserPwd = findViewById(R.id.login_user_pwd_layout);

        et_UserName = findViewById(R.id.login_user_name_input);
        if(mUserName!=null) et_UserName.setText(mUserName);
        et_UserPwd = findViewById(R.id.login_user_pwd_input);
        if(mUserPwd!=null) et_UserPwd.setText(mUserPwd);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.login_confirm) {
            if (checkForm()) {
                mUserName = et_UserName.getText().toString().trim();
                mUserPwd = et_UserPwd.getText().toString().trim();
                if (mListener != null) { mListener.onConfirm(mUserName, mUserPwd); }
                dismiss();
            }
        } else if (i == R.id.login_cancel) {
            dismiss();
        }
    }
    public boolean checkForm(){
        boolean isPass = true;
        final String uid = et_UserName.getText().toString().trim();
        final String pwd = et_UserPwd.getText().toString().trim();
        if(mListener!=null){
            if (mListener.VerifyUserName(uid, mNameErrorStr)) {
                til_UserName.setError(null);
            } else {
                til_UserName.setError(mNameErrorStr);
                isPass = false;
            }
            if (mListener.VerifyUserPwd(pwd, mPwdErrorStr)) {
                til_UserPwd.setError(null);
            } else {
                til_UserPwd.setError(mPwdErrorStr);
                isPass = false;
            }
        }else {
            if (uid.isEmpty()) {
                til_UserName.setError("用户名不能为空");
                isPass = false;
            } else {
                til_UserName.setError(null);
            }
            if (pwd.isEmpty()) {
                til_UserPwd.setError("密码不能为空");
                isPass = false;
            } else {
                til_UserPwd.setError(null);
            }
        }
        return isPass;
    }


    public void setViewVisibility(View view, int visibility) {
        view.setVisibility(visibility);
    }

    private Listener mListener = null;
    public void setListener(Listener listener){mListener = listener;}
    public interface Listener {
        void onViewClick(View view);

        void onConfirm(String userName, String userPwd);

        /** 用户名验证方法
         * @param nameInput  输入的用户名
         * @param nameErrorStr  用户名未通过时 显示的错误提示
         * @return   用户名验证是否通过
         */
        boolean VerifyUserName(String nameInput, String nameErrorStr);

        /** 密码验证方法
         * @param pwdInput  输入的密码
         * @param pwdErrorStr  密码未通过时 显示的错误提示
         * @return   密码验证是否通过
         */
        boolean VerifyUserPwd(String pwdInput,String pwdErrorStr);
    }
}
