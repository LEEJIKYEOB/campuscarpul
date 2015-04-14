package com.larvafly.campuscarpul;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.gc.materialdesign.views.ButtonRectangle;
import com.larvafly.bean.UserProfile_bean;
import com.larvafly.http.CAR_UPDATA_HTTP;
import com.larvafly.http.FILE_UPLOAD_HTTP;
import com.larvafly.http.HTTP_Handler;
import com.larvafly.http.HTTP_Handler.OnHttpReceiveListener;
import com.larvafly.http.JOIN_HTTP;
import com.larvafly.lib.Img_bitmap;
import com.larvafly.lib.ImguploadActivity;
import com.larvafly.lib.Static_date;
import com.rengwuxian.materialedittext.MaterialEditText;

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
import android.support.v7.widget.Toolbar;
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

public class Join02Activity extends ImguploadActivity implements OnClickListener, OnHttpReceiveListener {


    public Uri imageUri;
    public String img_src = null;
    ImageView join02_carimg_iv;
    MaterialEditText join02_carnum_met;
    MaterialEditText join02_carclass_met;
    ButtonRectangle join02_ok_br;
    Img_bitmap img_bitmap;

    HTTP_Handler http_handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join02_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("운전자 등록");
        this.setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_mtrl_am_alpha);


        join02_carimg_iv = (ImageView) findViewById(R.id.join02_carimg_iv);
        join02_carimg_iv.setOnClickListener(this);
        join02_carnum_met = (MaterialEditText) findViewById(R.id.join02_carnum_met);
        join02_carclass_met = (MaterialEditText) findViewById(R.id.join02_carclass_met);
        join02_ok_br = (ButtonRectangle) findViewById(R.id.join02_ok_br);
        join02_ok_br.setOnClickListener(this);

        img_bitmap = new Img_bitmap(this, false);

        http_handler = new HTTP_Handler(this, this);

    }


    @Override
    public void Http_Receive(Message msg) {
        // TODO Auto-generated method stub

        switch (msg.arg1) {

            case HTTP_Handler.HTTP_RECEIVE_CAR_UPDATA:


                Static_date.myProfile.setState(1);

                final MaterialDialog mMaterialDialog = new MaterialDialog(this);
                mMaterialDialog.setTitle("운전자등록완료");
                mMaterialDialog.setCanceledOnTouchOutside(true);
                mMaterialDialog.setMessage("운전자등록완료 심사는 1~2일정도 소요됩니다.");
                mMaterialDialog.setPositiveButton("확인", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        finish();
                        mMaterialDialog.dismiss();

                    }
                });
                mMaterialDialog.show();


                break;

            case HTTP_Handler.HTTP_RECEIVE_FILEUPLOAD:

                int profileimage_idx = (Integer) msg.obj;


                Static_date.myProfile.setCarimage_idx(profileimage_idx);
                Static_date.myProfile.setCar_number(join02_carnum_met.getEditableText().toString());
                Static_date.myProfile.setCar_class(join02_carclass_met.getEditableText().toString());

                CAR_UPDATA_HTTP car_updata_http = new CAR_UPDATA_HTTP(http_handler, Static_date.myProfile, HTTP_Handler.HTTP_RECEIVE_CAR_UPDATA);
                car_updata_http.start();


                break;

            case HTTP_Handler.HTTP_RECEIVE_FALSE:

                switch (msg.arg2) {


                    case HTTP_Handler.HTTP_RECEIVE_ERROR_CODE_20002:

                        Toast.makeText(getApplicationContext(), "알수없는 에러 다시시도해주세요", Toast.LENGTH_SHORT).show();

                        break;


                }

                break;

            default:
                break;
        }


    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        switch (v.getId()) {
            case R.id.join02_carimg_iv:


                img_bitmap.showimgdialog();

                break;
            case R.id.join02_ok_br:

                if (check()) {

                    FILE_UPLOAD_HTTP file_upload_http = new FILE_UPLOAD_HTTP(http_handler, HTTP_Handler.HTTP_RECEIVE_FILEUPLOAD);
                    file_upload_http.start();
                }


                break;


        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        //		super.onActivityResult(requestCode, resultCode, data);

        Log.d("test", "requestCode = " + requestCode);
        Log.d("test", "resultCode = " + resultCode);
        Log.d("test", "data = " + data);

        switch (requestCode) {

            case Img_bitmap.SELECT_PHOTO:

                if (resultCode == -1) {


                    AssetFileDescriptor afd;
                    try {

                        Log.d("test", "imageUri = " + imageUri.getPath());

                        afd = getContentResolver().openAssetFileDescriptor(imageUri, "r");

                        Bitmap bitmap = img_bitmap.getPhotoBitmapOfOptions(imageUri);

                        img_src = Environment.getExternalStorageDirectory() + "/cc_temp_profileimg.jpg";

                        join02_carimg_iv.setImageBitmap(bitmap);

                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }


                break;
            case Img_bitmap.SELECT_GALLERY:
                if (data != null) {

                    Uri img_url1 = data.getData();

                    if (img_url1 != null) {


                        AssetFileDescriptor afd;
                        try {
                            afd = getContentResolver().openAssetFileDescriptor(img_url1, "r");

                            Bitmap bitmap = img_bitmap.getGallaryBitmapOfOptions(afd, data);

                            img_src = Environment.getExternalStorageDirectory() + "/cc_temp_profileimg.jpg";

                            join02_carimg_iv.setImageBitmap(bitmap);

                        } catch (FileNotFoundException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                }

                break;

        }
    }

    public void setimgUri(Uri imageUri) {

        this.imageUri = imageUri;

    }

    public void setimgcancel() {

        join02_carimg_iv.setImageBitmap(null);
        img_src = null;

    }

    boolean check() {

//        if () Static_date.myProfile.setCar_number(join02_carnum_met.getEditableText().toString());
//        Static_date.myProfile.setCar_class(join02_carclass_met.getEditableText().toString());

        if (img_src == null) {
            Toast.makeText(getApplicationContext(), "운전면허증을 등록해 주세요.", Toast.LENGTH_SHORT).show();
            return false;

        }
        if (join02_carnum_met.getEditableText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "차랑번호가 공백입니다.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (join02_carclass_met.getEditableText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "차랑종류가 공백입니다.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


}


