package com.behincom.behincome.Activityes.Setting;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.behincom.behincome.Accesories.Dialog;
import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Adapters.Setting.adapActivityResultsMaker;
import com.behincom.behincome.Datas.BaseData.Basic_Acts;
import com.behincom.behincome.Datas.BaseData.Basic_ActResults;
import com.behincom.behincome.Datas.BaseData.Basic_ActResultsMaker;
import com.behincom.behincome.Datas.BaseData.Basic_ActGroups;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.Keys.Tables;
import com.behincom.behincome.Datas.Marketing.ToSend.ToSendMarketingActivityResults;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.Datas.Result.SimpleResponse;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.behincom.behincome.WebRequest.RWInterface;
import com.behincom.behincome.WebRequest.Retrofite;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragActResults extends Fragment {

    static Context context;
    private RSQLite SQL = new RSQLite();
    RSQLGeter geter = new RSQLGeter();
    private Dialog pDialog;
    actSetting act = new actSetting();
    RWInterface rInterface;

    private RecyclerView lstMain;
    private ImageView btnCheck;

//    private List<Basic_ActResults> lSubActResult = new ArrayList<>();
    private List<Basic_ActResultsMaker> lList = new ArrayList<>();

    public static String IdsToSend = "";

    private String Id = "0";

    public static fragActResults newInstance(Context mContext) {
        fragActResults fragment = new fragActResults();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_act_results, container, false);

        TextView lblTitle = view.findViewById(R.id.lblTitle);
        lstMain = view.findViewById(R.id.lstMain);
        ImageView imgBack = view.findViewById(R.id.imgBack);
        ImageView btnCheck = view.findViewById(R.id.btnCheck);

        IdsToSend = "";

//        Typeface tFace = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
//        lblTitle.setTypeface(tFace);

        lblTitle.setText("انتخاب نتیجه");
        imgBack.setVisibility(View.VISIBLE);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getFragByState(FragmentState.Setting);
            }
        });
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
            }
        });

        rInterface = Retrofite.getClient().create(RWInterface.class);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        RefreshList();
    }

    private void RefreshList() {
        List<Basic_ActGroups> lMain = geter.getList(Basic_ActGroups.class);
        for (Basic_ActGroups data : lMain) {
            Basic_ActResultsMaker mData = new Basic_ActResultsMaker();
            mData.ActResultsType = (0);
            mData.ActResultsTitle = (data.ActGroupTitle);
            mData.ActResultsID = (data.ActGroupID);
            lList.add(mData);

            List<Basic_Acts> lSub = geter.getList(Basic_Acts.class, " WHERE ActGroupID='" + data.ActGroupID + "'");
            for (Basic_Acts sData : lSub) {
                Basic_ActResultsMaker msData = new Basic_ActResultsMaker();
                msData.ActResultsType = (1);
                msData.ActResultsTitle = (sData.ActTitle);
                msData.ActResultsID = (sData.ActID);
                msData.ActID = (sData.ActID);
                lList.add(msData);

                List<Basic_ActResults> lResult = geter.getList(Basic_ActResults.class, " WHERE ActID='" + msData.ActID + "'");
                for (Basic_ActResults rData : lResult) {
                    Basic_ActResultsMaker mrData = new Basic_ActResultsMaker();
                    mrData.ActResultsType = (2);
                    mrData.ActResultsTitle = (rData.ActResultTitle);
                    mrData.ActResultsID = (rData.ActResultID);
                    mrData.ActTypeID = (rData.ActID);
                    mrData.ActID = rData.ActID;
                    mrData.isCheck = (rData.isCheck);
                    lList.add(mrData);
                }
            }
        }
        adapActivityResultsMaker adapter = new adapActivityResultsMaker(lList);
        lstMain.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        lstMain.setLayoutManager(mLayoutManager);
        lstMain.addItemDecoration(new HorizontalDividerItemDecoration.Builder(context).colorResId(R.color.txtWhite).size(2).build());
        lstMain.setItemAnimator(new DefaultItemAnimator());
        lstMain.setAdapter(adapter);
    }

    private void sendRequest() {
        pDialog = new Dialog(context);
        pDialog.Show();

        ObjectMapper oMapper = new ObjectMapper();
        List<Map> maps = new ArrayList<>();
        for (ToSendMarketingActivityResults data : getSubActResultsForRequest()) {
            Map map = oMapper.convertValue(data, Map.class);
            maps.add(map);
        }

        Call cAddResults = rInterface.RQAddMarketingActivityResults(Setting.getToken(), maps);
        cAddResults.enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                if(response.isSuccessful()){
                    for (ToSendMarketingActivityResults data : getSubActResultsForRequest()) {
                        SQL.Execute("UPDATE " + Tables.Basic_ActResults + " SET isCheck='1' WHERE ActResultID='" + data.ActivityResultID + "'");
                    }
                }
                act.getFragByState(FragmentState.Setting);
                pDialog.DisMiss();
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                pDialog.DisMiss();
            }
        });

//        Map<String, String> Header = Data.Header_Patch(context);
//        Map<String, String> Body = new HashMap<>();
//
//        if (lList.size() > 1) {
//            pDialog.Show();
//            Body.put(Data.APIKeys.accountId, Id);
//            Body.put(Data.APIKeys.SubActResults, getSubActResultsForRequest());
//            String mURL = Data.APIKeys.InsertSubActResult;
//            wAPI.API(Request.Method.POST, mURL, Header, Body);
//
//            wAPI.setOnIncomingResult(new WebApi.onResponseResult() {
//                @Override
//                public void onResponseResults(String Result, int StatusCode) {
//                    pDialog.DisMiss();
//                    if (StatusCode == 200) {
//                        try {
//                            Toast.makeText(context, "نتیجه ها ذخیره شدند.", Toast.LENGTH_SHORT).show();
//                            act.getFragByState(FragmentState.Setting);
//                        } catch (Exception Ex) {
//                            String Er = Ex.getMessage();
//                        }
//                    } else {
//                        try {
//                            Toast.makeText(context, "ارتباط با سرور برقرار نشد ، دوباره سعی کنید.", Toast.LENGTH_SHORT).show();
//                        } catch (Exception Ex) {
//                            String Er = Ex.getMessage();
//                        }
//                    }
//                }
//            });
//        } else Toast.makeText(context, "لطفا نتیجه ای را انتخاب کنید.", Toast.LENGTH_SHORT).show();
    }

    private List<ToSendMarketingActivityResults> getSubActResultsForRequest() {
        String[] items = IdsToSend.split(", ");
        List<ToSendMarketingActivityResults> Reterner = new ArrayList<>();
        for (String data : items) {
            try {
                ToSendMarketingActivityResults dat = new ToSendMarketingActivityResults();
                dat.ActivityResultID = Integer.parseInt(data.trim());
                dat.Point = 0;

                Reterner.add(dat);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return Reterner;
//        String SubActResultes = "";
//        lSubActResult = geter.getList(Basic_ActResults.class, "WHERE isCheck='1'");
//        for (Basic_ActResults data : lSubActResult) {
//            SubActResultes += data.ActResultID + ",";
//        }
//        return SubActResultes.substring(0, SubActResultes.length() - 1);
    }

}
