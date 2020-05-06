package pers.julio.notepad.supernetwork.Networking;

public interface UdpConnInterface {
    void scanDone(String recvStr);
    void recvUdpEcho(String recvStr);
}
