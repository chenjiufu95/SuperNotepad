package pers.julio.notepad.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import pers.julio.notepad.Activity.ExampleActivity;
import pers.julio.notepad.R;

/**
 * ClassName:  BtAndUartFragment
 * Description: TODO
 * Author;  julio_chan  2020/4/11 15:00
 */
public class BtAndUartFragment extends Fragment {
        private View rootView;
            private ExampleActivity activity;
            @Nullable
            @Override
            public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
                rootView = inflater.inflate(R.layout.fragment_bt_uart, null);
                return rootView;
            }
            @Override
            public void onActivityCreated(@Nullable Bundle savedInstanceState) {
                super.onActivityCreated(savedInstanceState);
                initView();
            }
            private void initView() {
                activity = (ExampleActivity) getActivity();
                //TextView textView = rootView.findViewById(R.id.);
                //rootView.findViewById(R.id.).setOnClickListener(this);
            }
}
