package com.behincom.behincome.Adapters.Customer.AddCustomer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.behincom.behincome.Activityes.Customer.AddCustomer.fragProperty;
import com.behincom.behincome.Activityes.Customer.AddCustomer.fragTag;
import com.behincom.behincome.Datas.BaseData.Basic_Properties;
import com.behincom.behincome.Datas.BaseData.Basic_PropertyGroups;
import com.behincom.behincome.Datas.BaseData.Basic_TagGroups;
import com.behincom.behincome.Datas.BaseData.Basic_Tags;
import com.behincom.behincome.Datas.Keys.AndroidKeyboards;
import com.behincom.behincome.Datas.Keys.TagType;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;

import java.util.ArrayList;
import java.util.List;

public class adapAddCustomerPropertySub extends RecyclerView.Adapter<adapAddCustomerPropertySub.AdapterMember> {

    private Context context;
    RSQLGeter geter = new RSQLGeter();

    private List<Basic_Properties> lList = new ArrayList<>();
    private List<Basic_Properties> lListCustomer = new ArrayList<>();

    public adapAddCustomerPropertySub(List<Basic_Properties> lList, List<Basic_Properties> lCustomer, Context mContext) {
        this.lList = lList;
        this.lListCustomer = lCustomer;
        this.context = mContext;
    }

    public List<Basic_Properties> getList() {
        return lList;
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
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_add_customer_property, parent, false);

        return new AdapterMember(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final AdapterMember holder, final int position) {
        final TextView lblSubTitle = holder.lblSubTitle;
        final TextInputEditText txtValue = holder.txtValue;

        lblSubTitle.setText(lList.get(position).PropertyTitle);
        txtValue.setText(lList.get(position).PropertyDescription);
        txtValue.setInputType(getType(lList.get(position).PropertyTypeKeyBoardId));

        txtValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lList.get(position).PropertyDescription = txtValue.getText().toString();
                boolean isIn = false;
                if(txtValue.getText().toString().length() > 0) {
                    for (Basic_Properties data : lListCustomer) {
                        if (lList.get(position).PropertyID == data.PropertyID) {
                            isIn = true;
                            data.PropertyDescription = txtValue.getText().toString();
                            break;
                        }
                    }
                    if (!isIn)
                        lListCustomer.add(lList.get(position));
                }else{
                    lListCustomer.remove(lList.get(position));
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    private int getType(int AndroidKey) {
        switch (AndroidKey) {
            case AndroidKeyboards.text:
                return InputType.TYPE_CLASS_TEXT;
            case AndroidKeyboards.number:
                return InputType.TYPE_CLASS_NUMBER;
            case AndroidKeyboards.date:
                return InputType.TYPE_CLASS_DATETIME;
            case AndroidKeyboards.datetime:
                return InputType.TYPE_CLASS_DATETIME;
            case AndroidKeyboards.numberDecimal:
                return InputType.TYPE_NUMBER_FLAG_DECIMAL;
            case AndroidKeyboards.phone:
                return InputType.TYPE_CLASS_PHONE;
            case AndroidKeyboards.textEmailAddress:
                return InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
            case AndroidKeyboards.textUri:
                return InputType.TYPE_TEXT_VARIATION_URI;
            case AndroidKeyboards.time:
                return InputType.TYPE_CLASS_DATETIME;
        }
        return InputType.TYPE_CLASS_TEXT;
    }

    private boolean isRadio(int TagGroupID){
        List<Basic_TagGroups> lGroup = geter.getList(Basic_TagGroups.class, " WHERE TagGroupID='" + TagGroupID + "'");
        if(lGroup.size() > 0){
            if(lGroup.get(0).TagGroupTypeId == TagType.RadioButton)
                return true;
            else
                return false;
        }else
            return false;
    }

    public static class AdapterMember extends RecyclerView.ViewHolder {

        public TextView lblSubTitle;
        public TextInputEditText txtValue;

        AdapterMember(View itemView) {
            super(itemView);
            lblSubTitle = itemView.findViewById(R.id.lblSubTitle);
            txtValue = itemView.findViewById(R.id.txtValue);
        }

    }

}
