package com.larvafly.http;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.os.Environment;
import android.util.Log;

public class FILE_UPLOAD_HTTP extends HTTP_Thread {

    public static final int PROFILE = 1;
    public static final int CAR = 2;

    private FileInputStream mFileInputStream = null;
    private URL connectUrl = null;
    private String lineEnd = "\r\n";
    private String twoHyphens = "--";
    private String boundary = "*****";
    private String urlString = "http://ukplio.cafe24.com/campuscarpul/FileUpload.php";
    private String filePath;

    private int mode;


    public FILE_UPLOAD_HTTP(HTTP_Handler handler, int resultcode) {
        super(handler, resultcode);


        this.filePath = Environment.getExternalStorageDirectory() + "/cc_temp_profileimg.jpg";

        Log.d("test", "s = " + filePath);

        this.mode = 0;

    }


    @Override
    public void run() {


        try {


            mFileInputStream = new FileInputStream(filePath);
            connectUrl = new URL(urlString);
            System.out.println("1");
            // open connection
            HttpURLConnection conn = (HttpURLConnection) connectUrl.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setDefaultUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            conn.connect();
            // write data
            //까지 오류 x
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());


            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"mode\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(URLEncoder.encode(String.valueOf(mode), "UTF-8"));
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + filePath + "\"" + lineEnd);
            dos.writeBytes(lineEnd);
            int bytesAvailable = mFileInputStream.available();
            int maxBufferSize = 1024 * 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);
            byte[] buffer = new byte[bufferSize];
            int bytesRead = mFileInputStream.read(buffer, 0, bufferSize);
            System.out.println("3");

            // read image
            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = mFileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = mFileInputStream.read(buffer, 0, bufferSize);
            }

            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // close streams
            mFileInputStream.close();
            dos.flush(); // finish upload...
            System.out.println("4");

            // get response
            int ch;
            InputStream is = conn.getInputStream();
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1) {
                b.append((char) ch);
            }
            String s = b.toString();
            dos.close();
            System.out.println(s);

            Log.d("test", "return = " + s);


            if (s != null) {


                JSONObject jsonObject = new JSONObject(s);

                int result_code = jsonObject.getInt("result_code");


                if (result_code == HTTP_Handler.HTTP_RECEIVE_TRUE) {

                    message.obj = jsonObject.getInt("idx");

                } else {
                    message.arg1 = http_Handler.HTTP_RECEIVE_FALSE;
                    message.arg2 = http_Handler.HTTP_RECEIVE_ERO_NICK_NO_CHECK;
                }

            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("error");

            Log.d("test", "error");

            // TODO: handle exception
        }

        http_Handler.sendMessage(message);

    }

}
