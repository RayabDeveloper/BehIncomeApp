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
import com.behincom.behincome.Adapters.Setting.adapSettingMainItems;
import com.behincom.behincome.Adapters.Setting.adapSettingSubItems;
import com.behincom.behincome.Adapters.SpinAdapter;
import com.behincom.behincome.Datas.Base.Basics;
import com.behincom.behincome.Datas.BaseData.Basic_ActivityFieldGroups;
import com.behincom.behincome.Datas.BaseData.Basic_ActivityFields;
import com.behincom.behincome.Datas.BaseData.Basic_AndroidKeyboardTypes;
import com.behincom.behincome.Datas.BaseData.Basic_Properties;
import com.behincom.behincome.Datas.BaseData.Basic_PropertyGroups;
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
import com.google.gson.Gson;
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

public class fragBasicData<T> extends Fragment {

    static Context context = AppController.getContext;
    static RWInterface rInterface;
    adapSettingMainItems adapterMain;
    static adapSettingSubItems adapterSub;
    public static Initializer objects;
    public static int Position = 0;
    static actSetting act = new actSetting();
    android.app.Dialog mDialog, sDialog;
    private static Dialog pDialog;
    static SpinAdapter spinAdap;
    static SpinAdapter spinAdapop;
    RSQLite SQL = new RSQLite();
    private static RSQLGeter geter = new RSQLGeter();

    static RecyclerView lstMain, lstSub;
    static Spinner spinType;
    ImageView imgBack, btnCheck, btnSearche;
    TextView lblTitle, lblMain, lblSub;
    LinearLayout btnMainAdd, linSub;
    Switch sw;
    static LinearLayout btnSubAdd;
    static LinearLayout linMain;
    static LinearLayout ViewEditor;
    static LinearLayout btnDelete;
    static LinearLayout btnUpdate;
    private List<T> SubItems = new ArrayList<>();

    private static int MainIdSelected = 0;
    private Object object;

    public static fragBasicData newInstance(Context mContext) {
        fragBasicData fragment = new fragBasicData();
        context = mContext;
        return fragment;
    }

    public static String IdsToSend = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_basic_data, container, false);

        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnDelete = view.findViewById(R.id.btnDeleter);
        sw = view.findViewById(R.id.sw);
        linMain = view.findViewById(R.id.linMain);
        ViewEditor = view.findViewById(R.id.ViewEditor);
        linSub = view.findViewById(R.id.linSub);
        btnMainAdd = view.findViewById(R.id.btnMainAdd);
        btnSubAdd = view.findViewById(R.id.btnSubAdd);
        lblMain = view.findViewById(R.id.lblMain);
        lblSub = view.findViewById(R.id.lblSub);
        lstMain = view.findViewById(R.id.lstMain);
        lstSub = view.findViewById(R.id.lstSub);
        imgBack = view.findViewById(R.id.imgBack);
        btnCheck = view.findViewById(R.id.btnCheck);
        btnSearche = view.findViewById(R.id.btnSearche);
        lblTitle = view.findViewById(R.id.lblTitle);

        lblTitle.setText(objects.FragTitle());
        imgBack.setVisibility(View.VISIBLE);
        btnCheck.setVisibility(View.VISIBLE);
        rInterface = Retrofite.getClient().create(RWInterface.class);
        ViewEditor.setVisibility(View.INVISIBLE);
        IdsToSend = "";

        lblMain.setText("گروه " + objects.FragTitle());
        lblSub.setText("مجموعه " + objects.FragTitle());

        if(objects.MainFieldIdName().equalsIgnoreCase("ProvinceID")){
            btnMainAdd.setVisibility(View.GONE);
        }

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
//        linMain.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showMain();
//            }
//        });
//        linSub.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hideMain();
//            }
//        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideDown(ViewEditor);
                if(ifMain)
                    ifMainDelete();
                else
                    ifSubDelete();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideDown(ViewEditor);
                if(ifMain)
                    ifMainUpdate();
                else
                    ifSubUpdate();
            }
        });
        btnMainAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog = new android.app.Dialog(context);
                mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                mDialog.setCancelable(true);
                mDialog.setCanceledOnTouchOutside(true);
                mDialog.setContentView(R.layout.dialog_setting_basic_main);
                Objects.requireNonNull(mDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

                TextView btnAccept = mDialog.findViewById(R.id.lblAccept);
                TextView btnCancell = mDialog.findViewById(R.id.lblCancell);
                final TextInputEditText txtTitle = mDialog.findViewById(R.id.txtTitle);
                final AppCompatRadioButton radGroup = mDialog.findViewById(R.id.radGroup);
                AppCompatRadioButton radSingle = mDialog.findViewById(R.id.radSingle);
                RadioGroup radGrouper = mDialog.findViewById(R.id.radGrouper);
                CardView btnSetIcon = mDialog.findViewById(R.id.btnSetIcon);

                boolean isProp = objects.SubClass().getSimpleName().equalsIgnoreCase("Basic_Properties") || objects.SubClass().getSimpleName().equalsIgnoreCase("Basic_ActivityFields");
                if(!isProp)
                    radGrouper.setVisibility(View.VISIBLE);
                else
                    radGrouper.setVisibility(View.GONE);

                btnAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                        GroupAddManager(txtTitle.getText().toString(), radGroup.isChecked() ? 2 : 3);
                    }
                });
                btnCancell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });
                btnSetIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                mDialog.show();
            }
        });
        btnSubAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sDialog = new android.app.Dialog(context);
                sDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                sDialog.setCancelable(true);
                sDialog.setCanceledOnTouchOutside(true);
                sDialog.setContentView(R.layout.dialog_setting_basic_sub);
                Objects.requireNonNull(sDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

                TextView btnAccept = sDialog.findViewById(R.id.lblAccept);
                TextView btnCancell = sDialog.findViewById(R.id.lblCancell);
                TextView lblTitle = sDialog.findViewById(R.id.lblTitle00);
                final TextInputEditText txtTitle = sDialog.findViewById(R.id.txtTitle);
                spinType = sDialog.findViewById(R.id.spinType);

                String fName = "";
                try {
                    for (int i = 0; i < objects.MainItems().size(); i++) {
                        Object mObject = objects.MainItems().get(i).getClass().newInstance();
                        Field[] fields = mObject.getClass().getDeclaredFields();
                        for (Field field : fields) {
                            if (field.getName().contains(objects.MainFieldIdName())) {
                                try {
                                    int Id = Integer.parseInt(field.get(objects.MainItems().get(i)).toString());
                                    if (Id == MainIdSelected) {
                                        for (Field field2 : fields) {
                                            if (field2.getName().contains(objects.MainFieldTitleName())) {
                                                try {
                                                    fName = field2.get(objects.MainItems().get(i)).toString();
                                                    break;
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                } catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                lblTitle.setText(fName);

                final boolean isProp = objects.SubClass().getSimpleName().equalsIgnoreCase("Basic_Properties");
                if(isProp) {
                    List<Basic_AndroidKeyboardTypes> lKeyboard = geter.getList(Basic_AndroidKeyboardTypes.class, " WHERE Deleted='0'");
                    spinAdap = new SpinAdapter(context, lKeyboard, "AndroidKeyboardTypeTitle");
                    spinType.setAdapter(spinAdap);
                    spinType.setVisibility(View.VISIBLE);
                }else{
                    spinType.setVisibility(View.GONE);
                }

                btnAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sDialog.dismiss();
                        if(isProp)
                            SubAddManager(txtTitle.getText().toString(), Integer.parseInt(spinAdap.getItemString(spinType.getSelectedItemPosition(), "AndroidKeyboardTypeID")));
                        else
                            SubAddManager(txtTitle.getText().toString(), 0);
                    }
                });
                btnCancell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sDialog.dismiss();
                    }
                });

                sDialog.show();
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

        adapterMain = new adapSettingMainItems<>(objects.MainItems(), objects.MainFieldIdName(), objects.MainFieldTitleName());
        adapterSub = new adapSettingSubItems<>(objects.SubItems(), objects.SubFieldTitleName(), objects.SubFieldIdName(), objects.MainFieldIdName());
        lstMain.setAdapter(adapterMain);
        lstSub.setAdapter(adapterSub);


        Field[] fields = objects.MainItems().get(0).getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().contains(objects.MainFieldIdName())) {
                try {
                    FilterSubItemsFromMainItemSelected(Integer.parseInt(field.get(objects.MainItems().get(0)).toString()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        SubAdderViewer( 0);

        return view;
    }
    public void FilterSubItemsFromMainItemSelected(int ParrentId) {
        MainIdSelected = ParrentId;
        try {
            SubItems = geter.getListByParrentId(objects.SubClass(), objects.MainFieldIdName(), ParrentId);
            try {
                for (T data : SubItems) {
                    Field[] fields = data.getClass().getDeclaredFields();
                    int IdValue = 0;
                    try {
                        for (Field field : fields) {
                            if (field.getName().contains(objects.SubFieldIdName())) {
                                try {
                                    IdValue = Integer.parseInt(field.get(data).toString());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            if (field.getName().contains("isCheck")) {
                                try {
                                    try {
                                        for (int dat : getSelectedItems()) {
                                            if (IdValue == dat) {
                                                field.set(data, true);
                                                break;
                                            } else {
                                                field.set(data, false);
                                            }
                                        }
                                    } catch (IllegalArgumentException e) {
                                        e.printStackTrace();
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            adapterMain = new adapSettingMainItems<>(objects.MainItems(), objects.MainFieldIdName(), objects.MainFieldTitleName());
            lstMain.setAdapter(adapterMain);
            adapterMain.notifyDataSetChanged();
            adapterSub = new adapSettingSubItems<>(SubItems, objects.SubFieldTitleName(), objects.SubFieldIdName(), objects.MainFieldIdName());
            lstSub.setAdapter(adapterSub);
            adapterSub.notifyDataSetChanged();
        } catch (Exception Ex) {
            String Er = Ex.getMessage();
        }
    }
    private List<Integer> getSelectedItems() {
        List<Integer> Reterner = new ArrayList<>();
        List<T> MarketingItems = new ArrayList<>();
        MarketingItems = geter.getListIsCheck(objects.SubClass(), objects.MainFieldIdName() + "='" + MainIdSelected + "'");

        for(T data : MarketingItems){
            Field[] fields = data.getClass().getDeclaredFields();
            int IdValue = 0;
            try {
                for (Field field : fields) {
                    if (field.getName().contains(objects.SubFieldIdName())) {
                        try {
                            Reterner.add(Integer.parseInt(field.get(data).toString()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return Reterner;
    }
    @Override
    public void onResume() {
        super.onResume();
        for (Object data : objects.SubItems()) {
            Field[] fields = data.getClass().getDeclaredFields();
            int IdValue = 0;
            for (Field field : fields) {
                if (field.getName().contains(objects.SubFieldIdName())) {
                    try {
                        IdValue = Integer.parseInt(field.get(data).toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (field.getName().contains("isCheck")) {
                    try {
                        if (Boolean.parseBoolean(field.get(data).toString()))
                            IdsToSend += IdValue + ", ";
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
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
        switch (objects.SubClass().getSimpleName()) {
            case "Basic_ActivityFields":
                Map<String, Object> ActivityFieldsList = new HashMap<>();
                ActivityFieldsList.put("Ids", IDs);

                if(isCheck) {
                    Call AddActivityFields = rInterface.RQAddMarketingActivityFields(Setting.getToken(), new HashMap<>(ActivityFieldsList));
                    AddActivityFields.enqueue(new Callback<SimpleResponse>() {
                        @Override
                        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                            if (response.isSuccessful()) {
                                SQL.Execute("UPDATE " + Tables.Basic_ActivityFields + " SET isCheck='1' WHERE ActivityFieldID='" + Id + "'");
                            }
                            pDialog.DisMiss();
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            pDialog.DisMiss();
                        }
                    });
                } else {
                    Call AddActivityFields = rInterface.RQRemoveMarketingActivityFields(Setting.getToken(), new HashMap<>(ActivityFieldsList));
                    AddActivityFields.enqueue(new Callback<SimpleResponse>() {
                        @Override
                        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                            if (response.isSuccessful()) {
                                SQL.Execute("UPDATE " + Tables.Basic_ActivityFields + " SET isCheck='0' WHERE ActivityFieldID='" + Id + "'");
                            }
                            pDialog.DisMiss();
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            pDialog.DisMiss();
                        }
                    });
                }
                break;
            case "Basic_Cities":
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
                break;
            case "Basic_Tags":
                Map<String, Object> TagList = new HashMap<>();
                TagList.put("Ids", IDs);

                if (isCheck) {
                    Call AddTag = rInterface.RQAddMarketingTag(Setting.getToken(), new HashMap<>(TagList));
                    AddTag.enqueue(new Callback<SimpleResponse>() {
                        @Override
                        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                            if (response.isSuccessful()) {
                                SQL.Execute("UPDATE " + Tables.Basic_Tags + " SET isCheck='1' WHERE TagID='" + Id + "'");
                            }
                            pDialog.DisMiss();
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            pDialog.DisMiss();
                        }
                    });
                } else {
                    Call AddTag = rInterface.RQRemoveMarketingTag(Setting.getToken(), new HashMap<>(TagList));
                    AddTag.enqueue(new Callback<SimpleResponse>() {
                        @Override
                        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                            if (response.isSuccessful()) {
                                SQL.Execute("UPDATE " + Tables.Basic_Tags + " SET isCheck='0' WHERE TagID='" + Id + "'");
                            }
                            pDialog.DisMiss();
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            pDialog.DisMiss();
                        }
                    });
                }
                break;
            case "Basic_Properties":
                Map<String, Object> PropertyList = new HashMap<>();
                PropertyList.put("Ids", IDs);

                if (isCheck) {
                    Call AddProperty = rInterface.RQAddMarketingProperties(Setting.getToken(), new HashMap<>(PropertyList));
                    AddProperty.enqueue(new Callback<SimpleResponse>() {
                        @Override
                        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                            if (response.isSuccessful()) {
                                SQL.Execute("UPDATE " + Tables.Basic_Properties + " SET isCheck='1' WHERE PropertyID='" + Id + "'");
                            }
                            pDialog.DisMiss();
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            pDialog.DisMiss();
                        }
                    });
                } else {
                    Call AddProperty = rInterface.RQRemoveMarketingProperties(Setting.getToken(), new HashMap<>(PropertyList));
                    AddProperty.enqueue(new Callback<SimpleResponse>() {
                        @Override
                        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                            if (response.isSuccessful()) {
                                SQL.Execute("UPDATE " + Tables.Basic_Properties + " SET isCheck='0' WHERE PropertyID='" + Id + "'");
                            }
                            pDialog.DisMiss();
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            pDialog.DisMiss();
                        }
                    });
                }
                break;
        }
    }

    public static void hideMain(){
        try {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linMain.getLayoutParams();
            params.width = 170;
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
    public static void SubAdderViewer(int position){
        boolean isAdmin = true;
        Field[] fields = objects.MainItems().get(position).getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                if (field.getName().contains("ByAdmin")) {
                    try {
                        String msd = field.get(objects.MainItems().get(position)).toString();
                        if(msd.equalsIgnoreCase("true"))
                            isAdmin = true;
                        else
                            isAdmin = false;
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }catch (Exception Ex){
            String Er = Ex.getMessage();
        }
        if(isAdmin){
            btnSubAdd.setVisibility(View.GONE);
        }else{
            btnSubAdd.setVisibility(View.VISIBLE);
        }
    }

    private int wichAPI(){
        switch (objects.SubClass().getSimpleName()){
            case "Basic_ActivityFields":
                return 1;
            case "Basic_Cities":
                return 2;
            case "Basic_Tags":
                return 3;
            case "Basic_Properties":
                return 4;
        }
        return 0;
    }
    private void GroupAddManager(final String Title, final int Type){
        Map<String, Object> BodyParameters = new HashMap<>();
        Call Insert;
        switch (wichAPI()){
            case 1://ActivityField
                BodyParameters = new HashMap<>();
                BodyParameters.put("ActivityFieldGroupTitle", Title);
                BodyParameters.put("ActivityFieldGroupOrder", 0);
                BodyParameters.put("ActivityFieldGroupID", 0);
                BodyParameters.put("ActivityFieldGroupFontIcon", "a");
                BodyParameters.put("ActivityFieldGroupColor", "a");
                BodyParameters.put("Deleted", false);

                Gson gsonh = new Gson();
                String jsonh = gsonh.toJson(BodyParameters);

                Insert = rInterface.RQInsertBasicActivityFieldGroups(Setting.getToken(), new HashMap<>(BodyParameters));
                Insert.enqueue(new Callback<SimpleResponse>() {
                    @Override
                    public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                        if (response.isSuccessful()) {
                            SimpleResponse simple = response.body();
                            if(simple.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())){
                                Map<String, Object> addional = simple.AdditionalData;
                                String mID = addional.get("ItemId").toString();
                                int Id = Integer.parseInt(mID.replace(".0", ""));
                                Basic_ActivityFieldGroups data = new Basic_ActivityFieldGroups();
                                data.ActivityFieldGroupID = Id;
                                data.ActivityFieldGroupTitle = Title;
                                data.AdjustedByAdmin = false;
                                data.isCheck = true;
                                SQL.Insert(data);

                                objects.MainItems(geter.getList(Basic_ActivityFieldGroups.class, " WHERE Deleted='0'"));
                                adapterMain = new adapSettingMainItems<>(objects.MainItems(), objects.MainFieldIdName(), objects.MainFieldTitleName());
                                lstMain.setAdapter(adapterMain);
                            }else if(simple.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())){
                                String Err = "";
                                for (Map.Entry<String, Object> entry : simple.Errors.entrySet()) {
                                    Err = entry.getValue().toString();
                                }
                                Toast.makeText(context, Err, Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(context, Basics.ServerError, Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case 3://Tag
                BodyParameters = new HashMap<>();
                BodyParameters.put("TagGroupTitle", Title);
                BodyParameters.put("TagGroupID", 0);
                BodyParameters.put("TagGroupOrder", 0);
                BodyParameters.put("TagGroupFontIcon", "a");
                BodyParameters.put("TagGroupColor", "a");
                BodyParameters.put("TagGroupTypeId", Type);

                Gson gson = new Gson();
                String json = gson.toJson(BodyParameters);

                Insert = rInterface.RQInsertBasicTagGroups(Setting.getToken(), new HashMap<>(BodyParameters));
                Insert.enqueue(new Callback<SimpleResponse>() {
                    @Override
                    public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                        if (response.isSuccessful()) {
                            SimpleResponse simple = response.body();
                            if(simple.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())){
                                Map<String, Object> addional = simple.AdditionalData;
                                String mID = addional.get("ItemId").toString();
                                int Id = Integer.parseInt(mID.replace(".0", ""));
                                Basic_TagGroups data = new Basic_TagGroups();
                                data.TagGroupID = Id;
                                data.TagGroupTitle = Title;
                                data.TagGroupAdjustedByAdmin = false;
                                data.TagGroupTypeId = Type;
                                data.isCheck = true;
                                SQL.Insert(data);

                                objects.MainItems(geter.getList(Basic_TagGroups.class, " WHERE Deleted='0'"));
                                adapterMain = new adapSettingMainItems<>(objects.MainItems(), objects.MainFieldIdName(), objects.MainFieldTitleName());
                                lstMain.setAdapter(adapterMain);
                            }else if(simple.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())){
                                String Err = "";
                                for (Map.Entry<String, Object> entry : simple.Errors.entrySet()) {
                                    Err = entry.getValue().toString();
                                }
                                Toast.makeText(context, Err, Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(context, Basics.ServerError, Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case 4://Property
                BodyParameters = new HashMap<>();
                BodyParameters.put("PropertyGroupID", 0);
                BodyParameters.put("PropertyGroupTitle", Title);
                BodyParameters.put("PropertyGroupOrder", 0);
                BodyParameters.put("PropertyGroupFontIcon", "a");
                BodyParameters.put("PropertyGroupColor", "a");

                Gson gsonn = new Gson();
                String jsonn = gsonn.toJson(BodyParameters);

                Insert = rInterface.RQInsertBasicPropertieGroups(Setting.getToken(), new HashMap<>(BodyParameters));
                Insert.enqueue(new Callback<SimpleResponse>() {
                    @Override
                    public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                        if (response.isSuccessful()) {
                            SimpleResponse simple = response.body();
                            if(simple.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())){
                                Map<String, Object> addional = simple.AdditionalData;
                                String mID = addional.get("ItemId").toString();
                                int Id = Integer.parseInt(mID.replace(".0", ""));
                                Basic_PropertyGroups data = new Basic_PropertyGroups();
                                data.PropertyGroupID = Id;
                                data.PropertyGroupTitle = Title;
                                data.PropertyGroupAdjustedByAdmin = false;
                                data.isCheck = true;
                                SQL.Insert(data);

                                objects.MainItems(geter.getList(Basic_PropertyGroups.class, " WHERE Deleted='0'"));
                                adapterMain = new adapSettingMainItems<>(objects.MainItems(), objects.MainFieldIdName(), objects.MainFieldTitleName());
                                lstMain.setAdapter(adapterMain);
                            }else if(simple.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())){
                                String Err = "";
                                for (Map.Entry<String, Object> entry : simple.Errors.entrySet()) {
                                    Err = entry.getValue().toString();
                                }
                                Toast.makeText(context, Err, Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(context, Basics.ServerError, Toast.LENGTH_LONG).show();
                    }
                });
                break;
        }
    }
    private void SubAddManager(final String Title, final int TypeKeyboard){
        Map<String, Object> BodyParameters = new HashMap<>();
        Call Insert;
        switch (wichAPI()){
            case 1://ActivityField
                BodyParameters = new HashMap<>();
                BodyParameters.put("ActivityFieldGroupID", MainIdSelected);
                BodyParameters.put("ActivityFieldID", 0);
                BodyParameters.put("ActivityFieldOrder", 0);
                BodyParameters.put("ActivityFieldTitle", Title);
                BodyParameters.put("Deleted", false);
                BodyParameters.put("ActivityFieldFontIcon", "");

                Gson gsonh = new Gson();
                String jsonh = gsonh.toJson(BodyParameters);

                Insert = rInterface.RQInsertBasicActivityFields(Setting.getToken(), new HashMap<>(BodyParameters));
                Insert.enqueue(new Callback<SimpleResponse>() {
                    @Override
                    public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                        if (response.isSuccessful()) {
                            SimpleResponse simple = response.body();
                            if(simple.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())){
                                Map<String, Object> addional = simple.AdditionalData;
                                String mID = addional.get("ItemId").toString();
                                int Id = Integer.parseInt(mID.replace(".0", ""));
                                Basic_ActivityFields data = new Basic_ActivityFields();
                                data.ActivityFieldID = Id;
                                data.ActivityFieldOrder = "1";
                                data.ActivityFieldGroupID = MainIdSelected;
                                data.ActivityFieldTitle = Title;
                                data.isCheck = true;
                                SQL.Insert(data);

                                FilterSubItemsFromMainItemSelected(MainIdSelected);
                            }else if(simple.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())){
                                String Err = "";
                                for (Map.Entry<String, Object> entry : simple.Errors.entrySet()) {
                                    Err = entry.getValue().toString();
                                }
                                Toast.makeText(context, Err, Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(context, Basics.ServerError, Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case 3://Tag
                BodyParameters = new HashMap<>();
                BodyParameters.put("TagGroupID", MainIdSelected);
                BodyParameters.put("TagID", 0);
                BodyParameters.put("TagOrder", 0);
                BodyParameters.put("TagTitle", Title);

                Gson gson = new Gson();
                String json = gson.toJson(BodyParameters);

                Insert = rInterface.RQInsertBasicTags(Setting.getToken(), new HashMap<>(BodyParameters));
                Insert.enqueue(new Callback<SimpleResponse>() {
                    @Override
                    public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                        if (response.isSuccessful()) {
                            SimpleResponse simple = response.body();
                            if(simple.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())){
                                Map<String, Object> addional = simple.AdditionalData;
                                String mID = addional.get("ItemId").toString();
                                int Id = Integer.parseInt(mID.replace(".0", ""));
                                Basic_Tags data = new Basic_Tags();
                                data.TagID = Id;
                                data.TagGroupID = MainIdSelected;
                                data.TagOrder = "1";
                                data.TagTitle = Title;
                                data.isCheck = true;
                                SQL.Insert(data);

                                FilterSubItemsFromMainItemSelected(MainIdSelected);
                            }else if(simple.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())){
                                String Err = "";
                                for (Map.Entry<String, Object> entry : simple.Errors.entrySet()) {
                                    Err = entry.getValue().toString();
                                }
                                Toast.makeText(context, Err, Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(context, Basics.ServerError, Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case 4://Property
                BodyParameters = new HashMap<>();
                BodyParameters.put("PropertyGroupID", MainIdSelected);
                BodyParameters.put("PropertyID", 0);
                BodyParameters.put("PropertyOrder", 0);
                BodyParameters.put("PropertyTitle", Title);
                BodyParameters.put("PropertyTypeKeyBoardId", TypeKeyboard);

                Gson gsonn = new Gson();
                String jsonn = gsonn.toJson(BodyParameters);

                Insert = rInterface.RQInsertBasicProperties(Setting.getToken(), new HashMap<>(BodyParameters));
                Insert.enqueue(new Callback<SimpleResponse>() {
                    @Override
                    public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                        if (response.isSuccessful()) {
                            SimpleResponse simple = response.body();
                            if(simple.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())){
                                Map<String, Object> addional = simple.AdditionalData;
                                String mID = addional.get("ItemId").toString();
                                int Id = Integer.parseInt(mID.replace(".0", ""));
                                Basic_Properties data = new Basic_Properties();
                                data.PropertyID = Id;
                                data.PropertyTitle = Title;
                                data.PropertyGroupID = MainIdSelected;
                                data.PropertyOrder = "1";
                                data.PropertyTypeKeyBoardId = TypeKeyboard;
                                data.isCheck = true;
                                SQL.Insert(data);

                                FilterSubItemsFromMainItemSelected(MainIdSelected);
                            }else if(simple.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())){
                                String Err = "";
                                for (Map.Entry<String, Object> entry : simple.Errors.entrySet()) {
                                    Err = entry.getValue().toString();
                                }
                                Toast.makeText(context, Err, Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(context, Basics.ServerError, Toast.LENGTH_LONG).show();
                    }
                });
                break;
        }
    }
    private void GroupDeleteManager(){
        Map<String, Object> BodyParameters = new HashMap<>();
        Call Delete;
        switch (wichAPI()){
            case 1://ActivityField
                BodyParameters = new HashMap<>();
                List<Integer> ids = new ArrayList<>();
                ids.add(MainIdSelected);
                BodyParameters.put("Ids", ids);

                Gson gson = new Gson();
                String json = gson.toJson(BodyParameters);

                Delete = rInterface.RQDeleteBasicActivityFieldGroups(Setting.getToken(), new HashMap<>(BodyParameters));
                Delete.enqueue(new Callback<SimpleResponse>() {
                    @Override
                    public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                        if (response.isSuccessful()) {
                            SimpleResponse simple = response.body();
                            if(simple.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())){
//                                SQL.Execute("DELETE FROM " + Tables.Basic_PropertyGroups + " WHERE PropertyGroupID='" + MainIdSelected + "'");
                                SQL.Execute("UPDATE " + Tables.Basic_ActivityFieldGroups + " SET Deleted='1' WHERE ActivityFieldGroupID='" + MainIdSelected + "'");

                                objects.MainItems(geter.getList(Basic_ActivityFieldGroups.class, " WHERE Deleted='0'"));
                                adapterMain = new adapSettingMainItems<>(objects.MainItems(), objects.MainFieldIdName(), objects.MainFieldTitleName());
                                lstMain.setAdapter(adapterMain);
                            }else if(simple.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())){
                                String Err = "";
                                for (Map.Entry<String, Object> entry : simple.Errors.entrySet()) {
                                    Err = entry.getValue().toString();
                                }
                                Toast.makeText(context, Err, Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(context, Basics.ServerError, Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case 3://Tag
                BodyParameters = new HashMap<>();
                List<Integer> idss = new ArrayList<>();
                idss.add(MainIdSelected);
                BodyParameters.put("Ids", idss);

                Gson gsonn = new Gson();
                String jsonn = gsonn.toJson(BodyParameters);

                Delete = rInterface.RQDeleteBasicTagGroups(Setting.getToken(), new HashMap<>(BodyParameters));
                Delete.enqueue(new Callback<SimpleResponse>() {
                    @Override
                    public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                        if (response.isSuccessful()) {
                            SimpleResponse simple = response.body();
                            if(simple.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())){
//                                SQL.Execute("DELETE FROM " + Tables.Basic_TagGroups + " WHERE TagGroupID='" + MainIdSelected + "'");
                                SQL.Execute("UPDATE " + Tables.Basic_TagGroups + " SET Deleted='1' WHERE TagGroupID='" + MainIdSelected + "'");

                                objects.MainItems(geter.getList(Basic_TagGroups.class, " WHERE Deleted='0'"));
                                adapterMain = new adapSettingMainItems<>(objects.MainItems(), objects.MainFieldIdName(), objects.MainFieldTitleName());
                                lstMain.setAdapter(adapterMain);
                            }else if(simple.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())){
                                String Err = "";
                                for (Map.Entry<String, Object> entry : simple.Errors.entrySet()) {
                                    Err = entry.getValue().toString();
                                }
                                Toast.makeText(context, Err, Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(context, Basics.ServerError, Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case 4://Property
                BodyParameters = new HashMap<>();
                List<Integer> idsss = new ArrayList<>();
                idsss.add(MainIdSelected);
                BodyParameters.put("Ids", idsss);

                Gson gsonb = new Gson();
                String jsonb = gsonb.toJson(BodyParameters);

                Delete = rInterface.RQDeleteBasicPropertieGroups(Setting.getToken(), new HashMap<>(BodyParameters));
                Delete.enqueue(new Callback<SimpleResponse>() {
                    @Override
                    public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                        if (response.isSuccessful()) {
                            SimpleResponse simple = response.body();
                            if(simple.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())){
//                                SQL.Execute("DELETE FROM " + Tables.Basic_PropertyGroups + " WHERE PropertyGroupID='" + MainIdSelected + "'");
                                SQL.Execute("UPDATE " + Tables.Basic_PropertyGroups + " SET Deleted='1' WHERE PropertyGroupID='" + MainIdSelected + "'");

                                objects.MainItems(geter.getList(Basic_PropertyGroups.class, " WHERE Deleted='0'"));
                                adapterMain = new adapSettingMainItems<>(objects.MainItems(), objects.MainFieldIdName(), objects.MainFieldTitleName());
                                lstMain.setAdapter(adapterMain);
                            }else if(simple.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())){
                                String Err = "";
                                for (Map.Entry<String, Object> entry : simple.Errors.entrySet()) {
                                    Err = entry.getValue().toString();
                                }
                                Toast.makeText(context, Err, Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(context, Basics.ServerError, Toast.LENGTH_LONG).show();
                    }
                });
                break;
        }
    }
    int mIDi = 0;
    private void SubDeleteManager(){
        Field[] fields = lList.getClass().getDeclaredFields();
        mIDi = 0;
        try {
            for (Field field : fields) {
                if (field.getName().contains(objects.SubFieldIdName())) {
                    try {
                        mIDi = Integer.parseInt(field.get(lList).toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, Object> BodyParameters = new HashMap<>();
        Call Delete;
        switch (wichAPI()){
            case 1://ActivityField
                BodyParameters = new HashMap<>();
                List<Integer> ids = new ArrayList<>();
                ids.add(mIDi);
                BodyParameters.put("Ids", ids);

                Delete = rInterface.RQDeleteBasicActivityFields(Setting.getToken(), new HashMap<>(BodyParameters));
                Delete.enqueue(new Callback<SimpleResponse>() {
                    @Override
                    public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                        if (response.isSuccessful()) {
                            SimpleResponse simple = response.body();
                            if(simple.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())){
//                                SQL.Execute("DELETE FROM " + Tables.Basic_ActivityFields + " WHERE ActivityFieldID='" + mIDi + "'");
                                SQL.Execute("UPDATE " + Tables.Basic_ActivityFields + " SET Deleted='1' WHERE ActivityFieldID='" + mIDi + "'");

                                FilterSubItemsFromMainItemSelected(MainIdSelected);
                            }else if(simple.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())){
                                String Err = "";
                                for (Map.Entry<String, Object> entry : simple.Errors.entrySet()) {
                                    Err = entry.getValue().toString();
                                }
                                Toast.makeText(context, Err, Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(context, Basics.ServerError, Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case 3://Tag
                BodyParameters = new HashMap<>();
                List<Integer> idss = new ArrayList<>();
                idss.add(mIDi);
                BodyParameters.put("Ids", idss);

                Delete = rInterface.RQDeleteBasicTags(Setting.getToken(), new HashMap<>(BodyParameters));
                Delete.enqueue(new Callback<SimpleResponse>() {
                    @Override
                    public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                        if (response.isSuccessful()) {
                            SimpleResponse simple = response.body();
                            if(simple.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())){
//                                SQL.Execute("DELETE FROM " + Tables.Basic_Tags + " WHERE TagID='" + mIDi + "'");
                                SQL.Execute("UPDATE " + Tables.Basic_Tags + " SET Deleted='1' WHERE TagID='" + mIDi + "'");

                                FilterSubItemsFromMainItemSelected(MainIdSelected);
                            }else if(simple.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())){
                                String Err = "";
                                for (Map.Entry<String, Object> entry : simple.Errors.entrySet()) {
                                    Err = entry.getValue().toString();
                                }
                                Toast.makeText(context, Err, Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(context, Basics.ServerError, Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case 4://Property
                BodyParameters = new HashMap<>();
                List<Integer> idsss = new ArrayList<>();
                idsss.add(mIDi);
                BodyParameters.put("Ids", idsss);

                Gson gson = new Gson();
                String json = gson.toJson(BodyParameters);

                Delete = rInterface.RQDeleteBasicProperties(Setting.getToken(), new HashMap<>(BodyParameters));
                Delete.enqueue(new Callback<SimpleResponse>() {
                    @Override
                    public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                        if (response.isSuccessful()) {
                            SimpleResponse simple = response.body();
                            if(simple.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())){
//                                SQL.Execute("DELETE FROM " + Tables.Basic_Properties + " WHERE PropertyID='" + mIDi + "'");
                                SQL.Execute("UPDATE " + Tables.Basic_Properties + " SET Deleted='1' WHERE PropertyID='" + mIDi + "'");

                                FilterSubItemsFromMainItemSelected(MainIdSelected);
                            }else if(simple.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())){
                                String Err = "";
                                for (Map.Entry<String, Object> entry : simple.Errors.entrySet()) {
                                    Err = entry.getValue().toString();
                                }
                                Toast.makeText(context, Err, Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(context, Basics.ServerError, Toast.LENGTH_LONG).show();
                    }
                });
                break;
        }
    }
    private void GroupUpdatedManager(final String Title, final int Type){
        Map<String, Object> BodyParameters = new HashMap<>();
        Call Update;
        switch (wichAPI()){
            case 1://ActivityField
                BodyParameters = new HashMap<>();
                BodyParameters.put("ActivityFieldGroupTitle", Title);
                BodyParameters.put("ActivityFieldGroupOrder", 0);
                BodyParameters.put("ActivityFieldGroupID", MainIdSelected);
                BodyParameters.put("Deleted", false);
                BodyParameters.put("ActivityFieldGroupFontIcon", "");
                BodyParameters.put("ActivityFieldGroupColor", "");

                Gson gson = new Gson();
                String json = gson.toJson(BodyParameters);

                Update = rInterface.RQUpdateBasicActivityFieldGroups(Setting.getToken(), new HashMap<>(BodyParameters));
                Update.enqueue(new Callback<SimpleResponse>() {
                    @Override
                    public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                        if (response.isSuccessful()) {
                            SimpleResponse simple = response.body();
                            if(simple.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())){
//                                Basic_ActivityFieldGroups lList = new Basic_ActivityFieldGroups();
//                                lList.ActivityFieldGroupTitle = Title;
//                                lList.ActivityFieldGroupID = MainIdSelected;
//                                lList.AdjustedByAdmin = false;
//                                lList.ActivityFieldGroupOrder = "0";
//                                lList.Deleted = false;
//                                SQL.Update(lList, " WHERE ActivityFieldGroupID='" + MainIdSelected + "'");
                                SQL.Execute("UPDATE Basic_ActivityFieldGroups SET ActivityFieldGroupTitle='" + Title + "' WHERE ActivityFieldGroupID='" + MainIdSelected + "'");


                                adapterMain = new adapSettingMainItems<>(geter.getList(Basic_ActivityFieldGroups.class, " WHERE Deleted='0'"), objects.MainFieldIdName(), objects.MainFieldTitleName());
                                lstMain.setAdapter(adapterMain);
                            }else if(simple.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())){
                                String Err = "";
                                for (Map.Entry<String, Object> entry : simple.Errors.entrySet()) {
                                    Err = entry.getValue().toString();
                                }
                                Toast.makeText(context, Err, Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(context, Basics.ServerError, Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case 3://Tag
                BodyParameters = new HashMap<>();
                BodyParameters.put("TagGroupID", MainIdSelected);
                BodyParameters.put("TagGroupTitle", Title);
                BodyParameters.put("TagGroupOrder", 0);
                BodyParameters.put("TagGroupFontIcon", "");
                BodyParameters.put("TagGroupColor", "");
                BodyParameters.put("TagGroupTypeId", Type);

                Gson gsonn = new Gson();
                String jsonn = gsonn.toJson(BodyParameters);

                Update = rInterface.RQUpdateBasicTagGroups(Setting.getToken(), new HashMap<>(BodyParameters));
                Update.enqueue(new Callback<SimpleResponse>() {
                    @Override
                    public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                        if (response.isSuccessful()) {
                            SimpleResponse simple = response.body();
                            if(simple.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())){
//                                Basic_TagGroups lList = new Basic_TagGroups();
//                                lList.TagGroupTitle = Title;
//                                lList.TagGroupID = MainIdSelected;
//                                lList.TagGroupOrder = "0";
//                                lList.TagGroupTypeId = Type;
//                                lList.Deleted = false;
//                                SQL.Update(lList, " WHERE TagGroupID='" + MainIdSelected + "'");
                                SQL.Execute("UPDATE Basic_TagGroups SET TagGroupTitle='" + Title + "' WHERE TagGroupID='" + MainIdSelected + "'");

                                adapterMain = new adapSettingMainItems<>(geter.getList(Basic_TagGroups.class, " WHERE Deleted='0'"), objects.MainFieldIdName(), objects.MainFieldTitleName());
                                lstMain.setAdapter(adapterMain);
                            }else if(simple.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())){
                                String Err = "";
                                for (Map.Entry<String, Object> entry : simple.Errors.entrySet()) {
                                    Err = entry.getValue().toString();
                                }
                                Toast.makeText(context, Err, Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(context, Basics.ServerError, Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case 4://Property
                BodyParameters = new HashMap<>();
                BodyParameters.put("PropertyGroupID", MainIdSelected);
                BodyParameters.put("PropertyGroupTitle", Title);
                BodyParameters.put("PropertyGroupOrder", 0);
                BodyParameters.put("PropertyGroupFontIcon", "");
                BodyParameters.put("PropertyGroupColor", "");

                Gson gsonj = new Gson();
                String jsonj = gsonj.toJson(BodyParameters);

                Update = rInterface.RQUpdateBasicPropertieGroups(Setting.getToken(), new HashMap<>(BodyParameters));
                Update.enqueue(new Callback<SimpleResponse>() {
                    @Override
                    public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                        if (response.isSuccessful()) {
                            SimpleResponse simple = response.body();
                            if(simple.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())){
//                                Basic_PropertyGroups lList = new Basic_PropertyGroups();
//                                lList.PropertyGroupTitle = Title;
//                                lList.PropertyGroupID = MainIdSelected;
//                                lList.PropertyGroupOrder = "0";
//                                lList.Deleted = false;
//                                SQL.Update(lList, " WHERE PropertyGroupID='" + MainIdSelected + "'");
                                SQL.Execute("UPDATE Basic_PropertyGroups SET PropertyGroupTitle='" + Title + "' WHERE PropertyGroupID='" + MainIdSelected + "'");

                                adapterMain = new adapSettingMainItems<>(geter.getList(Basic_PropertyGroups.class, " WHERE Deleted='0'"), objects.MainFieldIdName(), objects.MainFieldTitleName());
                                lstMain.setAdapter(adapterMain);
                            }else if(simple.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())){
                                String Err = "";
                                for (Map.Entry<String, Object> entry : simple.Errors.entrySet()) {
                                    Err = entry.getValue().toString();
                                }
                                Toast.makeText(context, Err, Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(context, Basics.ServerError, Toast.LENGTH_LONG).show();
                    }
                });
                break;
        }
    }
    private void SubUpdateManager(final int ID, final String Title, final int TypeKeyboard){
        Map<String, Object> BodyParameters = new HashMap<>();
        Call Update;
        switch (wichAPI()){
            case 1://ActivityField
                BodyParameters = new HashMap<>();
                BodyParameters.put("ActivityFieldID", ID);
                BodyParameters.put("ActivityFieldGroupID", MainIdSelected);
                BodyParameters.put("ActivityFieldOrder", 0);
                BodyParameters.put("ActivityFieldTitle", Title);
                BodyParameters.put("Deleted", false);
                BodyParameters.put("LastUpdateDate", "");
                BodyParameters.put("ActivityFieldFontIcon", "");

                Update = rInterface.RQUpdateBasicActivityFields(Setting.getToken(), new HashMap<>(BodyParameters));
                Update.enqueue(new Callback<SimpleResponse>() {
                @Override
                public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                    if (response.isSuccessful()) {
                        SimpleResponse simple = response.body();
                        if(simple.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())){
                            SQL.Execute("UPDATE Basic_ActivityFields SET ActivityFieldTitle='" + Title + "' WHERE ActivityFieldID='" + ID + "'");

                            FilterSubItemsFromMainItemSelected(MainIdSelected);
                        }else if(simple.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())){
                            String Err = "";
                            for (Map.Entry<String, Object> entry : simple.Errors.entrySet()) {
                                Err = entry.getValue().toString();
                            }
                            Toast.makeText(context, Err, Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    Toast.makeText(context, Basics.ServerError, Toast.LENGTH_LONG).show();
                }
            });
                break;
            case 3://Tag
                BodyParameters = new HashMap<>();
                BodyParameters.put("TagGroupID", MainIdSelected);
                BodyParameters.put("TagID", ID);
                BodyParameters.put("TagTitle", Title);
                BodyParameters.put("TagOrder", 0);

                Update = rInterface.RQUpdateBasicTags(Setting.getToken(), new HashMap<>(BodyParameters));
                Update.enqueue(new Callback<SimpleResponse>() {
                @Override
                public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                    if (response.isSuccessful()) {
                        SimpleResponse simple = response.body();
                        if(simple.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())){
                            SQL.Execute("UPDATE Basic_Tags SET TagTitle='" + Title + "' WHERE TagID='" + ID + "'");

                            FilterSubItemsFromMainItemSelected(MainIdSelected);
                        }else if(simple.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())){
                            String Err = "";
                            for (Map.Entry<String, Object> entry : simple.Errors.entrySet()) {
                                Err = entry.getValue().toString();
                            }
                            Toast.makeText(context, Err, Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    Toast.makeText(context, Basics.ServerError, Toast.LENGTH_LONG).show();
                }
            });
                break;
            case 4://Property
                BodyParameters = new HashMap<>();
                BodyParameters.put("PropertyID", ID);
                BodyParameters.put("PropertyGroupID", MainIdSelected);
                BodyParameters.put("PropertyTitle", Title);
                BodyParameters.put("PropertyOrder", 0);
                BodyParameters.put("PropertyTypeKeyBoardId", TypeKeyboard);

                Update = rInterface.RQUpdateBasicProperties(Setting.getToken(), new HashMap<>(BodyParameters));
                Update.enqueue(new Callback<SimpleResponse>() {
                @Override
                public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                    if (response.isSuccessful()) {
                        SimpleResponse simple = response.body();
                        if(simple.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())){
                            SQL.Execute("UPDATE Basic_Properties SET PropertyTitle='" + Title + "' AND PropertyTypeKeyBoardId='" + TypeKeyboard + "' WHERE PropertyID='" + ID + "'");

                            FilterSubItemsFromMainItemSelected(MainIdSelected);
                        }else if(simple.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())){
                            String Err = "";
                            for (Map.Entry<String, Object> entry : simple.Errors.entrySet()) {
                                Err = entry.getValue().toString();
                            }
                            Toast.makeText(context, Err, Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    Toast.makeText(context, Basics.ServerError, Toast.LENGTH_LONG).show();
                }
            });
                break;
        }
    }

    private void refreshManager(){
        adapterMain = new adapSettingMainItems<>(objects.MainItems(), objects.MainFieldIdName(), objects.MainFieldTitleName());
        adapterSub = new adapSettingSubItems<>(objects.SubItems(), objects.SubFieldTitleName(), objects.SubFieldIdName(), objects.MainFieldIdName());
        lstMain.setAdapter(adapterMain);
        lstSub.setAdapter(adapterSub);

        Field[] fields = objects.SubItems().get(0).getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().contains(objects.MainFieldIdName())) {
                try {
                    FilterSubItemsFromMainItemSelected(Integer.parseInt(field.get(objects.SubItems().get(0)).toString()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        SubAdderViewer(0);
    }

    static Object lList;
    static boolean ifMain = false;
    static int sIDd = 0;
    public static void Choiser(Object lListt, boolean ifMainn, int sID){
        sIDd = sID;
        lList = lListt;
        ifMain = ifMainn;
        slideUp(ViewEditor);
    }
    private static void slideUp(View view){
        view.setVisibility(View.VISIBLE);
        btnDelete.setVisibility(View.VISIBLE);
        btnUpdate.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(200);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }
    private static void slideDown(final View view){
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(200);
        animate.setFillAfter(true);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
                btnDelete.setVisibility(View.GONE);
                btnUpdate.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animate);
    }

    private void ifMainDelete(){
        GroupDeleteManager();
    }
    private void ifSubDelete(){
        SubDeleteManager();
    }
    private void ifMainUpdate(){
        mDialog = new android.app.Dialog(context);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.setContentView(R.layout.dialog_setting_basic_main);
        Objects.requireNonNull(mDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

        TextView btnAccept = mDialog.findViewById(R.id.lblAccept);
        TextView btnCancell = mDialog.findViewById(R.id.lblCancell);
        final TextInputEditText txtTitle = mDialog.findViewById(R.id.txtTitle);
        final AppCompatRadioButton radGroup = mDialog.findViewById(R.id.radGroup);
        AppCompatRadioButton radSingle = mDialog.findViewById(R.id.radSingle);
        RadioGroup radGrouper = mDialog.findViewById(R.id.radGrouper);
        CardView btnSetIcon = mDialog.findViewById(R.id.btnSetIcon);

        boolean isProp = objects.SubClass().getSimpleName().equalsIgnoreCase("Basic_Properties") || objects.SubClass().getSimpleName().equalsIgnoreCase("Basic_ActivityFields");
        if(!isProp)
            radGrouper.setVisibility(View.VISIBLE);
        else
            radGrouper.setVisibility(View.GONE);

        Field[] fields = lList.getClass().getDeclaredFields();
        String Value = "";
        boolean Rad = true;
        try {
            for (Field field : fields) {
                if (field.getName().contains("Title")) {
                    try {
                        Value = field.get(lList).toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (field.getName().contains("TagGroupTypeId")) {
                    try {
                        Rad = Integer.parseInt(field.get(lList).toString()) == 2;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        txtTitle.setText(Value);
        if(Rad)
            radGroup.setChecked(true);
        else
            radSingle.setChecked(true);

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                GroupUpdatedManager(txtTitle.getText().toString(), radGroup.isChecked() ? 2 : 3);
            }
        });
        btnCancell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        btnSetIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mDialog.show();
    }
    private void ifSubUpdate(){
        sDialog = new android.app.Dialog(context);
        sDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        sDialog.setCancelable(true);
        sDialog.setCanceledOnTouchOutside(true);
        sDialog.setContentView(R.layout.dialog_setting_basic_sub);
        Objects.requireNonNull(sDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

        TextView btnAccept = sDialog.findViewById(R.id.lblAccept);
        TextView btnCancell = sDialog.findViewById(R.id.lblCancell);
        TextView lblTitle = sDialog.findViewById(R.id.lblTitle00);
        final TextInputEditText txtTitle = sDialog.findViewById(R.id.txtTitle);
        spinType = sDialog.findViewById(R.id.spinType);

        String fName = "";
        try {
            for (int i = 0; i < objects.MainItems().size(); i++) {
                Object mObject = objects.MainItems().get(i).getClass().newInstance();
                Field[] fields = mObject.getClass().getDeclaredFields();
                for (Field field : fields) {
                    if (field.getName().contains(objects.MainFieldIdName())) {
                        try {
                            int Id = Integer.parseInt(field.get(objects.MainItems().get(i)).toString());
                            if (Id == MainIdSelected) {
                                for (Field field2 : fields) {
                                    if (field2.getName().contains(objects.MainFieldTitleName())) {
                                        try {
                                            fName = field2.get(objects.MainItems().get(i)).toString();
                                            break;
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        lblTitle.setText(fName);

        boolean isProp = objects.SubClass().getSimpleName().equalsIgnoreCase("Basic_Properties");
        if(isProp) {
            List<Basic_AndroidKeyboardTypes> lKeyboard = geter.getList(Basic_AndroidKeyboardTypes.class, " WHERE Deleted='0'");
            spinAdapop = new SpinAdapter(context, lKeyboard, "AndroidKeyboardTypeTitle");
            spinType.setAdapter(spinAdapop);
            spinType.setVisibility(View.VISIBLE);
        }else{
            spinType.setVisibility(View.GONE);
        }

        Field[] fields = lList.getClass().getDeclaredFields();
        String Value = "";
        int Key = 0;
        try {
            for (Field field : fields) {
                if (field.getName().contains("Title")) {
                    try {
                        Value = field.get(lList).toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if(isProp) {
                    if (field.getName().contains("PropertyTypeKeyBoardId")) {
                        try {
                            Key = Integer.parseInt(field.get(lList).toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        txtTitle.setText(Value);
        pos = 0;
        try {
            pos = spinAdapop.getItemPosition("PropertyTypeKeyBoardId", Integer.toString(Key));
            spinType.setSelection(pos);
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sDialog.dismiss();
//                if(pos > 0) {
                if(spinType.getVisibility() == View.VISIBLE){
                    SubUpdateManager(sIDd, txtTitle.getText().toString(), Integer.parseInt(spinAdapop.getItemString(spinType.getSelectedItemPosition(), "AndroidKeyboardTypeID")));
                }else
                    SubUpdateManager(sIDd, txtTitle.getText().toString(), 0);
            }
        });
        btnCancell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sDialog.dismiss();
            }
        });

        sDialog.show();
    }
    int pos = 0;

}
