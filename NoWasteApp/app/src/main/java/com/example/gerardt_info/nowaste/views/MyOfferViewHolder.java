package com.example.gerardt_info.nowaste.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.example.gerardt_info.nowaste.R;
import com.example.gerardt_info.nowaste.models.MyOffer;
import com.example.gerardt_info.nowaste.models.Offre;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;


/*
 * Projet  : No Waste
 * Auteur  : Tiago Gerard
 * Version : 1.0
 * Fichier : MyOfferViewHolder
 * */
public class MyOfferViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


    //associe les élements de la vue avec leur instance en java
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

    @BindView(R.id.imgBtnDelete)
    ImageButton imageButton;
    private WeakReference<MyOffreAdapter.Listener> callbackWeakRef;

    public MyOfferViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);

    }

    //met à jours une card avec une offre
    public void updateWithOffer(MyOffer offre, RequestManager glide,MyOffreAdapter.Listener callback){
        this.txvName.setText(offre.getPrenom());
        this.txvNum.setText(offre.getContact());
        this.txvDate.setText(offre.getDate());
        this.txvDesc.setText(offre.getDescription());
        imageButton.setOnClickListener(this);
        glide.load("http://10.134.97.230/nowaste/service/img/"+offre.getLien()).into(imgOffre);
        callbackWeakRef = new WeakReference<MyOffreAdapter.Listener>(callback);
    }



    @Override
    public void onClick(View v) {
        MyOffreAdapter.Listener callback = callbackWeakRef.get();
        if (callback != null) {
            callback.onClickDeleteButton(getAdapterPosition());
        }
    }
}
