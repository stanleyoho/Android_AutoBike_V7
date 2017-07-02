package autobike.stanley.idv.android_autobike_v7.tab.location;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import autobike.stanley.idv.android_autobike_v7.Common;
import autobike.stanley.idv.android_autobike_v7.R;


public class Tab_Location_Fragment extends Fragment implements OnMapReadyCallback{

    private final static String TAG = "LocationFragment";
    private GoogleMap map;
    private UiSettings uiSettings;
    private LocationManager mLocManager;
    private List<autobike.stanley.idv.android_autobike_v7.tab.location.Location> locationList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_location_fragment, container, false);

            SupportMapFragment supportFr = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.showMap);
            supportFr.getMapAsync(this);
            return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        showAllRentBike();
        setMap();

    }

    private void setMap() {
        mLocManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Location location = mLocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        map.setTrafficEnabled(true);
        uiSettings = map.getUiSettings();
        //開啟控制板
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setRotateGesturesEnabled(true);
        uiSettings.setScrollGesturesEnabled(true);;

        //定位權限判斷
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        }

        //myMark
//        map.addMarker(new MarkerOptions().position(new LatLng(24.967742,121.191700)).title("MyLocation"));
        //目前位置
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(23.793952,120.947721))
                .zoom(7)
                .build();
        //轉移位置動畫
        CameraUpdate cameraUpdate = CameraUpdateFactory
                .newCameraPosition(cameraPosition);
        map.animateCamera(cameraUpdate);
        for(autobike.stanley.idv.android_autobike_v7.tab.location.Location loc : this.locationList){
            map.addMarker(new MarkerOptions()
                    .position(new LatLng(loc.getLon(),loc.getLat()))
                    .title(loc.getLocname())
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.bikeformapred)));
        }

    }

    private void showAllRentBike(){
        if (Common.networkConnected(getActivity())) {
            String url = Common.URL + "LocationServlet";
            List<autobike.stanley.idv.android_autobike_v7.tab.location.Location> locationList = null;

            ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            try {
                locationList = new LocationGetAllTask().execute(url).get();
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (locationList == null || locationList.isEmpty()) {
                Common.showToast(getActivity(), R.string.msg_NoMotorFound);
            } else {
                this.locationList = locationList;
            }
            progressDialog.cancel();

        } else {
            Common.showToast(getActivity(), R.string.msg_NoMotorFound);
        }
    }

}
