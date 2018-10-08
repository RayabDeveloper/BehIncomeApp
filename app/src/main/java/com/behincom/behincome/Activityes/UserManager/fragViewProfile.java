package com.behincom.behincome.Activityes.UserManager;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.behincom.behincome.Accesories.DateConverter;
import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.Profile.Marketers;
import com.behincom.behincome.Datas.Profile.Profile;
import com.behincom.behincome.R;
import com.behincom.behincome.WebRequest.RWInterface;
import com.behincom.behincome.WebRequest.Retrofite;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragViewProfile extends Fragment {

    static Context context;
    RWInterface rInterface;
    actUserManager act = new actUserManager();

    ImageView imgProfile, imgBack, btnCheck;
    TextView lblUserPhone2, lblName, lblBirthDay, lblSex, lblMarri, lblNationality, lblAddress, lblTitle;

    public Marketers Marketer = new Marketers();
    private Profile data = new Profile();

    public static fragViewProfile newInstance(Context mContext){
        fragViewProfile fragment = new fragViewProfile();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_marketer_profile, container, false);

        lblTitle = view.findViewById(R.id.lblTitle);
        imgBack = view.findViewById(R.id.imgBack);
        lblUserPhone2 = view.findViewById(R.id.lblUserPhone2);
        lblName = view.findViewById(R.id.lblName);
        lblBirthDay = view.findViewById(R.id.lblBirthDay);
        lblSex = view.findViewById(R.id.lblSex);
        lblMarri = view.findViewById(R.id.lblMarri);
        lblNationality = view.findViewById(R.id.lblNationality);
        lblAddress = view.findViewById(R.id.lblAddress);
        imgProfile = view.findViewById(R.id.imgProfile);
        btnCheck = view.findViewById(R.id.btnCheck);

        lblTitle.setText("پروفایل کارمند");
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getFragByState(FragmentState.Marketers);
            }
        });
        btnCheck.setVisibility(View.GONE);

        HashMap<String, Object> map = new HashMap<>();
        map.put("UserID", Marketer.UserID);

        rInterface = Retrofite.getClient().create(RWInterface.class);
        Call cGetUser = rInterface.RQGetProfileByCode(Setting.getToken(), new HashMap<>(map));
        cGetUser.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if(response.isSuccessful()){
                    try {
                        data = response.body();

                        String UserName = data.Firstname + " " + data.Lastname;
                        lblName.setText(UserName);
                        DateConverter DC = new DateConverter();
                        lblBirthDay.setText(DC.getToHijri(data.BirthDate));
                        lblSex.setText(data.GenderTypeID == 1 ? "مرد" : "زن");
                        lblMarri.setText(data.MaritalStatusID == 1 ? "متاهل" : "مجرد");
                        lblNationality.setText(data.NationalCode);
                        lblAddress.setText(data.Address);
                        Glide.with(context).load(data.PhotoFilename).asBitmap().centerCrop().into(new BitmapImageViewTarget(imgProfile) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable circularBitmapDrawable =
                                        RoundedBitmapDrawableFactory.create(getActivity().getResources(), resource);
                                circularBitmapDrawable.setCircular(true);
                                imgProfile.setImageDrawable(circularBitmapDrawable);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        data = new Profile();
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                String Er = t.getMessage();
                //Nothing
            }
        });

        return view;
    }

}
