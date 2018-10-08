package com.behincom.behincome.Activityes;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.View;
import android.widget.Button;

import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Activityes.Login.actLogin;
import com.behincom.behincome.R;
import com.behincom.behincome.WebRequest.RWInterface;
import com.behincom.behincome.WebRequest.Retrofite;

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

        Setting.Save("ServerDateTime", "2018-01-01T11:11:11");

//        RWInterface rInterface = Retrofite.getClient().create(RWInterface.class);
//        Call test = rInterface.RQSender("BaseData", "GetServerDateTime");
//        test.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                if (response.isSuccessful()) {
//                    String asd = "ASD";
//                } else {
//                    String onError = "";
//                }
//            }
//
//            @Override
//            public void onFailure(Call call, Throwable t) {
//                String Er = t.getMessage();
//            }
//        });

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radOffline.isChecked())
                    Setting.Save("ServerURL", "http://192.168.1.159/BehincomeWeb/");
                else
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
}
