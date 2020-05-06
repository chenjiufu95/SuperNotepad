package com.doit.julio.stripneopixel.Net.Networking;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.xuhao.didi.core.iocore.interfaces.ISendable;
import com.xuhao.didi.core.pojo.OriginalData;
import com.xuhao.didi.core.protocol.IReaderProtocol;
import com.xuhao.didi.socket.client.sdk.OkSocket;
import com.xuhao.didi.socket.client.sdk.client.ConnectionInfo;
import com.xuhao.didi.socket.client.sdk.client.OkSocketOptions;
import com.xuhao.didi.socket.client.sdk.client.action.SocketActionAdapter;
import com.xuhao.didi.socket.client.sdk.client.connection.IConnectionManager;

import java.nio.ByteOrder;
import java.nio.charset.Charset;

public class TcpConnection extends Thread {
    private ConnectionInfo info;
    IConnectionManager manager;
    SocketActionAdapter adapter;
    private String tempRecvStr = "";
    private TcpSendData sendData = new TcpSendData("");

    public TcpConnection(String ip, int port, final DeviceTcpReadCallback listener) {
        info = new ConnectionInfo(ip, port);
        //调用OkSocket,开启这次连接的通道,拿到通道Manager
        manager = OkSocket.open(info);
        //注册Socket行为监听器,SocketActionAdapter是回调的Simple类,其他回调方法请参阅类文档
        adapter = new SocketActionAdapter(){
            @Override
            public void onSocketConnectionSuccess(ConnectionInfo info, String action) {
                //Toast.makeText(context, "连接成功", LENGTH_SHORT).show();
                System.out.println("~~~>  onSocketConnectionSuccess");
            }

            @Override
            public void onSocketDisconnection(ConnectionInfo info, String action, Exception e) {
                System.out.println("~~~> onSocketDisconnection  "+action);
                e.printStackTrace();
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        manager.connect();
                    }
                }, 3000);   //如果oksocket默认会重连
            }

            @Override
            public void onSocketConnectionFailed(ConnectionInfo info, String action, Exception e) {
                System.out.println("~~~> onSocketConnectionFailed");
            }

            @Override
            public void onSocketReadResponse(ConnectionInfo info, String action, OriginalData data) {
                String header = new String(data.getHeadBytes(), Charset.forName("utf-8"));
                String body = new String(data.getBodyBytes(), Charset.forName("utf-8"));
                String res = header + body;
                if (res.contains("\r")) {
                    if (TextUtils.equals(body, "\r")) {
                        tempRecvStr += header;
                    }
                    if (listener != null) {
                        listener.onTcpRead(tempRecvStr);
                    }
                    tempRecvStr = "";
                    return;
                }
                if (res.contains("\n")) {
                    if (TextUtils.equals(header, "\n")) {
                        tempRecvStr += body;
                    }
                    return;
                }
                tempRecvStr += res;
            }

            @Override
            public void onSocketWriteResponse(ConnectionInfo info, String action, ISendable data) {
                //System.out.println("~~~>  onSocketWriteResponse: "+action+" = "+data.toString());
            }
        };

        manager.registerReceiver(adapter);

        //获得当前连接通道的参配对象
        OkSocketOptions options= manager.getOption();
        //基于当前参配对象构建一个参配建造者类
        OkSocketOptions.Builder builder = new OkSocketOptions.Builder(options);
        builder.setConnectTimeoutSecond(10);
        builder.setReaderProtocol(new IReaderProtocol() { //设置触发条件，当接收到信息头长度为1，body长度为1时触发response回调函数
            @Override
            public int getHeaderLength() {
                return 1;
            }

            @Override
            public int getBodyLength(byte[] header, ByteOrder byteOrder) {
                return 1;
            }
        });
        //建造一个新的参配对象并且付给通道
        manager.option(builder.build());
        //调用通道进行连接
        manager.connect();
    }

    public void send(String msg) {
        if (!manager.isConnect()) {
            return;
        }
        //System.out.println("~~~>  TcpConnection send");
        sendData.setMessage(msg);
        manager.send(sendData);
    }

    public void disconnect() {
        manager.disconnect();
        manager.unRegisterReceiver(adapter);
    }

    public class TcpSendData implements ISendable {
        private String str = "";

        public TcpSendData(String msg) {
            str = msg;
        }

        public void setMessage(String msg) {
            this.str = msg;
        }

        @Override
        public byte[] parse() {
            //根据服务器的解析规则,构建byte数组
            return str.getBytes(Charset.defaultCharset());
        }
    }

    public interface DeviceTcpReadCallback {
        void onTcpRead(String data);
    }

}
