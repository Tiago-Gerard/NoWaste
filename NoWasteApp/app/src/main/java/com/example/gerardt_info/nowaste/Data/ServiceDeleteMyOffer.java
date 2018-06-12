package com.example.gerardt_info.nowaste.Data;

import android.util.Log;

import com.example.gerardt_info.nowaste.models.Utilisateur;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class ServiceDeleteMyOffer {
    public interface Callbacks{
        void onResponse(Boolean response);
        void onFailure();
    }

    public static void deleteOffer(ServiceDeleteMyOffer.Callbacks callback,String idOffre){
        final WeakReference<ServiceDeleteMyOffer.Callbacks> callbacksWeakReference = new WeakReference<ServiceDeleteMyOffer.Callbacks>(callback);


        AccesService accesService = AccesService.retrofitDeleteMyOffer.create(AccesService.class);

        retrofit2.Call<Boolean> call = accesService.deleteMyOffre(idOffre);

        call.enqueue(new Callback<Boolean>() {
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
