package com.behincom.behincome.Activityes;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRadioButton;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Activityes.Login.actLogin;
import com.behincom.behincome.Datas.BaseData.Basic_Provinces;
import com.behincom.behincome.Datas.Keys.Tables;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.behincom.behincome.WebRequest.RWInterface;
import com.behincom.behincome.WebRequest.Retrofite;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class actChoiseType extends AppCompatActivity {

    Context context = this;

    Button btnSelect;
    AppCompatRadioButton radOffline, radOnline, radManager, radMarketer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_choise_type);

        btnSelect = findViewById(R.id.btnSelect);
        radMarketer = findViewById(R.id.radMarketer);
        radManager = findViewById(R.id.radManager);
        radOnline = findViewById(R.id.radOnline);
        radOffline = findViewById(R.id.radOffline);

//        ResizeAnimation resize = new ResizeAnimation(
//                imgBig,
//                imgSmall.getLayoutParams().height,
//                imgBig.getLayoutParams().height
//        );
//        resize.setDuration(1000);
//        imgBig.startAnimation(resize);

        Setting.Save("ServerDateTime", "2018-01-01T11:11:11");

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(radOffline.isChecked())
//                    Setting.Save("ServerURL", "http://192.168.1.17/BehincomeWeb/");
//                else
//                    Setting.Save("ServerURL", "http://164.138.17.243/");
                Setting.Save("ServerURL", "http://164.138.17.243/");

                if(radManager.isChecked())
                    Setting.Save("AccountType", "1");
                else
                    Setting.Save("AccountType", "2");

                Intent intent = new Intent(context, actLogin.class);

                startActivity(intent);
            }
        });

    }

//    public class ResizeAnimation extends Animation {
//        final int targetHeight;
//        View view;
//        int startHeight;
//
//        public ResizeAnimation(View view, int targetHeight, int startHeight) {
//            this.view = view;
//            this.targetHeight = targetHeight;
//            this.startHeight = startHeight;
//        }
//
//        @Override
//        protected void applyTransformation(float interpolatedTime, Transformation t) {
//            int newHeight = (int) (startHeight - targetHeight * interpolatedTime);
//            //to support decent animation, change new heigt as Nico S. recommended in comments
//            //int newHeight = (int) (startHeight+(targetHeight - startHeight) * interpolatedTime);
//            view.getLayoutParams().height = newHeight;
//            view.requestLayout();
//        }
//
//        @Override
//        public void initialize(int width, int height, int parentWidth, int parentHeight) {
//            super.initialize(width, height, parentWidth, parentHeight);
//        }
//
//        @Override
//        public boolean willChangeBounds() {
//            return true;
//        }
//    }

}
