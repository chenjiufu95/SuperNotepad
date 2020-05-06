package pers.julio.notepad.superevent.EventBus.Base;

public class BaseEventModel<T> {
    private int code;
    private T data;

    public BaseEventModel(int code) {
        this.code = code;
    }

    public BaseEventModel(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
