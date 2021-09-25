package com.greencross.medigene.alerm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.greencross.medigene.R;
import com.greencross.medigene.util.Utils;


/**
 * 병원진료 예약서비스
 *
 */
public class Appointment extends FragmentActivity implements OnClickListener  {

	private Context con;
	private FragmentActivity ac;

	private String mEdtNumber = "114,,1,1,1";
	private boolean callonoff = false;
	private String str_msg="";
	private TextView caution;
	private int type = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.appointment);
		if(Utils.TEST){
			Log.i(Utils.TAG, "AppointmentFragment");
		}
		if(Utils.FRAGMENTACTIVITY3 == null){
			Utils.FRAGMENTACTIVITY3= this;
		}
		con = this;
		ac = this;

		Utils.progressBar.setVisibility(View.INVISIBLE);

		caution = (TextView)findViewById(R.id.caution);
		ImageView callBtn = (ImageView)findViewById(R.id.agent_connection_button);
		callBtn.setOnClickListener(this);
		
		TextView tvTitle = (TextView)findViewById(R.id.headTitle);
		tvTitle.setText(getString(R.string.appointment));

	}


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

			
			/*if(Utils.getCallState.aviliableCALL(ac.getApplicationContext())){
				Toast.makeText(ac, getString(R.string.no_call), Toast.LENGTH_LONG).show();
				break;
			}*/
			
			if(callonoff){
				dialog_on();
			}else{
				Utils.dialog(this.con, str_msg);
			}
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
		alertDialogBuilder.setMessage("asdfasd fasdf asdfa dsfss");
		alertDialogBuilder.setCancelable(true);
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}


	public void onPreExecute() {
		Utils.progressBar.setVisibility(View.VISIBLE);
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
