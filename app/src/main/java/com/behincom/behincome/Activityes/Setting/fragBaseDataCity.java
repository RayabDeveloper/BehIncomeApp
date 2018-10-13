package com.behincom.behincome.Activityes.Setting;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.behincom.behincome.Accesories.Dialog;
import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Adapters.Setting.adapBasicDataCity;
import com.behincom.behincome.Adapters.Setting.adapBasicDataProvince;
import com.behincom.behincome.Adapters.Setting.adapSettingMainItems;
import com.behincom.behincome.Adapters.Setting.adapSettingSubItems;
import com.behincom.behincome.Adapters.SpinAdapter;
import com.behincom.behincome.Datas.Base.Basics;
import com.behincom.behincome.Datas.BaseData.Basic_ActivityFieldGroups;
import com.behincom.behincome.Datas.BaseData.Basic_ActivityFields;
import com.behincom.behincome.Datas.BaseData.Basic_AndroidKeyboardTypes;
import com.behincom.behincome.Datas.BaseData.Basic_Cities;
import com.behincom.behincome.Datas.BaseData.Basic_Properties;
import com.behincom.behincome.Datas.BaseData.Basic_PropertyGroups;
import com.behincom.behincome.Datas.BaseData.Basic_Provinces;
import com.behincom.behincome.Datas.BaseData.Basic_TagGroups;
import com.behincom.behincome.Datas.BaseData.Basic_Tags;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.Keys.ResponseMessageType;
import com.behincom.behincome.Datas.Keys.Tables;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.Datas.Result.SimpleResponse;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.behincom.behincome.WebRequest.RWInterface;
import com.behincom.behincome.WebRequest.Retrofite;
import com.behincom.behincome.app.AppController;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragBaseDataCity extends Fragment {

    static Context context = AppController.getContext;
    static RWInterface rInterface;
    adapBasicDataProvince adapterMain;
    static adapBasicDataCity adapterSub;
    public static Initializer objects;
    public static int Position = 0;
    static actSetting act = new actSetting();
    private static Dialog pDialog;
    RSQLite SQL = new RSQLite();
    private static RSQLGeter geter = new RSQLGeter();

    static RecyclerView lstMain, lstSub;
    ImageView imgBack, btnCheck;
    TextView lblTitle;
    Switch sw;
    static LinearLayout linMain;

    private static List<Basic_Provinces> lProvince = new ArrayList<>();
    private static List<Basic_Cities> lCity = new ArrayList<>();
    private static int MainIdSelected = 0;
    public static String IdsToSend = "";

    public static fragBaseDataCity newInstance(Context mContext) {
        fragBaseDataCity fragment = new fragBaseDataCity();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_base_data_city, container, false);

        sw = view.findViewById(R.id.sw);
        linMain = view.findViewById(R.id.linMain);
        lstMain = view.findViewById(R.id.lstMain);
        lstSub = view.findViewById(R.id.lstSub);
        imgBack = view.findViewById(R.id.imgBack);
        btnCheck = view.findViewById(R.id.btnCheck);
        lblTitle = view.findViewById(R.id.lblTitle);

        lblTitle.setText("انتخاب شهر و استان");
        imgBack.setVisibility(View.VISIBLE);
        btnCheck.setVisibility(View.VISIBLE);
        rInterface = Retrofite.getClient().create(RWInterface.class);
        IdsToSend = "";

        sw.setChecked(true);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    showMain();
                else
                    hideMain();
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Position = 0;
                act.getFragByState(FragmentState.Setting);
            }
        });
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                RequestManager();
                act.getFragByState(FragmentState.Setting);
            }
        });

        lstMain.setLayoutManager(new LinearLayoutManager(context));
        lstMain.setHasFixedSize(true);
        lstMain.addItemDecoration(new HorizontalDividerItemDecoration.Builder(context).colorResId(R.color.txtBlue).size(2).build());
        lstMain.setItemAnimator(new DefaultItemAnimator());
        lstSub.setLayoutManager(new LinearLayoutManager(context));
        lstSub.setHasFixedSize(true);
        lstSub.addItemDecoration(new HorizontalDividerItemDecoration.Builder(context).colorResId(R.color.BaseBackgroundColor).size(2).build());
        lstSub.setItemAnimator(new DefaultItemAnimator());

        lProvince = geter.getList(Basic_Provinces.class, " WHERE Deleted='0'");
        lCity = geter.getList(Basic_Cities.class, " WHERE Deleted='0'");

        adapterMain = new adapBasicDataProvince(lProvince, context);
        adapterSub = new adapBasicDataCity(lCity, context);
        lstMain.setAdapter(adapterMain);
        lstSub.setAdapter(adapterSub);

        FilterSubItemsFromMainItemSelected(lProvince.get(0).ProvinceID);

        return view;
    }

    public void FilterSubItemsFromMainItemSelected(int ParrentId) {
        MainIdSelected = ParrentId;
        try {
            lCity = geter.getList(Basic_Cities.class, " WHERE ProvinceID='" + ParrentId + "' AND Deleted='0'");
            try {
                for (Basic_Cities data : lCity) {
                    int IdValue = data.CityID;
                    for (int dat : getSelectedItems()) {
                        if (IdValue == dat) {
                            data.isCheck = true;
                            break;
                        } else {
                            data.isCheck = false;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            adapterMain = new adapBasicDataProvince(lProvince, context);
            lstMain.setAdapter(adapterMain);
            adapterMain.notifyDataSetChanged();
            adapterSub = new adapBasicDataCity(lCity, context);
            lstSub.setAdapter(adapterSub);
            adapterSub.notifyDataSetChanged();
        } catch (Exception Ex) {
            String Er = Ex.getMessage();
        }
    }
    private List<Integer> getSelectedItems() {
        List<Integer> Reterner = new ArrayList<>();
        List<Basic_Cities> MarketingItems = new ArrayList<>();
        MarketingItems = geter.getListIsCheck(Basic_Cities.class, "ProvinceID='" + MainIdSelected + "'");

        for(Basic_Cities data : MarketingItems){
            Reterner.add(data.CityID);
        }

        return Reterner;
    }
    @Override
    public void onResume() {
        super.onResume();
        for (Basic_Cities data : lCity) {
            if (data.isCheck)
                IdsToSend += data.CityID + ", ";
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Position = 0;
        MainIdSelected = 0;
    }
    public static void RequestManager(final boolean isCheck, final int Id) {
        pDialog = new Dialog(context);
        pDialog.Show();

        final RSQLite SQL = new RSQLite();
        List<Integer> IDs = new ArrayList<>();
        IDs.add(Id);
        Map<String, Object> CityList = new HashMap<>();
        CityList.put("Ids", IDs);

        if(isCheck) {
            Call AddCity = rInterface.RQAddMarketingCities(Setting.getToken(), new HashMap<>(CityList));
            AddCity.enqueue(new Callback<SimpleResponse>() {
                @Override
                public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                    if (response.isSuccessful()) {
                        SQL.Execute("UPDATE " + Tables.Basic_Cities + " SET isCheck='1' WHERE CityID='" + Id + "'");
                    }
                    pDialog.DisMiss();
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    pDialog.DisMiss();
                }
            });
        }else{
            Call AddCity = rInterface.RQRemoveMarketingCities(Setting.getToken(), new HashMap<>(CityList));
            AddCity.enqueue(new Callback<SimpleResponse>() {
                @Override
                public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                    if (response.isSuccessful()) {
                        SQL.Execute("UPDATE " + Tables.Basic_Cities + " SET isCheck='0' WHERE CityID='" + Id + "'");
                    }
                    pDialog.DisMiss();
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    pDialog.DisMiss();
                }
            });
        }
    }

    public static void hideMain(){
        try {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linMain.getLayoutParams();
            params.width = 90;
            linMain.setLayoutParams(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void showMain(){
        try {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linMain.getLayoutParams();
            params.width = params.MATCH_PARENT;
            linMain.setLayoutParams(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
