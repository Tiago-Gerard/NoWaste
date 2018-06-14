/*
 * Projet  : No Waste
 * Auteur  : Tiago Gerard
 * Version : 1.0
 * Fichier : OfferViewHolder
 * */
package com.example.gerardt_info.nowaste.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.gerardt_info.nowaste.R;
import com.example.gerardt_info.nowaste.models.Offre;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;


public class OfferViewHolder extends RecyclerView.ViewHolder{

    //associe les élements de la vue avec leur instance en java
    @BindView(R.id.txvDistance)
    TextView txvDistance;
    @BindView(R.id.txvName)
    TextView txvName;
    @BindView(R.id.txvDesc)
    TextView txvDesc;
    @BindView(R.id.txvNum)
    TextView txvNum;
    @BindView(R.id.txvDate)
    TextView txvDate;
    @BindView(R.id.imgOffre)
    ImageView imgOffre;


    public OfferViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);

    }

    //met à jours une card avec une offre
    public void updateWithOffer(Offre offre, RequestManager glide){
        this.txvName.setText(offre.getPrenom());
        this.txvNum.setText(offre.getContact());
        this.txvDate.setText(offre.getDate());
        this.txvDesc.setText(offre.getDescription());
        Double d = Double.parseDouble(offre.getDistance());
        Integer i = d.intValue();
        this.txvDistance.setText(i.toString()+" m");
        glide.load("http://10.134.97.230/nowaste/service/img/"+offre.getLien()).into(imgOffre);
    }
}
