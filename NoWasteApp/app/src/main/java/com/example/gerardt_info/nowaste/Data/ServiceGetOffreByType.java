/*
 * Projet  : No Waste
 * Auteur  : Tiago Gerard
 * Version : 1.0
 * Fichier : ServiceGetOffreByType.java
 * */
package com.example.gerardt_info.nowaste.Data;

import android.util.Log;

import com.example.gerardt_info.nowaste.models.Offre;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class ServiceGetOffreByType {

    // interface de listener pour récuperer les reponses serveurs
    public interface Callbacks{
        void onResponse(List<Offre> offres);
        void onFailure();
    }

    public static void getOffresByType(ServiceGetOffreByType.Callbacks callback,double latitude,double longitude,String idUtilisateur,String idType){
        final WeakReference<ServiceGetOffreByType.Callbacks> callbacksWeakReference = new WeakReference<ServiceGetOffreByType.Callbacks>(callback);



        AccesService accesService = AccesService.retrofitGetOffreByType.create(AccesService.class);

        retrofit2.Call<List<Offre>> call = accesService.getOffreByType(latitude,longitude,idUtilisateur,idType);

        call.enqueue(new Callback<List<Offre>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Offre>> call, Response<List<Offre>> response) {

                if (callbacksWeakReference.get() != null){
                    callbacksWeakReference.get().onResponse(response.body());
                }
            }

            @Override
            public void onFailure(retrofit2.Call<List<Offre>> call, Throwable t) {
                Log.e("error service",String.valueOf(t.getLocalizedMessage()));
                if (callbacksWeakReference.get() != null){
                    callbacksWeakReference.get().onFailure();
                }

            }
        });
    }
}


