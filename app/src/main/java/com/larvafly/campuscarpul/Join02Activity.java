package com.larvafly.campuscarpul;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.larvafly.bean.UserProfile_bean;
import com.larvafly.http.HTTP_Handler;
import com.larvafly.http.HTTP_Handler.OnHttpReceiveListener;
import com.larvafly.lib.Static_date;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import it.neokree.materialtabs.MaterialTab;
import me.drakeet.materialdialog.MaterialDialog;

public class Join02Activity extends Activity implements OnClickListener,OnHttpReceiveListener{

	UserProfile_bean userProfile_bean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.join02_activity);
		
		Intent intent = getIntent();
		
		userProfile_bean = (UserProfile_bean) intent.getSerializableExtra("userProfile_bean");

//        MaterialDialog mMaterialDialog

        AlertDialog.Builder ab = new AlertDialog.Builder(this,R.style.DialogccGori);


		ab.setTitle("운전자 이세요?");
		ab.setMessage("운전자이시면 추가정보를 입력하여야 합니다.\n일반 사용자라면 건너뛰기를 눌러주세요");

		final EditText input = new EditText(this);
		input.setTypeface(Static_date.myfont);
		ab.setView(input);

		ab.setPositiveButton("건너뛰기", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
				
				Intent intent = new Intent(getApplicationContext(), Join02Activity.class);
				intent.putExtra("userProfile_bean", userProfile_bean);
				startActivity(intent);


				dialog.dismiss();

			}
		});

		ab.setNegativeButton("확인", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

				dialog.dismiss();

			}
		});

		//			

		Dialog d = ab.show();
		int dividerId = d.getContext().getResources().getIdentifier("android:id/titleDivider", null, null);
		View divider = d.findViewById(dividerId);
		divider.setBackgroundColor(getResources().getColor(R.color.lightblue_500));

	}

	@Override
	public void Http_Receive(Message msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}


}


