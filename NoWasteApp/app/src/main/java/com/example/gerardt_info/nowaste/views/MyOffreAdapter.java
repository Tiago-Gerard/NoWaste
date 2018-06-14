/*
 * Projet  : No Waste
 * Auteur  : Tiago Gerard
 * Version : 1.0
 * Fichier : MyOffreAdapter
 * */
package com.example.gerardt_info.nowaste.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.example.gerardt_info.nowaste.R;
import com.example.gerardt_info.nowaste.models.MyOffer;
import com.example.gerardt_info.nowaste.models.Offre;

import java.util.List;

public class MyOffreAdapter extends RecyclerView.Adapter<MyOfferViewHolder>{

    private List<MyOffer> offres;
    private RequestManager glide;

    public MyOffreAdapter(List<MyOffer> offres, RequestManager glide,Listener callback) {
        this.offres = offres;
        this.glide = glide;
        this.callback = callback;
    }
    public interface Listener {
        void onClickDeleteButton(int position);
    }
    private final Listener callback;

    @NonNull
    @Override
    public MyOfferViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.itemmesoffres, parent, false);

        return new MyOfferViewHolder(view);
    }



    @Override
    public void onBindViewHolder(MyOfferViewHolder holder, int position) {
        holder.updateWithOffer(this.offres.get(position), this.glide,this.callback);
    }

    @Override
    public int getItemCount() {
        return this.offres.size();
    }

    public MyOffer getMyOffer(int position){
        return this.offres.get(position);
    }


}

