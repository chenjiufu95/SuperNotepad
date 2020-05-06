package pers.julio.notepad.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lxj.xpopup.XPopup;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import pers.julio.notepad.Activity.ExampleActivity;
import pers.julio.notepad.ExampleEvent;
import pers.julio.notepad.R;
import pers.julio.notepad.recyclerview.Bean.BaseItemBean;
import pers.julio.notepad.superevent.EventBus.Base.BaseEvent;
import pers.julio.notepad.superevent.EventBus.Base.BaseEventModel;
import pers.julio.notepad.xpopup.MyPopup.MyCenterListView;

/**
 * ClassName:  EventFragment
 * Description: TODO
 * Author;  julio_chan  2020/4/11 9:14
 */
public class EventFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private ExampleActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_event, null);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        activity = (ExampleActivity) getActivity();
        rootView.findViewById(R.id.test_event_bus).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.test_event_bus:
                ArrayList<BaseItemBean> datas = new ArrayList<>();
                datas.add(BaseItemBean.builder(activity).setId(ExampleEvent.OBJECT).setKey("简单对象").setShowState(false).build());
                datas.add(BaseItemBean.builder(activity).setId(ExampleEvent.Map).setKey("Map对象").setShowState(false).build());
                datas.add(BaseItemBean.builder(activity).setId(ExampleEvent.JSON).setKey("Json对象").setShowState(false).build());
                showListPopupView("触发消息", datas, false);
                break;
            default:
                break;
        }
    }

    public void showListPopupView(String title, ArrayList<BaseItemBean> datas, boolean isMutilSel) {
        MyCenterListView myCenterListView = new MyCenterListView(activity, title, datas, isMutilSel);
        new XPopup.Builder(activity).dismissOnTouchOutside(false).asCustom(myCenterListView).show();
        if (isMutilSel) {
            myCenterListView.setListener(new MyCenterListView.SimpleListener() {
                @Override
                public void onMultiSelConfirm(HashMap<Object, Boolean> Map) {
                    Toast.makeText(activity, "多选结束", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            myCenterListView.setListener(new MyCenterListView.SimpleListener() {
                @Override
                public void onItemClick(int id) {
                    switch (id) {
                        case BaseEvent.OBJECT:  // 任意对象
                            Object object = "任意对象";
                            BaseEvent.sendEvent(new BaseEventModel(ExampleEvent.OBJECT, object));
                            break;
                        case BaseEvent.Map:  // Map对象
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("res", 0);
                            map.put("name", "julio");
                            map.put("age", 28);
                            map.put("pass", true);
                            map.put("colors", new String[]{"red", "green", "blue"});
                            BaseEvent.sendEvent(new BaseEventModel(ExampleEvent.Map, map));
                            break;
                        case BaseEvent.JSON:  // Json对象
                            String jsonStr = "{\"res\":0,\"name\":\"julio\",\"age\":28,\"pass\":true}";
                            BaseEvent.sendEvent(new BaseEventModel(ExampleEvent.JSON, jsonStr));
                            break;
                    }
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        BaseEvent.register(this);
    }
    @Override
    public void onStop() {
        super.onStop();
        BaseEvent.unregister(this);
    }

    //BaseEvent.register(this);
    //BaseEvent.unregister(this);
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void BaseBulbEvent(BaseEventModel baseEventModel) {
        switch (baseEventModel.getCode()) {
            case BaseEvent.OBJECT:
                String response = (String) baseEventModel.getData();
                Toast.makeText(activity, "收到 响应: " + response, Toast.LENGTH_SHORT).show();
                break;
            case BaseEvent.Map:
                HashMap<String, Object> map = (HashMap<String, Object>) baseEventModel.getData();
                if ((int) map.get("res") == 0) {
                    String name = (String) map.get("name");
                    int age = (int) map.get("age");
                    boolean pass = (boolean) map.get("pass");
                    Toast.makeText(activity, "收到 Map: name= " + name + ", age = " + age + ", pass = " + pass, Toast.LENGTH_SHORT).show();
                }
                break;
            case BaseEvent.JSON:
                //{"res":0,"cmd":41,"dev_id":"sfddd_DT-PLUG","ssid":"Doit","pass":"555"}
                String jsonStr = (String) baseEventModel.getData();
                if (jsonStr.contains("\"res\":0")) {
                    try {
                        JSONObject jsonObject = new JSONObject(jsonStr);
                        String name = jsonObject.getString("name");
                        int age = jsonObject.getInt("age");
                        boolean pass = jsonObject.getBoolean("pass");
                        Toast.makeText(activity, "收到 Json: name= " + name + ", age = " + age + ", pass = " + pass, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) { e.printStackTrace(); }
                }
                break;
        }
    }
}
