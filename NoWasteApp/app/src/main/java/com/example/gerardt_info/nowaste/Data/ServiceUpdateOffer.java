/*
 * Projet  : No Waste
 * Auteur  : Tiago Gerard
 * Version : 1.0
 * Fichier : ServiceUpdateOffer.java
 * */
package com.example.gerardt_info.nowaste.Data;

import android.util.Log;

import java.io.File;
import java.lang.ref.WeakReference;
;
import retrofit2.Response;

public class ServiceUpdateOffer {

    // interface de listener pour récuperer les reponses serveurs
    public interface Callbacks{
        void onResponse(Boolean bool );
        void onFailure();
}

    public static void updateOffer(ServiceCreateOffer.Callbacks callback,String idOffer,String description,String datePeremption,String idType,double longitude,double latitude){

        final WeakReference<ServiceCreateOffer.Callbacks> callbacksWeakReference = new WeakReference<ServiceCreateOffer.Callbacks>(callback);

        AccesService accesService = AccesService.retrofitUpdateOffer.create(AccesService.class);

        retrofit2.Call<Boolean> call = accesService.updateOffer(idOffer,description,datePeremption,latitude,longitude,idType);

        call.enqueue(new retrofit2.Callback<Boolean>() {
            @Override
            public void onResponse(retrofit2.Call<Boolean> call, Response<Boolean> response) {

                if (callbacksWeakReference.get() != null){
                    callbacksWeakReference.get().onResponse(response.body());
                }
            }

            @Override
            public void onFailure(retrofit2.Call<Boolean> call, Throwable t) {
                Log.e("error service",t.getMessage());
                if (callbacksWeakReference.get() != null){
                    callbacksWeakReference.get().onFailure();
                }

            }
        });
    }
}


