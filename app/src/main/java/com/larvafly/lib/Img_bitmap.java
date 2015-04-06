package com.larvafly.lib;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.larvafly.campuscarpul.Join02Activity;
import com.larvafly.campuscarpul.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import it.neokree.materialtabs.MaterialTab;
import me.drakeet.materialdialog.MaterialDialog;

public class Img_bitmap {
	
	public static final int SELECT_PHOTO = 1000;
    public static final int SELECT_GALLERY= 1001;


	private Uri imageUri;
	private String imageUri_path;
	
	private Activity activity;
	
	public Img_bitmap(final Activity activity) {
		// TODO Auto-generated constructor stub
		
		
		this.activity = activity;

	}
	
	public int exifOrientationToDegrees(int exifOrientation)
	{
		if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_90)
		{
			return 90;
		}
		else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_180)
		{
			return 180;
		}
		else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_270)
		{
			return 270;
		}
		return 0;
	}

	/**
	 * 이미지를 회전시킵니다.
	 * 
	 * @param bitmap 비트맵 이미지
	 * @param degrees 회전 각도
	 * @return 회전된 이미지
	 */
	public Bitmap rotate(Bitmap bitmap, int degrees)
	{
		if(degrees != 0 && bitmap != null)
		{
			Matrix m = new Matrix();
			m.setRotate(degrees, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);

			try
			{
				Bitmap converted = Bitmap.createBitmap(bitmap, 0, 0,
						bitmap.getWidth(), bitmap.getHeight(), m, true);
				if(bitmap != converted)
				{
					bitmap.recycle();
					bitmap = converted;
				}
			}
			catch(OutOfMemoryError ex)
			{
				// 메모리가 부족하여 회전을 시키지 못할 경우 그냥 원본을 반환합니다.
//				Toast.makeText(getApplicationContext(), "메모리가 부족합니다.", Toast.LENGTH_SHORT).show();	
				return null;
			}
		}

		return bitmap;
	}

	int IMAGE_MAX_SIZE = 500;
	public Bitmap getPhotoBitmapOfOptions(Uri imageUri){
		
		String fileName = imageUri.getPath();

		//Decode image size
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(fileName, options);

		int scale = 1;
		if (options.outHeight > IMAGE_MAX_SIZE || options.outWidth > IMAGE_MAX_SIZE) {
			scale = (int)Math.pow(2, (int) Math.round(Math.log(IMAGE_MAX_SIZE / 
					(double) Math.max(options.outHeight, options.outWidth)) / Math.log(0.5)));     
		}
		
		Bitmap image=null;
		if(scale == 1){
			options.inSampleSize = 1;
			//image = BitmapFactory.decodeFile(imagePath, options);
		}else{		
			options.inSampleSize = (int)(scale/2);
			//image = BitmapFactory.decodeFile(imagePath, options);
			//image = Bitmap.createScaledBitmap(image, 500, 500, true);
		}

		image = BitmapFactory.decodeFile(fileName, options);

		// 이미지를 상황에 맞게 회전시킨다
		ExifInterface exif = null;
		try {
			exif = new ExifInterface(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
		int exifDegree = exifOrientationToDegrees(exifOrientation);
		Bitmap img_src = rotate(image, exifDegree);

		img_src = getCircleBitmap(img_src);
		
		return img_src;
	}

	public Bitmap getGallaryBitmapOfOptions(AssetFileDescriptor afd,Intent data){

		//Decode image size
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFileDescriptor(afd.getFileDescriptor(), null, options);	

		int scale = 1;
		if (options.outHeight > IMAGE_MAX_SIZE || options.outWidth > IMAGE_MAX_SIZE) {
			scale = (int)Math.pow(2, (int) Math.round(Math.log(IMAGE_MAX_SIZE / 
					(double) Math.max(options.outHeight, options.outWidth)) / Math.log(0.5)));     
		}
		
		Log.d("test", "scale = "+scale);
		
		BitmapFactory.Options options2 = new BitmapFactory.Options();

		Bitmap image=null;
		if(scale == 1){
			options2.inSampleSize = 1;
			//image = BitmapFactory.decodeFile(imagePath, options);
		}else{		
			options2.inSampleSize = (int)(scale/2);
			//image = BitmapFactory.decodeFile(imagePath, options);
			//image = Bitmap.createScaledBitmap(image, 500, 500, true);
		}

		image = BitmapFactory.decodeFileDescriptor(afd.getFileDescriptor(),null, options2);

		saveBitmaptoJpeg(image,activity.getFilesDir().getPath(),"profile");
		
		// 이미지를 상황에 맞게 회전시킨다
		Uri imageUri = data.getData();
		String[] orientationColumn = {MediaStore.Images.Media.ORIENTATION};
		Cursor cur = activity.managedQuery(imageUri, orientationColumn, null, null, null);
		int orientation = -1;
		if (cur != null && cur.moveToFirst()) {
			orientation = cur.getInt(cur.getColumnIndex(orientationColumn[0]));
		}  
		//Matrix matrix = new Matrix();
		//matrix.postRotate(orientation);

		image = rotate(image, orientation);
//		Bitmap img_corner1 = LIB_Define.setRoundCorner(image1, 15);

		image = getCircleBitmap(image);
		
		return image;
	}
	
	

	public void showimgdialog() {
		// TODO Auto-generated method stub

//        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(
//                InputMethodManager.HIDE_NOT_ALWAYS,
//                InputMethodManager.HIDE_IMPLICIT_ONLY
//        );

        ListView listView;
        final MaterialDialog  mMaterialDialog = new MaterialDialog(activity);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                activity,
                R.layout.list_item
        );
        arrayAdapter.add("앨범");
        arrayAdapter.add("카메라");
        arrayAdapter.add("사진삭제");


        listView = new ListView(activity);
        listView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        float scale =activity.getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (8 * scale + 0.5f);
        listView.setPadding(0, dpAsPixels, 0, dpAsPixels);
        listView.setDividerHeight(1);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("test","position = " +position);
                Log.d("test","view = " +view.getId());
                Log.d("test","id = " +id);


                switch (position) {
                    case 0:

                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setDataAndType(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                MediaStore.Images.Media.CONTENT_TYPE);
                        intent.setType("image/*");

                        activity.startActivityForResult(intent, SELECT_GALLERY);
                        mMaterialDialog.dismiss();

                        break;
                    case 1:

                        File photo = new File(Environment.getExternalStorageDirectory(), "cc_temp_profileimg.jpg");

                        Log.d("test", "photo = " + photo.getPath());

                        imageUri_path = photo.getPath();

                        imageUri = Uri.fromFile(photo);

                        // 카메라를 호출합니다.
                        Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
                        i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        activity.startActivityForResult(i,SELECT_PHOTO);

                        mMaterialDialog.dismiss();

                        break;
                    case 2:

//					user_Info_bean.setPhoto(img_select, null);
//					imageView[img_select].setImageBitmap(null);

                        mMaterialDialog.dismiss();


                        break;

                    default:
                        break;
                }

            }
        });


        mMaterialDialog.setTitle("사진등록");
        mMaterialDialog.setContentView(listView);
        mMaterialDialog.setCanceledOnTouchOutside(true);
        mMaterialDialog.setPositiveButton("취소",new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog.dismiss();
            }
        });


        mMaterialDialog.show();



	}
	
	
	public String getPathFromUri(Uri uri){
		Cursor cursor = activity.getContentResolver().query(uri, null, null, null, null );
		cursor.moveToNext(); 
		String path = cursor.getString( cursor.getColumnIndex( "_data" ) );
		cursor.close();


		return path;
	}


	public static void saveBitmaptoJpeg(Bitmap bitmap,String folder, String name){
		String file_name = name+".jpg";
		try{

			FileOutputStream out = new FileOutputStream(folder+file_name);

			Log.d("test", "IMG file name = "+folder+file_name);

			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.close();

		}catch(FileNotFoundException exception){
			Log.e("FileNotFoundException", exception.getMessage());
		}catch(IOException exception){
			Log.e("IOException", exception.getMessage());
		}
	}
	
	public Bitmap getCircleBitmap(Bitmap bitmap) {
		int x;
		int r1,r2;
		if (bitmap.getWidth() >= bitmap.getHeight()) {
			x = bitmap.getHeight();
			r1 = (int)(bitmap.getWidth() - bitmap.getHeight())/2;
			r2 = 0;
		}else{
			x = bitmap.getWidth();
			r1 = 0;
			r2 = (int)(bitmap.getHeight() - bitmap.getWidth())/2;
		}
		
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		int size = (x/2);
		canvas.drawCircle(bitmap.getWidth()/2, bitmap.getHeight()/2, size, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		
		Log.d("test", "output.getWidth(); = " +output.getWidth());
		Log.d("test", "output.getHeight(); = " +output.getHeight());
		
		
		return output;
	}
	

}
