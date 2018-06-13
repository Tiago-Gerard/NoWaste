package com.example.gerardt_info.nowaste.controleurs;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gerardt_info.nowaste.Data.ServiceCreateOffer;
import com.example.gerardt_info.nowaste.Data.ServiceGetOffreByType;
import com.example.gerardt_info.nowaste.Data.ServiceGetType;
import com.example.gerardt_info.nowaste.Data.ServiceUpdate;
import com.example.gerardt_info.nowaste.Data.ServiceUpdateOffer;
import com.example.gerardt_info.nowaste.R;
import com.example.gerardt_info.nowaste.Data.utils.GPS;
import com.example.gerardt_info.nowaste.models.MyOffer;
import com.example.gerardt_info.nowaste.models.Offre;
import com.example.gerardt_info.nowaste.models.Type;
import com.example.gerardt_info.nowaste.models.Utilisateur;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
public class activity_home extends AppCompatActivity implements LocationListener,ServiceCreateOffer.Callbacks,ServiceGetType.Callbacks,ServiceUpdate.Callbacks {

    private TextView mTextMessage;
    private FragmentOffer fragmentMain;
    private FragmentMyOffer fragmentMainMyOffer;
    private static Context instance;
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
    private EditText editNom;
    private EditText editPrenom;
    private EditText editEmail;
    private EditText editNumero;
    private Spinner sp;
    private String path;
    private ProgressBar pgrsLoading;
    private List<Type> types;
    static private Double latitude=0.0;
    static private Double longitude=0.0;
    private ImageView img;
    boolean create = true;
    boolean filter = true;
    MyOffer myOffer;
    private String idType;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    offerNav();
                    menu.findItem(R.id.filter).setVisible(true);
                    return true;
                case R.id.navigation_dashboard:
                    myOfferNav();
                    menu.findItem(R.id.filter).setVisible(false);
                    return true;
            }
            return false;
        }
    };
    private void myOfferNav(){
        setTitle("Mes Offre");

        deleteMainFragment();
        configureAndShowMainFragmentMyOffer();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        instance = this;
        pgrsLoading = findViewById(R.id.pgrBar);
        utilisateur = (Utilisateur) getIntent().getSerializableExtra("Utilisateur");

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        offerNav();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.menu, menu);
        this.menu = menu;

        return true;
    }
    private void configureAndShowMainFragment() {

        fragmentMain = (FragmentOffer) getSupportFragmentManager().findFragmentById(R.id.recycler_container);

        if (fragmentMain == null){
            fragmentMain = new FragmentOffer();
            fragmentMain.setLatitude(latitude);
            fragmentMain.setLongitude(longitude);
            fragmentMain.setIdUtilisateur(utilisateur.getIdUtilisateur());
            fragmentMain.setIdType(idType);
            getSupportFragmentManager().beginTransaction().add(R.id.recycler_container, fragmentMain).commit();
            pgrsLoading.setVisibility(View.INVISIBLE);

        }
        else{
            fragmentMain.setLatitude(latitude);
            fragmentMain.setLongitude(longitude);
            fragmentMain.setIdUtilisateur(utilisateur.getIdUtilisateur());
            fragmentMain.setIdType(idType);
        }
    }
    public void updateOffer(MyOffer myOffer){
        create=false;
        this.myOffer = myOffer;
        setContentView(R.layout.create_offre);
        img = findViewById(R.id.imgOffre);

        this.editDesc = findViewById(R.id.editDesc);
        this.editDatePeremption = findViewById(R.id.editDate);
        this.editDesc.setText(myOffer.getDescription());
        this.editDatePeremption.setText(myOffer.getDate());
        this.editAdress = findViewById(R.id.editAdress);
        this.editPostal = findViewById(R.id.editCodePostal);
        Glide.with(this).load("http://10.134.97.230/nowaste/service/img/"+myOffer.getLien()).into(img);
        Geocoder geocoder;
        List<Address> addresses=null;
        geocoder = new Geocoder(this, Locale.getDefault());

        double lat = Double.parseDouble(myOffer.getLatitude());
        double lon = Double.parseDouble(myOffer.getLongitude());
        try {
            addresses = geocoder.getFromLocation(lat,lon, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        if(addresses!=null) {

            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String postalCode = addresses.get(0).getPostalCode();
            editAdress.setText(address);
            editPostal.setText(postalCode);
        }
    }
    private void deleteMainFragment(){
        if(fragmentMain!=null){
            fragmentMain.delete();
            fragmentMain=null;
        }


    }
    private void configureAndShowMainFragmentMyOffer() {

        fragmentMainMyOffer = (FragmentMyOffer) getSupportFragmentManager().findFragmentById(R.id.recycler_container_my_offer);

        if (fragmentMainMyOffer == null){
            fragmentMainMyOffer = new FragmentMyOffer();
            fragmentMainMyOffer.setIdUtilisateur(utilisateur.getIdUtilisateur());
            getSupportFragmentManager().beginTransaction().add(R.id.recycler_container_my_offer, fragmentMainMyOffer).commit();

        }
    }
    private void deleteMyOffer(){
        if(fragmentMainMyOffer!=null){
            fragmentMainMyOffer.delete();
            fragmentMainMyOffer=null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.create_offer:
                filter=false;
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
                setSetting();
                return true;
            case R.id.filter:
                filter=true;
                ServiceGetType.getType(this);


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    private void offerNav(){
        setTitle("Offre");
        deleteMyOffer();
        pgrsLoading.setVisibility(View.VISIBLE);
        getLocation();


    }

    public void OnclickImage(View v){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 0);
    }
    @SuppressLint("MissingPermission")
    public void getLocation() {
        pb=findViewById(R.id.pgrBar);
        pb.setVisibility(View.VISIBLE);
        final Handler h = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(longitude!=0.0&&latitude!=0.0){
                    h.removeCallbacks(this);
                    pb.setVisibility(View.INVISIBLE);

                    //stopGPS();
                    configureAndShowMainFragment();
                }
                else{
                    latitude = GPS.gps(instance).getLatitude();
                    longitude = GPS.gps(instance).getLongitude();
                    h.postDelayed(this,1000);
                }

            }
        };
        runnable.run();
    }
    @SuppressLint("MissingPermission")
    public void onClickButtonLoc(View v) {
        pb = findViewById(R.id.pbLocalisation);
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,activity_home.class);
        intent.putExtra("Utilisateur",utilisateur);
        startActivity(intent);
    }

    public void OnClickAdd(View v){
        Address a = convertAddress(editAdress.getText().toString()+","+editPostal.getText().toString());
        //todo recuperer les donner de la view
        if(create){
            addOfferOnServeur(path,a.getLatitude(),a.getLongitude(),editDatePeremption.getText().toString(),editDesc.getText().toString(),getIdTyoeFromText(),utilisateur.getIdUtilisateur());
        }
        else{
            ServiceUpdateOffer.updateOffer(this,myOffer.getId(),editDesc.getText().toString(),editDatePeremption.getText().toString(),myOffer.getIdType(),a.getLongitude(),a.getLatitude());
        }


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
    private Address convertAddress(String adress){
        Geocoder coder = new Geocoder(this);
        List<Address> address;
        Address location=null;
        //LatLng resLatLng = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(adress, 1);
            if (address == null) {
                return null;
            }

            if (address.size() == 0) {
                return null;
            }

            location = address.get(0);

            //resLatLng = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return location;
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
    private void addOfferOnServeur(String path,double latitude,double longitude, String datePeremption,String description,String idType,String idUtilisateur){
        ServiceCreateOffer.createUser(this,description,datePeremption,idType,longitude,latitude,path,idUtilisateur);
    }

    @Override
    public void onResponse(Boolean bool) {
            setContentView(R.layout.activity_home);
            getSupportActionBar().show();
        Intent intent = new Intent(this,activity_home.class);
        intent.putExtra("Utilisateur",utilisateur);
        startActivity(intent);
    }

    @Override
    public void onResponse(List<Type> types) {
        this.types = types;
        if(filter==false){
            setSpinner();
        }
        else{
            alertSpinner();
        }

    }


    @Override
    public void onFailure() {
        Log.e("Http request","ERROR");
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
    private void setSetting(){
        setContentView(R.layout.modifi_cationcompte);
        getSupportActionBar().hide();
        editNom = findViewById(R.id.editNom);
        editPrenom = findViewById(R.id.editPrenom);
        editEmail = findViewById(R.id.editEmail);
        editNumero = findViewById(R.id.editNumero);
        editNom.setText(utilisateur.getNom());
        editPrenom.setText(utilisateur.getPrenom());
        editEmail.setText(utilisateur.getEmail());
        editNumero.setText(utilisateur.getNumero());
    }
    public void OnClickModifie(View v){
        ServiceUpdate.updateUser(this,editPrenom.getText().toString(),editNom.getText().toString(),editEmail.getText().toString(),editNumero.getText().toString(),utilisateur.getIdUtilisateur());

    }
    public void alertSpinner() {
        AlertDialog.Builder b = new AlertDialog.Builder(this);

        b.setTitle("Filtres");
        String[] filtre =new String[types.size()];
        for (int i =0;i<types.size();i++)
        {
            filtre[i]=types.get(i).getNom();
        }
        b.setItems(filtre, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                idType = types.get(which).getIdType();
                offerNav();
                fragmentMain.executeHttpRequestWithRetrofit();

            }

        });

        b.show();
    }
}
