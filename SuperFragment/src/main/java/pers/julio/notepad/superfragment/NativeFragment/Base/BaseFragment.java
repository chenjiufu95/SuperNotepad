package pers.julio.notepad.superfragment.NativeFragment.Base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import pers.julio.notepad.superfragment.NativeFragment.Utils.HandleBackUtil;

/**
 * ClassName:  BaseFragment
 * Description: TODO
 * Author;  julio_chan  2020/4/18 12:41
 */
public abstract class BaseFragment extends Fragment implements HandleBackUtil.IBackPressed {
    protected View rootView;
    protected Context appContext;
    protected Activity activity;

    protected void HandleFragmentVisible(boolean visible){}

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        HandleFragmentVisible(isVisibleToUser);
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        HandleFragmentVisible(!hidden);
    }
    @Override
    public boolean onBackPressed() {
        return HandleBackUtil.handleBackPress(this);
    }

    //根据设置参数 重写 解析参数的 方法
    protected void parseParams() { Bundle bundle = getArguments(); }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        appContext = context.getApplicationContext();
        activity = (Activity) context;
        if (context instanceof Listener) {
            mListener = (Listener) context;
        //} else {
            //throw new RuntimeException(context.toString() + " must implement Listener");
        }
        parseParams();
    }

    protected abstract Object setLayout();
    protected abstract void onBindView(Bundle savedInstanceState, View rootView);
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView;
        if (setLayout() instanceof Integer) {
            rootView = inflater.inflate((int) setLayout(), container, false);
        } else if (setLayout() instanceof View) {
            rootView = (View) setLayout();
        } else {
            throw new ClassCastException("type of setLayout() must be int or View!");
        }
        onBindView(savedInstanceState, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mListener != null) { mListener.InitCompleted(savedInstanceState); }
    }
    private Listener mListener = null;
    public interface Listener {
        void InitCompleted(Bundle savedInstanceState);
    }

////////////////////////////////// 子类根据逻辑 需要实现的方法//////////////////////////////
    protected void setFragParams() { //获取参数
//        //BaseFragment fragment = new BaseFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("params", str);
//        fragment.setArguments(bundle);   //设置参数
//        return fragment;
    }
    protected BaseFragment setFragParam(String key, Object Value) { //获取参数
        return this;
    }
    //public static BaseFragment newInstance(Object... argus) {}
   /* public static BaseFragment newInstance(String str) {
        BaseFragment fragment = new BaseFragment();
        Bundle bundle = new Bundle();
        bundle.putString("parmas", str);
        fragment.setArguments(bundle);   //设置参数
        return fragment;
    }*/

}
