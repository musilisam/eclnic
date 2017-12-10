package com.example.sam.e_clinic;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.example.sam.e_clinic.book.cordinates;

public class PlaceViewer extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_viewer);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {

            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);

        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }


    }

    Double lat, longk;
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {

        if (requestCode == PLACE_PICKER_REQUEST)
        {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, PlaceViewer.this);


                String toastMsg = String.format("Place selected:  %s", place.getName());
             //   Toast.makeText(this, toastMsg, Toast.LENGTH_SHORT).show();
                cordinates=String.valueOf(place.getName());
                LatLng  latLng = place.getLatLng();
                lat= latLng.latitude;
                longk = latLng.longitude;

                new AlertDialog.Builder(PlaceViewer.this)
                        .setMessage("Selected place " +String.valueOf(cordinates))
                        .setPositiveButton
                                ("Okay", new DialogInterface.OnClickListener()
                                        {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                                PlaceViewer.this.finish();

                                                 startActivity(new Intent(PlaceViewer.this, book.class));
                                            }
                                        }
                                ).show();



            }
        }







    }
    String place_name;

    public void location_name(final Double lat,final Double longk)
    {

        class TestAsync extends AsyncTask<Void, Integer, String> {

            ProgressDialog progressDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog= new ProgressDialog(PlaceViewer.this);
                progressDialog.setMessage("Fetching location..");
                progressDialog.show();


            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();
                Toast.makeText(PlaceViewer.this, s, Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(PlaceViewer.this)
                        .setMessage("" +String.valueOf(place_name))
                        .setPositiveButton
                                ("Okay", new DialogInterface.OnClickListener()
                                        {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                                //PlaceViewer.this.finish();
                                                //
                                                // startActivity(new Intent(PlaceViewer.this, book.class));
                                            }
                                        }
                                ).show();
            }

            @Override
            protected String doInBackground(Void... voids)
            {


                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                String provider = locationManager.getBestProvider(new Criteria(), true);

                if (ActivityCompat.checkSelfPermission(PlaceViewer.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(PlaceViewer.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return "";
                }
                Location locations = locationManager.getLastKnownLocation(provider);
                List<String> providerList = locationManager.getAllProviders();
                if(null!=locations && null!=providerList && providerList.size()>0){
//                     longitude = locations.getLongitude();
//                     latitude = locations.getLatitude();
                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    try {
                        List<Address> listAddresses = geocoder.getFromLocation(lat, longk, 1);
                        if(null!=listAddresses&&listAddresses.size()>0)
                        {
                            place_name = listAddresses.get(0).getAddressLine(0);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                   // Toast.makeText(PlaceViewer.this, place_name, Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                return place_name;
            }
        }

        new TestAsync().execute();

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
//        mMap = googleMap;
//
//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
