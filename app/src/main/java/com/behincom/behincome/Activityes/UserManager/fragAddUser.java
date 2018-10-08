package com.behincom.behincome.Activityes.UserManager;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.behincom.behincome.Accesories.DateConverter;
import com.behincom.behincome.Accesories.Dialog;
import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Adapters.SpinAdapter;
import com.behincom.behincome.Adapters.UserManager.adapMarketers;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.Profile.Marketers;
import com.behincom.behincome.Datas.Profile.Profile;
import com.behincom.behincome.Datas.Roles.User_Roles;
import com.behincom.behincome.R;
import com.behincom.behincome.WebRequest.RWInterface;
import com.behincom.behincome.WebRequest.Retrofite;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragAddUser extends Fragment {

    static Context context;
    Dialog pDialog;
    android.app.Dialog mDialog;
    RWInterface rInterface;
    actUserManager act = new actUserManager();

    TextView lblTitle;
    TextView lblSearcheTitle;
    TextView lblAccept;
    TextView lblUserPhone2;
    TextView lblName;
    TextView lblBirthDay;
    TextView lblSex;
    TextView lblMarri;
    TextView lblNationality;
    TextView lblAddress;
    TextView lblAbout;
    EditText txtSearche;
    ImageView imgBack;
    ImageView imgProfile;
    ImageView btnCheck;
    LinearLayout linProfileProfile;
    CardView cardView;
    CardView cardAcceptTask;
    ScrollView scrollV;

    private static Profile data = new Profile();
    private static int UserID = 0;
    private static String UserName = "";
    public static List<Marketers> lMarketers = new ArrayList<>();

    public static fragAddUser newInstance(Context mContext){
        fragAddUser fragment = new fragAddUser();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_add_user, container, false);

        lblTitle = view.findViewById(R.id.lblTitle);
        lblSearcheTitle = view.findViewById(R.id.lblSearcheTitle);
        txtSearche = view.findViewById(R.id.txtSearche);
        lblAccept = view.findViewById(R.id.lblAccept);
        imgBack = view.findViewById(R.id.imgBack);
        linProfileProfile = view.findViewById(R.id.linProfileProfile);
        cardView = view.findViewById(R.id.cardView);
        scrollV = view.findViewById(R.id.scrollV);
        lblUserPhone2 = view.findViewById(R.id.lblUserPhone2);
        lblName = view.findViewById(R.id.lblName);
        lblBirthDay = view.findViewById(R.id.lblBirthDay);
        lblSex = view.findViewById(R.id.lblSex);
        lblMarri = view.findViewById(R.id.lblMarri);
        lblNationality = view.findViewById(R.id.lblNationality);
        lblAddress = view.findViewById(R.id.lblAddress);
        lblAbout = view.findViewById(R.id.lblAbout);
        imgProfile = view.findViewById(R.id.imgProfile);
        btnCheck = view.findViewById(R.id.btnCheck);
        cardAcceptTask = view.findViewById(R.id.cardAcceptTask);

        rInterface = Retrofite.getClient().create(RWInterface.class);

        lblTitle.setText("افزودن کارمند");
        imgBack.setVisibility(View.VISIBLE);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getFragByState(FragmentState.Marketers);
            }
        });
        btnCheck.setVisibility(View.GONE);

        txtSearche.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("UserID", txtSearche.getText().toString());

                Call cGetUser = rInterface.RQGetProfileByCode(Setting.getToken(), new HashMap<>(map));
                cGetUser.enqueue(new Callback<Profile>() {
                    @Override
                    public void onResponse(Call<Profile> call, Response<Profile> response) {
                        if(response.isSuccessful()){
                            try {
                                data = response.body();

                                linProfileProfile.setVisibility(View.VISIBLE);
                                btnCheck.setVisibility(View.VISIBLE);

                                UserID = data.UserID;
                                UserName = data.Firstname + " " + data.Lastname;
                                lblName.setText(UserName);
                                DateConverter DC = new DateConverter();
                                lblBirthDay.setText(DC.getToHijri(data.BirthDate));
                                lblSex.setText(data.GenderTypeID == 1 ? "مرد" : "زن");
                                lblMarri.setText(data.MaritalStatusID == 1 ? "متاهل" : "مجرد");
                                lblNationality.setText(data.NationalCode);
                                lblAddress.setText(data.Address);
//                                lblAbout.setText(data.);AboutMe
//                                Glide.with(context).load(data.PhotoFilename).asBitmap().into(new BitmapImageViewTarget(imgProfile) {});
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
                                linProfileProfile.setVisibility(View.GONE);
                                btnCheck.setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        String Er = t.getMessage();
                        //Nothing
                    }
                });
            }
            @Override
            public void afterTextChanged(Editable s) {
                String tt = "ASD";
            }
        });

        cardAcceptTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog = new Dialog(getActivity());
                pDialog.Show();

                Call cGetRoles = rInterface.RQGetBusinessManagerRoles(Setting.getToken());
                cGetRoles.enqueue(new Callback<List<User_Roles>>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(Call<List<User_Roles>> call, Response<List<User_Roles>> response) {
                        pDialog.DisMiss();
                        if (response.isSuccessful()) {
                            mDialog = new android.app.Dialog(getActivity());
                            mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            mDialog.setCancelable(true);
                            mDialog.setCanceledOnTouchOutside(true);
                            mDialog.setContentView(R.layout.dialog_ser_role_to_new_user);
                            Objects.requireNonNull(mDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

                            final Spinner spinRoles = mDialog.findViewById(R.id.spinRoles);
                            TextInputEditText txtMarketerName = mDialog.findViewById(R.id.txtMarketerName);
                            TextView lblCancell = mDialog.findViewById(R.id.lblCancell);
                            TextView lblAccept = mDialog.findViewById(R.id.lblAccept);
                            TextView lblSubmiteDate = mDialog.findViewById(R.id.lblSubmiteDate);
                            lblCancell.setVisibility(View.GONE);

                            List<User_Roles> lRoles = response.body();
                            final SpinAdapter spinAdap = new SpinAdapter(getActivity(), lRoles, "RoleName");
                            spinRoles.setAdapter(spinAdap);
                            txtMarketerName.setText(UserName);

                            String[] Datess = Setting.getServerDateTime().split("T");
                            String[] mDate = Datess[0].split("-");

                            lblSubmiteDate.setText(mDate[0] + "/" + mDate[1] + "/" + mDate[2]);
                            
                            lblAccept.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mDialog.dismiss();
                                    pDialog.Show();

                                    HashMap<String, Object> hMap = new HashMap<>();
                                    hMap.put("UserID", UserID);
                                    hMap.put("RoleID", Integer.parseInt(spinAdap.getItemString(spinRoles.getSelectedItemPosition(), "RoleID")));
                                    hMap.put("MarketerName", UserName);

                                    Call cAddUser = rInterface.RQAddMarketer(Setting.getToken(), new HashMap<>(hMap));
                                    cAddUser.enqueue(new Callback() {
                                        @Override
                                        public void onResponse(Call call, Response response) {
                                            pDialog.DisMiss();
                                            if(response.isSuccessful()){
                                                act.getFragByState(FragmentState.Marketers);
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call call, Throwable t) {
                                            pDialog.DisMiss();
                                        }
                                    });
                                }
                            });
                            lblCancell.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mDialog.dismiss();
                                }
                            });
                            mDialog.show();
                        }
                        pDialog.DisMiss();
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        pDialog.DisMiss();
                    }
                });
            }
        });

        return view;
    }

}
