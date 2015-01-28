package google.codeforindia.haiyya;

import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private Location getLocation()
    {
        AppLocationService appLocationService = new AppLocationService(MapsActivity.this);
        Location location = appLocationService.getLocation(LocationManager.NETWORK_PROVIDER);
        if(location==null)location=appLocationService.getLocation(LocationManager.GPS_PROVIDER);
        if(location==null)location=appLocationService.getLocation(LocationManager.PASSIVE_PROVIDER);
        if (location == null) {
            location.setLatitude(17.23);
            location.setLongitude(78.35);
        }
        return location;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        try{
            mMap.setMyLocationEnabled(true);
            Location loc=getLocation();
            LatLng curr=new LatLng(loc.getLatitude(),loc.getLongitude());
            CameraUpdate center = CameraUpdateFactory.newLatLng(curr);
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(18);
            mMap.moveCamera(center);
            mMap.animateCamera(zoom);
        }catch(Exception e){System.out.println("Location"+e.toString());}
        try {List<PoliceStations> result=SplashActivity.markerList;
            for (PoliceStations item : result) {
                final LatLng mark = new LatLng(item.lat,item.lon);
                mMap.addMarker(new MarkerOptions().position(mark)
                        .title(item.name+" | "+item.phonenumber)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)))
                        .showInfoWindow();
            }
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    try{String title=marker.getTitle();
                        System.out.println(title);
                    String phonenumber=title.substring(title.indexOf('|')+2);
                    title=title.substring(0,title.indexOf('|')-1);
                        Toast.makeText(getApplicationContext(),title+"_"+phonenumber,Toast.LENGTH_LONG).show();
                    }catch(Exception e){}
                    /*TODO
                        Send a message to this phonenumber
                    * */
                    return false;
                }
            });

        } catch (Exception e) {
            System.out.println("There was an error creating the Mobile Service. Verify the URL");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }
}
