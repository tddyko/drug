package com.greencross.medigene.alerm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.greencross.medigene.R;
import com.greencross.medigene.util.Utils;


/**
 * 건강검진 예약 서비스
 *
 */
public class Healthscreening extends FragmentActivity implements OnClickListener  {

	private Context con;
	private FragmentActivity ac;

	private String mEdtNumber = "01000000000";
	private boolean callonoff = false;
	private String str_msg="";
	private TextView caution;
	private int type = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.healthscreening_fragment);
		if(Utils.TEST){
			Log.i(Utils.TAG, "HealthscreeningFragment");
		}
		if(Utils.FRAGMENTACTIVITY2 == null){
			Utils.FRAGMENTACTIVITY2= this;
		}
		con = this;
		ac = Healthscreening.this;
		Utils.progressBar.setVisibility(View.INVISIBLE);

		caution = (TextView)findViewById(R.id.caution);

		ImageView callBtn = (ImageView)findViewById(R.id.agent_connection_button);
		callBtn.setOnClickListener(this);
		
		TextView tvTitle = (TextView)findViewById(R.id.headTitle);
		tvTitle.setText(getString(R.string.reservation_health_screening));
		
		Button getHospital =(Button)findViewById(R.id.getHospital);
		getHospital.setOnClickListener(this);
		
		type = 0;

	}
	
	/*@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.healthscreening_fragment, null);
		if(Utils.TEST){
			Log.i(Utils.TAG, "HealthscreeningFragment");
		}
		atv = this;
		Utils.progressBar.setVisibility(View.INVISIBLE);

		caution = (TextView)view.findViewById(R.id.caution);

		ImageView callBtn = (ImageView)view.findViewById(R.id.agent_connection_button);
		callBtn.setOnClickListener(this);
		type = 0;
		String param = getString(R.string.checkupReserve_Request, Utils.INSURESCODE, Utils.pref.getString("USER_NUMBER", ""), Utils.pref.getString("CID", ""));
		if(Utils.TEST){
			Log.i(Utils.TAG, "Logo param : "+param);
		}
		HttpAsyncTask rssTask = new HttpAsyncTask(this, Utils.pref.getString("apiURL", "")+Utils.URL_STR);
		rssTask.setParam(param);
		rssTask.execute();	
		return view;
	}*/

	/*@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		con = activity;
		ac = activity;
	}*/

	@Override
	public void onBackPressed(){
		AppManageHelper.footBack(ac);
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.agent_connection_button:

			
//			if(Utils.getCallState.aviliableCALL(ac.getApplicationContext())){
//				Toast.makeText(ac, getString(R.string.no_call), Toast.LENGTH_LONG).show();
//				break;
//			}
			
			if(callonoff){
				dialog_on();
			}else{
				Utils.dialog(this.con, str_msg);
			}
			break;
		case R.id.getHospital:
				if(Utils.TEST)
				Log.d("hsh", "return 진료병원");
				break;

		}
	}

	/**
	 * 전화걸기
	 */
	public void performDial() {
		if (mEdtNumber != null) {
			try {
				ac.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mEdtNumber)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 팝업생성
	 */
	public void dialog_on(){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(con);
		alertDialogBuilder.setTitle("");
		alertDialogBuilder.setMessage("asdfasdfffsdf23ewed");
		alertDialogBuilder.setPositiveButton("aa", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		alertDialogBuilder.setNeutralButton("bbb", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		alertDialogBuilder.setCancelable(true);
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}


	public void onPreExecute() {
		Utils.progressBar.setVisibility(View.INVISIBLE);
	}

	public void onPostExecute(String data) {
		Utils.progressBar.setVisibility(View.INVISIBLE);
		try {
			if(Utils.TEST){
				Log.i(Utils.TAG, "data : "+data);
			}

		} catch (Exception e) {
		}

	}

	public void onError() {

	}
}
