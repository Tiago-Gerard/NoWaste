/*
 * Projet  : No Waste
 * Auteur  : Tiago Gerard
 * Version : 1.0
 * Fichier : ServiceGetUser.java
 * */
package com.example.gerardt_info.nowaste.Data;

import android.util.Log;

import com.example.gerardt_info.nowaste.models.Offre;
import com.example.gerardt_info.nowaste.models.Utilisateur;

import java.lang.ref.WeakReference;
import java.util.List;

import okhttp3.internal.Util;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceGetUser {

    // interface de listener pour récuperer les reponses serveurs
    public interface Callbacks{
        void onResponse(List<Utilisateur> utilisateurs);
        void onFailure();
    }

    public static void getUser(ServiceGetUser.Callbacks callback,String telephone, String pass){
        final WeakReference<ServiceGetUser.Callbacks> callbacksWeakReference = new WeakReference<ServiceGetUser.Callbacks>(callback);

        Outils.setHeaders(telephone,pass);
        AccesService accesService = AccesService.retrofitUser.create(AccesService.class);

        retrofit2.Call<List<Utilisateur>> call = accesService.getUser();

        call.enqueue(new Callback<List<Utilisateur>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Utilisateur>> call, Response<List<Utilisateur>> response) {

                if (callbacksWeakReference.get() != null){
                    callbacksWeakReference.get().onResponse(response.body());
                }
            }

            @Override
            public void onFailure(retrofit2.Call<List<Utilisateur>> call, Throwable t) {
                Log.e("error service",t.getMessage());
                if (callbacksWeakReference.get() != null){
                    callbacksWeakReference.get().onFailure();
                }

            }
        });
    }
}
