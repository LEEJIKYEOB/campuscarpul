package com.larvafly.lib;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonRectangle;
import com.larvafly.campuscarpul.R;
import com.larvafly.http.HTTP_Handler;
import com.larvafly.http.HTTP_Handler.OnHttpReceiveListener;
import com.larvafly.http.JOIN_HTTP;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.FileNotFoundException;

public abstract class ImguploadActivity extends ActionBarActivity {


//    activity.imageUri = imageUri;
//    activity.imageView.setImageBitmap(null);
//    activity.img_src = null;

    public abstract void setimgUri(Uri imageUri);

    public abstract void setimgcancel();


}


