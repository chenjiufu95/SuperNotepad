package pers.julio.notepad;

import java.util.HashMap;

import pers.julio.notepad.superevent.EventBus.Base.BaseEvent;
import pers.julio.notepad.superevent.EventBus.Base.BaseEventModel;

/**
 * ClassName:  ExampleEvent
 * Description: TODO
 * Author;  julio_chan  2020/4/11 16:06
 */
public class ExampleEvent extends BaseEvent {
//    public static final int OBJECT = 0x100010;
//    public static final int Map = 0x100011;
//    public static final int JSON = 0x100012;

    public static final int DEVICE_STATE_REALTIME = 0x100000;     // 实时同步
    public static void sendRealTimeEvent(String deviceId, boolean isPowerOn) {
        HashMap<String, Boolean> toggleDevMap = new HashMap<>();
        toggleDevMap.put(deviceId,isPowerOn);
        sendEvent(new BaseEventModel(DEVICE_STATE_REALTIME,toggleDevMap));
    }
}
