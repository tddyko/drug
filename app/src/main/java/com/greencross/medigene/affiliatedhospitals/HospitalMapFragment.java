package com.greencross.medigene.affiliatedhospitals;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.greencross.medigene.R;
import com.greencross.medigene.affiliatedhospitals.common.Utils;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.LocationSource.OnLocationChangedListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.greencross.medigene.base.BaseFragment;
import com.greencross.medigene.base.CommonActionBar;

/**
 * 지도
 *
 */
public class HospitalMapFragment extends BaseFragment implements OnMapReadyCallback, OnClickListener{

	private MapView mMapView;
	private GoogleMap mMap;
	private Bundle mBundle;
	private LatLng mPosFija;
	private Criteria criteria;
	private LocationManager locationmanager;
//	private Marker aa;
	private Button myLocationBtn;
	private Button hospitalLocationBtn;
	private TextView hospitalName;
	private Context con;
	private BitmapDescriptor cc;
	private boolean checkMy = false;
	private OnLocationChangedListener listeners;
	private String gpxX = "";
	private String gpxY = "";
	private boolean hcheck = true;
	private Marker mHospital;

	public static Fragment newInstance() {
		HospitalMapFragment fragment = new HospitalMapFragment();
		return fragment;
	}


	@Override
	public void loadActionbar(CommonActionBar actionBar) {
		super.loadActionbar(actionBar);
		actionBar.setActionBarTitle(getArguments().getString("title"));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View inflatedView = inflater.inflate(R.layout.hospital_map_fragment, container, false);
		if(Utils.TEST){
			Log.i(Utils.TAG, "HospitalMapFragment");
		}
		con = getActivity();
		gpxX = getArguments().getString("gpxX");
		gpxY = getArguments().getString("gpxY");

		if(gpxX == null || gpxY == null || gpxX.equals("") || gpxY.equals("")){
			Toast tos = Toast.makeText(con, getString(R.string.message42), Toast.LENGTH_LONG);
			tos.setGravity(Gravity.CENTER, 0, 0);
			tos.show();
			hcheck = false;
		}

		mMapView = (MapView) inflatedView.findViewById(R.id.map);
		myLocationBtn = (Button) inflatedView.findViewById(R.id.myLocationBtn);
		hospitalLocationBtn = (Button) inflatedView.findViewById(R.id.hospitalLocationBtn);
		hospitalName = (TextView) inflatedView.findViewById(R.id.hospitalName);
		myLocationBtn.setOnClickListener(this);

		if(hcheck){
			hospitalLocationBtn.setOnClickListener(this);
			myLocationBtn.setBackgroundResource(R.drawable.btn_map_user_off);
			hospitalLocationBtn.setBackgroundResource(R.drawable.btn_map_hospital_on);  
		}else{
			myLocationBtn.setBackgroundResource(R.drawable.btn_map_user_on);
			hospitalLocationBtn.setBackgroundResource(R.drawable.btn_map_hospital_off);  
		}

		mMapView.getMapAsync(this);

		try {
			GooglePlayServicesUtil.isGooglePlayServicesAvailable(con);
			MapsInitializer.initialize(con);
		} catch (Exception e) {
		}

		mMapView.onCreate(mBundle);

		setUpMapIfNeeded(inflatedView);

		
		if(!hcheck){
			Toast tos = Toast.makeText(con, getString(R.string.message43), Toast.LENGTH_LONG);
			tos.setGravity(Gravity.CENTER, 0, 0);
			tos.show();
			myLocationBtn.performClick();
		}
		return inflatedView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBundle = savedInstanceState;
	}

	/**
	 * 지도 초기 위치 데이터 설정
	 * @param inflatedView
	 */
	private void setUpMapIfNeeded(View inflatedView) {
		hospitalName.setText(getArguments().getString("hospitalName"));
		float gpxXs = 0;
		float gpxYs = 0;
		if(gpxX != null && gpxY != null){
			try {
				
				gpxXs = Float.valueOf(gpxX);
				gpxYs = Float.valueOf(gpxY);
				if(Utils.TEST){
					Log.e(Utils.TAG, "eeee"+gpxXs);
					Log.e(Utils.TAG, "eeee"+gpxYs);
				}
			} catch (Exception e) {
				if(Utils.TEST){
					Log.e(Utils.TAG, "eeee");
				}
			}
		}
		mPosFija = new LatLng(gpxYs,gpxXs);    	
		if (mMap == null) {
//			mMap = ((MapView) inflatedView.findViewById(R.id.map)).getMapAsync(this);
			if (mMap != null) {
				setUpMap();

				mMap.setOnMarkerClickListener(new OnMarkerClickListener() {
					public boolean onMarkerClick(Marker marker) {
						marker.showInfoWindow();
						return true;
					}
				});
				mMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
					@Override
					public void onInfoWindowClick(Marker marker) {

					}
				});
			}
		}

	}

	/**
	 * 포커스 이동 및 Marker 표시
	 */
	private void setUpMap() {
		locationmanager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
		criteria=new Criteria();

		UiSettings settings = mMap.getUiSettings();
		settings.setAllGesturesEnabled(true);
		settings.setMyLocationButtonEnabled(true);

		if(hcheck){
//			mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mPosFija, 16));
//			cc = BitmapDescriptorFactory.fromResource(R.drawable.btn_pin);
//			mMap.addMarker(new MarkerOptions().position(mPosFija).icon(cc).title("병원"));
			
		}

		MyLocationSource source = new MyLocationSource();
		mMap.setLocationSource(source);
		mMap.setMyLocationEnabled(true);   

	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		locationmanager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
		criteria=new Criteria();
		UiSettings settings = googleMap.getUiSettings();
		settings.setAllGesturesEnabled(true);
		settings.setMyLocationButtonEnabled(true);
		googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mPosFija, 16));

		MyLocationSource source = new MyLocationSource();
		googleMap.setLocationSource(source);
		googleMap.setMyLocationEnabled(true);
//		cc = BitmapDescriptorFactory.fromResource(R.drawable.btn_pin);
		mMap = googleMap;

		mHospital = mMap.addMarker(new MarkerOptions()
				.position(mPosFija)
				.title("병원"));
		mHospital.setTag(0);

//		setUpMap();
	}

//	@Override
//	public boolean onMarkerClick(final Marker marker) {
//
//		// Retrieve the data from the marker.
//		Integer clickCount = (Integer) marker.getTag();
//
//		// Check if a click count was set, then display the click count.
//		if (clickCount != null) {
//			clickCount = clickCount + 1;
//		}
//
//		// Return false to indicate that we have not consumed the event and that we wish
//		// for the default behavior to occur (which is for the camera to move such that the
//		// marker is centered and for the marker's info window to open, if it has one).
//		return false;
//	}

	/**
	 * 내 위치 설정
	 */
	private class MyLocationSource implements LocationSource {
		@Override
		public void activate(OnLocationChangedListener listener) {
			listeners = listener;
			String provider = locationmanager.getBestProvider(criteria, true);
			if(provider!=null){  
				Location location = locationmanager.getLastKnownLocation(provider);
				locationmanager.requestLocationUpdates(provider, 2000, 10, locationListener);      
				if(location != null){
					location.setAccuracy(100); 
					listener.onLocationChanged(location);

//					LatLng ccc =  new LatLng(location.getLatitude(),location.getLongitude());
//					BitmapDescriptor cccc = BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher);
//					aa =  mMap.addMarker(new MarkerOptions().position(ccc).icon(cccc));
				}
			}
		}

		@Override
		public void deactivate() {
		}
	}    

	@Override
	public void onResume() {
		super.onResume();
		mMapView.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		mMapView.onPause();
	}

	@Override
	public void onDestroy() {
		mMapView.onDestroy();
		super.onDestroy();
	}	

	private final LocationListener locationListener = new LocationListener() {
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		}

		public void onProviderEnabled(String arg0) {
		}

		public void onProviderDisabled(String arg0) {
		}

		public void onLocationChanged(Location location) {
			if(Utils.TEST){
				Log.d("myLog"  , "onLocationChanged: !!"  + "onLocationChanged!!");
			}
			double lat =  location.getLatitude();
			double lng = location.getLongitude();
			if(Utils.TEST){
				Log.d("myLog"  , lat  + " " + lng);
			}
			LatLng myLocation =  new LatLng(location.getLatitude(),location.getLongitude());
			if(myLocation != null){
				if(checkMy){
					mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 16));
				}
//				if(aa != null){
//					aa.setPosition(myLocation);
//				}
				if (listeners != null) {
					listeners.onLocationChanged(location);
				}
			}
		}
	};    

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.myLocationBtn:
			checkMy = true;
			String provider = locationmanager.getBestProvider(criteria, true);
			if(provider==null){
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(con);
				alertDialogBuilder.setTitle("");
				alertDialogBuilder.setMessage("위치서비스 동의");
				alertDialogBuilder.setPositiveButton(getString(R.string.no),new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast tos = Toast.makeText(con, "Google 위치 서비스 사용 동의 시 사용가능합니다.", Toast.LENGTH_SHORT);
						tos.setGravity(Gravity.CENTER, 0, 0);
						tos.show();
					}
				});
				alertDialogBuilder.setNeutralButton(getString(R.string.yes),new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast tos = Toast.makeText(con, "Google 위치 서비스 사용 동의 시 사용가능합니다.", Toast.LENGTH_SHORT);
						tos.setGravity(Gravity.CENTER, 0, 0);
						tos.show();
						startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
					}
				});
				alertDialogBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						Toast tos = Toast.makeText(con, "Google 위치 서비스 사용 동의 시 사용가능합니다.", Toast.LENGTH_SHORT);
						tos.setGravity(Gravity.CENTER, 0, 0);
						tos.show();
					}
				}).show();

			}else{
				Location location = locationmanager.getLastKnownLocation(provider);
				if(location != null){
					LatLng myLocation =  new LatLng(location.getLatitude(),location.getLongitude());
					mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 16));

				}
			}



			myLocationBtn.setBackgroundResource(R.drawable.btn_map_user_on);
			hospitalLocationBtn.setBackgroundResource(R.drawable.btn_map_hospital_off);
			break;
		case R.id.hospitalLocationBtn:
			checkMy = false;
			mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mPosFija, 16));
			myLocationBtn.setBackgroundResource(R.drawable.btn_map_user_off);
			hospitalLocationBtn.setBackgroundResource(R.drawable.btn_map_hospital_on);
			break;
		default:
		}
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if(requestCode == 0) {
			String provider = locationmanager.getBestProvider(criteria, true);
			if(provider==null){
				Toast tos = Toast.makeText(con, "Google 위치 서비스 사용 동의 시 사용가능합니다.", Toast.LENGTH_SHORT);
				tos.setGravity(Gravity.CENTER, 0, 0);
				tos.show();
			}else{
				setUpMap();
				
			}
		}


	}
	
	
}