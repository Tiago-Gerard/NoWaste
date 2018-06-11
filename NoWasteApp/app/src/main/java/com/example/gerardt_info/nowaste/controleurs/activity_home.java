package com.example.gerardt_info.nowaste.controleurs;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gerardt_info.nowaste.Data.ServiceCreateOffer;
import com.example.gerardt_info.nowaste.Data.ServiceGetType;
import com.example.gerardt_info.nowaste.R;
import com.example.gerardt_info.nowaste.metier.GPS;
import com.example.gerardt_info.nowaste.models.Type;
import com.example.gerardt_info.nowaste.models.Utilisateur;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
public class activity_home extends AppCompatActivity implements LocationListener,ServiceCreateOffer.Callbacks,ServiceGetType.Callbacks {

    private TextView mTextMessage;
    private FragmentMain fragmentMain;
    private static Context instance;
    private Utilisateur utilisateu;
    private Menu menu;
    private static final int SELECT_PICTURE = 1;
    private LocationManager lm;
    private List<Address> a;
    private Geocoder g;
    private ProgressBar pb;
    private Utilisateur utilisateur;
    private EditText editAdress;
    private EditText editPostal;
    private EditText editDesc;
    private EditText editDatePeremption;
    private Spinner sp;
    private String path;
    private List<Type> types;
    private Double latitude=0.0;
    private Double longitude=0.0;


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
        utilisateur = (Utilisateur) getIntent().getSerializableExtra("Utilisateur");

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
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.create_offer:
                setContentView(R.layout.create_offre);
                getSupportActionBar().hide();
                pb=findViewById(R.id.pbLocalisation);
                pb.setVisibility(View.INVISIBLE);
                editAdress = findViewById(R.id.editAdress);
                editPostal = findViewById(R.id.editCodePostal);
                sp = findViewById(R.id.spinner2);
                editDatePeremption=findViewById(R.id.editDate);
                editDesc = findViewById(R.id.editDesc);
                ServiceGetType.getType(this);
                return true;
            case R.id.change_acc:
                setContentView(R.layout.modifi_cationcompte);
                getSupportActionBar().hide();
                return true;
            case R.id.filter:
                setContentView(R.layout.activity_filtre);
                getSupportActionBar().hide();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void OnclickImage(View v){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 0);
    }

    @SuppressLint("MissingPermission")
    public void onClickButtonLoc(View v) {
        pb.setVisibility(View.VISIBLE);
        final Handler h = new Handler();
        a = new ArrayList<>();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(longitude!=0.0&&latitude!=0.0){
                    h.removeCallbacks(this);
                    g = new Geocoder(getApplicationContext(), Locale.getDefault());
                    try {
                        a = g.getFromLocation(latitude, longitude, 1);
                        String[] adresse = new String[3];
                        adresse = a.get(0).getAddressLine(0).split(",");
                        editAdress.setText(adresse[0].toString());
                        editPostal.setText(adresse[1].toString());

                        pb.setVisibility(View.INVISIBLE);
                        //stopGPS();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    latitude = GPS.gps(getApplicationContext()).getLatitude();
                    longitude = GPS.gps(getApplicationContext()).getLongitude();
                    h.postDelayed(this,100);
                }

            }
        };
        runnable.run();
    }


    private void stopGPS(){
        lm.removeUpdates(this);
    }

    public void OnClickAdd(View v){
        addOfferOnServeur(path,latitude.toString(),longitude.toString(),editDatePeremption.getText().toString(),editDesc.getText().toString(),getIdTyoeFromText(),utilisateur.getIdUtilisateur());

    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                ImageView img = findViewById(R.id.imgOffre);
                img.setImageBitmap(selectedImage);

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            1);
                    path = getRealPathFromURI(imageUri);
                }
                //UploadService.uploadImage(this, getRealPathFromURI(data.getData()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
               // Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            //Toast.makeText(LoginActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }
    public String getRealPathFromURI(Uri contentUri) {

        // can post image
        String [] proj={MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery( contentUri,
                proj, // Which columns to return
                null,       // WHERE clause; which rows to return (all rows)
                null,       // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }
    private void addOfferOnServeur(String path,String latitude,String longitude, String datePeremption,String description,String idType,String idUtilisateur){
        ServiceCreateOffer.createUser(this,description,datePeremption,idType,longitude,latitude,path,idUtilisateur);
    }

    @Override
    public void onResponse(Boolean bool) {

    }

    @Override
    public void onResponse(List<Type> types) {
        this.types = types;
        setSpinner();
    }

    @Override
    public void onFailure() {

    }
    private void setSpinner() {
        // you need to have a list of data that you want the spinner to display
        List<String> spinnerArray = new ArrayList<String>();
        for (Type t:types)
        {
            spinnerArray.add(t.getNom());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) sp;
        sItems.setAdapter(adapter);
    }
    private String getIdTyoeFromText(){
        String text = sp.getSelectedItem().toString();
        for (Type t:types)
        {
            if(t.getNom()==text){
                return t.getIdType();
            }

        }
        return null;
    }
}
