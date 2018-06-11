package com.example.gerardt_info.nowaste.Data;

import android.app.Service;
import android.util.Log;

import com.example.gerardt_info.nowaste.models.Offre;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class ServiceOffre {

    public interface Callbacks{
        void onResponse(List<Offre> offres);
        void onFailure();
    }

    public static void getOffres(ServiceOffre.Callbacks callback,double latitude,double longitude){
        final WeakReference<ServiceOffre.Callbacks> callbacksWeakReference = new WeakReference<ServiceOffre.Callbacks>(callback);



        AccesService accesService = AccesService.retrofitGetOffre.create(AccesService.class);

        retrofit2.Call<List<Offre>> call = accesService.getOffre(latitude,longitude);

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

