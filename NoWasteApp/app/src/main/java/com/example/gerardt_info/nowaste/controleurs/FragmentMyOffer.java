package com.example.gerardt_info.nowaste.controleurs;

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

import com.bumptech.glide.Glide;
import com.example.gerardt_info.nowaste.Data.ServiceDeleteMyOffer;
import com.example.gerardt_info.nowaste.Data.ServiceMyOffre;
import com.example.gerardt_info.nowaste.R;
import com.example.gerardt_info.nowaste.Data.utils.ItemClickSupport;
import com.example.gerardt_info.nowaste.models.MyOffer;
import com.example.gerardt_info.nowaste.views.MyOffreAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentMyOffer extends Fragment implements ServiceMyOffre.Callbacks,MyOffreAdapter.Listener,ServiceDeleteMyOffer.Callbacks{
    @BindView(R.id.main_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.swipe) SwipeRefreshLayout swipeRefreshLayout;

    private List<MyOffer> offres;
    private MyOffreAdapter adapter;

    public void setIdUtilisateur(String idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    private String idUtilisateur;

    public FragmentMyOffer() {

    }
    public void delete(){
        getFragmentManager().beginTransaction().remove(this).commitAllowingStateLoss();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this,view);
        this.configureRecyclerView();
        this.configureSwipeRefreshLayout();
        this.executeHttpRequestWithRetrofit();
        configureOnClickRecyclerView();
        return view;
    }

    private void executeHttpRequestWithRetrofit() {

        ServiceMyOffre.getOffres(this,idUtilisateur);
    }

    private void configureSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                executeHttpRequestWithRetrofit();
            }
        });
    }

    private void configureRecyclerView() {
        this.offres = new ArrayList<>();
        this.adapter = new MyOffreAdapter( this.offres, Glide.with(this),this);
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void configureOnClickRecyclerView() {
        ItemClickSupport.addTo(recyclerView, R.layout.item).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                MyOffer offre = adapter.getMyOffer(position);
                ((activity_home)getActivity()).updateOffer(offre);


            }
        });
    }
        @Override
        public void onClickDeleteButton(int position) {
            MyOffer offre = adapter.getMyOffer(position);
            //Toast.makeText(getContext(), "You are trying to delete user : "+user.getLogin(), Toast.LENGTH_SHORT).show();
            ServiceDeleteMyOffer.deleteOffer(this,offre.getId());

        }

    private void updateUI(List<MyOffer> offres){
        this.offres.clear();
        this.offres.addAll(offres);
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);

        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_animation_fall_down);

        this.recyclerView.setLayoutAnimation(controller);
        this.recyclerView.scheduleLayoutAnimation();
    }

    @Override
    public void onResponse(List<MyOffer> offres)
    {
            updateUI(offres);
    }

    @Override
    public void onResponse(Boolean response) {
            executeHttpRequestWithRetrofit();
    }

    @Override
    public void onFailure() {
        Log.e("ERROR ", "Service Error");
    }
}

