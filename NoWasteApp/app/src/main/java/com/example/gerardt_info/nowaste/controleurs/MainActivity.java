package com.example.gerardt_info.nowaste.controleurs;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.gerardt_info.nowaste.Data.ServiceOffre;
import com.example.gerardt_info.nowaste.R;
import com.example.gerardt_info.nowaste.models.Offre;

import java.util.List;

public class MainActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String mPhoneNumber;
        getSupportActionBar().hide();

        TelephonyManager tMgr = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        /*if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            mPhoneNumber = tMgr.getLine1Number();
            Log.e("phone number",mPhoneNumber.toString());

            return;
        }*/
        Intent intent = new Intent(this,activity_home.class);
        startActivity(intent);
    }


}
