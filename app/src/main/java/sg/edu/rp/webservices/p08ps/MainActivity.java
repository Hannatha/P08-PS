package sg.edu.rp.webservices.p08ps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String[] regions = {"Select area", "North", "Central", "East"};
    private GoogleMap map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment)
                fm.findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback(){ // getMapAsync gets a callback object which will be triggered when the GoogleMap instance is ready to be used
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                LatLng poi_North = new LatLng(1.463750, 103.836524);
                Marker north = map.addMarker(new
                        MarkerOptions()
                        .position(poi_North)
                        .title("HQ - North")
                        .snippet("Block 333, Admiralty Ave 3, 765654" + "Operating hours: 10am-5pm\n" +
                                "Tel:65433456\n")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.star)));
                LatLng poi_Central = new LatLng(1.348005, 103.931594);

                Marker central = map.addMarker(new
                        MarkerOptions()
                        .position(poi_Central)
                        .title("Central")
                        .snippet( "Block 3A, Orchard Ave 3, 134542\n" + "Operating hours: 11am-8pm\n" +
                                "Tel:67788652\n")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                LatLng poi_East = new LatLng(1.303535, 103.842068);
                Marker east = map.addMarker(new
                        MarkerOptions()
                        .position(poi_East)
                        .title("East")
                        .snippet("Block 555, Tampines Ave 3, 287788 \n" + "Operating hours: 9am-5pm\n" +
                                "Tel:66776677\n")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION);

                if (permissionCheck == PermissionChecker.PERMISSION_GRANTED) {
                    map.setMyLocationEnabled(true);
                } else {
                    Log.e("GMap - Permission", "GPS access has not been granted");
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                }

                UiSettings ui = map.getUiSettings();
                ui.setZoomControlsEnabled(true);

                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        String markerName = marker.getTitle();
                        Toast.makeText(MainActivity.this, markerName, Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });
            }


        });

        Spinner spin = findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,regions);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
    }
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        if (position == 0) {
            LatLng poi_Select = new LatLng(1.3521, 103.8198);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_Select,
                    11));

        } else if (position == 1) {
            LatLng poi_North = new LatLng(1.463750, 103.836524);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_North,
                    18));
        } else if (position == 2) {
            LatLng poi_Central = new LatLng(1.348005, 103.931594);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_Central,
                    18));
        } else if (position == 3) {
            LatLng poi_East = new LatLng(1.303535, 103.842068);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_East,
                    18));
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}