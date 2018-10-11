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
import com.behincom.behincome.Datas.BaseData.Basic_ActivityFieldGroups;
import com.behincom.behincome.Datas.BaseData.Basic_AndroidKeyboardTypes;
import com.behincom.behincome.Datas.BaseData.Basic_PropertyGroups;
import com.behincom.behincome.Datas.BaseData.Basic_TagGroups;
import com.behincom.behincome.Datas.Keys.FragmentState;
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
    SpinAdapter spinAdapop;
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
    LinearLayout btnDelete;
    LinearLayout btnUpdate;
    private List<T> SubItems = new ArrayList<>();

    private static int MainIdSelected = 0;
    private Object object;

    public static fragBasicData newInstance(Context mContext) {
        fragBasicData fragment = new fragBasicData();
        context = mContext;
        return fragment;
    }

    public static String IdsToSend = "";

    //todo String asd = IdsToSend; ( To Get All Checked on Current Sub Only )
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

                boolean isProp = objects.SubClass().getSimpleName().equalsIgnoreCase("Basic_Properties");
                if(isProp)
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
                    List<Basic_AndroidKeyboardTypes> lKeyboard = geter.getList(Basic_AndroidKeyboardTypes.class);
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
                BodyParameters.put("ActivityFieldGroupFontIcon", "");
                BodyParameters.put("ActivityFieldGroupColor", "");
                BodyParameters.put("Deleted", false);

                Insert = rInterface.RQInsertBasicActivityFieldGroups(Setting.getToken(), new HashMap<>(BodyParameters));
                Insert.enqueue(new Callback<SimpleResponse>() {
                    @Override
                    public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                        if (response.isSuccessful()) {
                            SimpleResponse simple = response.body();
                            Map<String, Object> addional = simple.AdditionalData;
                            String mID = addional.get("ItemId").toString();
                            int Id = Integer.parseInt(mID.replace(".0", ""));
                            Basic_ActivityFieldGroups data = new Basic_ActivityFieldGroups();
                            data.ActivityFieldGroupID = Id;
                            data.ActivityFieldGroupTitle = Title;
                            data.AdjustedByAdmin = false;
                            data.isCheck = true;
                            SQL.Insert(data);

                            objects.MainItems(geter.getList(Basic_ActivityFieldGroups.class));
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {

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

                String asdf = Setting.getToken();

                Insert = rInterface.RQInsertBasicTagGroups(Setting.getToken(), new HashMap<>(BodyParameters));
                Insert.enqueue(new Callback<SimpleResponse>() {
                    @Override
                    public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                        if (response.isSuccessful()) {
                            SimpleResponse simple = response.body();
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

                            objects.MainItems(geter.getList(Basic_TagGroups.class));
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {

                    }
                });
                break;
            case 4://Property
                BodyParameters = new HashMap<>();
                BodyParameters.put("PropertyGroupID", Title);
                BodyParameters.put("PropertyGroupTitle", 0);
                BodyParameters.put("PropertyGroupOrder", 0);
                BodyParameters.put("PropertyGroupFontIcon", "");
                BodyParameters.put("PropertyGroupColor", "");

                Insert = rInterface.RQInsertBasicPropertieGroups(Setting.getToken(), new HashMap<>(BodyParameters));
                Insert.enqueue(new Callback<SimpleResponse>() {
                    @Override
                    public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                        if (response.isSuccessful()) {
                            SimpleResponse simple = response.body();
                            Map<String, Object> addional = simple.AdditionalData;
                            String mID = addional.get("ItemId").toString();
                            int Id = Integer.parseInt(mID.replace(".0", ""));
                            Basic_PropertyGroups data = new Basic_PropertyGroups();
                            data.PropertyGroupID = Id;
                            data.PropertyGroupTitle = Title;
                            data.PropertyGroupAdjustedByAdmin = false;
                            data.isCheck = true;
                            SQL.Insert(data);

                            objects.MainItems(geter.getList(Basic_PropertyGroups.class));
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {

                    }
                });
                break;
        }
    }
    private void SubAddManager(String Title, int TypeKeyboard){
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

                Insert = rInterface.RQInsertBasicActivityFields(Setting.getToken(), new HashMap<>(BodyParameters));
                break;
            case 3://Tag
                BodyParameters = new HashMap<>();
                BodyParameters.put("TagGroupID", MainIdSelected);
                BodyParameters.put("TagID", 0);
                BodyParameters.put("TagOrder", 0);
                BodyParameters.put("TagTitle", Title);

                Insert = rInterface.RQInsertBasicTags(Setting.getToken(), new HashMap<>(BodyParameters));
                Insert.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        if(response.isSuccessful()){
                            String asd = "ASD";
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        String asd = "ASD";
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

                Insert = rInterface.RQInsertBasicProperties(Setting.getToken(), new HashMap<>(BodyParameters));
                break;
        }
    }
    private void GroupDeleteManager(){
        Map<String, Object> BodyParameters = new HashMap<>();
        Call Delete;
        switch (wichAPI()){
            case 1://ActivityField
                BodyParameters = new HashMap<>();
                BodyParameters.put("activityFieldGroupId", MainIdSelected);

                Delete = rInterface.RQDeleteBasicActivityFields(Setting.getToken(), new HashMap<>(BodyParameters));
                break;
            case 3://Tag
                BodyParameters = new HashMap<>();
                BodyParameters.put("tagGroupId", MainIdSelected);

                Delete = rInterface.RQDeleteBasicTags(Setting.getToken(), new HashMap<>(BodyParameters));
                break;
            case 4://Property
                BodyParameters = new HashMap<>();
                BodyParameters.put("propertyGroupId", MainIdSelected);

                Delete = rInterface.RQDeleteBasicProperties(Setting.getToken(), new HashMap<>(BodyParameters));
                break;
        }
    }
    private void SubDeleteManager(){
        Field[] fields = lList.getClass().getDeclaredFields();
        int ID = 0;
        try {
            for (Field field : fields) {
                if (field.getName().contains(objects.SubFieldIdName())) {
                    try {
                        ID = Integer.parseInt(field.get(lList).toString());
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
                BodyParameters.put("activityFieldId", ID);

                Delete = rInterface.RQDeleteBasicActivityFields(Setting.getToken(), new HashMap<>(BodyParameters));
                break;
            case 3://Tag
                BodyParameters = new HashMap<>();
                BodyParameters.put("tagId", ID);

                Delete = rInterface.RQDeleteBasicTags(Setting.getToken(), new HashMap<>(BodyParameters));
                break;
            case 4://Property
                BodyParameters = new HashMap<>();
                BodyParameters.put("propertyId", ID);

                Delete = rInterface.RQDeleteBasicProperties(Setting.getToken(), new HashMap<>(BodyParameters));
                break;
        }
    }
    private void GroupUpdatedManager(String Title, int Type){
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

                Update = rInterface.RQUpdateBasicActivityFieldGroups(Setting.getToken(), new HashMap<>(BodyParameters));
                break;
            case 3://Tag
                BodyParameters = new HashMap<>();
                BodyParameters.put("TagGroupID", MainIdSelected);
                BodyParameters.put("TagGroupTitle", Title);
                BodyParameters.put("TagGroupOrder", 0);
                BodyParameters.put("TagGroupFontIcon", "");
                BodyParameters.put("TagGroupColor", "");
                BodyParameters.put("TagGroupTypeId", Type);

                Update = rInterface.RQUpdateBasicTagGroups(Setting.getToken(), new HashMap<>(BodyParameters));
                break;
            case 4://Property
                BodyParameters = new HashMap<>();
                BodyParameters.put("PropertyGroupID", MainIdSelected);
                BodyParameters.put("PropertyGroupTitle", Title);
                BodyParameters.put("PropertyGroupOrder", 0);
                BodyParameters.put("PropertyGroupFontIcon", "");
                BodyParameters.put("PropertyGroupColor", "");

                Update = rInterface.RQUpdateBasicPropertieGroups(Setting.getToken(), new HashMap<>(BodyParameters));
                break;
        }
    }
    private void SubUpdateManager(int ID, String Title, int TypeKeyboard){
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
                break;
            case 3://Tag
                BodyParameters = new HashMap<>();
                BodyParameters.put("TagGroupID", MainIdSelected);
                BodyParameters.put("TagID", ID);
                BodyParameters.put("TagTitle", Title);
                BodyParameters.put("TagOrder", 0);

                Update = rInterface.RQUpdateBasicTags(Setting.getToken(), new HashMap<>(BodyParameters));
                break;
            case 4://Property
                BodyParameters = new HashMap<>();
                BodyParameters.put("PropertyID", ID);
                BodyParameters.put("PropertyGroupID", MainIdSelected);
                BodyParameters.put("PropertyTitle", Title);
                BodyParameters.put("PropertyOrder", 0);
                BodyParameters.put("PropertyTypeKeyBoardId", TypeKeyboard);

                Update = rInterface.RQUpdateBasicProperties(Setting.getToken(), new HashMap<>(BodyParameters));
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
    public static void Choiser(Object lListt, boolean ifMainn){
        lList = lListt;
        ifMain = ifMainn;
        slideUp(ViewEditor);
    }
    private static void slideUp(View view){
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(200);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }
    private static void slideDown(View view){
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(200);
        animate.setFillAfter(true);
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

        boolean isProp = objects.SubClass().getSimpleName().equalsIgnoreCase("Basic_Properties");
        if(isProp)
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
        final Spinner spinType = sDialog.findViewById(R.id.spinType);

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
            List<Basic_AndroidKeyboardTypes> lKeyboard = geter.getList(Basic_AndroidKeyboardTypes.class);
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
        int pos = spinAdapop.getItemPosition("PropertyTypeKeyBoardId", Integer.toString(Key));
        spinType.setSelection(pos);

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sDialog.dismiss();
                SubUpdateManager(1, txtTitle.getText().toString(), Integer.parseInt(spinAdapop.getItemString(spinType.getSelectedItemPosition(), "AndroidKeyboardTypeID")));
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

}
