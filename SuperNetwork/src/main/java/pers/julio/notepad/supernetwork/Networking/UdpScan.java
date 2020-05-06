package pers.julio.notepad.supernetwork.Networking;

import android.content.Context;
import android.net.wifi.WifiManager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpScan extends Thread {
    private Context context;
    private int port;
    private DatagramSocket udpSocket;
    private DatagramPacket dataPacket = null;
    private String sendCmd;
    WifiManager manager;
    private WifiManager.MulticastLock lock;
    byte[] recvBuff = new byte[2048];
    private boolean isStart = true;
    private UdpScanInterface callback;

    public UdpScan (Context context, String cmd, int port, UdpScanInterface callback) {
        this.context = context;
        this.sendCmd = cmd;
        this.port = port;
        this.callback = callback;
        manager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        lock = manager.createMulticastLock("UDPScan");
    }

    public void udpSend() {
        try {
            udpSocket.send(dataPacket); //发送
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setStop() {
        isStart = false;
        if (udpSocket != null) {
            udpSocket.close();
            udpSocket=null;
        }
    }

    public void run(){
        DatagramPacket recivedata = null;
       // lock.acquire();
        try {
            udpSocket = new DatagramSocket();
            dataPacket = new DatagramPacket(sendCmd.getBytes(), sendCmd.length());
            dataPacket.setPort(port);
            InetAddress broadcastAddr;
            broadcastAddr = InetAddress.getByName("255.255.255.255");
            dataPacket.setAddress(broadcastAddr);

            recivedata = new DatagramPacket(recvBuff, recvBuff.length);
// recivedata.setData(redata);
// recivedata.setLength(redata.length);
// recivedata.setPort(DEFAULT_PORT);
// recivedata.setAddress(broadcastAddr);

            //udpSocket.send(dataPacket); //发送
        } catch (Exception e) {
            e.printStackTrace();
        }
        udpSend();
        while(isStart){
            //udpSend();
            try {
                udpSocket.receive(recivedata);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if( recivedata.getLength() != 0 ) {
                String codeString = new String(recivedata.getData(), 0, recivedata.getLength());
                System.out.println( "2接收到数据为："+codeString );
                callback.scanDone(codeString);
                /*if (recivedata!= null){
                    if (recivedata.getAddress() != null)
                        System.out.print("2recivedataIP地址为：" + recivedata.getAddress().toString() + "\r\n"); //此为IP地址
                    if (recivedata.getSocketAddress() != null)
                        System.out.print("2recivedata_sock地址为：" + recivedata.getSocketAddress().toString() + "\r\n"); //此为IP加端口号
                }*/
            }
        }
        //lock.release();
        if (udpSocket != null) {
            udpSocket.close();
            udpSocket=null;
        }
    }
}
