package com.example.gerardt_info.nowaste.Data;



import com.example.gerardt_info.nowaste.models.Offre;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AccesService {


    @GET("service/offer/get.php")
    Call<List<Offre>> getOffre(
            @Query("latitude") double lat,
            @Query("longitude") double lon);
            Retrofit retrofitGetOffre = new Retrofit.Builder()
            .baseUrl("http://10.134.97.230/nowaste/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}