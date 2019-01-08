package com.example.rohilscomputer.finalproject;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Vector;

public class GameActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    private MySurfaceView surface;

    private MapView mapView;
    private GoogleMap gMap;
    private LocationManager locationManager;

    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private final int PERMISSION_REQUEST = 1;
    private final int mapRadius = 400; //radius around player

    private Vector<bossFighter> bosses;
    private Vector<TChest> chests;
    private Vector<Marker> bossMarkers;
    private Vector<Marker> chestMarkers;

    private User current;

    private int bossFight = -1;
    private int chestFound = -1;

    private userDBHelper myDb;

    bossFighter bossFighter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDb = new userDBHelper(this);

        setContentView(R.layout.activity_game);

        getSupportActionBar().hide();

        bosses = new Vector<>();
        chests = new Vector<>();
        bossMarkers = new Vector<>();
        chestMarkers = new Vector<>();

        bossFighter = new bossFighter("Randy", 100, 10, 43.95, -78.89);
        bosses.add(bossFighter);
        //myDb.insertBossFighters(boss);

        TChest treasureChest = new TChest(43.945139 , -78.896108, 400, 25);
        chests.add(treasureChest);
        //myDb.insertChest(treasureChest);

        current = new User("test", "pass", "pass", "email", "email");
        //myDb.insertUser(current);


        Bundle mapViewBundle = null;
        if(savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        mapView = findViewById(R.id.mapview);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

        surface = new MySurfaceView(this);

        mapView.addView(surface);
    }


    public void attackOnePressed(View view) throws InterruptedException {


        Attack attack = new Attack(surface.renderer);
        System.out.println(bossFighter.getCombatHealth());
        attack.lightAttack(bossFighter,current);
        System.out.println(bossFighter.getCombatHealth());
        attack.bossAttack(bossFighter,current);
        System.out.println( "user health " + current.getCombatHealth());
        if(attack.isFightOver(bossFighter,current))
        {
            boolean result = attack.didUserWin(bossFighter,current);

            if (result)  bossMarkers.get(bosses.indexOf(bossFighter)).remove();
        }


        surface.renderer.player.animator.PlayAnimation(0);
        surface.renderer.player.color = new float[] {1.0f, 0.0f, 0.0f, 1.0f};
    }

    public void attackTwoPressed(View view) throws InterruptedException {

        Attack attack = new Attack(surface.renderer);
        attack.heavyAttack(bossFighter,current);
        System.out.println("Boss Health " + bossFighter.getCombatHealth());
        attack.bossAttack(bossFighter,current);
        System.out.println( "user health " + current.getCombatHealth());

        if(attack.isFightOver(bossFighter,current))
        {
            boolean result = attack.didUserWin(bossFighter,current);

            if (result)  bossMarkers.get(bosses.indexOf(bossFighter)).remove();
        }

        surface.renderer.player.animator.PlayAnimation(1);
        surface.renderer.player.color = new float[] {0.0f, 0.0f, 1.0f, 1.0f};
    }

    @Override
    protected void onPause() {
        super.onPause();
        surface.onPause();
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        surface.onResume();
        mapView.onResume();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if(mapViewBundle == null)
        {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setMinZoomPreference(12);
        LatLng ny = new LatLng(41, -74);
        gMap.moveCamera(CameraUpdateFactory.newLatLng(ny));

        gMap.getUiSettings().setZoomControlsEnabled(true);
        gMap.getUiSettings().setTiltGesturesEnabled(true);
        gMap.getUiSettings().setMyLocationButtonEnabled(true);

        for (bossFighter boss : bosses) {
            LatLng pos = new LatLng(boss.getBossLatitude(), boss.getBossLongitude());
            bossMarkers.add(gMap.addMarker(new MarkerOptions().position(pos).title(boss.getBossName())));
        }

        for (TChest chest : chests) {
            LatLng pos = new LatLng(chest.getTCLatitude(), chest.getTCLongitude());
            chestMarkers.add(gMap.addMarker(new MarkerOptions().position(pos).title("Treasure Chest")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))));
        }

        setupLocationListener();
    }



    public void setupLocationListener() {
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST);
            }
            gMap.setMyLocationEnabled(true);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 1, this);
            Location last = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(last != null)
            {
                onLocationChanged(last);
            }
        } catch (SecurityException e) {
            e.getMessage();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST) {
            if (grantResults.length <= 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Log.d("GPS", "Error getting permissions");
            }
        }
    }



    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onLocationChanged(Location location) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        LatLng newPos = new LatLng(lat, lng);


        surface.renderer.updateCameraPos(lat, lng);

        final CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(newPos);
        gMap.animateCamera(cameraUpdate);

        bossFight = checkImmediateRadius(newPos);

        if (bossFight != -1) {
            String bossText = "Fighting " + bosses.get(bossFight).getBossName();
            Toast fight = android.widget.Toast.makeText(GameActivity.this, bossText, Toast.LENGTH_LONG);
            fight.show();
            double [] coordinates = new double[]{bosses.get(bossFight).getBossLatitude(),bosses.get(bossFight).getBossLongitude()};
            bossFighter sendInfo = bosses.get(bossFight);
            Combat combat = new Combat(current, sendInfo);


        } //start a fight with the nearby boss

        else if ((chestFound = checkImmediateRadiusChest(newPos)) != -1) {
            //open chest
            String discovery = "Found chest with " + chests.get(chestFound).getTCExp() +
                               " Exp and " + chests.get(chestFound).getTCCurrency() + " currency.";

            Toast open = android.widget.Toast.makeText(GameActivity.this,discovery,
                                                        android.widget.Toast.LENGTH_LONG);
            open.show();


            TChest found = chests.get(chestFound);

            chestMarkers.get(chestFound).remove();

            current.setUserTotalEXP(current.getUserTotalEXP() + found.getTCExp());
            current.setExpNeeded(current.getExpNeeded() - found.getTCExp());
        }

    }

    public int checkImmediateRadius(LatLng position) {
        double lat1 = position.latitude;
        double lon1 = position.longitude;
        float[] results = {0};

        for (bossFighter boss : bosses) {
            double lat2 = boss.getBossLatitude();
            double lon2 = boss.getBossLongitude();

            Location.distanceBetween(lat1, lon1, lat2, lon2, results);
            System.out.println(results[0]);
            if (results[0] <= mapRadius) return bosses.indexOf(boss);
        }

        return -1;
    }

    public int checkImmediateRadiusChest(LatLng position) {
        double lat1 = position.latitude;
        double lon1 = position.longitude;
        float[] results = {0};

        for (TChest chest : chests) {
            double lat2 = chest.getTCLatitude();
            double lon2 = chest.getTCLongitude();

            Location.distanceBetween(lat1, lon1, lat2, lon2, results);
            System.out.println(results[0]);
            if (results[0] <= mapRadius) return chests.indexOf(chest);
        }

        return -1;
    }



    public boolean didUserWin(int userHealth, int bossHealth)
    {
        if(bossHealth <= 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }



}
