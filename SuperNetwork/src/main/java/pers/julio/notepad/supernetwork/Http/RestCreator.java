package pers.julio.notepad.supernetwork.Http;

import java.util.ArrayList;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import pers.julio.notepad.supernetwork.Http.Interceptor.DebugInterceptor;
import pers.julio.notepad.supernetwork.R;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * ClassName:  RestCreator
 * Description: TODO
 * Author;  julio_chan  2020/1/14 9:13
 */
public class RestCreator {
    /**
     * OKHttpHolder
     */
    private static final class OKHttpHolder {
        private static final int TIME_OUT = 30;
        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
        //private static final ArrayList<Interceptor> INTERCEPTORS = NeoPixel.getConfig(ConfigType.NET_INTERCEPTOR);
        private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();

        /* private static OkHttpClient.Builder addInterceptor() {
             if (INTERCEPTORS != null && !INTERCEPTORS.isEmpty()) {
                 for (Interceptor interceptor : INTERCEPTORS) {
                     BUILDER.addInterceptor(interceptor);
                 }
             }
             return BUILDER;
         }*/
        private static OkHttpClient.Builder addInterceptor() {
           BUILDER.addInterceptor(new DebugInterceptor("test", R.raw.test));
            return BUILDER;
        }
        private static final OkHttpClient OK_HTTP_CLIENT = addInterceptor()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
    }

    /**
     * 构建全局Retrofit客户端
     */
    private static final class RetrofitHolder {
        private static final String BASE_URL = "http://192.168.9.141/"; //NeoPixel.getConfig(ConfigType.NET_HOST);
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OKHttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    /**
     * Service接口
     */
    public static RestService getRestService() {
        return RestServiceHolder.REST_SERVICE;
    }
    private static final class RestServiceHolder {
        private static final RestService REST_SERVICE = RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
    }

    /**
     * @return  参数容器
     */
    public static WeakHashMap<String, Object> getParams() {
        return ParamsHolder.PARAMS;
    }
    private static final class ParamsHolder {
        private static final WeakHashMap<String, Object> PARAMS = new WeakHashMap<>();
    }

}
