package com.behincom.behincome.Adapters.Customer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.behincom.behincome.Activityes.Customer.fragAddCustomer;
import com.behincom.behincome.Datas.BaseData.Basic_Properties;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;

import java.util.List;

public class adapStoreDetail extends RecyclerView.Adapter<adapStoreDetail.AdapterMember> {

    Context context;
    RSQLite SQL = new RSQLite();
    RSQLGeter geter = new RSQLGeter();

    public List<Basic_Properties> lList;

    public adapStoreDetail(List<Basic_Properties> lList) {
        this.lList = lList;
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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_adstore_detail, parent, false);

        return new AdapterMember(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final AdapterMember holder, final int position) {
        final TextView lblDetailName = holder.lblDetailName;
        final EditText txtDetailDes = holder.txtDetailDes;
        final ImageView imgReNew = holder.imgReNew;

//        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
//        lblDetailName.setTypeface(tf);
//        txtDetailDes.setTypeface(tf);

        lblDetailName.setText(lList.get(position).PropertyTitle);
        if (lList.get(position).PropertyDescription.length() > 0)
            imgReNew.setVisibility(View.VISIBLE);
        else imgReNew.setVisibility(View.GONE);
        txtDetailDes.setText(lList.get(position).PropertyDescription);
        txtDetailDes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txtDetailDes.getText().toString().length() > 0)
                    imgReNew.setVisibility(View.VISIBLE);
                else imgReNew.setVisibility(View.GONE);

//                fragAddCustomer.lProperty.get(position).PropertyDescription = (txtDetailDes.getText().toString());
//                List<mBasic_Properties> lProp = geter.getList(mBasic_Properties.class, "WHERE PropertyID='" + lList.get(position).PropertyID + "'");
//                String Q = "";
//                if(lProp.size() > 0) {
////                    SQL.Execute("UPDATE mBasic_Properties SET PropertyDescription='" + txtDetailDes.getText().toString() + "' WHERE PropertyID='" + lList.get(position).PropertyID + "'");
//
//                }else{
//                    lList.get(position).PropertyDescription = txtDetailDes.getText().toString();
////                    SQL.Insert(lList.get(position), mBasic_Properties.class);
//
//                }
                for (Basic_Properties data : fragAddCustomer.lPropertie) {
                    if(data.PropertyID == lList.get(position).PropertyID){
                        data.PropertyDescription = txtDetailDes.getText().toString();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        imgReNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtDetailDes.setText("");
                String Q = "UPDATE Basic_Properties SET PropertyDescription='' WHERE PropertyID='" + lList.get(position).PropertyID + "'";
                SQL.Execute(Q);
            }
        });
    }

    public static class AdapterMember extends RecyclerView.ViewHolder {

        public TextView lblDetailName;
        public EditText txtDetailDes;
        public ImageView imgReNew;

        public AdapterMember(View itemView) {
            super(itemView);
            lblDetailName = itemView.findViewById(R.id.lblDetailName);
            txtDetailDes = itemView.findViewById(R.id.txtDetailDes);
            imgReNew = itemView.findViewById(R.id.imgReNew);
        }

    }

}
