/*
 * Projet  : No Waste
 * Auteur  : Tiago Gerard
 * Version : 1.0
 * Fichier : OffreAdapter
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
import com.example.gerardt_info.nowaste.models.Offre;
import com.example.gerardt_info.nowaste.views.OfferViewHolder;

import java.util.List;

public class OffreAdapter  extends RecyclerView.Adapter<OfferViewHolder>{

    private List<Offre> offres;
    private RequestManager glide;

    public OffreAdapter( List<Offre> offres, RequestManager glide) {
        this.offres = offres;
        this.glide = glide;
    }


    @NonNull
    @Override
    public OfferViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item, parent, false);
        return new OfferViewHolder(view);
    }



    @Override
    public void onBindViewHolder(OfferViewHolder holder, int position) {
        holder.updateWithOffer(this.offres.get(position), this.glide);
    }


    @Override
    public int getItemCount() {
        return this.offres.size();
    }

    public Offre getOffre(int position){
        return this.offres.get(position);
    }

}

