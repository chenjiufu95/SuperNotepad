package pers.julio.notepad.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import pers.julio.notepad.Activity.ExampleActivity;
import pers.julio.notepad.R;
import pers.julio.notepad.superfragment.NativeFragment.Base.BaseFragment;
import pers.julio.notepad.supernetwork.Http.RestClient;
import pers.julio.notepad.supernetwork.Http.callback.ISuccess;

/**
 * ClassName:  TestFragment
 * Description: TODO
 * Author;  julio_chan  2020/1/13 18:43
 */
public class TestFragment extends BaseFragment implements View.OnClickListener {
    private ExampleActivity parent;
    private TextView mLogs;
    private TestFrag01 mFrag_01;
    private TestFrag02 mFrag_02;
    private TestFrag03 mFrag_03;

    @Override
    protected void HandleFragmentVisible(boolean visible) {
        super.HandleFragmentVisible(visible);
        parent.setTitle("测试界面 ==》" + (visible ? "显示" : "隐藏"));
    }


    @Override
    protected Object setLayout() { return R.layout.fragment_test; }
    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {
        parent = (ExampleActivity) activity;
        mLogs = rootView.findViewById(R.id.test_logs);
        rootView.findViewById(R.id.test_add_fragment).setOnClickListener(this);
        rootView.findViewById(R.id.test_replace_fragment).setOnClickListener(this);
        rootView.findViewById(R.id.test_show_fragment).setOnClickListener(this);
        rootView.findViewById(R.id.test_hide_fragment).setOnClickListener(this);
        //testTasks();
    }
    public void AddLogs(String newlog) {
        String logs = (String) mLogs.getText();
        mLogs.setText(logs + newlog + "\n");
    }

    private void testTasks() {
        testRestClient();
        //UploadSceneFrames("http://192.168.9.151:80/sss/", Global.FILE_PATH + "Custom_8McZ.hex");
        //PostSceneFrames("http://192.168.9.151:80/sss/", Global.FILE_PATH + "Custom_qXxa.hex");
        //testStorage();
        //testFile();
        //String json = JSONUtils.JointJSON("first","last");
        //ToastUtil.showToast(NeoPixel.getAppContext(), json);
    }
    private void testRestClient() {
        new RestClient.Builder().url("test")
                //.url("http://192.168.43.170:8080/app/index.html")
                //.url("https://www.baidu.com/")
                //.params("avatar", path)
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Toast.makeText(getContext(), response,Toast.LENGTH_SHORT).show();
                    }
                })
                .build().get();
    }
    private void PostSceneFrames(String uploadUrl, String datas) {
        new RestClient.Builder()//.url("http://192.168.43.170:8080/app/index.html").url(Global.URL_FRAME_UPLOAD)
                .url(uploadUrl)
                .loader(getContext())
                //.file(file)
                .raw(datas)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Toast.makeText(getContext(), "桢数据上传成功",Toast.LENGTH_SHORT).show();
                      /*  new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("桢数据上传成功");
                            }
                        }, 2000);*/
                    }
                })
                .build().post();
    }
    private void UploadSceneFrames(String uploadUrl, String fileName) {
        File file = new File(fileName);
        if(!file.exists()){
            Toast.makeText(getContext(), "文件不存在" + fileName,Toast.LENGTH_SHORT).show();
            return;
        }
        new RestClient.Builder()//.url("http://192.168.43.170:8080/app/index.html").url(Global.URL_FRAME_UPLOAD)
                .url(uploadUrl)
                .loader(getContext())
                .file(file)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Toast.makeText(getContext(), "桢数据上传成功",Toast.LENGTH_SHORT).show();
                      /*  new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("桢数据上传成功");
                            }
                        }, 2000);*/
                    }
                })
                .build().upload();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.test_add_fragment:
              if (mFrag_01 == null) {   mFrag_01 = new TestFrag01(); }
                parent.AddFirstFragment(null,R.id.test_frag_container,mFrag_01);
                break;
            case R.id.test_replace_fragment:
                if (mFrag_02 == null) {   mFrag_02 = new TestFrag02(); }
                parent.ReplaceFragment(R.id.test_frag_container,mFrag_02);
                break;
            case R.id.test_show_fragment:
                if (mFrag_03 == null) {   mFrag_03 = new TestFrag03(); }
                parent.ReplaceFragment(R.id.test_frag_container,mFrag_03);
                break;
            case R.id.test_hide_fragment:
                parent.HideFragment();
                break;
            default:
                break;
        }
    }





/*    private void testFile() {
        String rawFile = FileUtil.getRawFile(R.raw.frames);
        LogUtil.e(LogUtil.STORAGE, rawFile);
    }

    private void testStorage() {
        StripSceneTest scene = new StripSceneTest(0,"Test", "Test",0);
        scene.Appearance = new SceneAppearance(2,2,8,2,8,25,true);
        scene.setDefaultLedsColor(2);

        scene.FrameSize = 2;
        SparseArray led1 = new SparseArray(scene.FrameSize); // Led:1 的桢数据
        led1.put(1, new MyColor(170,187,204));  // 第1桢
        led1.put(2, new MyColor(221,238,255));  // 第2桢
        //frame1.put(3, new MyColor());
        SparseArray led2 = new SparseArray(scene.FrameSize);// Led:2 的桢数据
        led2.put(1, new MyColor(153,136,119));  // 第1桢
        led2.put(2, new MyColor(102,85,68));    // 第2桢
        //frame1.put(3, new MyColor());
        scene.LedsFramesColor = new SparseArray();
        scene.LedsFramesColor.put(1, led1);
        scene.LedsFramesColor.put(2, led2);
        LogUtil.e(LogUtil.STORAGE, StorageUtils.getStripSceneStr(scene));
        LogUtil.e(LogUtil.STORAGE, "===================================================");

        // 3, 写入桢数据
        //Global.FREQUENCY = scene.SceneFrames.size();  //帧数已经被固定
        if (StorageUtils.SaveTestStripFrames(scene)) {
            ToastUtil.showToast(NeoPixel.getAppContext(), "==写入文件：成功--");
        }else {
            ToastUtil.showToast(NeoPixel.getAppContext(), "==写入文件：失败--");
        }
    }*/
}
