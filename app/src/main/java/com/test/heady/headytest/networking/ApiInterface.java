package com.test.heady.headytest.networking;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("json")
    Call<ApiResponse> getResponse();

}
