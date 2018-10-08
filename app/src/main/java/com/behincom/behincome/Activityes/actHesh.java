package com.behincom.behincome.Activityes;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Datas.Result.SimpleResponse;
import com.behincom.behincome.R;
import com.behincom.behincome.WebRequest.RWInterface;
import com.behincom.behincome.WebRequest.Retrofite;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class actHesh extends AppCompatActivity {

    Context context = this;
    Dialog piDialog;

    Button btn, btn2;
    ImageView img;
    TextView lbl1, lbl2, lbl3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_hesh);

        btn = findViewById(R.id.btn);
        btn2 = findViewById(R.id.btn2);
        img = findViewById(R.id.img);
        lbl1 = findViewById(R.id.lbl1);
        lbl2 = findViewById(R.id.lbl2);
        lbl3 = findViewById(R.id.lbl3);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PicSelector();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File imgFile = new  File(lbl3.getText().toString());
                if(imgFile.exists()){
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    img.setImageBitmap(myBitmap);
                }
            }
        });

    }
    boolean isGrant = false;
    private boolean askForPermission(String permission) {
        if (ContextCompat.checkSelfPermission(actHesh.this, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(actHesh.this, permission)) {
                ActivityCompat.requestPermissions(actHesh.this, new String[]{permission}, 3);
                return false;
            } else {
                ActivityCompat.requestPermissions(actHesh.this, new String[]{permission}, 3);
                return false;
            }
        } else {
            return true;
        }
    }
    Bitmap ProfileImg;
    Uri savedImageURI;
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    try {


                        File fileee = new File(Environment.getExternalStorageDirectory().getPath(), "photo.jpg");

                        try {
                            RWInterface rInterface = Retrofite.getClient().create(RWInterface.class);

                            lbl3.setText(fileee.toString());

                            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), fileee);
                            MultipartBody.Part body = MultipartBody.Part.createFormData("upload", fileee.getName(), reqFile);

                            Call<SimpleResponse> call4 = rInterface.RQProfilePic(Setting.getToken(), body);
                            call4.enqueue(new Callback<SimpleResponse>() {
                                @Override
                                public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                                    try {
                                        lbl1.setText(response.body().Description);
                                        lbl2.setText(response.body().Type);
                                    }catch (Exception Ex){
                                        String Er = Ex.getMessage();
                                    }
                                }

                                @Override
                                public void onFailure(Call<SimpleResponse> call, Throwable t) {
                                    lbl1.setText(t.getMessage());
                                }
                            });
                        } catch (Exception Ex) {
                            String Er = Ex.getLocalizedMessage();
                        }
                    }catch (Exception Ex){
                        String Er = Ex.getMessage();
                    }
                }
                break;
            case 1:
                if(resultCode == RESULT_OK){
                    try {
                        Uri pickedImage = imageReturnedIntent.getData();
                        String hhhh = getPath( context.getApplicationContext( ), pickedImage );
                        lbl3.setText(hhhh);
                        try {
                            RWInterface rInterface = Retrofite.getClient().create(RWInterface.class);
                            File file = new File(hhhh);

                            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
                            MultipartBody.Part body = MultipartBody.Part.createFormData("upload", file.getName(), reqFile);

                            Call<SimpleResponse> call4 = rInterface.RQProfilePic(Setting.getToken(), body);
                            call4.enqueue(new Callback<SimpleResponse>() {
                                @Override
                                public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                                    try {
                                        lbl1.setText(response.body().Description);
                                        lbl2.setText(response.body().Type);
                                    }catch (Exception Ex){
                                        String Er = Ex.getMessage();
                                    }
                                }

                                @Override
                                public void onFailure(Call<SimpleResponse> call, Throwable t) {
                                    lbl1.setText(t.getMessage());
                                }
                            });
                        } catch (Exception Ex) {
                            String Er = Ex.getLocalizedMessage();
                        }
                    }catch (Exception Ex){
                        String Er = Ex.getMessage();
                    }
                }
                break;
        }
    }
    public static String getPath( Context context, Uri uri ) {
        String result = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver( ).query( uri, proj, null, null, null );
        if(cursor != null){
            if ( cursor.moveToFirst( ) ) {
                int column_index = cursor.getColumnIndexOrThrow( proj[0] );
                result = cursor.getString( column_index );
            }
            cursor.close( );
        }
        if(result == null) {
            result = "Not found";
        }
        return result;
    }
    private void PicSelector(){
        lbl1.setText("");
        lbl2.setText("");
        lbl3.setText("");
        if(askForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) && askForPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            isGrant = askForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            isGrant = askForPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            piDialog = new Dialog(context);
            piDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            piDialog.setCancelable(true);
            piDialog.setCanceledOnTouchOutside(true);
            piDialog.setContentView(R.layout.dialog_takepic);
            Objects.requireNonNull(piDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

            TextView lblTakePhoto = piDialog.findViewById(R.id.lblTakePhoto);
            TextView lblChooseFromGallery = piDialog.findViewById(R.id.lblChooseFromGallery);
            Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/ir_sans.ttf");
            lblTakePhoto.setTypeface(tf);
            lblChooseFromGallery.setTypeface(tf);

            lblTakePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    fileUri = getOutputMediaFileUri();
//                    takePicture.putExtra( MediaStore.EXTRA_OUTPUT, fileUri );
//                    startActivityForResult(takePicture, 0);//zero can be replaced with any action code

                    Intent photo = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    Uri uri  = Uri.parse("file:///sdcard/photo.jpg");
                    photo.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);
                    startActivityForResult(photo,0);
                    piDialog.dismiss();
                }
            });
            lblChooseFromGallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 1);//one can be replaced with any action code
                    piDialog.dismiss();
                }
            });
            piDialog.show();
        }
    }
    Uri fileUri;
    private static Uri getOutputMediaFileUri(){
        return Uri.fromFile(getOutputMediaFile());
    }
    private static File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");

        return mediaFile;
    }

}
