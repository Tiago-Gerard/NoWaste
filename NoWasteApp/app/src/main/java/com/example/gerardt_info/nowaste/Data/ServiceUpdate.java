
/*
 * Projet  : No Waste
 * Auteur  : Tiago Gerard
 * Version : 1.0
 * Fichier : ServiceUpdate.java
 * */
package com.example.gerardt_info.nowaste.Data;

import android.util.Log;

import com.example.gerardt_info.nowaste.models.Utilisateur;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class ServiceUpdate {

    // interface de listener pour récuperer les reponses serveurs
    public interface Callbacks{
        void onResponse(Boolean utilisateurs);
        void onFailure();
}

    public static void updateUser(ServiceUpdate.Callbacks callback,String prenom,String nom,String email,String numero,String idUtilisateur){
        final WeakReference<ServiceUpdate.Callbacks> callbacksWeakReference = new WeakReference<ServiceUpdate.Callbacks>(callback);

        AccesService accesService = AccesService.retrofitCreateUser.create(AccesService.class);

        retrofit2.Call<Boolean> call = accesService.updateUser(idUtilisateur,email,nom,numero,prenom);

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
