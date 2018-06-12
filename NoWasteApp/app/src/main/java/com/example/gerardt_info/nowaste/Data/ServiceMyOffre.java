package com.example.gerardt_info.nowaste.Data;

import android.util.Log;

import com.example.gerardt_info.nowaste.models.MyOffer;
import com.example.gerardt_info.nowaste.models.Offre;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class ServiceMyOffre {public interface Callbacks{
    void onResponse(List<MyOffer> offres);
    void onFailure();
}

    public static void getOffres(ServiceMyOffre.Callbacks callback,String idUtilisateur){
        final WeakReference<ServiceMyOffre.Callbacks> callbacksWeakReference = new WeakReference<ServiceMyOffre.Callbacks>(callback);



        AccesService accesService = AccesService.retrofitGetMyOffre.create(AccesService.class);

        retrofit2.Call<List<MyOffer>> call = accesService.getMesOffres(idUtilisateur);

        call.enqueue(new Callback<List<MyOffer>>() {
            @Override
            public void onResponse(retrofit2.Call<List<MyOffer>> call, Response<List<MyOffer>> response) {

                if (callbacksWeakReference.get() != null){
                    callbacksWeakReference.get().onResponse(response.body());
                }
            }

            @Override
            public void onFailure(retrofit2.Call<List<MyOffer>> call, Throwable t) {
                Log.e("error service",String.valueOf(t.getLocalizedMessage()));
                if (callbacksWeakReference.get() != null){
                    callbacksWeakReference.get().onFailure();
                }

            }
        });
    }
}


