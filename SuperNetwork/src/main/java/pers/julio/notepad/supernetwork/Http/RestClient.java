package pers.julio.notepad.supernetwork.Http;

import android.content.Context;


import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pers.julio.notepad.supernetwork.Http.callback.IError;
import pers.julio.notepad.supernetwork.Http.callback.IFailure;
import pers.julio.notepad.supernetwork.Http.callback.IRequest;
import pers.julio.notepad.supernetwork.Http.callback.ISuccess;
import pers.julio.notepad.supernetwork.Http.callback.RequestCallbacks;
import pers.julio.notepad.supernetwork.Loader.Loader;
import pers.julio.notepad.supernetwork.Loader.LoaderStyle;
import retrofit2.Call;

/**
 * ClassName:  RestClient
 * Description: TODO
 * Author;  julio_chan  2020/1/14 9:08
 */
public class RestClient {
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final String URL;
    private final IRequest REQUEST;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final RequestBody BODY;
    private final LoaderStyle LOADER_STYLE;
    private final File FILE;
    private final Context CONTEXT;

    RestClient(String url, Map<String, Object> params,
               String downloadDir, String extension, String name,
               IRequest request, ISuccess success, IFailure failure, IError error,
               RequestBody body, File file,
               Context context, LoaderStyle loaderStyle) {
        this.URL = url;
        PARAMS.putAll(params);
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extension;
        this.NAME = name;
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.BODY = body;
        this.FILE = file;
        this.CONTEXT = context;
        this.LOADER_STYLE = loaderStyle;
    }

    private void request(HttpMethod method) {
        final RestService service = RestCreator.getRestService();
        Call<String> call = null;

        if (REQUEST != null) { REQUEST.onRequestStart(); }
        if (LOADER_STYLE != null) { Loader.showLoading(CONTEXT, LOADER_STYLE);}
        switch (method) {
            case GET:
                call = service.get(URL, PARAMS);
                break;
            case POST:
                call = service.post(URL, PARAMS);
                break;
            case POST_RAW:
                call = service.postRaw(URL, BODY);
                break;
            case PUT:
                call = service.put(URL, PARAMS);
                break;
            case PUT_RAW:
                call = service.putRaw(URL, BODY);
                break;
            case DELETE:
                call = service.delete(URL, PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part body = MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);
                call = service.upload(URL, body);
                break;
            default:
                break;
        }
        if (call != null) {
            call.enqueue(new RequestCallbacks(REQUEST, SUCCESS, FAILURE, ERROR, LOADER_STYLE));
        }
    }

    public final void get() {
        request(HttpMethod.GET);
    }

    public final void post() {
        if (BODY == null) {
            request(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) { throw new RuntimeException("params must be null!"); }
            request(HttpMethod.POST_RAW);
        }
    }

    public final void put() {
        if (BODY == null) {
            request(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) { throw new RuntimeException("params must be null!"); }
            request(HttpMethod.PUT_RAW);
        }
    }

    public final void delete() {
        request(HttpMethod.DELETE);
    }

    public final void upload() {
        request(HttpMethod.UPLOAD);
    }

   /* public final void download() {
        new DownloadHandler(URL, REQUEST, DOWNLOAD_DIR, EXTENSION, NAME, SUCCESS, FAILURE, ERROR).handleDownload();
    }*/


   //////////////////////////////////////////////////////////////////////////////////////////////////
    public static class Builder {
       private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
       private String mUrl = null;
       private IRequest mIRequest = null;
       private ISuccess mISuccess = null;
       private IFailure mIFailure = null;
       private IError mIError = null;
       private RequestBody mBody = null;
       private Context mContext = null;
       private LoaderStyle mLoaderStyle = null;
       private File mFile = null;
       private String mDownloadDir = null;
       private String mExtension = null;
       private String mName = null;

       public Builder() { }

       public final Builder url(String url) {
           this.mUrl = url;
           return this;
       }

       public final Builder params(WeakHashMap<String, Object> params) {
           PARAMS.putAll(params);
           return this;
       }
       public final Builder params(String key, Object value) {
           PARAMS.put(key, value);
           return this;
       }

       public final Builder file(File file) {
           this.mFile = file;
           return this;
       }
       public final Builder file(String file) {
           this.mFile = new File(file);
           return this;
       }

       public final Builder name(String name) {
           this.mName = name;
           return this;
       }
       public final Builder dir(String dir) {
           this.mDownloadDir = dir;
           return this;
       }

       public final Builder extension(String extension) {
           this.mExtension = extension;
           return this;
       }

       public final Builder raw(String raw) {
           this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
           return this;
       }
       public final Builder raw(byte[] bytes) {
           //this.mBody = RequestBody.create(bytes);
           this.mBody = RequestBody.create(MediaType.parse("application/octet-stream;charset=UTF-8"), bytes) ;
           return this;
       }

       public final Builder onRequest(IRequest iRequest) {
           this.mIRequest = iRequest;
           return this;
       }

       public final Builder success(ISuccess iSuccess) {
           this.mISuccess = iSuccess;
           return this;
       }

       public final Builder failure(IFailure iFailure) {
           this.mIFailure = iFailure;
           return this;
       }

       public final Builder error(IError iError) {
           this.mIError = iError;
           return this;
       }

       public final Builder loader(Context context, LoaderStyle style) {
           this.mContext = context;
           this.mLoaderStyle = style;
           return this;
       }

       public final Builder loader(Context context) {
           this.mContext = context;
           this.mLoaderStyle = LoaderStyle.BallClipRotatePulseIndicator;
           return this;
       }

       public final RestClient build() {
           return new RestClient(mUrl, PARAMS,
                   mDownloadDir, mExtension, mName,
                   mIRequest, mISuccess, mIFailure,
                   mIError, mBody, mFile, mContext,
                   mLoaderStyle);
       }
    }
}
