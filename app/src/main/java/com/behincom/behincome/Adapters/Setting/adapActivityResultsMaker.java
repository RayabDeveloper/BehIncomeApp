package com.behincom.behincome.Adapters.Setting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.behincom.behincome.Accesories.Dialog;
import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Activityes.Setting.Results.fragActResults;
import com.behincom.behincome.Adapters.SpinAdapter;
import com.behincom.behincome.Datas.Base.Basics;
import com.behincom.behincome.Datas.BaseData.Basic_ActResultsMaker;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.Keys.ResponseMessageType;
import com.behincom.behincome.Datas.Keys.Tables;
import com.behincom.behincome.Datas.Marketing.ToSend.ToSendMarketingActivityResults;
import com.behincom.behincome.Datas.Point;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.Datas.Result.SimpleResponse;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.behincom.behincome.WebRequest.RWInterface;
import com.behincom.behincome.WebRequest.Retrofite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class adapActivityResultsMaker extends RecyclerView.Adapter<adapActivityResultsMaker.AdapterMember> {

    Context context;
    RSQLite SQL = new RSQLite();
    RSQLGeter geter = new RSQLGeter();
    fragActResults frag = new fragActResults();
    RWInterface rInterface = Retrofite.getClient().create(RWInterface.class);
    private Dialog pDialog;
    SpinAdapter adapPoint;

    public List<Basic_ActResultsMaker> lList;

    public adapActivityResultsMaker(List<Basic_ActResultsMaker> lList, Context context) {
        this.lList = lList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return lList.size();
    }

    public void Clear() {
        try {
            this.lList.clear();
        } catch (Exception ignored) {
        }
    }

    @Override
    public AdapterMember onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_sub_act_subact_result, parent, false);

        return new AdapterMember(itemView);
    }

    private boolean isFirst = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final AdapterMember holder, final int position) {
        LinearLayout linMainTitle = holder.linMainTitle;
        LinearLayout linSubTitle = holder.linSubTitle;
        LinearLayout linTitle = holder.linTitle;
        TextView lblMainTitle = holder.lblMainTitle;
        TextView lblSubTitle = holder.lblSubTitle;
        TextView lblTitle = holder.lblTitle;
        final AppCompatCheckBox ch = holder.ch;
        final Spinner spinPoint = holder.spinPoint;

        List<Point> lPoint = new ArrayList<>();
        for (int i = -20; i <= 20; i++) {
            Point p = new Point();
            p.ID = i;
            p.Point = i;

            lPoint.add(p);
        }
        adapPoint = new SpinAdapter(context, lPoint, "Point");
        spinPoint.setAdapter(adapPoint);

        if (lList.get(position).isCheck) {
            spinPoint.setVisibility(View.VISIBLE);
            int pos = adapPoint.getItemPosition("Point", Integer.toString(lList.get(position).Point));
            spinPoint.setSelection(pos);
        } else {
            spinPoint.setVisibility(View.GONE);
        }

        switch (lList.get(position).ActResultsType) {
            case 0:
                linMainTitle.setVisibility(View.VISIBLE);
                linSubTitle.setVisibility(View.GONE);
                linTitle.setVisibility(View.GONE);

                lblMainTitle.setText(lList.get(position).ActResultsTitle);
                break;
            case 1:
                linMainTitle.setVisibility(View.GONE);
                linSubTitle.setVisibility(View.VISIBLE);
                linTitle.setVisibility(View.GONE);

                lblSubTitle.setText(lList.get(position).ActResultsTitle);
                break;
            case 2:
                linMainTitle.setVisibility(View.GONE);
                linSubTitle.setVisibility(View.GONE);
                linTitle.setVisibility(View.VISIBLE);

                lblTitle.setText(lList.get(position).ActResultsTitle);
                if (lList.get(position).isCheck) {
                    ch.setChecked(true);
                    frag.IdsToSend += lList.get(position).ActResultsID;
                } else
                    ch.setChecked(false);
                break;
        }
        lblTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ch.isChecked())
                    ch.setChecked(false);
                else
                    ch.setChecked(true);
                Checker(ch.isChecked(), position);
            }
        });
        ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Checker(ch.isChecked(), position);
            }
        });
        spinPoint.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                    isFirst = true;
                return false;
            }
        });
        spinPoint.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int positionn, long id) {
                if (isFirst) {
                    try {
                        pDialog = new Dialog(context);
                        pDialog.Show();

                        final String Point = adapPoint.getItemString(position, "Point");
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("ActResultId", lList.get(position).ActResultsID);
                        map.put("Point", Integer.parseInt(Point));

                        Call cAddResults = rInterface.RQAddPointMarketingActivityResults(Setting.getToken(), map);
                        cAddResults.enqueue(new Callback<SimpleResponse>() {
                            @Override
                            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                                if (response.isSuccessful()) {
                                    SimpleResponse simple = response.body();
                                    if (simple.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())) {
                                        SQL.Execute("UPDATE " + Tables.Basic_ActResults + " SET Point='" + Point + "' WHERE ActResultID='" + lList.get(position).ActResultsID + "'");
                                        lList.get(position).Point = Integer.parseInt(Point);
                                        notifyDataSetChanged();
                                    } else if (simple.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())) {
                                        String Err = "";
                                        for (Map.Entry<String, Object> entry : simple.Errors.entrySet()) {
                                            Err = entry.getValue().toString();
                                        }
                                        Toast.makeText(context, Err, Toast.LENGTH_LONG).show();
                                    }
                                }
                                pDialog.DisMiss();
                            }

                            @Override
                            public void onFailure(Call call, Throwable t) {
                                pDialog.DisMiss();
                                Toast.makeText(context, Basics.ServerError, Toast.LENGTH_LONG).show();
                            }
                        });
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void Checker(boolean isCheck, final int position) {
        pDialog = new Dialog(context);
        pDialog.Show();

        if (isCheck) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("ActResultId", lList.get(position).ActResultsID);
            map.put("Point", 0);

            Call cAddResults = rInterface.RQAddMarketingActivityResults(Setting.getToken(), map);
            cAddResults.enqueue(new Callback<SimpleResponse>() {
                @Override
                public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                    if (response.isSuccessful()) {
                        SimpleResponse simple = response.body();
                        if (simple.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())) {
                            SQL.Execute("UPDATE " + Tables.Basic_ActResults + " SET isCheck='1' WHERE ActResultID='" + lList.get(position).ActResultsID + "'");
                            lList.get(position).isCheck = true;
                            notifyDataSetChanged();
                        } else if (simple.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())) {
                            String Err = "";
                            for (Map.Entry<String, Object> entry : simple.Errors.entrySet()) {
                                Err = entry.getValue().toString();
                            }
                            Toast.makeText(context, Err, Toast.LENGTH_LONG).show();
                        }
                    }
                    pDialog.DisMiss();
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    pDialog.DisMiss();
                    Toast.makeText(context, Basics.ServerError, Toast.LENGTH_LONG).show();
                }
            });
        } else {
            HashMap<String, Object> map2 = new HashMap<>();
            List<Integer> IDs = new ArrayList<>();
            IDs.add(lList.get(position).ActResultsID);
            map2.put("Ids", IDs);

            Call cAddResults = rInterface.RQRemoveMarketingActivityResults(Setting.getToken(), map2);
            cAddResults.enqueue(new Callback<SimpleResponse>() {
                @Override
                public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                    if (response.isSuccessful()) {
                        SimpleResponse simple = response.body();
                        if (simple.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())) {
                            SQL.Execute("UPDATE " + Tables.Basic_ActResults + " SET isCheck='0' AND Point='0' WHERE ActResultID='" + lList.get(position).ActResultsID + "'");
                            lList.get(position).Point = 0;
                            lList.get(position).isCheck = false;
                            notifyDataSetChanged();
                        } else if (simple.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())) {
                            String Err = "";
                            for (Map.Entry<String, Object> entry : simple.Errors.entrySet()) {
                                Err = entry.getValue().toString();
                            }
                            Toast.makeText(context, Err, Toast.LENGTH_LONG).show();
                        }
                    }
                    pDialog.DisMiss();
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    pDialog.DisMiss();
                    Toast.makeText(context, Basics.ServerError, Toast.LENGTH_LONG).show();
                }
            });
        }

//        String Q2 = "";
//        if (isCheck) {
//            Q2 = "UPDATE Basic_ActResults SET isCheck='1' WHERE ActResultID='" + lList.get(position).ActResultsID + "'";
//            frag.IdsToSend += lList.get(position).ActResultsID + ", ";
//        } else {
//            Q2 = "UPDATE Basic_ActResults SET isCheck='0' WHERE ActResultID='" + lList.get(position).ActResultsID + "'";
//            frag.IdsToSend = frag.IdsToSend.replace(lList.get(position).ActResultsID + ", ", "") ;
//        }
//        String Q = "UPDATE Basic_Acts SET isCheck='" + (isCheck ? "1" : "0") + "' WHERE ActID='" + lList.get(position).ActID + "'";
//        SQL.Execute(Q);
//        SQL.Execute(Q2);
//        lList.get(position).isCheck = (isCheck);
//        fragActResults.sendRequest(lList.get(position).ActResultsID);


    }

    public static class AdapterMember extends RecyclerView.ViewHolder {

        public LinearLayout linMainTitle;
        public LinearLayout linSubTitle;
        public LinearLayout linTitle;
        public TextView lblMainTitle;
        public TextView lblSubTitle;
        public TextView lblTitle;
        public AppCompatCheckBox ch;
        public Spinner spinPoint;

        public AdapterMember(View itemView) {
            super(itemView);
            linMainTitle = itemView.findViewById(R.id.linMainTitle);
            linSubTitle = itemView.findViewById(R.id.linSubTitle);
            linTitle = itemView.findViewById(R.id.linTitle);
            lblMainTitle = itemView.findViewById(R.id.lblMainTitle);
            lblSubTitle = itemView.findViewById(R.id.lblSubTitle);
            lblTitle = itemView.findViewById(R.id.lblTitle);
            ch = itemView.findViewById(R.id.ch);
            spinPoint = itemView.findViewById(R.id.spinPoint);
        }

    }

}
