package api;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/12/3.
 */

public interface LoginApi {

    @FormUrlEncoded
    @POST("register/")
    Observable<ResponseBody> register(@Field("mobile") String mobile, @Field("password") String password);
}
