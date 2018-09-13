package com.e21cn.apiservice;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * Created by liangminqiang on 2018/4/11.
 */

public interface ToolServerce {

    @GET("apps/latest/5b5a89d1ca87a8108ff993a5?api_token=9633f677d251b0b14d0523452924450a&bundle_id=com.e21cn.kszjface.ui&type=android")//更新
    Call<ResponseBody> update();


}

