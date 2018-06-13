package com.example.gerardt_info.nowaste.controleurs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gerardt_info.nowaste.Data.ServiceGetOffreByType;
import com.example.gerardt_info.nowaste.Data.ServiceOffre;
import com.example.gerardt_info.nowaste.R;
import com.example.gerardt_info.nowaste.models.Offre;
import com.example.gerardt_info.nowaste.views.OffreAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentOffer extends Fragment implements ServiceOffre.Callbacks,ServiceGetOffreByType.Callbacks{
    @BindView(R.id.main_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.swipe) SwipeRefreshLayout swipeRefreshLayout;

    private List<Offre> offres;
    private OffreAdapter adapter;

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    private Double latitude;

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    private Double longitude;

    public void setIdUtilisateur(String idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    private String idUtilisateur;

    public void setIdType(String idType) {
        this.idType = idType;
    }

    private String idType;
    public FragmentOffer() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this,view);
        this.configureRecyclerView();
        this.configureSwipeRefreshLayout();
        this.executeHttpRequestWithRetrofit();
        return view;
    }

    public void executeHttpRequestWithRetrofit() {
        if(idType!=null){
            ServiceGetOffreByType.getOffresByType(this,latitude,longitude,idUtilisateur,idType);
        }
        else {
            ServiceOffre.getOffres(this,latitude,longitude,idUtilisateur);
        }

    }

    private void configureSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                executeHttpRequestWithRetrofit();
            }
        });
    }
    public void delete(){
        getFragmentManager().beginTransaction().remove(this).commitAllowingStateLoss();
    }
    private void configureRecyclerView() {
        this.offres = new ArrayList<>();
        this.adapter = new OffreAdapter( this.offres, Glide.with(this));
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }




    private void updateUI(List<Offre> offres){
        this.offres.clear();
        this.offres.addAll(offres);
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);

        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_animation_fall_down);

        this.recyclerView.setLayoutAnimation(controller);
        this.recyclerView.scheduleLayoutAnimation();
    }



    @Override
    public void onResponse(List<Offre> offres) {
        updateUI(offres);
    }

    @Override
    public void onFailure() {
        Log.e("ERROR ", "Service Error");
    }
}

