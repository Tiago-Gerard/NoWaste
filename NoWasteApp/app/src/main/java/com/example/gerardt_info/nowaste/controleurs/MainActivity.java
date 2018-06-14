/*
 * Projet  : No Waste
 * Auteur  : Tiago Gerard
 * Version : 1.0
 * Fichier : MainActivity.java
 * */
package com.example.gerardt_info.nowaste.controleurs;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gerardt_info.nowaste.Data.ServiceCreateUser;
import com.example.gerardt_info.nowaste.Data.ServiceGetUser;
import com.example.gerardt_info.nowaste.Data.ServiceOffre;
import com.example.gerardt_info.nowaste.R;
import com.example.gerardt_info.nowaste.models.Offre;
import com.example.gerardt_info.nowaste.models.Utilisateur;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ServiceGetUser.Callbacks, ServiceCreateUser.Callbacks {
    String number=null;
    static int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE=1;
    EditText editNumero;
    EditText editPrenom;
    EditText editNom;
    EditText editEmail;

    //constructeur du controleur de la vue de création de compte
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        checkPermission();
        Log.e("numero de telephone",number);
        editNumero = findViewById(R.id.editNumero);
        editEmail = findViewById(R.id.editEmail);
        editNom = findViewById(R.id.editNom);
        editPrenom=findViewById(R.id.editPrenom);
        editNumero.setText(number);
        ServiceGetUser.getUser(this,number,number);
    }





    private void checkPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_PHONE_STATE)) {
            }
            else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
            }
        }
        else {
            // Permission has already been granted
            TelephonyManager tMgr = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
            number = tMgr.getLine1Number();

        }
        }
        public void Onclick(View v){
        if(number.isEmpty()){
            Toast.makeText(this, "L'application n'arrive pas à récuperer votre numéro", Toast.LENGTH_LONG).show();
        }
        else{
            ServiceCreateUser.createUser(this,editPrenom.getText().toString(),editNom.getText().toString(),editEmail.getText().toString(),editNumero.getText().toString());
            ServiceGetUser.getUser(this,number,number);
        }
        }
    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    TelephonyManager tMgr = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
                    number = tMgr.getLine1Number();
                    ServiceGetUser.getUser(this,number,number);

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Log.e("askPermission","permission denied");
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
    //la reponse du serveur avec un utilisateur si un compte correspond au numéro envoyé
    @Override
    public void onResponse(List<Utilisateur> utilisateurs) {
        if(utilisateurs.size()!=0){
            Intent intent = new Intent(this,activity_home.class);
            intent.putExtra("Utilisateur",utilisateurs.get(0));
            startActivity(intent);
        }

    }

    @Override
    public void onFailure() {
        Log.e("sdk","error Service");
    }
}
