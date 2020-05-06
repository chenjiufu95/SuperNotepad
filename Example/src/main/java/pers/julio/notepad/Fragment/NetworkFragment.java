package pers.julio.notepad.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lxj.xpopup.XPopup;

import java.io.File;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import pers.julio.notepad.Activity.ExampleActivity;
import pers.julio.notepad.R;
import pers.julio.notepad.recyclerview.Bean.BaseItemBean;
import pers.julio.notepad.supernetwork.Http.RestClient;
import pers.julio.notepad.supernetwork.Http.callback.IRequest;
import pers.julio.notepad.supernetwork.Http.callback.ISuccess;
import pers.julio.notepad.superutils.MyToastUtil;
import pers.julio.notepad.xpopup.MyPopup.MyCenterListView;

/**
 * ClassName:  NetworkFragment
 * Description: TODO
 * Author;  julio_chan  2020/4/11 9:20
 */
public class NetworkFragment extends Fragment implements View.OnClickListener {
    public static final String FILE_PATH = "/data/data/pers.julio.notepad/";//"/data/data/pers.julio.notepad/files/";
    private static final int TASK_GET = 100;
    private static final int TASK_POST = 102;
    private static final int TASK_POST_FILE = 103;
    private static final int TASK_UPLOAD = 104;
    private static final int TASK_DOWNLOAD = 105;
    private View rootView;
    private ExampleActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_network, null);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        activity = (ExampleActivity) getActivity();
        rootView.findViewById(R.id.test_socket).setOnClickListener(this);
        rootView.findViewById(R.id.test_tcp).setOnClickListener(this);
        rootView.findViewById(R.id.test_udp).setOnClickListener(this);
        rootView.findViewById(R.id.test_http).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.test_socket:
                Toast.makeText(activity, "尽请期待: Socket 通信", Toast.LENGTH_SHORT).show();
                break;
            case R.id.test_tcp:
                Toast.makeText(activity, "尽请期待: TCP 通信", Toast.LENGTH_SHORT).show();
                break;
            case R.id.test_udp:
                Toast.makeText(activity, "尽请期待: UDP 通信", Toast.LENGTH_SHORT).show();
                break;
            case R.id.test_http:
                ArrayList<BaseItemBean> datas = new ArrayList<>();
                datas.add(BaseItemBean.builder(activity).setId(TASK_GET).setKey("Get 请求").setShowState(false).build());
                datas.add(BaseItemBean.builder(activity).setId(TASK_POST).setKey("Post 请求").setShowState(false).build());
                datas.add(BaseItemBean.builder(activity).setId(TASK_POST_FILE).setKey("Post 文件").setShowState(false).build());
                datas.add(BaseItemBean.builder(activity).setId(TASK_UPLOAD).setKey("上传").setShowState(false).build());
                datas.add(BaseItemBean.builder(activity).setId(TASK_DOWNLOAD).setKey("下载").setShowState(false).build());
                showListPopupView("Http 任务", datas);
                break;
            default:
                break;
        }
    }

    public void showListPopupView(String title, ArrayList<BaseItemBean> datas) {
        MyCenterListView myCenterListView = new MyCenterListView(activity, title, datas, false);
        new XPopup.Builder(activity).dismissOnTouchOutside(false).asCustom(myCenterListView).show();
        myCenterListView.setListener(new MyCenterListView.SimpleListener() {
            @Override
            public void onItemClick(int id) {
                switch (id) {
                    case TASK_GET:
                        new RestClient.Builder()
                                .url("test")
                                //.url("http://192.168.9.141/app/index.html")
                                //.url("https://www.baidu.com/")
                                //.params("avatar", path)
                                .loader(getContext())
                                .success(new ISuccess() {
                                    @Override
                                    public void onSuccess(String response) {
                                        MyToastUtil.showToast(getContext(), "Get: " + response);
                                    }
                                })
                                .build().get();
                        break;
                    case TASK_POST:
                       /* new RestClient.Builder()//.url("http://192.168.43.170:8080/app/index.html").url(Global.URL_FRAME_UPLOAD)
                                .url("test")
                                .loader(activity)
                                //.file(fileName)
                                .raw(datas)
                                .success(new ISuccess() {
                                    @Override
                                    public void onSuccess(String response) {
                                        MyToastUtil.showToastInThread(activity, "同步成功");
                                    }
                                })
                                .onRequest(new IRequest() {
                                    @Override
                                    public void onRequestStart() { }
                                    @Override
                                    public void onRequestEnd() {

                                    }
                                })
                                .build().post();*/
                        break;
                    case TASK_POST_FILE:
                        new RestClient.Builder()//.url("http://192.168.43.170:8080/app/index.html").url(Global.URL_FRAME_UPLOAD)
                                .url("test")
                                .loader(activity)
                                .file("http://192.168.9.141/upload/" + File.separator + FILE_PATH + "test.txt")
                                .success(new ISuccess() {
                                    @Override
                                    public void onSuccess(String response) {
                                        MyToastUtil.showToastInThread(activity, "同步成功");
                                    }
                                })
                                .onRequest(new IRequest() {
                                    @Override
                                    public void onRequestStart() { }
                                    @Override
                                    public void onRequestEnd() {

                                    }
                                })
                                .build().post();
                        break;
                    case TASK_UPLOAD:
                        UploadSceneFrames("http://192.168.9.141/upload/", FILE_PATH + "test.txt");
                        break;
                    case TASK_DOWNLOAD:

                        break;
                }
            }
        });
    }

    private void UploadSceneFrames(String uploadUrl, String fileName) {
        File file = new File(fileName);
        if(!file.exists()){
            MyToastUtil.showToast(getContext(), "文件不存在：" + fileName);
            return;
        }
        new RestClient.Builder()//.url("http://192.168.43.170:8080/app/index.html").url(Global.URL_FRAME_UPLOAD)
                .url(uploadUrl)
                .loader(getContext())
                .file(file)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        MyToastUtil.showToast(getContext(), "桢数据上传成功");
                    }
                })
                .build().upload();
    }
}
