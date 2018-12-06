package com.raomeng.common.network.http;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * @author RaoMeng
 * @describe
 * @date 2018/1/3 15:11
 */

public interface HttpService {

    @GET()
    Flowable<Object> getRequest(@Url String url);

    @GET()
    Flowable<Object> getRequest(@Url String url, @QueryMap Map<String, Object> param);

    @GET()
    Flowable<Object> getRequest(@Url String url, @QueryMap Map<String, Object> param, @HeaderMap Map<String, Object> header);

    @FormUrlEncoded
    @POST()
    Flowable<Object> postRequest(@Url String url);

    @FormUrlEncoded
    @POST()
    Flowable<Object> postRequest(@Url String url, @FieldMap Map<String, Object> param);

    @FormUrlEncoded
    @POST()
    Flowable<Object> postRequest(@Url String url, @FieldMap Map<String, Object> param, @HeaderMap Map<String, Object> header);

}
