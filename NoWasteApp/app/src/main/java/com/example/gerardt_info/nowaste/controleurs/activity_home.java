package com.example.gerardt_info.nowaste.controleurs;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.gerardt_info.nowaste.R;
import com.example.gerardt_info.nowaste.models.Utilisateur;

public class activity_home extends AppCompatActivity {

    private TextView mTextMessage;
    private FragmentMain fragmentMain;
    private static Context instance;
    private Utilisateur utilisateu;
    private Menu menu;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    configureAndShowMainFragment();
                    setTitle("Offer");
                    menu.findItem(R.id.change_acc).setVisible(false);
                    menu.findItem(R.id.filter).setVisible(true);
                    menu.findItem(R.id.create_offer).setVisible(false);

                    return true;
                case R.id.navigation_dashboard:
                    menu.findItem(R.id.change_acc).setVisible(false);
                    menu.findItem(R.id.filter).setVisible(false);
                    menu.findItem(R.id.create_offer).setVisible(true);

                    return true;
                case R.id.navigation_notifications:
                    menu.findItem(R.id.change_acc).setVisible(true);
                    menu.findItem(R.id.filter).setVisible(false);
                    menu.findItem(R.id.create_offer).setVisible(false);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.menu, menu);
        this.menu = menu;

        return true;
    }
    private void configureAndShowMainFragment() {
        fragmentMain = (FragmentMain) getSupportFragmentManager().findFragmentById(R.id.recycler_container);

        if (fragmentMain == null){
            fragmentMain = new FragmentMain();
            getSupportFragmentManager().beginTransaction().add(R.id.recycler_container, fragmentMain).commit();

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){


        }

        return super.onOptionsItemSelected(item);
    }

}
