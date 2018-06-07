package com.example.gerardt_info.nowaste.controleurs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.gerardt_info.nowaste.R;

public class activity_home extends AppCompatActivity {

    private TextView mTextMessage;
    private FragmentMain fragmentMain;
    private static Context instance;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    configureAndShowMainFragment();
                    setTitle("Offer");
                    return true;
                case R.id.navigation_dashboard:

                    return true;
                case R.id.navigation_notifications:

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
