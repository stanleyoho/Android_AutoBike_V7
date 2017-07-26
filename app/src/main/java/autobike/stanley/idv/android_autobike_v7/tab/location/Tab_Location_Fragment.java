package autobike.stanley.idv.android_autobike_v7.tab.location;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.concurrent.ExecutionException;

import autobike.stanley.idv.android_autobike_v7.Common;
import autobike.stanley.idv.android_autobike_v7.R;
import autobike.stanley.idv.android_autobike_v7.tab.news.Tab_News_Fragment;


public class Tab_Location_Fragment extends Fragment implements OnMapReadyCallback{

    private final static String TAG = "LocationFragment";
    private GoogleMap map;
    private UiSettings uiSettings;
    private SupportMapFragment supportFr;
    private LocationManager mLocManager;
    private List<autobike.stanley.idv.android_autobike_v7.tab.location.Location> locationList;
    private View rootView;
    private Location location;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.tab_location_fragment, container, false);
        supportFr = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.showMap);
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
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {

            location = mLocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


        } else {
            Toast.makeText(getActivity(), "error_permission_map", Toast.LENGTH_LONG).show();
        }


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
        //用FOR把DB抓下的物件每個都上MARK
        addMarkersToMap(locationList , map);


        //換標記樣式
        map.setInfoWindowAdapter(new MyInfoWindowAdapter());

        MyMarkerListener myMarkerListener = new MyMarkerListener();
        map.setOnMarkerClickListener(myMarkerListener);
        map.setOnInfoWindowClickListener(myMarkerListener);
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

    private class MyMarkerListener implements GoogleMap.OnMarkerClickListener,
            GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerDragListener {
        @Override
        public boolean onMarkerClick(Marker marker) {
            Toast.makeText(getContext(),marker.getTitle(),Toast.LENGTH_SHORT);
            return false;
        }

        @Override
        public void onInfoWindowClick(Marker marker) {
            Toast.makeText(getContext(),marker.getTitle(),Toast.LENGTH_SHORT);
        }

        @Override
        public void onMarkerDragStart(Marker marker) {

        }

        @Override
        public void onMarkerDragEnd(Marker marker) {

        }

        @Override
        public void onMarkerDrag(Marker marker) {

        }
    }

    private class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
        private final View infoWindow;

        MyInfoWindowAdapter() {
            infoWindow = View.inflate(getActivity(), R.layout.tab_location_info_window, null);
        }
        @Override
        public View getInfoWindow(Marker marker) {
            int logoId;
            ImageView ivLogo = ((ImageView) infoWindow
                    .findViewById(R.id.ivLogo));
            for(autobike.stanley.idv.android_autobike_v7.tab.location.Location loc : locationList){
                if ((marker.getTitle()).equals(loc.getLocname())) {
                    try {
                        new LocationGetImageTask(ivLogo).execute(Common.URL_LocationServlet,loc.getLocno(),100).get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }else{
                    logoId = 0;
                }
            }

            String title = marker.getTitle();
            TextView tvTitle = ((TextView) infoWindow
                    .findViewById(R.id.tvTitle));
            tvTitle.setText(title);

            String snippet = marker.getSnippet();
            TextView tvSnippet = ((TextView) infoWindow
                    .findViewById(R.id.tvSnippet));
            tvSnippet.setText(snippet);


            return infoWindow;

        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }
    }

    public void onClearMapClick(View view) {
        map.clear();
    }

    public void onResetMapClick(View view) {
        map.clear();
        addMarkersToMap(locationList,map);
    }

    private void addMarkersToMap(List<autobike.stanley.idv.android_autobike_v7.tab.location.Location> loc , GoogleMap map) {
        for (autobike.stanley.idv.android_autobike_v7.tab.location.Location locccc : loc) {
            map.addMarker(new MarkerOptions()
                    .position(new LatLng(locccc.getLon(), locccc.getLat()))
                    .title(locccc.getLocname())
                    .snippet(locccc.getAddr())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder)));
        }

    }
}
