package com.ademkayaaslan.currencyconverter;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {
    @GET("latest?access_key=1024af92fcf33cd687926e305ec6c231")
    Call<Post> getPosts();
}
