/*
 * Projet  : No Waste
 * Auteur  : Tiago Gerard
 * Version : 1.0
 * Fichier : Outils.java
 * */
package com.example.gerardt_info.nowaste.Data;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Outils {

    static private String telephone;
    static private String pass;


    // set les headers pour les envoyer au serveur
    static public void setHeaders(String telephone,String pass){
        Outils.telephone= telephone;
        Outils.pass=pass;
    }
    static public OkHttpClient getOkHttpClient(){
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();

                Request.Builder builder = originalRequest.newBuilder().header("Authorization",
                        Credentials.basic(Outils.telephone,Outils.pass ));

                Request newRequest = builder.build();
                return chain.proceed(newRequest);
            }
        }).build();
        return okHttpClient;
    }
}
