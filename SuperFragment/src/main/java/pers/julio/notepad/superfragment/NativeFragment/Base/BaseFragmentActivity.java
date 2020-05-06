package pers.julio.notepad.superfragment.NativeFragment.Base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import pers.julio.notepad.superfragment.R;
import pers.julio.notepad.superutils.MySpUtil;
import qiu.niorgai.StatusBarCompat;

/**
 * ClassName:  BaseFragmentActivity
 * Description: TODO
 * Author;  julio_chan  2020/4/18 12:41
 */

public abstract class BaseFragmentActivity extends FragmentActivity {
    private static final String TAG_LAST_FRAG = "lastFragTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.translucentStatusBar(this, true);
        //Utils.setStatusBarColor(this);
    }

    public void setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            } else {
                Window window = getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.flags |= flagTranslucentStatus | flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
        }
    }
    public void finishForLoadDataError() {
        Toast.makeText(this, getString(R.string.load_error), Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 2000);
    }


    protected Fragment mCurFragment;
    protected FragmentManager mFragManager;
    protected HashMap<String, Fragment> mFragMap = null;

    // 必须指定 Fragment 操作的 位置ID
    public abstract int ContainerViewId(); // { return R.id.frag_container; }

    // 为防止 重叠：需要 保存当前Fragment Tag, 重新创建时 恢复。
    // 以下两个方法 留给用户 重写 保存和恢复逻辑， 如果重写必须同时重写； 若不重写 则保存到默认地址
    public boolean saveCurFragTag(String tag) { return false; }
    public String getLastFragTag() { return null; }


    /** 添加一个Fragment(不清空) */
    public void addFragment(Fragment fragment) {
        addFragment(fragment,null);
    }
    public void addFragment(Fragment fragment, String tag) {
        if (fragment == null) return;
        if (mFragMap == null) { mFragMap = new HashMap<>(); }
        if(tag == null || tag.equals(""))
            tag = fragment.getClass().getSimpleName();
        mFragMap.put(tag, fragment);
    }

    /** 一次设置Fragment(会清空) */
    public void setFragments(HashMap<String, Fragment> fragMap) {
        if (fragMap == null) return;
        if (mFragMap == null) { mFragMap = new HashMap<>(); }
        mFragMap.clear();
        mFragMap.putAll(fragMap);
    }
    public void setFragments(ArrayList<Fragment> fragments) {
        if (fragments == null) return;
        if (mFragMap == null) { mFragMap = new HashMap<>(); }
        mFragMap.clear();
        int size = fragments.size();
        for (int i = 0; i < size; i++) {
            Fragment fragment = fragments.get(i);
            if (fragment == null) continue;
            mFragMap.put(fragment.getClass().getSimpleName(), fragment);
        }
    }

    /** 第一次添加Fragment 时 调用(fragment重叠)
     * @param saveInstanceState  null 时 代表 正常启动， 否则 代表意外 后重启
     * @param fristFragment  复用时 需传入 类 成员变量
     */
    public void AddFirstFragment(Bundle saveInstanceState, Fragment fristFragment) {
        AddFirstFragment(saveInstanceState, ContainerViewId(),fristFragment);
    }
    public void AddFirstFragment(Bundle saveInstanceState, @IdRes int containerViewId, Fragment fristFragment) {
        if (mFragManager == null) { mFragManager = getSupportFragmentManager(); }
        String tag = fristFragment.getClass().getSimpleName();
        if (!fristFragment.isAdded() || null == mFragManager.findFragmentByTag(tag)) {
            FragmentTransaction transaction = mFragManager.beginTransaction();
            if (saveInstanceState == null) {
                transaction.add(containerViewId, fristFragment);
                transaction.addToBackStack(tag);
                transaction.commitAllowingStateLoss();
                if (!saveCurFragTag(tag)) MySpUtil.putString(this, TAG_LAST_FRAG, tag);
            } else { //通过tag找回失去引用但是存在内存中的fragment.id相同
                String lastFragTag = getLastFragTag();
                if (lastFragTag == null)
                    lastFragTag = MySpUtil.getString(this, TAG_LAST_FRAG, null);
                if (lastFragTag != null) {
                    Fragment lastFragment = mFragManager.findFragmentByTag(lastFragTag);
                    if (lastFragment != null) transaction.hide(lastFragment);
                }
                transaction.show(fristFragment);
                transaction.commitAllowingStateLoss();
            }
            mCurFragment = fristFragment;
        }
    }

    /** 替换Fragment
     * @param fragment
     */
    public void ReplaceFragment(Fragment fragment) {
        ReplaceFragment(ContainerViewId(),fragment);
    }
    public void ReplaceFragment(@IdRes int containerViewId, Fragment fragment) {
        if (fragment == null) return;
        String tag = fragment.getClass().getSimpleName();
        if (mFragMap == null) { mFragMap = new HashMap<>(); }
        if(mFragMap.get(tag) == null){
            mFragMap.put(fragment.getClass().getSimpleName(), fragment);
        }
        if (mFragManager == null) mFragManager = getSupportFragmentManager();
        FragmentTransaction transaction = mFragManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.frag_fade_in, R.anim.frag_fade_out);
        transaction.replace(containerViewId, fragment);
        transaction.addToBackStack(tag);
        transaction.commitAllowingStateLoss();
        mCurFragment = fragment;
        if (!saveCurFragTag(tag)) MySpUtil.putString(this, TAG_LAST_FRAG, tag);
    }

    /** 使用Show/Hide模式  显示
     * @param fragment  复用时 需传入 类 成员变量
     */
    public void ShowFragment(Fragment fragment) {
        ShowFragment(ContainerViewId(), fragment);
    }
    public void ShowFragment(@IdRes int containerViewId,Fragment fragment) {
        if (fragment == null) return;
        String tag = fragment.getClass().getSimpleName();
        if (mFragMap == null) { mFragMap = new HashMap<>(); }
        if (mFragMap.get(tag) == null) {
            mFragMap.put(fragment.getClass().getSimpleName(), fragment);
        }
        if (mFragManager == null) mFragManager = getSupportFragmentManager();
        FragmentTransaction transaction = mFragManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.frag_fade_in, R.anim.frag_fade_out);
        if (mCurFragment != null) transaction.hide(mCurFragment);

        if (!fragment.isAdded() && null == mFragManager.findFragmentByTag(tag)) {//判断是否以添加过
            transaction.add(containerViewId, fragment);
        }
        transaction.show(fragment);
        transaction.addToBackStack(tag);//添加到回退栈  重要!!!
        transaction.commitAllowingStateLoss();
        mCurFragment = fragment;
        if (!saveCurFragTag(tag)) MySpUtil.putString(this, TAG_LAST_FRAG, tag);
    }

    /** 根据标签 隐藏当前fragment,显示下一个fragment
     *  由于 本类维护好 回退栈及标签，所以此方法 少用
     * @param tag
     */
    private void ShowFragmentByTag(String tag) {
        if (tag == null || tag.equals("")) return;
        if (mFragMap == null) return;
        Fragment nextFragment = mFragMap.get(tag);
        if (nextFragment != null) {
            if (mFragManager == null) mFragManager = getSupportFragmentManager();
            FragmentTransaction transaction = mFragManager.beginTransaction();
            transaction.hide(mCurFragment);

            if (!nextFragment.isAdded()) {//判断是否以添加过
                transaction.add(ContainerViewId(), nextFragment);
                transaction.addToBackStack(tag);//添加到回退栈  重要!!!
            }
            transaction.show(nextFragment);
            transaction.commitAllowingStateLoss();
            mCurFragment = nextFragment;
            if (!saveCurFragTag(tag)) MySpUtil.putString(this, TAG_LAST_FRAG, tag);
        } else {
            throw new RuntimeException("");
        }
    }

    /** 隐藏并返回上一个fragment*/
    public void HideFragment() {
        if(mFragManager == null) mFragManager = getSupportFragmentManager();
        FragmentTransaction transaction = mFragManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.frag_fade_out, R.anim.frag_fade_in);
        mFragManager.popBackStackImmediate();
        mCurFragment = mFragManager.findFragmentById(ContainerViewId());
    }

    /** 隐藏并返回上一个fragment*/
    public void HideFragment(String tag) {
        if (mFragManager == null) mFragManager = getSupportFragmentManager();
        FragmentTransaction transaction = mFragManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.frag_fade_out, R.anim.frag_fade_in);
        //0表示只弹出该元素以上的所有元素，POP_BACK_STACK_INCLUSIVE 表示弹出包含该元素及以上的所有元素
        mFragManager.popBackStackImmediate(tag,0/*FragmentManager.POP_BACK_STACK_INCLUSIVE*/);
        mCurFragment = mFragManager.findFragmentById(ContainerViewId());
    }
}
