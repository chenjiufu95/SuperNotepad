package pers.julio.notepad.superevent.EventBus.Base;
import org.greenrobot.eventbus.EventBus;

public class BaseEvent {
    public static final int OBJECT = 0x100010;
    public static final int Map = 0x100011;
    public static final int JSON = 0x100012;

    public static void sendEvent(BaseEventModel event) {
        EventBus.getDefault().post(event);
    }

    public static void sendStickyEvent(BaseEventModel event) {
        EventBus.getDefault().postSticky(event);
    }

    public static void register(Object subscriber) {
        if (!EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().register(subscriber);
        }
    }
    public static void unregister(Object subscriber) {
        if(EventBus.getDefault().isRegistered(subscriber)){
            EventBus.getDefault().unregister(subscriber);
        }
    }
}
