/*
 * Projet  : No Waste
 * Auteur  : Tiago Gerard
 * Version : 1.0
 * Fichier : AccesService.java
 * */
package com.example.gerardt_info.nowaste.Data;



import com.example.gerardt_info.nowaste.models.MyOffer;
import com.example.gerardt_info.nowaste.models.Offre;
import com.example.gerardt_info.nowaste.models.Type;
import com.example.gerardt_info.nowaste.models.Utilisateur;

import java.io.IOException;
import java.util.List;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import com.example.gerardt_info.nowaste.Data.Outils;

public interface AccesService {

    //liste des accés à la base de données
    final String baseUrl ="http://10.134.97.230/nowaste/";

    @GET("service/offer/get.php")
    Call<List<Offre>> getOffre(
            @Query("latitude") double lat,
            @Query("longitude") double lon,
            @Query("idUtilisateur")String idUtilisateur);

    Retrofit retrofitGetOffre = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET("service/offer/get.php")
    Call<List<Offre>> getOffreByType(
            @Query("latitude") double lat,
            @Query("longitude") double lon,
            @Query("idUtilisateur")String idUtilisateur,
            @Query("idType")String idType);

    Retrofit retrofitGetOffreByType = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    @GET("service/user/get.php")
    Call<List<Utilisateur>> getUser();

    Retrofit retrofitUser = new Retrofit.Builder()
            .baseUrl(baseUrl)
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
    Retrofit retrofitCreateUser = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET("service/type/get.php")
    Call<List<Type>> getType();
    Retrofit retrofitType = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @Multipart
    @POST("service/offer/create.php")
    Call<Boolean> createOffer(
            @Part MultipartBody.Part image,
            @Part("name") RequestBody name,
            @Query("description") String description,
            @Query("datePeremption") String datePeremption,
            @Query("latitude") double latitude,
            @Query("longitude") double longitude,
            @Query("idUtilisateur") String idUtilisateur,
            @Query("idType") String idType);
    Retrofit retrofitCreateOffer = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET("service/offer/get.php")
    Call<List<MyOffer>> getMesOffres(
            @Query("idUtilisateur") String idUtilisateur);

    Retrofit retrofitGetMyOffre = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @POST("service/user/update.php")
    @FormUrlEncoded
    Call<Boolean> updateUser(
            @Field("idUtilisateur") String idUtilisateur,
            @Field("email") String email,
            @Field("nom") String nom,
            @Field("numero") String numero,
            @Field("prenom") String prenom);
    Retrofit retrofitUpdateUser = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @POST("service/offer/delete.php")
    @FormUrlEncoded
    Call<Boolean> deleteMyOffre(
            @Field("idOffre") String idOffre);

    Retrofit retrofitDeleteMyOffer = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    @POST("service/offer/update.php")
    @FormUrlEncoded
    Call<Boolean> updateOffer(
            @Field("idOffre") String idOffre,
            @Field("description") String description,
            @Field("datePeremption") String datePeremption,
            @Field("latitude") double latitude,
            @Field("longitude") double longitude,
            @Field("idType") String idType);
    Retrofit retrofitUpdateOffer = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();



}