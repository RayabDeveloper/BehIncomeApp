package com.behincom.behincome.Activityes;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.behincom.behincome.R;

public class actMoveFAB extends AppCompatActivity {

    ConstraintLayout rootView;
    FloatingActionButton mFAB;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_move_fab);

        mFAB = findViewById(R.id.mFAB);

        mFAB.setOnTouchListener(new View.OnTouchListener() {
            float x, y;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){

                    case MotionEvent.ACTION_MOVE:

                        mFAB.setX(mFAB.getX() + (event.getX() - x));
                        mFAB.setY(mFAB.getY() + (event.getY() - y));
                        return true;
                    case MotionEvent.ACTION_DOWN:
                        x = event.getX();
                        y = event.getY();
                        return true;
                }

                return false;
            }
        });

    }

    @Override
    public void onBackPressed() {

    }
}
