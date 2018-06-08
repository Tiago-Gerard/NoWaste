package com.example.gerardt_info.nowaste.Data;



import com.example.gerardt_info.nowaste.models.Offre;
import com.example.gerardt_info.nowaste.models.Utilisateur;

import java.io.IOException;
import java.util.List;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import com.example.gerardt_info.nowaste.Data.Outils;

public interface AccesService {


    @GET("service/offer/get.php")
    Call<List<Offre>> getOffre(
            @Query("latitude") double lat,
            @Query("longitude") double lon);

    Retrofit retrofitGetOffre = new Retrofit.Builder()
            .baseUrl("http://10.134.97.230/nowaste/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    @GET("service/user/get.php")
    Call<List<Utilisateur>> getUser();

    Retrofit retrofitUser = new Retrofit.Builder()
            .baseUrl("http://10.134.97.230/nowaste/")
            .client(Outils.getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @POST("service/user/create.php")
    @FormUrlEncoded
    Call<List<Utilisateur>> createUser(
            @Field("email") String email,
            @Field("nom") String nom,
            @Field("numero") String numero,
            @Field("prenom") String prenom);
    Retrofit retrofitAddSchool = new Retrofit.Builder()
            .baseUrl("http://10.134.97.230/nowaste/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}