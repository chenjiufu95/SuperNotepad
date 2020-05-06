package pers.julio.notepad.supernetwork.Http.callback;

import android.os.Handler;

import pers.julio.notepad.supernetwork.Http.RestCreator;
import pers.julio.notepad.supernetwork.Loader.Loader;
import pers.julio.notepad.supernetwork.Loader.LoaderStyle;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ClassName:  RequestCallbacks
 * Description: TODO
 * Author;  julio_chan  2020/1/14 9:49
 */
public final class RequestCallbacks implements Callback<String> {

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final LoaderStyle LOADER_STYLE;
    //private static final Handler HANDLER = NeoPixel.getHandler();

    public RequestCallbacks(IRequest request, ISuccess success, IFailure failure, IError error, LoaderStyle style) {
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.LOADER_STYLE = style;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.isSuccessful()) {
            if (call.isExecuted()) {
                if (SUCCESS != null) { SUCCESS.onSuccess(response.body()); }
            }
        } else {
            if (ERROR != null) { ERROR.onError(response.code(), response.message()); }
        }
        if (REQUEST != null) { REQUEST.onRequestEnd(); }
        onRequestFinish();
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if (FAILURE != null) { FAILURE.onFailure(); }
        if (REQUEST != null) { REQUEST.onRequestEnd(); }
        onRequestFinish();
    }

    private void onRequestFinish() {
        final long delayed = 1000;//NeoPixel.getConfig(ConfigType.LOADER_DELAYED);
        if (LOADER_STYLE != null) {
            //HANDLER.postDelayed(new Runnable() {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    RestCreator.getParams().clear();
                    Loader.stopLoading();
                }
            }, delayed);
        }
    }
}
