package com.example.gerardt_info.nowaste.Data;

import android.util.Log;

import com.example.gerardt_info.nowaste.models.Utilisateur;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceCreateOffer {public interface Callbacks{
    void onResponse(Boolean bool );
    void onFailure();
}

    public static void createUser(ServiceCreateOffer.Callbacks callback,String description,String datePeremption,String idType,String longitude,String latitude,String path,String idUtilisateur){
        File file = new File(path);
        final RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), reqFile);
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "image");

        final WeakReference<ServiceCreateOffer.Callbacks> callbacksWeakReference = new WeakReference<ServiceCreateOffer.Callbacks>(callback);

        AccesService accesService = AccesService.retrofitCreateOffer.create(AccesService.class);

        retrofit2.Call<Boolean> call = accesService.createOffer(body,name,description,datePeremption,latitude,longitude,idUtilisateur,idType);

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


