package com.behincom.behincome.Accesories;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;

import java.io.File;

public class BitmapCreator {

    Bitmap bmp = null;
    String pth = "";
    public BitmapCreator() {

    }
    public BitmapCreator(Bitmap bitmap) {
        bmp = bitmap;
    }
    public BitmapCreator(String path) {
        pth = path;
    }

    public Bitmap getResizedBitmap(int newHeight, int newWidth) {
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bmp, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }
    public Bitmap getResizedBitmap(Bitmap image, int newHeight, int newWidth) {
        int width = image.getWidth();
        int height = image.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(image, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }
    public Bitmap getBitmapFromPath() {
        File sd = Environment.getExternalStorageDirectory();
        File image = new File(pth);
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);
        return bitmap;
    }
    public Bitmap getBitmapFromPath(String fPath) {
        File sd = Environment.getExternalStorageDirectory();
        File image = new File(fPath);
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);
        return bitmap;
    }

}
