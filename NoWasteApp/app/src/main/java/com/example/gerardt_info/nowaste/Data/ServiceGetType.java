/*
 * Projet  : No Waste
 * Auteur  : Tiago Gerard
 * Version : 1.0
 * Fichier : ServiceGetType.java
 * */
package com.example.gerardt_info.nowaste.Data;

import android.util.Log;

import com.example.gerardt_info.nowaste.models.Type;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class ServiceGetType {

    // interface de listener pour récuperer les reponses serveurs
    public interface Callbacks{
    void onResponse(List<Type> types);
    void onFailure();
}

    public static void getType(ServiceGetType.Callbacks callback){
        final WeakReference<ServiceGetType.Callbacks> callbacksWeakReference = new WeakReference<ServiceGetType.Callbacks>(callback);

        AccesService accesService = AccesService.retrofitType.create(AccesService.class);

        retrofit2.Call<List<Type>> call = accesService.getType();

        call.enqueue(new Callback<List<Type>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Type>> call, Response<List<Type>> response) {

                if (callbacksWeakReference.get() != null){
                    callbacksWeakReference.get().onResponse(response.body());
                }
            }

            @Override
            public void onFailure(retrofit2.Call<List<Type>> call, Throwable t) {
                Log.e("error service",t.getMessage());
                if (callbacksWeakReference.get() != null){
                    callbacksWeakReference.get().onFailure();
                }

            }
        });
    }
}

