package pers.julio.notepad.superfragment.NativeFragment.Utils;

import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import pers.julio.notepad.superfragment.R;
import pers.julio.notepad.superutils.MySpUtil;

/**
 * ClassName:  FragHandler
 * Description: TODO
 * Author;  julio_chan  2020/4/18 15:55
 */
public class FragHandler {
    private static final String TAG_LAST_FRAG = "lastFragTag";

    private static Context mContent;
    private static int mContainerId;
    private static Fragment mCurFragment;
    private static FragmentManager mFragManager;
    private static HashMap<String, Fragment> mFragMap = null;

    public FragHandler(Context content, int containerId, FragmentManager manager) {
        mContent = content;
        mContainerId = containerId;
        mFragManager = manager;
    }

    public static void addFragment(Fragment fragment) {
        addFragment(fragment, null);
    }

    /**
     * 使用Show/Hide模式
     *
     * @param fragment 复用时 需传入 类成员变量
     */
    public static void addFragment(Fragment fragment, String tag) {
        if (fragment == null) return;
        if (mFragMap == null) { mFragMap = new HashMap<>(); }
        if (tag == null || tag.equals("")) {
            tag = fragment.getClass().getSimpleName();
        }
        mFragMap.put(tag, fragment);
    }

    public static void setFragments(HashMap<String, Fragment> fragMap) {
        if (fragMap == null) return;
        if (mFragMap == null) { mFragMap = new HashMap<>(); }
        mFragMap.clear();
        mFragMap.putAll(fragMap);
    }

    public static void setFragments(ArrayList<Fragment> fragments) {
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

    /**
     * 第一次添加Fragment 时 调用(fragment重叠)
     *
     * @param saveInstanceState null 时 代表 正常启动， 否则 代表意外 后重启
     * @param fristFragment     复用时 需传入 类 成员变量
     */
    public static void loadFirstFragment(Bundle saveInstanceState, Fragment fristFragment) {
        if (mFragManager != null) {
            FragmentTransaction transaction = mFragManager.beginTransaction();
            if (saveInstanceState == null) {
                transaction.add(mContainerId, fristFragment);
                String tag = fristFragment.getClass().getSimpleName();
                transaction.addToBackStack(tag);
                transaction.commit();
                MySpUtil.putString(mContent, TAG_LAST_FRAG, tag);
            } else { //通过tag找回失去引用但是存在内存中的fragment.id相同
                String lastFragTag = MySpUtil.getString(mContent, TAG_LAST_FRAG, null);
                if (lastFragTag != null) {
                    Fragment lastFragment = mFragManager.findFragmentByTag(lastFragTag);
                    if (lastFragment != null) transaction.hide(lastFragment);
                }
                transaction.show(fristFragment);
                transaction.commit();
            }
            mCurFragment = fristFragment;
        } else {
            throw new RuntimeException("FragmentManager is not ready,call configure");
        }
    }

    /**
     * 使用Show/Hide模式  显示
     *
     * @param fragment 复用时 需传入 类 成员变量
     */
    public static void showFragment(Fragment fragment) {
        if (fragment == null) return;
        String tag = fragment.getClass().getSimpleName();
        if (mFragMap == null) { mFragMap = new HashMap<>(); }
        if (mFragMap.get(tag) == null) {
            mFragMap.put(fragment.getClass().getSimpleName(), fragment);
        }
        if (mFragManager != null) {
            FragmentTransaction transaction = mFragManager.beginTransaction();
            if (mCurFragment != null) transaction.hide(mCurFragment);

            if (!fragment.isAdded()) {//判断是否以添加过
                transaction.add(mContainerId, fragment);
                transaction.addToBackStack(tag);//添加到回退栈  重要!!!
            }
            transaction.show(fragment);
            transaction.commit();
            mCurFragment = fragment;
            MySpUtil.putString(mContent, TAG_LAST_FRAG, tag);
        } else {
            throw new RuntimeException("FragmentManager is not ready,call configure");
        }
    }

    /**
     * 根据标签 隐藏当前fragment,显示下一个fragment
     * 由于 本类维护好 回退栈及标签，所以此方法 少用
     *
     * @param tag
     */
    public static void showFragmentByTag(String tag) {
        if (tag == null || tag.equals("")) return;
        if (mFragMap == null) return;
        Fragment nextFragment = mFragMap.get(tag);
        if (nextFragment != null) {
            if (mFragManager != null) {
                FragmentTransaction transaction = mFragManager.beginTransaction();
                transaction.hide(mCurFragment);

                if (!nextFragment.isAdded()) {//判断是否以添加过
                    transaction.add(mContainerId, nextFragment);
                    transaction.addToBackStack(tag);//添加到回退栈  重要!!!
                }
                transaction.show(nextFragment);
                transaction.commit();
                mCurFragment = nextFragment;
                MySpUtil.putString(mContent, TAG_LAST_FRAG, tag);
            } else {
                throw new RuntimeException("FragmentManager is not ready,call configure");
            }
        } else {
            throw new RuntimeException("");
        }
    }

    /*** 隐藏并返回上一个fragment*/
    public static void hideFragment(String tag) {
        if (mFragManager != null) {
            //0表示只弹出该元素以上的所有元素，POP_BACK_STACK_INCLUSIVE 表示弹出包含该元素及以上的所有元素
            mFragManager.popBackStackImmediate(tag, 0/*FragmentManager.POP_BACK_STACK_INCLUSIVE*/);
            mCurFragment = mFragManager.findFragmentById(mContainerId);
        } else {
            throw new RuntimeException("FragmentManager is not ready,call configure");
        }
    }

    /*** 隐藏并返回上一个fragment*/
    public static void hideFragment() {
        if (mFragManager != null) {
            mFragManager.popBackStackImmediate();
            mCurFragment = mFragManager.findFragmentById(mContainerId);
        } else {
            throw new RuntimeException("FragmentManager is not ready,call configure");
        }
    }
}
