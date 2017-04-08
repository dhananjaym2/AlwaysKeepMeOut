package keepmeout.travel.Container;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import keepmeout.travel.R;
import keepmeout.travel.constants.AppConstants;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Double LatDest = getIntent().getExtras().getDouble(AppConstants.DestinationLat);
        Double LngDest = getIntent().getExtras().getDouble(AppConstants.DestinationLong);
        Double LatSrc = getIntent().getExtras().getDouble(AppConstants.SourceLat);
        Double LngSrc = getIntent().getExtras().getDouble(AppConstants.SourceLong);

        LatLng src = new LatLng(LatSrc, LngSrc);
        LatLng dest = new LatLng(LatDest, LngDest);
        Log.d("LatLong",LatSrc.toString()+" "+LngSrc.toString());
        mMap.addMarker(new MarkerOptions().position(src).title("Marker for origin"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(src));
//        mMap.addMarker(new MarkerOptions().position(dest).title("Marker for destination"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(src));
    }
}

