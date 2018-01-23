package utils;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/12/2.
 */

public class RetrofitManager {

    private Retrofit retrofit;
    //private static final String DEFUALT_BASE_UTL="http://fangzhou.181858.com/index.php/App/Users/";
    private static final String DEFUALT_BASE_UTL="http://admin-bj-test.hxsapp.com/manager/User/";

    //单例模式
    private static class SingleHolder{
        private static final RetrofitManager instance = new RetrofitManager(DEFUALT_BASE_UTL);
    }
    //使用默认的单例 RetrofitManager,是默认的baseurl
    public static RetrofitManager getDefault(){
        return SingleHolder.instance;
    }
    //如果baseurl不是默认的话，那就调用这个方法构造一个新的RetrofitManager
    public static RetrofitManager getRetrofitManager(String baseUrl){
        return new RetrofitManager(baseUrl);
    }

    //构造方法
    private RetrofitManager(String baseurl) {
        this.retrofit = buildRetrofit(baseurl);
    }

    private Retrofit buildRetrofit(String baseUrl){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        return builder.build();
    }

    public  <T>T create(Class<T> clazz){
        return retrofit.create(clazz);
    }


}
