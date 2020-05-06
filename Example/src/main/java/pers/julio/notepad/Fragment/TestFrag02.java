package pers.julio.notepad.Fragment;


import android.os.Bundle;
import android.view.View;

import pers.julio.notepad.Activity.ExampleActivity;
import pers.julio.notepad.R;
import pers.julio.notepad.superfragment.NativeFragment.Base.BaseFragment;

/**
 * ClassName:  TestFrag01
 * Description: TODO
 * Author;  julio_chan  2020/4/26 15:54
 */
public class TestFrag02 extends BaseFragment {
    private ExampleActivity parent;

    @Override
    protected void HandleFragmentVisible(boolean visible) {
        super.HandleFragmentVisible(visible);
        if (parent != null) { parent.AddTestLog("测试_02 ==》" + (visible ? "显示" : "隐藏")); }
    }

    @Override
    protected Object setLayout() { return R.layout.frag_test_02; }

    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {
        parent = (ExampleActivity) activity;
    }
}
