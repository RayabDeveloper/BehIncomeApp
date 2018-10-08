package com.behincom.behincome.Activityes.Splash;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.Toast;

import com.behincom.behincome.Accesories.Device;
import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Activityes.Main.actMain;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.Loader.LoadBaseData;
import com.behincom.behincome.Loader.LoadMarketingData;
import com.behincom.behincome.Loader.MergeBaseDataWithMarketing;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.behincom.behincome.WebRequest.RWInterface;
import com.behincom.behincome.WebRequest.Retrofite;

public class actSplash extends AppCompatActivity {

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_splash);
        try{
            getDatas(mA);
        }catch (Exception Ex){
            String Er = Ex.getMessage();
        }
    }

    private static int mA = 3;

    private void getDatas(final int a){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                if(a > 0) {
                    LoadBaseData LoadData = new LoadBaseData();
                    LoadData.setOnLoadDataListener(new LoadBaseData.onLoadAllListener() {
                        @Override
                        public void onLoads() {
                            if (MergeBaseDataWithMarketing.Merge()) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        finishAffinity();
                                        Intent intent = new Intent(context, actMain.class);
                                        startActivity(intent);
                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(context, "تلاش مجدد", Toast.LENGTH_SHORT).show();
                                        getDatas(mA--);
                                    }
                                });
                            }
                        }
                    }, new LoadBaseData.onFailedLoad() {
                        @Override
                        public void onFailed(String Error) {
                            String Er = Error;
                        }
                    });
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "عدم دسترسی به سرور به هر دلیلی", Toast.LENGTH_SHORT).show();
                            finishAffinity();
                        }
                    });
                }
            }
        });
    }

}
