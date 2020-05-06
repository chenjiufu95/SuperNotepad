package pers.julio.notepad.supernetwork.Networking;

import android.net.wifi.WifiManager;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpConnection extends Thread {
    private int port;
    private DatagramSocket udpSocket;
    WifiManager manager;
    byte[] recvBuff = new byte[2048];
    private boolean isStart = true;
    private UdpConnInterface callback;

    public UdpConnection (int port, UdpConnInterface callback) {
        this.port = port;
        this.callback = callback;
    }

    public void udpStartScanning() {
        // QUOTE: 2020/4/10,  引用待整理，暂时屏蔽
        //udpSend(Constants.UDP_CMD_PING);
    }

    public void udpSend(String msg) {
        try {
            DatagramPacket dataPacket = new DatagramPacket(msg.getBytes(), msg.length());
            dataPacket.setPort(port);
            InetAddress broadcastAddr;
            broadcastAddr = InetAddress.getByName("255.255.255.255");
            dataPacket.setAddress(broadcastAddr);
            if (udpSocket == null || udpSocket.isClosed()) return;
            udpSocket.send(dataPacket); //发送
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setStop() {
        isStart = false;
        /*if (udpSocket != null) {
            udpSocket.close();
        }*/
    }

    public void run(){
        DatagramPacket recivedata = null;
        try {
            udpSocket = new DatagramSocket();
            recivedata = new DatagramPacket(recvBuff, recvBuff.length);
        } catch (Exception e) {
            e.printStackTrace();
        }

        while(isStart){
            try {
                if (udpSocket.isClosed()) break;
                udpSocket.receive(recivedata);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if( recivedata.getLength() != 0 ) {
                String codeString = new String(recivedata.getData(), 0, recivedata.getLength());
                System.out.println( "接收到数据为："+codeString );
                if (codeString.contains("cmd=pong")) {
                    callback.scanDone(codeString);
                } else {
                    callback.recvUdpEcho(codeString);
                }
                //System.out.print("recivedataIP地址为："+recivedata.getAddress().toString()+"\r\n"); //此为IP地址
                //System.out.print("recivedata_sock地址为："+recivedata.getSocketAddress().toString()+"\r\n"); //此为IP加端口号
            }
        }
        udpSocket.close();
    }

    public void setUdpCallbacks(UdpConnInterface callback) {
        this.callback = callback;
    }
}
