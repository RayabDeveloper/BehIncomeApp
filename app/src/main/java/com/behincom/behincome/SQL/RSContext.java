package com.behincom.behincome.SQL;

import android.app.Application;
import android.content.Context;

public class RSContext extends Application {

    protected static Context getContext;

    @Override
    public void onCreate() {
        super.onCreate();
        getContext = this;
    }
}
